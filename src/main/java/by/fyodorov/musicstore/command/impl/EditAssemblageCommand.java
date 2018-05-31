package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.model.UserRole;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.AssemblageReceiver;
import by.fyodorov.musicstore.validator.RequestParameterValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * editing assemblage
 */
public class EditAssemblageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(EditAssemblageCommand.class);
    private static final String SEPARATOR = ",";
    private AssemblageReceiver assemblageReceiver;

    /**
     * create command with assemblage receiver
     * @param assemblageReceiver - receiver for working with assemblages
     */
    public EditAssemblageCommand(AssemblageReceiver assemblageReceiver) {
        this.assemblageReceiver = assemblageReceiver;
    }

    /**
     * performing command. Edit name, genre, price and tracks of selected assemblage
     * @param requestInfo - map with arguments of request and session
     * @return - command of forward or redirect
     * @throws CommandException - when command can't perform
     */
    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        String userRole = (String) requestInfo.getSessionAttribute(RequestArgument.SESSION_ROLE.getName());
        String oldAssemblage = (String) requestInfo.getSessionAttribute(RequestArgument.ASSEMBLAGE_SESSION_NAME.getName());
        String assemblageName = requestInfo.getParameter(RequestArgument.ASSEMBLAGE_EDIT_NAME.getName());
        String[] assemblageGenre = requestInfo.getRequestMultipleAttribute(RequestArgument.ASSEMBLAGE_EDIT_GENRE.getName());
        String assemblagePrice = requestInfo.getParameter(RequestArgument.ASSEMBLAGE_EDIT_PRICE.getName());
        String[] tracks = requestInfo.getRequestMultipleAttribute(RequestArgument.ASSEMBLAGE_EDIT_TRACKS.getName());
        RequestParameterValidator validator = new RequestParameterValidator();
        try {
            if (UserRole.ADMIN.toString().equals(userRole) && oldAssemblage != null && assemblageName != null && assemblageGenre != null && validator.validatePrice(assemblagePrice) && tracks != null) {
                if (assemblageReceiver.editAssemblage(oldAssemblage, assemblageName, String.join(SEPARATOR, assemblageGenre), Integer.valueOf(assemblagePrice), tracks)) {
                    LOGGER.debug("edit assemblage successfully");
                } else {
                    LOGGER.debug("edit assemblage failed");
                }
            }
        } catch (ConnectorException e) {
            throw new CommandException("Can't edit assemblage - " + assemblageName, e);
        }
        return new RedirectGoTo(PagesUrl.ALL_ASSEMBLAGES_PAGE.getPath());
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}