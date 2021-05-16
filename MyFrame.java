import javax.swing.JFrame;
import java.lang.Thread;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import animator.Animator;
public class MyFrame extends JFrame implements KeyListener , ComponentListener{
	public float opacityPercentage;
	final Animator animator;
	public BufferedImage image;
	public MyFrame(){
		setUndecorated(true);
		setSize(500,500);
		image=getBufferedImage();
		animator=new Animator(this);
		opacityPercentage=0.5f;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("MyFrame");
		setOpacity(opacityPercentage);
		addKeyListener(this);
		addComponentListener(this);
		setResizable(true);
		setVisible(true);
	}
	private BufferedImage getBufferedImage(){
		return new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_ARGB);
	}
	private Graphics2D _getGraphics(){
		return (Graphics2D)image.getGraphics();
	}
	public void keyPressed(KeyEvent event){}
	public void keyTyped(KeyEvent event){}
	public void keyReleased(KeyEvent event){
		if(KeyEvent.VK_F11==event.getKeyCode()){
			System.out.println("F11");
			if(getExtendedState()==JFrame.MAXIMIZED_BOTH)
				setExtendedState(JFrame.NORMAL);
			else
			setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
	}
	public void componentHidden(ComponentEvent event){}
	public void componentMoved(ComponentEvent event){}
	public void componentResized(ComponentEvent event){
		image=getBufferedImage();
		animator.resize();
	}
	public void componentShown(ComponentEvent event){}
	
	@Override
	public void paint(Graphics g){
		clearRect(_getGraphics());
		//fillOval(getWidth()/2,getHeight()/2,Math.min(getWidth(),getHeight())/2,g);	
		animator.paint(_getGraphics());
		g.drawImage(image,0,0,getWidth(),getHeight(),null);
	}
	private void clearRect(final Graphics g){
		final Color col=g.getColor();
		g.setColor(new Color(255,255,255));
		g.fillRect(0,0,getWidth(),getHeight());
		g.setColor(col);
	}
	public void fillOval(double _x,double _y,double _radius,Graphics g){
		int x=(int)_x;
		int y=(int)_y;
		int radius=(int)_radius;
		g.fillOval(x-radius,y-radius,radius*2,radius*2);
	}
	
	public static void main(String $[]){
		//JFrame.setDefaultLookAndFeelDecorated(true);
		MyFrame frame=new MyFrame();
		Runnable runnable=()->{
				System.out.println("Thread is started");
				boolean flag=true;
				float step=0.001f;
				while(true){
					frame.opacityPercentage+=(flag?1:-1)*step;
					if(frame.opacityPercentage>=1){
						flag=false;
						continue;
					}
					if(frame.opacityPercentage<=0){
						flag=true;
						continue;
					}
					try{
						Thread.sleep(30);
					}catch(InterruptedException ex){
						ex.printStackTrace();
					}
					frame.repaint();	
					frame.setOpacity(frame.opacityPercentage);
				}
		};
		Thread thread=new Thread(runnable);
		thread.start();
	}
}
