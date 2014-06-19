package combat;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;


public class Hero extends Entity
{
	private float speed;
	private float health;
	public Hero() throws SlickException
	{
		super();
		speed = .25f;
		setImg(new Image("data/neuron.png"));
		setHealth(100);
		setType("hero");
	}
	public void attack() throws SlickException
	{
		
	}
	public void drawTravel(Graphics g)
	{
		
	}
	public void health(float amt)
	{
		health += amt;
	}
	public Shape getBoundingBox()
	{
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
	public float getHealth()
	{
		return health;
	}
	public float getSpd()
	{
		return speed;
	}
	public boolean isAlive()
	{
		if(health > 0)
			return true;
		else
			return false;
	}
	public void setHealth(float health)
	{
		this.health = health;
	}
	public void setSpd(float spd)
	{
		speed = spd;
	}
}
