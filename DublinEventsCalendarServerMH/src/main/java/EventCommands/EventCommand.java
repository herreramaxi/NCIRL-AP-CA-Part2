/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventCommands;

import Interfaces.IEventService;
import Service.EventService;
import Model.Event;
import Model.TCPConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author Maximiliano Herrera
 */
public abstract class EventCommand {

    private LocalDate _date;
    private LocalTime _time;
    private String _message;

    public LocalDate getDate() {
        return _date;
    }

    public void setDate(LocalDate _date) {
        this._date = _date;
    }

    public LocalTime getTime() {
        return _time;
    }

    public void setTime(LocalTime _time) {
        this._time = _time;
    }

    public String getMessage() {
        return _message;
    }

    public void setMessage(String _message) {
        this._message = _message;
    }

    public abstract void Execute(TCPConnection tCPConnection, IEventService service) throws IOException;

    public boolean isStopCommand() {
        return false;
    }

    protected String SerializeEvents(LocalDate date, ArrayList<Event> events) {
        StringBuilder sb = new StringBuilder();
        
        sb.append(date).append(";");
        
        for (Event event : events) {
           sb.append(event.getTime()).append(",");
           sb.append(event.getEventName()).append(";");
        }        
        
        return sb.toString();
    }
}
