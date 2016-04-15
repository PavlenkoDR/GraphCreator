
public class Function {
	
	public static double Differentials[];
	public int size;
	
    public static double ReturnY(double x)
    {
    	return 0.5*x*x + Math.cos(2*x);
    }
    
	private static double _GetDifferentialInPoint(double x, int n)
	{
		double e = 0.00000001;
		double dx = 0.01;
		double f1 = -1000000, f2 = 0;
		if (n == 1)
		{
			while(Math.abs(f2 - f1) >= e)
			{
			    f2 = f1;
			    f1 = (ReturnY(x + dx) - ReturnY(x)) / dx;
			    dx /= 2;
			}
		}
		else
		{
			while(Math.abs(f1 - f2) >= e)
			{
			    f2 = f1;
			    f1 = (_GetDifferentialInPoint(x + dx, n - 1) - _GetDifferentialInPoint(x, n - 1)) / dx;
			    dx /= 2;
			}
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
}
