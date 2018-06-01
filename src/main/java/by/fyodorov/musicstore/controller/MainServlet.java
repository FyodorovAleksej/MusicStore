package by.fyodorov.musicstore.controller;

import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.Command;
import by.fyodorov.musicstore.command.CommandException;
import by.fyodorov.musicstore.command.GoToInterface;
import by.fyodorov.musicstore.command.RequestParameterMap;
import by.fyodorov.musicstore.command.impl.CommandCreator;
import by.fyodorov.musicstore.connector.ConnectionPool;
import by.fyodorov.musicstore.connector.ConnectorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * main servlet of web application
 */
public class MainServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(MainServlet.class);

    /**
     * initialize servlet. Save context parameters to map
     */
    @Override
    public void init() {
        ContextParameter.getInstance().addContextParam(getServletContext());
    }

    /**
     * perform GET request
     * @param request - servlet request
     * @param response - servlet response
     * @throws ServletException - can't forward or redirect
     * @throws IOException - can't forward
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("GET");
        doOperation(request, response);
    }

    /**
     * perform POST request
     * @param request - servlet request
     * @param response - servlet response
     * @throws ServletException - can't forward or redirect
     * @throws IOException - can't forward
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.debug("POST");
        doOperation(request, response);
    }

    /**
     * destroy servlet. Shutdown connection pool
     */
    @Override
    public void destroy() {
        LOGGER.debug("DESTROY MainServlet");
        try {
            ConnectionPool.getInstance(null).destroy();
        } catch (ConnectorException e) {
            LOGGER.catching(e);
        }
    }

    /**
     * getting command from request (that was set by filter) and perform it
     * @param request - servlet request
     * @param response - servlet response
     * @throws ServletException - can't forward or redirect
     * @throws IOException - can't forward
     */
    private void doOperation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommandCreator creator = new CommandCreator();
        try {
            String commandName = (String) request.getAttribute(RequestArgument.COMMAND_NAME.getName());
            LOGGER.debug("command = " + commandName);
            Command command = creator.createCommand(commandName);
            RequestParameterMap map = new RequestParameterMap(request);
            GoToInterface jump = command.perform(map);
            map.refresh(request);
            jump.goTo(request, response);
        } catch (CommandException e) {
            LOGGER.catching(e);
            response.setStatus(500);
        }
    }
}
