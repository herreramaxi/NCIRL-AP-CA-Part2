/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResponseCommands;

import UI.UIMediator;
import java.io.IOException;

/**
 *
 * @author Maximiliano Herrera
 */
public abstract class ResponseCommand {

    protected String _response;

    protected ResponseCommand(String response) {
        _response = response;
    }

    public abstract void Execute(UIMediator uiMediator) throws IOException;
}
