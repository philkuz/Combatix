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
	public static float HT = Application.HEIGHT*4;
	public static float WT = Application.WIDTH*4;
	final public static float CAMHT = Application.HEIGHT;
	final public static float CAMWT = Application.WIDTH;
	
	public static float border;
	public Image bg;
	public Image img;
	int state;
	public static float mseX, mseY;
	public static ArrayList<Integer> unID;//unused IDS
	public static ArrayList<Entity> bulList;
	public static ArrayList<Hero> heroList;
	public static ArrayList<Entity> entList;
	public Player pl;
	public static float bgX, bgY;
	public static Rectangle boundaries, camBound;
	public float buf;
	public static float cX, cY, dXC, dYC;//Camera location.
	
	public CombatState(int state)
	{
		this.state = state;
		unID = new ArrayList<Integer>();
		bulList = new ArrayList<Entity>();
		heroList = new ArrayList<Hero>();
		entList = new ArrayList<Entity>();
	}
	
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		pl = new Player();
		mseX = 0;
		mseY = 0;
		Background bg = new Background();
		addEnt(bg);
		WT = bg.getWidth();
		HT = bg.getHeight();
		for(int x = 0; x < 10; x++)
		{
			SimpleAI l = new SimpleAI();
			l.setLoc((float)Math.random()*WT, (float)Math.random()*HT);
			addEnt(l);
		}
		
		addEnt(pl.getHero());
		
		
		
		border = 10;
		bgX = 0;
		bgY = 0;
		buf = 150;
		boundaries = new Rectangle(border, border, WT-border*2, HT-border*2);
		camBound = new Rectangle(buf,buf,CAMWT-buf*2, CAMHT-buf*2);
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		g.drawString("("+mseX+", "+mseY+")", mseX, mseY);
		pl.draw();
		for(Entity e: entList)
		{
			e.draw();
			g.drawString(""+e.getHealth(), e.getCamX(), e.getCamY());
		}
		//g.draw(camBound);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
	{
		Input input = gc.getInput();
		mseX = input.getMouseX();
		mseY = input.getMouseY();
		pl.control(delta, input);
		float units = 0.5f*delta;

		for(int x = 0; x < entList.size(); x++)
		{
			Entity e = entList.get(x);
			e.update(delta);
		}		
		//Combination of hero and bullet list to enable collision detection from prolonged entities
		//ie walls
		Hero h = pl.getHero();
		if(!h.getCamBox().intersects(camBound))
		{
			float spd = h.getSpd()*delta;
			dXC = (float)Math.cos(Math.PI/4)*spd;
			dYC = (float)Math.sin(Math.PI/4)*spd;
			//System.out.println("true");
			if(h.getX()<cX+buf&&cX>0)
			{
				System.out.print("Left");
				cX -= dXC;
				if(cX<0)
				{
					cX = 0;
				}
			}
			if(h.getX()+h.getWidth()>cX+camBound.getWidth()&&cX<WT-CAMWT)
			{
				System.out.print("Right");
				cX += dXC;
				if(cX+CAMWT>WT)
				{
					cX = WT-CAMWT;
				}
			}
			if(h.getY() < cY+buf&&cY>0)
			{
				System.out.print("Up");
			
				cY -= dYC;
				if(cY<0)
				{
					cY = 0;
				}
			}
			if(h.getY()+h.getHeight()>cY+camBound.getHeight()&&cY<HT-CAMHT)
			{
				cY += dYC;
				System.out.print("Down");
				if(cY+CAMHT>HT)
				{
					cY = HT-CAMHT;
				}
			}
			System.out.println();
		}
		for(int x=0; x< entList.size(); x++)
		{
			Entity e = entList.get(x);
			for(int y = 0; y < entList.size(); y++)
			{
				Entity f = entList.get(y);
				if(e.collidable()&&f.collidable())
				{
					if((!e.equals(f))&&e.intersection(f))
					{
						e.collision(f);
					}
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
			return entList.size();
		}
		else
		{
			int ret = unID.get(0);
			unID.remove(0);
			return ret;
		}
	}
	public static void addEnt(Entity e)
	{
		int id = entID();
		e.setID(id);
		entList.add(e);
	}
	public static void delEnt(Entity e)
	{
		entList.remove(e);
		unID.add((Integer)e.getID());
	}
	public static Rectangle getBounderies()
	{
		return boundaries;
	}
	//Camera x coordinate, not to be confused with center coordinate
	public static float getCX()
	{
		return cX;
	}
	//Camera y coordiante, not to be confused with center coordinate
	public static float getCY()
	{
		return cY;
	}
}