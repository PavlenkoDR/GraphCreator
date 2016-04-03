import java.awt.Color;
import java.awt.Graphics;


public class Paint {
	static double BorderMinX = -20;
	static double BorderMaxX = 20;
	static double BorderMinY = -20;
	static double BorderMaxY = 20;
	
    double tmp1;
    double tmp2;
    static double ceil = 5;
    double xGraph[];
    double yGraph[];

    static double PointsX[];
    static double PointsY[];
    
    static int size;

    static double xScale1 = 1;
    static double yScale1 = 1;
    
    static int width;
    static int height;

    static double PosX = 0;
    static double PosY = 0;
    
    static int nApprox = 2;
    
    static int PointsRadius;
    
    static double ActionRadius;
    static int ActionPoint;
    
	Paint(Interp t_Interp)
	{
		PointsX = t_Interp.GetPointsX();
		PointsY = t_Interp.GetPointsY();
		size = t_Interp.GetSize();

        xGraph = new double[(int)Math.round((BorderMaxX - BorderMinX)*ceil) + 1];
        yGraph = new double[(int)Math.round((BorderMaxX - BorderMinX)*ceil) + 1];
 		  //System.out.println("!!!" + ((int)((BorderMaxX - BorderMinX)*ceil) + 1) + "!!!");
        //g.setColor(Color.BLACK);
        for (double i = BorderMinX; i <= BorderMaxX; i+= 1/(double)ceil)
        {
	  		  //System.out.println("> " + (int)Math.round((i - BorderMinX)*ceil));
	  		  xGraph[(int)Math.round((i - BorderMinX)*ceil)] = i;
	  		  yGraph[(int)Math.round((i - BorderMinX)*ceil)] = -(t_Interp.getValue(i));
	  		  //System.out.println((int)((i - BorderMinX)*ceil));
	  		  //System.out.println("{" + (int)xGraph[(int)((i - BorderMinX)*ceil)] + "," + (int)yGraph[(int)((i - BorderMinX)*ceil)] + "},");
        }
 		  //System.out.println("<<<" + xGraph[(int)Math.round((BorderMaxX - BorderMinX)*ceil) + 1] + " " + yGraph[(int)Math.round((BorderMaxX - BorderMinX)*ceil) + 1] + ">>>");
        //*
        //for (double i = 0; i <= BorderMaxX - BorderMinX; i += 1/(double)ceil)
        //{
	  	  //	System.out.println(xGraph[(int)(i*ceil)] + " " + yGraph[(int)(i*ceil)]);
        //}
		
	}
	
	Paint(Approx t_Approx)
	{
		PointsX = t_Approx.GetPointsX();
		PointsY = t_Approx.GetPointsY();
		size = t_Approx.GetSize();

        xGraph = new double[(int)Math.round((BorderMaxX - BorderMinX)*ceil) + 1];
        yGraph = new double[(int)Math.round((BorderMaxX - BorderMinX)*ceil) + 1];
 		  //System.out.println("!!!" + ((int)((BorderMaxX - BorderMinX)*ceil) + 1) + "!!!");
        //g.setColor(Color.BLACK);
        for (double i = BorderMinX; i <= BorderMaxX; i+= 1/(double)ceil)
        {
	  		  //System.out.println("> " + (int)Math.round((i - BorderMinX)*ceil));
	  		  xGraph[(int)Math.round((i - BorderMinX)*ceil)] = i;
	  		  yGraph[(int)Math.round((i - BorderMinX)*ceil)] = -(t_Approx.getValue(i, nApprox));
	  		  //System.out.println((int)((i - BorderMinX)*ceil));
	  		  //System.out.println("{" + (int)xGraph[(int)((i - BorderMinX)*ceil)] + "," + (int)yGraph[(int)((i - BorderMinX)*ceil)] + "},");
        }
 		  //System.out.println("<<<" + xGraph[(int)Math.round((BorderMaxX - BorderMinX)*ceil) + 1] + " " + yGraph[(int)Math.round((BorderMaxX - BorderMinX)*ceil) + 1] + ">>>");
        //*
        //for (double i = 0; i <= BorderMaxX - BorderMinX; i += 1/(double)ceil)
        //{
	  	  //	System.out.println(xGraph[(int)(i*ceil)] + " " + yGraph[(int)(i*ceil)]);
        //}
	}
	/*
	void Scale(double xScale, double yScale, int width, int height)
	{
        xScale1 = width/((BorderMaxX - BorderMinX)*ceil);
        yScale1 = 1;
        tmp1 = yGraph[0];
        tmp2 = yGraph[0];
        for (double i = BorderMinX; i <= BorderMaxX; i+= 1/(double)ceil)
        {
      	  if (yGraph[(int)Math.round((i - BorderMinX)*ceil)] > tmp1)
      		  tmp1 = yGraph[(int)Math.round((i - BorderMinX)*ceil)];
      	  if (yGraph[(int)Math.round((i - BorderMinX)*ceil)] < tmp2)
      		  tmp2 = yGraph[(int)Math.round((i - BorderMinX)*ceil)];
        }
        yScale1 = height/(tmp1 - tmp2);
        xScale1 *= 0.5;
        yScale1 *= 0.5;
        System.out.println("<<<" + xScale1 + " " + yScale1 + ">>>");
		/*
        for (double i = BorderMinX; i <= BorderMaxX; i+= 1/(double)ceil)
        {
      	  xGraph[(int)Math.round((i - BorderMinX)*ceil)] *= xScale1;
      	  yGraph[(int)Math.round((i - BorderMinX)*ceil)] *= yScale1;
        }
        //
	}
	
	void MoveOnCenter(int width, int height)
	{

        PosX = 0;
        PosY = 0;
        tmp1 = xGraph[0];
        tmp2 = xGraph[0];
        for (double i = BorderMinX; i <= BorderMaxX; i+= 1/(double)ceil)
        {
      	  if (xGraph[(int)Math.round((i - BorderMinX)*ceil)]*xScale1 > tmp1)
      		  tmp1 = xGraph[(int)Math.round((i - BorderMinX)*ceil)]*xScale1;
      	  if (xGraph[(int)Math.round((i - BorderMinX)*ceil)]*xScale1 < tmp2)
      		  tmp2 = xGraph[(int)Math.round((i - BorderMinX)*ceil)]*xScale1;
        }
        PosX = ((double)width - (tmp1 - tmp2));
        tmp1 = yGraph[0];
        tmp2 = yGraph[0];
        for (double i = BorderMinX; i <= BorderMaxX; i+= 1/(double)ceil)
        {
      	  if (yGraph[(int)Math.round((i - BorderMinX)*ceil)]*yScale1 > tmp1)
      		  tmp1 = yGraph[(int)Math.round((i - BorderMinX)*ceil)]*yScale1;
      	  if (yGraph[(int)Math.round((i - BorderMinX)*ceil)]*yScale1 < tmp2)
      		  tmp2 = yGraph[(int)Math.round((i - BorderMinX)*ceil)]*yScale1;
        }
        PosY = ((double)height - (tmp1 - tmp2))/(double)2;
        if ((PosY > height/2)||(PosY < 0))
      	  PosY = height/2;
        //System.out.println("<<<" + PosX + " " + PosY + ">>>");
        
        /*
        for (double i = BorderMinX; i <= BorderMaxX; i+= 1/(double)ceil)
        {
        	  xGraph[(int)Math.round((i - BorderMinX)*ceil)] += PosX;
        	  yGraph[(int)Math.round((i - BorderMinX)*ceil)] += PosY;
        }
        //
	}
	*/
	void DrawGraph(Graphics g)
	{
        for (double i = BorderMinX; i <= BorderMaxX - 1/(double)ceil; i+= 1/(double)ceil)
        {
        	//g.setColor(Color.BLACK);
        	double x1 = xGraph[(int)Math.round((i - BorderMinX)*ceil)]*xScale1 + PosX;
        	double y1 = yGraph[(int)Math.round((i - BorderMinX)*ceil)]*yScale1 + height - PosY;
        	double x2 = xGraph[(int)Math.round((i - BorderMinX)*ceil)+1]*xScale1 + PosX;
        	double y2 = yGraph[(int)Math.round((i - BorderMinX)*ceil)+1]*yScale1 + height - PosY;
	  		  if (y1 < 0) y1 = -2;
	  		  else if (y1 > height) y1 = height + 2;
	  		  if (y2 < 0) y2 = -2;
	  		  else if (y2 > height) y2 = height + 2;
      	  	g.drawLine(
      	  			//*
      			  (int)Math.round(x1), 
      			  (int)Math.round(y1), 
      			  (int)Math.round(x2), 
      			  (int)Math.round(y2)
      			  //*/
      	  			/*
        			  (int)Math.round(xGraph[(int)Math.round((i - BorderMinX)*ceil)]), 
          			  (int)Math.round(yGraph[(int)Math.round((i - BorderMinX)*ceil)]), 
          			  (int)Math.round(xGraph[(int)Math.round((i - BorderMinX)*ceil)+1]), 
          			  (int)Math.round(yGraph[(int)Math.round((i - BorderMinX)*ceil)+1])
          			  //*/
      			  );
            //System.out.println("<<<" + ((int)Math.round(xGraph[(int)Math.round((i - BorderMinX)*ceil)]*xScale1 + PosX))
            //					+ " " + ((int)Math.round(yGraph[(int)Math.round((i - BorderMinX)*ceil)]*yScale1 + height - PosY)) + ">>>");
        }
	}
	static void DrawPoints(Graphics g)
	{
    	g.setColor(Color.RED);
        for (int i = 0; i < size; i++)
        {
      	  	g.drawOval(
      	  			(int)Math.round(PointsX[i]*xScale1 + PosX) - PointsRadius, 
      	  			(int)Math.round(-PointsY[i]*yScale1 + height - PosY) - PointsRadius, 
      	  		    PointsRadius*2, 
      	  		    PointsRadius*2);
        }
	}
	
	static void DrawGrid(Graphics g)
	{
    	g.setColor(new Color(220, 220, 220));
		for (double i = BorderMinX*xScale1; i <= BorderMaxX*xScale1; i+= xScale1)
			for (double j = BorderMinY*yScale1; j <= BorderMaxY*yScale1; j+= yScale1)
			{
	      	  	g.drawLine(
	      	  			(int)Math.round(i + PosX), 
	      	  			(int)Math.round(BorderMinY*yScale1 + PosY), 
	      	  			(int)Math.round(i + PosX), 
	      	  			(int)Math.round(BorderMaxY*yScale1 + PosY)
	      			  );
	      	  	g.drawLine(
		      			(int)Math.round(BorderMinX*xScale1 + PosX), 
		      			(int)Math.round(j + PosY), 
		      			(int)Math.round(BorderMaxX*xScale1 + PosX), 
		      			(int)Math.round(j + PosY)
		      			  );
			}
	}
	static void DrawCoord(Graphics g)
	{
    	g.setColor(new Color(0, 0, 0));

  	  	g.drawLine(
  	  			(int)Math.round(PosX), 
  	  			(int)Math.round(BorderMinY*yScale1 + PosY), 
  	  			(int)Math.round(PosX), 
  	  			(int)Math.round(BorderMaxY*yScale1 + PosY)
  			  );
		g.drawString("y", 
				(int)Math.round(PosX + PointsRadius) - 20 , 
				((int)Math.round(BorderMinY*yScale1 + PosY + 10)>10)?((int)Math.round(BorderMinY*yScale1 + PosY + 10)):10);
  	  	g.drawLine(
      			(int)Math.round(BorderMinX*xScale1 + PosX), 
      			(int)Math.round(PosY), 
      			(int)Math.round(BorderMaxX*xScale1 + PosX), 
      			(int)Math.round(PosY)
      			  );
		g.drawString("X", 
				((int)Math.round(BorderMaxX*xScale1 + PosX) - 10 < width - 10)?((int)Math.round(BorderMaxX*xScale1 + PosX) - 10):(width - 10), 
				(int)Math.round(PosY) - 10);
	}
	
	static void DefaultSetting()
	{
		  		xScale1 = 16;
		  		yScale1 = 16;
		  		PosX = 100;
		  		PosY = 100;
		  		width = 400;
		  		height = 400;
		  		ceil = 20;
		  		nApprox = 4;
		  		PointsRadius = 3;
		  		BorderMinX = -20;
		  		BorderMaxX = 20;
		  		BorderMinY = -20;
		  		BorderMaxY = 20;
		  		ActionRadius = 0.5;
	}
}
