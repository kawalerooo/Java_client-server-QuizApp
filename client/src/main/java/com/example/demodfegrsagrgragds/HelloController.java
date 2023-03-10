package com.example.demodfegrsagrgragds;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class HelloController {
    private static InetAddress ip;
    private static Socket s;
    private static DataInputStream dis;
    private static DataOutputStream dos;

    @FXML
    private TextField answer;

    @FXML
    private TextField nickname;

    @FXML
    void sendAnswer(ActionEvent event) {
        try {
            dos.writeUTF(nickname.getText() + ":" + answer.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        try {
            ip = InetAddress.getByName("localhost");
            s = new Socket(ip, 1234);
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());

        } catch (Exception ignored) {
        }
    }
}
