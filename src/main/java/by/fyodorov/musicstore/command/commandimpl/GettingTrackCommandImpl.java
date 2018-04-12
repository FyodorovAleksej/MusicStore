package by.fyodorov.musicstore.command.commandimpl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.model.TrackEntity;
import by.fyodorov.musicstore.receiver.TrackReceiver;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;

public class GettingTrackCommandImpl implements Command {
    private TrackReceiver receiver;

    public GettingTrackCommandImpl(TrackReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        String name = requestInfo.getParameter(RequestArgument.TRACK_NAME.getName());
        LinkedList<TrackEntity> tracks;
        try {
            tracks = receiver.findAllTracks(name);
            if (tracks.isEmpty()) {
                requestInfo.setRequestParameter(RequestArgument.TRACK_FIND_RESULT.getName(), "nothing to find");
            }
            else {
                requestInfo.setRequestAttribute(RequestArgument.TRACK_LIST.getName(), tracks);
            }
        } catch (ConnectorException e) {
            throw new CommandException("can't getting from track database", e);
        }
        return new ForwardGoTo(PagesUrl.MAIN_PAGE.getPath());
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
