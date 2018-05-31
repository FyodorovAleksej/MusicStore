package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.TrackReceiver;
import by.fyodorov.musicstore.view.TrackWithoutPriceView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;

/**
 * getting list of user's tracks
 */
public class ListOwnTrackCommand implements Command {
    private TrackReceiver trackReceiver;

    /**
     * creating command with track receiver
     * @param trackReceiver - receiver for working with tracks
     */
    public ListOwnTrackCommand(TrackReceiver trackReceiver) {
        this.trackReceiver = trackReceiver;
    }

    /**
     * performing command. Getting list of user's tracks
     * @param requestInfo - map with arguments of request and session
     * @return - command of forward or redirect
     * @throws CommandException - when command can't perform
     */
    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        String name = (String) requestInfo.getSessionAttribute(RequestArgument.SESSION_LOGIN.getName());
        LinkedList<TrackWithoutPriceView> tracks;
        try {
            tracks = trackReceiver.findTracksForUser(name);
            if (tracks.isEmpty()) {
                requestInfo.setRequestParameter(RequestArgument.TRACK_FIND_RESULT.getName(), "nothing to find");
            } else {
                requestInfo.setRequestAttribute(RequestArgument.TRACK_OWN_LIST.getName(), tracks);
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
