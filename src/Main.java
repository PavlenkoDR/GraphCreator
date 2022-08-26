import java.awt.Color;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import org.w3c.dom.css.RGBColor;

import CHM.*;
import ChmFunctions.Function;
import Graph.GraphFrame;


public class Main {
    Main()
    {
    }
    public static void main(String[] args) {

		// test 1
//		double tmp = DifferenceScheme.matrixDeterminant(new double[][]{{-3, 2, -5}, {10, 4, 7}, {6, -8, -9}});
//		double[] KramerSLAE1 = DifferenceScheme.KramerSLAE(new double[][]{{2, 1, -1}, {3, 2, 2}, {1, 0, 1}}, new double[]{3, -7, -2});
//		double[] sweepSLAE1 = DifferenceScheme.sweepSLAE(new double[][]{{2, 1, -1}, {3, 2, 2}, {1, 0, 1}}, new double[]{3, -7, -2});

		//

    	long last_time = System.currentTimeMillis();
    	GraphFrame frame;
    	frame = new GraphFrame("Frame", 1200, 800);
    	frame.SetDrawLine(true);
    	frame.requestFocusInWindow();
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	int n = 50;
    	double left = 0, right = 5, y0 = 0.8;
    	String func = "sin(x) - 2*y"; // y' = sin(x) - 2*y
    	//int n = 10;
    	//double left = 0, right = 1, y0 = -1;
    	//String func = "sin(2*x) + y*cos(x)";
		/*
		BaseDifferential.FunctionProvider provider = new BaseDifferential.FunctionProvider() {
			@Override
			public double function(double x) {
				return Math.pow(Math.E, -2*x)+2*Math.sin(x)/5-Math.cos(x)/5; // https://www.wolframalpha.com/input?i=y%27+%3D+sin%28x%29+-+2*y
			}
		};
    	double tmppp[][] = BaseDifferential.returnfunctionAnswer(provider, left, right, n);
    	frame.AddFunc(n, tmppp[0], tmppp[1], Color.GREEN, "Answer");
		//*/
		/*
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
    	//*/

		List<GraphFrame> frames = new ArrayList<>();

		DifferenceScheme scheme = new DifferenceScheme(0.1, 10000, 0.0, 1.0, 30);
		int animationFrames = 10;
		int animationDelay = 200;


		for (int i = 0; i < scheme.answer.t_tau.length - 1; ++i) {
			if ((i - 1) % (scheme.answer.t_tau.length / animationFrames) != 0){
				continue;
			}
			GraphFrame frame1;
			frame1 = new GraphFrame("Frame", 1900, 1200);
			frame1.SetDrawLine(true);

			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			frame1.AddFunc(new Function(scheme.answer.x_h.length, scheme.answer.x_h, scheme.answer.u[i]), Color.BLACK, "u, t = " + scheme.answer.t_tau[i]);
			frame1.AddFunc(new Function(scheme.answer.x_h.length, scheme.answer.x_h, scheme.answer.error_absolute[i]), Color.BLUE, "u, t = " + scheme.answer.t_tau[i] + ",  error absolute");
			frame1.AddFunc(new Function(scheme.answer.x_h.length, scheme.answer.x_h, scheme.answer.error_relative[i]), Color.RED, "u, t = " + scheme.answer.t_tau[i] + ",  error relative");

			//frame.Graph.FunctionList.get(frame.Graph.FunctionList.size() - 1).getL().Enable = false;
			frames.add(frame1);
		}

		Runnable runnable = () -> {
			try {
				//TimeUnit.MILLISECONDS.sleep(5000);
				while (true) {
					for (int i = 0; i < frames.size(); ++i){

						frames.get(i).requestFocus();
						TimeUnit.MILLISECONDS.sleep(animationDelay);
					}
				}
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		};

		Thread thread = new Thread(runnable);
		thread.start();

		/*
    	//==================================== EulersMethod ====================================
    	EulersMethod euler = new EulersMethod(func, left, right, n, y0);
    	//euler.paintSolve(1, frame, "EulersMethod", new Color((int)(256*Math.random()), (int)(256*Math.random()), (int)(256*Math.random())));
    	euler.paintSolve(0, frame, "EulersMethod", Color.BLUE);
    	euler.paintError(provider, 1, frame, "E1EM", Color.RED);
    	euler.paintError(provider, 10, frame, "E10EM", Color.RED);
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
		/*
		//==================================== ExplicitDfference ==============================
		ExplicitDfference test = new ExplicitDfference("sin(x)-2*y", -10, 10, 0, 200, 200);
		test.exportSolve(4);
		//GraphFrame frame1;
		//GraphFrame frame1 = new GraphFrame("Frame", 800, 600);
		//frame1 = new GraphFrame("Frame", 1200, 800);
		//frame1.SetDrawLine(true);
		//frame1.requestFocusInWindow();
		frame.AddFunc(new Function(test.getSize(), test.getX(),
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
		//frame.AddFunc("0-cos(x)", -10, 10, Color.BLUE, "-cos(x)");
		//test.paintError("0-cos(x)", 1, frame, "ErrorExplicitDfference", Color.RED);
		//test.paintError("0-cos(x)", 10, frame, "ErrorExplicitDfference", Color.CYAN);
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
		/*
		//==================================== ExplicitDfference ==============================
		IxplicitDfference testIxplicitDfference = new IxplicitDfference("sin(x)-2*y", -10, 10, 0, 200, 200);
		testIxplicitDfference.exportSolve(4);
		//GraphFrame frame1;
		//GraphFrame frame1 = new GraphFrame("Frame", 800, 600);
		//frame1 = new GraphFrame("Frame", 1200, 800);
		//frame1.SetDrawLine(true);
		//frame1.requestFocusInWindow();
		frame.AddFunc(new Function(testIxplicitDfference.getSize(), testIxplicitDfference.getX(),
						Function.returnInvers(testIxplicitDfference.getU()[testIxplicitDfference.getU().length - 1])),
				new Color((int)(256*Math.random()),
						(int)(256*Math.random()),
						(int)(256*Math.random())),
				"level max");
		frame.AddFunc("0-cos(x)", -10, 10, Color.BLUE, "-cos(x)");
		testIxplicitDfference.paintError("0-cos(x)", 1, frame, "ErrorExplicitDfference", Color.RED);
		testIxplicitDfference.paintError("0-cos(x)", 10, frame, "ErrorExplicitDfference", Color.CYAN);

    	//frame.AddFunc("0-cos(x)", -10, 10, Color.BLUE, "-cos(x)");
    	//frame.AddFunc("sin(x)", -10, 10, Color.BLUE, "sin(x)");
    	//frame.AddFunc("sin(x)", -10, 10, Color.CYAN, "sin(x)");
		//*/
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
