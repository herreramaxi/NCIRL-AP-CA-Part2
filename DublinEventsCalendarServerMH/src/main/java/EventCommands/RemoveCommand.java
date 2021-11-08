/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventCommands;

import Interfaces.IEventService;
import Model.Event;
import Model.TCPConnection;
import java.util.ArrayList;

public class RemoveCommand extends EventCommand {

    @Override
    public void Execute(TCPConnection tCPConnection, IEventService service) {
        ArrayList<Event> events = service.RemoveEvent(this.getDate(), this.getTime(), this.getMessage());

        String message = this.SerializeEvents(this.getDate(), events);
        tCPConnection.WriteMessage(message);
    }
}
