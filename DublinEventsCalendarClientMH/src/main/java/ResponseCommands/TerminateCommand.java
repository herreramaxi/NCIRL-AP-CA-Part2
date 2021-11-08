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
public class TerminateCommand extends ResponseCommand {

    public TerminateCommand(String response) {
        super(response);
    }

    @Override
    public void Execute(UIMediator uiMediator) throws IOException {
        uiMediator.getTcpConnection().Close();
        uiMediator.SetOutputResult(_response, _response);
    }
}
