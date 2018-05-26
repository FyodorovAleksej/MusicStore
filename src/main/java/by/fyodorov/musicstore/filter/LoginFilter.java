package by.fyodorov.musicstore.filter;

import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.impl.CommandType;

import javax.servlet.*;
import java.io.IOException;

public class LoginFilter implements Filter {
    private static final String COMMAND_NAME = CommandType.LOGIN_COMMAND.toString();

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setAttribute(RequestArgument.COMMAND_NAME.getName(), COMMAND_NAME);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
