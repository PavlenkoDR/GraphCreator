
public class CHMFure {
	static double Func(double x)
	{
		return Math.pow(x, 3) - Math.cos(2*x);
	}
	static double FurFunc(double x, double a, double b, int i, int what)
	{
		double _x = (b-a)*x/(2*Math.PI) + (b+a)/2;
		double result;
		if (what == 0)
		{
			result = Func(_x) * Math.cos(i*x);
		}
		else
		{
			result = Func(_x) * Math.sin(i*x);
		}
		return result;
	}
	static double CalcMidIntegral(double a, double b, int degre, int _i, int what)
	{
		int n;
		if (degre == -1)
			n = 10000;
		else
			n  = (int)Math.round(Math.pow(2, degre));
		int i;
		double result = 0, h;
		h = (b - a) / n; //Шаг сетки
		for(i = 0; i < n; i++)
		{
				result += FurFunc(a + h * (i + 0.5), a, b, _i, what); //Вычисляем в средней точке и добавляем в сумму
		}
		result *= h;
	  	return result;
	}

	static double Return_ai(double a, double b, int _i)
	{
		return (b-a)/(2*Math.PI*Math.PI)*CalcMidIntegral(-Math.PI, Math.PI, -1, _i, 0);
	}
	
	static double Return_bi(double a, double b, int _i)
	{
		return (b-a)/(2*Math.PI*Math.PI)*CalcMidIntegral(-Math.PI, Math.PI, -1, _i, 1);
	}
	public static double Fur(double x, double a, double b, int n)
	{
		double h = (b - a)/n;
		double A[] = new double[n];
		double B[] = new double[n];
		for (int i = 0; i < n; i++)
		{
			A[i] = Return_ai(a, b, i);
			B[i] = Return_bi(a, b, i);
		}
		double sum = A[0]/2;
		for (int i = 1; i < n; i++)
		{
			sum += A[i]*Math.cos(i*x) + B[i]*Math.sin(i*x);
		}
		return sum;
	}
}
