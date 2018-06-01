package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.model.UserRole;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.model.GenreType;
import by.fyodorov.musicstore.model.PerformerEntity;
import by.fyodorov.musicstore.model.TrackEntity;
import by.fyodorov.musicstore.receiver.PerformerReceiver;
import by.fyodorov.musicstore.receiver.TrackReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.Optional;

/**
 * getting information for edit track
 */
public class EditTrackPrepareCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(EditTrackPrepareCommand.class);
    private TrackReceiver trackReceiver;
    private PerformerReceiver performerReceiver;

    /**
     * create command with track and performer receivers
     * @param trackReceiver - receiver for working with tracks
     * @param performerReceiver - receiver for working with performers
     */
    public EditTrackPrepareCommand(TrackReceiver trackReceiver, PerformerReceiver performerReceiver) {
        this.trackReceiver = trackReceiver;
        this.performerReceiver = performerReceiver;
    }

    /**
     * performing command. Prepare information about track for editing
     * @param requestInfo - map with arguments of request and session
     * @return - command of forward or redirect
     * @throws CommandException - when command can't perform
     */
    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        Optional<String> path = Optional.empty();
        String userRole = (String) requestInfo.getSessionAttribute(RequestArgument.SESSION_ROLE.getName());
        String name = (String) requestInfo.getSessionAttribute(RequestArgument.SESSION_LOGIN.getName());
        String track = (String) requestInfo.getSessionAttribute(RequestArgument.TRACK_NAME.getName());
        try {
            if (UserRole.ADMIN.toString().equals(userRole) && name != null && track != null) {
                LinkedList<String> performers = performerReceiver.findAllPerformers();
                requestInfo.setRequestAttribute(RequestArgument.PERFORMER_LIST.getName(), performers);

                Optional<TrackEntity> trackEntity = trackReceiver.findTrack(track);
                if (trackEntity.isPresent()) {
                    Optional<PerformerEntity> performer = performerReceiver.findPerformerId(trackEntity.get().getPerformerId());
                    if (performer.isPresent()) {
                        requestInfo.setRequestAttribute(RequestArgument.TRACK_EDIT_OLD_NAME.getName(), trackEntity.get().getName());
                        requestInfo.setRequestAttribute(RequestArgument.TRACK_EDIT_OLD_CLASSIC_GENRE.getName(), decodeGenre(trackEntity.get().getGenre(), GenreType.CLASSIC_GENRE.getValue()));
                        requestInfo.setRequestAttribute(RequestArgument.TRACK_EDIT_OLD_ELECTRO_GENRE.getName(), decodeGenre(trackEntity.get().getGenre(), GenreType.ELECTRO_GENRE.getValue()));
                        requestInfo.setRequestAttribute(RequestArgument.TRACK_EDIT_OLD_POP_GENRE.getName(), decodeGenre(trackEntity.get().getGenre(), GenreType.POP_GENRE.getValue()));
                        requestInfo.setRequestAttribute(RequestArgument.TRACK_EDIT_OLD_ROCK_GENRE.getName(), decodeGenre(trackEntity.get().getGenre(), GenreType.ROCK_GENRE.getValue()));
                        requestInfo.setRequestAttribute(RequestArgument.TRACK_EDIT_OLD_JAZZ_GENRE.getName(), decodeGenre(trackEntity.get().getGenre(), GenreType.JAZZ_GENRE.getValue()));
                        requestInfo.setRequestAttribute(RequestArgument.TRACK_EDIT_OLD_BLUES_GENRE.getName(), decodeGenre(trackEntity.get().getGenre(), GenreType.BLUES_GENRE.getValue()));
                        requestInfo.setRequestAttribute(RequestArgument.TRACK_EDIT_OLD_PRICE.getName(), trackEntity.get().getPrice());
                        requestInfo.setRequestAttribute(RequestArgument.TRACK_EDIT_OLD_PERFORMER.getName(), performer.get().getName());
                    }
                }
                path = Optional.of(PagesUrl.TRACK_EDIT_PAGE.getPath());
            }
        } catch (ConnectorException e) {
            throw new CommandException("can't connect to find track|performer", e);
        }
        return new ForwardGoTo(path.orElse(PagesUrl.MAIN_PAGE.getPath()));
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }

    private String decodeGenre(String genres, int code) {
        if ((GenreType.toGenreType(genres) & code) != 0) {
            return "checked";
        }
        return "";
    }
}
