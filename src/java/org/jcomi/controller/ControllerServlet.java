package org.jcomi.controller;

import me.kazoku.core.database.sql.client.JavaSQLClient;
import org.jcomi.util.DatabaseUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public abstract class ControllerServlet extends HttpServlet {
    private JavaSQLClient client;

    protected JavaSQLClient getClient() {
        if (client == null) {
            client = DatabaseUtil.createClient();
        }
        return client;
    }

    private void methodNotAllowed(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        } catch (IOException e) {
            Logger.getLogger(ControllerServlet.class.getName()).log(Level.SEVERE, "Error while handling request", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        methodNotAllowed(req, resp);
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        methodNotAllowed(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        methodNotAllowed(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        methodNotAllowed(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        methodNotAllowed(req, resp);
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        methodNotAllowed(req, resp);
    }

    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        methodNotAllowed(req, resp);
    }
}
