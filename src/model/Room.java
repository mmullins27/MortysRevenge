package model;

public class Room {
	
	private boolean explored;
	private boolean hasRick;
	private boolean hasSlime;
	private boolean hasBlood;
	private boolean hasPit;
	private boolean hasMorty;
	
	public Room(){
		explored = false;
		hasRick = false;
		hasSlime = false;
		hasBlood = false;
		hasPit = false;
		hasMorty = false;
	}
	
	public boolean explored(){
		return explored;
	}
	
	public boolean hasRick(){
		return hasRick;
	}
	
	public boolean hasMorty(){
		return hasMorty;
	}
	
	public boolean hasSlime(){
		return hasSlime;
	}
	
	public boolean hasBlood(){
		return hasBlood;
	}
	
	public boolean hasGoop(){
		if(this.hasSlime() && this.hasBlood())
			return true;
		return false;
	}
	
	public boolean hasPit(){
		return hasPit;
	}
	
	public boolean isEmpty(){
		if(!hasMorty && !hasRick && !hasSlime && !hasBlood && !hasPit)
			return true;
		return false;
	}
	
	public void explore(){
		explored = true;
	}
	
	public void addRick(){
		hasRick = true;
	}
	
	public void addMorty(){
		hasMorty = true;
	}
	
	public void loseMorty(){
		hasMorty = false;
	}

	public void addSlime(){
		hasSlime = true;
	}
	
	public void addBlood(){
		hasBlood = true;
	}
	
	public void addPit(){
		hasPit = true;
	}
	
}
