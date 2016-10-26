package model;

/*
 * Mason Mullins
 * Game.java
 * 10/2/16
 * Conducts the inner workings of the game
 */


import java.util.Random;
import java.util.Observable;

public class Game extends Observable{

	private Room[][] board;
	private int DIMS;
	private int mortyRows;
	private int mortyCols;
	private boolean isOver;
	public boolean mKilled;
	public boolean rKilled;
	public boolean inSlime;
	public boolean inBlood;
	public boolean inGoop;
	public boolean fell;
	public boolean cobain;
	Random rng;
	
	
	public Game(){
		isOver = false;
		DIMS = 12;
		board = new Room[DIMS][DIMS];
		rng = new Random();
		
		
		for(int i = 0; i < DIMS; i++){
			for(int j = 0; j < DIMS; j++){
				board[i][j] = new Room();
			}//end inner for
		}//end outer for
		fillBoard();
		notifyObservers();
	}//end constructor
	
	
	//This function will add all randomized elements to the board
	public void fillBoard(){
		dropRick();
		dropPit();
		dropMorty();
	}//end fillBoard
	
	public Game newGame(){
		isOver = false;
		board = new Room[DIMS][DIMS];
		for(int i = 0; i < DIMS; i++){
			for(int j = 0; j < DIMS; j++){
				board[i][j] = new Room();
			}//end inner for
		}//end outer for
		fillBoard();
		setChanged();
		
		notifyObservers();
		return this;
		
	}//end newGame
	
	private void dropPit() {
		int count = 1;
		int bCount = 0;
		int myX = 0;
		int myY = 0;
		int numPits = (rng.nextInt(7)) + 1;
		while(bCount < numPits){
			while(count > 0){ //Never true; rely on continue and break statements
				myX = rng.nextInt(DIMS);
				myY = rng.nextInt(DIMS);
				if(!board[myX][myY].isEmpty())
					continue;
				board[myX][myY].addPit();	
				break;
			}//end small while
			//SLIME TIME
			//1 up
			if(myY == 0){
				board[myX][DIMS-1].addSlime();
			}else{
				board[myX][myY-1].addSlime();
			}
			//1 Down
				board[myX][(myY+1)%DIMS].addSlime();
			//1 Left
			if(myX == 0){
				board[DIMS-1][myY].addSlime();
			}else{
				board[myX-1][myY].addSlime();
			}
			//1 Right
			board[(myX+1)%DIMS][myY].addSlime();
			bCount++;
		}//end big while
		
	}//end dropPit


	//Randomly drop Morty on the board
	public void dropMorty(){
		int count = 1;
		int myX;
		int myY;
		while(count > 0){ //Never true; rely on contunue and break statements
		myX = rng.nextInt(DIMS);
		myY = rng.nextInt(DIMS);
		if(!board[myX][myY].isEmpty())
			continue;
		mortyRows = myX;
		mortyCols = myY;
		board[myX][myY].addMorty();	
		board[myX][myY].explore();
		break;
		}//end while
	}//end dropMorty
	
	//Randomly drop Rick on the board, not where Morty is
	public void dropRick(){
		int count = 1;
		int myRows = -1;
		int myCols = -1;
		while(count > 0){ //Never true; rely on contunue and break statements
			myRows = rng.nextInt(DIMS);
			myCols = rng.nextInt(DIMS);
			if(board[myRows][myCols].hasMorty())
				continue;
			board[myRows][myCols].addRick();
			break;
		}//end while
		
		//Add blood
		//2 up
		if(myCols == 0){
			board[myRows][DIMS-1].addBlood();
		}else{
			board[myRows][myCols-1].addBlood();
		}
		if(myCols-1 == 0){
			board[myRows][DIMS-1].addBlood();
		}else if(myCols-1 == -1){
			board[myRows][DIMS-2].addBlood();
		}else{
			board[myRows][myCols-2].addBlood();
		}
		
		//2 Down
		board[myRows][(myCols+1)%DIMS].addBlood();
		board[myRows][(myCols+2)%DIMS].addBlood();
		
		//2 Left
		if(myRows == 0){
			board[DIMS-1][myCols].addBlood();
		}else{
			board[myRows-1][myCols].addBlood();
		}
		if(myRows-1 == 0){
			board[DIMS-1][myCols].addBlood();
		}else if(myRows-1 == -1){
			board[DIMS-2][myCols].addBlood();
		}else{
			board[myRows-2][myCols].addBlood();
		}
		
		//2 Right
		board[(myRows+1)%DIMS][myCols].addBlood();
		board[(myRows+2)%DIMS][myCols].addBlood();
				
		//Diagonals
		//Top Right (Row - 1, Col + 1)
		int newRows = myRows;
		int newCols = myCols;
		
		if(myRows == 0){
			newRows = DIMS-1;
		}else{
			newRows = myRows - 1;
		}
		newCols = (myCols+1)%DIMS;
		board[newRows][newCols].addBlood();
		//Top Left (Row - 1, Col - 1)
		if(myCols == 0)
			newCols = DIMS-1;
		else
			newCols = myCols - 1;
		board[newRows][newCols].addBlood();
		//Bottom left (Row + 1, Col - 1)
		newRows = (myRows+1)%DIMS;
		board[newRows][newCols].addBlood();
		//Bottom Right (Row + 1, Col + 1)
		newCols = (myCols+1)%DIMS;
		board[newRows][newCols].addBlood();
		
	}//end dropRick
	
	
	
	
	
	//Morty explores West
	public void moveLeft(){
		int newCols;
		if(mortyCols == 0){
			newCols = DIMS-1;
		}else{
			newCols = mortyCols-1;
		}
		board[mortyRows][mortyCols].loseMorty();
		board[mortyRows][newCols].addMorty();
		mortyCols = newCols;
		checkRoom();
		setChanged();
		notifyObservers();
		}//end moveUp
	
	//Morty explores East
	public void moveRight(){
		
		int newY = (mortyCols+1)%DIMS;
		board[mortyRows][mortyCols].loseMorty();
		board[mortyRows][newY].addMorty();
		mortyCols = newY;
		checkRoom();
		setChanged();
		notifyObservers();
	}//end moveDown
	
	//Morty explores South
	public void moveDown(){
		int newRows = (mortyRows+1)%DIMS;
		board[mortyRows][mortyCols].loseMorty();
		board[newRows][mortyCols].addMorty();
		mortyRows = newRows;
		checkRoom();
		setChanged();
		notifyObservers();
	}//end moveRight
	
	//Morty explores North
	public void moveUp(){
		int newRows;
		if(mortyRows == 0){
			newRows = DIMS-1;
		}else{
			newRows = mortyRows-1;
		}
		board[mortyRows][mortyCols].loseMorty();
		board[newRows][mortyCols].addMorty();
		mortyRows = newRows;
		checkRoom();
		setChanged();
		notifyObservers();
	}//end moveLeft
	
	
	

	private void checkRoom() {
		mKilled = false;
		rKilled = false;
		inSlime = false;;
		inBlood = false;
		inGoop = false;
		fell = false;
		cobain = false;
		
		Room current = board[mortyRows][mortyCols];
		board[mortyRows][mortyCols].explore();
		if(current.hasRick()){
			isOver = true;
			mKilled = true;
			rKilled = false;
			
		}
		else if(current.hasPit()){
			isOver = true;
			mKilled = true;
			fell = true;
		}
		else if(current.hasGoop()){
			inGoop = true;
		}
		else if(current.hasBlood()){
			inBlood = true;
		}
		else if(current.hasSlime()){
			inSlime = true;
		}
		
	}//end checkRoom
	
	//true if killed wumpus, false if lose
	public boolean shootDown(){
		int thisRows = mortyRows;
		int thisCols = mortyCols;
		thisRows = (thisRows+1)%DIMS;
		while(thisRows != mortyRows){
			if(board[thisRows][thisCols].hasRick()){
				mKilled = false;
				rKilled = true;
				isOver = true;
				cobain = false;
				setChanged();
				notifyObservers();
				return true;
			}
			thisRows = (thisRows+1)%DIMS;
		}//end while
		mKilled = true;
		rKilled = false;
		isOver = true;
		cobain = true;
		setChanged();
		notifyObservers();
		return false;	
	}// end shootRight
	
	//true if killed wumpus, false if lose
	public boolean shootUp(){
		int thisRows = mortyRows;
		int thisCols = mortyCols;
		if(thisRows == 0){
			thisRows = DIMS-1;
		}else{
			thisRows--;
		}
		while(thisRows != mortyRows){
			if(board[thisRows][thisCols].hasRick()){
				rKilled = true;
				mKilled = false;
				isOver = true;
				cobain = false;
				setChanged();
				notifyObservers();
				return true;
			}
			if(thisRows == 0){
				thisRows = DIMS-1;
			}else{
				thisRows--;
			}
		}//end while
		rKilled = false;
		mKilled = true;
		isOver = true;
		cobain = true;
		setChanged();
		notifyObservers();
		return false;	
	}// end shootLeft
	
	//true if killed wumpus, false if lose
	public boolean shootRight(){
		int thisRows = mortyRows;
		int thisCols = mortyCols;
		thisCols = (thisCols+1)%DIMS;
		while(thisCols != mortyCols){
			if(board[thisRows][thisCols].hasRick()){
				rKilled = true;
				mKilled = false;
				isOver = true;
				cobain = false;
				setChanged();
				notifyObservers();
				return true;
			}
			thisCols = (thisCols+1)%DIMS;
		}//end while
		rKilled = false;
		mKilled = true;
		isOver = true;
		cobain = true;
		setChanged();
		notifyObservers();
		return false;		
	}//end shootDown
	
	//true if killed wumpus, false if lose
	public boolean shootLeft(){
		int thisRows = mortyRows;
		int thisCols = mortyCols;
		if(thisCols == 0){
			thisCols = DIMS-1;
		}else{
			thisCols--;
		}
		while(thisCols != mortyCols){
			if(board[thisRows][thisCols].hasRick()){
				rKilled = true;
				mKilled = false;
				isOver = true;
				cobain = false;
				setChanged();
				notifyObservers();
				return true;
			}
			if(thisCols == 0){
				thisCols = DIMS-1;
			}else{
				thisCols--;
			}
		}//end while
		mKilled = true;
		rKilled = false;
		isOver = true;
		cobain = true;
		setChanged();
		notifyObservers();
		return false;
	}//end shootUp
	
	public Room getRoom(int rows, int cols){
		return board[rows][cols];
	}
		
	//Returns a string representation of the game
	//Brackets = rooms
	public String toStringBrackets(){
		String str = "";
		for(int i = 0; i < DIMS; i++){
			for(int j = 0; j < DIMS; j++){
				Room current = board[i][j];
				str = str + "[";
				if(!current.explored()){
					str = str + "X";
				}
				else if(current.hasRick()){
					str = str + "W";
				}
				else if(current.hasPit()){
					str = str + "P";
				}
				else if(current.hasMorty()){
					str = str + "O";
				}
				else if(current.hasGoop()){
					str = str + "G";
				}
				else if(current.hasBlood()){
					str = str + "B";
				}
				else if(current.hasSlime()){
					str = str + "S";
				}
				else{
					str = str + " ";
				}
				
				str = str + "] ";
			}//end inner for
			str = str + "\n";
		}//end outer for
		return str;
	}//end toStringBrackets
		
	
	
	public boolean isOver(){
		return isOver;
	}
	
	
	
	
}
