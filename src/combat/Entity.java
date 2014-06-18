package combat;

import java.text.DecimalFormat;

import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public abstract class Entity 
{
	
	private int id;
	private float x,y;
	private float speed;
	private Shape boundingBox;
	private Image img;
	private String type;
	private float dir;
	
	public Entity()
	{
		speed = .5f;
		id = -1;
		setType("entity");
	}
	public void delete()
	{
		CombatState.delBul(this);
	}
	public void move(float dx, float dy)
	{
		Rectangle r = CombatState.getBounderies();
		if(x+dx > r.getX() && x+dx < r.getX()+r.getWidth()-getWidth())
			x += dx;
		if(y+dy > r.getY() && y+dy < r.getY()+r.getHeight()-getHeight())
			y += dy;
	}
	public void turn(float deg)
	{
		dir+=deg;
	}
	public void update(int delta)
	{
		
	}
	public void orient()
	{
		img.setRotation(getDir()/(float)Math.PI*180);
	}
	
	public void draw()
	{
		img.draw(x,y);
	}
	public Shape getBoundingBox()
	{
		return boundingBox;
	}
	public float getCX()
	{
		return x+img.getWidth()/2;
	}
	public float getCY()
	{
		return y+img.getHeight()/2;
	}
	public float getDir()
	{
		return dir;
	}
	public float getMseDir()
	{
		float adj = CombatState.mseX-getCX();//x
		float opp = CombatState.mseY-getCY();//y
		float tan = (float)Math.atan(opp/adj);
		if(adj < 0)
		{
			tan = (float)Math.PI+tan;
			
		}
		return tan;
	}
	public float getHeight()
	{
		return img.getHeight();
	}
	public int getID()
	{
		return id;
	}
	public float getSpeed()
	{
		return speed;
	}
	public String getType()
	{
		return type;
	}
	
	public String getStrID()
	{
		DecimalFormat df = new DecimalFormat();
		df.setMinimumIntegerDigits(3);
		return df.format(id);
	}
	public float getWidth()
	{
		return img.getWidth();
	}
	public float getX()
	{
		return x;
	}
	public float getY()
	{
		return y;
	}
	public void setLoc(float x, float y)
	{
		this.x = x;
		this.y = y;
	}	
	public void setID(int id)
	{
		this.id = id;
	}
	public void setImg(Image img)
	{
		this.img = img;
	}
	public void setX(float x)
	{
		this.x = x;
	}
	public void setY(float y)
	{
		this.y = y;
	}
	public void setSpd(float speed)
	{
		this.speed = speed;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public boolean sameType(Entity e)
	{
		if(e.getType().equals(getType()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public boolean intersection(Entity e)
	{
		if(this.getBoundingBox().intersects(e.getBoundingBox()))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public void hit(Hero e)
	{
	}
}	