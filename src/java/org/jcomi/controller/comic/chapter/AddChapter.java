package org.jcomi.controller.comic.chapter;

import org.jcomi.controller.ControllerServlet;
import org.jcomi.controller.comic.AddComic;
import org.jcomi.entity.account.Account;
import org.jcomi.entity.comic.Comic;
import org.jcomi.entity.comic.ComicDataAccess;
import org.jcomi.entity.comic.chapter.Chapter;
import org.jcomi.entity.comic.chapter.ChapterDataAccess;
import org.jcomi.entity.comic.chapter.Page;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "AddComicChapter", value = "/comic/chapter/add",
    initParams = {
        @WebInitParam(name = "uploadPath", value = "/uploads/comic")
    }
)
@MultipartConfig(maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 512) // 10MB, 512MB
public class AddChapter extends ControllerServlet {

    private String uploadImage(Part image, Comic comic, Chapter chapter, int ordinal) {
        try {
            Path virtualPath = Paths.get(getServletConfig().getInitParameter("uploadPath"), String.valueOf(comic.getId()), String.valueOf(chapter.getOrdinal()));
            Path realPath = Paths.get(getServletContext().getRealPath(""), virtualPath.toString());
            Files.createDirectories(realPath); // create directory if not exists
            String fileName = ordinal + ".webp"; // coverImage.getSubmittedFileName();
            Path filePath = Paths.get(realPath.toString(), fileName); // realPath + fileName
            Files.deleteIfExists(filePath); // delete if exists
            Files.copy(image.getInputStream(), filePath); // upload file
            return Paths.get(virtualPath.toString(), fileName).toString(); // virtualPath + fileName
        } catch (IOException e) {
            Logger.getLogger(AddComic.class.getName()).log(Level.SEVERE, "Error when upload cover image", e);
            return "";
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("/comic/chapter/add.jsp").forward(request, response);
        } catch (Exception e) {
            Logger.getLogger(AddComic.class.getName()).log(Level.SEVERE, "Error while getting comic info", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            Optional<Object> rawAccount = Optional.ofNullable(request.getSession(false))
                .map(session -> session.getAttribute("user"));
            if (!rawAccount.isPresent()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            Account account = (Account) rawAccount.get();
            if (!account.isAdmin() && !account.isUploader()) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            Optional<Integer> comicId = Optional.ofNullable(request.getParameter("id")).filter(id -> !id.isEmpty()).map(Integer::parseInt);
            Optional<String> title = Optional.ofNullable(request.getParameter("title")).filter(s -> !s.isEmpty());
            Optional<Integer> ordinal = Optional.ofNullable(request.getParameter("ordinal")).filter(s -> !s.isEmpty()).map(Integer::parseInt);
            if (!comicId.isPresent() || !ordinal.isPresent()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing Comic ID or Chapter ordinal");
                System.out.println("Bad request");
                return;
            }
            ComicDataAccess comicDataAccess = new ComicDataAccess();
            Optional<Comic> comic = comicDataAccess.getOne(comicId.get());
            if (!comic.isPresent()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Comic not found");
                return;
            }
            Chapter chapter = new Chapter();
            chapter.setComicId(comic.get().getId());
            chapter.setOrdinal(ordinal.get());
            Logger.getLogger(AddComic.class.getName()).log(Level.INFO, "Add chapter {0} to comic {1}", new Object[]{chapter.getOrdinal(), comic.get().getId()});
            title.ifPresent(chapter::setTitle);
            Collection<Part> images = request.getParts();
            List<Page> pages = new ArrayList<>();
            ChapterDataAccess chapterDataAccess = new ChapterDataAccess();
            chapterDataAccess.insertAndGetIdentifier(chapter);
            int i = 1;
            for (Part image : images) {
                if (image.getName().contains("images")) {
                    String imagePath = uploadImage(image, comic.get(), chapter, i);
                    if (!imagePath.isEmpty()) {
                        Page page = new Page();
                        page.setChapterId(chapter.getId());
                        page.setOrdinal(i);
                        page.setImageUrl(imagePath);
                        pages.add(page);
                    }
                    i++;
                }
            }
            chapterDataAccess.addPages(pages);

//            response.sendRedirect(request.getContextPath() + "/comic/view/" + "?id=" + comic.get().getId() + "&chapter=" + chapter.getOrdinal());
            response.getWriter().print("{\"status\": \"success\", \"id\": " + comic.get().getId() + "}");
//            response.getWriter().write("{\"status\": \"success\", \"id\": " + comic.get().getId() + ", \"chapter\": " + chapter.getOrdinal() + "}");
        } catch (Exception e) {
            Logger.getLogger(AddComic.class.getName()).log(Level.SEVERE, "Error while adding comic", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
