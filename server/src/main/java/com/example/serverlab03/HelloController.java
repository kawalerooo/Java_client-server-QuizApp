package com.example.serverlab03;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;


public class HelloController {

    public static ArrayList<Question> questions = new ArrayList<>();
    public static ArrayList<Question> answeredQuestions = new ArrayList<>();
    public static Question actualQuestion;
    public static TextArea text;
    public static ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(15);  //max 15 odp


    @FXML
    private TextArea textArea;

    @FXML
    public void initialize() throws FileNotFoundException {

        HelloController.text = textArea;

        var file = new File("src/main/java/plik.txt");
        var scanner = new Scanner(file);
        ArrayList<String> txtFile = new ArrayList<String>();

        while (scanner.hasNextLine()) {
            var text = scanner.nextLine();
            var answersAndQuestions = text.split(";");
            txtFile.addAll(Arrays.asList(answersAndQuestions));
        }

        int id = 0;
        for (int i = 0; i < txtFile.size(); i += 2) {
            questions.add(new Question(id, txtFile.get(i), txtFile.get(i + 1)));
            id++;

        }
        actualQuestion = getUnansweredQuestion();
        text.setText(actualQuestion.getQuestion());

        var checkAnswers = new AnswerController();
        checkAnswers.start();

        Thread server = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket ss = null;
                    ss = new ServerSocket(1234);
                    Socket s;
                    while (true) {
                        s = ss.accept();
                        DataInputStream dis = new DataInputStream(s.getInputStream());
                        DataOutputStream dos = new DataOutputStream(s.getOutputStream());

                        ClientHandler mtch = new ClientHandler(s, dis, dos);
                        Thread t = new Thread(mtch);
                        t.start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        server.start();
    }

    public static Question getUnansweredQuestion() {

        var unansweredQuestion = new Question(2137, "", "");

        while (answeredQuestions.size() < questions.size()) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, questions.size());
            var questionExist = false;

            for (int i = 0; i < answeredQuestions.size(); i++) {
                if (answeredQuestions.get(i).getQuestionID() == questions.get(randomNum).getQuestionID()) {
                    questionExist = true;
                    break;
                }
            }

            if (!questionExist) {
                unansweredQuestion = questions.get(randomNum);
                break;
            }
        }

        return unansweredQuestion;
    }
}


























































