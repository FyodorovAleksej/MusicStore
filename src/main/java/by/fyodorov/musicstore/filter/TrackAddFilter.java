package by.fyodorov.musicstore.filter;

import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.model.UserRole;
import by.fyodorov.musicstore.command.impl.CommandType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

import static by.fyodorov.musicstore.application.RequestArgument.FILE_KEY;

public class TrackAddFilter implements Filter {
    private static final String COMMAND_NAME = CommandType.TRACK_ADD_COMMAND.toString();
    private static final String INIT_INDEX = "index";
    private String index;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        index = filterConfig.getInitParameter(INIT_INDEX);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Part filePart = request.getPart(FILE_KEY.getName());
        request.setAttribute(FILE_KEY.getName(), filePart);
        String role = (String) request.getSession().getAttribute(RequestArgument.SESSION_ROLE.getName());
        if (role == null || UserRole.GUEST.toString().equals(role) || UserRole.USER.toString().equals(role)) {
            response.sendRedirect(request.getContextPath() + index);
            return;
        }
        servletRequest.setAttribute(RequestArgument.COMMAND_NAME.getName(), COMMAND_NAME);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
