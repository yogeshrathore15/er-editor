package ru.soultakov.remotecontrol.mail;

import java.io.IOException;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;

public interface IMessageProcessor<T> {

    List<T> processMessages(List<Message> messages) throws MessagingException, IOException;

}
