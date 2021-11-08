/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Interfaces.IObserverPull;
import Model.ObserverType;
import javax.swing.SwingUtilities;

/**
 *
 * @author Maximiliano Herrera
 */
public class UIComponentObserverPull implements IObserverPull {

    private final Runnable _function;
    private final ObserverType _observerEventType;

    public ObserverType getObserverEventType() {
        return _observerEventType;
    }

    public UIComponentObserverPull(Runnable function, ObserverType observerEventType) {
        _function = function;
        _observerEventType = observerEventType;
    }

    @Override
    public void update(ObserverType observerEventType) {
        if (observerEventType != _observerEventType)
            return;

        SwingUtilities.invokeLater(_function);
    }
}
