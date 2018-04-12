package test.fyodorov.musicstore.validator;

import by.fyodorov.musicstore.model.UserEntity;
import by.fyodorov.musicstore.validator.RequestParameterValidator;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Optional;

import static org.testng.Assert.*;

public class RequestParameterValidatorTest {

    @Test
    public void testValidateUserName() throws Exception {
        final String user = "Akela";
        RequestParameterValidator validator = new RequestParameterValidator();
        Assert.assertTrue(validator.validateUserName(user));
    }

    @Test
    public void testValidatePassword() throws Exception {
        final String password = "Akela";
        RequestParameterValidator validator = new RequestParameterValidator();
        Assert.assertTrue(validator.validatePassword(password));
    }

    @Test
    public void testValidateEmail() throws Exception {
        final String mail = "Fyodorov.aleksej@gmail.com";
        RequestParameterValidator validator = new RequestParameterValidator();
        Assert.assertTrue(validator.validateEmail(mail));
    }
}