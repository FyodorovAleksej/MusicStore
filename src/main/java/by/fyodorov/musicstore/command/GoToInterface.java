package by.fyodorov.musicstore.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * interface of forward or redirect command for servlet
 */
public interface GoToInterface {

    /**
     * forward or redirect to page
     * @param request - request of servlet
     * @param response - response of servlet
     * @throws ServletException - when can't forward or redirect
     * @throws IOException - when can't forward or redirect
     */
    void goTo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
