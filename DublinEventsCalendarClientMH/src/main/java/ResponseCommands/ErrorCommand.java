/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ResponseCommands;

import UI.UIMediator;

/**
 *
 * @author Maximiliano Herrera
 */
public class ErrorCommand extends ResponseCommand {

    private final String _errorMessage;

    public ErrorCommand(String errorMessage, String response) {
        super(response);
        _errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return _errorMessage;
    }

    @Override
    public void Execute(UIMediator uiMediator) {
        uiMediator.getTcpConnection().Close();

        StringBuilder sb = new StringBuilder();
        sb.append("Response: ").append(_response).append("\n");
        sb.append("Error message: ").append(_errorMessage);

        uiMediator.SetOutputResult(_response, sb.toString());
    }
}
