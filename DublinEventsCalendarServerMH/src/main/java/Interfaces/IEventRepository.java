/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interfaces;

import Model.Event;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maximiliano Herrera
 */
public interface IEventRepository {

    public void add(Event event);

    public boolean remove(Event event);

    public ArrayList<Event> getEventsForDate(LocalDate date);

    public List<Event> GetAllEvents();

    public void CleanAllInMemoryData();
}
