package server;

import java.util.List;

public class Question {
    private final String questionText;
    private final List<String> possibleAnswers;
    private final String correctAnswer;

    public Question(String questionText, List<String> possibleAnswers, String correctAnswer) {
        this.questionText = questionText;
        this.possibleAnswers = possibleAnswers;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(questionText).append("\n");
        for (int i = 0; i < possibleAnswers.size(); i++) {
            sb.append(i + 1).append(" ").append(possibleAnswers.get(i)).append("\n");
        }
        return sb.toString();
    }
}