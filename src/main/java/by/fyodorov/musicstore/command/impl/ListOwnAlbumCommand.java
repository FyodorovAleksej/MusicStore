package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.AlbumReceiver;
import by.fyodorov.musicstore.view.AlbumWithoutPriceView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;

/**
 * getting user's albums
 */
public class ListOwnAlbumCommand implements Command {
    private AlbumReceiver albumReceiver;

    /**
     * create command with album receiver
     * @param albumReceiver - receiver for working with albums
     */
    public ListOwnAlbumCommand(AlbumReceiver albumReceiver) {
        this.albumReceiver = albumReceiver;
    }

    /**
     * performing command. Getting list of user's albums
     * @param requestInfo - map with arguments of request and session
     * @return - command of forward or redirect
     * @throws CommandException - when command can't perform
     */
    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        String name = (String) requestInfo.getSessionAttribute(RequestArgument.SESSION_LOGIN.getName());
        LinkedList<AlbumWithoutPriceView> albums;
        try {
            albums = albumReceiver.findAlbumForUser(name);
            if (albums.isEmpty()) {
                requestInfo.setRequestParameter(RequestArgument.ALBUM_FIND_RESULT.getName(), "nothing to find");
            } else {
                requestInfo.setRequestAttribute(RequestArgument.ALBUM_OWN_LIST.getName(), albums);
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
