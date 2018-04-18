package by.fyodorov.musicstore.filter;

import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.application.UserRole;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthorityFilter implements Filter {
    private static final String INIT_INDEX = "index";
    private String index;

    @Override
    public void init(FilterConfig filterConfig) {
        index = filterConfig.getInitParameter(INIT_INDEX);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String role = (String) request.getSession().getAttribute(RequestArgument.SESSION_ROLE.getName());
        if (role == null || UserRole.GUEST.getName().equals(role)) {
            response.sendRedirect(request.getContextPath() + index);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
