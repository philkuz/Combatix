package entities;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

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
		Shape bnds = getBoundingBox();
		float radius = (float)Math.sqrt(Math.pow(bnds.getWidth()/2, 2)+Math.pow(bnds.getHeight()/2, 2));
		new Bullet(getDir(), getCX()+radius*(float)Math.cos(getDir()), getCY()+radius*(float)Math.sin(getDir()));
	}
	
}
