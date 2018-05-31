package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.UserReceiver;
import by.fyodorov.musicstore.view.UserView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;

/**
 * getting list of all users
 */
public class ListUsersCommand implements Command {
    private UserReceiver userReceiver;

    /**
     * creating command with user userReceiver
     * @param userReceiver - receiver for working with users
     */
    public ListUsersCommand(UserReceiver userReceiver) {
        this.userReceiver = userReceiver;
    }

    /**
     * performing command. Getting list of users
     * @param requestInfo - map with arguments of request and session
     * @return - command of forward or redirect
     * @throws CommandException - when command can't perform
     */
    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        LinkedList<UserView> users;
        try {
            users = userReceiver.findAllUsers();
            requestInfo.setRequestAttribute(RequestArgument.USER_ADMIN_LIST.getName(), users);

        } catch (ConnectorException e) {
            throw new CommandException("can't getting from user database", e);
        }
        return new ForwardGoTo(PagesUrl.USER_ADMIN_PAGE.getPath());
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
