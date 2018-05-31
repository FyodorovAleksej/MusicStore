package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.model.UserRole;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.PerformerReceiver;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.Optional;

/**
 * prepare information for adding new track in system
 */
public class AddTrackPrepareCommand implements Command {
    private PerformerReceiver performerReceiver;

    /**
     * create command with performer receiver
     * @param performerReceiver - receiver for working with performers
     */
    AddTrackPrepareCommand(PerformerReceiver performerReceiver) {
        this.performerReceiver = performerReceiver;
    }

    /**
     * performing command. Prepare information about track for adding
     * @param requestInfo - map with arguments of request and session
     * @return - command of forward or redirect
     * @throws CommandException - when command can't perform
     */
    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        Optional<String> path = Optional.empty();
        String userRole = (String) requestInfo.getSessionAttribute(RequestArgument.SESSION_ROLE.getName());
        String name = (String) requestInfo.getSessionAttribute(RequestArgument.SESSION_LOGIN.getName());
        try {
            if (UserRole.ADMIN.toString().equals(userRole) && name != null) {
                LinkedList<String> performers = performerReceiver.findAllPerformers();
                requestInfo.setRequestAttribute(RequestArgument.PERFORMER_LIST.getName(), performers);
                path = Optional.of(PagesUrl.TRACK_ADD_PAGE.getPath());
            }
        } catch (ConnectorException e) {
            throw new CommandException("can't connect to find performers", e);
        }
        return new ForwardGoTo(path.orElse(PagesUrl.MAIN_PAGE.getPath()));
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
