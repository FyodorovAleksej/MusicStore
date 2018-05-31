package by.fyodorov.musicstore.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * class of forward perform for servlet
 */
public class ForwardGoTo implements GoToInterface {
    private static final Logger LOGGER = LogManager.getLogger(ForwardGoTo.class);
    private String path;

    /**
     * create forward command with path
     * @param path - path of page to forward
     */
    public ForwardGoTo(String path) {
        this.path = path;
    }

    /**
     * perform forward to this path
     * @param request - request of servlet
     * @param response - response of servlet
     * @throws ServletException - when can't forward
     * @throws IOException - when can't forward
     */
    @Override
    public void goTo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.debug("goto = \"" + request.getServletContext().getContextPath() + path + "\"");
        request.getRequestDispatcher(request.getServletContext().getContextPath() + path).forward(request, response);
    }
}
