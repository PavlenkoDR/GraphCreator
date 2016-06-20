import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

// =================================== Числаки ======================================

public class CHMInterpMatrix{

    static InterpMatrix t_InterpTmp;
    
    
    public static double Factorial(int num) {
    	double fact=1;
    	for (int i = 1; i <= num; i++)
    		fact*=i;
    	return fact;
    }

    public static double DeltaFAbsolute(double step)
    {
    	double max = 0, now = 0;
    	//System.out.println("\n" + size + "\n");
    	for (double i = CHMFunction.LeftBorder; i <= CHMFunction.RightBorder; i+= 1/step)
    	{
    		now = Math.abs(t_InterpTmp.getValue(i) - CHMFunction.ReturnY(i));
    		//System.out.println(t_InterpTmp.getValue(X[i]) - Y[i]);
    		max = (now > max)?now:max;
    	}
    	return max;
    }
    
    public static double DeltaFOtmosit(double step)
    {
    	double max = 0, now = 0, ytmp;
    	for (double i = CHMFunction.LeftBorder; i <= CHMFunction.RightBorder; i+= 1/step)
    	{
    		ytmp = Math.abs(CHMFunction.ReturnY(i));
    		now = (Math.abs(t_InterpTmp.getValue(i) - CHMFunction.ReturnY(i))/(ytmp!=0?ytmp:1))*100;
    		max = (now > max)?now:max;
    	}
    	return max;
    }
    
    public static double Taylor(double step)
    {
    	double max = 0, now = 0;
    	for (double i = CHMFunction.LeftBorder; i <= CHMFunction.RightBorder; i+= 1/step)
    	{
    		now = Math.abs(CHMFunction.ReturnY(i));
    		max = (now > max)?now:max;
    	}
    	return ((max/Factorial(CHMFunction.size))*Math.pow((CHMFunction.RightBorder - CHMFunction.LeftBorder), CHMFunction.size));
    }
	
    public static void GetTableOutPut()
    {
    	File fout = new File("InterpolTable.txt"); 
    	double step = 100000;
        try {
            //проверяем, что если файл не существует то создаем его
            if(!fout.exists()){
            	fout.createNewFile();
            }
     
            //PrintWriter обеспечит возможности записи в файл
            PrintWriter out = new PrintWriter(fout.getAbsoluteFile());
     
            try {
                //Записываем текст у файл
            	out.println("Power | Delta Absolute | Delta Otnos | Theoretics Error");
            	out.println("\\hline");
            	int SignAfterCommon = 12;
            	for (int i = 3; i <= 100; i++)
            	{
                	//try {
                		//Thread.sleep(200);
            		CHMFunction.GenPoints(i, 0.6, 1.1);
                    	t_InterpTmp = new InterpMatrix(CHMFunction.size, CHMFunction.X, CHMFunction.Y);
                    	out.println(
                    				"$"+i+"$&$" + 
                    						Bicycles.GetRound(DeltaFAbsolute(step), SignAfterCommon)+ "$&$" + 
                    						Bicycles.GetRound(DeltaFOtmosit(step), SignAfterCommon) + "$&$" + 
                    						Bicycles.GetRound(Taylor(step), SignAfterCommon) + 
                    				"$\\\\"
                    				);
                    	out.println("\\hline");
                    	//obj.t_Interp = t_InterpTmp;
                    	//obj.repaint();
                	//} catch (InterruptedException e) {
                	//	e.printStackTrace();
                	//}
            	}
            	
            } finally {
                //После чего мы должны закрыть файл
                //Иначе файл не запишется
                out.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

}
