import message.Message;

import java.io.*;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 */
public class Server {

	public static final int TCP_PORT = 9999;
	public static final int UDP_PORT = 9998;
    private static final int SIZE = 200000;

	static ArrayList<Thread> clientThreads;

	public static void main(String args[]) {
		System.out.println("Starting the server");

		clientThreads = new ArrayList<Thread>();

		ServerSocket serverTCP = null;
		DatagramSocket serverUDP = null;

		try {
			serverTCP = new ServerSocket(TCP_PORT);
			serverUDP = new DatagramSocket(UDP_PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}

        ObjectOutputStream out;
        ObjectInputStream in;

		while (true) {
			try {
				System.out.println("Accepting connections...");
				Socket socketTCP = serverTCP.accept();
                System.out.println("Connection accepted...");
                out = new ObjectOutputStream(socketTCP.getOutputStream());
                out.flush();
                in = new ObjectInputStream(socketTCP.getInputStream());

                Message data = null;

                try {
                    data = (Message) in.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

				if(data.getType() == Message.AUTHENTICATE_USER) {
					Thread newPictureClient = new Thread(new PictureWorkerRunnable(in, out));
					clientThreads.add(newPictureClient);
                    newPictureClient.start();

                    Thread newAudioClient = new Thread(new AudioWorkerRunnable(serverUDP));
                    clientThreads.add(newAudioClient);
                    newAudioClient.start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
