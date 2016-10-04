import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.JPanel;

//Window Builder

public class GraphPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener{

	private static final long serialVersionUID = 1L;
	private double MouseX, MouseY;
	private double MouseLastX = 0, MouseLastY = 0;
	private double MouseTransX, MouseTransY;
	
	private double PosXVec = 0, PosYVec = 0;
	private double ScaleVec = 1;

	public boolean editing;
	public boolean 	boolDrawGraphApprox, 
					boolDrawGraphPoly,
					boolDrawGraphSpline,
					boolDrawGraphExtraPoly;
	
	Function func;
	Paint paint;
	
	public void paint(Graphics g) {
		super.paint(g);
		//if ((paint.width != getSize().width)||(paint.height != getSize().height))
			paint = new Paint((getSize().width > 50)? (getSize().width):50, (getSize().height > 50)? (getSize().height):50);
		//System.out.println("!");
  		paint.SetFunc(func);
  		paint.Scale = (Math.min(paint.width, paint.height)/(Paint.BorderMax - Paint.BorderMin) - 1) * ScaleVec;
  		paint.PosX = getSize().width/2 + PosXVec;
  		paint.PosY = getSize().height/2 + PosYVec;
			
  		paint.DrawGrid(g);
  		paint.DrawCoord(g);
  		paint.SetDrawGraphExtraPoly(boolDrawGraphExtraPoly);
  		if (func.GetPointsFlag)
  		{
  			g.setColor(Color.RED);
  			g.drawString("Input points ", 40 , 95);
  			paint.DrawPoints(g);
		}

  		if (boolDrawGraphPoly)
  		{
  	  	  	g.setColor(Color.MAGENTA);
  	  		g.drawString("Matrix Interpolation ", 40 , 50);
  	  		if (func.GetPointsFlag)paint.DrawGraphPoly(g);
  		}
  		if (boolDrawGraphApprox)
  		{
  	  	  	g.setColor(Color.BLUE);
  	  		g.drawString("Approximation " + func.nApprox + " power", 40 , 65);
  	  		if (func.GetPointsFlag) paint.DrawGraphApprox(g);
  		}
  		if (boolDrawGraphSpline)
  		{
  	  	  	g.setColor(Color.BLACK);
  	  		g.drawString("Spline Interpolation ", 40 , 80);
  	  	  	if (func.GetPointsFlag)paint.DrawGraphSpline(g);
  		}
	  	g.setColor(Color.RED);
	  	g.drawString("x =  " + MouseTransX, 40 , paint.height - 50);
	  	g.drawString("y =  " + MouseTransY, 40 , paint.height - 30);
	  	g.drawString("1:"+paint.grid_power, getSize().width - 70 , paint.height - 30);
	}
    
	public GraphPanel()
	{
		super();
    	func = new Function();
		boolDrawGraphApprox = false;
		boolDrawGraphPoly = false;
		boolDrawGraphSpline = false;
		editing = false;
		paint = new Paint((getSize().width > 50)? (getSize().width):50, (getSize().height > 50)? (getSize().height):50);
    	addMouseListener(this);
    	addMouseMotionListener(this);
    	addMouseWheelListener(this);
    	KeyAdapter keyCase = new KeyAdapter() {
        	public void keyReleased(KeyEvent e) {
        		String Key = KeyEvent.getKeyText(e.getKeyCode());
                switch(Key)
                {
                case "2":
                	func.nApprox = (func.nApprox < func.size)?func.nApprox + 1:func.size;
                	break;
            	case "1":
            		func.nApprox = (func.nApprox > 2)?func.nApprox - 1:2;
            		break;
                }
                func.approx.InitMatrix(func.nApprox);
            	repaint();
            	};
    		};
    	addKeyListener(keyCase);
	    setVisible(true);
	}
    
	@Override
	public void mouseClicked(MouseEvent e) {	//Щелчок
		MouseLastX = PosXVec;
		MouseLastY = PosXVec;
	}

	@Override
	public void mouseEntered(MouseEvent e) {	//Мышь попадает в окно
		
	}

	@Override
	public void mouseExited(MouseEvent e) {		//Мышь выходит из окна
		
	}

	@Override
	public void mousePressed(MouseEvent e) {	//Нажатие
		Paint.ActionPoint = -1;
		MouseLastX = PosXVec;
		MouseLastY = PosYVec;
		if ((func.GetPointsFlag)&&(editing))
		for (int i = 0; i < func.size; i++)
		{
			if ((func.X[i] - 0.1 > MouseTransX - paint.ActionRadius)&&
	    		   (func.X[i] - 0.1 < MouseTransX + paint.ActionRadius))
			if ((func.Y[i] > MouseTransY - paint.ActionRadius)&&
			   (func.Y[i] < MouseTransY + paint.ActionRadius))
			{
				Paint.ActionPoint = i;
				return;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {	//Отжатие
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {	//Перетаскивание
		if (Paint.ActionPoint == -1)
		{
			PosXVec = e.getX() - MouseX + MouseLastX;
			PosYVec = e.getY() - MouseY + MouseLastY;
			repaint();
		}
		else if (func.GetPointsFlag)
		{
			MouseX = e.getX();
			MouseY = e.getY();
			MouseTransX = (MouseX - paint.PosX)/paint.Scale;
			MouseTransY = -(MouseY - paint.PosY)/paint.Scale;
			MouseTransX = new BigDecimal(MouseTransX).setScale(2, RoundingMode.UP).doubleValue();
			MouseTransY = new BigDecimal(MouseTransY).setScale(2, RoundingMode.UP).doubleValue();
			func.Y[Paint.ActionPoint] = MouseTransY;
			//t_Poly.F[Paint.ActionPoint] = MouseTransY;
			if (func.GetPointsFlag == true)  
				func.Y[Paint.ActionPoint] = MouseTransY;
			func.Y[Paint.ActionPoint] = MouseTransY;
			if (boolDrawGraphPoly) func.interp.InitMatrix();
			if (boolDrawGraphApprox) func.approx.InitMatrix(func.nApprox);
			if (boolDrawGraphSpline) func.spline.build_spline();
			//paint.func = func;
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {	//Перемещение мыши
		MouseX = e.getX();
		MouseY = e.getY();
		MouseTransX = (MouseX - paint.PosX)/paint.Scale;
		MouseTransY = -(MouseY - paint.PosY)/paint.Scale;
		MouseTransX = new BigDecimal(MouseTransX).setScale(2, RoundingMode.UP).doubleValue();
		MouseTransY = new BigDecimal(MouseTransY).setScale(2, RoundingMode.UP).doubleValue();
		MouseLastX = PosXVec;
		MouseLastY = PosYVec;
		repaint(65, paint.height - 60, 50, 30);
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int notches = e.getWheelRotation();
		double speed = 1.1;
		if (notches < 0) {
			ScaleVec=(ScaleVec<100)?ScaleVec*speed:ScaleVec;
		} else {
			//System.out.println(ScaleVec);
			ScaleVec=(ScaleVec>0.1)?ScaleVec/speed:ScaleVec;
		}
		PosXVec = (PosXVec/paint.Scale)*(paint.height/(Paint.BorderMax - Paint.BorderMin) - 1) * ScaleVec;
		PosYVec = (PosYVec/paint.Scale)*(paint.height/(Paint.BorderMax - Paint.BorderMin) - 1) * ScaleVec;
		repaint();
	}
	

}
