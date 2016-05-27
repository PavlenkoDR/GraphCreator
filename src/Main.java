
public class Main {
	GraphFrame frame;
    public static void main(String[] args){
    	new Main();
    }
    Main()
    {
    	System.out.println(CHMFure.Fur(1, 1, 2, 100));
    	System.out.println(CHMFure.Func(1));
    	frame = new GraphFrame("Окошко", 600, 600);
    	frame.DrawPolynom();
    	frame.DrawApprox();
    }
}
