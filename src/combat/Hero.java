package combat;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;


public class Hero extends Entity
{
	private float health;
	private float maxHealth;
	private float dif;
	private boolean player;
	private float attack;
	private float agility;
	private float defense;
	private float xp;
	private int level;
	
	public Hero() throws SlickException
	{
		super();
		setSpd(.25f);
		setImg(new Image("data/neuron.png"));
		setHealth(100);
		maxHealth = 100;
		setType("hero");
		dif = 10;
		level = 1;
		attack = 10;
		agility = 10;
		defense = 10;
		xp = 0;
		player = false;
	}
	public void attack() throws SlickException
	{
		
	}
	public void checkXP()
	{
		if(xp>Math.pow(1.5, level)+9.5)
		{
			levelUp();
		}
	}
	public void update(int delta) throws SlickException
	{
		checkXP();
	}
	public void drawTravel(Graphics g)
	{
		
	}
	public void health(float amt)
	{
		health += amt;
	}
	public void levelUp()
	{
		attack += 3;
		agility += 3;
		defense += 3;
		level += 1;
	}
	public Shape getBoundingBox()
	{
		return new Rectangle(getX()+dif, getY()+dif, getWidth()-dif, getHeight()-dif);
	}
	public float getHealth()
	{
		return health;
	}
	public float getMaxHealth()
	{
		return maxHealth;
	}
	public float getHealthRatio()
	{
		return health/maxHealth;
	}
	public float getAtk()
	{
		return attack;
	}
	public float getDef()
	{
		return defense;
	}
	public float getAgl()
	{
		return agility;
	}
	public int getLevel()
	{
		return level;
	}
	public float getSpd()
	{
		return agility*2.5f;
	}
	public float getXP()
	{
		return xp;
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
	public void setDif(float differ)
	{
		this.dif = differ;
	}
	public void setAtk(float atk)
	{
		attack = atk;
	}
	public void setDef(float def)
	{
		defense = def;
	}
	public void setAgl(float agl)
	{
		agility = agl;
	}
	public void togglePlayer()
	{
		if(player)
			player = false;
		else
			player = true;
	}
}