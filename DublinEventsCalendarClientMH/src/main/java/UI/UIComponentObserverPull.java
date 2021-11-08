/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Interfaces.IObserverPull;
import javax.swing.SwingUtilities;

/**
 *
 * @author Maximiliano Herrera
 */
public class UIComponentObserverPull implements IObserverPull {

    private final Runnable _function;

    public UIComponentObserverPull(Runnable function) {
        _function = function;
    }

    @Override
    public void update() {
        SwingUtilities.invokeLater(_function);
    }
}
