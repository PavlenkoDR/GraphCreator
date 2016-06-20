import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;


public class CHMIntegral {

	
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
			result += CHMFunction.ReturnY(a + h * (i + 0.5)); //Вычисляем в средней точке и добавляем в сумму
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
	    result += CHMFunction.ReturnY(a + h * i); //Вычисляем в левой точке и добавляем в сумму
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
	    result += CHMFunction.ReturnY(a + h * (i + 1)); //Вычисляем в правой точке и добавляем в сумму
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
	    result += CHMFunction.ReturnY(a + h*i)/* *(i + 1 - (i - 1)) / 2*/;
	  }
	  result = h*CHMFunction.ReturnY(a)*(a + h*1)/ 2 + h*result + h*CHMFunction.ReturnY(b)*(b - a + h*(n - 1))/2;
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
	  double result = 0, h;
	  h = (b - a) / (n); //Шаг сетки
	  /*
	  for(i = 1; i < n / 2; i++)
	  {
	    result += CHMFunction.ReturnY(a + h*i*2) + 4*CHMFunction.ReturnY(a + h*(i*2 + 1)) + CHMFunction.ReturnY(a + h*(i*2 + 2));
	  }
	  */
	  result = CHMFunction.ReturnY(a) + CHMFunction.ReturnY(b);
	  double sum1 = 0, sum2 = 0;
	  for (int i = 1; i < (n-1)/2; i++)
		  sum1 += CHMFunction.ReturnY(a + h*i*2);
	  sum1 *=2;
	  for (int i = 1; i < (n)/2; i++)
		  sum2 += CHMFunction.ReturnY(a + h*(i*2-1));
	  sum2*=4;
	  result += sum1 + sum2;
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
	    result += CHMFunction.ReturnY(u[i]); //Вычисляем в левой точке и добавляем в сумму
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

	static double ReturnDefferential(double x, int n)
	{
		double result = 0;
		if (n == 1)
			result =  x - 2*Math.sin(2*x);
		else if (n == 2)
			result =  1 - 4*Math.cos(2*x);
		else
			result = -Math.pow(2, n)*Math.cos(2*x + n*Math.PI/2);
		return result;
	}
	
	static double ThError(double a, double b, int n, int step)
	{
		double max = Math.abs(ReturnDefferential(a, 1));
		double def;
		for (double i = a; i <= b; i += 1/((double)step))
		{
			def = Math.abs(ReturnDefferential(i, 1));
			max = (def > max) ? def : max;
		}
		return Math.pow((b-a), n)*max/Bicycles.Factorial(n);
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
            	int degree = 15;
            	out.println("Expreriment number | Power | " + /*"Time of work | " + */ "Value of Integral | Absolute | Otnosit | R(x) | Speed of Convergence");
            	out.println();
            	out.println("========== Left Integral ==========\\\\");
            	int step = 10000;
            	double h;
            	int n = 2;
            	int SignAfterCommon = 8;

            	out.print("\\begin{tabular}{| c | c | c | c | c | c | c |}\n\\hline\n№&n&	I&$\\bigtriangleup I_n$&$\\delta I_n$&$R_n$&$\\frac{\\bigtriangleup I_{2j}}{\\bigtriangleup I_{2j+1}}$\\\\\\hline\n");
            	for (int i = 1; i <= degree; i++)
            	{
            		//currentTime = System.currentTimeMillis();
            		integral = CHMIntegral.CalcLeftIntegral(left, right, i);
            		//deltaCurrentTime = System.currentTimeMillis() - currentTime;
            		DeltaCurrent = CHMIntegral.DeltaAbsolute(th, integral);
            		h = (right - left) / n;
            		n *= 2;
                	out.println(	i+ "&" + "$2^{"+i+"}$" + "&" + 
        							//deltaCurrentTime + "" + 
        							"$" + GetRound(integral, SignAfterCommon) + "$" + "&" +
        							"$" + GetRound(DeltaCurrent, SignAfterCommon) + "$" + "&" + 
        							"$" + GetRound(CHMIntegral.DeltaOtnosit(th, integral), SignAfterCommon) + "$" + "&" + 
        							"$" + GetRound(ThError(left, right, n, step), SignAfterCommon) + "$" + "&" +
        							"$" + GetRound(SpeedConvergence(DeltaPrevios, DeltaCurrent), SignAfterCommon) + "$" + "\\\\");
                	out.println("\\hline");
                	DeltaPrevios = DeltaCurrent;
                	System.out.print("!");
            	}
            	out.print("\\end{tabular}");
            	out.println();
            	out.println("========== Midle Integral ==========\\\\");
            	out.print("\\begin{tabular}{| c | c | c | c | c | c | c |}\n\\hline\n№&n&	I&$\\bigtriangleup I_n$&$\\delta I_n$&$R_n$&$\\frac{\\bigtriangleup I_{2j}}{\\bigtriangleup I_{2j+1}}$\\\\\\hline\n");
            	System.out.println("<");
            	DeltaPrevios = 1;
            	n = 2;
            	for (int i = 1; i <= degree; i++)
            	{
            		//currentTime = System.currentTimeMillis();
            		integral = CHMIntegral.CalcMidIntegral(left, right, i);
            		//deltaCurrentTime = System.currentTimeMillis() - currentTime;
            		DeltaCurrent = CHMIntegral.DeltaAbsolute(th, integral);
            		h = (right - left) / n;
            		n *= 2;
                	out.println(	i+ "&" + "$2^{"+i+"}$" + "&" + 
        							//deltaCurrentTime + "" + 
        							"$" + GetRound(integral, SignAfterCommon) + "$" + "&" +
        							"$" + GetRound(DeltaCurrent, SignAfterCommon) + "$" + "&" + 
        							"$" + GetRound(CHMIntegral.DeltaOtnosit(th, integral), SignAfterCommon) + "$" + "&" + 
        							"$" + GetRound(ThError(left, right, n, step), SignAfterCommon) + "$" + "&" +
        							"$" + GetRound(SpeedConvergence(DeltaPrevios, DeltaCurrent), SignAfterCommon) + "$" + "\\\\");
                	out.println("\\hline");
                	DeltaPrevios = DeltaCurrent;
                	System.out.print("!");
            	}
            	out.print("\\end{tabular}");
            	out.println();
            	out.println("========== Right Integral ==========\\\\");
            	out.print("\\begin{tabular}{| c | c | c | c | c | c | c |}\n\\hline\n№&n&	I&$\\bigtriangleup I_n$&$\\delta I_n$&$R_n$&$\\frac{\\bigtriangleup I_{2j}}{\\bigtriangleup I_{2j+1}}$\\\\\\hline\n");
            	System.out.println("<");
            	DeltaPrevios = 1;
            	n = 2;
            	for (int i = 1; i <= degree; i++)
            	{
            		//currentTime = System.currentTimeMillis();
            		integral = CHMIntegral.CalcRightIntegral(left, right, i);
            		//deltaCurrentTime = System.currentTimeMillis() - currentTime;
            		DeltaCurrent = CHMIntegral.DeltaAbsolute(th, integral);
            		h = (right - left) / n;
            		n *= 2;
                	out.println(	i+ "&" + "$2^{"+i+"}$" + "&" + 
        							//deltaCurrentTime + "" + 
        							"$" + GetRound(integral, SignAfterCommon) + "$" + "&" +
        							"$" + GetRound(DeltaCurrent, SignAfterCommon) + "$" + "&" + 
        							"$" + GetRound(CHMIntegral.DeltaOtnosit(th, integral), SignAfterCommon) + "$" + "&" + 
        							"$" + GetRound(ThError(left, right, n, step), SignAfterCommon) + "$" + "&" +
        							"$" + GetRound(SpeedConvergence(DeltaPrevios, DeltaCurrent), SignAfterCommon) + "$" + "\\\\");
                	out.println("\\hline");
                	DeltaPrevios = DeltaCurrent;
                	System.out.print("!");
            	}
            	out.print("\\end{tabular}");
            	out.println();
            	out.println("========== Trapeciya Integral ==========\\\\");
            	out.print("\\begin{tabular}{| c | c | c | c | c | c | c |}\n\\hline\n№&n&	I&$\\bigtriangleup I_n$&$\\delta I_n$&$R_n$&$\\frac{\\bigtriangleup I_{2j}}{\\bigtriangleup I_{2j+1}}$\\\\\\hline\n");
            	System.out.println("<");
            	DeltaPrevios = 1;
            	n = 2;
            	for (int i = 1; i <= degree; i++)
            	{
            		//currentTime = System.currentTimeMillis();
            		integral = CHMIntegral.CalcTrapIntegral(left, right, i);
            		//deltaCurrentTime = System.currentTimeMillis() - currentTime;
            		DeltaCurrent = CHMIntegral.DeltaAbsolute(th, integral);
            		h = (right - left) / n;
            		n *= 2;
                	out.println(	i+ "&" + "$2^{"+i+"}$" + "&" + 
        							//deltaCurrentTime + "" + 
        							"$" + GetRound(integral, SignAfterCommon) + "$" + "&" +
        							"$" + GetRound(DeltaCurrent, SignAfterCommon) + "$" + "&" + 
        							"$" + GetRound(CHMIntegral.DeltaOtnosit(th, integral), SignAfterCommon) + "$" + "&" + 
        							"$" + GetRound(ThError(left, right, n, step), SignAfterCommon) + "$" + "&" +
        							"$" + GetRound(SpeedConvergence(DeltaPrevios, DeltaCurrent), SignAfterCommon) + "$" + "\\\\");
                	out.println("\\hline");
                	DeltaPrevios = DeltaCurrent;
                	System.out.print("!");
            	}
            	out.print("\\end{tabular}");
            	out.println();
            	out.println("========== Simpson Integral ==========\\\\");
            	out.print("\\begin{tabular}{| c | c | c | c | c | c | c |}\n\\hline\n№&n&	I&$\\bigtriangleup I_n$&$\\delta I_n$&$R_n$&$\\frac{\\bigtriangleup I_{2j}}{\\bigtriangleup I_{2j+1}}$\\\\\\hline\n");
            	System.out.println("<");
            	DeltaPrevios = 1;
            	n = 2;
            	for (int i = 1; i <= degree; i++)
            	{
            		//currentTime = System.currentTimeMillis();
            		//deltaCurrentTime = System.currentTimeMillis() - currentTime;
            		h = (right - left) / n;
            		n *= 2;
            		integral = CHMIntegral.CalcSimpIntegral(left, right, i);
            		DeltaCurrent = CHMIntegral.DeltaAbsolute(th, integral);
                	out.println(	i+ "&" + "$2^{"+i+"}$" + "&" + 
        							//deltaCurrentTime + "" + 
        							"$" + GetRound(integral, SignAfterCommon) + "$" + "&" +
        							"$" + GetRound(DeltaCurrent, SignAfterCommon) + "$" + "&" + 
        							"$" + GetRound(CHMIntegral.DeltaOtnosit(th, integral), SignAfterCommon) + "$" + "&" + 
        							"$" + GetRound(ThError(left, right, i, step), SignAfterCommon) + "$" + "&" +
        							"$" + GetRound(SpeedConvergence(DeltaPrevios, DeltaCurrent), SignAfterCommon) + "$" + "\\\\");
                	out.println("\\hline");
                	DeltaPrevios = DeltaCurrent;
                	System.out.print("!");
            	}
            	out.print("\\end{tabular}");
            	out.println();
            	out.println("========== MonteCarlo Integral ==========\\\\");
            	out.print("\\begin{tabular}{| c | c | c | c | c | c | c |}\n\\hline\n№&n&	I&$\\bigtriangleup I_n$&$\\delta I_n$&$R_n$&$\\frac{\\bigtriangleup I_{2j}}{\\bigtriangleup I_{2j+1}}$\\\\\\hline\n");
            	System.out.println("<");
            	DeltaPrevios = 1;
            	n = 2;
            	for (int i = 1; i <= degree; i++)
            	{
            		//currentTime = System.currentTimeMillis();
            		integral = CHMIntegral.CalcMonteIntegral(left, right, i);
            		//deltaCurrentTime = System.currentTimeMillis() - currentTime;
            		DeltaCurrent = CHMIntegral.DeltaAbsolute(th, integral);
            		h = (right - left) / n;
            		n *= 2;
                	out.println(	i+ "&" + "$2^{"+i+"}$" + "&" + 
        							//deltaCurrentTime + "" + 
        							"$" + GetRound(integral, SignAfterCommon) + "$" + "&" +
        							"$" + GetRound(DeltaCurrent, SignAfterCommon) + "$" + "&" + 
        							"$" + GetRound(CHMIntegral.DeltaOtnosit(th, integral), SignAfterCommon) + "$" + "&" + 
        							"$" + GetRound(ThError(left, right, n, step), SignAfterCommon) + "$" + "&" +
        							"$" + GetRound(SpeedConvergence(DeltaPrevios, DeltaCurrent), SignAfterCommon) + "$" + "\\\\");
                	out.println("\\hline");
                	DeltaPrevios = DeltaCurrent;
                	System.out.print("!");
            	}
            	out.print("\\end{tabular}");
            	out.println("\\newpage");
            	out.println("========== n = 10000 ==========\\\\");
            	out.print("\\begin{tabular}{| c | c | c | c | c |}\n\\hline\nМетод &I &$\\bigtriangleup I_n$  & $\\delta I_n$   & $R_n$  \\\\ \\hline\n");
            	System.out.println("<");
            	h = (right - left)/((double)10000);
        		integral = CHMIntegral.CalcLeftIntegral(left, right, -1);
            	out.println(	"Left"+ "&" + 
    							//deltaCurrentTime + "" + 
    							"$" + GetRound(integral, SignAfterCommon) + "$" + "&" +
    							"$" + GetRound(CHMIntegral.DeltaAbsolute(th, integral), SignAfterCommon) + "$" + "&" + 
    							"$" + GetRound(CHMIntegral.DeltaOtnosit(th, integral), SignAfterCommon) + "$" + "&" + 
    							"$" + "\\\\");
            	out.println("\\hline");
        		integral = CHMIntegral.CalcMidIntegral(left, right, -1);
            	out.println(	"Midl"+ "&" + 
    							//deltaCurrentTime + "" + 
    							"$" + GetRound(integral, SignAfterCommon) + "$" + "&" +
    							"$" + GetRound(CHMIntegral.DeltaAbsolute(th, integral), SignAfterCommon) + "$" + "&" + 
    							"$" + GetRound(CHMIntegral.DeltaOtnosit(th, integral), SignAfterCommon) + "$" + "&" + 
    							"$" + "\\\\");
            	out.println("\\hline");
        		integral = CHMIntegral.CalcRightIntegral(left, right, -1);
            	out.println(	"Right"+ "&" + 
    							//deltaCurrentTime + "" + 
    							"$" + GetRound(integral, SignAfterCommon) + "$" + "&" +
    							"$" + GetRound(CHMIntegral.DeltaAbsolute(th, integral), SignAfterCommon) + "$" + "&" + 
    							"$" + GetRound(CHMIntegral.DeltaOtnosit(th, integral), SignAfterCommon) + "$" + "&" + 
    							"$" + "\\\\");
            	out.println("\\hline");
        		integral = CHMIntegral.CalcTrapIntegral(left, right, -1);
            	out.println(	"Trap"+ "&" + 
    							//deltaCurrentTime + "" + 
    							"$" + GetRound(integral, SignAfterCommon) + "$" + "&" +
    							"$" + GetRound(CHMIntegral.DeltaAbsolute(th, integral), SignAfterCommon) + "$" + "&" + 
    							"$" + GetRound(CHMIntegral.DeltaOtnosit(th, integral), SignAfterCommon) + "$" + "&" + 
    							"$" + "\\\\");
            	out.println("\\hline");
        		integral = CHMIntegral.CalcSimpIntegral(left, right, -1);
            	out.println(	"Simp"+ "&" + 
    							//deltaCurrentTime + "" + 
    							"$" + GetRound(integral, SignAfterCommon) + "$" + "&" +
    							"$" + GetRound(CHMIntegral.DeltaAbsolute(th, integral), SignAfterCommon) + "$" + "&" + 
    							"$" + GetRound(CHMIntegral.DeltaOtnosit(th, integral), SignAfterCommon) + "$" + "&" + 
    							"$" + "\\\\");
            	out.println("\\hline");
        		integral = CHMIntegral.CalcMonteIntegral(left, right, -1);
            	out.println(	"Monte"+ "&" + 
    							//deltaCurrentTime + "" + 
    							"$" + GetRound(integral, SignAfterCommon) + "$" + "&" +
    							"$" + GetRound(CHMIntegral.DeltaAbsolute(th, integral), SignAfterCommon) + "$" + "&" + 
    							"$" + GetRound(CHMIntegral.DeltaOtnosit(th, integral), SignAfterCommon) + "$" + "&" + 
    							"$" + "\\\\");
            	out.println("\\hline");
            	out.print("\\end{tabular}");
            	out.println();
            	out.println("========== Simpson Integral ==========\\\\");
            	System.out.println("<");
            	DeltaPrevios = 1;
            	n = 2;
            	for (int i = 1; i <= degree; i++)
            	{
            		h = (right - left) / n;
            		n *= 2;
            		integral = CHMIntegral.CalcSimpIntegral(left, right, i);
            		DeltaCurrent = CHMIntegral.DeltaAbsolute(th, integral);
                	out.println(
        							DeltaCurrent + " " +
        							ThError(left, right, i, step));
                	System.out.print("!");
            	}
            	out.println();
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
