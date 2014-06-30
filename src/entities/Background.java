package entities;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import combat.*;

public class Background extends Entity 
{
	public Background() throws SlickException
	{
		super();
		setImg(new Image("data/tiledbg.jpg").getScaledCopy((int)CombatState.WT, (int)CombatState.HT));
	}
	public Shape getBoundingBox()
	{
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
	public boolean collidable()
	{
		return false;
	}
}
