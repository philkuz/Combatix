package entities;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import combat.*;
public class SimpleAI extends Default
{
	public float speed;
	private float aV;//angular velocity
	private int angleDir;
	private float destX, destY, degree;
	private float destT;//destination angle
	private float dx, dy, dT;
	private float[] xA;
	private float[] yA;
	private boolean xbit, ybit;
	private int shotRate = 1000;
	private int curInt;
	private boolean init;
	private boolean test;
	public SimpleAI() throws SlickException
	{
		super();
		speed = 0.1f;
		aV = 0.0025f;
		xA = new float[4];
		yA = new float[4];
		xA[0]=40;xA[1]=40;xA[2]=240;xA[3]=240;
		yA[0]=40;yA[1]=240;yA[2]=40;yA[3]=240;
		curInt = shotRate;
		init = true;
		test = true;
	}
	public void update(int delta) throws SlickException
	{
		randTurn(delta);
		orient();
		//default implementation
		if(!isAlive())
		{
			delete();
		}
		if(curInt >= shotRate)
		{
			attack();
			curInt = 0;
		}
		else
			curInt+=delta;
	}
	//movement to encapsulate the random lateral 2D movement (movement comparable to that
	//of lateral adj in Player.java. Extremely buggy and not in use.
	public void randMove(int delta)
	{
		if(init)
		{
			destX = getX();
			destY = getY();
			destT = getDir();
			init = false;
		}
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
	//Movement relying on random angle directions, but travelling in the direction faced.
	//so far no bugs. :) (Uses the Player.angleAdj() method of movement)
	public void randTurn(int delta)
	{
		//the problem stems from the radians bs
		if(getDir() == destT)
		{
			destT = (float)(Math.random()*Math.PI*2);
			float sepa = destT-getDir();
			if(sepa > Math.PI ||(sepa < 0 && sepa > -Math.PI))
				angleDir = -1;
			else
				angleDir = 1;
		}
		dT = angleDir*aV*delta;
		/*if((angleDir == 1 && (getDir()+ dT)%(2*Math.PI) > destT))
		{
			if(Math.abs(destT-getDir())>0.0425)
			{
				System.out.println("Jump--Dest: " +destT + " cur: "+getDir()+" dt: "+dT+ " CONDITION 1");

			}
			setDir(destT);
			dT = 0;
		}
		else if((angleDir == -1 && (getDir()+ dT)%(2*Math.PI) < destT))
		{
			if(Math.abs(destT-getDir())>0.0425)
			{
				System.out.println("Jump--Dest: " +destT + " cur: "+getDir()+" dt: "+dT+ " CONDITION 2 ");

			}
			setDir(destT);
			
			dT = 0;
		}*/
		if(angle(getDir()-destT)<Math.abs(dT))
		{
			setDir(destT);
			dT = 0;
		}
		turn(dT);
		float spd = speed*delta;
		move((float)Math.cos(getDir())*spd, (float)Math.sin(getDir())*spd);
	}
	public void drawTravel(Graphics g)
	{
		g.drawLine(getX(), getY(), destX, destY);
	}
	public float angle(float angle)
	{
		while(angle < 0)
		{
			angle += Math.PI*2;
		}
		angle %= Math.PI*2;
		return angle;
	}

}
