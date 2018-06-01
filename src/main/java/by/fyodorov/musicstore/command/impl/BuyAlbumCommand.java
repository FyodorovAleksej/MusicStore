package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.UserReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.fyodorov.musicstore.application.PagesUrl.ALBUM_INFO_WITH_ARG_PAGE;

/**
 * buy album for current user
 */
public class BuyAlbumCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(BuyAlbumCommand.class);
    private UserReceiver userReceiver;

    /**
     * create command with user receiver
     * @param userReceiver - receiver for working with users
     */
    public BuyAlbumCommand(UserReceiver userReceiver) {
        this.userReceiver = userReceiver;
    }

    /**
     * performing command. Adding album and tracks in it to current user.
     * @param requestInfo - map with arguments of request and session
     * @return - command of forward or redirect
     * @throws CommandException - when command can't perform
     */
    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        String userName = (String) requestInfo.getSessionAttribute(RequestArgument.SESSION_LOGIN.getName());
        String albumName = (String) requestInfo.getSessionAttribute(RequestArgument.ALBUM_SESSION_NAME.getName());
        try {
            if (userName != null && albumName != null) {

                if (userReceiver.buyAlbum(userName, albumName)) {
                    LOGGER.info("buy \"" + albumName + "\" successfully");
                } else {
                    LOGGER.info("buy \"" + albumName + "\" unsuccessfully");
                }
            }
        } catch (ConnectorException e) {
            throw new CommandException("Can't buy album - " + albumName, e);
        }
        return new RedirectGoTo(requestInfo.getContextPath() + ALBUM_INFO_WITH_ARG_PAGE.getPath() + albumName);
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
