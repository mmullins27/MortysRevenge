package controller;

/*
 * Mason Mullins
 * GUI.java
 * 10/2/16
 * Runs the GUI for the game, run this file in order to play
 * 
 */



import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import model.Game;
import model.KeyBindings;
import views.TextView;
import views.GraphicView;

public class GUI extends JFrame{

	private static Game game;
	private TextView textView;
	private JPanel currentView;
	private GraphicView graphicView;
	public static final int width = 600;
	public static final int height = 650;
	private KeyBindings listener;
	private MenuItemListener menuListener;
	
	
	public static void main(String[] args) {
	    GUI g = new GUI();
	    g.setVisible(true);
	  }
	
	public GUI() {
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    this.setSize(width, height);
	    this.setLocation(100, 40);
	    this.setTitle("Morty's Revenge");
	    listener = new KeyBindings(this);
	    
	    setupMenus();
	    initializeGame();
	    textView = new TextView(game, 600, height, this);
	    graphicView = new GraphicView(game, 600, height, this);
	    addObservers();
	    // Set default view
	    setView(graphicView);
	    this.addKeyListener(listener);
	    
	}//end constructor
	
	private void initializeGame() {
		game = new Game();
		
	}

	private void setupMenus() {
		JMenuItem menu = new JMenu("Options");
		JMenuItem t = new JMenuItem("Text Version");
		JMenuItem g = new JMenuItem("Graphic Version");
		JMenuItem ng = new JMenuItem("Find New Universe");
		
		menu.add(t);
		menu.add(g);
		menu.add(ng);
		
		JMenuBar menuBar = new JMenuBar();
	    setJMenuBar(menuBar);
	    menuBar.add(menu);
	    
	    
	    menuListener = new MenuItemListener();
	    menu.addActionListener(menuListener);
	    menu.addKeyListener(listener);
	    menuBar.addKeyListener(listener);
	    t.addKeyListener(listener);
	    g.addKeyListener(listener);
	 
	    this.requestFocusInWindow();
	    t.addActionListener(menuListener);
	    g.addActionListener(menuListener);
	    ng.addActionListener(menuListener);
	}

	public static Game getGame(){
		return game;
	}
	
	
	public void addObservers(){
		game.addObserver(textView);
	    game.addObserver(graphicView);
	}
	
	
	
	private void setView(JPanel newView) {
	    if (currentView != null){
	      remove(currentView);
	    }//end if
	    currentView = newView;
	    this.add(currentView);
	    currentView.setFocusable(true);
	    currentView.requestFocus();
	    currentView.repaint();
	    validate();
	  }
	
	
	
	private class MenuItemListener implements ActionListener {

	    @Override
	    public void actionPerformed(ActionEvent e) {
	      
	      String str = ((JMenuItem) e.getSource()).getText();

	      if (str.equals("Text Version"))
	        setView(textView);

	     if (str.equals("Graphic Version")) 
	        setView(graphicView);
	     	
	     
	     if (str.equalsIgnoreCase("Find New Universe")){
	    	 game = game.newGame();
	    	 
	     
	     }
	      
	    
	  }//end actionPerformed
	
	}//end MenuItemListener
	
	
	
}
