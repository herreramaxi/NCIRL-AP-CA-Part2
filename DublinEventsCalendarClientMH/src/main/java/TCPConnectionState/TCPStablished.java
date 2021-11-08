/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPConnectionState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Maximiliano Herrera
 */
public class TCPStablished extends TCPState {

    private final Socket _link;
    private final BufferedReader _in;
    private final PrintWriter _out;

    public TCPStablished(TCPConnection tCPConnection) throws IOException {
        _link = new Socket(tCPConnection.getHost(), tCPConnection.getPort());
        _in = new BufferedReader(new InputStreamReader(_link.getInputStream()));
        _out = new PrintWriter(_link.getOutputStream(), true);

        tCPConnection.Notify();
    }

    @Override
    public String ReadMessage(TCPConnection tCPConnection) throws IOException {
        return _in.readLine();
    }

    @Override
    public void WriteMessage(TCPConnection tCPConnection, Object object) {
        _out.println(String.valueOf(object));
    }

    @Override
    public void Close(TCPConnection tCPConnection) {
        try {
            _in.close();
            _out.close();
            _link.close();
            System.out.println("TCPConnection resources closed");
        } catch (IOException ex) {
            Logger.getLogger(TCPStablished.class.getName()).log(Level.SEVERE, "Error when closing TCPConnection resources", ex);
        }

        tCPConnection.ChangeState(new TCPClosed());
        tCPConnection.Notify();
    }

    @Override
    public String GetConnectionStatus() {
        return "Connected";
    }
}
