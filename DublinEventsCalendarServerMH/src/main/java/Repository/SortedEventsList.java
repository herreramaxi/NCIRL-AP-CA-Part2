/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repository;

import Model.Event;
import java.util.ArrayList;

/**
 *
 * @author Maximiliano Herrera
 */

//The idea of this class is to prevent sorting the events when retrieving them, which is O(nlogn)
public class SortedEventsList extends ArrayList<Event>{

    //Time complexity: O(N)
    @Override
    public boolean add(Event anEvent) {
        for (int i = 0; i < this.size(); i++) {
            Event e = this.get(i);

            int comparison = anEvent.getTime().compareTo(e.getTime());

            if (comparison < 0) {
                super.add(i, anEvent);
                return true;
            }

            if (comparison == 0)
                return false;
        }

        return super.add(anEvent);
    }  
}
