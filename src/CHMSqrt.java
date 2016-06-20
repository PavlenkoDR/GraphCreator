<<<<<<< HEAD
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;


public class CHMSqrt {
	
	static double Dif1(double x)
	{
		return 2 + Math.sin(x);
=======

public class CHMSqrt {
	
	static double Func(double x)
	{
		return Math.pow(x, 3) - Math.cos(2*x);
	}
	
	static double Dif1(double x)
	{
		return 3*Math.pow(x, 2) + 2*Math.sin(2*x);
>>>>>>> dbe462ef1d9d99496edea6e1da8ec043fb27e86c
	}
	
	static double Dif2(double x)
	{
<<<<<<< HEAD
		return Math.cos(x);
=======
		return 6*x + 4*Math.cos(2*x);
>>>>>>> dbe462ef1d9d99496edea6e1da8ec043fb27e86c
	}
	
	static double NewTone(double a, double b, double e)
	{
		double result = 999999999, tmp = 0;
		if (a > b) return 0;
<<<<<<< HEAD
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
=======
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
>>>>>>> dbe462ef1d9d99496edea6e1da8ec043fb27e86c
		return result;
	}
	
	static double Hord(double a, double b, double e)
	{
		double result = 999999999, tmp = 0, x0;
		if (a > b) return 0;
<<<<<<< HEAD
		if (CHMFunction.ReturnY(a)*CHMFunction.ReturnY(b) > 0) return 0;
		if (CHMFunction.ReturnY(a)*Dif2(a) >= 0)
=======
		if (Func(a)*Func(b) > 0) return 0;
		if (Func(a)*Dif2(a) >= 0)
>>>>>>> dbe462ef1d9d99496edea6e1da8ec043fb27e86c
		{
			result = a;
			x0 = b;
		}
<<<<<<< HEAD
		else if (CHMFunction.ReturnY(b)*Dif2(b) >= 0)
=======
		else if (Func(b)*Dif2(b) >= 0)
>>>>>>> dbe462ef1d9d99496edea6e1da8ec043fb27e86c
		{
			result = b;
			x0 = a;
		}
		else
			return 0;
<<<<<<< HEAD
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
=======
		while (true)
		{
			tmp = result;
			result = result - (Func(result)*(result - x0))/(Func(result) - Func(x0));
			if (Math.abs(result - tmp) < e)
				break;
		}
>>>>>>> dbe462ef1d9d99496edea6e1da8ec043fb27e86c
		return result;
	}
	
	static double Dihot(double a, double b, double e)
	{
		double result = 999999999, tmp = 0;
		if (a > b) return 0;
<<<<<<< HEAD
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
=======
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
>>>>>>> dbe462ef1d9d99496edea6e1da8ec043fb27e86c
		return result;
	}
	
}
