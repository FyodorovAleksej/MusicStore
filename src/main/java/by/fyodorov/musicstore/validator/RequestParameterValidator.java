package by.fyodorov.musicstore.validator;

import by.fyodorov.musicstore.application.RequestArgument;

public class RequestParameterValidator {

    public boolean validateUserName(String name) {
        return validate(name, RequestArgument.LOGIN);
    }

    public boolean validatePassword(String password) {
        return validate(password, RequestArgument.PASSWORD);
    }

    public boolean validateEmail(String email) {
        return validate(email, RequestArgument.EMAIL);
    }


    private boolean validate(String value, RequestArgument argumentType) {
        if (value == null) {
            return false;
        }
        String pattern = argumentType.getPattern();
        return pattern == null || value.matches(pattern);
    }
}
