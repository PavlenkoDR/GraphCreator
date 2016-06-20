<<<<<<< HEAD
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

=======
>>>>>>> dbe462ef1d9d99496edea6e1da8ec043fb27e86c

public class Main {
	GraphFrame frame;
    public static void main(String[] args){
    	new Main();
<<<<<<< HEAD
    	System.out.println("Done!");
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
=======
>>>>>>> dbe462ef1d9d99496edea6e1da8ec043fb27e86c
    }
    Main()
    {
    	//CHMFure.GetResult(1.5);
<<<<<<< HEAD
    	//CHMSpline.CreateDots();
    	//CHMSpline.GetTableOutPut();
    	//CHMFure.GetTableOutPut();
    	//CHMFure.GetTableDotsOutPut();
    	//CHMIntegral.GetTable(0.124062);
    	/*
    	CHMFure.CreateDots();
    	double a = -2, b = 2, eps = 0.000000000000001;
    	long currentTime = 0, deltaCurrentTime = 0;
		currentTime = System.currentTimeMillis();
    	System.out.println(CHMSqrt.NewTone(a, b, eps));
		deltaCurrentTime = System.currentTimeMillis() - currentTime;
		System.out.println("Time: " + deltaCurrentTime);
		
		currentTime = System.currentTimeMillis();
    	System.out.println(CHMSqrt.Hord(a, b, eps));
		deltaCurrentTime = System.currentTimeMillis() - currentTime;
		System.out.println("Time: " + deltaCurrentTime);
		
		currentTime = System.currentTimeMillis();
    	System.out.println(CHMSqrt.Dihot(a, b, eps));
		deltaCurrentTime = System.currentTimeMillis() - currentTime;
		System.out.println("Time: " + deltaCurrentTime);

		replace("NewTone.txt");
		replace("DihotTone.txt");
		replace("HordTone.txt");
		//replace("FureTable.txt");
		replace("FureTableDot.txt");
		replace("IntegralTable.txt");
		replace("InterpolTable.txt");
		*/
		//replace("SplineTable.txt");
    	frame = new GraphFrame("Окошко", 600, 600);
    	frame.DrawPolynom();
    	frame.DrawApprox();
    	//frame.DrawFur();
    	//frame.DrawSpline();
=======
    	//CHMFure.CreateDots();
    	CHMSpline.CreateDots();
    	System.out.println(CHMSqrt.NewTone(0.5, 1, 0.0001));
    	System.out.println(CHMSqrt.Hord(0.5, 1, 0.0001));
    	System.out.println(CHMSqrt.Dihot(0.5, 1, 0.0001));
    	frame = new GraphFrame("Окошко", 600, 600);
    	frame.DrawPolynom();
    	frame.DrawApprox();
    	frame.DrawSpline();
>>>>>>> dbe462ef1d9d99496edea6e1da8ec043fb27e86c
    	//CHMIntegral.GetTable(-0.0066415);
    }
}
