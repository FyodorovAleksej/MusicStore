package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.AlbumReceiver;
import by.fyodorov.musicstore.receiver.TrackReceiver;
import by.fyodorov.musicstore.view.AlbumView;
import by.fyodorov.musicstore.view.TrackView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.Optional;

public class AlbumInfoCommand implements Command {
    private AlbumReceiver albumReceiver;
    private TrackReceiver trackReceiver;

    public AlbumInfoCommand (AlbumReceiver albumReceiver, TrackReceiver trackReceiver) {
        this.albumReceiver = albumReceiver;
        this.trackReceiver = trackReceiver;
    }

    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        Optional<String> path = Optional.empty();
        String albumName = requestInfo.getParameter(RequestArgument.ALBUM_NAME_FOR_INFO.getName());
        String userName = (String) requestInfo.getSessionAttribute(RequestArgument.SESSION_LOGIN.getName());
        try {
            Optional<AlbumView> albumInfo = albumReceiver.albumInfoForUser(albumName, userName);
            if (albumInfo.isPresent()) {
                requestInfo.setSessionAttribute(RequestArgument.ALBUM_SESSION_NAME.getName(), albumInfo.get().getName());
                requestInfo.setRequestAttribute(RequestArgument.ALBUM_INFO_NAME.getName(), albumInfo.get().getName());
                requestInfo.setRequestAttribute(RequestArgument.ALBUM_INFO_GENRE.getName(), albumInfo.get().getGenre());
                requestInfo.setRequestAttribute(RequestArgument.ALBUM_INFO_PRICE.getName(), albumInfo.get().getPrice());
                requestInfo.setRequestAttribute(RequestArgument.ALBUM_INFO_DATE.getName(), albumInfo.get().getDate());
                requestInfo.setRequestAttribute(RequestArgument.ALBUM_INFO_PERFORMER.getName(), albumInfo.get().getPerformer());
                requestInfo.setRequestAttribute(RequestArgument.ALBUM_INFO_SUMMARY.getName(), albumInfo.get().getSummary());
                LinkedList<TrackView> tracks = trackReceiver.findTracksInAlbum(albumName, userName);
                requestInfo.setRequestAttribute(RequestArgument.ALBUM_TRACKS_LIST.getName(), tracks);
                path = Optional.of(PagesUrl.ALBUM_INFO_PAGE.getPath());
            }
        }
        catch (ConnectorException e) {
            throw new CommandException("can't connect to find album", e);
        }
        return new ForwardGoTo(path.orElse(PagesUrl.MAIN_PAGE.getPath()));
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
