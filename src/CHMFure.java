
public class CHMFure {
	
	static double a = 1, b = 2;
	
	static double[] funcX, funcY,
					furX, furY,
					deltaX, deltaY;
	
	static int		sizeFunc,
					sizeFur,
					sizeDelta;
	
	static boolean GetPointsFlag = false;

	static double Func(double x)
	{
		return Math.pow(x, 3) - Math.cos(2*x);
	}
	
	static double FurFunc(double x, int k, int flag)
	{
		//x=(b-a)/(2*Math.PI)*x+(b+a)/2;
		double result = 0;
		switch(flag)
		{
		case 0:
			result = Func(x)*Math.sin(k*x);
			break;
		case 1:
			result = Func(x)*Math.cos(k*x);
			break;
		case 2:
			result = Func(x);
			break;
		default:
			break;
		}
		return result;
	}

	double FurFuncx2y(double x)
	{
		x=(b-a)/(2*Math.PI)*x+(b+a)/2;
		return x;
	}
	
	static double CalcMidIntegral(int k, int flag)
	{
		double left = -Math.PI, right = Math.PI;
		int n = 1000;
		int i;
		double result = 0, h;
		h = (right - left) / n; //Шаг сетки
		for(i = 0; i < n; i++)
			result += FurFunc(left + h * (i + 0.5), k, flag);
		result *= h;
		//System.out.print(">" + result);
	  	return result;
	}
	static double a_0()
	{
		return CalcMidIntegral(0, 2)/Math.PI;
	}
	
	static double a_i(int k)
	{
		return CalcMidIntegral(k, 0)/Math.PI;
	}
	
	static double b_i(int k)
	{
		return CalcMidIntegral(k, 1)/Math.PI;
	}
	
	static double GetFur(double x)
	{
		int n = 200;
		double sum = a_0()/2;
		//System.out.print("!" + sum);
		for (int i = 0; i < n; i++)
			sum += a_i(i)*Math.sin(i*x);
		//System.out.print("!" + sum);
		for (int i = 0; i < n; i++)
			sum += b_i(i)*Math.cos(i*x);
		return sum;
	}
	
	static void GetResult(double x)
	{
		double fun = CHMFure.Func(x);
		double fur = CHMFure.GetFur(x);
    	System.out.println("Function: " + fun);
    	System.out.println("Furye: "+ fur);
    	System.out.println("Delta absolute: "+ Math.abs(fur - fun));
    	System.out.println("Delta otnos: "+ Math.abs(fur - fun)*100/fun);
	}
	
	static void CreateDots()
	{
		int n;
		double h;
		
		n = 100;
		sizeFunc = n;
		h = (b-a)/n;
		funcX = new double[n + 1];
		funcY = new double[n + 1];
		for (int i = 0; i <= n; i++)
		{
			funcX[i] = a + h*i;
			funcY[i] = -Func(funcX[i]);
		}

		sizeFur = n;
		furX = new double[n + 1];
		furY = new double[n + 1];
		for (int i = 0; i <= n; i++)
		{
			furX[i] = a + h*i;
			furY[i] = -GetFur(furX[i]);
		}

		sizeDelta = n;
		deltaX = new double[n + 1];
		deltaY = new double[n + 1];
		for (int i = 0; i <= n; i++)
		{
			deltaX[i] = a + h*i;
			deltaY[i] = -Math.abs(funcY[i] - furY[i]);
		}
		
		double sum = 0;
		
		for (int i = 0; i <= n; i++)
			sum += -deltaY[i];
		System.out.println("Midle absolute delta: " + sum/(n+1));
		
		sum = 0;
		for (int i = 0; i <= n; i++)
			sum += deltaY[i]*100/funcY[i];
		System.out.println("Midle otnosit delta: " + sum/(n+1) + "%");
		
		GetPointsFlag = true;
	}
	
<<<<<<< HEAD
}
=======
}
>>>>>>> dbe462ef1d9d99496edea6e1da8ec043fb27e86c
