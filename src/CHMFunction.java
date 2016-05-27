
public class CHMFunction {

	public static double Differentials[];
	public static double X[];
	public static double Y[];
	public static int size;
	public static boolean GetPointsFlag = false;
	public static double LeftBorder;
	public static double RightBorder;
	
    public static double ReturnY(double x)
    {
    	return 0.5*x*x + Math.cos(2*x);
    }
    
	private static double _GetDifferentialInPoint(double x, int n)
	{
		//double e = 0.00000001;
		double dx = 0.00001;
		double f1;
		if (n == 1)
		{
			f1 = (ReturnY(x + dx) - ReturnY(x)) / dx;
		}
		else
		{
			f1 = (_GetDifferentialInPoint(x + dx, n - 1) - _GetDifferentialInPoint(x, n - 1)) / dx;
		}
		return f1;
	}
	public static double GetDifferentialInPoint(double x, int n)
	{
		if (n < 1)
		{
			System.out.println("ERROR: n < 1");
			return 0;
		}
		double result = _GetDifferentialInPoint(x, n);
		return result;
	}
	
    public static void GenPoints(int n, double min, double max)
    {
    	GetPointsFlag = true;
    	size = n;
    	X = new double[size + 1];
    	Y = new double[size + 1];
    	LeftBorder = min;
    	RightBorder = max;
    	int k = 0;
    	for (double i = min; i <= max; i+= (max - min)/(n - 1))
    	{
    		X[k] = i;
    		Y[k] = CHMFunction.ReturnY(i);
    		k++;
    	}
    	X[k] = max;
    	Y[k] = CHMFunction.ReturnY(max);
    }
    /*
    public static void DrawInGraph(Graphics g, int width, int height)
    {
    	double ceil = Paint.ceil;
    	double BorderMinX = Paint.BorderMinX;
    	double BorderMaxX = Paint.BorderMaxX;
    	double x1 = BorderMinX*Paint.xScale1 + Paint.PosX;
    	double y1 = -CHMFunction.ReturnY(BorderMinX)*Paint.yScale1 + Paint.PosY;
        for (double i = BorderMinX+1/(double)ceil; i <= BorderMaxX - 1/(double)ceil; i+= 1/(double)ceil)
        {
        	double x2 = i*Paint.xScale1 + Paint.PosX;
        	double y2 = -CHMFunction.ReturnY(i)*Paint.yScale1 + Paint.PosY;
        	//g.setColor(Color.BLACK);
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
      	  	x1 = x2;
      	  	y1 = y2;
        }
    	
    }
	/*
	private Frac _GetDifferentialInPoint(Frac x, int n)
	{
		//double e = 0.00000001;
		Frac dx = new Frac(0.00001);
		Frac f1;
		if (n == 1)
		{
			f1 = Frac.Div(new Frac(ReturnY(Frac.Add(x, dx).GetDouble()) - ReturnY(x.GetDouble())), dx);
		}
		else
		{
			f1 = Frac.Div(Frac.Sub(_GetDifferentialInPoint(Frac.Add(x, dx), n - 1), _GetDifferentialInPoint(x, n - 1)), dx);
		}
		return f1;
	}
	public double GetDifferentialInPoint(double x, int n)
	{
		if (n < 1)
		{
			System.out.println("ERROR: n < 1");
			return 0;
		}
		Frac result = _GetDifferentialInPoint(new Frac(x), n);
		return result.GetDouble();
	}
	*/
}
