package combat;


import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import entities.*;

public class CombatState extends BasicGameState
{
	//HT and WT are the dimensions of the "world" CAMHT and CAMWT are the camera dimensions
	final public static float HT = Application.HEIGHT;
	final public static float WT = Application.WIDTH;
	final public static float CAMWT = Application.HEIGHT;
	final public static float CAMHT = Application.WIDTH;
	
	public static float border;
	public Image bg;
	public Image img;
	int state;
	public static float mseX, mseY;
	public static ArrayList<Integer> unID;//unused IDS
	public static ArrayList<Entity> bulList;
	public static ArrayList<Hero> heroList;
	public Player pl;
	public static float bgX, bgY;
	public static Rectangle boundaries;
	
	public CombatState(int state)
	{
		this.state = state;
		unID = new ArrayList<Integer>();
		bulList = new ArrayList<Entity>();
		heroList = new ArrayList<Hero>();
		
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		pl = new Player();
		mseX = 0;
		mseY = 0;
		Hero h = new SimpleAI();
		h.setLoc(20,20);
		addHero(h);
		//bg = new Image("data/background.jpg");
		border = 10;
		bgX = 0;
		bgY = 0;
		boundaries = new Rectangle(border, border, WT-border, HT-border);
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		//bg.draw(bgX, bgY);
		g.drawString("("+mseX+", "+mseY+")", mseX, mseY);
		pl.draw();
		for(Entity e: bulList)
		{
			e.draw();
		}
		for(Hero h: heroList)
		{
			h.draw();
			g.drawString(""+h.getHealth(), h.getX(), h.getY());
		}
		g.draw(getBounderies());
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
	{
		Input input = gc.getInput();
		mseX = input.getMouseX();
		mseY = input.getMouseY();
		pl.control(delta, input);
		float units = 0.5f*delta;
		for(int x = 0; x < bulList.size(); x++)
		{
			Entity e = bulList.get(x);
			e.update(delta);
		}
		for(int y = 0; y < heroList.size(); y++)
		{
			Hero h = heroList.get(y);
			h.update(delta);
		}
		if(input.isKeyDown(Input.KEY_W))
		{
			bgY += -units;
		}
		if(input.isKeyDown(Input.KEY_S))
		{
			bgY += units;
		}
		if(input.isKeyDown(Input.KEY_A))
		{
			bgX -= units;
		}
		if(input.isKeyDown(Input.KEY_D))
		{
			bgX += units;
		}
		/*
		for(int x = 0; x < entList.size(); x++)
		{
			Entity e = entList.get(x);
			if(e.sameType(new Bullet(0,0,0)))
			{
				for(int y = 0; y < entList.size(); y++)
				{
					if(x != y)
					{
						Entity f = entList.get(y);
						if(e.intersection(f))
						{
							e.checkHit(f);
						}
					}
					System.out.println("cunt");
				}
			}
		}*/
		for(int x = 0; x < bulList.size(); x++)
		{
			Entity e = bulList.get(x);
			for(int y = 0; y < heroList.size(); y++)
			{
				Hero h = heroList.get(y);
				if(h.intersection(e))
				{
					e.hit(h);
					break;
				}
			}
		}
	}

	public int getID() 
	{
		return state;
	}
	public static int entID()
	{
		if(unID.size() == 0)
		{
			return bulList.size();
		}
		else
		{
			int ret = unID.get(0);
			unID.remove(0);
			return ret;
		}
	}
	public static void addBul(Entity e)
	{
		int id = entID();
		e.setID(id);
		bulList.add(e);
	}
	public static void addHero(Hero h)
	{
		heroList.add(h);
	}
	//implement binary search
	public static void delBul(Entity e)
	{
		bulList.remove(e);
	}
	public static void delHero(Hero h)
	{
		heroList.remove(h);
	}
	public static Rectangle getBounderies()
	{
		return boundaries;
	}
	
	
	
}