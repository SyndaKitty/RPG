package net.spencerhaney.engine;

public abstract class GameObject {
	private float x;
	private float y;
	private Game g;
	
	public abstract void update();
	
	public void render(){
		System.out.println(x + " " + y);
		//TODO Implement real render
	}
	
	public void setGame(Game g){
	    this.g = g;
	}
	
	public Game getGame(){
		return g;
	}
	
}
