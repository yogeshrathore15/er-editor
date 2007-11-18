package ru.soultakov.remotecontrol.mail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;

import org.apache.log4j.Logger;

import com.sun.mail.pop3.POP3Folder;

public class MailService {

    private static final Logger LOGGER = Logger.getLogger(MailService.class);

    private Properties properties;

    private String username;
    private String password;

    private Set<String> readMessages;

    @SuppressWarnings("unchecked")
    public MailService() {
        try {
            final ObjectInputStream ois = new ObjectInputStream(new FileInputStream("temp_storage"));
            readMessages = (Set<String>) ois.readObject();
        } catch (final Exception e) {
            readMessages = new HashSet<String>();
        }
    }

    public <T> List<T> receiveUnreadMessages(IMessageProcessor<T> processor)
            throws MessagingException, IOException {
        final Session session = Session.getDefaultInstance(properties, null);
        final Store store = session.getStore();
        try {
            store.connect(username, password);
            LOGGER.info("Connected to '" + username + "'");
            final Folder inbox = store.getFolder("INBOX");
            try {
                inbox.open(Folder.READ_ONLY);
                LOGGER.info("INBOX was successfully opened");
                LOGGER.info("Getting unread messages...");
                final ArrayList<Message> unreadMessages = new ArrayList<Message>();
                for (final Message m : inbox.getMessages()) {
                    if (isUnread(inbox, m)) {
                        unreadMessages.add(m);
                    }
                }
                LOGGER.info("There are " + unreadMessages.size() + " unread messages");
                return processor.processMessages(unreadMessages);
            } finally {
                LOGGER.info("INBOX closed");
                inbox.close(false);
            }
        } finally {
            store.close();
        }
    }

    private boolean isUnread(Folder inbox, final Message m) throws MessagingException {
        if (inbox instanceof POP3Folder) {
            final POP3Folder pop3Inbox = (POP3Folder) inbox;
            final String uid = pop3Inbox.getUID(m);
            return readMessages.add(uid);
        }
        return true;
    }

    public void close() throws FileNotFoundException, IOException {
        LOGGER.info("Storing unread messages");
        final ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(
                "temp_storage"));
        try {
            objectOutputStream.writeObject(readMessages);
        } finally {
            objectOutputStream.close();
        }
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
