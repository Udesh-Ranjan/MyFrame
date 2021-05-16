package animator;

import java.awt.Color;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.awt.Graphics;
import circle.Circle;

public class Animator{
	final static int MAX_RADIUS=200;
	final Random random;
	final List<Circle>list;
	final JFrame frame;
	public Animator(final JFrame frame){
		random=new Random();
		this.frame=frame;
		final int width=frame.getWidth();
		final int height=frame.getHeight();
		list=new ArrayList<>();
		for(int i=0;i<100;i++){
			list.add(getRandomCircle());
		}
	}
	public int nextInt(){
		return Math.abs(random.nextInt());
	}
	public Circle getRandomCircle(){
		final int width=frame.getWidth();
		final int height=frame.getHeight();
		Circle circle=null;
		int x=nextInt()%width;
		int y=nextInt()%height;
		int r=nextInt()%255;
		int g=nextInt()%255;
		int b=nextInt()%255;
		int a=nextInt()%255;
		Color col=new Color(r,g,b);
		int radius=nextInt()%(MAX_RADIUS);
		circle=new Circle(x,y,radius,col);
		return circle;
	}
	public void paint(final Graphics g){
		int count=0;
		for(int i=0;i<list.size();i++){
			Circle circle=list.get(i);
			circle.radius+=1;
			if(circle.radius>=MAX_RADIUS){
				list.remove(i);
				count++;
				continue;
			}
			Color color=g.getColor();
			g.setColor(circle.color);
			fillOval(circle.x,circle.y,circle.radius,g);
			g.setColor(color);
		}
		for(int i=0;i<count;i++)
			list.add(0,getRandomCircle());

	}
	public void resize(){
		for(int i=0;i<list.size();i++){
			final int width=frame.getWidth();
			final int height=frame.getHeight();
			Circle circle=list.get(i);
			circle.x=nextInt()%frame.getWidth();
			circle.y=nextInt()%frame.getHeight();
		}
	}
	public void fillOval(double _x,double _y,double _radius,Graphics g){
		int x=(int)_x;
		int y=(int)_y;
		int radius=(int)_radius;
		g.fillOval(x-radius,y-radius,radius*2,radius*2);
	}
}
