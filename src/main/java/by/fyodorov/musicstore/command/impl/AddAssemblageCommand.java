package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.AssemblageReceiver;
import by.fyodorov.musicstore.validator.RequestParameterValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * adding new assemblage in system
 */
public class AddAssemblageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddAssemblageCommand.class);
    private static final String SEPARATOR = ",";
    private AssemblageReceiver receiver;

    /**
     * create command with assemblage receiver
     * @param receiver - receiver for working with assemblages
     */
    public AddAssemblageCommand(AssemblageReceiver receiver) {
        this.receiver = receiver;
    }

    /**
     * performing command. Getting selected information about new assemblage and adding it in DB
     * @param request - request map from servlet
     * @return - command to forward or redirect
     * @throws CommandException - when command can't perform
     */
    @Override
    public GoToInterface perform(RequestParameterMap request) throws CommandException {
        String userName = (String) request.getSessionAttribute(RequestArgument.SESSION_LOGIN.getName());
        String assemblageName = request.getParameter(RequestArgument.ASSEMBLAGE_ADD_NAME.getName());
        String[] assemblageGenre = request.getRequestMultipleAttribute(RequestArgument.ASSEMBLAGE_ADD_GENRE.getName());
        String assemblagePrice = request.getParameter(RequestArgument.ASSEMBLAGE_ADD_PRICE.getName());
        String[] tracks = request.getRequestMultipleAttribute(RequestArgument.ASSEMBLAGE_ADD_TRACKS.getName());
        RequestParameterValidator validator = new RequestParameterValidator();
        try {
            if (userName != null && assemblageName != null && assemblageGenre != null && validator.validatePrice(assemblagePrice) && tracks != null) {
                if (receiver.addNewAssemblage(assemblageName, String.join(SEPARATOR, assemblageGenre), Integer.valueOf(assemblagePrice), userName, tracks)) {
                    LOGGER.debug("add assemblage successfully");
                } else {
                    LOGGER.debug("add assemblage failed");
                }
            }
        } catch (ConnectorException e) {
            throw new CommandException("Can't add assemblage - " + assemblageName, e);
        }
        return new RedirectGoTo(PagesUrl.ALL_ASSEMBLAGES_PAGE.getPath());
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
