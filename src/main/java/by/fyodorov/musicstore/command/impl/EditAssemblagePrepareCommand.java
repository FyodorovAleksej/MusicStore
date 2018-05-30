package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.application.UserRole;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.model.GenreType;
import by.fyodorov.musicstore.receiver.AlbumReceiver;
import by.fyodorov.musicstore.receiver.AssemblageReceiver;
import by.fyodorov.musicstore.receiver.PerformerReceiver;
import by.fyodorov.musicstore.receiver.TrackReceiver;
import by.fyodorov.musicstore.view.AlbumView;
import by.fyodorov.musicstore.view.AssemblageView;
import by.fyodorov.musicstore.view.TrackView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.Optional;

public class EditAssemblagePrepareCommand implements Command {
    private AssemblageReceiver assemblageReceiver;
    private TrackReceiver trackReceiver;

    EditAssemblagePrepareCommand(TrackReceiver trackReceiver, AssemblageReceiver assemblageReceiver) {
        this.assemblageReceiver = assemblageReceiver;
        this.trackReceiver = trackReceiver;
    }

    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        Optional<String> path = Optional.empty();
        String userRole = (String) requestInfo.getSessionAttribute(RequestArgument.SESSION_ROLE.getName());
        String name = (String) requestInfo.getSessionAttribute(RequestArgument.SESSION_LOGIN.getName());
        String assemblage = (String) requestInfo.getSessionAttribute(RequestArgument.ASSEMBLAGE_SESSION_NAME.getName());
        try {
            if (UserRole.ADMIN.toString().equals(userRole) && name != null && assemblage != null) {
                Optional<AssemblageView> assemblageInfo = assemblageReceiver.assemblageInfoForUser(assemblage, name);
                if (assemblageInfo.isPresent()) {
                    requestInfo.setRequestAttribute(RequestArgument.ASSEMBLAGE_EDIT_OLD_NAME.getName(), assemblageInfo.get().getName());
                    requestInfo.setRequestAttribute(RequestArgument.ASSEMBLAGE_EDIT_OLD_CLASSIC_GENRE.getName(), decodeGenre(assemblageInfo.get().getGenre(), GenreType.CLASSIC_GENRE.getValue()));
                    requestInfo.setRequestAttribute(RequestArgument.ASSEMBLAGE_EDIT_OLD_ELECTRO_GENRE.getName(), decodeGenre(assemblageInfo.get().getGenre(), GenreType.ELECTRO_GENRE.getValue()));
                    requestInfo.setRequestAttribute(RequestArgument.ASSEMBLAGE_EDIT_OLD_POP_GENRE.getName(), decodeGenre(assemblageInfo.get().getGenre(), GenreType.POP_GENRE.getValue()));
                    requestInfo.setRequestAttribute(RequestArgument.ASSEMBLAGE_EDIT_OLD_ROCK_GENRE.getName(), decodeGenre(assemblageInfo.get().getGenre(), GenreType.ROCK_GENRE.getValue()));
                    requestInfo.setRequestAttribute(RequestArgument.ASSEMBLAGE_EDIT_OLD_JAZZ_GENRE.getName(), decodeGenre(assemblageInfo.get().getGenre(), GenreType.JAZZ_GENRE.getValue()));
                    requestInfo.setRequestAttribute(RequestArgument.ASSEMBLAGE_EDIT_OLD_BLUES_GENRE.getName(), decodeGenre(assemblageInfo.get().getGenre(), GenreType.BLUES_GENRE.getValue()));
                    requestInfo.setRequestAttribute(RequestArgument.ASSEMBLAGE_EDIT_OLD_PRICE.getName(), assemblageInfo.get().getPrice());
                }
                LinkedList<TrackView> tracks = trackReceiver.findTrackInfo(name);
                LinkedList<TrackView> assemblageTracks = trackReceiver.findTracksInAssemblage(assemblage, name);
                tracks.removeAll(assemblageTracks);
                assemblageTracks.forEach((track) -> track.setCheck(true));
                tracks.addAll(assemblageTracks);
                if (!tracks.isEmpty()) {
                    requestInfo.setRequestAttribute(RequestArgument.TRACK_SEARCH_LIST.getName(), tracks);
                }
                path = Optional.of(PagesUrl.ASSEMBLAGE_EDIT_PAGE.getPath());
            }
        }
        catch (ConnectorException e) {
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
