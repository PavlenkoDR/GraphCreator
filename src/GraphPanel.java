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
	public double MouseX, MouseY;
	public double MouseLastX = 0, MouseLastY = 0;
	public double MouseTransX, MouseTransY;
	
	public double PosXVec = 0, PosYVec = 0;
	public double xScaleVec = 1, yScaleVec = 1;

	public boolean editing;
	public boolean 	boolDrawGraphApprox, 
					boolDrawGraphPoly,
					boolDrawGraphFur,
					boolDrawGraphSpline;
	
	Function func;
	Paint paint;
	
	public void paint(Graphics g) {
	  	   super.paint(g);
		  		paint = new Paint((getSize().width > 50)? (getSize().width):50, (getSize().height > 50)? (getSize().height):50);
		  		paint.SetFunc(func);
		  		paint.xScale1 = (paint.width/(Paint.BorderMaxX - Paint.BorderMinX) - 1) * xScaleVec;
		  		paint.yScale1 = (paint.height/(Paint.BorderMaxY - Paint.BorderMinY) - 1) * yScaleVec;
		  		paint.PosX = getSize().width/2 + PosXVec;
		  		paint.PosY = getSize().height/2 + PosYVec;
	   			
		  		paint.DrawGrid(g);
		  		paint.DrawCoord(g);
	   			
	   			g.setColor(Color.RED);
	   			g.drawString("Input points ", 40 , 80);
	   			if (func.GetPointsFlag) paint.DrawPoints(g);

	 		  	g.setColor(Color.MAGENTA);
	   			g.drawString("Matrix Interpolation ", 40 , 50);
	 		  	if ((boolDrawGraphPoly)&&(func.GetPointsFlag)) paint.DrawGraphPoly(g);

	 		  	g.setColor(Color.BLUE);
	   			g.drawString("Approximation " + func.nApprox + " power", 40 , 65);
	 		  	if ((boolDrawGraphPoly)&&(func.GetPointsFlag)) paint.DrawGraphApprox(g);
	 		  	if ((boolDrawGraphFur)&&(CHMFure.GetPointsFlag))
	 		  	{
		 		  	g.setColor(Color.GREEN);
		   			g.drawString("Func", 40 , 95);
		 		  	paint.DrawGraph(g, CHMFure.funcX, CHMFure.funcY, CHMFure.sizeFunc);

		 		  	g.setColor(Color.ORANGE);
		   			g.drawString("Fur", 40 , 110);
		 		  	 paint.DrawGraph(g, CHMFure.furX, CHMFure.furY, CHMFure.sizeFur);

		 		  	g.setColor(Color.RED);
		   			g.drawString("Delta", 40 , 125);
		 		  	paint.DrawGraph(g, CHMFure.deltaX, CHMFure.deltaY, CHMFure.sizeDelta);
	 		  	}
	 		  	if ((boolDrawGraphSpline)&&(CHMSpline.GetPointsFlag))
	 		  	{
		 		  	g.setColor(Color.GREEN);
		   			g.drawString("Func", 40 , 95);
		 		  	paint.DrawGraph(g, CHMSpline.funcX, CHMSpline.funcY, CHMSpline.sizeFunc);

		 		  	g.setColor(Color.ORANGE);
		   			g.drawString("Spline", 40 , 110);
		 		  	paint.DrawGraph(g, CHMSpline.splineX, CHMSpline.splineY, CHMSpline.sizeSpline);

		 		  	g.setColor(Color.RED);
		   			g.drawString("Delta", 40 , 125);
		 		  	paint.DrawGraph(g, CHMSpline.deltaX, CHMSpline.deltaY, CHMSpline.sizeDelta);
	 		  	}
	 		  	
	 		  	g.setColor(Color.RED);
	 		  	g.drawString("x =  " + MouseTransX, 40 , paint.height - 50);
	 		  	g.drawString("y =  " + MouseTransY, 40 , paint.height - 30);
	     }
    
	public GraphPanel()
	{
		super();
		boolDrawGraphApprox = false;
		boolDrawGraphPoly = false;
    	func = new Function();
		editing = false;
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
		if ((boolDrawGraphPoly)&&(func.GetPointsFlag)&&(editing))
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
		else if ((boolDrawGraphPoly)&&(func.GetPointsFlag))
		{
			MouseX = e.getX();
			MouseY = e.getY();
			MouseTransX = (MouseX - paint.PosX)/paint.xScale1;
			MouseTransY = -(MouseY - paint.PosY)/paint.yScale1;
			MouseTransX = new BigDecimal(MouseTransX).setScale(2, RoundingMode.UP).doubleValue();
			MouseTransY = new BigDecimal(MouseTransY).setScale(2, RoundingMode.UP).doubleValue();
			func.Y[Paint.ActionPoint] = MouseTransY;
			//t_Poly.F[Paint.ActionPoint] = MouseTransY;
			if (func.GetPointsFlag == true)  
				func.Y[Paint.ActionPoint] = MouseTransY;
			func.Y[Paint.ActionPoint] = MouseTransY;
			func.interp.InitMatrix();
			func.approx.InitMatrix(func.nApprox);
			paint.func = func;
			repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {	//Перемещение мыши
		MouseX = e.getX();
		MouseY = e.getY();
		MouseTransX = (MouseX - paint.PosX)/paint.xScale1;
		MouseTransY = -(MouseY - paint.PosY)/paint.yScale1;
		MouseTransX = new BigDecimal(MouseTransX).setScale(2, RoundingMode.UP).doubleValue();
		MouseTransY = new BigDecimal(MouseTransY).setScale(2, RoundingMode.UP).doubleValue();
		MouseLastX = PosXVec;
		MouseLastY = PosYVec;
		repaint(65, paint.height - 60, 50, 30);
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		//System.out.print("!!!");
		//double LastDisplayCenterTransX, LastDisplayCenterTransY;
		//double DisplayCenterTransX, DisplayCenterTransY;
	       int notches = e.getWheelRotation();
	       int speed = 10;
			//LastDisplayCenterTransX = (paint.width/2  - paint.PosX)/(paint.xScale1);
			//LastDisplayCenterTransY = (paint.height/2  - paint.PosY)/(paint.yScale1);
	       if (notches < 0) {
	    	   xScaleVec+=speed/(Paint.BorderMaxX - Paint.BorderMinX);
	           yScaleVec+=speed/(Paint.BorderMaxY - Paint.BorderMinY);
	           //PosXVec += -getSize().width/(Paint.width/(Paint.BorderMaxX - Paint.BorderMinX));
	           //PosYVec += -getSize().height/(Paint.height/(Paint.BorderMaxY - Paint.BorderMinY));
	       } else {
	           	xScaleVec-=speed/(Paint.BorderMaxX - Paint.BorderMinX);
	           	yScaleVec-=speed/(Paint.BorderMaxY - Paint.BorderMinY);
	           	//Paint.xScale1 = (paint.width/(Paint.BorderMaxX - Paint.BorderMinX) - 1) * xScaleVec;
	  			//Paint.yScale1 = (paint.height/(Paint.BorderMaxY - Paint.BorderMinY) - 1) * yScaleVec;
	           	xScaleVec = ((paint.width/(Paint.BorderMaxX - Paint.BorderMinX) - 1) * xScaleVec > 0)?xScaleVec:(xScaleVec + speed/(Paint.BorderMaxX - Paint.BorderMinX));
	           	yScaleVec = ((paint.height/(Paint.BorderMaxY - Paint.BorderMinY) - 1) * yScaleVec > 0)?yScaleVec:(yScaleVec + speed/(Paint.BorderMaxY - Paint.BorderMinY));
		           //PosXVec -= -getSize().width/(Paint.width/(Paint.BorderMaxX - Paint.BorderMinX));
		           //PosYVec -= -getSize().height/(Paint.height/(Paint.BorderMaxY - Paint.BorderMinY));
	       }
			MouseX = e.getX();
			MouseY = e.getY();
			MouseTransX = (MouseX - paint.PosX)/(paint.xScale1 + xScaleVec);
			MouseTransY = -(MouseY - paint.PosY)/(paint.yScale1 + yScaleVec);
			MouseTransX = new BigDecimal(MouseTransX).setScale(2, RoundingMode.UP).doubleValue();
			MouseTransY = new BigDecimal(MouseTransY).setScale(2, RoundingMode.UP).doubleValue();
			//DisplayCenterTransX = (paint.width/2  - paint.PosX)/(paint.xScale1 * xScaleVec);
			//DisplayCenterTransY = (paint.height/2  - paint.PosY)/(paint.yScale1 * yScaleVec);
			if (notches < 0) {
				//PosXVec = (DisplayCenterTransX - LastDisplayCenterTransX)*(Paint.xScale1 + xScaleVec);
				//PosYVec += -(LastDisplayCenterTransY - DisplayCenterTransY)*(paint.yScale1 * yScaleVec);
			}
			else
			{
				//PosXVec = (DisplayCenterTransX - LastDisplayCenterTransX)*(Paint.xScale1 + xScaleVec);
				//PosYVec += (LastDisplayCenterTransY - DisplayCenterTransY)*(paint.yScale1 * yScaleVec);
			}
			repaint();
	    }
	

}