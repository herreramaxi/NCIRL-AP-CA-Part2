/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Model.Event;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maximiliano Herrera
 */
public interface IEventService {

    public ArrayList<Event> AddEvent(LocalDate date, LocalTime time, String eventName);

    public ArrayList<Event> RemoveEvent(LocalDate date, LocalTime time, String eventName);

    public List<Event> GetEvents();
}
