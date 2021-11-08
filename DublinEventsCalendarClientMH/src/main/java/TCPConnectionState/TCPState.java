/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCPConnectionState;

import java.io.IOException;

/**
 *
 * @author Maximiliano Herrera
 */
public abstract class TCPState {

    public abstract String ReadMessage(TCPConnection tCPConnection) throws IOException;

    public abstract void WriteMessage(TCPConnection tCPConnection, Object object) throws IOException;

    public abstract void Close(TCPConnection tCPConnection);

    public abstract String GetConnectionStatus();
}
