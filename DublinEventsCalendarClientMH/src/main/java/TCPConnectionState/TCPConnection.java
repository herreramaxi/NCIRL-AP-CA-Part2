/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPConnectionState;

import Interfaces.IObserverPull;
import Interfaces.ISubject;
import java.io.IOException;
import java.net.InetAddress;

/**
 *
 * @author Maximiliano Herrera
 */
public class TCPConnection implements ISubject{

    public int getPort() {
        return _port;
    }

    public InetAddress getHost() {
        return _host;
    }
    
    private TCPState _state;
    private final int _port;
    private final InetAddress _host;
    private IObserverPull _observer;
    
    public TCPConnection(InetAddress host, int port) {
        _host = host;
        _port = port;
        _state = new TCPClosed();
    }

    public String ReadMessage() throws IOException {
        return _state.ReadMessage(this);
    }

    public void WriteMessage(Object object) throws IOException {
        _state.WriteMessage(this, object);
    }

    public void Close() {
        _state.Close(this);
    }

    public void ChangeState(TCPState state) {
        _state = state;
    }
    
     @Override
    public void attach(IObserverPull observer) {
       _observer = observer;
    }

    @Override
    public void Notify() {
        if (_observer == null)
            return;

        _observer.update();
    }

    public String GetConnectionStatus() {
      return  _state.GetConnectionStatus();
    }
}
