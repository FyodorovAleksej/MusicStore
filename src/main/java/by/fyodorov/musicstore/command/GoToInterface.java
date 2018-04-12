package by.fyodorov.musicstore.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface GoToInterface {

    void goTo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
