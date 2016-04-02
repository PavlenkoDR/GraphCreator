import java.awt.Color;
import java.awt.Graphics;
import java.io.File;

import javax.swing.JFrame;

//Window Builder

public class Main {
    public static void main(String[] args) {
    	File f = new File("input.txt");
    	final Interp t_Interp = new Interp(f);
		System.out.print(t_Interp.getValue(0));
    	//t_Interp.OutputData();
		final double BorderMinX = -10;
		final double BorderMaxX = 10;
		final double BorderMinY = -10;
		final double BorderMaxY = 10;
    	JFrame Form1=new JFrame("Окно с изображением")
    	{
    	       public void paint(Graphics g) {
    	          super.paint(g);
    	          double tmp1, tmp2;
    	          double ceil = 5;
    	          double xGraph[] = new double[(int)Math.round((BorderMaxX - BorderMinX)*ceil) + 1];
    	          double yGraph[] = new double[(int)Math.round((BorderMaxX - BorderMinX)*ceil) + 1];
     	  		  //System.out.println("!!!" + ((int)((BorderMaxX - BorderMinX)*ceil) + 1) + "!!!");
    	          //g.setColor(Color.BLACK);
    	          for (double i = BorderMinX; i <= BorderMaxX; i+= 1/(double)ceil)
    	          {
         	  		  //System.out.println(i);
    	        	  xGraph[(int)Math.round((i - BorderMinX)*ceil)] = Math.round((i - BorderMinX)*ceil);
    	        	  yGraph[(int)Math.round((i - BorderMinX)*ceil)] = (t_Interp.getValue(i))*ceil;
         	  		  //System.out.println((int)((i - BorderMinX)*ceil));
         	  		  System.out.println("{" + (int)xGraph[(int)((i - BorderMinX)*ceil)] + "," + (int)yGraph[(int)((i - BorderMinX)*ceil)] + "},");
    	          }

    	          //*
    	          //for (double i = 0; i <= BorderMaxX - BorderMinX; i += 1/(double)ceil)
    	          //{
         	  	  //	System.out.println(xGraph[(int)(i*ceil)] + " " + yGraph[(int)(i*ceil)]);
    	          //}
    	          double xScale = getSize().width/((BorderMaxX - BorderMinX)*ceil);
    	          double yScale = 0;
    	          for (double i = BorderMinX; i <= BorderMaxX; i+= 1/(double)ceil)
    	          {
    	        	  if (yGraph[(int)Math.round((i - BorderMinX)*ceil)] > yScale)
    	        		  yScale = yGraph[(int)Math.round((i - BorderMinX)*ceil)];
    	          }
    	          yScale = getSize().height/yScale;
     	  		  System.out.println("<<<" + yScale + ">>>");
     	  		  //*
    	          xScale *= 0.5;
    	          yScale *= 0.5;
    	          for (double i = BorderMinX; i <= BorderMaxX; i+= 1/(double)ceil)
    	          {
    	        	  xGraph[(int)Math.round((i - BorderMinX)*ceil)] *= xScale;
    	        	  yGraph[(int)Math.round((i - BorderMinX)*ceil)] *= yScale;
    	          }
    	          //*
    	          double PosX = 0;
    	          double PosY = 0;
    	          tmp1 = yGraph[0];
    	          tmp2 = yGraph[0];
    	          for (double i = BorderMinX; i <= BorderMaxX; i+= 1/(double)ceil)
    	          {
    	        	  if (yGraph[(int)Math.round((i - BorderMinX)*ceil)] > tmp1)
    	        		  tmp1 = yGraph[(int)Math.round((i - BorderMinX)*ceil)];
    	        	  if (yGraph[(int)Math.round((i - BorderMinX)*ceil)] < tmp2)
    	        		  tmp2 = yGraph[(int)Math.round((i - BorderMinX)*ceil)];
    	          }
    	          PosX = (getSize().width - (BorderMaxX - BorderMinX)*xScale*ceil)/2;
    	          PosY = (getSize().height - (tmp1 - tmp2))/2;
    	          if (PosY > getSize().height/2)
    	        	  PosY = getSize().height/2;
    	          //*
    	          for (double i = BorderMinX; i <= BorderMaxX; i+= 1/(double)ceil)
    	          {
    	          	  xGraph[(int)Math.round((i - BorderMinX)*ceil)] += PosX;
    	          	  yGraph[(int)Math.round((i - BorderMinX)*ceil)] += PosY;
    	          }
    	          //*/
    	          for (double i = BorderMinX; i < BorderMaxX; i+= 1/(double)ceil)
    	          {
    	        	  g.drawLine(
    	        			  (int)Math.round(xGraph[(int)Math.round((i - BorderMinX)*ceil)]), 
    	        			  (int)Math.round(yGraph[(int)Math.round((i - BorderMinX)*ceil)]), 
    	        			  (int)Math.round(xGraph[(int)Math.round((i - BorderMinX)*ceil)+1]), 
    	        			  (int)Math.round(yGraph[(int)Math.round((i - BorderMinX)*ceil)+1])
    	        			  );
    	          }
    	          //g.drawLine(0,0 ,getSize().width , getSize().height);// рисуем линию через весь размер экрана
    	          //g.setColor(new Color (128,128,128));
    	          //g.drawOval(getSize().width/2 , getSize().height/2, 100, 50);
    	          //g.setColor(new Color (255, 0, 0));
    	          //g.drawRoundRect(60 , 60 , 210 , 210 , 50 , 10);
    	          //g.drawString("Hello From Java 2D", 50 , 50);
    	          //g.fillRect(20 , 20 , 60 , 60);
    	       }
    	};
    	Form1.setSize(400, 400);
    	Form1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	Form1.setVisible(true);
    	//double X[] = {-2, -1, 0, 1, 2};
    	//double F[] = {4, 1, 0, 1, 4};
    	//int size = 5;
    	//Interp LU = new Interp(size, X, F);
    	/*
    	File f = new File("input.txt");
    	Interp LU = new Interp(f);
    	LU.OutputData();
		System.out.print(LU.getValue(0));
		//*/
    	//*
    	//File f = new File("input.txt");
    	//Approx LU = new Approx(f);
		//System.out.print(LU.getValue(-2, 3));
    	//LU.OutputData();
    	//*/
    }
}