/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Interfaces.IObserverPull;
import Interfaces.ISubject;
import Model.ClientRunnable;
import Model.MessageType;
import com.github.lgooddatepicker.components.DateTimePicker;
import TCPConnectionState.TCPConnection;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author Maximiliano Herrera
 */
public class UIMediator implements ISubject {

    private final TCPConnection _tcpConnection;
    private final BlockingQueue _queue = new ArrayBlockingQueue(50);

    private final ClientRunnable _clientRunnable;
    private final Thread _clientThread;

    private final JTextField _jTextFieldInput;
    private final JTextField _jTextFieldStatus;
    private final DateTimePicker _dateTimePicker;
    private final JComboBox<String> _jComboBoxActionType;
    private final MainJFrameClient _mainJFrame;

    private String _rawResponse;
    private String _parsedResponse;

    private IObserverPull _observer;

    public TCPConnection getTcpConnection() {
        return _tcpConnection;
    }

    public String getRawResponse() {
        return _rawResponse;
    }

    public String getParsedResponse() {
        return _parsedResponse;
    }
 
    public UIMediator(MainJFrameClient mainJFrame) throws UnknownHostException {
        _mainJFrame = mainJFrame;
        _jTextFieldInput = mainJFrame.getTextFieldInput();
        _dateTimePicker = mainJFrame.dateTimePicker();
        _jComboBoxActionType = mainJFrame.getComboBoxActionType();
        _jTextFieldStatus = mainJFrame.getTextFieldStatus();
        InetAddress host = InetAddress.getLocalHost();
        _tcpConnection = new TCPConnection(host, 1234);
        _tcpConnection.attach(
                new UIComponentObserverPull(
                        () -> _jTextFieldStatus.setText("Connection status: " + _tcpConnection.GetConnectionStatus())));

        _clientRunnable = new ClientRunnable(_tcpConnection, _queue, this);
        _clientThread = new Thread(_clientRunnable);
        _clientThread.start();
    }

    public void ExecuteCommand() throws Exception {

        LocalDate date = _dateTimePicker.datePicker.getDate();
        LocalTime time = _dateTimePicker.timePicker.getTime();
        String action = _jComboBoxActionType.getSelectedItem().toString();
        String eventName = _jTextFieldInput.getText();

        String message = SerializeMessage(action, date, time, eventName);

        System.out.println("enqueing message: " + message);
        _queue.put(message);

        System.out.println("message enqueued");

        _jTextFieldInput.setText("");
    }

    public void ExecuteRawCommand(String rawMessage) throws Exception {
        _queue.put(rawMessage);
    }

    public void ShowErrorDialog(String message) {
        _mainJFrame.ShowErrorDialog(message);
    }

    private String SerializeMessage(String action, LocalDate date, LocalTime time, String message) {
        if (action == null)
            return "";
        if (action.toUpperCase().equals(MessageType.STOP.toString()))
            return action;

        StringBuilder sb = new StringBuilder();
        sb.append(action).append(";");
        sb.append(date).append(";");
        sb.append(time).append(";");
        sb.append(message);

        return sb.toString();
    }

    public void InitializeUI() {
        _jTextFieldStatus.setText("Connection status: disconnected");
    }

    public void SetOutputResult(String rawResponse, String parsedResponse) {
        _rawResponse = rawResponse;
        _parsedResponse = parsedResponse;

        this.Notify();
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
}
