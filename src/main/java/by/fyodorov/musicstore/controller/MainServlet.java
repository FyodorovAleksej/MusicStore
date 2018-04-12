package by.fyodorov.musicstore.controller;

import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.command.commandimpl.CommandCreator;
import by.fyodorov.musicstore.connector.ConnectionPool;
import by.fyodorov.musicstore.connector.ConnectorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(MainServlet.class);


    @Override
    public void init() throws ServletException {
        ContextParameter.getInstance().addContextParam(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("GET");
        doOperation(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.debug("POST");
        doOperation(request, response);
    }

    @Override
    public void destroy() {
        LOGGER.debug("DESTROY MainServlet");
        try {
            ConnectionPool.getInstance(null).destroy();
        }
        catch (ConnectorException e) {
            LOGGER.catching(e);
        }
    }

    private void doOperation(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommandCreator creator = new CommandCreator();
        try {
            String commandName = (String)request.getAttribute(RequestArgument.COMMAND_NAME.getName());
            LOGGER.debug("command = " + commandName);
            Command command = creator.createCommand(commandName);
            RequestParameterMap map = new RequestParameterMap(request);
            GoToInterface jump = command.perform(map);
            map.refresh(request);
            jump.goTo(request, response);
        }
        catch (CommandException e) {
            LOGGER.catching(e);
            response.setStatus(512);
        }
    }
}
