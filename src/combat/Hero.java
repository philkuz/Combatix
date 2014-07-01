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
	private float dif;
	private boolean player;
	private Image defImg, hitImg;
	private float attack;
	private float agility;
	private float defense;
	private float xp;
	private int level;
	private int res = 0;
	private boolean hit = false;
	private ArrayList<Integer> colList;
	
	public Hero() throws SlickException
	{
		super();
		setSpd(.25f);
		defImg = new Image("data/neuron.png");
		hitImg = new Image("data/heroH.png");
		setDefImg(defImg);
		setHealth(100);
		setType("hero");
		dif = 10;
		level = 1;
		attack = 10;
		agility = 10;
		defense = 10;
		xp = 10;
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
		if(xp>Math.pow(1.5, level)*10)
		{
			levelUp();
		}
	}
	public void update(int delta) throws SlickException
	{
		checkXP();
		if(hit)
		{
			if(res > 100)
			{
				hit = false;
				setImg(getDefImg());
				orient();
			}
			else
			{
				res+=delta;
			}
		}
	}
	public void drawTravel(Graphics g)
	{
		
	}
	public void levelUp()
	{
		attack += 1;
		agility += 1;
		defense += 1;
		level += 1;
		setHealth(getHealth()+10);
	}
	public Shape getBoundingBox()
	{
		return new Rectangle(getX()+dif, getY()+dif, getWidth()-dif, getHeight()-dif);
	}
	public float getMaxHealth()
	{
		return 10*defense;
	}
	public float getHealthRatio()
	{
		return getHealth()/getMaxHealth();
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
	public Image getDefImg()
	{
		return defImg;
	}
	public int getLevel()
	{
		return level;
	}
	public float getSpd()
	{
		return 0.2f + agility * .01f;
	}
	public float getXP()
	{
		return xp;
	}
	public void hurt() throws SlickException
	{
		res = 0;
		hit = true;
		setImg(hitImg);
		orient();
	}
	public boolean isAlive()
	{
		if(getHealth()> 0)
		{
			return true;
		}
		else
		{
			CombatState.enemies--;
			for(int y = colList.size()-1; y >= 0; y--)
			{
				int id = CombatState.search(colList.get(y));
				if(id >= 0)
				{
					Entity e = CombatState.entList.get(id);
					if(e.ofType("hero"))
					{
						Hero h = (Hero)e;
						if(h.player)
							System.out.println("Increase of XP " + xp);
						h.addXp(xp);
						break;
					}
				}
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
	public void setDefImg(Image img)
	{
		setImg(img);
		defImg = img;
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
	public void setHitImg(Image image)
	{
		hitImg = image;
	}
}