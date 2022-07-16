package org.jcomi;

import org.jcomi.controller.ControllerServlet;
import org.jcomi.util.ThreadUtil;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

@WebServlet(name = "database-connection-test", value = "/debug/database/connection/test")
public class DBConnectionTest extends ControllerServlet {
    @Override
    protected void doGet(@SuppressWarnings("unused") HttpServletRequest request, HttpServletResponse response) {
        int retries = 0;
        while (retries++ < 10) {
            try (Connection ignored = getClient().getConnection()) {
                response.setHeader("connection-retries", String.valueOf(retries));
                response.setStatus(HttpServletResponse.SC_OK);
                return;
            } catch (SQLException e) {
                Logger.getLogger(DBConnectionTest.class.getName()).warning("Connection failed: " + e.getMessage());
                ThreadUtil.sleepSafe(100);
            }
        }
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }
}
