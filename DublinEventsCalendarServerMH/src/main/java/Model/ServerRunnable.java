/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Interfaces.IEventService;
import Interfaces.IObserverPull;
import Interfaces.ISubject;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Maximiliano Herrera
 */
public class ServerRunnable implements Runnable, ISubject {

    private ServerSocket _servSock;
    private IEventService _service;
    private int _connections;
    private ArrayList<String> _receivedMessages = new ArrayList<>();
    private ArrayList<IObserverPull> _Observer = new ArrayList<>();

    public ServerRunnable(int port, IEventService service) throws UIException {  
        try {
            _servSock = new ServerSocket(port);
            _service = service;

            System.out.println("Listening on port " + port);
        } catch (IOException e) {
            String errorMessage = "Unable to attach to port: " + port;
            System.out.println(errorMessage);
            throw new UIException(errorMessage);
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Socket link = _servSock.accept();

                ConnectionHandler connectionHandler = new ConnectionHandler(link, _service, this);
                Thread thread = new Thread(connectionHandler);
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("ServerRunnable thread has been interrupted, finishing...");
    }

    public synchronized int incrementConnection() {
        _connections++;
        Notify(ObserverType.Connections);

        return _connections;
    }

    public synchronized void decrementConnection() {
        if (_connections == 0)
            return;

        _connections--;
        Notify(ObserverType.Connections);
    }

    public synchronized void addReceivedMessage(String message) {
        _receivedMessages.add(message);
        Notify(ObserverType.ReceivedMessages);
    }

    public synchronized void cleanAllReceivedMessage() {
        _receivedMessages.clear();
        Notify(ObserverType.ReceivedMessages);
    }

    @Override
    public void attach(IObserverPull observer) {
        _Observer.add(observer);
    }

    @Override
    public void Notify(ObserverType observerType) {
        if (_Observer == null)
            return;

        _Observer.stream()
                .forEach(x -> x.update(observerType));
    }

    public synchronized int getActiveConnections() {
        return _connections;
    }

    public synchronized ArrayList<String> getReceivedMessages() {
        return _receivedMessages;
    }

    public List<Event> getInMemoryDBEvents() {
        return _service.GetEvents();
    }
}
