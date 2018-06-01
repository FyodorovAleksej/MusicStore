package by.fyodorov.musicstore.validator;

import by.fyodorov.musicstore.application.RequestArgument;

/**
 * class for validate parameters
 */
public class RequestParameterValidator {

    /**
     * validate login
     * @param name - login to validate
     * @return - is login valid?
     */
    public boolean validateUserName(String name) {
        return validate(name, RequestArgument.LOGIN);
    }

    /**
     * validate password
     * @param password - password to validate
     * @return - is password valid?
     */
    public boolean validatePassword(String password) {
        return validate(password, RequestArgument.PASSWORD);
    }

    /**
     * validate email
     * @param email - email to validate
     * @return - is email valid?
     */
    public boolean validateEmail(String email) {
        return validate(email, RequestArgument.EMAIL);
    }

    /**
     * validate integer
     * @param integer - integer to validate
     * @return - is integer valid?
     */
    public boolean validateInteger(String integer) {
        return validate(integer, RequestArgument.TRACK_ADD_PRICE);
    }

    /**
     * validate price
     * @param price - price to validate
     * @return - is price valid?
     */
    public boolean validatePrice(String price) {
        return validateInteger(price) && Integer.valueOf(price) >= 0;
    }


    /**
     * validate argument
     * @param value - string value of argument
     * @param argumentType - type of argument for getting pattern
     * @return - is argument valid?
     */
    public boolean validate(String value, RequestArgument argumentType) {
        if (value == null) {
            return false;
        }
        String pattern = argumentType.getPattern();
        return pattern == null || value.matches(pattern);
    }
}
