import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

public class EngineManager {
	
	private static Game game;
	
	public static void main(String [] args){
		initDisplay();
		initGl();
		initGame();
		
		gameLoop();
		
		cleanUp();
	}
	
	private static void initGame(){
		game = new Runescape();
	}
	
	private static void gameLoop(){
		while(!Display.isCloseRequested()){
			getInput();
			update();
			render();
		}
	}
	
	private static void getInput(){
		game.getInput();
	}
	
	private static void update(){
		game.update();
	}
	
	private static void render(){
		glClear(GL_COLOR_BUFFER_BIT);
		glLoadIdentity();
		
		game.render();
		
		Display.update();
		Display.sync(60);
	}
	
	private static void initDisplay(){
		try{
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.create();
			Display.setVSyncEnabled(true);
			Keyboard.create();
		}catch(LWJGLException e){
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	private static void initGl(){
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), 0, Display.getHeight(), -1, 1);
		
		glMatrixMode(GL_MODELVIEW);
		
		glClearColor(0, 0, 0, 1);
		
		glDisable(GL_DEPTH_TEST);
	}
	
	private static void cleanUp(){
		Display.destroy();
		Keyboard.destroy();
	}
	
}