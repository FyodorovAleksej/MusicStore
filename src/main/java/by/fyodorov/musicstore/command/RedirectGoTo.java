package by.fyodorov.musicstore.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * class of redirect perform for servlet
 */
public class RedirectGoTo implements GoToInterface {
    private String path;

    /**
     * create redirect command with path
     * @param path - path of page to redirect
     */
    public RedirectGoTo(String path) {
        this.path = path;
    }

    /**
     * perform redirect to this path
     * @param request - request of servlet
     * @param response - response of servlet
     * @throws IOException - when can't redirect
     */
    @Override
    public void goTo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(path);
    }
}
