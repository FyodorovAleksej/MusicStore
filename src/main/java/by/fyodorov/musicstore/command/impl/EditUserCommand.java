package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.model.UserRole;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.UserReceiver;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * edit user
 */
public class EditUserCommand implements Command {
    private UserReceiver userReceiver;
    private static final String SEPARATOR = ",";

    /**
     * creating command with user receiver
     * @param userReceiver - receiver for working with users
     */
    public EditUserCommand(UserReceiver userReceiver) {
        this.userReceiver = userReceiver;
    }

    /**
     * performing command. Edit role, bonuses and discount for selected user
     * @param requestInfo - map with arguments of request and session
     * @return - command of forward or redirect
     * @throws CommandException - when command can't perform
     */
    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        Optional<String> path = Optional.empty();
        String userRole = (String) requestInfo.getSessionAttribute(RequestArgument.SESSION_ROLE.getName());
        String infoName = (String) requestInfo.getSessionAttribute(RequestArgument.USER_INFO.getName());
        String infoRole = requestInfo.getParameter(RequestArgument.USER_ROLE_INFO.getName());
        String discount = requestInfo.getParameter(RequestArgument.USER_DISCOUNT_INFO.getName());
        String[] bonuses = requestInfo.getRequestMultipleAttribute(RequestArgument.USER_BONUS_INFO.getName());
        try {
            if (UserRole.ADMIN.toString().equals(userRole) && infoName != null && infoRole != null && discount != null && bonuses != null) {
                userReceiver.updateUser(infoName, infoRole, String.join(SEPARATOR, bonuses), Integer.valueOf(discount));
                path = Optional.of(requestInfo.getContextPath() + PagesUrl.USER_INFO_WITH_ARG_PAGE.getPath() + infoName);
            }
        } catch (ConnectorException e) {
            throw new CommandException("can't connect to edit user", e);
        }
        return new RedirectGoTo(path.orElse(PagesUrl.MAIN_PAGE.getPath()));
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
