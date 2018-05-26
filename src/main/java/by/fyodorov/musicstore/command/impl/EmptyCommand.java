package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.command.Command;
import by.fyodorov.musicstore.command.GoToInterface;
import by.fyodorov.musicstore.command.RedirectGoTo;
import by.fyodorov.musicstore.command.RequestParameterMap;

import javax.servlet.http.HttpServletRequest;

public class EmptyCommand implements Command {

    @Override
    public GoToInterface perform(RequestParameterMap request) {
        return new RedirectGoTo("/");
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }
}