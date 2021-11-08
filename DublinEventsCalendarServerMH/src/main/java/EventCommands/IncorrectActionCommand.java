/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventCommands;

import Interfaces.IEventService;
import Model.MessageType;
import Model.TCPConnection;
import java.io.IOException;

/**
 *
 * @author Maximiliano Herrera
 */
public class IncorrectActionCommand extends EventCommand {

    private final String _errorMessage;

    public IncorrectActionCommand(String errorMessage) {
        _errorMessage = errorMessage;
    }

    @Override
    public void Execute(TCPConnection tCPConnection, IEventService service) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(MessageType.ERROR.toString()).append(";");
        sb.append(_errorMessage).append(";");
     
        tCPConnection.WriteMessage(sb.toString());
    }
}
