package org.jcomi.controller.comic.chapter;

import org.jcomi.controller.ControllerServlet;
import org.jcomi.controller.comic.AddComic;
import org.jcomi.entity.account.Account;
import org.jcomi.entity.comic.Comic;
import org.jcomi.entity.comic.ComicDataAccess;
import org.jcomi.entity.comic.chapter.Chapter;
import org.jcomi.entity.comic.chapter.ChapterDataAccess;
import org.jcomi.entity.comic.chapter.Page;
import org.jcomi.entity.comic.genre.ComicGenre;
import org.jcomi.entity.comic.genre.ComicGenreDataAccess;
import org.jcomi.entity.genre.Genre;
import org.jcomi.entity.genre.GenreDataAccess;

import javax.servlet.ServletException;
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

@WebServlet(name = "AddComic", value = "/comic/add",
    initParams = {
        @WebInitParam(name = "uploadPath", value = "/uploads/comic")
    }
)
@MultipartConfig(maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 512) // 10MB, 128MB
public class AddChapter extends ControllerServlet {

    private String uploadImage(Part image, Comic comic) {
        try {
            Path virtualPath = Paths.get(getServletConfig().getInitParameter("uploadPath"), String.valueOf(comic.getId()));
            Path realPath = Paths.get(getServletContext().getRealPath(virtualPath.toString()));
            Files.createDirectories(realPath); // create directory if not exists
            String fileName = "cover.webp"; // coverImage.getSubmittedFileName();
            Path filePath = Paths.get(realPath.toString(), fileName); // realPath + fileName
            Files.copy(image.getInputStream(), filePath); // upload file
            return Paths.get(virtualPath.toString(), fileName).toString(); // virtualPath + fileName
        } catch (IOException e) {
            Logger.getLogger(AddComic.class.getName()).log(Level.SEVERE, "Error when upload cover image", e);
            return "";
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
            Optional<Integer> comicId = Optional.ofNullable(request.getParameter("comic-id")).filter(id -> !id.isEmpty()).map(Integer::parseInt);
            Optional<String> title = Optional.ofNullable(request.getParameter("title")).filter(s -> !s.isEmpty());
            Optional<Integer> ordinal = Optional.ofNullable(request.getParameter("ordinal")).filter(s -> !s.isEmpty()).map(Integer::parseInt);
            if (!comicId.isPresent() || !ordinal.isPresent()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing comic-id");
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
            title.ifPresent(chapter::setTitle);
            Collection<Part> images = request.getParts();
            List<Page> pages = new ArrayList<>();
            int i = 1;
            for (Part image : images) {
                if (image.getName().contains("images")) {
                    String imagePath = uploadImage(image, comic.get());
                    if (!imagePath.isEmpty()) {
                        Page page = new Page();
                        page.setChapterId(chapter.getId());
                        page.setOrdinal(i++);
                        page.setImageUrl(imagePath);
                        pages.add(page);
                    }
                }
            }
            ChapterDataAccess chapterDataAccess = new ChapterDataAccess();
            chapterDataAccess.insert(chapter);
            chapterDataAccess.addPages(chapter.getId(), pages);

//            response.sendRedirect(request.getContextPath() + "/comic/view/" + "?id=" + comic.get().getId() + "&chapter=" + chapter.getOrdinal());
        } catch (Exception e) {
            Logger.getLogger(AddComic.class.getName()).log(Level.SEVERE, "Error while adding comic", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
