package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.AlbumReceiver;
import by.fyodorov.musicstore.validator.RequestParameterValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * adding new album in system
 */
public class AddAlbumCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddAlbumCommand.class);
    private static final String SEPARATOR = ",";
    private AlbumReceiver albumReceiver;

    /**
     * create command with album albumReceiver
     * @param albumReceiver - album albumReceiver for work with album repository
     */
    public AddAlbumCommand(AlbumReceiver albumReceiver) {
        this.albumReceiver = albumReceiver;
    }

    /**
     * performing command. Getting info about new album and adding to DB
     * @param request - request map of servlet
     * @return - command to forward or redirect
     * @throws CommandException - when command can't to perform
     */
    @Override
    public GoToInterface perform(RequestParameterMap request) throws CommandException {
        String userName = (String) request.getSessionAttribute(RequestArgument.SESSION_LOGIN.getName());
        String albumName = request.getParameter(RequestArgument.ALBUM_ADD_NAME.getName());
        String[] albumGenre = request.getRequestMultipleAttribute(RequestArgument.ALBUM_ADD_GENRE.getName());
        String albumPrice = request.getParameter(RequestArgument.ALBUM_ADD_PRICE.getName());
        String albumPerformer = request.getParameter(RequestArgument.ALBUM_ADD_PERFORMER.getName());
        String[] tracks = request.getRequestMultipleAttribute(RequestArgument.ALBUM_ADD_TRACKS.getName());
        RequestParameterValidator validator = new RequestParameterValidator();
        try {
            if (userName != null && albumName != null && albumGenre != null && validator.validatePrice(albumPrice) && albumPerformer != null && tracks != null) {
                if (albumReceiver.addNewAlbum(albumName, String.join(SEPARATOR, albumGenre), Integer.valueOf(albumPrice), albumPerformer, tracks)) {
                    LOGGER.info("add album \"" + albumName + "\" successfully");
                } else {
                    LOGGER.info("add album \"" + albumName + "\" failed");
                }
            }
        } catch (ConnectorException e) {
            throw new CommandException("Can't add album - " + albumName, e);
        }
        return new RedirectGoTo(PagesUrl.ALL_ALBUMS_PAGE.getPath());
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
