import java.util.ArrayList;
import java.util.Arrays;

/**
 * Runs all threads
 */

public class BridgeRunner {

	public static void main(String[] args) throws InterruptedException {
		// check commandline input
		if (args.length < 2) {
			System.out.println("Usage: java BridgeRunner <bridge limit> <num cars>");
			System.exit(0);
		}

		try {
			if (Integer.parseInt(args[0]) < 1 || Integer.parseInt(args[1]) < 1){
				System.out.println("Error: bridge limit and/or num cars must be positive.");
				System.exit(0);
			}
		} catch (NumberFormatException e) {
			System.out.println("Error: Inputs must be integers.");
			System.exit(0);
		}

		//instantiate the bridge
		OneLaneBridge b = new OneLaneBridge(Integer.parseInt(args[0]));

		// allocate threads
		Thread[] cars = new Thread[Integer.parseInt(args[1])];
		for (int i = 0; i < Integer.parseInt(args[1]); i++){
			cars[i] = new Thread(new Car(i, b));
			cars[i].start();
		}

		for (int i = 0; i < Integer.parseInt(args[1]); i++){
			cars[i].join();
		}

		System.out.println("All cars have crossed!");
	}


}