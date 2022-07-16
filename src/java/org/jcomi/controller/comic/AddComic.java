package org.jcomi.controller.comic;

import org.jcomi.controller.ControllerServlet;
import org.jcomi.entity.account.Account;
import org.jcomi.entity.comic.Comic;
import org.jcomi.entity.comic.ComicDataAccess;

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
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "AddComic", value = "/comic/add",
    initParams = {
        @WebInitParam(name = "uploadPath", value = "/uploads/comic")
    }
)
@MultipartConfig(maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50) // 10MB, 50MB
public class AddComic extends ControllerServlet {

    private String uploadCover(Part coverImage, Comic comic) {
        try {
            Path virtualPath = Paths.get(getServletConfig().getInitParameter("uploadPath"), String.valueOf(comic.getId()));
            Path realPath = Paths.get(getServletContext().getRealPath(virtualPath.toString()));
            Files.createDirectories(realPath); // create directory if not exists
            String fileName = "cover.webp"; // coverImage.getSubmittedFileName();
            Path filePath = Paths.get(realPath.toString(), fileName); // realPath + fileName
            Files.copy(coverImage.getInputStream(), filePath); // upload file
            return Paths.get(virtualPath.toString(), fileName).toString(); // virtualPath + fileName
        } catch (IOException e) {
            Logger.getLogger(AddComic.class.getName()).log(Level.SEVERE, "Error when upload cover image", e);
            return "https://via.placeholder.com/190x247?text=Cover";
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        Logger.getLogger("AddComic").info("Upload path: " + getServletContext().getRealPath(getServletConfig().getInitParameter("uploadPath")));
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
            Optional<String> name = Optional.ofNullable(request.getParameter("name")).filter(s -> !s.isEmpty());
            Optional<String> description = Optional.ofNullable(request.getParameter("description")).filter(s -> !s.isEmpty());
            Optional<String> author = Optional.ofNullable(request.getParameter("author")).filter(s -> !s.isEmpty());
            Optional<String> altName = Optional.ofNullable(request.getParameter("alt-name")).filter(s -> !s.isEmpty());
            Optional<Part> cover = Optional.ofNullable(request.getPart("cover-image"));
            if (!name.isPresent()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            Comic comic = new Comic();
            comic.setName(name.get());
            description.ifPresent(comic::setDescription);
            author.ifPresent(comic::setAuthor);
            altName.ifPresent(comic::setAltName);
            comic.setUploaderId(account.getId());
            ComicDataAccess.getInstance().insertAndGetIdentifier(comic);
            if (cover.isPresent()) {
                comic.setCover(uploadCover(cover.get(), comic));
                ComicDataAccess.getInstance().update(comic);
            }
            response.sendRedirect(request.getContextPath() + "/comic/info" + "?id=" + comic.getId());
        } catch (Exception e) {
            Logger.getLogger(AddComic.class.getName()).log(Level.SEVERE, "Error while adding comic", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
