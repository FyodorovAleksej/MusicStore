package by.fyodorov.musicstore.command.commandimpl;

import by.fyodorov.musicstore.command.Command;
import by.fyodorov.musicstore.receiver.PerformerReceiver;
import by.fyodorov.musicstore.receiver.TrackReceiver;
import by.fyodorov.musicstore.receiver.UserReceiver;

public enum CommandEnum {
    LOGIN_COMMAND(new LoginCommandImpl(new UserReceiver())),
    SIGN_UP_COMMAND(new SignUpCommandImpl(new UserReceiver())),
    REGISTER_COMMAND(new RegisterCommandImpl(new UserReceiver())),
    LOGOUT_COMMAND(new LogoutCommandImpl()),
    EMPTY_COMMAND(new EmptyCommandImpl()),

    TRACK_INFO_COMMAND(new TrackInfoCommandImpl(new TrackReceiver(), new PerformerReceiver())),

    GETTING_TRACK_COMMAND(new GettingTrackCommandImpl(new TrackReceiver()));

    private Command command;

    CommandEnum(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public static CommandEnum getDefault() {
        return EMPTY_COMMAND;
    }
}
