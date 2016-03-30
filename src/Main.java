//import java.io.File;


public class Main {
    public static void main(String[] args) {
    	double X[] = {-2, -1, 0, 1, 2};
    	double F[] = {4, 1, 0, 1, 4};
    	int size = 5;
    	Interp LU = new Interp(size, X, F);
    	//File f = new File("input.txt");
    	//Interp LU = new Interp(f);
    	//LU.OutputData();
		System.out.print(LU.getValue(-3));
    }
}
