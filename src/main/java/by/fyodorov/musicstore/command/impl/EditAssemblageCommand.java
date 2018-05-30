package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.application.UserRole;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.AssemblageReceiver;
import by.fyodorov.musicstore.validator.RequestParameterValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class EditAssemblageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(EditAssemblageCommand.class);
    private static final String SEPARATOR = ",";
    private AssemblageReceiver receiver;

    public EditAssemblageCommand(AssemblageReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public GoToInterface perform(RequestParameterMap request) throws CommandException {
        String userRole = (String) request.getSessionAttribute(RequestArgument.SESSION_ROLE.getName());
        String oldAssemblage = (String) request.getSessionAttribute(RequestArgument.ASSEMBLAGE_SESSION_NAME.getName());
        String assemblageName = request.getParameter(RequestArgument.ASSEMBLAGE_EDIT_NAME.getName());
        String[] assemblageGenre = request.getRequestMultipleAttribute(RequestArgument.ASSEMBLAGE_EDIT_GENRE.getName());
        String assemblagePrice = request.getParameter(RequestArgument.ASSEMBLAGE_EDIT_PRICE.getName());
        String[] tracks = request.getRequestMultipleAttribute(RequestArgument.ASSEMBLAGE_EDIT_TRACKS.getName());
        RequestParameterValidator validator = new RequestParameterValidator();
        try {
            if (UserRole.ADMIN.toString().equals(userRole) && oldAssemblage != null && assemblageName != null && assemblageGenre != null && validator.validatePrice(assemblagePrice) && tracks != null) {
                if (receiver.editAssemblage(oldAssemblage, assemblageName, String.join(SEPARATOR, assemblageGenre), Integer.valueOf(assemblagePrice), tracks)) {
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