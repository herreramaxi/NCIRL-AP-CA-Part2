/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

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
public class TCPConnection {

    private final Socket _link;
    private final BufferedReader _in;
    private final PrintWriter _out;

    public TCPConnection(Socket link) throws IOException {
        _link = link;
        _in = new BufferedReader(new InputStreamReader(_link.getInputStream()));
        _out = new PrintWriter(_link.getOutputStream(), true);
    }

    public String ReadMessage() throws IOException {
        return _in.readLine();
    }

    public void WriteMessage(Object object) {
        _out.println(String.valueOf(object));
    }

    public void Close() {
        try {
            _in.close();
            _out.close();
            _link.close();
        } catch (IOException ex) {
            Logger.getLogger(TCPConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}