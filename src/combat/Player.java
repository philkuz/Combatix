package combat;

import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import entities.*;

public class Player 
{
	final public static float HT = Application.HEIGHT;
	final public static float WT = Application.WIDTH;
	private Hero hero;
	private int lol;
	private boolean chill;
	private int shotRate = 200;
	private float turnRate;
	private Input ipt;
	public Player() throws SlickException
	{
		hero = new Default();
		hero.setX(20);
		hero.setY(20);
		hero.togglePlayer();
		hero.setDefImg(new Image("data/hero.png").getScaledCopy(hero.getImg().getHeight(), hero.getImg().getWidth()));
		hero.setHitImg(new Image("data/hero.png").getScaledCopy(hero.getImg().getHeight(), hero.getImg().getWidth()));
		hero.setHealth(100);
		lol = shotRate;
		chill = false;
		turnRate = 0.0075f;
	}
	public Hero getHero()
	{
		return hero;
	}
	public void control(int delta, Input ipt) throws SlickException
	{
		this.ipt = ipt;
		float spd = hero.getSpd()*delta;
		lol+=delta;
		hero.checkXP();
		//Traditional movement implementation
		/**/
		angleAdj(delta, spd);
		if(ipt.isKeyPressed(Input.KEY_SPACE))
		{
			if(chill)
			{
				hero.attack();
				chill = false;
			}
		}
		if(lol>shotRate)
		{
			lol = 0;
			chill = true;
		}
		hero.orient();
	}
	public void angleAdj(int delta, float spd)
	{
		//angled movement implementation
		if(ipt.isKeyDown(Input.KEY_UP))
		{
			hero.move((float)Math.cos(hero.getDir())*spd, (float)Math.sin(hero.getDir())*spd);
		}
		if(ipt.isKeyDown(Input.KEY_DOWN))
		{
			hero.move(-(float)Math.cos(hero.getDir())*spd, -(float)Math.sin(hero.getDir())*spd);
		}
		if(ipt.isKeyDown(Input.KEY_RIGHT))
		{
			hero.turn(turnRate*delta);
		}
		if(ipt.isKeyDown(Input.KEY_LEFT))
		{
			hero.turn(-turnRate*delta);
		}
	}
	//Previous implementation of movement, as of writing not implemented and 
	//I advise not to use it because it feels unnatural.
	public void lateralAdj(int delta, float spd)
	{
		float dia = (float) (spd*Math.cos(Math.PI/4));
		float border = CombatState.border;
		float right = WT-(border+hero.getWidth());
		float bot = HT-(border+hero.getHeight());
		if(ipt.isKeyDown(Input.KEY_UP)&&!(ipt.isKeyDown(Input.KEY_LEFT)||ipt.isKeyDown(Input.KEY_RIGHT)))
		{
			if(!(getY()-spd<border))
				hero.move(0, -spd);
			else
				hero.setY(border);
		}
		if(ipt.isKeyDown(Input.KEY_DOWN)&&!(ipt.isKeyDown(Input.KEY_LEFT)||ipt.isKeyDown(Input.KEY_RIGHT)))
		{
			if(!(getY()+spd>bot))
				hero.move(0, spd);
			else
				hero.setY(bot);
		}
		if(ipt.isKeyDown(Input.KEY_LEFT)&&!(ipt.isKeyDown(Input.KEY_UP)||ipt.isKeyDown(Input.KEY_DOWN)))
		{
			if(!(getX()-spd<border))
				hero.move(-spd,0);
			else
				hero.setX(border);
		}
		if(ipt.isKeyDown(Input.KEY_RIGHT)&&!(ipt.isKeyDown(Input.KEY_UP)||ipt.isKeyDown(Input.KEY_DOWN)))
		{
			if(!(getX()+spd>right))
				hero.move(spd,0);
			else
				hero.setX(right);
		}
		if(ipt.isKeyDown(Input.KEY_LEFT)&&ipt.isKeyDown(Input.KEY_DOWN))
		{
			if(!(getX()-spd<border))
				hero.move(-dia,0);
			else
				hero.setX(border);
			if(!(getY()+spd>bot))
				hero.move(0, dia);
			else
				hero.setY(bot);
		}
		if(ipt.isKeyDown(Input.KEY_RIGHT)&&ipt.isKeyDown(Input.KEY_DOWN))
		{
			if(!(getX()+spd>right))
				hero.move(dia,0);
			else
				hero.setX(right);
			if(!(getY()+spd>bot))
				hero.move(0, dia);
			else
				hero.setY(bot);
		}
		if(ipt.isKeyDown(Input.KEY_LEFT)&&ipt.isKeyDown(Input.KEY_UP))
		{
			if(!(getY()-spd<border))
				hero.move(0, -dia);
			else
				hero.setY(border);
			if(!(getX()-spd<border))
				hero.move(-dia,0);
			else
				hero.setX(border);
		}
		if(ipt.isKeyDown(Input.KEY_RIGHT)&&ipt.isKeyDown(Input.KEY_UP))
		{
			if(!(getX()+spd>right))
				hero.move(dia,0);
			else
				hero.setX(right);
			if(!(getY()-spd<border))
				hero.move(0, -dia);
			else
				hero.setY(border);
		}
	}
	public void draw()
	{
		hero.draw();
	}
	public float getSpeed()
	{
		return hero.getSpd();
	}
	public String getHealth()
	{
		String health = ""+hero.getHealth();
		return health;
	}
	public float getX()
	{
		return hero.getX();
	}
	public float getY()
	{
		return hero.getY();
	}
	
}
