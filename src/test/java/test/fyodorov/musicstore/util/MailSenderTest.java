package test.fyodorov.musicstore.util;

import by.fyodorov.musicstore.util.MailSender;
import org.testng.annotations.Test;

public class MailSenderTest {

    @Test
    public void testMailSend() throws Exception {

        MailSender sender = new MailSender("mailOffline.properties");
        sender.send("Hello world", "Fyodorov.aleksej@gmail.com");
    }
}
