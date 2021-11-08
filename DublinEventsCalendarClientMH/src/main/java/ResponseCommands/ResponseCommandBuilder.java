/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResponseCommands;

import Model.MessageType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Maximiliano Herrera
 */
public class ResponseCommandBuilder {

    public ResponseCommand Build(String response) {
        if (response == null || response.isEmpty()) {
            return new ErrorCommand("Response is null or empty", response);
        }

        StringTokenizer st = new StringTokenizer(response, ";");

        if (st.countTokens() == 0) {
            return new ErrorCommand("No tokens detected from response", response);
        }

        ArrayList<String> tokens = new ArrayList<String>();

        while (st.hasMoreTokens()) {
            tokens.add(st.nextToken());
        }

        String messageType = tokens.get(0).toUpperCase();

        ResponseCommand command;
        if (messageType.equals(MessageType.ERROR.toString())) {
            String errorMessage = tokens.size() > 1 ? tokens.get(1) : "Unknown error";
            command = new ErrorCommand(errorMessage, response);
        } else if (messageType.equals(MessageType.TERMINATE.toString())) {
            command = new TerminateCommand(response);
        } else {

            try {
                String dateAsString = tokens.get(0);
                LocalDate date = LocalDate.parse(dateAsString, DateTimeFormatter.ISO_DATE);

                command = new SuccessCommand(date, tokens, response);
            } catch (Exception ex) {
                return new ErrorCommand("Error when parsing successCommand: " + tokens.toString(), response);
            }
        }

        return command;
    }
}
