package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.model.UserRole;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.TrackReceiver;
import by.fyodorov.musicstore.validator.RequestParameterValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * editing track
 */
public class EditTrackCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(EditAlbumCommand.class);
    private static final String SEPARATOR = ",";
    private TrackReceiver trackReceiver;

    /**
     * creating command with track receiver
     * @param trackReceiver - receiver for working with tracks
     */
    public EditTrackCommand(TrackReceiver trackReceiver) {
        this.trackReceiver = trackReceiver;
    }

    /**
     * performing command. Edit name, price, performer of track
     * @param requestInfo - map with arguments of request and session
     * @return - command of forward or redirect
     * @throws CommandException - when command can't perform
     */
    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        String userRole = (String) requestInfo.getSessionAttribute(RequestArgument.SESSION_ROLE.getName());
        String oldTrack = (String) requestInfo.getSessionAttribute(RequestArgument.TRACK_NAME.getName());
        String trackName = requestInfo.getParameter(RequestArgument.TRACK_EDIT_NAME.getName());
        String[] trackGenre = requestInfo.getRequestMultipleAttribute(RequestArgument.TRACK_EDIT_GENRE.getName());
        String trackPrice = requestInfo.getParameter(RequestArgument.TRACK_EDIT_PRICE.getName());
        String trackPerformer = requestInfo.getParameter(RequestArgument.TRACK_EDIT_PERFORMER.getName());
        RequestParameterValidator validator = new RequestParameterValidator();
        try {
            if (UserRole.ADMIN.toString().equals(userRole) && oldTrack != null && trackName != null && trackGenre != null && validator.validatePrice(trackPrice) && trackPerformer != null) {
                if (trackReceiver.editTrack(oldTrack, trackName, String.join(SEPARATOR, trackGenre), Integer.valueOf(trackPrice), trackPerformer)) {
                    LOGGER.debug("edit track successfully");
                } else {
                    LOGGER.debug("edit track failed");
                }
            }
        } catch (ConnectorException e) {
            throw new CommandException("Can't edit track - " + trackName, e);
        }
        return new RedirectGoTo(PagesUrl.ALL_TRACKS_PAGE.getPath());
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
