package views;
/*
 * Mason Mullins
 * TextView.java
 * 10/2/16
 * Implements the text version of the game
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import controller.GUI;
import model.Game;
import model.KeyBindings;


public class TextView extends JPanel implements Observer{

	private Game game;
	private int height;
	private int width;
	JTextArea area;
	private int DIMS = 12;
	private KeyBindings listener;
	private JLabel message;
	private GUI gui;
	
	public TextView(Game inGame, int w, int h, GUI inGUI){
		this.game = inGame;
		width = w;
		height = h;
		create();
		gui = inGUI;
		
		
		
	}//end constructor
	
	
	private void create() {
		this.setLayout(new BorderLayout());
		this.setSize(width, height);
		
		
		Font lilFont = new Font("Courier", Font.TRUETYPE_FONT, 16);
		Font bigFont = new Font("Courier", Font.TRUETYPE_FONT, 20);
		Font titleFont = new Font("SansSerif", Font.BOLD, 46);
		JLabel directions = new JLabel();
		JLabel title = new JLabel();
		JLabel byMason = new JLabel();
		message = new JLabel();
		message.setText("");
		message.setFont(lilFont);
		message.setForeground(Color.MAGENTA);
		byMason.setText("By: Mason Mullins");
		byMason.setFont(lilFont);
		title.setFont(titleFont);
		title.setForeground(Color.green);
		title.setText("     Morty's Revenge");
		directions.setText("Move: Arrow Keys        Fire Laser: WASD");
		area = new JTextArea(DIMS,DIMS);
		area.setFont(bigFont);
		area.setText(game.toStringBrackets());
		area.setSize(500,400);
		area.setFocusable(true);
		directions.setFocusable(true);
		this.setFocusable(true);
		JPanel titleBy = new JPanel(new BorderLayout());
		this.add(directions, BorderLayout.NORTH);
		this.add(area, BorderLayout.SOUTH);
		titleBy.add(title, BorderLayout.CENTER);
		titleBy.add(byMason, BorderLayout.SOUTH);
		titleBy.add(message, BorderLayout.BEFORE_FIRST_LINE);
		titleBy.setFocusable(true);
		title.setFocusable(true);
		byMason.setFocusable(true);
		this.add(titleBy, BorderLayout.WEST);
		listener = new KeyBindings(gui);
		this.addKeyListener(listener);
		byMason.addKeyListener(listener);
		title.addKeyListener(listener);
		area.addKeyListener(listener);
		titleBy.addKeyListener(listener);
		directions.addKeyListener(listener);
		title.addKeyListener(listener);
		
		
		
		
		
	}//end create


	@Override
	public void update(Observable o, Object arg) {
		area.setText(GUI.getGame().toStringBrackets());
		
		if(GUI.getGame().isOver()){
			
			if(GUI.getGame().rKilled){
				message.setText("Morty defeated Rick. Hazaah, y'know?!");
			}
			if(GUI.getGame().mKilled){
				if(GUI.getGame().fell){
					message.setText("Morty fell down a pit. Ahhhhh, y'know?!");
				}else if(GUI.getGame().cobain){
					message.setText("Morty hit himself. What a goof, y'know?!");
					
				}else{
					message.setText("Rick defeated Morty. Easy *burp* peasy.");
				}
			}//if morty died
			
			
		}//end large if
		else{
			if(GUI.getGame().inGoop){
				message.setText("Ew! Goop. (Rick AND pit nearby)");
			}else if(GUI.getGame().inBlood){
				message.setText("Yuck! Blood! (Rick nearby)");
			}else if(GUI.getGame().inSlime){
				message.setText("Gross! Slime! (Pit nearby)");
			}else{
				message.setText("");
			}
		}
		this.requestFocus();
		repaint();
		
	}//end update

}
