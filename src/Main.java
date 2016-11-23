import java.awt.Color;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import CHM.AdamsaMethod4;
import CHM.EulersMethod;
import CHM.ImproveEulersMethod;
import CHM.RungeKutta4;
import Graph.Function;
import Graph.GraphFrame;
import MathPars.MatchParser;


public class Main {
    Main()
    {
    }
    public static void main(String[] args){
    	long last_time = System.currentTimeMillis();
    	GraphFrame frame;
    	GraphFrame frame1 = new GraphFrame("Frame", 800, 600);
    	frame = new GraphFrame("Frame", 800, 600);
    	frame.SetDrawSpline(true);
    	frame.requestFocusInWindow();
    	frame.setSize(800, 600);
    	AdamsaMethod4 euler = new AdamsaMethod4("sin(x)", -Math.PI, Math.PI, 0.1, 0);
    	//euler.printSolve(5);
    	//euler.exportSolve(5);
    	euler.paintSolve(1.55, frame);
    	frame.AddFunc("0-cos(x)", -10, 10, Color.BLUE, "-cos(x)");
    	Function errfunc = new Function(euler.getSize(), euler.getX(), euler.getError("0-cos(x)"));
    	frame.AddFunc(errfunc, Color.RED, "Error");
    	for (int i = 0; i < errfunc.size; i++)
    	{
    		errfunc.Y[i] *= 10000000;
    		System.out.println(errfunc.Y[i]);
    	}
    	errfunc = new Function(errfunc.size, errfunc.X, errfunc.Y);
    	frame.AddFunc(errfunc, Color.RED, "Error*10000");
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
