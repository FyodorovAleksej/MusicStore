package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.model.PerformerEntity;
import by.fyodorov.musicstore.model.TrackEntity;
import by.fyodorov.musicstore.receiver.PerformerReceiver;
import by.fyodorov.musicstore.receiver.TrackReceiver;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * getting track information about selected track
 */
public class InfoTrackCommand implements Command {
    private TrackReceiver trackReceiver;
    private PerformerReceiver performerReceiver;

    /**
     * creating command with track and performer receivers
     * @param trackReceiver - receiver for working with tracks
     * @param performerReceiver - receiver for working with performers
     */
    InfoTrackCommand(TrackReceiver trackReceiver, PerformerReceiver performerReceiver) {
        this.trackReceiver = trackReceiver;
        this.performerReceiver = performerReceiver;
    }

    /**
     * performing command. Getting information about selected track
     * @param requestInfo - map with arguments of request and session
     * @return - command of forward or redirect
     * @throws CommandException - when command can't perform
     */
    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        Optional<String> path = Optional.empty();
        String trackName = requestInfo.getParameter(RequestArgument.TRACK_NAME_INFO.getName());
        try {
            Optional<TrackEntity> trackEntity = trackReceiver.findTrack(trackName);
            if (trackEntity.isPresent()) {
                Optional<PerformerEntity> performer = performerReceiver.findPerformerId(trackEntity.get().getPerformerId());
                if (performer.isPresent()) {
                    requestInfo.setSessionAttribute(RequestArgument.TRACK_NAME.getName(), trackName);
                    requestInfo.setRequestAttribute(RequestArgument.TRACK_INFO_NAME.getName(), trackEntity.get().getName());
                    requestInfo.setRequestAttribute(RequestArgument.TRACK_INFO_GENRE.getName(), trackEntity.get().getGenre());
                    requestInfo.setRequestAttribute(RequestArgument.TRACK_INFO_PRICE.getName(), trackEntity.get().getPrice());
                    requestInfo.setRequestAttribute(RequestArgument.TRACK_INFO_DATE.getName(), trackEntity.get().getDate());

                    requestInfo.setRequestAttribute(RequestArgument.TRACK_INFO_COMMENT_LIST.getName(), trackReceiver.findComments(trackEntity.get().getName()));

                    requestInfo.setRequestAttribute(RequestArgument.PERFORMER_INFO_NAME.getName(), performer.get().getName());
                }
            }
        } catch (ConnectorException e) {
            throw new CommandException("can't connect to find track", e);
        }
        path = Optional.of(PagesUrl.TRACK_INFO_PAGE.getPath());
        return new ForwardGoTo(path.orElse(PagesUrl.MAIN_PAGE.getPath()));
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
