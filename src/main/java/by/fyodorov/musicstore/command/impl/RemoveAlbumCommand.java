package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.application.UserRole;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.AlbumReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class RemoveAlbumCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(RemoveAlbumCommand.class);
    private AlbumReceiver receiver;

    public RemoveAlbumCommand(AlbumReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public GoToInterface perform(RequestParameterMap request) throws CommandException {
        String userRole = (String) request.getSessionAttribute(RequestArgument.SESSION_ROLE.getName());
        String albumName = (String) request.getSessionAttribute(RequestArgument.ALBUM_SESSION_NAME.getName());
        try {
            if (UserRole.ADMIN.toString().equals(userRole) && albumName != null) {
                if (receiver.removeAlbum(albumName)) {
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
