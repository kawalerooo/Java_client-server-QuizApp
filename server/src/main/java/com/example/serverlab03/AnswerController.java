package com.example.serverlab03;

public class AnswerController extends Thread {

    @Override
    public void run() {
        while (true) {
            String received = null;
            try {
                received = HelloController.queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            var lineData = received.split(":");
            var userName = lineData[0];
            var userMessage = lineData[1];

            if (userMessage.equals(HelloController.actualQuestion.getAnswer())) {
                HelloController.text.setText(HelloController.text.getText() + "\n" + "Poprawna odpowiedź " + userName);
                HelloController.answeredQuestions.add(HelloController.actualQuestion);

                if (HelloController.questions.size() == HelloController.answeredQuestions.size()) {
                    HelloController.text.setText(HelloController.text.getText() + "\n Koniec pytań");
                } else {
                    HelloController.actualQuestion = HelloController.getUnansweredQuestion();
                    HelloController.text.setText(HelloController.text.getText() + "\n" + HelloController.actualQuestion.getQuestion());
                }

            } else {
                HelloController.text.setText(HelloController.text.getText() + "\n" + "Błędna odpowiedź " + userName);
            }
        }
    }
}
