package combat;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

public class MainMenuState extends BasicGameState
{
	public Image icon;
	public Button start;
	public int id;
	public MainMenuState(int id)
	{
		this.id = id;
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		icon = new Image("data/hero.png");
		Image stBut = new Image("data/PLAY.png");
		start = new Button(stBut, Application.WIDTH/2-stBut.getWidth()/2, 400);
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		//g.draw(new Rectangle(10,10,100,100));
		icon.draw(Application.WIDTH/2-icon.getWidth()/2, 200);
		start.draw();
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
	{
		if(start.hit())
		{
			((CombatState) sbg.getState(1)).start();
			sbg.enterState(1, new FadeOutTransition(), new FadeInTransition());
		}
	}
	public int getID() 
	{
		return id;
	}
}
