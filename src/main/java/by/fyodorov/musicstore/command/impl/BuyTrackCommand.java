package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.UserReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.fyodorov.musicstore.application.PagesUrl.TRACK_INFO_WITH_ARG_PAGE;

/**
 * buy track for current user
 */
public class BuyTrackCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(BuyTrackCommand.class);
    private UserReceiver userReceiver;

    /**
     * create command with user receiver
     * @param userReceiver - receiver for working with users
     */
    public BuyTrackCommand(UserReceiver userReceiver) {
        this.userReceiver = userReceiver;
    }

    /**
     * performing command. Adding track to user
     * @param requestInfo - map with arguments of request and session
     * @return - command of forward or redirect
     * @throws CommandException - when command can't perform
     */
    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        String userName = (String) requestInfo.getSessionAttribute(RequestArgument.SESSION_LOGIN.getName());
        String trackName = (String) requestInfo.getSessionAttribute(RequestArgument.TRACK_NAME.getName());
        try {
            if (userName != null && trackName != null) {

                if (userReceiver.buyTrack(userName, trackName)) {
                    LOGGER.info("buy \"" + trackName + "\" successfully");
                } else {
                    LOGGER.info("buy \"" + trackName + "\" Unsuccessfully");
                }
            }
        } catch (ConnectorException e) {
            throw new CommandException("Can't buy track - " + trackName, e);
        }
        return new RedirectGoTo(requestInfo.getContextPath() + TRACK_INFO_WITH_ARG_PAGE.getPath() + trackName);
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
