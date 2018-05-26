package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.AlbumReceiver;
import by.fyodorov.musicstore.view.AlbumView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;

public class GettingAlbumCommand implements Command {
    private AlbumReceiver receiver;

    public GettingAlbumCommand(AlbumReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        String userName = (String) requestInfo.getSessionAttribute(RequestArgument.SESSION_LOGIN.getName());
        LinkedList<AlbumView> albums;
        try {
            albums = receiver.findAlbumInfo(userName);
            if (albums.isEmpty()) {
                requestInfo.setRequestParameter(RequestArgument.ALBUM_FIND_RESULT.getName(), "nothing to find");
            }
            else {
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
