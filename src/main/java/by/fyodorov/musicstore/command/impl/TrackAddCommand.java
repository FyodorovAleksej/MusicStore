package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.InitParameter;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.application.UserRole;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.receiver.TrackReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import static by.fyodorov.musicstore.application.PagesUrl.MAIN_PAGE;

public class TrackAddCommand implements Command {
    private static Logger LOGGER = LogManager.getLogger(TrackAddCommand.class);
    private TrackReceiver receiver;

    public TrackAddCommand(TrackReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public GoToInterface perform(RequestParameterMap request) throws CommandException {
        String userName = (String) request.getSessionAttribute(RequestArgument.SESSION_LOGIN.getName());
        String userRole = (String) request.getSessionAttribute(RequestArgument.SESSION_ROLE.getName());
        String performer = request.getParameter(RequestArgument.TRACK_ADD_PERFORMER.getName());
        String trackName = request.getParameter(RequestArgument.TRACK_ADD_NAME.getName());
        String trackPrice = request.getParameter(RequestArgument.TRACK_ADD_PRICE.getName());
        String trackGenre = request.getParameter(RequestArgument.TRACK_ADD_GENRE.getName());

        LOGGER.debug(String.format("adding track - (%s, %s, %s)", userName, trackName, performer));
        try {
            if (UserRole.ADMIN.toString().equals(userRole) && performer != null && trackName != null && validPrice(trackPrice) && trackGenre != null) {
                Part filePart = (Part) request.getRequestAttribute(RequestArgument.FILE_KEY.getName());
                InputStream inputStream = filePart.getInputStream();
                FileWriter writer = new FileWriter(request.getContextPath() + InitParameter.FILE_PATH + trackName + InitParameter.TRACK_EXTENSION);
                while (inputStream.available() > 0) {
                    writer.write(inputStream.read());
                }
                writer.flush();
                writer.close();

                LOGGER.debug("getting file = \"" + filePart.getName() + "\"");

                receiver.addNewTrack(trackName, trackGenre, Integer.valueOf(trackPrice), performer);
            }
        }
        catch (ConnectorException e) {
            throw new CommandException("Can't adding track", e);
        }
        catch (IOException e) {
            throw new CommandException("Can't copy new track", e);
        }
        return new RedirectGoTo(request.getContextPath() + MAIN_PAGE.getPath());
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }

    private boolean validPrice(String price) {
        return  receiver.validatePrice(price);
    }
}