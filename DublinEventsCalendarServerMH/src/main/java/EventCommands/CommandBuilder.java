/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventCommands;

import Model.IncorrectActionException;
import Model.MessageType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Maximiliano Herrera
 */
public class CommandBuilder {

    public EventCommand Build(String message) throws Exception {
        if (message == null || message.length() == 0)
            throw new Exception("Wrong format in message");

        System.out.println("Message received: " + message);

        StringTokenizer st = new StringTokenizer(message, ";");
        ArrayList<String> tokens = new ArrayList<>();

        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            System.out.println(token);
            tokens.add(token);
        }

        if (tokens.isEmpty())
            throw new Exception("Wrong format in message");

        String action = tokens.get(0);
        MessageType messageType = this.getMessageType(action);
        EventCommand eventCommand;

        System.out.println("Message type: " + messageType);
        try {
            switch (messageType) {
                case ADD:
                case REMOVE:
                    if (tokens.size() < 4)
                        throw new Exception("Wrong format on " + messageType + " command");

                    eventCommand = messageType == MessageType.ADD ? new AddCommand() : new RemoveCommand();
                    LocalDate date = LocalDate.parse(tokens.get(1));
                    eventCommand.setDate(date);
                    LocalTime time = LocalTime.parse(tokens.get(2));
                    eventCommand.setTime(time);
                    eventCommand.setMessage(tokens.get(3));
                    break;
                case STOP:
                    eventCommand = new StopCommand();
                    break;
                default:
                    //This is for requirements, I normally follow Special case pattern
                    throw new IncorrectActionException("Incorrect action: " + action);
            }

        } catch (IncorrectActionException exception) {
            return new IncorrectActionCommand(exception.getMessage());
        }

        return eventCommand;
    }

    private MessageType getMessageType(String action) {
        try {
            return MessageType.valueOf(action.toUpperCase());
        } catch (Exception ex) {
            return MessageType.INCORRECT_ACTION;
        }
    }
}
