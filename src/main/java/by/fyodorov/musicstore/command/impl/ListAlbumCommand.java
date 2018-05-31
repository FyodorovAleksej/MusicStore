package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.AlbumReceiver;
import by.fyodorov.musicstore.view.AlbumView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;

/**
 * getting list of albums
 */
public class ListAlbumCommand implements Command {
    private AlbumReceiver albumReceiver;

    /**
     * create command with album receiver
     * @param albumReceiver - receiver for working with albums
     */
    public ListAlbumCommand(AlbumReceiver albumReceiver) {
        this.albumReceiver = albumReceiver;
    }

    /**
     * performing command. Getting list of albums
     * @param requestInfo - map with arguments of request and session
     * @return - command of forward or redirect
     * @throws CommandException - when command can't perform
     */
    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        String userName = (String) requestInfo.getSessionAttribute(RequestArgument.SESSION_LOGIN.getName());
        LinkedList<AlbumView> albums;
        try {
            albums = albumReceiver.findAlbumInfo(userName);
            if (albums.isEmpty()) {
                requestInfo.setRequestParameter(RequestArgument.ALBUM_FIND_RESULT.getName(), "nothing to find");
            } else {
                requestInfo.setRequestAttribute(RequestArgument.ALBUM_SEARCH_LIST.getName(), albums);
            }
        } catch (ConnectorException e) {
            throw new CommandException("can't getting from album database", e);
        }
        return new ForwardGoTo(PagesUrl.ALBUM_SEARCH_PAGE.getPath());
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
