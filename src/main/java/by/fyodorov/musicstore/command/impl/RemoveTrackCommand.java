package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.model.UserRole;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.TrackReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * removing selected track from system
 */
public class RemoveTrackCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(RemoveAlbumCommand.class);
    private TrackReceiver trackReceiver;

    /**
     * created command with track receiver
     * @param trackReceiver - receiver for working with tracks
     */
    public RemoveTrackCommand(TrackReceiver trackReceiver) {
        this.trackReceiver = trackReceiver;
    }

    /**
     * performing command. Remove selected track from system
     * @param requestInfo - map with arguments of request and session
     * @return - command of forward or redirect
     * @throws CommandException - when command can't perform
     */
    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        String userRole = (String) requestInfo.getSessionAttribute(RequestArgument.SESSION_ROLE.getName());
        String trackName = (String) requestInfo.getSessionAttribute(RequestArgument.TRACK_NAME.getName());
        try {
            if (UserRole.ADMIN.toString().equals(userRole) && trackName != null) {
                if (trackReceiver.removeTrack(trackName)) {
                    LOGGER.debug("remove track successfully");
                } else {
                    LOGGER.debug("remove track failed");
                }
            }
        } catch (ConnectorException e) {
            throw new CommandException("Can't remove track - " + trackName, e);
        }
        return new RedirectGoTo(PagesUrl.ALL_TRACKS_PAGE.getPath());
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
