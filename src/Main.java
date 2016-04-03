import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

//Window Builder

public class Main extends JFrame implements MouseListener, MouseMotionListener{
	
	private static final long serialVersionUID = 1L;
	
	File f = new File("input.txt");
	final Interp t_Interp = new Interp(f);
	final Approx t_Approx = new Approx(f);

	double MouseX, MouseY;
	double MouseTransX, MouseTransY;
	
	JPanel Panel1;
	
	public Main()
	{
		super("Окно с изображением");
    	setSize(1000, 1000);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setVisible(true);
    	addMouseListener(this);
    	addMouseMotionListener(this);
    	addKeyListener(new KeyAdapter() {
 
            public void keyReleased(KeyEvent e) {
                String Key = KeyEvent.getKeyText(e.getKeyCode());
                switch(Key)
                {
                case "2":
                	Paint.nApprox = (Paint.nApprox < Paint.size)?Paint.nApprox + 1:Paint.size;
                	break;
            	case "1":
            		Paint.nApprox = (Paint.nApprox > 2)?Paint.nApprox - 1:2;
            		break;
                }
            	t_Approx.InitMatrix(Paint.nApprox);
            	repaint();
            }
             
        });
    	Panel1 = new JPanel()
    	{
    		public void paint(Graphics g) {
    		  	Paint.DrawGrid(g);
    		  	Paint.DrawCoord(g);
    		  	g.setColor(Color.MAGENTA);
    		  	g.drawString("Interpolation ", 40 , 50);
    		  	g.setColor(Color.BLUE);
    		  	g.drawString("Approximation " + Paint.nApprox + " power", 40 , 65);
    		  	g.setColor(Color.RED);
    		  	g.drawString("Input points ", 40 , 80);
    		}
    	};
    	add(Panel1);
   	    	Paint.DefaultSetting();
 			Paint.PosX = getSize().width/2;
 			Paint.PosY = getSize().height/2;
 			Paint.width = (getSize().width > 50)? (getSize().width):50;
 			Paint.height = (getSize().height > 50)? (getSize().height):50;
 			Paint.xScale1 = Paint.width/(Paint.BorderMaxX - Paint.BorderMinX) - 1;
 			Paint.yScale1 = Paint.height/(Paint.BorderMaxY - Paint.BorderMinY) - 1;
	  		Paint.ceil = 20;
	  		Paint.nApprox = 4;
	  		Paint.PointsRadius = 3;
	        setVisible(true);
	  		
		
	}
    public void paint(Graphics g) {
 	   super.paint(g);
 			Paint.PosX = getSize().width/2;
 			Paint.PosY = getSize().height/2;
 			Paint.width = (getSize().width > 50)? (getSize().width):50;
 			Paint.height = (getSize().height > 50)? (getSize().height):50;
 			Paint.xScale1 = Paint.width/(Paint.BorderMaxX - Paint.BorderMinX) - 1;
 			Paint.yScale1 = Paint.height/(Paint.BorderMaxY - Paint.BorderMinY) - 1;
		  	Paint t_Paint_Interp = new Paint(t_Interp);
		  	Paint t_Paint_Approx = new Paint(t_Approx);
		  	Paint.DrawPoints(g);
		  	g.setColor(Color.MAGENTA);
		  	t_Paint_Interp.DrawGraph(g);
		  	//g.drawString("Interpolation ", 40 , 50);
		  	g.setColor(Color.BLUE);
		  	t_Paint_Approx.DrawGraph(g);
		  	//g.drawString("Approximation " + Paint.nApprox + " power", 40 , 65);
		  	g.setColor(Color.RED);
		  	//g.drawString("Input points ", 40 , 80);
		  	g.drawString("x =  " + MouseTransX, 40 , Paint.height - 50);
		  	g.drawString("y =  " + MouseTransY, 40 , Paint.height - 30);
		  	//*/
		  	//g.drawLine(0,0 ,getSize().width , getSize().height);// рисуем линию через весь размер экрана
		  	//g.setColor(new Color (128,128,128));
		  	//g.drawOval(getSize().width/2 , getSize().height/2, 100, 50);
		  	//g.setColor(new Color (255, 0, 0));
		  	//g.drawRoundRect(60 , 60 , 210 , 210 , 50 , 10);
		  	//g.drawString("Hello From Java 2D", 50 , 50);
		  	//g.fillRect(20 , 20 , 60 , 60);
    }
    public static void main(String[] args){
    	new Main();
    }

	@Override
	public void mouseClicked(MouseEvent e) {	//Щелчок
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {	//Мышь попадает в окно
		
	}

	@Override
	public void mouseExited(MouseEvent e) {		//Мышь выходит из окна
		
	}

	@Override
	public void mousePressed(MouseEvent e) {	//Нажатие
		for (int i = 0; i < Paint.size; i++)
		{
			if ((Paint.PointsX[i] > MouseTransX - Paint.ActionRadius)&&
				(Paint.PointsX[i] < MouseTransX + Paint.ActionRadius))
				if ((Paint.PointsY[i] > MouseTransY - Paint.ActionRadius)&&
					(Paint.PointsY[i] < MouseTransY + Paint.ActionRadius))
				{
					Paint.ActionPoint = i;
					return;
				}
			Paint.ActionPoint = -1;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {	//Отжатие
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {	//Перетаскивание
		if (Paint.ActionPoint == -1)
			return;
		MouseX = e.getX();
		MouseY = e.getY();
		MouseTransX = (MouseX - Paint.PosX)/Paint.xScale1;
		MouseTransY = -(MouseY - Paint.PosY)/Paint.yScale1;
		MouseTransX = new BigDecimal(MouseTransX).setScale(2, RoundingMode.UP).doubleValue();
		MouseTransY = new BigDecimal(MouseTransY).setScale(2, RoundingMode.UP).doubleValue();
		t_Interp.F[Paint.ActionPoint] = MouseTransY;
		t_Approx.F[Paint.ActionPoint] = MouseTransY;
		Paint.PointsY[Paint.ActionPoint] = MouseTransY;
		t_Interp.InitMatrix();
		t_Approx.InitMatrix(Paint.nApprox);
		
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {		//Перемещение мыши
		// TODO Auto-generated method stub
		MouseX = e.getX();
		MouseY = e.getY();
		MouseTransX = (MouseX - Paint.PosX)/Paint.xScale1;
		MouseTransY = -(MouseY - Paint.PosY)/Paint.yScale1;
		MouseTransX = new BigDecimal(MouseTransX).setScale(2, RoundingMode.UP).doubleValue();
		MouseTransY = new BigDecimal(MouseTransY).setScale(2, RoundingMode.UP).doubleValue();
		repaint(65, Paint.height - 60, 50, 30);
		
	}
}