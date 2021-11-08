/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 *
 * @author Maximiliano Herrera
 */
public class Event {

    private LocalDate _date;

    public LocalDate getDate() {
        return _date;
    }

    public LocalTime getTime() {
        return _time;
    }

    public String getEventName() {
        return _eventName;
    }
    private LocalTime _time;
    private String _eventName;

    @Override
    public String toString() {
        return _date + ";" + _time + ";" + _eventName + ";";
    }

    public Event(LocalDate date, LocalTime time, String eventName) {
        _date = date;
        _time = time;
        _eventName = eventName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj.getClass() != this.getClass()) {
            return false;
        }

        final Event other = (Event) obj;

        if ((_eventName == null) ? (other.getEventName() != null) : !_eventName.equals(other.getEventName())) {
            return false;
        }

        if (!_date.isEqual(other.getDate())) {
            return false;
        }

        if (!_time.equals(other.getTime())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(_date);
        hash = 79 * hash + Objects.hashCode(_time);
        hash = 79 * hash + Objects.hashCode(_eventName);
        return hash;
    }
}
