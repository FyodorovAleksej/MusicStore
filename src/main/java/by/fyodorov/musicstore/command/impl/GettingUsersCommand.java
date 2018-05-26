package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.UserReceiver;
import by.fyodorov.musicstore.view.TrackView;
import by.fyodorov.musicstore.view.UserView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;

public class GettingUsersCommand implements Command {
    private UserReceiver receiver;

    public GettingUsersCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        LinkedList<UserView> users;
        try {
            users = receiver.findAllUsers();
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
