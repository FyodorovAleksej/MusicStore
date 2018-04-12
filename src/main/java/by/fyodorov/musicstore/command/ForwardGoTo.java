package by.fyodorov.musicstore.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ForwardGoTo implements GoToInterface {
    private static final Logger LOGGER = LogManager.getLogger(ForwardGoTo.class);
    private String path;

    public ForwardGoTo(String path) {
        this.path = path;
    }

    @Override
    public void goTo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.debug("goto = \"" + request.getServletContext().getContextPath() + path + "\"");
        request.getRequestDispatcher(request.getServletContext().getContextPath() + path).forward(request, response);
    }
}
