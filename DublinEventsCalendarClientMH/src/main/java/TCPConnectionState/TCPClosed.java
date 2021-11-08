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
public class TCPClosed extends TCPState {
   
    @Override
    public String ReadMessage(TCPConnection tCPConnection) throws IOException {
        tCPConnection.ChangeState(new TCPStablished(tCPConnection));
        return tCPConnection.ReadMessage();
    }

    @Override
    public void WriteMessage(TCPConnection tCPConnection, Object object) throws IOException{
         tCPConnection.ChangeState(new TCPStablished(tCPConnection));
         tCPConnection.WriteMessage(object);
    }

    @Override
    public void Close(TCPConnection tCPConnection) {        
    }    

    @Override
    public String GetConnectionStatus() {
          return "Disconnected";
    }
}