import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class RPLSTest {

	GameInfo theGame;

	@BeforeAll
	static void before(){
		com.sun.javafx.application.PlatformImpl.startup(() -> {});
	}

	@BeforeEach
	void init() {
		theGame = new GameInfo();
	}

	@Test	//test update
	void testUpdateP1(){
		theGame.choice = "rock";
		theGame.update(1);
		assertEquals("rock", theGame.p1plays, "p1 not updated correctly");
		assertEquals("", theGame.choice, "choice not set to empty string");
	}

	@Test	//test update
	void testUpdateP2(){
		theGame.choice = "scissors";
		theGame.update(2);
		assertEquals("scissors", theGame.p2plays, "p2 not updated correctly");
		assertEquals("", theGame.choice, "choice not set to empty string");
	}

	@Test	//test playAgain
	void testAgainP1(){
		theGame.playAgain(1);
		assertTrue(theGame.again1, "should be true");
		assertFalse(theGame.again2, "should be false");
	}

	@Test	//test playAgain
	void testAgainP2(){
		theGame.playAgain(2);
		assertTrue(theGame.again2, "should be true");
		assertFalse(theGame.again1, "should be false");
	}

	@Test //test roundWinner
	void testP1Wins(){
		theGame.p1plays = "rock";
		theGame.p2plays = "scissors";
		assertEquals(1, theGame.roundWinner(), "P1 should have won");
	}

	@Test //test roundWinner
	void testP2Wins(){
		theGame.p1plays = "paper";
		theGame.p2plays = "scissors";
		assertEquals(2, theGame.roundWinner(), "P2 should have won");
	}

	@Test //test roundWinner
	void testDraw(){
		theGame.p1plays = "spock";
		theGame.p2plays = "spock";
		assertEquals(0, theGame.roundWinner(), "should have been a draw");
	}

	@Test //testScore
	void testScore(){
		theGame.p1plays = "rock";
		theGame.p2plays = "scissors";
		theGame.roundWinner();
		assertEquals(1, theGame.p1score, "P1 should have one point");
		assertEquals(0, theGame.p2score, "P2 should have no points");
		theGame.p1plays = "scissors";
		theGame.p2plays = "spock";
		theGame.roundWinner();
		assertEquals(1, theGame.p1score, "P1 should have one point");
		assertEquals(1, theGame.p2score, "P2 should have one point");
		theGame.p1plays = "spock";
		theGame.p2plays = "spock";
		theGame.roundWinner();
		assertEquals(1, theGame.p1score, "P1 should have one point");
		assertEquals(1, theGame.p2score, "P2 should have one point");
	}

	@Test	//test bothPlayed
	void testBothPlayedFalse(){
		theGame.p1plays = "";
		assertFalse(theGame.bothPlayed(), "should be false");
	}

	@Test	//test bothPlayed
	void testBothPlayedFalse2(){
		theGame.p1plays = "spock";
		theGame.p2plays = "";
		assertFalse(theGame.bothPlayed(), "should be false");
	}

	@Test	//test bothPlayed
	void testBothPlayedTrue(){
		theGame.p1plays = "scissors";
		theGame.p2plays = "rock";
		assertTrue(theGame.bothPlayed(), "should be true");
	}
}
