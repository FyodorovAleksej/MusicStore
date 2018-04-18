package by.fyodorov.musicstore.command.commandimpl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.TrackReceiver;
import by.fyodorov.musicstore.view.TrackWithoutPriceView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;

public class GettingOwnTrackCommandImpl implements Command {
    private TrackReceiver receiver;

    public GettingOwnTrackCommandImpl(TrackReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public GoToInterface perform(RequestParameterMap request) throws CommandException {
        String name = (String) request.getSessionAttribute(RequestArgument.SESSION_LOGIN.getName());
        LinkedList<TrackWithoutPriceView> tracks;
        try {
            tracks = receiver.findTracksForUser(name);
            if (tracks.isEmpty()) {
                request.setRequestParameter(RequestArgument.TRACK_FIND_RESULT.getName(), "nothing to find");
                return new RedirectGoTo(PagesUrl.MAIN_PAGE.getPath());
            }
            else {
                request.setRequestAttribute(RequestArgument.TRACK_OWN_LIST.getName(), tracks);
            }
        } catch (ConnectorException e) {
            throw new CommandException("can't getting from track database", e);
        }
        return new ForwardGoTo(PagesUrl.TRACK_OWN_VIEW_PAGE.getPath());
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
