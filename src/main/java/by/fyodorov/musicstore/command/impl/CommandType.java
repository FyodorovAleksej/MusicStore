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

    ALBUM_INFO_COMMAND(new AlbumInfoCommand(new AlbumReceiver(), new TrackReceiver())),
    ASSEMBLAGE_INFO_COMMAND(new AssemblageInfoCommand(new AssemblageReceiver(), new TrackReceiver())),

    BUY_TRACK_COMMAND(new TrackBuyCommand(new UserReceiver())),
    BUY_ALBUM_COMMAND(new AlbumBuyCommand(new UserReceiver())),
    BUY_ASSEMBLAGE_COMMAND(new AssemblageBuyCommand(new UserReceiver())),

    TRACK_SEARCH_COMMAND(new GettingTrackCommand(new TrackReceiver())),
    ALBUM_SEARCH_COMMAND(new GettingAlbumCommand(new AlbumReceiver())),
    ASSEMBLAGE_SEARCH_COMMAND(new GettingAssemblageCommand(new AssemblageReceiver())),

    OWN_TRACK_COMMAND(new GettingOwnTrackCommand(new TrackReceiver())),
    OWN_ALBUM_COMMAND(new GettingOwnAlbumCommand(new AlbumReceiver())),
    OWN_ASSEMBLAGE_COMMAND(new GettingOwnAssemblageCommand(new AssemblageReceiver())),

    TRACK_ADD_COMMAND(new TrackAddCommand(new TrackReceiver())),

    USER_INFO_COMMAND(new UserInfoCommand(new UserReceiver())),
    USER_EDIT_COMMAND(new EditUserCommand(new UserReceiver())),
    ADMIN_USER_COMMAND(new GettingUsersCommand(new UserReceiver())),

    ADD_ALBUM_PREPARE_COMMAND(new AddAlbumPrepareCommand(new TrackReceiver(), new PerformerReceiver())),
    ADD_ASSEMBLAGE_PREPARE_COMMAND(new AddAssemblagePrepareCommand(new TrackReceiver())),

    ADD_ALBUM_COMMAND(new AddAlbumCommand(new AlbumReceiver())),
    ADD_ASSEMBLAGE_COMMAND(new AddAssemblageCommand(new AssemblageReceiver())),

    EDIT_ALBUM_COMMAND(new EditAlbumPrepareCommand(new TrackReceiver(), new PerformerReceiver(), new AlbumReceiver())),
    EDIT_ASSEMBLAGE_COMMAND(new EditAssemblagePrepareCommand(new TrackReceiver(), new AssemblageReceiver())),

    EDIT_ALBUM_APPLY_COMMAND(new EditAlbumCommand(new AlbumReceiver())),
    EDIT_ASSEMBLAGE_APPLY_COMMAND(new EditAssemblageCommand(new AssemblageReceiver())),

    REMOVE_ALBUM_COMMAND(new RemoveAlbumCommand(new AlbumReceiver())),
    REMOVE_ASSEMBLAGE_COMMAND(new RemoveAssemblageCommand(new AssemblageReceiver()));

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
