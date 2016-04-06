import java.util.Random;


public class Integral {
	static double InFunction(double x) //Подынтегральная функция
	{
	  return Math.sin(x);
	}

	static double CalcMidIntegral(double a, double b, int n)
	{
	  int i;
	  double result = 0, h;
	  h = (b - a) / n; //Шаг сетки
	  for(i = 0; i < n; i++)
	  {
	    result += InFunction(a + h * (i + 0.5)); //Вычисляем в средней точке и добавляем в сумму
	  }
	  result *= h;
	  return result;
	}

	static double CalcLeftIntegral(double a, double b, int n)
	{
	  int i;
	  double result = 0, h;
	  h = (b - a) / n; //Шаг сетки
	  for(i = 0; i < n; i++)
	  {
	    result += InFunction(a + h * i); //Вычисляем в левой точке и добавляем в сумму
	  }
	  result *= h;
	  return result;
	}

	static double CalcRightIntegral(double a, double b, int n)
	{
	  int i;
	  double result = 0, h;
	  h = (b - a) / n; //Шаг сетки
	  for(i = 0; i < n; i++)
	  {
	    result += InFunction(a + h * (i + 1)); //Вычисляем в правой точке и добавляем в сумму
	  }
	  result *= h;
	  return result;
	}

	static double CalcTrapIntegral(double a, double b, int n)
	{
	  int i;
	  double result = 0, h;
	  h = (b - a) / n; //Шаг сетки
	  for(i = 1; i < n - 1; i++)
	  {
	    result += InFunction(a + h*i)/* *(i + 1 - (i - 1)) / 2*/;
	  }
	  result = InFunction(a)*(h*1 / 2) + result + InFunction(b)*(b - a + h*(n - 1))/2;
	  return result;
	}

	static double CalcSimpIntegral(double a, double b, int n)
	{
		if (n % 2 != 0)
		{
			System.out.println("Error: n % 2 != 0");
			return 0;
		}
	  int i;
	  double result = 0, h;
	  h = (b - a) / (n * 6); //Шаг сетки
	  for(i = 1; i < n / 2; i++)
	  {
	    result += InFunction(a + h*i*2) + 4*InFunction(a + h*(i*2 + 1)) + InFunction(a + h*(i*2) + 2);
	  }
	  result *= h*6;
	  return result;
	}

	static double [] GetRandomMass(double a, double b, int n)
	{
		double result[] = new double[n];
		double tmp;
		result[0] = a;
		result[n - 1] = b;
		for (int i = 1; i < n - 1; i ++)
		{
			result[i] = a + Math.random()*(b - a);
			for (int j = i; j > 0; j--)
			{
				if (result[j] > result[j - 1])
					break;
				else if (result[j] == result[j - 1])
				{
					i--;
					break;
				}
				else
				{
					tmp = result[j];
					result[j] = result[j-1];
					result[j-1] = tmp;
				}
			}
		}
		/*
		for (int i = 0; i < n; i++)
		{
			System.out.print(result[i] + " ");
		}
		*/
		return result;
	}
	
	static double CalcMonteIntegral(double a, double b, int n)
	{
	  int i;
	  double result = 0, h;
	  h = (b - a) / n; //Шаг сетки
	  double u[] = GetRandomMass(a, b, n);
	  for(i = 0; i < n; i++)
	  {
	    result += InFunction(u[i]); //Вычисляем в левой точке и добавляем в сумму
	  }
	  result *= h;
	  return result;
	}
}
