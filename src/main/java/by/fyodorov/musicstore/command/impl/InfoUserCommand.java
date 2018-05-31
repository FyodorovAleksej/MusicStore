package by.fyodorov.musicstore.command.impl;

import by.fyodorov.musicstore.application.PagesUrl;
import by.fyodorov.musicstore.application.RequestArgument;
import by.fyodorov.musicstore.model.UserRole;
import by.fyodorov.musicstore.command.*;
import by.fyodorov.musicstore.connector.ConnectorException;
import by.fyodorov.musicstore.model.UserBonusType;
import by.fyodorov.musicstore.model.UserEntity;
import by.fyodorov.musicstore.receiver.UserReceiver;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * getting information about selected user
 */
public class InfoUserCommand implements Command {
    private UserReceiver userReceiver;

    /**
     * created command with user receiver
     * @param userReceiver - receiver for working with users
     */
    InfoUserCommand(UserReceiver userReceiver) {
        this.userReceiver = userReceiver;
    }

    /**
     * performing command. Prepare information about selected user
     * @param requestInfo - map with arguments of request and session
     * @return - command of forward or redirect
     * @throws CommandException - when command can't perform
     */
    @Override
    public GoToInterface perform(RequestParameterMap requestInfo) throws CommandException {
        Optional<String> path = Optional.empty();
        String userRole = (String) requestInfo.getSessionAttribute(RequestArgument.SESSION_ROLE.getName());
        String infoName = requestInfo.getParameter(RequestArgument.USER_NAME_INFO.getName());
        try {
            if (UserRole.ADMIN.toString().equals(userRole) && infoName != null) {
                UserEntity userEntity = userReceiver.findUser(infoName);
                if (userEntity != null) {
                    requestInfo.setSessionAttribute(RequestArgument.USER_INFO.getName(), infoName);
                    requestInfo.setRequestAttribute(RequestArgument.USER_ROLE_USER_INFO.getName(), decodeRole(userEntity.getRole(), UserRole.USER));
                    requestInfo.setRequestAttribute(RequestArgument.USER_ROLE_ADMIN_INFO.getName(), decodeRole(userEntity.getRole(), UserRole.ADMIN));
                    requestInfo.setRequestAttribute(RequestArgument.USER_BONUS_TRACK_INFO.getName(), decodeBonus(userEntity.getBonus(), UserBonusType.USER_BONUS_TRACK.getValue()));
                    requestInfo.setRequestAttribute(RequestArgument.USER_BONUS_ALBUM_INFO.getName(), decodeBonus(userEntity.getBonus(), UserBonusType.USER_BONUS_ALBUM.getValue()));
                    requestInfo.setRequestAttribute(RequestArgument.USER_BONUS_ASSEMBLAGE_INFO.getName(), decodeBonus(userEntity.getBonus(), UserBonusType.USER_BONUS_ASSEMBLAGE.getValue()));
                    requestInfo.setRequestAttribute(RequestArgument.USER_DISCOUNT_INFO.getName(), userEntity.getDiscount());
                    path = Optional.of(PagesUrl.USER_INFO_PAGE.getPath());
                }
            }
        } catch (ConnectorException e) {
            throw new CommandException("can't connect to find user", e);
        }
        return new ForwardGoTo(path.orElse(PagesUrl.MAIN_PAGE.getPath()));
    }

    @Override
    public void refresh(HttpServletRequest request) {

    }

    private String decodeRole(String userRole, UserRole role) {
        if (role.toString().equals(userRole)) {
            return "selected";
        }
        return "";
    }

    private String decodeBonus(String bonus, int code) {
        if ((UserBonusType.toBonusType(bonus) & code) != 0) {
            return "checked";
        }
        return "";
    }
}
