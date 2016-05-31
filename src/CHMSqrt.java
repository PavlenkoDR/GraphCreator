
public class CHMSqrt {
	
	static double Func(double x)
	{
		return Math.pow(x, 3) - Math.cos(2*x);
	}
	
	static double Dif1(double x)
	{
		return 3*Math.pow(x, 2) + 2*Math.sin(2*x);
	}
	
	static double Dif2(double x)
	{
		return 6*x + 4*Math.cos(2*x);
	}
	
	static double NewTone(double a, double b, double e)
	{
		double result = 999999999, tmp = 0;
		if (a > b) return 0;
		if (Func(a)*Dif2(a) >= 0)
			result = a;
		else if (Func(b)*Dif2(b) >= 0)
			result = b;
		else
			return 0;
		while (true)
		{
			tmp = result;
			result = -Func(result)/Dif1(result) + result;
			if (Math.abs(result - tmp) < e)
				break;
		}
		return result;
	}
	
	static double Hord(double a, double b, double e)
	{
		double result = 999999999, tmp = 0, x0;
		if (a > b) return 0;
		if (Func(a)*Func(b) > 0) return 0;
		if (Func(a)*Dif2(a) >= 0)
		{
			result = a;
			x0 = b;
		}
		else if (Func(b)*Dif2(b) >= 0)
		{
			result = b;
			x0 = a;
		}
		else
			return 0;
		while (true)
		{
			tmp = result;
			result = result - (Func(result)*(result - x0))/(Func(result) - Func(x0));
			if (Math.abs(result - tmp) < e)
				break;
		}
		return result;
	}
	
	static double Dihot(double a, double b, double e)
	{
		double result = 999999999, tmp = 0;
		if (a > b) return 0;
		if (Func(a)*Func(b) > 0) return 0;
		double left = a, right = b;
		while (true)
		{
			tmp = result;
			result = left + (right-left)/2;
			if (Func(result)*Func(left) < 0)
				right = result;
			else if (Func(result)*Func(right) < 0)
				left = result;
			else
				return result;
			if (Math.abs(result - tmp) < e)
				break;
		}
		return result;
	}
	
}
