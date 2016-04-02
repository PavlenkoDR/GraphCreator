import java.awt.Color;
import java.awt.Graphics;
import java.io.File;

import javax.swing.JFrame;

//Window Builder

public class Main {
    public static void main(String[] args) {
    	File f = new File("input.txt");
    	final Interp t_Interp = new Interp(f);
    	final Approx t_Approx = new Approx(f);
		//System.out.print(LU.getValue(-2, 3));
		//System.out.print(t_Interp.getValue(0));
    	JFrame Form1=new JFrame("Окно с изображением")
    	{
    	       public void paint(Graphics g) {
    	    	   super.paint(g);
   	  		  		Paint.xScale1 = 8*2;
   	  		  		Paint.yScale1 = 2*2;
   	  		  		Paint.PosX = 200;
   	  		  		Paint.PosY = 200;
   	  		  		Paint.width = getSize().width;
   	  		  		Paint.height = getSize().height;
   	  		  		Paint.ceil = 20;
   	  		  		Paint.nApprox = 6;
    	  		  	Paint t_Paint_Interp = new Paint(t_Interp);
    	  		  	Paint t_Paint_Approx = new Paint(t_Approx);
    	    	    /*
    	  		  	t_Paint_Interp.Scale(0.5, 0.5, getSize().width, getSize().height);
    	  		  	t_Paint_Interp.MoveOnCenter(getSize().width, getSize().height);
    	  		  	t_Paint_Interp.Draw(g);
    	  		  	*/
    	  		  	//t_Paint_Interp.Scale(0.5, 0.5, getSize().width, getSize().height);
    	  		  	//t_Paint_Interp.MoveOnCenter(getSize().width, getSize().height);
    	  		  	t_Paint_Interp.DrawPoints(g);
    	  		  	g.setColor(Color.BLUE);
    	  		  	t_Paint_Interp.DrawGraph(g);
    	  		  	//t_Paint_Approx.Scale(0.5, 0.5, getSize().width, getSize().height);
    	  		    //t_Paint_Approx.MoveOnCenter(getSize().width, getSize().height);
    	  		  	g.setColor(Color.BLACK);
    	  		  	t_Paint_Approx.DrawGraph(g);
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