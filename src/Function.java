import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Function {
	public double X[];
	public double Y[];
	public int size;
	public InterpMatrix interp;
	public Approx approx;
    int nApprox = 2;
	public boolean GetPointsFlag = false;
	
	public Function(int _size, double[] _X, double[] _F)
	{
		interp = new InterpMatrix(_size, _X, _F);
		approx = new Approx(_size, _X, _F);
    	size = _size;
    	X = _X;
    	Y = _F;
	    nApprox = 2;
    	GetPointsFlag = true;
	}
	
	public Function(File f){
		Scanner in;
		try {
			in = new Scanner(f);
			size = in.nextInt();
			X = new double[size];
			Y = new double[size];
			for (int i = 0; i < size; i++)
				X[i] = in.nextDouble();
			for (int i = 0; i < size; i++)
				Y[i] = in.nextDouble();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		interp = new InterpMatrix(size, X, Y);
		approx = new Approx(size, X, Y);
	    nApprox = 2;
    	GetPointsFlag = true;
	};

	public Function(Function func){
    	GetPointsFlag = func.GetPointsFlag;
    	size = func.size;
    	nApprox = func.nApprox;
    	X = func.X;
    	Y = func.Y;
    	interp = func.interp;
    	approx = func.approx;
	}
	
	public Function(){
    	GetPointsFlag = false;
    	size = 0;
    	nApprox = 2;
	}
	
}
