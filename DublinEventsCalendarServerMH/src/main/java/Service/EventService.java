/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Interfaces.IEventRepository;
import Interfaces.IEventService;
import Interfaces.IObserverPull;
import Interfaces.ISubject;
import Model.Event;
import Model.ObserverType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maximiliano Herrera
 */
public class EventService implements IEventService, ISubject {

    private final IEventRepository _repository;
    private IObserverPull _observer;

    public EventService(IEventRepository repository) {
        _repository = repository;
    }

    @Override
    public ArrayList<Event> AddEvent(LocalDate date, LocalTime time, String eventName) {
        Event anEvent = new Event(date, time, eventName);
        _repository.add(anEvent);

        System.out.println("Event added: " + anEvent);
        this.Notify(ObserverType.Events);

        return _repository.getEventsForDate(date);
    }

    @Override
    public ArrayList<Event> RemoveEvent(LocalDate date, LocalTime time, String eventName) {
        Event anEvent = new Event(date, time, eventName);
        boolean removed = _repository.remove(anEvent);
        System.out.println("Event removed: " + removed);
        this.Notify(ObserverType.Events);

        return _repository.getEventsForDate(date);
    }

    @Override
    public List<Event> GetEvents() {
        return _repository.GetAllEvents();
    }

    @Override
    public void Notify(ObserverType observerType) {
        if (_observer == null)
            return;

        _observer.update(observerType);
    }

    @Override
    public void attach(IObserverPull observer) {
        _observer = observer;
    }

    public void CleanAllInMemoryData() {
        _repository.CleanAllInMemoryData();
        this.Notify(ObserverType.Events);
    }
}
