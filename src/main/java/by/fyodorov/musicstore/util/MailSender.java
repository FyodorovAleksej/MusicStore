package by.fyodorov.musicstore.util;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * class of sender message by email
 */
public class MailSender {
    private Properties mailProps;
    private static final String SUBJECT_KEY = "subject";
    private static final String TRANSPORT_KEY = "mail.transport.protocol";
    private static final String SEPARATOR = ".";
    private static final String MAIL_KEY = "mail";
    private static final String USER_KEY = "user";
    private static final String PASSWORD_KEY = "password";
    private static final String DOMEN_KEY = "domen";

    /**
     * creating sender with path to properties file
     * @param path - properties file
     * @throws MailException - when can't open or read from properties file
     */
    public MailSender(String path) throws MailException {
        Properties props = new Properties();
        try {
            props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(path));
        } catch (FileNotFoundException e) {
            throw new MailException("can't find file = \"" + path + "\"", e);
        } catch (IOException e) {
            throw new MailException("can't read file = \"" + path + "\"", e);
        }
        mailProps = props;
    }

    /**
     * send message with text to email
     * @param text - text of message
     * @param address - email to send
     * @throws MailException - when can't send message
     */
    public void send(String text, String address) throws MailException {
        try {
            Session mailSession = Session.getDefaultInstance(mailProps);
            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(mailProps.getProperty(MAIL_KEY + SEPARATOR + mailProps.getProperty(TRANSPORT_KEY) + SEPARATOR + USER_KEY)));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(address));
            message.setSubject(mailProps.getProperty(SUBJECT_KEY));
            message.setText(text);

            Transport transport = mailSession.getTransport();
            transport.connect(mailProps.getProperty(mailProps.getProperty(MAIL_KEY + SEPARATOR + mailProps.getProperty(TRANSPORT_KEY) + SEPARATOR + USER_KEY)), mailProps.getProperty(MAIL_KEY + SEPARATOR + mailProps.getProperty(TRANSPORT_KEY) + SEPARATOR + PASSWORD_KEY));
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            throw new MailException("Can't send message", e);
        }
    }

    /**
     * sending mail with text and url to address
     * @param text - text of message
     * @param url - url to append to message
     * @param address - address to send message
     * @throws MailException - when can't sending message
     */
    public void sendUrl(String text, String url, String address) throws MailException {
        try {
            Session mailSession = Session.getDefaultInstance(mailProps);
            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(mailProps.getProperty(MAIL_KEY + SEPARATOR + mailProps.getProperty(TRANSPORT_KEY) + SEPARATOR + USER_KEY)));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(address));
            message.setSubject(mailProps.getProperty(SUBJECT_KEY));
            message.setContent(String.format("<p>%s<br><br>" + "<a href=\"%s\">%s</a>", text, mailProps.getProperty(DOMEN_KEY) + url, mailProps.getProperty(DOMEN_KEY) + url), "text/html; charset=utf-8");
            message.setText(String.format("<p>%s<br><br>" + "<a href=\"%s\">%s</a>", text, mailProps.getProperty(DOMEN_KEY) + url, mailProps.getProperty(DOMEN_KEY) + url), "utf-8", "html");

            Transport transport = mailSession.getTransport();
            transport.connect(mailProps.getProperty(mailProps.getProperty(MAIL_KEY + SEPARATOR + mailProps.getProperty(TRANSPORT_KEY) + SEPARATOR + USER_KEY)), mailProps.getProperty(MAIL_KEY + SEPARATOR + mailProps.getProperty(TRANSPORT_KEY) + SEPARATOR + PASSWORD_KEY));
            transport.sendMessage(message, message.getAllRecipients());
            message.getAllRecipients();
            transport.close();
        } catch (MessagingException e) {
            throw new MailException("Can't send message", e);
        }
    }
}
