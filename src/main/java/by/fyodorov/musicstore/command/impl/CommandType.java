package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.command.Command;
import by.fyodorov.musicstore.receiver.*;

public enum CommandType {
    LOGIN_COMMAND(new LoginCommand(new UserReceiver())),
    SIGN_UP_COMMAND(new SignUpCommand(new UserReceiver())),
    REGISTER_COMMAND(new RegisterCommand(new UserReceiver())),
    LOGOUT_COMMAND(new LogoutCommand()),
    EMPTY_COMMAND(new EmptyCommand()),

    TRACK_INFO_COMMAND(new TrackInfoCommand(new TrackReceiver(), new PerformerReceiver())),
    COMMENT_ADD_COMMAND(new CommentAddCommand(new CommentReceiver())),

    BUY_TRACK_COMMAND(new TrackBuyCommand(new UserReceiver())),

    TRACK_SEARCH_COMMAND(new GettingTrackCommand(new TrackReceiver())),
    ALBUM_SEARCH_COMMAND(new GettingAlbumCommand(new AlbumReceiver())),
    ASSEMBLAGE_SEARCH_COMMAND(new GettingAssemblageCommand(new AssemblageReceiver())),

    OWN_TRACK_COMMAND(new GettingOwnTrackCommand(new TrackReceiver())),
    OWN_ALBUM_COMMAND(new GettingOwnAlbumCommand(new AlbumReceiver())),
    OWN_ASSEMBLAGE_COMMAND(new GettingOwnAssemblageCommand(new AssemblageReceiver())),

    TRACK_ADD_COMMAND(new TrackAddCommand(new TrackReceiver())),

    ADMIN_USER_COMMAND(new GettingUsersCommand(new UserReceiver()));

    private Command command;

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public static CommandType getDefault() {
        return EMPTY_COMMAND;
    }
}
