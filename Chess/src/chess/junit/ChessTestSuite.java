package chess.junit;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * To create a Test Suite, we just need to declare the
 * RunWith: annotation as a Suite and add all the test
 * cases to be executed using the SuiteClasses annotation.
 */
@RunWith(value=Suite.class)
@SuiteClasses(value={ 
		ChessTestCasePieceAccessors.class,
		ChessTestCasePieceCM.class,
		ChessTestCasePieceRule.class
}) 
public class ChessTestSuite { }


