package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.InitParameter;
import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.controller.ContextParameter;
import by.fyodorov.musicstore.receiver.TrackReceiver;
import by.fyodorov.musicstore.view.TrackView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;

/**
 * getting list of all tracks
 */
public class ListTrackCommand implements Command {
    private TrackReceiver trackReceiver;

    /**
     * creating command with track trackReceiver
     * @param trackReceiver - trackReceiver for working with tracks
     */
    public ListTrackCommand(TrackReceiver trackReceiver) {
        this.trackReceiver = trackReceiver;
    }

    /**
     * performing command. Getting list of all tracks
     * @param requestInfo - map with arguments of request and session
     * @return - command of forward or redirect
     * @throws CommandException - when command can't perform
     */
    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        String name = (String) requestInfo.getSessionAttribute(RequestArgument.SESSION_LOGIN.getName());
        String pageMaxString = ContextParameter.getInstance().getContextParam(InitParameter.PAGE_MAX.toString());
        Integer pageMax = pageMaxString == null ? 0 : Integer.valueOf(pageMaxString);
        String page = requestInfo.getParameter(RequestArgument.SEARCH_PAGE.getName());
        Integer currentPage = page == null ? 0 : Integer.valueOf(page);
        LinkedList<TrackView> tracks;
        try {
            requestInfo.setSessionAttribute(RequestArgument.ALL_COUNT.getName(), trackReceiver.findTrackCount());
            tracks = trackReceiver.findTrackLimitInfo(name, (currentPage * pageMax), pageMax);
            if (tracks.isEmpty()) {
                requestInfo.setRequestParameter(RequestArgument.TRACK_FIND_RESULT.getName(), "nothing to find");
            } else {
                requestInfo.setRequestAttribute(RequestArgument.TRACK_SEARCH_LIST.getName(), tracks);
            }
        } catch (ConnectorException e) {
            throw new CommandException("can't getting from track database", e);
        }
        return new ForwardGoTo(PagesUrl.TRACK_SEARCH_PAGE.getPath());
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
