package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.TrackReceiver;
import by.fyodorov.musicstore.view.TrackView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;

public class GettingTrackCommand implements Command {
    private TrackReceiver receiver;

    public GettingTrackCommand(TrackReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        String name = (String) requestInfo.getSessionAttribute(RequestArgument.SESSION_LOGIN.getName());
        LinkedList<TrackView> tracks;
        try {
            tracks = receiver.findTrackInfo(name);
            if (tracks.isEmpty()) {
                requestInfo.setRequestParameter(RequestArgument.TRACK_FIND_RESULT.getName(), "nothing to find");
            }
            else {
                requestInfo.setRequestAttribute(RequestArgument.TRACK_SEARCH_LIST.getName(), tracks);
            }
        } catch (ConnectorException e) {
            throw new CommandException("can't getting from track database", e);
        }
        return new ForwardGoTo(PagesUrl.TRACK_SEARCH_PAGE.getPath());
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
