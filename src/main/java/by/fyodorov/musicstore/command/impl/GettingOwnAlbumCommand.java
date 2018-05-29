package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.AlbumReceiver;
import by.fyodorov.musicstore.view.AlbumWithoutPriceView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;

public class GettingOwnAlbumCommand implements Command {
    private AlbumReceiver receiver;

    public GettingOwnAlbumCommand(AlbumReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public GoToInterface perform(RequestParameterMap request) throws CommandException {
        String name = (String) request.getSessionAttribute(RequestArgument.SESSION_LOGIN.getName());
        LinkedList<AlbumWithoutPriceView> albums;
        try {
            albums = receiver.findAlbumForUser(name);
            if (albums.isEmpty()) {
                request.setRequestParameter(RequestArgument.ALBUM_FIND_RESULT.getName(), "nothing to find");
            }
            else {
                request.setRequestAttribute(RequestArgument.ALBUM_OWN_LIST.getName(), albums);
            }
        } catch (ConnectorException e) {
            throw new CommandException("can't getting from album database", e);
        }
        return new ForwardGoTo(PagesUrl.ALBUM_OWN_VIEW_PAGE.getPath());
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
