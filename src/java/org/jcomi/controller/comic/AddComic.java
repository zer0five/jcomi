package org.jcomi.controller.comic;

import org.jcomi.controller.ControllerServlet;
import org.jcomi.entity.account.Account;
import org.jcomi.entity.comic.Comic;
import org.jcomi.entity.comic.ComicDataAccess;
import org.jcomi.entity.comic.genre.ComicGenre;
import org.jcomi.entity.comic.genre.ComicGenreDataAccess;
import org.jcomi.entity.genre.Genre;
import org.jcomi.entity.genre.GenreDataAccess;

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
@MultipartConfig
public class AddComic extends ControllerServlet {

    private String uploadCover(Part coverImage, Comic comic) {
        try {
            Path virtualPath = Paths.get(getServletConfig().getInitParameter("uploadPath"), String.valueOf(comic.getId()));
            Path realPath = Paths.get(getServletContext().getRealPath(""), virtualPath.toString());
            Files.createDirectories(realPath); // create directory if not exists
            String fileName = "cover.webp"; // coverImage.getSubmittedFileName();
            Path filePath = Paths.get(realPath.toString(), fileName); // realPath + fileName
            Files.deleteIfExists(filePath); // delete old cover image if exists
            Files.copy(coverImage.getInputStream(), filePath); // upload file
            return Paths.get(virtualPath.toString(), fileName).toString(); // virtualPath + fileName
        } catch (IOException e) {
            Logger.getLogger(AddComic.class.getName()).log(Level.SEVERE, "Error when upload cover image", e);
            return "https://via.placeholder.com/190x247?text=Cover";
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher("/comic/add.jsp").forward(request, response);
        } catch (Exception e) {
            Logger.getLogger(AddComic.class.getName()).log(Level.SEVERE, "Error when add comic", e);
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
            request.getParameterMap().forEach((key, value) -> {
                for (String v : value) {
                    System.out.println(key + ": " + v);
                }
            });
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
            ComicDataAccess dataAccess = new ComicDataAccess();
            dataAccess.insertAndGetIdentifier(comic);

            if (cover.isPresent()) {
                String url = uploadCover(cover.get(), comic);
                comic.setCover(url);
            }
            Optional<String[]> genres = Optional.ofNullable(request.getParameterValues("genres[]")).filter(s -> s.length > 0);

            GenreDataAccess genreDataAccess = new GenreDataAccess();
            ComicGenreDataAccess comicGenreDataAccess = new ComicGenreDataAccess();
            if (genres.isPresent()) {
                for (String genre : genres.get()) {
                    Optional<Genre> genreEntity = genreDataAccess.getOne(genre);
                    if (genreEntity.isPresent()) {
                        ComicGenre comicGenre = new ComicGenre(comic, genreEntity.get());
                        comicGenreDataAccess.insert(comicGenre);
                    }
                }
            }
            response.getWriter().print("{\"status\": \"success\", \"id\": " + comic.getId() + "}");
//            response.sendRedirect(request.getContextPath() + "/comic/information" + "?id=" + comic.getId());
        } catch (Exception e) {
            Logger.getLogger(AddComic.class.getName()).log(Level.SEVERE, "Error while adding comic", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

}
