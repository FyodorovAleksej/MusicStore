package by.fyodorov.musicstore.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RedirectGoTo implements GoToInterface {
    private String path;

    public RedirectGoTo(String path) {
        this.path = path;
    }

    @Override
    public void goTo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(path);
    }
}
