import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**

 */
public class WorkerRunnable implements Runnable {

	protected Socket clientSocket = null;
	protected String serverText = null;


	public WorkerRunnable(Socket clientSocket, String serverText) {
		this.clientSocket = clientSocket;
		this.serverText = serverText;
	}

	public void run() {
		try {
			int sequence = 0;
			InputStream input = clientSocket.getInputStream();
			OutputStream output = clientSocket.getOutputStream();
			long time = System.currentTimeMillis();
			PrintWriter out =
					new PrintWriter(clientSocket.getOutputStream(), true);
			byte[] buffer = new byte[input.available()];
			input.read(buffer);

			File targetFile = new File("src/main/resources/img" + sequence +".jpg");
			OutputStream outStream = new FileOutputStream(targetFile);
			outStream.write(buffer);
			sequence++;

//			output.write(("HTTP/1.1 200 OK\n\nWorkerRunnable: " + this.serverText + " - " + time + "").getBytes());
//			output.close();
			input.close();
			outStream.close();
			System.out.println("Request processed: " + time);
		} catch (IOException e) {
			//report exception somewhere.
			e.printStackTrace();
		}
	}
}