package combat;

import java.util.ArrayList;

import org.lwjgl.util.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;


public class Hero extends Entity
{
	private float maxHealth;
	private float dif;
	private boolean player;
	private float attack;
	private float agility;
	private float defense;
	private float xp;
	private int level;
	private ArrayList<Integer> colList;
	
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
		colList = new ArrayList<Integer>();
	}
	public void attack() throws SlickException
	{
		
	}
	public void addCol(int id)
	{
		colList.remove((Integer)id);
		colList.add(id);
	}
	public void addXp(float exp)
	{
		xp +=exp;
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
	public float getMaxHealth()
	{
		return maxHealth;
	}
	public float getHealthRatio()
	{
		return getHealth()/maxHealth;
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
		return agility*.025f;
	}
	public float getXP()
	{
		return xp;
	}
	public boolean isAlive()
	{
		if(getHealth()> 0)
		{
			return true;
		}
		else
		{
			int x = 1;
			while(true)
			{
				Entity e = CombatState.entList.get(CombatState.search(colList.get(colList.size()-x)));
				if(e.ofType("hero"))
				{
					Hero h = (Hero)e;
					h.addXp(xp);
					break;
				}
				if(x == colList.size())
				{
					break;
				}
				x++;//has to be placed after the size check to make sure the loop isn't ended prematurely.
			}
			return false;
		}
	}
	public void setColor(Color cl)
	{
		Image img = getImg();
		img.setImageColor(cl.getRed(), cl.getGreen(), cl.getBlue());
		setImg(img);
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