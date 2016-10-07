import java.awt.Color;
import java.awt.Graphics;


public class Paint {
	double BorderMin = -20;
	double BorderMax = 20;
    static double ceil = 20;
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
	
	public void SetDrawGraphExtraPoly(boolean f){boolDrawGraphExtraPoly = f;}

	int CoordToPixelX(double coord)
	{
		return (int)Math.round(coord*Scale + PosX);
	}
	int CoordToPixelY(double coord)
	{
		return (int)Math.round(-coord*Scale + PosY);
	}
	double PixelToCoordX(double pixel)
	{
		return (pixel - PosX)/Scale;
	}
	double PixelToCoordY(double pixel)
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
		if (func == _func) return;
		func = _func;
		if (func.GetPointsFlag)
		{
			double tmpX = (func.X[func.size - 1] - func.X[0])*2;
			//double tmpY = (func.maxY - func.minY)*2;
			
			BorderMax = tmpX;
			BorderMin = -tmpX;

			xGraphPoly = new double[(int)Math.round((BorderMax - BorderMin)*ceil) + 1];
			yGraphPoly = new double[(int)Math.round((BorderMax - BorderMin)*ceil) + 1];
			xGraphApprox = new double[(int)Math.round((BorderMax - BorderMin)*ceil) + 1];
			yGraphApprox = new double[(int)Math.round((BorderMax - BorderMin)*ceil) + 1];
			xGraphSpline = new double[(int)Math.round((BorderMax - BorderMin)*ceil) + 1];
			yGraphSpline = new double[(int)Math.round((BorderMax - BorderMin)*ceil) + 1];
			double shift = 0;
    		for (double i = BorderMin; i <= BorderMax; i+= 1/(double)ceil)
    		{
    			xGraphPoly[(int)Math.round((i - BorderMin)*ceil)] = i + shift;
    			yGraphPoly[(int)Math.round((i - BorderMin)*ceil)] = -(func.interp.getValue(i + shift));
    			xGraphApprox[(int)Math.round((i - BorderMin)*ceil)] = i + shift;
    			yGraphApprox[(int)Math.round((i - BorderMin)*ceil)] = -(func.approx.getValue(i + shift, func.nApprox));
    			xGraphSpline[(int)Math.round((i - BorderMin)*ceil)] = i + shift;
    			yGraphSpline[(int)Math.round((i - BorderMin)*ceil)] = -(func.spline.getValue(i + shift));
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
			from = BorderMin;
			to = BorderMax;
		}
		else
		{
			from = func.X[0];
			to = func.X[func.size - 1];
		}
	    for (double i = from; i <= to - 1/(double)ceil; i+= 1/(double)ceil)
        {
	    	double x1 = _x[(int)Math.round((i - BorderMin)*ceil)]*Scale + PosX;
        	double y1 = _y[(int)Math.round((i - BorderMin)*ceil)]*Scale + PosY;
        	double x2 = _x[(int)Math.round((i - BorderMin)*ceil)+1]*Scale + PosX;
        	double y2 = _y[(int)Math.round((i - BorderMin)*ceil)+1]*Scale + PosY;
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
    		g.setColor(new Color(0, 0, 0));

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
