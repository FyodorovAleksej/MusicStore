package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.command.Command;
import by.fyodorov.musicstore.receiver.*;

public enum CommandType {
    LOGIN_COMMAND(new LoginCommand(new UserReceiver())),
    SIGN_UP_COMMAND(new SignUpCommand(new UserReceiver())),
    REGISTER_COMMAND(new RegisterCommand(new UserReceiver())),
    LOGOUT_COMMAND(new LogoutCommand()),
    EMPTY_COMMAND(new EmptyCommand()),

    TRACK_INFO_COMMAND(new InfoTrackCommand(new TrackReceiver(), new PerformerReceiver())),
    COMMENT_ADD_COMMAND(new AddCommentCommand(new CommentReceiver())),

    ALBUM_INFO_COMMAND(new InfoAlbumCommand(new AlbumReceiver(), new TrackReceiver())),
    ASSEMBLAGE_INFO_COMMAND(new InfoAssemblageCommand(new AssemblageReceiver(), new TrackReceiver())),

    BUY_TRACK_COMMAND(new BuyTrackCommand(new UserReceiver())),
    BUY_ALBUM_COMMAND(new BuyAlbumCommand(new UserReceiver())),
    BUY_ASSEMBLAGE_COMMAND(new BuyAssemblageCommand(new UserReceiver())),

    TRACK_SEARCH_COMMAND(new ListTrackCommand(new TrackReceiver())),
    ALBUM_SEARCH_COMMAND(new ListAlbumCommand(new AlbumReceiver())),
    ASSEMBLAGE_SEARCH_COMMAND(new ListAssemblageCommand(new AssemblageReceiver())),

    OWN_TRACK_COMMAND(new ListOwnTrackCommand(new TrackReceiver())),
    OWN_ALBUM_COMMAND(new ListOwnAlbumCommand(new AlbumReceiver())),
    OWN_ASSEMBLAGE_COMMAND(new ListOwnAssemblageCommand(new AssemblageReceiver())),

    TRACK_ADD_COMMAND(new AddTrackCommand(new TrackReceiver())),
    TRACK_ADD_PREPARE_COMMAND(new AddTrackPrepareCommand(new PerformerReceiver())),
    TRACK_EDIT_PREPARE_COMMAND(new EditTrackPrepareCommand(new TrackReceiver(), new PerformerReceiver())),
    TRACK_EDIT_COMMAND(new EditTrackCommand(new TrackReceiver())),
    TRACK_REMOVE_COMMAND(new RemoveTrackCommand(new TrackReceiver())),

    USER_INFO_COMMAND(new InfoUserCommand(new UserReceiver())),
    USER_EDIT_COMMAND(new EditUserCommand(new UserReceiver())),
    ADMIN_USER_COMMAND(new ListUsersCommand(new UserReceiver())),

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
