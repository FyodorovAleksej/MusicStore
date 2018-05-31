package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.CommentReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.fyodorov.musicstore.application.PagesUrl.TRACK_INFO_WITH_ARG_PAGE;

/**
 * adding comment for track
 */
public class AddCommentCommand implements Command {
    private static Logger LOGGER = LogManager.getLogger(AddCommentCommand.class);
    private CommentReceiver commentReceiver;

    /**
     * creating command with comment commentReceiver
     * @param commentReceiver - commentReceiver for working with comments
     */
    public AddCommentCommand(CommentReceiver commentReceiver) {
        this.commentReceiver = commentReceiver;
    }

    /**
     * performing command. Adding new comment from current user to selected track
     * @param requestInfo - map with arguments of request and session
     * @return - command of forward or redirect
     * @throws CommandException - when command can't perform
     */
    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        String userName = (String) requestInfo.getSessionAttribute(RequestArgument.SESSION_LOGIN.getName());
        String text = requestInfo.getParameter(RequestArgument.COMMENT_TEXT.getName());
        String trackName = (String) requestInfo.getSessionAttribute(RequestArgument.TRACK_NAME.getName());
        LOGGER.debug(String.format("comment - (%s, %s, %s)", userName, text, trackName));
        try {
            if (userName != null && text != null && trackName != null) {
                if (commentReceiver.addComment(text, userName, trackName)) {
                    LOGGER.debug("adding successfully");
                } else {
                    LOGGER.debug("adding Unsuccessfully");
                }
            }
        } catch (ConnectorException e) {
            throw new CommandException("Can't adding comment", e);
        }
        return new RedirectGoTo(requestInfo.getContextPath() + TRACK_INFO_WITH_ARG_PAGE.getPath() + trackName);
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
