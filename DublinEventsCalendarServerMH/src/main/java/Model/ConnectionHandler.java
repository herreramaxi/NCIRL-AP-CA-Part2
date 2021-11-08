/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Service.EventService;
import EventCommands.CommandBuilder;
import EventCommands.EventCommand;
import Interfaces.IEventService;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maximiliano Herrera
 */
public class ConnectionHandler implements Runnable {

    private final TCPConnection _tcpConnection;
    private final CommandBuilder _builder;
    private final IEventService _service;
    private final ServerRunnable _parent;

    public ConnectionHandler(Socket link, IEventService service, ServerRunnable parent) throws IOException {
        _tcpConnection = new TCPConnection(link);
        _builder = new CommandBuilder();
        _service = service;
        _parent = parent;
    }

    @Override
    public void run() {
        try {
            int connectionId = _parent.incrementConnection();

            boolean isStopCommand = false;

            while (!isStopCommand) {
                String message = _tcpConnection.ReadMessage();
                _parent.addReceivedMessage("ConnectionId: " + connectionId + ", message: " + message);

                EventCommand command = _builder.Build(message);
                command.Execute(_tcpConnection, _service);
                isStopCommand = command.isStopCommand();
            }
        } catch (IOException ex) {
            Logger.getLogger(ConnectionHandler.class.getName()).log(Level.WARNING, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ConnectionHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            System.out.println("decrementing connections, closing tcp, and finishing thread");
            _tcpConnection.Close();
            _parent.decrementConnection();          
        }
    }
}
