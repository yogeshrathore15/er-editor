package ru.soultakov.remotecontrol.mail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

public class MailService {

    private Properties properties;

    private String username;
    private String password;

    // public void send(String to, String subject, String body) {
    // try {
    // final Properties props = new Properties();
    // final Session session = Session.getDefaultInstance(props, null);
    // final Message message = new MimeMessage(session);
    // message.setFrom(new InternetAddress(from));
    // message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to,
    // false));
    // message.setSubject(subject);
    // message.setText(body);
    // // -- Set some other header information --
    // message.setHeader("X-Mailer", "LOTONtechEmail");
    // message.setSentDate(new Date());
    // // -- Send the message --
    // Transport.send(message);
    // System.out.println("Message sent OK.");
    // } catch (final Exception ex) {
    // ex.printStackTrace();
    // }
    // }

    public <T> List<T> receiveUnreadMessages(IMessageProcessor<T> processor)
            throws MessagingException, IOException {
        final Session session = Session.getDefaultInstance(properties, null);
        final Store store = session.getStore();
        try {
            store.connect(username, password);
            final Folder inbox = store.getFolder("INBOX");
            try {
                inbox.open(Folder.READ_ONLY);
                final ArrayList<Message> unreadMessages = new ArrayList<Message>();
                for (final Message m : inbox.getMessages()) {
                    if (!m.getFlags().contains(Flags.Flag.SEEN)) {
                        unreadMessages.add(m);
                    }
                }
                return processor.processMessages(unreadMessages);
            } finally {
                inbox.close(false);
            }
        } finally {
            store.close();
        }
    }

    public Properties getProperties() {
        return this.properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
