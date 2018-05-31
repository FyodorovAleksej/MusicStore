package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.AssemblageReceiver;
import by.fyodorov.musicstore.view.AssemblageView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;

/**
 * getting list of assemblages
 */
public class ListAssemblageCommand implements Command {
    private AssemblageReceiver assemblageReceiver;

    /**
     * created command with assemblage receiver
     * @param assemblageReceiver - receiver for working with assemblages
     */
    public ListAssemblageCommand(AssemblageReceiver assemblageReceiver) {
        this.assemblageReceiver = assemblageReceiver;
    }

    /**
     * performing command. Getting list of assemblages
     * @param requestInfo - map with arguments of request and session
     * @return - command of forward or redirect
     * @throws CommandException - when command can't perform
     */
    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        String userName = (String) requestInfo.getSessionAttribute(RequestArgument.SESSION_LOGIN.getName());
        LinkedList<AssemblageView> albums;
        try {
            albums = assemblageReceiver.findAssemblageInfo(userName);
            if (albums.isEmpty()) {
                requestInfo.setRequestParameter(RequestArgument.ASSEMBLAGE_FIND_RESULT.getName(), "nothing to find");
            } else {
                requestInfo.setRequestAttribute(RequestArgument.ASSEMBLAGE_SEARCH_LIST.getName(), albums);
            }
        } catch (ConnectorException e) {
            throw new CommandException("can't getting from assemblage database", e);
        }
        return new ForwardGoTo(PagesUrl.ASSEMBLAGE_SEARCH_PAGE.getPath());
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
