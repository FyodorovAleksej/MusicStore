package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.application.UserRole;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.AlbumReceiver;
import by.fyodorov.musicstore.validator.RequestParameterValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class EditAlbumCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(EditAlbumCommand.class);
    private static final String SEPARATOR = ",";
    private AlbumReceiver receiver;

    public EditAlbumCommand(AlbumReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public GoToInterface perform(RequestParameterMap request) throws CommandException {
        String userRole = (String) request.getSessionAttribute(RequestArgument.SESSION_ROLE.getName());
        String oldAlbum = (String) request.getSessionAttribute(RequestArgument.ALBUM_SESSION_NAME.getName());
        String albumName = request.getParameter(RequestArgument.ALBUM_EDIT_NAME.getName());
        String[] albumGenre = request.getRequestMultipleAttribute(RequestArgument.ALBUM_EDIT_GENRE.getName());
        String albumPrice = request.getParameter(RequestArgument.ALBUM_EDIT_PRICE.getName());
        String albumPerformer = request.getParameter(RequestArgument.ALBUM_EDIT_PERFORMER.getName());
        String[] tracks = request.getRequestMultipleAttribute(RequestArgument.ALBUM_EDIT_TRACKS.getName());
        RequestParameterValidator validator = new RequestParameterValidator();
        try {
            if (UserRole.ADMIN.toString().equals(userRole) && oldAlbum != null && albumName != null && albumGenre != null && validator.validatePrice(albumPrice) && albumPerformer != null && tracks != null) {
                if (receiver.editAlbum(oldAlbum, albumName, String.join(SEPARATOR, albumGenre), Integer.valueOf(albumPrice), albumPerformer, tracks)) {
                    LOGGER.debug("edit album successfully");
                } else {
                    LOGGER.debug("edit album failed");
                }
            }
        } catch (ConnectorException e) {
            throw new CommandException("Can't edit album - " + albumName, e);
        }
        return new RedirectGoTo(PagesUrl.ALL_ALBUMS_PAGE.getPath());
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}