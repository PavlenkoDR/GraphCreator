import java.awt.Color;
import java.awt.Graphics;


public class Paint {
	static double BorderMinX = -20;
	static double BorderMaxX = 20;
	static double BorderMinY = -20;
	static double BorderMaxY = 20;
    static double ceil = 20;
	
	private double xGraphPoly[];
	private double yGraphPoly[];
	private double xGraphApprox[];
	private double yGraphApprox[];
	private double xGraphSpline[];
	private double yGraphSpline[];
	double xScale1 = 16;
	double yScale1 = 16;
	double PosX = 0;
	double PosY = 0;
	double ActionRadius = 0.5;
    
	int width;
	int height;
   
	static int PointsRadius = 3;
	static int ActionPoint = -1;

	public boolean boolDrawGraphExtraPoly;
	
	public void SetDrawGraphExtraPoly(boolean f){boolDrawGraphExtraPoly = f;}
	
	Function func;
    
	Paint(int _width, int _height)
	{
  		width = _width;
  		height = _height;

		xGraphPoly = new double[(int)Math.round((BorderMaxX - BorderMinX)*ceil) + 1];
		yGraphPoly = new double[(int)Math.round((BorderMaxX - BorderMinX)*ceil) + 1];
		xGraphApprox = new double[(int)Math.round((BorderMaxX - BorderMinX)*ceil) + 1];
		yGraphApprox = new double[(int)Math.round((BorderMaxX - BorderMinX)*ceil) + 1];
		xGraphSpline = new double[(int)Math.round((BorderMaxX - BorderMinX)*ceil) + 1];
		yGraphSpline = new double[(int)Math.round((BorderMaxX - BorderMinX)*ceil) + 1];
    	ScaleX = xScale1;
    	ScaleY = yScale1;
	}
	public void SetFunc(Function _func)
	{
		func = _func;
		if (func.GetPointsFlag)
		{
			double tmp = Math.max(func.X[func.size - 1] - func.X[0], func.Y[func.size - 1] - func.Y[0])*2;
			BorderMaxX = tmp;
			BorderMaxY = tmp;
			BorderMinX = -tmp;
			BorderMinY = -tmp;
		}
		if (func.GetPointsFlag)
        		for (double i = BorderMinX; i <= BorderMaxX; i+= 1/(double)ceil)
        		{
        			xGraphPoly[(int)Math.round((i - BorderMinX)*ceil)] = i;
        			yGraphPoly[(int)Math.round((i - BorderMinX)*ceil)] = -(func.interp.getValue(i));
        			xGraphApprox[(int)Math.round((i - BorderMinX)*ceil)] = i;
        			yGraphApprox[(int)Math.round((i - BorderMinX)*ceil)] = -(func.approx.getValue(i, func.nApprox));
        			xGraphSpline[(int)Math.round((i - BorderMinX)*ceil)] = i;
        			yGraphSpline[(int)Math.round((i - BorderMinX)*ceil)] = -(func.spline.getValue(i));
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
			from = BorderMinX;
			to = BorderMaxX;
		}
		else
		{
			from = func.X[0];
			to = func.X[func.size - 1];
		}
	    for (double i = from; i <= to - 1/(double)ceil; i+= 1/(double)ceil)
        {
	    	double x1 = _x[(int)Math.round((i - BorderMinX)*ceil)]*xScale1 + PosX;
        	double y1 = _y[(int)Math.round((i - BorderMinX)*ceil)]*yScale1 + PosY;
        	double x2 = _x[(int)Math.round((i - BorderMinX)*ceil)+1]*xScale1 + PosX;
        	double y2 = _y[(int)Math.round((i - BorderMinX)*ceil)+1]*yScale1 + PosY;
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
        		double x1 = _x[i]*xScale1 + PosX;
        		double y1 = _y[i]*yScale1 + PosY;
        		double x2 = _x[i+1]*xScale1 + PosX;
        		double y2 = _y[i+1]*yScale1 + PosY;
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
      					(int)Math.round(func.X[i]*xScale1 + PosX) - PointsRadius, 
      		  			(int)Math.round(-func.Y[i]*yScale1 + PosY) - PointsRadius, 
      					PointsRadius*2, 
      					PointsRadius*2);
        	}
	}

	double ScaleX, ScaleY;
	void DrawGrid(Graphics g)
	{
    	g.setColor(new Color(220, 220, 220));
    	double fromX = (BorderMinX)*xScale1, toX = (BorderMaxX)*xScale1;
    	double fromY = (BorderMinY)*yScale1, toY = (BorderMaxY)*yScale1;
    	while(fromX + PosX> 0)
    	{
    		fromX -= xScale1;
    	}
    	while(fromY + PosY> 0)
    	{
    		fromY -= yScale1;
    	}
    	while(toX + PosX < width)
    	{
    		toX += xScale1;
    	}
    	while(toY + PosY < height)
    	{
    		toY += yScale1;
    	}
		for (double i = fromX; i <= toX; i+= xScale1)
			for (double j = fromY; j <= toY; j+= yScale1)
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
  	  			(int)Math.round(PosX), 
  	  			(int)Math.round(BorderMinY*yScale1 + PosY), 
  	  			(int)Math.round(PosX), 
  	  			(int)Math.round(BorderMaxY*yScale1 + PosY)
  			  );
		g.drawString("y", 
				(int)Math.round(PosX + PointsRadius) - 20 , 
				((int)Math.round(BorderMinY*yScale1 + PosY + 10)>10)?((int)Math.round(BorderMinY*yScale1 + PosY + 10)):10);
  	  	g.drawLine(
      			(int)Math.round(BorderMinX*xScale1 + PosX), 
      			(int)Math.round(PosY), 
      			(int)Math.round(BorderMaxX*xScale1 + PosX), 
      			(int)Math.round(PosY)
      			  );
		g.drawString("X", 
				((int)Math.round(BorderMaxX*xScale1 + PosX)< 10/*width*/)?((int)Math.round(BorderMaxX*xScale1 + PosX) - 10):(10/*width*/ - 10), 
				(int)Math.round(PosY) - 10);
	}

}
