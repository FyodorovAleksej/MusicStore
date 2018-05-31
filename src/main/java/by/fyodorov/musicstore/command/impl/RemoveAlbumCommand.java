package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.model.UserRole;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.AlbumReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * removing album from system
 */
public class RemoveAlbumCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(RemoveAlbumCommand.class);
    private AlbumReceiver albumReceiver;

    /**
     * created command with album receiver
     * @param albumReceiver - receiver for working with albums
     */
    public RemoveAlbumCommand(AlbumReceiver albumReceiver) {
        this.albumReceiver = albumReceiver;
    }

    /**
     * performing command. Remove selected album from system
     * @param requestInfo - map with arguments of request and session
     * @return - command of forward or redirect
     * @throws CommandException - when command can't perform
     */
    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        String userRole = (String) requestInfo.getSessionAttribute(RequestArgument.SESSION_ROLE.getName());
        String albumName = (String) requestInfo.getSessionAttribute(RequestArgument.ALBUM_SESSION_NAME.getName());
        try {
            if (UserRole.ADMIN.toString().equals(userRole) && albumName != null) {
                if (albumReceiver.removeAlbum(albumName)) {
                    LOGGER.debug("remove album successfully");
                } else {
                    LOGGER.debug("remove album failed");
                }
            }
        } catch (ConnectorException e) {
            throw new CommandException("Can't remove album - " + albumName, e);
        }
        return new RedirectGoTo(PagesUrl.ALL_ALBUMS_PAGE.getPath());
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
