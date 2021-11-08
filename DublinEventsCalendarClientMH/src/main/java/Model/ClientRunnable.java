/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import UI.UIMediator;
import ResponseCommands.ResponseCommand;
import ResponseCommands.ResponseCommandBuilder;
import TCPConnectionState.TCPConnection;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maximiliano Herrera
 */
public class ClientRunnable implements Runnable {

    private final TCPConnection _tcpConnection;
    private final BlockingQueue _queue;
    private final ResponseCommandBuilder _builder;
    private final UIMediator _mediator;

    public ClientRunnable(TCPConnection tcpConnection, BlockingQueue queue, UIMediator mediator) {
        _tcpConnection = tcpConnection;
        _queue = queue;
        _mediator = mediator;
        _builder = new ResponseCommandBuilder();
    }

    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            try {
                System.out.println("waiting for a queue message");
                String message = (String) _queue.take();
                System.out.println("dequeued message: " + message);
                
                _tcpConnection.WriteMessage(message);

                String response = _tcpConnection.ReadMessage();
                ResponseCommand command = _builder.Build(response);
                
                System.out.println("Executing command: " +  command.getClass());
                command.Execute(_mediator);

            } catch (InterruptedException ex) {
                Logger.getLogger(ClientRunnable.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ClientRunnable.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("IOException occured, closing tcp connection");
                _tcpConnection.Close();
                _mediator.ShowErrorDialog("Error when trying to communicate with server");
            }
        }
    }

}
