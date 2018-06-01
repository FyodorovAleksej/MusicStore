package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.model.UserRole;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.AlbumReceiver;
import by.fyodorov.musicstore.validator.RequestParameterValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * editing album
 */
public class EditAlbumCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(EditAlbumCommand.class);
    private static final String SEPARATOR = ",";
    private AlbumReceiver albumReceiver;

    /**
     * creating command with album receiver
     * @param albumReceiver - receiver for working with albums
     */
    public EditAlbumCommand(AlbumReceiver albumReceiver) {
        this.albumReceiver = albumReceiver;
    }

    /**
     * performing command. Edit name, genre, price, performer and tracks of existing album
     * @param requestInfo - map with arguments of request and session
     * @return - command of forward or redirect
     * @throws CommandException - when command can't perform
     */
    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        String userRole = (String) requestInfo.getSessionAttribute(RequestArgument.SESSION_ROLE.getName());
        String oldAlbum = (String) requestInfo.getSessionAttribute(RequestArgument.ALBUM_SESSION_NAME.getName());
        String albumName = requestInfo.getParameter(RequestArgument.ALBUM_EDIT_NAME.getName());
        String[] albumGenre = requestInfo.getRequestMultipleAttribute(RequestArgument.ALBUM_EDIT_GENRE.getName());
        String albumPrice = requestInfo.getParameter(RequestArgument.ALBUM_EDIT_PRICE.getName());
        String albumPerformer = requestInfo.getParameter(RequestArgument.ALBUM_EDIT_PERFORMER.getName());
        String[] tracks = requestInfo.getRequestMultipleAttribute(RequestArgument.ALBUM_EDIT_TRACKS.getName());
        RequestParameterValidator validator = new RequestParameterValidator();
        try {
            if (UserRole.ADMIN.toString().equals(userRole) && oldAlbum != null && albumName != null && albumGenre != null && validator.validatePrice(albumPrice) && albumPerformer != null && tracks != null) {
                if (albumReceiver.editAlbum(oldAlbum, albumName, String.join(SEPARATOR, albumGenre), Integer.valueOf(albumPrice), albumPerformer, tracks)) {
                    LOGGER.info("edit \"" + albumName + "\" album successfully");
                } else {
                    LOGGER.info("edit \"" + albumName + "\" album failed");
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