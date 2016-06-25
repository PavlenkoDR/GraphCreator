
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
    		return Math.pow(x, 3) - Math.cos(2*x);
   	}
    
	private static double _GetDifferentialInPoint(double x, int n)
	{
		double dx = 0.00001;
		double f1;
		if (n == 1)
			f1 = (ReturnY(x + dx) - ReturnY(x)) / dx;
		else
			f1 = (_GetDifferentialInPoint(x + dx, n - 1) - _GetDifferentialInPoint(x, n - 1)) / dx;
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
}
