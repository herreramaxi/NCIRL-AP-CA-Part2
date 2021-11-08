/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository;

import Interfaces.IEventRepository;
import Model.Event;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Maximiliano Herrera
 */
public class InMemoryDatabase implements IEventRepository {

    private HashMap<LocalDate, SortedEventsList> _hashMap = new HashMap<>();

    //Time complexity tree: O(logN)
    @Override
    public synchronized void add(Event event) {
        if (!_hashMap.containsKey(event.getDate())) {
            _hashMap.put(event.getDate(), new SortedEventsList());
        }

        _hashMap.get(event.getDate()).add(event);
    }

    //Time complexity tree: O(N)
    @Override
    public synchronized boolean remove(Event event) {
        if (!_hashMap.containsKey(event.getDate())) {
            return false;
        }

        return _hashMap.get(event.getDate()).remove(event);
    }

    //Time complexity tree: O(1)
    @Override
    public synchronized ArrayList<Event> getEventsForDate(LocalDate date) {
        if (!_hashMap.containsKey(date)) {
            return new ArrayList<>();
        }

        return _hashMap.get(date);
    }

    @Override
    public synchronized List<Event> GetAllEvents() {
        return _hashMap.values().stream()
                .flatMap(List::stream)
                .sorted()
                .collect(Collectors.toList());
    }

    @Override
    public synchronized void CleanAllInMemoryData() {
        _hashMap = new HashMap<>();       
    }
}
