package entities;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import combat.*;

public class Background extends Entity 
{
	public Background() throws SlickException
	{
		super();
		setImg(new Image("data/background.jpg").getScaledCopy(.3f));
	}
}
