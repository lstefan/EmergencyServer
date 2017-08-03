import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 */
public class Main {

	public static void main(String args[]) {
		System.out.println("Starting the server");
		Runnable multiThreadedServer = new MultiThreadedServer(999);
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(multiThreadedServer);
		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		System.out.println("Finished all threads");

	}
}
