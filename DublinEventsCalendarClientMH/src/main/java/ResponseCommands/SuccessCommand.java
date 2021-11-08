/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResponseCommands;

import Model.Event;
import UI.UIMediator;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Maximiliano Herrera
 */
public class SuccessCommand extends ResponseCommand {

    private final LocalDate _date;
    private ArrayList<Event> _events = new ArrayList<>();

    public SuccessCommand(LocalDate date, ArrayList<String> tokens, String response) {
        super(response);
        _date = date;

        for (String token : tokens) {
            StringTokenizer st = new StringTokenizer(token, ",");

            if (st.countTokens() < 2)
                continue;

            ArrayList<String> subTokens = new ArrayList<>();
            while (st.hasMoreTokens()) {
                subTokens.add(st.nextToken());
            }

            try {
                LocalTime time = LocalTime.parse(subTokens.get(0));
                Event event = new Event(_date, time, subTokens.get(1));
                _events.add(event);

            } catch (Exception ex) {
                System.out.println("Error while parsing subtokens");
            }
        }
    }

    public ArrayList<Event> getEvents() {
        return _events;
    }

    @Override
    public void Execute(UIMediator uiMediator) {
        StringBuilder sb = new StringBuilder();
        sb.append("Response: ").append(_response).append("\n");
        sb.append("Date: ").append(_date).append("\n");
        sb.append("Events: ").append(_events.isEmpty() ? "N/A" : "\n");

        _events.forEach(event -> {
            sb.append(event.toString()).append("\n");
        });

        uiMediator.SetOutputResult(_response, sb.toString());
    }
}
