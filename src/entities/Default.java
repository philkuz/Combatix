package entities;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import combat.*;

public class Default extends Hero
{
	public Default() throws SlickException
	{
		super();
		this.setImg(new Image("data/hero.png"));
	}
	public void attack() throws SlickException
	{
		new Bullet(getDir(), getCX(), getCY());
	}
	
}
