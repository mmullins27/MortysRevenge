package model;
/*
 * Mason Mullins
 * KeyBindings.java
 * 10/2/16
 * A KeyAdapter meant to be given to the observers
 */
import java.awt.event.KeyAdapter;

import java.awt.event.KeyEvent;

import controller.GUI;

public class KeyBindings extends KeyAdapter{
	
	private GUI gui;
	private Game game;
	
	
	
	public KeyBindings(GUI inGUI){
		gui = inGUI;
		game = gui.getGame();
		
	}

	public void keyPressed(KeyEvent e) {
		//ARROW KEYS
		
		if(!gui.getGame().isOver()){
		if(e.getKeyCode() == KeyEvent.VK_UP){
			GUI.getGame().moveUp();
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			GUI.getGame().moveDown();
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			GUI.getGame().moveLeft();
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			GUI.getGame().moveRight();
		}
		//END ARROW KEYS
		
		//WASD
		if(e.getKeyCode() == KeyEvent.VK_W){
			GUI.getGame().shootUp();
		}
		if(e.getKeyCode() == KeyEvent.VK_S){
			GUI.getGame().shootDown();
		}
		if(e.getKeyCode() == KeyEvent.VK_A){
			GUI.getGame().shootLeft();
		}
		if(e.getKeyCode() == KeyEvent.VK_D){
			GUI.getGame().shootRight();
		}
		//END WASD
		}
	}
	
	
}
