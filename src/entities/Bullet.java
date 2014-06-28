package entities;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import combat.*;

public class Bullet extends Entity
{
	private double slope;
	private float strength;
	public boolean onScr;
	private int source;

	//m = direction facing, x and y are cartesian starting coordinates, and ID is the id of the source object
	
	public Bullet(float m, float x, float y, float attack, int src) throws SlickException
	{
		setImg((new Image("data/neuron.png")).getScaledCopy(.2f));
		setX(x-getWidth()/2);
		setY(y-getHeight()/2);
		this.slope = m;
		setSpd(0.8f);
		onScr = true;
		CombatState.addEnt(this);
		setType("bullet");
		source = src;
		strength = attack;
	}
	public void draw()
	{
		if(onScr)
		{
			super.draw();
		}
	}
	public void collision(Entity e)
	{
		if(e.ofType("hero"))
		{
			e.setHealth(e.getHealth()-strength);
			((Hero)e).addCol(source);
			try {
				((Hero)e).hurt();
			} catch (SlickException e1) {}
		}
		delete();
	}
	public void update(int delta)
	{
		checkScr();
		if(!onScr)
		{
			delete();
		}
		else
		{
			float del = (float)delta;
			float xVel = (float)Math.cos(slope)*getSpd()*del;
			float yVel = (float)Math.sin(slope)*getSpd()*del;
			move(xVel, yVel);
		}
		
	}
	public void checkScr()
	{
		float wdt = CombatState.WT;
		float hgt = CombatState.HT;
		if(getX() < -getWidth() || getY() < -getHeight()|| getX() > wdt || getY() > hgt)
		{
			onScr = false;
		}
	}
	public void move(float dx, float dy)
	{
		Rectangle r = CombatState.getBounderies();
		if(getX()+dx < r.getX() || getX()+dx > r.getX()+r.getWidth()-getWidth())
			this.delete();
		else if(getY()+dy < r.getY() || getY()+dy > r.getY()+r.getHeight()-getHeight())
			this.delete();
		else
			super.move(dx, dy);
	}
	public Shape getBoundingBox()
	{
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
	public void hit(Hero e)
	{
		e.setHealth(e.getHealth()-strength);
		onScr = false;
		delete();
	}	
}
