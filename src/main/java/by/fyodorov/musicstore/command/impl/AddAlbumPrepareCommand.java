package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.model.UserRole;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.PerformerReceiver;
import by.fyodorov.musicstore.receiver.TrackReceiver;
import by.fyodorov.musicstore.view.TrackView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.Optional;

/**
 * prepare information for adding new album
 */
public class AddAlbumPrepareCommand implements Command {
    private PerformerReceiver performerReceiver;
    private TrackReceiver trackReceiver;

    /**
     * creating command with track and performer receivers
     * @param trackReceiver - receiver for work with tracks
     * @param performerReceiver - receiver for working with performers
     */
    AddAlbumPrepareCommand(TrackReceiver trackReceiver, PerformerReceiver performerReceiver) {
        this.trackReceiver = trackReceiver;
        this.performerReceiver = performerReceiver;
    }

    /**
     * performing command. Prepare information about tracks, performers for adding album
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
                LinkedList<TrackView> tracks = trackReceiver.findTrackInfo(name);
                if (!tracks.isEmpty()) {
                    requestInfo.setRequestAttribute(RequestArgument.TRACK_SEARCH_LIST.getName(), tracks);
                }
                path = Optional.of(PagesUrl.ALBUM_ADD_PAGE.getPath());
            }
        } catch (ConnectorException e) {
            throw new CommandException("can't connect to find tracks|performers", e);
        }
        return new ForwardGoTo(path.orElse(PagesUrl.MAIN_PAGE.getPath()));
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
