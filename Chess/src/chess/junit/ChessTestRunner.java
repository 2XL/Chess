package chess.junit;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * This class is used to execute a test Suite. It can be
 * used to execute more than one Suite, or even a Suite
 * of suites in order to automatically execute large tests.
 */
public class ChessTestRunner {

	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(ChessTestSuite.class);
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		System.out.println(result.wasSuccessful());
	}

}
