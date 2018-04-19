package by.fyodorov.musicstore.command.commandimpl;

import by.fyodorov.musicstore.command.Command;
import by.fyodorov.musicstore.receiver.*;
import by.fyodorov.musicstore.view.TrackWithoutPriceView;

public enum CommandEnum {
    LOGIN_COMMAND(new LoginCommandImpl(new UserReceiver())),
    SIGN_UP_COMMAND(new SignUpCommandImpl(new UserReceiver())),
    REGISTER_COMMAND(new RegisterCommandImpl(new UserReceiver())),
    LOGOUT_COMMAND(new LogoutCommandImpl()),
    EMPTY_COMMAND(new EmptyCommandImpl()),

    TRACK_INFO_COMMAND(new TrackInfoCommandImpl(new TrackReceiver(), new PerformerReceiver())),
    COMMENT_ADD_COMMAND(new CommentAddCommandImpl(new CommentReceiver())),

    BUY_TRACK_COMMAND(new TrackBuyCommandImpl(new UserReceiver())),

    GETTING_TRACK_COMMAND(new GettingTrackCommandImpl(new TrackReceiver())),

    OWN_TRACK_COMMAND(new GettingOwnTrackCommandImpl(new TrackReceiver())),
    OWN_ALBUM_COMMAND(new GettingOwnAlbumCommandImpl(new AlbumReceiver())),
    OWN_ASSEMBLAGE_COMMAND(new GettingOwnAssemblageCommandImpl(new AssemblageReceiver()));

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
