package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.model.UserRole;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.model.GenreType;
import by.fyodorov.musicstore.receiver.AlbumReceiver;
import by.fyodorov.musicstore.receiver.PerformerReceiver;
import by.fyodorov.musicstore.receiver.TrackReceiver;
import by.fyodorov.musicstore.view.AlbumView;
import by.fyodorov.musicstore.view.TrackWithoutPriceView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.Optional;

/**
 * preparing information for editing album
 */
public class EditAlbumPrepareCommand implements Command {
    private AlbumReceiver albumReceiver;
    private PerformerReceiver performerReceiver;
    private TrackReceiver trackReceiver;

    /**
     * create command with album, performer and track receivers
     * @param trackReceiver - receiver for working with tracks
     * @param performerReceiver - receiver for working with performers
     * @param albumReceiver - receiver for working with albums
     */
    EditAlbumPrepareCommand(TrackReceiver trackReceiver, PerformerReceiver performerReceiver, AlbumReceiver albumReceiver) {
        this.albumReceiver = albumReceiver;
        this.trackReceiver = trackReceiver;
        this.performerReceiver = performerReceiver;
    }

    /**
     * performing command. Prepare information about album for editing
     * @param requestInfo - map with arguments of request and session
     * @return - command of forward or redirect
     * @throws CommandException - when command can't perform
     */
    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        Optional<String> path = Optional.empty();
        String userRole = (String) requestInfo.getSessionAttribute(RequestArgument.SESSION_ROLE.getName());
        String name = (String) requestInfo.getSessionAttribute(RequestArgument.SESSION_LOGIN.getName());
        String album = (String) requestInfo.getSessionAttribute(RequestArgument.ALBUM_SESSION_NAME.getName());
        try {
            if (UserRole.ADMIN.toString().equals(userRole) && name != null) {
                LinkedList<String> performers = performerReceiver.findAllPerformers();
                requestInfo.setRequestAttribute(RequestArgument.PERFORMER_LIST.getName(), performers);

                Optional<AlbumView> albumInfo = albumReceiver.albumInfoForUser(album, name);
                if (albumInfo.isPresent()) {
                    requestInfo.setRequestAttribute(RequestArgument.ALBUM_EDIT_OLD_NAME.getName(), albumInfo.get().getName());
                    requestInfo.setRequestAttribute(RequestArgument.ALBUM_EDIT_OLD_CLASSIC_GENRE.getName(), decodeGenre(albumInfo.get().getGenre(), GenreType.CLASSIC_GENRE.getValue()));
                    requestInfo.setRequestAttribute(RequestArgument.ALBUM_EDIT_OLD_ELECTRO_GENRE.getName(), decodeGenre(albumInfo.get().getGenre(), GenreType.ELECTRO_GENRE.getValue()));
                    requestInfo.setRequestAttribute(RequestArgument.ALBUM_EDIT_OLD_POP_GENRE.getName(), decodeGenre(albumInfo.get().getGenre(), GenreType.POP_GENRE.getValue()));
                    requestInfo.setRequestAttribute(RequestArgument.ALBUM_EDIT_OLD_ROCK_GENRE.getName(), decodeGenre(albumInfo.get().getGenre(), GenreType.ROCK_GENRE.getValue()));
                    requestInfo.setRequestAttribute(RequestArgument.ALBUM_EDIT_OLD_JAZZ_GENRE.getName(), decodeGenre(albumInfo.get().getGenre(), GenreType.JAZZ_GENRE.getValue()));
                    requestInfo.setRequestAttribute(RequestArgument.ALBUM_EDIT_OLD_BLUES_GENRE.getName(), decodeGenre(albumInfo.get().getGenre(), GenreType.BLUES_GENRE.getValue()));
                    requestInfo.setRequestAttribute(RequestArgument.ALBUM_EDIT_OLD_PRICE.getName(), albumInfo.get().getPrice());
                    requestInfo.setRequestAttribute(RequestArgument.ALBUM_EDIT_OLD_PERFORMER.getName(), albumInfo.get().getPerformer());
                }
                LinkedList<TrackWithoutPriceView> tracks = trackReceiver.findTracksWithoutPrice();
                LinkedList<TrackWithoutPriceView> albumTracks = trackReceiver.findTracksInAlbum(album);
                tracks.removeAll(albumTracks);
                albumTracks.forEach((track) -> track.setCheck(true));
                albumTracks.addAll(tracks);
                if (!albumTracks.isEmpty()) {
                    requestInfo.setRequestAttribute(RequestArgument.TRACK_SEARCH_LIST.getName(), albumTracks);
                }
                path = Optional.of(PagesUrl.ALBUM_EDIT_PAGE.getPath());
            }
        } catch (ConnectorException e) {
            throw new CommandException("can't connect to find tracks|performers", e);
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
