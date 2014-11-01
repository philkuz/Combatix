package entities;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import combat.*;

public class Barrier extends Entity
{
	Shape outline;
	public Barrier(Shape shp)
	{
		setType("barrier");
		setX(shp.getX());
		setY(shp.getY());
		this.outline = shp;
	}
	public Barrier(float x, float y, Image img)
	{
		setType("barrier");
		setX(x);
		setY(y);
		setImg(img);
		outline = new Rectangle(x, y, img.getWidth(), img.getHeight());
	}
	public void update(int delta)
	{
		//setDX(0.05f*delta);
		//setDY(0.05f*delta);
		super.update(delta);
	}
	public void collision(Entity e)
	{
		outline = getBoundingBox();
		if(!e.ofType("barrier"))
		{
			if(e.getX()<outline.getX()&&e.getDX() > 0)
			{
				e.move(-e.getDX(), 0);
				e.setDX(getDX());
				System.out.println("Left");
			}
			else if(e.getX()+e.getWidth()>(getX()+getWidth())&&e.getDX() < 0)
			{
				e.move(-e.getDX(), 0);
				e.setDX(getDX());
				System.out.println("Right");
			}
			if(e.getY()<outline.getY()&&e.getDY()>0)
			{
				e.move(0,-e.getDY());
				e.setDY(getDY());
				System.out.println("Up");
			}
			else if(e.getY()+e.getHeight()>outline.getY()+outline.getHeight()&&e.getDY()<0)
			{
				e.move(0,-e.getDY());
				e.setDY(getDY());
				System.out.println("Down");
			}
		}
	}
	public Shape getBoundingBox()
	{
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
}
