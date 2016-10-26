package views;
/*
 * Mason Mullins
 * GraphicView.java
 * 10/2/16
 * Implements the graphic version of the game
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import controller.GUI;
import model.Game;
import model.KeyBindings;

public class GraphicView extends JPanel implements Observer{
	
	private Game game;
	private int DIMS = 12;
	private int height;
	private int width;
	private Image morty, rick, goop, slime, blood, pit, ground;
	private KeyBindings listener;
	private GUI gui;

	public GraphicView(Game inGame, int w, int h, GUI inGUI){
		game = inGame;
		height = h;
		width = w;
		gui = inGUI;
		
		create();
		repaint();
	}
	
	
	
	private void create() {
		setOpaque(true);
		this.setSize(width, height);
		this.setFocusable(true);
		listener = new KeyBindings(gui);
		this.addKeyListener(listener);
		setBackground(Color.DARK_GRAY);
		addImgs();
	}//end create



	private void addImgs() {
		try {
			morty = ImageIO.read(new File(("images/MortyEyepatch.png")));
			ground = ImageIO.read(new File(("images/Ground.png")));
			rick = ImageIO.read(new File(("images/Rick.png")));
			goop = ImageIO.read(new File(("images/Goop.png")));
			blood = ImageIO.read(new File(("images/Blood.png")));
			slime = ImageIO.read(new File(("images/Slime.png")));
			pit = ImageIO.read(new File(("images/SlimePit.png")));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}//end addImgs



	@Override
	public void update(Observable o, Object arg) {
		
		
		repaint();
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
      	Graphics2D g2 = (Graphics2D) g;
      	Game current = GUI.getGame();
      	
		
      	for(int col = 0; col < DIMS; col++){
      		for(int row = 0; row < DIMS; row++){
      			if(current.getRoom(row, col).explored()){
      				g2.drawImage(ground, col*50, row*50, null);
      				
      				if(current.getRoom(row, col).hasGoop()){
      					g2.drawImage(goop, col*50, row*50, null);
      				}else if(current.getRoom(row, col).hasBlood()){
      					g2.drawImage(blood, col*50, row*50, null);
      				}else if(current.getRoom(row, col).hasSlime()){
      					g2.drawImage(slime, col*50, row*50, null);
      				}
      			
      				if(current.getRoom(row, col).hasMorty()){
      					g2.drawImage(morty, col*50, row*50, null);
      				}
      				if(current.getRoom(row, col).hasRick()){
      					g2.drawImage(rick, col*50, row*50, null);
      				}else if(current.getRoom(row, col).hasPit()){
      					g2.drawImage(pit, col*50, row*50, null);
      				}
      			}//if room = explored
      			g2.setColor(Color.WHITE);
      			g2.drawString("Move: Arrow Keys        Fire Laser: WASD", 10, 600);
      			
      		}//end inner for
      	}//end outer for
      	g2.setColor(Color.MAGENTA);
      	if(GUI.getGame().isOver()){
			
			if(GUI.getGame().rKilled){
				
				g2.drawString("Morty defeated Rick. Hazaah, y'know?!",50, 50);
				
			}
			if(GUI.getGame().mKilled){
				if(GUI.getGame().fell){
					g2.drawString("Morty fell down a pit. Ahhhhh, y'know?!",50, 50);
				}else if(GUI.getGame().cobain){
					
					g2.drawString("Morty hit himself. What a goof, y'know?!",50, 50);
					
				}else{
					
					g2.drawString("Rick defeated Morty. Easy *burp* peasy.",50, 50);
					
				}
			}//if morty died
      	}//end large if
		else{
			if(GUI.getGame().inGoop){
				g2.drawString("Ew! Goop. (Rick AND pit nearby)",50, 50);
			}else if(GUI.getGame().inBlood){
				g2.drawString("Yuck! Blood! (Rick nearby)",50, 50);
			}else if(GUI.getGame().inSlime){
				g2.drawString("Gross! Slime! (Pit nearby)",50, 50);
			}else{
				g2.drawString("",50, 50);
			}
			repaint();
		
		}
	}//end paintComponent
}
