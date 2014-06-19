package entities;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import combat.*;
public class SimpleAI extends Default
{
	public float speed;
	private float destX, destY, degree;
	private float dx, dy;
	private float[] xA;
	private float[] yA;
	private boolean xbit, ybit;
	public static int count;
	public SimpleAI() throws SlickException
	{
		super();
		speed = 0.1f;
		setX(20);
		setY(20);
		destX = getX();
		destY = getY();
		xA = new float[4];
		yA = new float[4];
		xA[0]=40;xA[1]=40;xA[2]=240;xA[3]=240;
		yA[0]=40;yA[1]=240;yA[2]=40;yA[3]=240;
		count = 3;
	}
	public void update(int delta)
	{
		randMove(delta);
		//default implementation
		if(!isAlive())
		{
			delete();
		}
	}
	public void randMove(int delta)
	{
		float cur = speed*delta;
		if(destX == getX() && destY == getY())
		{
			Rectangle bounds= CombatState.getBounderies();
			destX = (float)Math.random()*bounds.getWidth()+bounds.getX();
			destY = (float)Math.random()*bounds.getHeight()+bounds.getY();
			/*destX = xA[count];
			destY = yA[count];
			count = (count+1)%4;*/
			float adj = destX-getCX();//x
			float opp = destY-getCY();//y
			float tan = (float)Math.atan(opp/adj);
			if(adj < 0)
			{
				tan = (float)Math.PI+tan;
			}
			degree = tan;
			if(destX>bounds.getX()+bounds.getWidth()-getWidth())
			{
				destX = bounds.getX()+bounds.getWidth()-getWidth();
			}
			if(destY>bounds.getY()+bounds.getHeight()-getHeight())
			{
				destY = bounds.getY()+bounds.getHeight()-getHeight();
			}
			System.out.println("New destination: "+destX+", "+destY);
			dx = 0;
			dy = 0;
			xbit = true;
			ybit = true;
		}	
			
		if((dx > 0 && getX()+dx >= destX )||(dx < 0 && getX() + dx <= destX))
		{
			setX(destX);
			dx = 0;
			xbit = false;
			if(ybit)
			{
				dy = cur;
				ybit = false;
			}
		}
		else if(xbit)
			dx = cur*(float)Math.cos(degree);
		if((dy > 0 && getY()+ dy > destY )||(dy < 0 && getY() + dy < destY))
		{
			setY(destY);
			dy = 0;
			ybit = false;
			if(xbit)
			{
				dx = cur;
				xbit = false;
			}
		}
		else if(ybit)
			dy = cur*(float)Math.sin(degree);
		move(dx, dy);
	}
	public void drawTravel(Graphics g)
	{
		g.drawLine(getX(), getY(), destX, destY);
	}

}
