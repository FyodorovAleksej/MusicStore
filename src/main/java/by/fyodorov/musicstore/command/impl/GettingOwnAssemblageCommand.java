package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.AssemblageReceiver;
import by.fyodorov.musicstore.view.AssemblageWithoutPriceView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;

public class GettingOwnAssemblageCommand implements Command {
    private AssemblageReceiver receiver;

    public GettingOwnAssemblageCommand(AssemblageReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public GoToInterface perform(RequestParameterMap request) throws CommandException {
        String name = (String) request.getSessionAttribute(RequestArgument.SESSION_LOGIN.getName());
        LinkedList<AssemblageWithoutPriceView> assemblages;
        try {
            assemblages = receiver.findAssemblageForUser(name);
            if (assemblages.isEmpty()) {
                request.setRequestParameter(RequestArgument.ASSEMBLAGE_FIND_RESULT.getName(), "nothing to find");
                return new RedirectGoTo(PagesUrl.MAIN_PAGE.getPath());
            }
            else {
                request.setRequestAttribute(RequestArgument.ASSEMBLAGE_OWN_LIST.getName(), assemblages);
            }
        } catch (ConnectorException e) {
            throw new CommandException("can't getting from assemblage database", e);
        }
        return new ForwardGoTo(PagesUrl.ASSEMBLAGE_OWN_VIEW_PAGE.getPath());
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
