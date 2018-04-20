package by.fyodorov.musicstore.command.commandimpl;

import by.fyodorov.musicstore.command.Command;
import by.fyodorov.musicstore.command.CommandException;
import by.fyodorov.musicstore.command.GoToInterface;
import by.fyodorov.musicstore.command.RequestParameterMap;

import javax.servlet.http.HttpServletRequest;

public class GettingAlbumCommandImpl implements Command {
    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        return null;
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}
