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

public class StopCommand extends EventCommand {

    @Override
    public void Execute(TCPConnection tCPConnection, IEventService service) throws IOException {
        System.out.println("sending terminate response");
        tCPConnection.WriteMessage(MessageType.TERMINATE.toString());      
    }

    @Override
    public boolean isStopCommand() {
        return true;
    }
}
