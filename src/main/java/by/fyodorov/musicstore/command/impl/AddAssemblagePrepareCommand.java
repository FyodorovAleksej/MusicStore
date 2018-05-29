package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.application.UserRole;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.model.TrackEntity;
import by.fyodorov.musicstore.receiver.PerformerReceiver;
import by.fyodorov.musicstore.receiver.TrackReceiver;
import by.fyodorov.musicstore.view.TrackView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.Optional;

public class AddAssemblagePrepareCommand implements Command {
    private TrackReceiver trackReceiver;

    AddAssemblagePrepareCommand(TrackReceiver trackReceiver) {
        this.trackReceiver = trackReceiver;
    }

    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        Optional<String> path = Optional.empty();
        String userRole = (String) requestInfo.getSessionAttribute(RequestArgument.SESSION_ROLE.getName());
        String name = (String) requestInfo.getSessionAttribute(RequestArgument.SESSION_LOGIN.getName());
        try {
            if (UserRole.ADMIN.toString().equals(userRole) && name != null) {
                LinkedList<TrackView> tracks = trackReceiver.findTrackInfo(name);
                if (!tracks.isEmpty()) {
                    requestInfo.setRequestAttribute(RequestArgument.TRACK_SEARCH_LIST.getName(), tracks);
                }
                path = Optional.of(PagesUrl.ASSEMBLAGE_ADD_PAGE.getPath());
            }
        }
        catch (ConnectorException e) {
            throw new CommandException("can't connect to find tracks", e);
        }
        return new ForwardGoTo(path.orElse(PagesUrl.MAIN_PAGE.getPath()));
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
