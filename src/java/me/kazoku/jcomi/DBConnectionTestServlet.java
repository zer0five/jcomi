package me.kazoku.jcomi;

import me.kazoku.core.database.sql.client.JavaSQLClient;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Logger;
import me.kazoku.jcomi.util.DatabaseUtil;

import static me.kazoku.jcomi.util.ThreadUtil.sleep;

@WebServlet(name = "database-connection-test", value = "/database-connection-test")
public class DBConnectionTestServlet extends HttpServlet {

    private static JavaSQLClient client;

    @Override
    public void init() {
        this.client = DatabaseUtil.createClient();
    }

    private void testDatabaseConnection(PrintWriter writer) {
        int retry = 0;
        Optional<SQLException> ex = Optional.empty();
        while (retry++ < 10) {
            try (Connection ignored = client.getConnection()) {
                writer.write("{\"status\": \"success\", \"message\": \"Connection successful (retries: " + retry + ")\"}");
                return;
            } catch (SQLException e) {
                ex = Optional.of(e);
                sleep(100);
            }
        }
        writer.write("{\"status\": \"error\", \"message\": \"" + ex.map(Exception::getMessage).orElse("Could not connect to database") + "\"}");
    }

    private void doRequest(@SuppressWarnings("unused") HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        try (PrintWriter writer = response.getWriter()) {
            testDatabaseConnection(writer);
        } catch (IOException e) {
            Logger.getLogger(DBConnectionTestServlet.class.getName()).severe(e.getMessage());
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doRequest(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doRequest(request, response);
    }

    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doRequest(request, response);
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doRequest(request, response);
    }

    public void doHead(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doRequest(request, response);
    }

    @Override
    public void doOptions(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doRequest(request, response);
    }

    @Override
    public void doTrace(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doRequest(request, response);
    }

    @Override
    public void destroy() {
        client = null;
    }
}
