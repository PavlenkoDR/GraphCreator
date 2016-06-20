import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;


public class CHMSqrt {
	
	static double Dif1(double x)
	{
		return 2 + Math.sin(x);
	}
	
	static double Dif2(double x)
	{
		return Math.cos(x);
	}
	
	static double NewTone(double a, double b, double e)
	{
		double result = 999999999, tmp = 0;
		if (a > b) return 0;
		if (CHMFunction.ReturnY(a)*Dif2(a) >= 0)
			result = a;
		else if (CHMFunction.ReturnY(b)*Dif2(b) >= 0)
			result = b;
		else
			return 0;

    	File fout = new File("NewTone.txt"); 
    	double step = 1000;
        try {
            //проверяем, что если файл не существует то создаем его
            if(!fout.exists()){
            	fout.createNewFile();
            }
     
            //PrintWriter обеспечит возможности записи в файл
            PrintWriter out = new PrintWriter(fout.getAbsoluteFile());
            //int i = 1;
            out.println("\\hline");
            try {
        		while (true)
        		{
        			tmp = result;
        			result = -CHMFunction.ReturnY(result)/Dif1(result) + result;
        			//out.println("$"+i + "$ & $"+result+"$\\\\ \\hline");
        			out.println(result);
        			//i++;
        			if (Math.abs(result - tmp) < e)
        				break;
        		}
            	
            } finally {
                //После чего мы должны закрыть файл
                //Иначе файл не запишется
                out.close();
            }
        } catch(IOException e1) {
            throw new RuntimeException(e1);
        }
		return result;
	}
	
	static double Hord(double a, double b, double e)
	{
		double result = 999999999, tmp = 0, x0;
		if (a > b) return 0;
		if (CHMFunction.ReturnY(a)*CHMFunction.ReturnY(b) > 0) return 0;
		if (CHMFunction.ReturnY(a)*Dif2(a) >= 0)
		{
			result = a;
			x0 = b;
		}
		else if (CHMFunction.ReturnY(b)*Dif2(b) >= 0)
		{
			result = b;
			x0 = a;
		}
		else
			return 0;
    	File fout = new File("HordTone.txt"); 
        try {
            //проверяем, что если файл не существует то создаем его
            if(!fout.exists()){
            	fout.createNewFile();
            }
     
            //PrintWriter обеспечит возможности записи в файл
            PrintWriter out = new PrintWriter(fout.getAbsoluteFile());
            //int i = 1;
            try {
        		while (true)
        		{
        			tmp = result;
        			result = result - (CHMFunction.ReturnY(result)*(result - x0))/(CHMFunction.ReturnY(result) - CHMFunction.ReturnY(x0));
        			//out.println("$"+i + "$ & $"+result+"$\\\\ \\hline");
        			out.println(result);
        			//i++;
        			if (Math.abs(result - tmp) < e)
        				break;
        		}
            	
            } finally {
                //После чего мы должны закрыть файл
                //Иначе файл не запишется
                out.close();
            }
        } catch(IOException e1) {
            throw new RuntimeException(e1);
        }
		return result;
	}
	
	static double Dihot(double a, double b, double e)
	{
		double result = 999999999, tmp = 0;
		if (a > b) return 0;
		if (CHMFunction.ReturnY(a)*CHMFunction.ReturnY(b) > 0) return 0;
		double left = a, right = b;
    	File fout = new File("DihotTone.txt"); 
        try {
            //проверяем, что если файл не существует то создаем его
            if(!fout.exists()){
            	fout.createNewFile();
            }
     
            //PrintWriter обеспечит возможности записи в файл
            PrintWriter out = new PrintWriter(fout.getAbsoluteFile());
            //int i = 1;
            try {
        		while (true)
        		{
        			tmp = result;
        			result = left + (right-left)/2;
        			if (CHMFunction.ReturnY(result)*CHMFunction.ReturnY(left) < 0)
        				right = result;
        			else if (CHMFunction.ReturnY(result)*CHMFunction.ReturnY(right) < 0)
        				left = result;
        			else
        				break;
        			//out.println("$"+i + "$ & $"+result+"$\\\\ \\hline");
        			//i++;
        			out.println(result);
        			if (Math.abs(result - tmp) < e)
        				break;
        		}
            	
            } finally {
                //После чего мы должны закрыть файл
                //Иначе файл не запишется
                out.close();
            }
        } catch(IOException e1) {
            throw new RuntimeException(e1);
        }
		return result;
	}
	
}
