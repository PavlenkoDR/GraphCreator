import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;


public class Integral {

	static double ReturnDefferential(double x)
	{
		return x - 2*Math.sin(2*x);
	}
	
	static double CalcMidIntegral(double a, double b, int degre)
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
			result += Function.ReturnY(a + h * (i + 0.5)); //Вычисляем в средней точке и добавляем в сумму
		}
		result *= h;
	  	return result;
	}

	static double CalcLeftIntegral(double a, double b, int degre)
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
	    result += Function.ReturnY(a + h * i); //Вычисляем в левой точке и добавляем в сумму
	  }
	  result *= h;
	  return result;
	}

	static double CalcRightIntegral(double a, double b, int degre)
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
	    result += Function.ReturnY(a + h * (i + 1)); //Вычисляем в правой точке и добавляем в сумму
	  }
	  result *= h;
	  return result;
	}

	static double CalcTrapIntegral(double a, double b, int degre)
	{
		int n;
		if (degre == -1)
			n = 10000;
		else
			n  = (int)Math.round(Math.pow(2, degre));
	  int i;
	  double result = 0, h;
	  h = (b - a) / n; //Шаг сетки
	  for(i = 1; i < n - 1; i++)
	  {
	    result += Function.ReturnY(a + h*i)/* *(i + 1 - (i - 1)) / 2*/;
	  }
	  result = h*Function.ReturnY(a)*(a + h*1)/ 2 + h*result + h*Function.ReturnY(b)*(b - a + h*(n - 1))/2;
	  return result;
	}

	static double CalcSimpIntegral(double a, double b, int degre)
	{
		int n;
		if (degre == -1)
			n = 10000;
		else
			n  = (int)Math.round(Math.pow(2, degre));
		if (n % 2 != 0)
		{
			System.out.println("Error: n % 2 != 0");
			return 0;
		}
	  int i;
	  double result = 0, h;
	  h = (b - a) / (n); //Шаг сетки
	  for(i = 1; i < n / 2; i++)
	  {
	    result += Function.ReturnY(a + h*i*2) + 4*Function.ReturnY(a + h*(i*2 + 1)) + Function.ReturnY(a + h*(i*2 + 2));
	  }
	  result *= h/3;
	  return result;
	}

	static double [] GetRandomMass(double a, double b, int n)
	{
		double result[] = new double[n];
		result[0] = a;
		result[n - 1] = b;
		for (int i = 1; i < n - 1; i ++)
		{
			result[i] = a + Math.random()*(b - a);
		}
		return result;
	}
	
	static double CalcMonteIntegral(double a, double b, int degre)
	{
		int n;
		if (degre == -1)
			n = 10000;
		else
			n  = (int)Math.round(Math.pow(2, degre));
	  int i;
	  double result = 0, h;
	  h = (b - a) / n; //Шаг сетки
	  double u[] = GetRandomMass(a, b, n);
	  for(i = 0; i < n; i++)
	  {
	    result += Function.ReturnY(u[i]); //Вычисляем в левой точке и добавляем в сумму
	  }
	  result *= h;
	  return result;
	}
	
	static double DeltaAbsolute(double th, double pr)
	{
		return Math.abs(th - pr);
	}
	
	static double DeltaOtnosit(double th, double pr)
	{
		return (DeltaAbsolute(th, pr) * 100)/Math.abs(th);
	}
	
	static double SpeedConvergence(double DeltaPrevios, double DeltaCurrent)
	{
		return DeltaPrevios/DeltaCurrent;
	}
	
	static double ThError(double a, double b, double h, int step)
	{
		double max = ReturnDefferential(a);
		double def;
		for (double i = a; i <= b; i += 1/((double)step))
		{
			def = ReturnDefferential(i);
			max = (def > max) ? def : max;
		}
		return max*h*(b-a)/((double)2);
	}
    
    public static double GetRound(double x, int n)
    {
    	return new BigDecimal(x).setScale(n, RoundingMode.UP).doubleValue();
    }
    
	static void GetTable(double th)
	{

    	File fout = new File("IntegralTable.txt");
        try {
            //проверяем, что если файл не существует то создаем его
            if(!fout.exists()){
            	fout.createNewFile();
            }
     
            //PrintWriter обеспечит возможности записи в файл
            PrintWriter out = new PrintWriter(fout.getAbsoluteFile());
     
            try {
                //Записываем текст у файл
            	//long currentTime = 0, deltaCurrentTime = 0;
            	double integral = 0;
            	double left = 0.6, right = 1.1;
            	double DeltaPrevios = 1, DeltaCurrent;
            	int degree = 16;
            	out.println("Expreriment number | Power | " + /*"Time of work | " + */ "Value of Integral | Absolute | Otnosit | R(x) | Speed of Convergence");
            	out.println();
            	out.println("==================================================================== Left Integral ====================================================================");
            	int step = 10000;
            	double h;
            	int n = 2;
            	int SignAfterCommon = 8;
            	for (int i = 1; i <= degree; i++)
            	{
            		//currentTime = System.currentTimeMillis();
            		integral = Integral.CalcLeftIntegral(left, right, i);
            		//deltaCurrentTime = System.currentTimeMillis() - currentTime;
            		DeltaCurrent = Integral.DeltaAbsolute(th, integral);
            		h = (right - left) / n;
            		n *= 2;
                	out.println(	i+ "&" + "$2^{"+i+"}$" + "&" + 
        							//deltaCurrentTime + "" + 
        							"$" + GetRound(integral, SignAfterCommon) + "$" + "&" +
        							"$" + GetRound(DeltaCurrent, SignAfterCommon) + "$" + "&" + 
        							"$" + GetRound(Integral.DeltaOtnosit(th, integral), SignAfterCommon) + "$" + "&" + 
        							"$" + GetRound(ThError(left, right, h, step), SignAfterCommon) + "$" + "&" +
        							"$" + GetRound(SpeedConvergence(DeltaPrevios, DeltaCurrent), SignAfterCommon) + "$" + "\\\\");
                	out.println("\\hline");
                	DeltaPrevios = DeltaCurrent;
                	System.out.print("!");
            	}
            	out.println();
            	System.out.println("<");
            	DeltaPrevios = 1;
            	n = 2;
            	out.println("==================================================================== Midle Integral ====================================================================");
            	for (int i = 1; i <= degree; i++)
            	{
            		//currentTime = System.currentTimeMillis();
            		integral = Integral.CalcMidIntegral(left, right, i);
            		//deltaCurrentTime = System.currentTimeMillis() - currentTime;
            		DeltaCurrent = Integral.DeltaAbsolute(th, integral);
            		h = (right - left) / n;
            		n *= 2;
                	out.println(	i+ "&" + "$2^{"+i+"}$" + "&" + 
        							//deltaCurrentTime + "" + 
        							"$" + GetRound(integral, SignAfterCommon) + "$" + "&" +
        							"$" + GetRound(DeltaCurrent, SignAfterCommon) + "$" + "&" + 
        							"$" + GetRound(Integral.DeltaOtnosit(th, integral), SignAfterCommon) + "$" + "&" + 
        							"$" + GetRound(ThError(left, right, h, step), SignAfterCommon) + "$" + "&" +
        							"$" + GetRound(SpeedConvergence(DeltaPrevios, DeltaCurrent), SignAfterCommon) + "$" + "\\\\");
                	out.println("\\hline");
                	DeltaPrevios = DeltaCurrent;
                	System.out.print("!");
            	}
            	out.println();
            	System.out.println("<");
            	DeltaPrevios = 1;
            	n = 2;
            	out.println("==================================================================== Right Integral ====================================================================");
            	for (int i = 1; i <= degree; i++)
            	{
            		//currentTime = System.currentTimeMillis();
            		integral = Integral.CalcRightIntegral(left, right, i);
            		//deltaCurrentTime = System.currentTimeMillis() - currentTime;
            		DeltaCurrent = Integral.DeltaAbsolute(th, integral);
            		h = (right - left) / n;
            		n *= 2;
                	out.println(	i+ "&" + "$2^{"+i+"}$" + "&" + 
        							//deltaCurrentTime + "" + 
        							"$" + GetRound(integral, SignAfterCommon) + "$" + "&" +
        							"$" + GetRound(DeltaCurrent, SignAfterCommon) + "$" + "&" + 
        							"$" + GetRound(Integral.DeltaOtnosit(th, integral), SignAfterCommon) + "$" + "&" + 
        							"$" + GetRound(ThError(left, right, h, step), SignAfterCommon) + "$" + "&" +
        							"$" + GetRound(SpeedConvergence(DeltaPrevios, DeltaCurrent), SignAfterCommon) + "$" + "\\\\");
                	out.println("\\hline");
                	DeltaPrevios = DeltaCurrent;
                	System.out.print("!");
            	}
            	out.println();
            	System.out.println("<");
            	DeltaPrevios = 1;
            	n = 2;
            	out.println("==================================================================== Trapeciya Integral ====================================================================");
            	for (int i = 1; i <= degree; i++)
            	{
            		//currentTime = System.currentTimeMillis();
            		integral = Integral.CalcTrapIntegral(left, right, i);
            		//deltaCurrentTime = System.currentTimeMillis() - currentTime;
            		DeltaCurrent = Integral.DeltaAbsolute(th, integral);
            		h = (right - left) / n;
            		n *= 2;
                	out.println(	i+ "&" + "$2^{"+i+"}$" + "&" + 
        							//deltaCurrentTime + "" + 
        							"$" + GetRound(integral, SignAfterCommon) + "$" + "&" +
        							"$" + GetRound(DeltaCurrent, SignAfterCommon) + "$" + "&" + 
        							"$" + GetRound(Integral.DeltaOtnosit(th, integral), SignAfterCommon) + "$" + "&" + 
        							"$" + GetRound(ThError(left, right, h, step), SignAfterCommon) + "$" + "&" +
        							"$" + GetRound(SpeedConvergence(DeltaPrevios, DeltaCurrent), SignAfterCommon) + "$" + "\\\\");
                	out.println("\\hline");
                	DeltaPrevios = DeltaCurrent;
                	System.out.print("!");
            	}
            	out.println();
            	System.out.println("<");
            	DeltaPrevios = 1;
            	n = 2;
            	out.println("==================================================================== Simpson Integral ====================================================================");
            	for (int i = 1; i <= degree; i++)
            	{
            		//currentTime = System.currentTimeMillis();
            		integral = Integral.CalcSimpIntegral(left, right, i);
            		//deltaCurrentTime = System.currentTimeMillis() - currentTime;
            		DeltaCurrent = Integral.DeltaAbsolute(th, integral);
            		h = (right - left) / n;
            		n *= 2;
                	out.println(	i+ "&" + "$2^{"+i+"}$" + "&" + 
        							//deltaCurrentTime + "" + 
        							"$" + GetRound(integral, SignAfterCommon) + "$" + "&" +
        							"$" + GetRound(DeltaCurrent, SignAfterCommon) + "$" + "&" + 
        							"$" + GetRound(Integral.DeltaOtnosit(th, integral), SignAfterCommon) + "$" + "&" + 
        							"$" + GetRound(ThError(left, right, h, step), SignAfterCommon) + "$" + "&" +
        							"$" + GetRound(SpeedConvergence(DeltaPrevios, DeltaCurrent), SignAfterCommon) + "$" + "\\\\");
                	out.println("\\hline");
                	DeltaPrevios = DeltaCurrent;
                	System.out.print("!");
            	}
            	out.println();
            	System.out.println("<");
            	DeltaPrevios = 1;
            	n = 2;
            	out.println("==================================================================== MonteCarlo Integral ====================================================================");
            	for (int i = 1; i <= degree; i++)
            	{
            		//currentTime = System.currentTimeMillis();
            		integral = Integral.CalcMonteIntegral(left, right, i);
            		//deltaCurrentTime = System.currentTimeMillis() - currentTime;
            		DeltaCurrent = Integral.DeltaAbsolute(th, integral);
            		h = (right - left) / n;
            		n *= 2;
                	out.println(	i+ "&" + "$2^{"+i+"}$" + "&" + 
        							//deltaCurrentTime + "" + 
        							"$" + GetRound(integral, SignAfterCommon) + "$" + "&" +
        							"$" + GetRound(DeltaCurrent, SignAfterCommon) + "$" + "&" + 
        							"$" + GetRound(Integral.DeltaOtnosit(th, integral), SignAfterCommon) + "$" + "&" + 
        							"$" + GetRound(ThError(left, right, h, step), SignAfterCommon) + "$" + "&" +
        							"$" + GetRound(SpeedConvergence(DeltaPrevios, DeltaCurrent), SignAfterCommon) + "$" + "\\\\");
                	out.println("\\hline");
                	DeltaPrevios = DeltaCurrent;
                	System.out.print("!");
            	}
            	System.out.println("<");
            	out.println("==================================================================== n = 10000 ====================================================================");
            	h = (right - left)/((double)10000);
        		integral = Integral.CalcLeftIntegral(left, right, -1);
            	out.println(	"Left"+ "&" + 
    							//deltaCurrentTime + "" + 
    							"$" + GetRound(integral, SignAfterCommon) + "$" + "&" +
    							"$" + GetRound(Integral.DeltaAbsolute(th, integral), SignAfterCommon) + "$" + "&" + 
    							"$" + GetRound(Integral.DeltaOtnosit(th, integral), SignAfterCommon) + "$" + "&" + 
    							"$" + GetRound(ThError(left, right, h, step), SignAfterCommon) + "$" + "\\\\");
            	out.println("\\hline");
        		integral = Integral.CalcMidIntegral(left, right, -1);
            	out.println(	"Left"+ "&" + 
    							//deltaCurrentTime + "" + 
    							"$" + GetRound(integral, SignAfterCommon) + "$" + "&" +
    							"$" + GetRound(Integral.DeltaAbsolute(th, integral), SignAfterCommon) + "$" + "&" + 
    							"$" + GetRound(Integral.DeltaOtnosit(th, integral), SignAfterCommon) + "$" + "&" + 
    							"$" + GetRound(ThError(left, right, h, step), SignAfterCommon) + "$" + "\\\\");
            	out.println("\\hline");
        		integral = Integral.CalcRightIntegral(left, right, -1);
            	out.println(	"Left"+ "&" + 
    							//deltaCurrentTime + "" + 
    							"$" + GetRound(integral, SignAfterCommon) + "$" + "&" +
    							"$" + GetRound(Integral.DeltaAbsolute(th, integral), SignAfterCommon) + "$" + "&" + 
    							"$" + GetRound(Integral.DeltaOtnosit(th, integral), SignAfterCommon) + "$" + "&" + 
    							"$" + GetRound(ThError(left, right, h, step), SignAfterCommon) + "$" + "\\\\");
            	out.println("\\hline");
        		integral = Integral.CalcTrapIntegral(left, right, -1);
            	out.println(	"Left"+ "&" + 
    							//deltaCurrentTime + "" + 
    							"$" + GetRound(integral, SignAfterCommon) + "$" + "&" +
    							"$" + GetRound(Integral.DeltaAbsolute(th, integral), SignAfterCommon) + "$" + "&" + 
    							"$" + GetRound(Integral.DeltaOtnosit(th, integral), SignAfterCommon) + "$" + "&" + 
    							"$" + GetRound(ThError(left, right, h, step), SignAfterCommon) + "$" + "\\\\");
            	out.println("\\hline");
        		integral = Integral.CalcSimpIntegral(left, right, -1);
            	out.println(	"Left"+ "&" + 
    							//deltaCurrentTime + "" + 
    							"$" + GetRound(integral, SignAfterCommon) + "$" + "&" +
    							"$" + GetRound(Integral.DeltaAbsolute(th, integral), SignAfterCommon) + "$" + "&" + 
    							"$" + GetRound(Integral.DeltaOtnosit(th, integral), SignAfterCommon) + "$" + "&" + 
    							"$" + GetRound(ThError(left, right, h, step), SignAfterCommon) + "$" + "\\\\");
            	out.println("\\hline");
        		integral = Integral.CalcMonteIntegral(left, right, -1);
            	out.println(	"Left"+ "&" + 
    							//deltaCurrentTime + "" + 
    							"$" + GetRound(integral, SignAfterCommon) + "$" + "&" +
    							"$" + GetRound(Integral.DeltaAbsolute(th, integral), SignAfterCommon) + "$" + "&" + 
    							"$" + GetRound(Integral.DeltaOtnosit(th, integral), SignAfterCommon) + "$" + "&" + 
    							"$" + GetRound(ThError(left, right, h, step), SignAfterCommon) + "$" + "\\\\");
            	out.println("\\hline");
            	System.out.println("Done");
            } finally {
                //После чего мы должны закрыть файл
                //Иначе файл не запишется
                out.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
	}
	
}
