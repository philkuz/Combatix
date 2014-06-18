package combat;


import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.*;

public class Application extends StateBasedGame
{
	public static final int MAINMENU = 0;
	public static final int WIDTH = 720;
	public static final int HEIGHT = 480;
	public static final int FPS = 60;
	
	public Application(String appName)
	{
		super(appName);
	}

	public void initStatesList(GameContainer arg0) throws SlickException 
	{
		this.addState(new CombatState(0));
	}
	
	public static void main(String[] args)
	{
		try{
			AppGameContainer app = new AppGameContainer(new Application("Relativity Simulations (C) Phillip Kuznetsov 2014"));
			app.setDisplayMode(WIDTH, HEIGHT, false);
			app.setIcon("data/neuron.png");
			app.setTargetFrameRate(FPS);
			app.setShowFPS(false);
			app.start();
		}catch(SlickException e){
			e.printStackTrace();
		}
	}
}