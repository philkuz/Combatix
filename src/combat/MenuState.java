package combat;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class MenuState extends BasicGameState
{
	private int id;
	public Button menuB, exitB, restartB;
	public MenuState(int id )
	{
		this.id = id;
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		Image menu = new Image("data/mainmenu.png");
		Image ex = new Image("data/exit.png");
		Image restart = new Image("data/exit.png");
		restartB = new Button(restart, CombatState.CAMWT/2-restart.getWidth()/2,200); 
		menuB = new Button(menu, CombatState.CAMWT/2-menu.getWidth()/2, restartB.getY()+restartB.getHeight());
		exitB = new Button(ex,CombatState.CAMWT/2-ex.getWidth()/2, menuB.getY()+menuB.getHeight());
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		Image gameOver = new Image("data/GameOver.png");
		gameOver.draw((CombatState.CAMWT-gameOver.getWidth())/2, 75);
		menuB.draw();
		exitB.draw();
		restartB.draw();
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		if(menuB.hit())
			sbg.enterState(Application.MAINMENU, new FadeOutTransition(), new FadeInTransition());
		if(exitB.hit())
			System.exit(0);
		if(restartB.hit())
		{
			((CombatState) sbg.getState(Application.COMBAT)).start();
			sbg.enterState(Application.COMBAT, new FadeOutTransition(), new FadeInTransition());
		}
	}

	public int getID()
	{
		return id;
	}
}
