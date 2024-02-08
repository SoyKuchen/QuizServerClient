package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String localhost = "127.0.0.1";
        int PORT = 33333;

        try (
                Socket socket = new Socket(localhost, PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                Scanner scanner = new Scanner(System.in)
        ) {

            // indica la cantidad de preguntas
            int questionCount = input.readInt();

            for (int i = 0; i < questionCount; i++) {
                handleQuestion(input, output, scanner);
            }

            // muestra el total de aciertos
            System.out.println(input.readUTF());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void handleQuestion(DataInputStream input, DataOutputStream output, Scanner scanner) throws IOException {
        // muestra la pregunta y pide que ingrese la respuesta
        String question = input.readUTF();
        System.out.print(question);

        // lee y envia la respuesta
        String answer = scanner.nextLine();
        output.writeUTF(answer);

        // muestra si acertó o no
        String result = input.readUTF();
        System.out.println(result);
    }
}