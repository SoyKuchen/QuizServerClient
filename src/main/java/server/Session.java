package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.List;

public class Session extends Thread {
    private final Socket socket;
    private final List<Question> questions;
    private final SocketAddress clientAddress;

    public Session(Socket socketForClient, List<Question> questions) {
        this.socket = socketForClient;
        this.questions = questions;
        this.clientAddress = socket.getRemoteSocketAddress();
        System.out.println("Se ha recibido una conexión desde " + clientAddress);
    }

    @Override
    public void run() {
        try (
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            System.out.println("Iniciando la partida para el cliente " + clientAddress);

            output.writeInt(questions.size());

            int correctAnswers = 0;

            for (int i = 0; i < questions.size(); i++) {
                Question question = questions.get(i);
                output.writeUTF(question.toString() + "Ingresa tu respuesta:\n");

                String receivedMsg = input.readUTF();

                if (question.getCorrectAnswer().equals(receivedMsg)) {
                    correctAnswers++;
                    output.writeUTF("Tu respuesta es CORRECTA");
                    System.out.println(clientAddress + " acierta la pregunta " + (i + 1));
                } else {
                    output.writeUTF("Tu respuesta es INCORRECTA");
                    System.out.println(clientAddress + " falla la pregunta " + (i + 1));
                }
            }

            output.writeUTF("Has terminado el juego con " + correctAnswers + " aciertos");
            System.out.println("El cliente " + clientAddress + " ha finalizado una partida con " + correctAnswers + " aciertos.\n");

            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
