package combat;


import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import entities.*;

public class CombatState extends BasicGameState
{
	//HT and WT are the dimensions of the "world" CAMHT and CAMWT are the camera dimensions
	public static float HT = Application.HEIGHT*2;
	public static float WT = Application.WIDTH*2;
	final public static float CAMHT = Application.HEIGHT;
	final public static float CAMWT = Application.WIDTH;
	
	public static float border;
	public Image bg;
	public Image img;
	int state;
	public static float mseX, mseY;
	public static ArrayList<Entity> entList;
	public Player pl;
	public static float bgX, bgY;
	public static Rectangle boundaries, camBound;
	public float buf;
	public static float cX, cY, dXC, dYC;//Camera location.
	public static int enemies;
	
	
	public CombatState(int state)
	{
		this.state = state;
		
	}
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException 
	{
		enemies = 0;
		start();
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
	{
		//g.drawString("("+mseX+", "+mseY+")", mseX, mseY);
		pl.draw();
		for(Entity e: entList)
		{
			e.draw();
		}
		g.setColor(Color.gray);
		g.fill(new Rectangle(CAMWT-120,20, 100,120));
		
		g.setColor(Color.white);
		g.drawString("Level: "+pl.getHero().getLevel(), CAMWT-110, 25);
		g.drawString("XP: "+pl.getHero().getXP(), CAMWT-110, 60);
		g.drawString("Atk: "+pl.getHero().getAtk(), CAMWT-110, 75);
		g.drawString("Agl: "+pl.getHero().getAgl(), CAMWT-110, 90);
		g.drawString("Def: "+pl.getHero().getDef(), CAMWT-110, 105);
		g.drawString("Left?: "+ enemies, CAMWT-110, 120);
		g.setColor(Color.red);
		g.fill(new Rectangle(CAMWT-110, 45,80, 15));
		g.setColor(Color.green);
		g.fill(new Rectangle(CAMWT-110, 45, pl.getHero().getHealthRatio()*80, 15));
		g.setColor(Color.white);
		g.drawString(pl.getHero().getHealth()+"/"+pl.getHero().getMaxHealth(), CAMWT-110, 45);
		//g.draw(camBound);
		miniMap(g);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException 
	{
		Input input = gc.getInput();
		mseX = input.getMouseX();
		mseY = input.getMouseY();
		pl.control(delta, input);
		for(int x = 0; x < entList.size(); x++)
		{
			Entity e = entList.get(x);
			e.update(delta);
		}		
		//Combination of hero and bullet list to enable collision detection from prolonged entities
		//ie walls
		Hero h = pl.getHero();
		if(!pl.getHero().isAlive())
		{
			sbg.enterState(Application.MENU, new FadeOutTransition(), new FadeInTransition());
		}
		int mult = 0;
		if(input.isKeyPressed(Input.KEY_Q))
		{
			mult = (mult+1)%8;
		}
		if(!h.getCamBox().intersects(camBound)&&pl.getHero().isAlive())
		{
			float spd = h.getSpd()*delta;
			//dXC = (float)(Math.cos(h.getDir())+Math.PI/8*mult)*spd;
			//dYC = (float)(Math.sin(h.getDir())+Math.PI/8*mult)*spd;
			dXC = ((float)Math.cos(Math.PI/4)-0.01f)*spd;
			dYC = ((float)Math.sin(Math.PI/4)-0.01f)*spd;
			//System.out.println("true");
			if(h.getX()<cX+buf&&cX>0)
			{
				cX -= dXC;
				if(cX<0)
				{
					cX = 0;
				}
			}
			if(h.getX()+h.getWidth()>cX+camBound.getWidth()&&cX<WT-CAMWT)
			{
				cX += dXC;
				if(cX+CAMWT>WT)
				{
					cX = WT-CAMWT;
				}
			}
			if(h.getY() < cY+buf&&cY>0)
			{
				cY -= dYC;
				if(cY<0)
				{
					cY = 0;
				}
			}
			if(h.getY()+h.getHeight()>cY+camBound.getHeight()&&cY<HT-CAMHT)
			{
				cY += dYC;
				if(cY+CAMHT>HT)
				{
					cY = HT-CAMHT;
				}
			}
			
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
	public void start() throws SlickException
	{
		enemies = 0;
		pl = new Player();
		entList = new ArrayList<Entity>();
		Background bg = new Background();
		addEnt(bg);
		WT = bg.getWidth();
		HT = bg.getHeight();
		for(int x = 0; x < 100; x++)
		{
			System.out.println(enemies);
			SimpleAI l = new SimpleAI();
			l.setLoc((float)Math.random()*(WT-l.getWidth()), (float)Math.random()*(HT-l.getHeight()));
			addEnt(l);
		}
		addEnt(pl.getHero());
		cX = 0;
		cY = 0;
		border = 10;
		bgX = 0;
		bgY = 0;
		buf = 150;
		boundaries = new Rectangle(border, border, WT-border*2, HT-border*2);
		camBound = new Rectangle(buf,buf,CAMWT-buf*2, CAMHT-buf*2);
	}
	public static int entID()
	{
		return entList.size();
	}
	public static void miniMap(Graphics g)
	{
		float mmW = 200;
		float mmH = mmW*9/16;
		float mmX = 10;
		float mmY = CAMHT-(10+mmH);
		
		float scale = mmW/WT;
		for(Entity e: entList)
		{
			e.draw(e.getX()*scale+mmX, e.getY()*scale+mmY, scale);
		}
		Rectangle r= new Rectangle(cX*scale+mmX, cY*scale+mmY, CAMWT*scale, CAMHT*scale);
		g.draw(r);
	}
	public static void addEnt(Entity e)
	{
		int id = entID();
		e.setID(id);
		if(id < entList.size())
			entList.add(id, e);
		else
			entList.add(e);
	}
	public static void delEnt(Entity e)
	{
		entList.remove(e);
		//unID.add((Integer)e.getID());
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
	//Camera y coordinate, not to be confused with center coordinate
	public static float getCY()
	{
		return cY;
	}
	public static void credit(int id, float xp)
	{
		Entity e = entList.get(search(id));
		if(e.ofType("hero"))
		{
			Hero h = (Hero)e;
			h.addXp(xp);	
		}
	}
	public static int search(int id)
	{
		int idx = 0;
		int curID = entList.get(0).getID();
		
		while(curID<id)
		{
			idx++;
			curID = entList.get(idx).getID();
		}
		if(curID == id)
		{
			return idx;
		}
		else
		{
			return -1;
		}
	}
	public static Shape getCam()
	{
		Rectangle r = new Rectangle(cX, cY, CAMWT, CAMHT);
		return r;
	}
}