package Graph;
import java.awt.Color;
import java.awt.Graphics;

import ChmFunctions.Function;


public class Paint {
	public double BorderMin = -20;
	public double BorderMax = 20;
	public double BorderDotsMin = -20;
	public double BorderDotsMax = 20;
    private double ceil = 20;
    private static double quality = 5;
	private double ActionRadius = 0.5;
	
	private double xGraphPoly[];
	private double yGraphPoly[];
	private double xGraphApprox[];
	private double yGraphApprox[];
	private double xGraphSpline[];
	private double yGraphSpline[];
	double Scale = 16;
	double PosX = 0;
	double PosY = 0;
	double grid_power;
    
	int width;
	int height;
	
	private static int PointsRadius = 3;

	public boolean boolDrawGraphExtraPoly;
	
	public double getCeil(){return ceil;}
	public void SetDrawGraphExtraPoly(boolean f){boolDrawGraphExtraPoly = f;}

	public static void setQuality(double x)
	{
		quality = (x<0.1)?0.1:((x>10)?10:x);
	}
	
	public static double getQuality()
	{
		return quality;
	}
	
	public int CoordToPixelX(double coord)
	{
		return (int)Math.round(coord*Scale + PosX);
	}
	public int CoordToPixelY(double coord)
	{
		return (int)Math.round(-coord*Scale + PosY);
	}
	public double PixelToCoordX(double pixel)
	{
		return (pixel - PosX)/Scale;
	}
	public double PixelToCoordY(double pixel)
	{
		return (PosY - pixel)/Scale;
	}
	
	Function func;
    
	Paint(int _width, int _height)
	{
  		width = _width;
  		height = _height;
	}
	public void SetFunc(Function _func)
	{
		//if (func == _func) return;
		func = _func;
		if (func.GetPointsFlag)
		{
			BorderDotsMin = PixelToCoordX(0);
			BorderDotsMax = PixelToCoordX(width);
			ceil = quality*width/((BorderDotsMax - BorderDotsMin)*10);
			xGraphPoly = new double[(int)Math.round((BorderDotsMax - BorderDotsMin)*ceil) + 1];
			yGraphPoly = new double[(int)Math.round((BorderDotsMax - BorderDotsMin)*ceil) + 1];
			xGraphApprox = new double[(int)Math.round((BorderDotsMax - BorderDotsMin)*ceil) + 1];
			yGraphApprox = new double[(int)Math.round((BorderDotsMax - BorderDotsMin)*ceil) + 1];
			xGraphSpline = new double[(int)Math.round((BorderDotsMax - BorderDotsMin)*ceil) + 1];
			yGraphSpline = new double[(int)Math.round((BorderDotsMax - BorderDotsMin)*ceil) + 1];
			double shift = 0;
    		for (double i = BorderDotsMin; i <= BorderDotsMax; i+= 1/(double)ceil)
    		{
    			xGraphPoly[(int)Math.round((i - BorderDotsMin)*ceil)] = i + shift;
    			yGraphPoly[(int)Math.round((i - BorderDotsMin)*ceil)] = -(func.interp.getValue(i + shift));
    			xGraphApprox[(int)Math.round((i - BorderDotsMin)*ceil)] = i + shift;
    			yGraphApprox[(int)Math.round((i - BorderDotsMin)*ceil)] = -(func.approx.getValue(i + shift, func.nApprox));
    			xGraphSpline[(int)Math.round((i - BorderDotsMin)*ceil)] = i + shift;
    			yGraphSpline[(int)Math.round((i - BorderDotsMin)*ceil)] = -(func.spline.getValue(i + shift));
    		}
		}
	}

	void DrawGraphPoly(Graphics g)
	{
		_DrawGraph(g, xGraphPoly, yGraphPoly);
	}
	
	void DrawGraphApprox(Graphics g)
	{
		_DrawGraph(g, xGraphApprox, yGraphApprox);
	}
	
	void DrawGraphSpline(Graphics g)
	{
		_DrawGraph(g, xGraphSpline, yGraphSpline);
	}


	private void _DrawGraph(Graphics g, double[] _x, double[] _y)
	{
		double from, to;
		if (boolDrawGraphExtraPoly)
		{
			from = BorderDotsMin;
			to = BorderDotsMax;
		}
		else
		{
			from = (func.X[0]>BorderDotsMin)?func.X[0]:BorderDotsMin;
			to = (func.X[func.size - 1]<BorderDotsMax)?func.X[func.size - 1]:BorderDotsMax;
		}
	    for (double i = from; i <= to - 1/(double)ceil; i+= 1/(double)ceil)
        {
	    	double x1 = _x[(int)Math.round((i - BorderDotsMin)*ceil)]*Scale + PosX;
        	double y1 = _y[(int)Math.round((i - BorderDotsMin)*ceil)]*Scale + PosY;
        	double x2 = _x[(int)Math.round((i - BorderDotsMin)*ceil)+1]*Scale + PosX;
        	double y2 = _y[(int)Math.round((i - BorderDotsMin)*ceil)+1]*Scale + PosY;
		  	if (y1 < 0) y1 = -2;
		  	else if (y1 > height) y1 = height + 2;
		  	if (y2 < 0) y2 = -2;
		  	else if (y2 > height) y2 = height + 2;
      		g.drawLine(
      				  (int)Math.round(x1), 
      				  (int)Math.round(y1), 
      				  (int)Math.round(x2), 
      				  (int)Math.round(y2)
      		);
        }
	}
	
	void DrawGraph(Graphics g, double[] _x, double[] _y, int n)
	{
        	for (int i = 0; i < n - 1; i++)
        	{
        		double x1 = _x[i]*Scale + PosX;
        		double y1 = _y[i]*Scale + PosY;
        		double x2 = _x[i+1]*Scale + PosX;
        		double y2 = _y[i+1]*Scale + PosY;
		  		if (y1 < -2) 
		  		{
		  			x1 = (x2-x1)*(-2-y1)/(y2-y1)+x1;
		  			y1 = -2;
		  	    }
		  		else if (y1 > height + 2) 
	  			{
		  			x1 = (x2-x1)*(height + 2-y1)/(y2-y1)+x1;
		  			y1 = height + 2;
	  			}
		  		if (y2 < -2) 
		  		{
		  			x2 = (x2-x1)*(-2-y1)/(y2-y1)+x2;
		  			y2 = -2;
		  		}
		  		else if (y2 > height + 2) 
		  		{
		  			x2 = (x2-x1)*(height + 2-y1)/(y2-y1)+x2;
		  			y2 = height + 2;
		  		}
      		  	g.drawLine(
      				  (int)Math.round(x1), 
      				  (int)Math.round(y1), 
      				  (int)Math.round(x2), 
      				  (int)Math.round(y2)
      				  );
        	}
        	//(x-x1)/(x2-x1)=(y-y1)/(y2-y1)
        	//y = (y2-y1)*(x-x1)/(x2-x1)+y1
        	//x = (x2-x1)*(y-y1)/(y2-y1)+x1
	}
	
	void DrawGraphLine(Graphics g)
	{
		DrawGraph(g, func.X, func.inversY, func.size);
	}
	
	void DrawPoints(Graphics g)
	{
		for (int i = 0; i < func.size; i++)
 	{
		  	g.drawOval(
      					CoordToPixelX(func.X[i]) - getPointsRadius(), 
      		  			CoordToPixelY(func.Y[i]) - getPointsRadius(), 
      					getPointsRadius()*2, 
      					getPointsRadius()*2);
 	}
		/*
		int [] clast = new int[func.size];
		for (int i = 0; i < func.size; i++)
			clast[i] = -1;
		for (int i = 0; i < func.size; i++)
			for (int j = 0; j < func.size; j++)
				if ((Math.abs(func.X[i]*Scale - func.X[j]*Scale) < 2) && (Math.abs(func.Y[i]*Scale - func.Y[j]*Scale) < 2) && (i != j))
				{
					clast[i] = i;
					clast[j] = i;
				}
		for (int i = 0; i < func.size; i++)
		{
			if (clast[i] > -1)
			{
				int j = i;
				while (true)
				{
					if (j == func.size)
						break;
					if (clast[j] != clast[i])
						break;
					viz[j] = false;
					j++;
				}
				viz[(j-i)/2] = true;
				
			}
		}
		*/
	}

	void DrawGrid(Graphics g)
	{
    	g.setColor(new Color(220, 220, 220));
    	grid_power = 0.03125;
    	while (Scale*grid_power < (width+height)/80) grid_power *= 2;
    	int scale_int =(((int)(Scale*grid_power) == 0)?1:((int)(Scale*grid_power)));
    	double fromX = ((-(int)PosX)/(scale_int))*Scale*grid_power, toX = ((width - (int)PosX)/(scale_int))*Scale*grid_power;
    	double fromY = ((-(int)PosY)/(scale_int))*Scale*grid_power, toY = ((height -(int)PosY)/(scale_int))*Scale*grid_power;
    	//long start_time = System.currentTimeMillis();
    	//long end_time = System.currentTimeMillis();
    	//long difference = end_time-start_time;
    	//System.out.println(difference);
		for (double i = fromX; i <= toX; i+= Scale*grid_power)
			for (double j = fromY; j <= toY; j+= Scale*grid_power)
			{
	      	  	g.drawLine(
	      	  			(int)Math.round(i + PosX), 
	      	  			(int)Math.round(-height), 
	      	  			(int)Math.round(i + PosX), 
	      	  			(int)Math.round(height)
	      			  );
	      	  	g.drawLine(
		      			(int)Math.round(-width), 
		      			(int)Math.round(j + PosY), 
		      			(int)Math.round(width), 
		      			(int)Math.round(j + PosY)
		      			  );
			}
	}
	void DrawCoord(Graphics g)
	{
    	g.setColor(Color.GRAY);

  	  	g.drawLine(
  	  			CoordToPixelX(0), 
  	  			(CoordToPixelY(BorderMin) < height)?CoordToPixelY(BorderMin):(height), 
  	  			CoordToPixelX(0), 
  	  			(CoordToPixelY(BorderMax) > 0)?CoordToPixelY(BorderMax):(0)
  			  );
		g.drawString("y", 
				CoordToPixelX(-10/Scale), 
				(CoordToPixelY(BorderMax - 1)>10)?((CoordToPixelY(BorderMax - 1) < height - 10)?(CoordToPixelY(BorderMax - 1)):height - 10):10);
  	  	g.drawLine(
  	  			(CoordToPixelX(BorderMin) > 0)?CoordToPixelX(BorderMin):(0), 
  	  			CoordToPixelY(0), 
  	  			(CoordToPixelX(BorderMax) < width)?CoordToPixelX(BorderMax):(width),
      			CoordToPixelY(0)
      			  );
		g.drawString("X", 
				(CoordToPixelX(BorderMin + 1) > 10)?((CoordToPixelX(BorderMin + 1) < width - 10)?(CoordToPixelX(BorderMin + 1) - 10):width - 10):0, 
				CoordToPixelY(10/Scale));
	}
	public static int getPointsRadius() {
		return PointsRadius;
	}

	public static void setPointsRadius(int pointsRadius) {
		PointsRadius = pointsRadius;
	}

	public double getActionRadius() {
		return ActionRadius;
	}

	public void setActionRadius(double actionRadius) {
		ActionRadius = actionRadius;
	}

}
