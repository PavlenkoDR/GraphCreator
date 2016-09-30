import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Function {
	public double X[];
	public double Y[];
	public int size;
	public InterpMatrix interp;
	public Approx approx;
	public Spline spline;
    int nApprox = 2;
	public boolean GetPointsFlag = false;


    public void quickSort() {
        doSort(0, size-1);
    }

    private void doSort(int start, int end) {
        if (start >= end)
            return;
        int i = start, j = end;
        int cur = i - (i - j) / 2;
        while (i < j) {
            while (i < cur && (X[i] <= X[cur])) {
                i++;
            }
            while (j > cur && (X[cur] <= X[j])) {
                j--;
            }
            if (i < j) {
                double temp = X[i];
                double temp1 = Y[i];
                X[i] = X[j];
                Y[i] = Y[j];
                X[j] = temp;
                Y[j] = temp1;
                if (i == cur)
                    cur = j;
                else if (j == cur)
                    cur = i;
            }
        }
        doSort(start, cur);
        doSort(cur+1, end);
    }
	public Function(int _size, double[] _X, double[] _F)
	{
    	size = _size;
    	X = _X;
    	Y = _F;
	    nApprox = 2;
    	GetPointsFlag = true;
    	quickSort();
		interp = new InterpMatrix(size, X, Y);
		approx = new Approx(size, X, Y);
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
    	quickSort();
		interp = new InterpMatrix(size, X, Y);
		approx = new Approx(size, X, Y);
		spline = new Spline(size, X, Y);
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
    	spline = func.spline;
	}
	
	public Function(){
    	GetPointsFlag = false;
    	size = 0;
    	nApprox = 2;
	}
	
}
