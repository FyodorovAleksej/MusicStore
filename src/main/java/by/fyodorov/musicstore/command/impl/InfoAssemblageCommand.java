package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.AssemblageReceiver;
import by.fyodorov.musicstore.receiver.TrackReceiver;
import by.fyodorov.musicstore.view.AssemblageView;
import by.fyodorov.musicstore.view.TrackView;
import by.fyodorov.musicstore.view.TrackWithoutPriceView;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.Optional;

/**
 * getting information about selected assemblage
 */
public class InfoAssemblageCommand implements Command {
    private AssemblageReceiver assemblageReceiver;
    private TrackReceiver trackReceiver;

    /**
     * created command with assemblage and track receivers
     * @param assemblageReceiver - receiver for working with assemblages
     * @param trackReceiver - receiver for working with tracks
     */
    public InfoAssemblageCommand(AssemblageReceiver assemblageReceiver, TrackReceiver trackReceiver) {
        this.assemblageReceiver = assemblageReceiver;
        this.trackReceiver = trackReceiver;
    }

    /**
     * performing command. Getting information for selected assemblage
     * @param requestInfo - map with arguments of request and session
     * @return - command of forward or redirect
     * @throws CommandException - when command can't perform
     */
    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        Optional<String> path = Optional.empty();
        String assemblageName = requestInfo.getParameter(RequestArgument.ASSEMBLAGE_NAME_FOR_INFO.getName());
        String userName = (String) requestInfo.getSessionAttribute(RequestArgument.SESSION_LOGIN.getName());
        try {
            Optional<AssemblageView> assemblageView = assemblageReceiver.assemblageInfoForUser(assemblageName, userName);
            if (assemblageView.isPresent()) {
                requestInfo.setSessionAttribute(RequestArgument.ASSEMBLAGE_SESSION_NAME.getName(), assemblageView.get().getName());
                requestInfo.setRequestAttribute(RequestArgument.ASSEMBLAGE_INFO_NAME.getName(), assemblageView.get().getName());
                requestInfo.setRequestAttribute(RequestArgument.ASSEMBLAGE_INFO_GENRE.getName(), assemblageView.get().getGenre());
                requestInfo.setRequestAttribute(RequestArgument.ASSEMBLAGE_INFO_PRICE.getName(), assemblageView.get().getPrice());
                requestInfo.setRequestAttribute(RequestArgument.ASSEMBLAGE_INFO_DATE.getName(), assemblageView.get().getDate());
                requestInfo.setRequestAttribute(RequestArgument.ASSEMBLAGE_INFO_OWNER.getName(), assemblageView.get().getOwner());
                requestInfo.setRequestAttribute(RequestArgument.ASSEMBLAGE_INFO_SUMMARY.getName(), assemblageView.get().getSummary());
                LinkedList<TrackWithoutPriceView> tracks = trackReceiver.findTracksInAssemblage(assemblageName);
                requestInfo.setRequestAttribute(RequestArgument.ASSEMBLAGE_TRACKS_LIST.getName(), tracks);
                path = Optional.of(PagesUrl.ASSEMBLAGE_INFO_PAGE.getPath());
            }
        } catch (ConnectorException e) {
            throw new CommandException("can't connect to find assemblage", e);
        }
        return new ForwardGoTo(path.orElse(PagesUrl.MAIN_PAGE.getPath()));
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
