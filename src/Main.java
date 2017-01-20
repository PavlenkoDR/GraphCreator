import java.awt.Color;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    	frame = new GraphFrame("Frame", 800, 600);
    	frame.SetDrawLine(true);
    	frame.requestFocusInWindow();
    	frame.setSize(800, 600);
    	
    	/*
    	//==================================== EulersMethod ====================================
    	EulersMethod euler = new EulersMethod("sin(x)", -Math.PI, Math.PI, 40, 0);
    	euler.paintSolve(0, frame, "EulersMethod", Color.GREEN);
    	euler.paintError("0-cos(x)", 1, frame, "EEM", Color.RED);
    	euler.paintError("0-cos(x)", 10, frame, "ESEM", Color.RED);
    	
    	//*/
    	/*
    	
    	//==================================== ImproveEulersMethod ====================================
    	ImproveEulersMethod ieuler = new ImproveEulersMethod("sin(x)", -Math.PI, Math.PI, 40, 0);
    	ieuler.paintSolve(0, frame, "ImproveEulersMethod", Color.GREEN);
    	ieuler.paintError("0-cos(x)", 1, frame, "ErrorImproveEulersMethod", Color.RED);
    	ieuler.paintError("0-cos(x)", 2000, frame, "ErrorScaleImproveEulersMethod", Color.RED);
    	
    	//*/
    	/*
    	
    	//==================================== AdamsaMethod4 ====================================
    	AdamsaMethod4 adam4 = new AdamsaMethod4("sin(x)", -Math.PI, Math.PI, 40, 0);
    	adam4.paintSolve(0, frame, "AdamsaMethod4", Color.GREEN);
    	adam4.paintError("0-cos(x)", 1, frame, "ErrorAdamsaMethod4", Color.RED);
    	adam4.paintError("0-cos(x)", 10000, frame, "ErrorScaleAdamsaMethod4", Color.RED);
    	
		//*/
    	///*
    	
    	//==================================== RungeKutta4 ====================================
    	RungeKutta4 rung4 = new RungeKutta4("sin(x)", -Math.PI, Math.PI, 40, 0);
    	rung4.paintSolve(0, frame, "RungeKutta4", Color.GREEN);
    	rung4.paintError("0-cos(x)", 1, frame, "ErrorRungeKutta4", Color.RED);
    	rung4.paintError("0-cos(x)", 10000000, frame, "ErrorScaleRungeKutta4", Color.RED);
    	//System.out.println(rung4.getAverageError("0-cos(x)"));
    	

    	//==================================== ExplicitDfference ==============================
    	ExplicitDfference test = new ExplicitDfference("x*x", -10, 10, 0, 100, 2000);
    	test.exportSolve(4);
    	GraphFrame frame1;
    	//GraphFrame frame1 = new GraphFrame("Frame", 800, 600);
    	frame1 = new GraphFrame("Frame", 800, 600);
    	frame1.SetDrawLine(true);
    	frame1.requestFocusInWindow();
    	frame1.setSize(800, 600);
    	frame1.AddFunc(new Function(test.getSize(), test.getX(), 
				Function.returnInvers(test.getU()[test.getU().length - 1])), 
				new Color((int)(256*Math.random()), 
				(int)(256*Math.random()), 
				(int)(256*Math.random())), 
				"level max");
    	frame1.AddFunc(new Function(test.getSize(), test.getX(), 
				Function.returnInvers(test.getAverageU())), 
				new Color((int)(256*Math.random()), 
				(int)(256*Math.random()), 
				(int)(256*Math.random())), 
				"average max");
    	frame1.AddFunc("x*x*x/3", -10, 10, Color.BLUE, "-cos(x)");
    	//test.paintError("0-cos(x)", 1, frame1, "ErrorRungeKutta4", Color.RED);
    	//*
    	for (int i = 0; i < test.getU().length; i++)
    	{
    		if (i % 250 == 0)
    			frame1.AddFunc(new Function(test.getSize(), test.getX(), 
    					Function.returnInvers(test.getU()[i])), 
    					new Color((int)(256*Math.random()), 
    					(int)(256*Math.random()), 
    					(int)(256*Math.random())), 
    					"level " + i);
    		//System.out.println(i);
    	}
    	//*/
    	
    	frame.AddFunc("0-cos(x)", -10, 10, Color.BLUE, "-cos(x)");
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
