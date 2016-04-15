import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.swing.JFrame;
import javax.swing.JPanel;

//Window Builder

public class Main extends JFrame implements MouseListener, MouseMotionListener, MouseWheelListener{
	
	private static final long serialVersionUID = 1L;
	
	File f;
	InterpMatrix t_Interp;
	//final InterpPoly t_Poly;
	Approx t_Approx;

    public static void DrawInGraph(Graphics g)
    {
    	double ceil = Paint.ceil;
    	double BorderMinX = Paint.BorderMinX;
    	double BorderMaxX = Paint.BorderMaxX;
    	double x1 = BorderMinX*Paint.xScale1 + Paint.PosX;
    	double y1 = -Function.ReturnY(BorderMinX)*Paint.yScale1 + Paint.PosY;
        for (double i = BorderMinX+1/(double)ceil; i <= BorderMaxX - 1/(double)ceil; i+= 1/(double)ceil)
        {
        	double x2 = i*Paint.xScale1 + Paint.PosX;
        	double y2 = -Function.ReturnY(i)*Paint.yScale1 + Paint.PosY;
        	//g.setColor(Color.BLACK);
	  		if (y1 < 0) y1 = -2;
	  		else if (y1 > Paint.height) y1 = Paint.height + 2;
	  		if (y2 < 0) y2 = -2;
	  		else if (y2 > Paint.height) y2 = Paint.height + 2;
      	  	g.drawLine(
      			  (int)Math.round(x1), 
      			  (int)Math.round(y1), 
      			  (int)Math.round(x2), 
      			  (int)Math.round(y2)
      			  );
      	  	x1 = x2;
      	  	y1 = y2;
        }
    	
    }
    
	double MouseX, MouseY;
	double MouseLastX = 0, MouseLastY = 0;
	double MouseTransX, MouseTransY;
	
	static double X[];

	static double Y[];
	static int size;
	static double LeftBorder;

	static double RightBorder;
	
	JPanel Panel1;
	//JButton Button1;
	//Box Box1;
    double PosXVec = 0, PosYVec = 0;
    double xScaleVec = 0, yScaleVec = 0;
	
    static boolean GetPointsFlag = false;
    
	public Main()
	{
		super("Окно с изображением");
    	setSize(1000, 1000);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setVisible(true);
    	addMouseListener(this);
    	addMouseMotionListener(this);
    	addMouseWheelListener(this);
    	f = new File("input.txt");
    	//t_Interp = new InterpMatrix(f);
    	//t_Approx = new Approx(f);
    	//t_Poly = new InterpPoly(f);
    	GenPoints(5, 0.6, 1.1);
    	t_Interp = new InterpMatrix(size, X, Y);
    	t_Approx = new Approx(size, X, Y);
    	//t_Poly = new InterpPoly(size, X, Y);
    	
    	addKeyListener(new KeyAdapter() {
 
            public void keyReleased(KeyEvent e) {
                String Key = KeyEvent.getKeyText(e.getKeyCode());
                switch(Key)
                {
                case "2":
                	Paint.nApprox = (Paint.nApprox < Paint.size)?Paint.nApprox + 1:Paint.size;
                	break;
            	case "1":
            		Paint.nApprox = (Paint.nApprox > 2)?Paint.nApprox - 1:2;
            		break;
                }
            	t_Approx.InitMatrix(Paint.nApprox);
            	repaint();
            }
             
        });
    	
    	Panel1 = new JPanel()
    	{
			private static final long serialVersionUID = 1L;

			public void paint(Graphics g) {
    	  	   super.paint(g);
    	  			Paint.width = (getSize().width > 50)? (getSize().width):50;
    	  			Paint.height = (getSize().height > 50)? (getSize().height):50;
    	  			Paint.xScale1 = Paint.width/(Paint.BorderMaxX - Paint.BorderMinX) - 1 + xScaleVec;
    	  			Paint.yScale1 = Paint.height/(Paint.BorderMaxY - Paint.BorderMinY) - 1 + yScaleVec;
     	   			Paint.PosX = getSize().width/2 + PosXVec;
     	   			Paint.PosY = getSize().height/2 + PosYVec;
        		  	Paint.DrawGrid(g);
        		  	Paint.DrawCoord(g);
        		  	g.setColor(Color.MAGENTA);
        		  	g.drawString("Matrix Interpolation ", 40 , 50);
        		  	g.setColor(Color.ORANGE);
        		  	g.drawString("Polynom Interpolation ", 40 , 65);
        		  	g.setColor(Color.BLUE);
        		  	g.drawString("Approximation " + Paint.nApprox + " power", 40 , 80);
        		  	g.setColor(Color.RED);
        		  	g.drawString("Input points ", 40 , 95);
    	 		  	Paint t_Paint_Interp = new Paint(t_Interp);
    	 		  	//Paint t_Paint_Poly = new Paint(t_Poly);
    	 		  	Paint t_Paint_Approx = new Paint(t_Approx);
    	 		  	Paint.DrawPoints(g);
    	 		  	DrawInGraph(g);
    	 		  	g.setColor(Color.MAGENTA);
    	 		  	t_Paint_Interp.DrawGraph(g);
    	 		  	g.setColor(Color.ORANGE);
    	 		  	//t_Paint_Poly.DrawGraph(g);
    	 		  	//g.drawString("Interpolation ", 40 , 50);
    	 		  	g.setColor(Color.BLUE);
    	 		  	t_Paint_Approx.DrawGraph(g);
    	 		  	//g.drawString("Approximation " + Paint.nApprox + " power", 40 , 65);
    	 		  	g.setColor(Color.RED);
    	 		  	//g.drawString("Input points ", 40 , 80);
    	 		  	g.drawString("x =  " + MouseTransX, 40 , Paint.height - 50);
    	 		  	g.drawString("y =  " + MouseTransY, 40 , Paint.height - 30);

    	 		  	//*/
    	 		  	//g.drawLine(0,0 ,getSize().width , getSize().height);// рисуем линию через весь размер экрана
    	 		  	//g.setColor(new Color (128,128,128));
    	 		  	//g.drawOval(getSize().width/2 , getSize().height/2, 100, 50);
    	 		  	//g.setColor(new Color (255, 0, 0));
    	 		  	//g.drawRoundRect(60 , 60 , 210 , 210 , 50 , 10);
    	 		  	//g.drawString("Hello From Java 2D", 50 , 50);
    	 		  	//g.fillRect(20 , 20 , 60 , 60);
    	     }
    	};
    	
    	add(Panel1);

   	    Paint.DefaultSetting();
	  	Paint.ceil = 20;
	  	Paint.nApprox = 2;
	  	Paint.PointsRadius = 3;
	    setVisible(true);
		
	}
	//======================================================================================================
    public static void main(String[] args){
    	//GetTableOutPut();
    	//Integral.GetTable(0.124062);
    	//GenPoints(11, 0.6, 1.1);
    	//DividedDifferences t_DividedDifferences;
    	//t_DividedDifferences = new DividedDifferences(size, X, Y);
    	//System.out.println(t_DividedDifferences.NewtonsFirstFormula(0.62));
    	//System.out.println(Function.GetDifferentialInPoint(1, 2));
    	new Main();
    }
    
    public static double Factorial(int num) {
    	double fact=1;
    	for (int i = 1; i <= num; i++)
    		fact*=i;
    	return fact;
    }
    
    static InterpMatrix t_InterpTmp;
    
    public static double DeltaFAbsolute(double step)
    {
    	double max = 0, now = 0;
    	//System.out.println("\n" + size + "\n");
    	for (double i = LeftBorder; i <= RightBorder; i+= 1/step)
    	{
    		now = Math.abs(t_InterpTmp.getValue(i) - Function.ReturnY(i));
    		//System.out.println(t_InterpTmp.getValue(X[i]) - Y[i]);
    		max = (now > max)?now:max;
    	}
    	return max;
    }
    
    public static double DeltaFOtmosit(double step)
    {
    	double max = 0, now = 0, ytmp;
    	for (double i = LeftBorder; i <= RightBorder; i+= 1/step)
    	{
    		ytmp = Math.abs(Function.ReturnY(i));
    		now = (Math.abs(t_InterpTmp.getValue(i) - Function.ReturnY(i))/(ytmp!=0?ytmp:1))*100;
    		max = (now > max)?now:max;
    	}
    	return max;
    }
    
    public static double Taylor(double step)
    {
    	double max = 0, now = 0;
    	for (double i = LeftBorder; i <= RightBorder; i+= 1/step)
    	{
    		now = Math.abs(Function.ReturnY(i));
    		max = (now > max)?now:max;
    	}
    	return ((max/Factorial(size))*Math.pow((RightBorder - LeftBorder), size));
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
                    	GenPoints(i, 0.6, 1.1);
                    	t_InterpTmp = new InterpMatrix(size, X, Y);
                    	out.println(
                    				"$"+i+"$&$" + 
                    				GetRound(DeltaFAbsolute(step), SignAfterCommon)+ "$&$" + 
                    				GetRound(DeltaFOtmosit(step), SignAfterCommon) + "$&$" + 
                    				GetRound(Taylor(step), SignAfterCommon) + 
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
    
    public static void GenPoints(int n, double min, double max)
    {
    	GetPointsFlag = true;
    	size = n;
    	X = new double[size + 1];
    	Y = new double[size + 1];
    	LeftBorder = min;
    	RightBorder = max;
    	int k = 0;
    	for (double i = min; i <= max; i+= (max - min)/(n - 1))
    	{
    		X[k] = i;
    		Y[k] = Function.ReturnY(i);
    		k++;
    	}
    	X[k] = max;
    	Y[k] = Function.ReturnY(max);
    }
    
    public static double GetRound(double x, int n)
    {
    	if (n < 0)
    		return x;
    	return new BigDecimal(x).setScale(n, RoundingMode.FLOOR).doubleValue();
    }
    
	@Override
	public void mouseClicked(MouseEvent e) {	//Щелчок
		MouseLastX = PosXVec;
		MouseLastY = PosXVec;
	}

	@Override
	public void mouseEntered(MouseEvent e) {	//Мышь попадает в окно
		
	}

	@Override
	public void mouseExited(MouseEvent e) {		//Мышь выходит из окна
		
	}

	@Override
	public void mousePressed(MouseEvent e) {	//Нажатие
		Paint.ActionPoint = -1;
		MouseLastX = PosXVec;
		MouseLastY = PosYVec;
		for (int i = 0; i < Paint.size; i++)
		{
			if ((Paint.PointsX[i] - 0.1 > MouseTransX - Paint.ActionRadius)&&
				(Paint.PointsX[i] - 0.1 < MouseTransX + Paint.ActionRadius))
				if ((Paint.PointsY[i] > MouseTransY - Paint.ActionRadius)&&
					(Paint.PointsY[i] < MouseTransY + Paint.ActionRadius))
				{
					Paint.ActionPoint = i;
					return;
				}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {	//Отжатие
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {	//Перетаскивание
		if (Paint.ActionPoint == -1)
		{
			PosXVec = e.getX() - MouseX + MouseLastX;
			PosYVec = e.getY() - MouseY + MouseLastY;
		}
		else
		{
			MouseX = e.getX();
			MouseY = e.getY();
			MouseTransX = (MouseX - Paint.PosX)/Paint.xScale1;
			MouseTransY = -(MouseY - Paint.PosY)/Paint.yScale1;
			MouseTransX = new BigDecimal(MouseTransX).setScale(2, RoundingMode.UP).doubleValue();
			MouseTransY = new BigDecimal(MouseTransY).setScale(2, RoundingMode.UP).doubleValue();
			t_Interp.F[Paint.ActionPoint] = MouseTransY;
			t_Approx.F[Paint.ActionPoint] = MouseTransY;
			//t_Poly.F[Paint.ActionPoint] = MouseTransY;
			if (GetPointsFlag == true)  Y[Paint.ActionPoint] = MouseTransY;
			Paint.PointsY[Paint.ActionPoint] = MouseTransY;
			
		}
		t_Interp.InitMatrix();
		t_Approx.InitMatrix(Paint.nApprox);
		
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {		//Перемещение мыши
		MouseX = e.getX();
		MouseY = e.getY();
		MouseTransX = (MouseX - Paint.PosX)/Paint.xScale1;
		MouseTransY = -(MouseY - Paint.PosY)/Paint.yScale1;
		MouseTransX = new BigDecimal(MouseTransX).setScale(2, RoundingMode.UP).doubleValue();
		MouseTransY = new BigDecimal(MouseTransY).setScale(2, RoundingMode.UP).doubleValue();
		MouseLastX = PosXVec;
		MouseLastY = PosXVec;
		repaint(65, Paint.height - 60, 50, 30);
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		//System.out.print("!!!");
	       int notches = e.getWheelRotation();
	       int speed = 300;
	       if (notches < 0) {
	           xScaleVec+=speed/(Paint.BorderMaxX - Paint.BorderMinX);
	           yScaleVec+=speed/(Paint.BorderMaxY - Paint.BorderMinY);
	       } else {
	           xScaleVec-=speed/(Paint.BorderMaxX - Paint.BorderMinX);
	           yScaleVec-=speed/(Paint.BorderMaxY - Paint.BorderMinY);
	           xScaleVec = (xScaleVec>=0)?xScaleVec:0;
	           yScaleVec = (yScaleVec>=0)?yScaleVec:0;
	       }
	       repaint();
	    }
	

}