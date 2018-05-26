package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.CommentReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.fyodorov.musicstore.application.PagesUrl.TRACK_INFO_WITH_ARG_PAGE;

public class CommentAddCommand implements Command {
    private static Logger LOGGER = LogManager.getLogger(CommentAddCommand.class);
    private CommentReceiver receiver;

    public CommentAddCommand(CommentReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public GoToInterface perform(RequestParameterMap request) throws CommandException {
        String userName = (String) request.getSessionAttribute(RequestArgument.SESSION_LOGIN.getName());
        String text = request.getParameter(RequestArgument.COMMENT_TEXT.getName());
        String trackName = (String) request.getSessionAttribute(RequestArgument.TRACK_NAME.getName());
        LOGGER.debug(String.format("comment - (%s, %s, %s)", userName, text, trackName));
        try {
            if (userName != null && text != null && trackName != null) {
                if (receiver.addComment(text, userName, trackName)) {
                    LOGGER.debug("adding successfully");
                } else {
                    LOGGER.debug("adding Unsuccessfully");
                }
            }
        }
        catch (ConnectorException e) {
            throw new CommandException("Can't adding comment", e);
        }
        return new RedirectGoTo(request.getContextPath() + TRACK_INFO_WITH_ARG_PAGE.getPath() + trackName);
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
