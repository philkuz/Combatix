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
	public float y;
	public MainMenuState(int id)
	{
		this.id = id;
	}

	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		icon = new Image("data/hero.png").getScaledCopy(4);
		Image stBut = new Image("data/PLAY.png").getScaledCopy(2);
		float sep = 75;
		y = (Application.HEIGHT-(icon.getHeight()+sep+stBut.getHeight()))/2;
		start = new Button(stBut, 10, icon.getHeight()+30);
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		//g.draw(new Rectangle(10,10,100,100));
		icon.draw(10, 10);
		start.draw();
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
	{
		if(start.hit())
		{
			((CombatState) sbg.getState(1)).start();
			sbg.enterState(1, new FadeOutTransition(), null);
		}
	}
	public int getID() 
	{
		return id;
	}
}
