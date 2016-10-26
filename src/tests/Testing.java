package tests;
/*
 * Mason Mullins
 * Testing.java
 * 10/2/16
 * JUnit tests meant to test Game.java
 */



import static org.junit.Assert.*;
import model.Game;

import org.junit.Test;

public class Testing {

	@Test
	public void testBools() {
		Game game = new Game();
		assertTrue(!game.mKilled);
		assertTrue(!game.rKilled);
		assertTrue(!game.inSlime);
		assertTrue(!game.inBlood);
		assertTrue(!game.inGoop);
		assertTrue(!game.isOver());
		
	}//end testBools

	
	@Test
	public void testBoard(){
		Game game = new Game();
		for(int i = 0; i < 12; i++){
			for(int j = 0; j < 12; j++){
				assertFalse(game.getRoom(i, j) == null);
			}
		}
	}//end testBoard
	
	@Test
	public void testNewGame(){
		Game game = new Game();
		game.moveLeft();
		game.moveRight();
		game.moveDown();
		game.moveUp();
		game.newGame();
		for(int i = 0; i < 12; i++){
			for(int j = 0; j < 12; j++){
				assertFalse(game.getRoom(i, j) == null);
			}
		}
	}
	
	@Test
	public void testArrow(){
		Game game1 = new Game();
		Game game2 = new Game();
		Game game3 = new Game();
		Game game4 = new Game();
		game1.shootDown();
		game2.shootUp();
		game3.shootLeft();
		game4.shootRight();
		assertTrue(game1.isOver());
		assertTrue(game2.isOver());
		assertTrue(game3.isOver());
		assertTrue(game4.isOver());
		
	}//end testArrow
	
	@Test
	public void testToString(){
		Game game = new Game();
		assertTrue(game.toStringBrackets() != null);
		
		
	}
	
	
}
