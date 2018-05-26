package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.UserReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.fyodorov.musicstore.application.PagesUrl.TRACK_INFO_WITH_ARG_PAGE;

public class TrackBuyCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(TrackBuyCommand.class);
    private UserReceiver receiver;

    public TrackBuyCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public GoToInterface perform(RequestParameterMap request) throws CommandException {
        String userName = (String) request.getSessionAttribute(RequestArgument.SESSION_LOGIN.getName());
        String trackName = (String) request.getSessionAttribute(RequestArgument.TRACK_NAME.getName());
        try {
            if (userName != null && trackName != null) {

                if (receiver.buyTrack(userName, trackName)) {
                    LOGGER.debug("buy successfully");
                } else {
                    LOGGER.debug("buy Unsuccessfully");
                }
            }
        }
        catch (ConnectorException e) {
            throw new CommandException("Can't buy track - " + trackName, e);
        }
        return new RedirectGoTo(request.getContextPath() + TRACK_INFO_WITH_ARG_PAGE.getPath() + trackName);
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
