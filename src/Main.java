
public class Main {
	GraphFrame frame;
    public static void main(String[] args){
    	new Main();
    }
    Main()
    {
    	//CHMFure.GetResult(1.5);
    	//CHMFure.CreateDots();
    	CHMSpline.CreateDots();
    	System.out.println(CHMSqrt.NewTone(0.5, 1, 0.0001));
    	System.out.println(CHMSqrt.Hord(0.5, 1, 0.0001));
    	System.out.println(CHMSqrt.Dihot(0.5, 1, 0.0001));
    	frame = new GraphFrame("Окошко", 600, 600);
    	frame.DrawPolynom();
    	frame.DrawApprox();
    	frame.DrawSpline();
    	//CHMIntegral.GetTable(-0.0066415);
    }
}
