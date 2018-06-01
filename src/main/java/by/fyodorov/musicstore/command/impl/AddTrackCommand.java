package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.InitParameter;
import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.model.UserRole;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.controller.ContextParameter;
import by.fyodorov.musicstore.receiver.TrackReceiver;
import by.fyodorov.musicstore.validator.RequestParameterValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import static by.fyodorov.musicstore.application.InitParameter.FILE_PATH;

/**
 * adding new track to system
 */
public class AddTrackCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddTrackCommand.class);
    private static final String SEPARATOR = ",";
    private TrackReceiver trackReceiver;

    /**
     * create command with track trackReceiver
     * @param trackReceiver - trackReceiver for working with tracks
     */
    public AddTrackCommand(TrackReceiver trackReceiver) {
        this.trackReceiver = trackReceiver;
    }

    /**
     * performing command. Adding track to system
     * @param requestInfo - map with arguments of request and session
     * @return - command of forward or redirect
     * @throws CommandException - when command can't perform
     */
    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        String userName = (String) requestInfo.getSessionAttribute(RequestArgument.SESSION_LOGIN.getName());
        String userRole = (String) requestInfo.getSessionAttribute(RequestArgument.SESSION_ROLE.getName());
        String performer = requestInfo.getParameter(RequestArgument.TRACK_ADD_PERFORMER.getName());
        String trackName = requestInfo.getParameter(RequestArgument.TRACK_ADD_NAME.getName());
        String trackPrice = requestInfo.getParameter(RequestArgument.TRACK_ADD_PRICE.getName());
        String[] genres = requestInfo.getRequestMultipleAttribute(RequestArgument.TRACK_ADD_GENRE.getName());
        RequestParameterValidator validator = new RequestParameterValidator();

        LOGGER.info(String.format("adding track - (%s, %s, %s)", userName, trackName, performer));
        try {
            if (UserRole.ADMIN.toString().equals(userRole) && performer != null && trackName != null && validator.validatePrice(trackPrice) && genres != null) {
                Part filePart = (Part) requestInfo.getRequestAttribute(RequestArgument.FILE_KEY.getName());
                InputStream inputStream = filePart.getInputStream();
                FileWriter writer = new FileWriter(ContextParameter.getInstance().getContextParam(FILE_PATH.toString()) + trackName + InitParameter.TRACK_EXTENSION);
                while (inputStream.available() > 0) {
                    writer.write(inputStream.read());
                }
                writer.flush();
                writer.close();

                LOGGER.info("getting file = \"" + filePart.getName() + "\"");
                trackReceiver.addNewTrack(trackName, String.join(SEPARATOR, genres), Integer.valueOf(trackPrice), performer);
            }
        } catch (ConnectorException e) {
            throw new CommandException("Can't adding track", e);
        } catch (IOException e) {
            throw new CommandException("Can't copy new track", e);
        }
        return new RedirectGoTo(PagesUrl.ALL_TRACKS_PAGE.getPath());
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}