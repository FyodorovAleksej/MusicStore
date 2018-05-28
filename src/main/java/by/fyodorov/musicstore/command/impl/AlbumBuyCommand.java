package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.UserReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.fyodorov.musicstore.application.PagesUrl.ALBUM_INFO_WITH_ARG_PAGE;

public class AlbumBuyCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AlbumBuyCommand.class);
    private UserReceiver receiver;

    public AlbumBuyCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public GoToInterface perform(RequestParameterMap request) throws CommandException {
        String userName = (String) request.getSessionAttribute(RequestArgument.SESSION_LOGIN.getName());
        String albumName = (String) request.getSessionAttribute(RequestArgument.ALBUM_SESSION_NAME.getName());
        try {
            if (userName != null && albumName != null) {

                if (receiver.buyAlbum(userName, albumName)) {
                    LOGGER.debug("buy successfully");
                } else {
                    LOGGER.debug("buy Unsuccessfully");
                }
            }
        }
        catch (ConnectorException e) {
            throw new CommandException("Can't buy album - " + albumName, e);
        }
        return new RedirectGoTo(request.getContextPath() + ALBUM_INFO_WITH_ARG_PAGE.getPath() + albumName);
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
