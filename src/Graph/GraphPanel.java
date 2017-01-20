package Graph;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JList;
import javax.swing.JPanel;

import Another.Pair;
import ChmFunctions.Function;
import MathPars.MatchParser;

//Window Builder

public class GraphPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener{

	static Pair<Function, Color> make_pair(Function f, Color c){return new Pair<Function, Color>(f, c);}
	
	private static final long serialVersionUID = 1L;
	private double MouseX, MouseY;
	private double MouseXNow, MouseYNow;
	private double MouseXLast, MouseYLast;
	private double LastVecX = 0, LastVecY = 0;
	private double MouseTransX, MouseTransY;
	
	public double PosXVec = 0, PosYVec = 0;
	public double ScaleVec = 1;

	public boolean editing;
	public boolean 	boolDrawGraphApprox, 
					boolDrawGraphPoly,
					boolDrawGraphSpline,
					boolDrawGraphExtraPoly,
					boolDrawGraphLine = false,
					boolDrawSelectedArea = false;
	int activeButton;
	
	JList<Object> DotsList;
	
	Function func = null;
	Function nowfunc;
	public Paint paint;
	List<Integer> ActionPoint;
	List<Pair<Function, Color> > FunctionList = new ArrayList<Pair<Function, Color> >();
	int selectedID;
	private MatchParser p = new MatchParser();
	boolean allocation;
	boolean screenRun;
	int[] selectedList;
	
	int ActiveKey;
	
	BufferedImage imgResult;
	Graphics gNow;
	
	public void pushFunction1(Function f, Color c)
	{
		FunctionList.add(make_pair(f, c));
	}
	
	public BufferedImage getImage()
	{
	    screenRun = true;
	    imgResult = new BufferedImage((getWidth() <= 0)?100:getWidth(), (getHeight() <= 0)?100:getHeight(), BufferedImage.TYPE_INT_RGB);
	    gNow = imgResult.getGraphics();
	    paint(gNow);
		return imgResult;
	}
	public void paint(Graphics g) {
		super.paint(g);
		//paint = new Paint((getSize().width > 50)? (getSize().width):50, (getSize().height > 50)? (getSize().height):50);
  		paint.PosX = getSize().width/2 + PosXVec;
  		paint.PosY = getSize().height/2 + PosYVec;
  		paint.Scale = ((Math.min(paint.width, paint.height))/Math.abs(paint.BorderMax - paint.BorderMin)) * ScaleVec * 95/100;
  		paint.DrawGrid(g);
  		paint.DrawCoord(g);
  		paint.SetDrawGraphExtraPoly(boolDrawGraphExtraPoly);
		if (boolDrawSelectedArea)
		{
			g.setColor(Color.RED);
			g.drawRect(paint.CoordToPixelX(func.X[0]) - 5, 
					   paint.CoordToPixelY(func.maxY) - 5, 
					   paint.CoordToPixelX(func.X[func.size - 1]) - paint.CoordToPixelX(func.X[0]) + 10, 
					   -paint.CoordToPixelY(func.maxY) + paint.CoordToPixelY(func.minY) + 10);
		}

		g.setColor(new Color(200, 200, 200));
		if (!boolDrawGraphLine)g.drawString("Lines ", 40 , 35);
	  	if (!boolDrawGraphPoly) g.drawString("Matrix Interpolation ", 40 , 50);
	  	if (!boolDrawGraphApprox)g.drawString("Approximation " + "_" + " power", 40 , 65);
	  	if (!boolDrawGraphSpline)g.drawString("Spline Interpolation ", 40 , 80);
	  	if (selectedID == -1) return;
  		for (int i = 0; i < FunctionList.size(); i++)
  		{
  			nowfunc = FunctionList.get(i).getL();
  			if (!nowfunc.Enable) continue;
  	  		paint.SetFunc(nowfunc);
  	  		if (nowfunc.GetPointsFlag)
  	  		{
  	  			g.setColor(Color.RED);
  	  			g.drawString("Input points ", 40 , 95);
  	  			//paint.DrawPoints(g);
  			}

  	  		if (boolDrawGraphPoly)
  	  		{
  	  			if (FunctionList.get(i).getR() == null)
  	  	  			g.setColor(Color.MAGENTA);
  	  			else 
  	  				g.setColor(FunctionList.get(i).getR());
  	  	  		if (nowfunc.GetPointsFlag)paint.DrawGraphPoly(g);
  	  			if (FunctionList.get(selectedID).getR() == null)
  	  	  			g.setColor(Color.MAGENTA);
  	  			else 
  	  				g.setColor(FunctionList.get(selectedID).getR());
  	  			g.drawString("Matrix Interpolation ", 40 , 50);
  	  		}
  	  		if (boolDrawGraphApprox)
  	  		{
  	  			if (FunctionList.get(i).getR() == null)
  	  	  	  	  	g.setColor(Color.BLUE);
  	  			else 
  	  				g.setColor(FunctionList.get(i).getR());
  	  	  		if (nowfunc.GetPointsFlag) paint.DrawGraphApprox(g);
  	  			if (FunctionList.get(selectedID).getR() == null)
  	  	  			g.setColor(Color.BLUE);
  	  			else 
  	  				g.setColor(FunctionList.get(selectedID).getR());
  	  	  		g.drawString("Approximation " + nowfunc.nApprox + " power", 40 , 65);
  	  		}
  	  		if (boolDrawGraphSpline)
  	  		{
  	  			if (FunctionList.get(i).getR() == null)
  	  	  	  	  	g.setColor(Color.ORANGE);
  	  			else 
  	  				g.setColor(FunctionList.get(i).getR());
  	  	  	  	if (nowfunc.GetPointsFlag)paint.DrawGraphSpline(g);
  	  			if (FunctionList.get(selectedID).getR() == null)
  	  	  			g.setColor(Color.ORANGE);
  	  			else 
  	  				g.setColor(FunctionList.get(selectedID).getR());
  	  	  		g.drawString("Spline Interpolation ", 40 , 80);
  	  		}
  	  		if (boolDrawGraphLine)
  	  		{
  	  			if (FunctionList.get(i).getR() == null)
  	  	  	  	  	g.setColor(Color.BLACK);
  	  			else 
  	  				g.setColor(FunctionList.get(i).getR());
  	  	  	  	if (nowfunc.GetPointsFlag)paint.DrawGraphLine(g);
  	  			if (FunctionList.get(selectedID).getR() == null)
  	  	  			g.setColor(Color.BLACK);
  	  			else 
  	  				g.setColor(FunctionList.get(selectedID).getR());
  	  	  		g.drawString("Lines ", 40 , 35);
  	  		}
  		}
	  	g.setColor(Color.RED);
	  	if (func != null)
	  	if (func.GetPointsFlag)
	  	{
	        selectedList = DotsList.getSelectedIndices();
	        for (int i = 0; i < selectedList.length; i++)
	        	g.drawOval(paint.CoordToPixelX(func.X[selectedList[i]]) - 2, paint.CoordToPixelY(func.Y[selectedList[i]]) - 2, 4, 4);
	  	}
		if (allocation) g.drawRect(
				(int)((MouseXLast < MouseXNow)?MouseXLast:MouseXNow), 
				(int)((MouseYLast < MouseYNow)?MouseYLast:MouseYNow), 
				Math.abs((int)MouseXNow - (int)MouseXLast), 
				Math.abs((int)MouseYNow - (int)MouseYLast));
		if (!screenRun)
		{
		  	g.drawString("x =  " + MouseTransX, 40 , paint.height - 50);
		  	g.drawString("y =  " + MouseTransY, 40 , paint.height - 30);
		}
	  	g.drawString("1:"+paint.grid_power, getSize().width - 70 , paint.height - 30);
	}
    
	public GraphPanel()
	{
		super();
		ActionPoint = new ArrayList<Integer>();
    	//func = new Function();
		boolDrawGraphApprox = false;
		boolDrawGraphPoly = false;
		boolDrawGraphSpline = false;
		editing = false;
		paint = new Paint((getSize().width > 50)? (getSize().width):50, (getSize().height > 50)? (getSize().height):50);
    	addMouseListener(this);
    	addMouseMotionListener(this);
    	addMouseWheelListener(this);
	    setVisible(true);
	    imgResult = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
	    gNow = imgResult.getGraphics();
	    paint(gNow);
	}
    
	@Override
	public void mouseClicked(MouseEvent e) {	//Щелчок
		LastVecX = PosXVec;
		LastVecY = PosXVec;
		activeButton = e.getButton();
		if (ActiveKey != 17) 
		{
			ActionPoint.clear();
			DotsList.clearSelection();
		}
		if (func.GetPointsFlag)
		{
			paint.setActionRadius((Paint.getPointsRadius() + 1)/paint.Scale);
			for (int i = 0; i < func.size; i++)
			{
				if ((func.X[i] + 0.1 > MouseTransX - paint.getActionRadius())&&
		    		   (func.X[i] + 0.1 < MouseTransX + paint.getActionRadius()))
				if ((func.Y[i] > MouseTransY - paint.getActionRadius())&&
				   (func.Y[i] < MouseTransY + paint.getActionRadius()))
				{
					ActionPoint.add(i);
				}
			}
			if (ActionPoint.size() != 0) 
			{
				int tmp_int[] = new int[ActionPoint.size()];
				for (int i = 0; i < ActionPoint.size(); i++)
					tmp_int[i] = ActionPoint.get(i);
				DotsList.setSelectedIndices(tmp_int);
			}
			
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {	//Мышь попадает в окно
		
	}

	@Override
	public void mouseExited(MouseEvent e) {		//Мышь выходит из окна
		
	}

	@Override
	public void mousePressed(MouseEvent e) {	//Нажатие
		LastVecX = PosXVec;
		LastVecY = PosYVec;
		MouseXNow = e.getX();
		MouseYNow = e.getY();
		MouseXLast = e.getX();
		MouseYLast = e.getY();
		allocation = false;
		activeButton = e.getButton();
		if (ActiveKey != 17) 
		{
			ActionPoint.clear();
			DotsList.clearSelection();
		}
		if (func.GetPointsFlag)
		{
			paint.setActionRadius((Paint.getPointsRadius() + 1)/paint.Scale);
			for (int i = 0; i < func.size; i++)
			{
				if ((func.X[i] + 0.1 > MouseTransX - paint.getActionRadius())&&
		    		   (func.X[i] + 0.1 < MouseTransX + paint.getActionRadius()))
				if ((func.Y[i] > MouseTransY - paint.getActionRadius())&&
				   (func.Y[i] < MouseTransY + paint.getActionRadius()))
				{
					ActionPoint.add(i);
				}
			}
			if (ActionPoint.size() != 0) 
			{
				int tmp_int[] = new int[ActionPoint.size()];
				for (int i = 0; i < ActionPoint.size(); i++)
					tmp_int[i] = ActionPoint.get(i);
				DotsList.setSelectedIndices(tmp_int);
			}
			
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {	//Отжатие
		MouseXNow = e.getX();
		MouseYNow = e.getY();
		if (allocation)
		{
			if (func.GetPointsFlag)
			{
				double coordlXLast = paint.PixelToCoordX(MouseXLast);
				double coordlYLast = paint.PixelToCoordY(MouseYLast);
				double coordlXNow = paint.PixelToCoordX(MouseXNow);
				double coordlYNow = paint.PixelToCoordY(MouseYNow);
				double minX = Math.min(coordlXLast, coordlXNow);
				double maxX = Math.max(coordlXLast, coordlXNow);
				double minY = Math.min(coordlYLast, coordlYNow);
				double maxY = Math.max(coordlYLast, coordlYNow);
				//paint.setActionRadius((Paint.getPointsRadius() + 1)/paint.Scale);
				for (int i = 0; i < func.size; i++)
				{
					if ((func.X[i] + 0.1 >= minX)&&(func.X[i] + 0.1 <= maxX)&&
							(func.Y[i] >= minY)&&(func.Y[i] <= maxY))
					{
						ActionPoint.add(i);
					}
				}
			}
			if (ActionPoint.size() != 0) 
			{
				int tmp_int[] = new int[ActionPoint.size()];
				for (int i = 0; i < ActionPoint.size(); i++)
					tmp_int[i] = ActionPoint.get(i);
				DotsList.setSelectedIndices(tmp_int);
			}
		}
		allocation = false;
		activeButton = -1;
		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {	//Перетаскивание
		double LastMouseTrans = MouseTransY;
		int[] tmp_o = DotsList.getSelectedIndices();
		switch (activeButton) {
		case 1:
			MouseX = e.getX();
			MouseY = e.getY();
			if ((func.GetPointsFlag)&&(tmp_o.length > 0)&&(ActiveKey == 17)&&(editing))
			{
				// Drag
				MouseTransX = (MouseX - paint.PosX)/paint.Scale;
				MouseTransY = -(MouseY - paint.PosY)/paint.Scale;
				MouseTransX = new BigDecimal(MouseTransX).setScale(2, RoundingMode.UP).doubleValue();
				MouseTransY = new BigDecimal(MouseTransY).setScale(2, RoundingMode.UP).doubleValue();
				for (int i = 0; i < tmp_o.length; i++)
				{
					func.Y[tmp_o[i]] += MouseTransY - LastMouseTrans;
				}
				if (boolDrawGraphPoly) func.interp.InitMatrix();
				if (boolDrawGraphApprox) func.approx.InitMatrix(func.nApprox);
				if (boolDrawGraphSpline) func.spline.build_spline();
				//paint.func = func;
				repaint();
			}
			else
			{
				allocation = true;
				MouseXNow = e.getX();
				MouseYNow = e.getY();
				repaint();
			}
			break;
		case 3:
			// Move
			PosXVec = e.getX() - MouseX + LastVecX;
			PosYVec = e.getY() - MouseY + LastVecY;
			repaint();
			break;
		default:
			break;
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
		LastVecX = PosXVec;
		LastVecY = PosYVec;
		repaint(65, paint.height - 60, 50, 30);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		int notches = e.getWheelRotation();
		double speed = 1.1;
		if (notches < 0) {
			ScaleVec=(ScaleVec<300)?ScaleVec*speed:ScaleVec;
		} else {
			ScaleVec=(ScaleVec>0.001)?ScaleVec/speed:ScaleVec;
		}
		PosXVec = (PosXVec/paint.Scale)*(paint.height/(paint.BorderMax - paint.BorderMin)) * ScaleVec * 95/100;
		PosYVec = (PosYVec/paint.Scale)*(paint.height/(paint.BorderMax - paint.BorderMin)) * ScaleVec * 95/100;
		repaint();
	}
	

}
