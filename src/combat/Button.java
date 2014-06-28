package combat;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;


public class Button 
{
	private Image img;
	private float x;
	private float y;
	private float width;
	private float height;
	public Button(Image img, float x, float y)
	{
		this.img = img;
		this.x=x;
		this.y=y;
		this.width = img.getWidth();
		this.height = img.getHeight();
	}
	
	public Button(float x, float y, float width, float height)
	{
		this.x=x;
		this.y=y;
		this.width = width;
		this.height = height;
	}
	public Rectangle getBounds()
	{
		return (new Rectangle(x,y,width,height));
	}
	public boolean mouseOver()
	{
		if(this.getBounds().contains(Mouse.getX(), Application.HEIGHT-Mouse.getY()))
			return true;
		else
			return false;
	}
	public boolean hit()
	{
		if(mouseOver()&&Mouse.isButtonDown(0))
			return true;
		else 
			return false;
	}
	public void draw()
	{
		img.draw(x,y);
	}
	public float getX()
	{
		return x;
	}
	public float getY()
	{
		return y;
	}
	public float getWidth()
	{
		return width; 
	}
	public float getHeight()
	{
		return height;
	}
	public void draw(Graphics g)
	{
		g.drawRect(x,y,width, height);
	}
}
