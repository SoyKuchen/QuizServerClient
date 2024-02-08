package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private static final int PORT = 33333;
    private static final String FILE_PATH = "src/main/resources/questions.txt";
    private static final List<Question> QUESTIONS = new ArrayList<>();

    public static void main(String[] args) {
        loadQuestions();

        try (ServerSocket serverSocket = new ServerSocket(PORT)){
            System.out.println("Servidor arrancado.\nEscuchando por el puerto " + PORT);

            while (true) {
                Session session = new Session(serverSocket.accept(), QUESTIONS);
                session.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadQuestions() {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(FILE_PATH))) {

            List<String> lines = reader.lines().toList();

            for (int i = 0; i < lines.size(); i += 6) {
                Question question = new Question(lines.get(i), lines.subList(i + 1, i + 5), lines.get(i + 5));
                QUESTIONS.add(question);
                System.out.println("Pregunta añadida: " + question.getQuestionText());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
