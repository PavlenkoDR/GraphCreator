import java.awt.Color;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFrame;

import org.w3c.dom.css.RGBColor;

import CHM.*;
import ChmFunctions.Function;
import Graph.GraphFrame;


public class Main {
    Main()
    {
    }
    public static void main(String[] args){
    	long last_time = System.currentTimeMillis();
    	GraphFrame frame;
    	//GraphFrame frame1 = new GraphFrame("Frame", 800, 600);
    	frame = new GraphFrame("Frame", 1200, 800);
    	frame.SetDrawLine(true);
    	frame.requestFocusInWindow();
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	int n = 30;
    	double left = 0, right = 1, y0 = 0.8;
    	String func = "sin(x) - 2*y";
    	//int n = 10;
    	//double left = 0, right = 1, y0 = -1;
    	//String func = "sin(2*x) + y*cos(x)";
    	double tmppp[][] = BaseDifferential.returnfunctionAnswer(left, right, n, frame);
    	//frame.AddFunc(n, tmppp[0], tmppp[1], Color.GREEN, "Answer");
    	RungeKutta4 euler1;
    	double errN[] = new double[n], err[], errX[] = new double[n];
    	/*
    	for (int i = 0; i < n; i++)
    	{
    		errX[i] = i;
    		euler1 = new RungeKutta4(func, left, right, i+4, y0);
    		err = euler1.getError();
    		errN[i] = 0;
    		for (int j = 1; j < err.length-1; j++)
    			errN[i]+=Math.abs(err[j]);
    		errN[i]*=10000;
    	}
    	frame.AddFunc(n, errX, errN, Color.RED, "Awerage");
    	*/
    	//*
    	//==================================== EulersMethod ====================================
    	EulersMethod euler = new EulersMethod(func, left, right, n, y0);
    	//euler.paintSolve(1, frame, "EulersMethod", new Color((int)(256*Math.random()), (int)(256*Math.random()), (int)(256*Math.random())));
    	euler.paintSolve(0, frame, "EulersMethod", Color.BLUE);
    	euler.paintError(1, frame, "E1EM", Color.RED);
    	euler.paintError(10, frame, "E10EM", Color.RED);
    	euler.exportSolve(8);
    	//*/
    	/*
    	//==================================== ImproveEulersMethod ====================================
    	ImproveEulersMethod ieuler = new ImproveEulersMethod(func, left, right, n, y0);
    	//ieuler.paintSolve(2, frame, "ImproveEulersMethod", new Color((int)(256*Math.random()), (int)(256*Math.random()), (int)(256*Math.random())));
    	ieuler.paintSolve(0, frame, "ImproveEulersMethod", Color.BLUE);
    	ieuler.paintError(1, frame, "E1IEM", Color.RED);
    	ieuler.paintError(100, frame, "E10IEM", Color.RED);
    	ieuler.exportSolve(8);
    	//*/
    	/*
    	
    	//==================================== AdamsaMethod4 ====================================
    	AdamsaMethod4 adam4 = new AdamsaMethod4(func, left, right, n, y0);
    	//adam4.paintSolve(3, frame, "AdamsaMethod4", new Color((int)(256*Math.random()), (int)(256*Math.random()), (int)(256*Math.random())));
    	adam4.paintSolve(0, frame, "AdamsaMethod4", Color.BLUE);
    	adam4.paintError(1, frame, "E1A4", Color.RED);
    	adam4.paintError(10000, frame, "E10A4", Color.RED);
    	adam4.exportSolve(8);
		//*/
    	/*
    	
    	//==================================== RungeKutta4 ====================================
    	RungeKutta4 rung4 = new RungeKutta4(func, left, right, n, y0);
    	//rung4.paintSolve(4, frame, "RungeKutta4", new Color((int)(256*Math.random()), (int)(256*Math.random()), (int)(256*Math.random())));
    	rung4.paintSolve(0, frame, "RungeKutta4", Color.MAGENTA);
    	rung4.paintError(1, frame, "E1RG4", Color.RED);
    	rung4.paintError(1000000, frame, "E10RG4", Color.RED);
    	rung4.exportSolve(8);
    	rung4.exportFunctionAnswer(8);
    	//*/
    	//*
    	//==================================== ExplicitDfference ==============================
    	ExplicitDfference test = new ExplicitDfference("sin(x)-2*y", -10, 10, 0, 200, 200);
    	test.exportSolve(4);
    	GraphFrame frame1;
    	//GraphFrame frame1 = new GraphFrame("Frame", 800, 600);
    	frame1 = new GraphFrame("Frame", 1200, 800);
    	frame1.SetDrawLine(true);
    	frame1.requestFocusInWindow();
    	frame1.AddFunc(new Function(test.getSize(), test.getX(), 
				Function.returnInvers(test.getU()[test.getU().length - 1])), 
				new Color((int)(256*Math.random()), 
				(int)(256*Math.random()), 
				(int)(256*Math.random())), 
				"level max");
    	/*
    	frame1.AddFunc(new Function(test.getSize(), test.getX(), 
				Function.returnInvers(test.getAverageU())), 
				new Color((int)(256*Math.random()), 
				(int)(256*Math.random()), 
				(int)(256*Math.random())), 
				"average max");
				*/
    	frame1.AddFunc("0-cos(x)", -10, 10, Color.BLUE, "-cos(x)");
    	test.paintError("0-cos(x)", 1, frame1, "ErrorExplicitDfference", Color.RED);
    	test.paintError("0-cos(x)", 10, frame1, "ErrorExplicitDfference", Color.CYAN);
    	/*
    	for (int i = 0; i < test.getU().length; i++)
    	{
    		if ((i+1) % 50 == 0)
    			frame1.AddFunc(new Function(test.getSize(), test.getX(), 
    					Function.returnInvers(test.getU()[i])), 
    					new Color((int)(256*Math.random()), 
    					(int)(256*Math.random()), 
    					(int)(256*Math.random())), 
    					"level " + i);
    		//System.out.println(i);
    	}
    	//*/

    	//frame.AddFunc("0-cos(x)", -10, 10, Color.BLUE, "-cos(x)");
    	//frame.AddFunc("sin(x)", -10, 10, Color.BLUE, "sin(x)");
    	//frame.AddFunc("sin(x)", -10, 10, Color.CYAN, "sin(x)");
    	new Main();
    	System.out.println("Done! " + (System.currentTimeMillis() - last_time));
    }
    public static void replace(String fileName) {
        String search = ".";
        String replace = ",";
        Charset charset = StandardCharsets.UTF_8;
        Path path = Paths.get(fileName);
        try {
	        Files.write(path,
	            new String(Files.readAllBytes(path), charset).replace(search, replace)
	                .getBytes(charset));
        } catch (IOException e) {
	        e.printStackTrace();
        }
        
    }
}
