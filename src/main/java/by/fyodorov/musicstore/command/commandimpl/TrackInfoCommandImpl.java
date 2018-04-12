package by.fyodorov.musicstore.command.commandimpl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.model.PerformerEntity;
import by.fyodorov.musicstore.model.TrackEntity;
import by.fyodorov.musicstore.receiver.PerformerReceiver;
import by.fyodorov.musicstore.receiver.TrackReceiver;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.Optional;

public class TrackInfoCommandImpl implements Command {
    private TrackReceiver trackReceiver;
    private PerformerReceiver performerReceiver;

    TrackInfoCommandImpl(TrackReceiver trackReceiver, PerformerReceiver performerReceiver) {
        this.trackReceiver = trackReceiver;
        this.performerReceiver = performerReceiver;
    }

    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        Optional<String> path = Optional.empty();
        String trackName = requestInfo.getParameter(RequestArgument.TRACK_NAME_INFO.getName());
        try {
            TrackEntity trackEntity = trackReceiver.findTrack(trackName);
            if (trackEntity != null) {
                PerformerEntity performer = performerReceiver.findPerformerId(trackEntity.getPerformerId());
                requestInfo.setRequestAttribute(RequestArgument.TRACK_INFO_NAME.getName(), trackEntity.getName());
                requestInfo.setRequestAttribute(RequestArgument.TRACK_INFO_GENRE.getName(), trackEntity.getGenre());
                requestInfo.setRequestAttribute(RequestArgument.TRACK_INFO_PRICE.getName(), trackEntity.getPrice());
                requestInfo.setRequestAttribute(RequestArgument.TRACK_INFO_DATE.getName(), trackEntity.getDate());

                requestInfo.setRequestAttribute(RequestArgument.TRACK_INFO_COMMENT_LIST.getName(), trackReceiver.getComments(trackEntity.getName()));

                requestInfo.setRequestAttribute(RequestArgument.PERFORMER_INFO_NAME.getName(), performer.getName());
            }
        }
        catch (ConnectorException e) {
            throw new CommandException("can't connect to find track", e);
        }
        path = Optional.of(PagesUrl.TRACK_INFO_PAGE.getPath());
        return new ForwardGoTo(path.orElse(PagesUrl.MAIN_PAGE.getPath()));
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
