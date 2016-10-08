import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JList;
import javax.swing.JScrollPane;


public class GraphFrame extends JFrame{
	
	
	private static final long serialVersionUID = 1L;
	public GraphPanel Graph;
	private JPanel Panel1;
	private JButton ButtonOpen;
	private JButton ButtonApprox;
	private JButton ButtonGoToCenter;
	private JButton ButtonSaveImage;
	private JCheckBox CheckBox1;
	private JCheckBox CheckBoxPoly;
	private JCheckBox CheckBoxApprox;
	private JCheckBox CheckBoxSpline;
	private JCheckBox CheckBoxExtraPoly;
	private SpinnerModel model;
	private JSpinner spinner;
	JScrollPane southScroll;
	JPanel Panel2;

	public static void setQuality(double x)
	{
		Paint.setQuality(x);
	}
	
	public void SetFunc(Function func)
	{
		Graph.paint.SetFunc(func);
	   	spinner.setModel(new SpinnerNumberModel(2, 2, Graph.func.size, 1));
    	Graph.repaint();
	}
	public void SetDots(int _size, double[] _X, double[] _F)
	{
		Graph.paint.SetFunc(new Function(_size, _X, _F));
	   	spinner.setModel(new SpinnerNumberModel(2, 2, Graph.func.size, 1));
    	Graph.repaint();
	}
	public void SetDotsFromFile(File file)
	{
		Graph.paint.SetFunc(new Function(file));
	   	spinner.setModel(new SpinnerNumberModel(2, 2, Graph.func.size, 1));
    	Graph.repaint();
	}
	public void SetDrawPolynom(boolean f)
	{
 	   Graph.boolDrawGraphPoly = f;
 	   CheckBoxPoly.setSelected(f);
	}
	public void SetDrawExtraPoly(boolean f)
	{
 	   Graph.boolDrawGraphExtraPoly = f;
 	   CheckBoxExtraPoly.setSelected(f);
 	   Graph.repaint();
	}
	public void SetDrawApprox(boolean f)
	{
 	   Graph.boolDrawGraphApprox = f;
 	   CheckBoxApprox.setSelected(f);
 	   ButtonApprox.setVisible(f);
 	   spinner.setVisible(f);
	}
	public void SetDrawSpline(boolean f)
	{
 	   Graph.boolDrawGraphSpline = f;
 	   CheckBoxSpline.setSelected(f);
	}
	public void saveScreen(String name)
	{
		try {
			ImageIO.write(Graph.getImage(), "png", new File(name));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Graph.screenRun = false;
	}
	GraphFrame(String name, int width, int height)
    {
    	super(name);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
	   	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	//TPanel Panel1 = new TPanel();
    	setLocationRelativeTo(null); //open on center
    	setLayout(new GridBagLayout());
    	setSize(width, height);
    	Graph = new GraphPanel();
    	add(Graph, new 	GridBagConstraints(
    		0, 0, 1, 1, 1, 50, 
			GridBagConstraints.WEST, 
			GridBagConstraints.CENTER, 
			new Insets(1, 1, 1, 1), 
			getSize().width, 
			getSize().height - 50));
    	addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
		  		Panel2.remove(southScroll);
		  		Panel2.add(southScroll, new GridBagConstraints(
		  				1, 2, 1, 1, 1, 1, 
		  				GridBagConstraints.EAST, 
		  				GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 1, getSize().height - 220));
		  		remove(Graph);
				add(Graph, new 	GridBagConstraints(
					0, 0, 1, 2, 1, 1, 
					GridBagConstraints.NORTH, 
					GridBagConstraints.CENTER, 
					new Insets(1, 1, 1, 1), 
					getSize().width, 
					getSize().height));    
				Graph.paint.width = Graph.getSize().width;
				Graph.paint.height = Graph.getSize().height; 
				Graph.repaint();
			}
			@Override
			public void componentHidden(ComponentEvent arg0) {
			}
			@Override
			public void componentMoved(ComponentEvent arg0) {
			}
			@Override
			public void componentShown(ComponentEvent arg0) {
			}
    	});
    	addWindowStateListener(new WindowStateListener() {
			@Override
			public void windowStateChanged(WindowEvent e) {
		  		Panel2.remove(southScroll);
		  		Panel2.add(southScroll, new GridBagConstraints(
		  				1, 2, 1, 1, 1, 1, 
		  				GridBagConstraints.EAST, 
		  				GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 1, getSize().height - 220));
		  		remove(Graph);
				add(Graph, new 	GridBagConstraints(
					0, 0, 1, 2, 1, 1, 
					GridBagConstraints.NORTH, 
					GridBagConstraints.CENTER, 
					new Insets(1, 1, 1, 1), 
					getSize().width, 
					getSize().height));   
				Graph.paint.width = Graph.getSize().width;
				Graph.paint.height = Graph.getSize().height; 
				Graph.repaint();
			}
    	});
    	setLocationRelativeTo(null); //open on center
    	setLayout(new GridBagLayout());
    	Panel1 = new JPanel();
    	add(Panel1, new GridBagConstraints(
			1, 0, 1, 1, 1, 1, 
			GridBagConstraints.NORTH, 
			GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 200, 0));
    	ButtonOpen = new JButton("Открыть файл");
    	ButtonApprox = new JButton("Аппроксимировать");
    	ButtonGoToCenter = new  JButton("Центровать");
    	ButtonSaveImage = new  JButton("Скриншот");
    	final JFileChooser fileopen = new JFileChooser(); 
        // Сам слушатель:
        ChangeListener listener = new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
        		//JSpinner js = (JSpinner) e.getSource();
        	    //System.out.println("Введенное значение: " + js.getValue());
        	}
        };
    	// Конец слушателя 
    
        // Объявление модели JSpinner'а
	    model = new SpinnerNumberModel();
        //Объявление JSpinner'а, которого будем слушать
        spinner = new JSpinner(model);
        if (Graph.func.size >= 2)
        	spinner.setModel(new SpinnerNumberModel(2, 2, Graph.func.size, 1));
        else
        	spinner.setModel(new SpinnerNumberModel(0, 0, 0, 1));
        spinner.addChangeListener(listener);
    	Panel1.setLayout(new BoxLayout(Panel1, BoxLayout.Y_AXIS));
    	Panel1.add(ButtonOpen);
    	CheckBoxExtraPoly = new JCheckBox("Экстраполяция");
    	Panel1.add(CheckBoxExtraPoly);
    	CheckBoxPoly = new JCheckBox("Полином");
    	Panel1.add(CheckBoxPoly);
    	CheckBoxApprox = new JCheckBox("Аппроксимация");
    	Panel1.add(CheckBoxApprox);
    	CheckBoxSpline = new JCheckBox("Сплайн");
    	Panel1.add(CheckBoxSpline);
    	CheckBox1 = new JCheckBox("Редактирование");
    	Panel1.add(CheckBox1);
    	Panel1.add(ButtonGoToCenter);
    	Panel1.add(ButtonSaveImage);
    	Panel1.add(ButtonApprox);
    	Panel1.add(spinner);
  		ButtonApprox.setEnabled(false);
  		spinner.setEnabled(false);
  		Graph.DotsList = new JList<Object>();
  		Graph.DotsList.setLayoutOrientation(JList.VERTICAL);          
  		southScroll = new JScrollPane(Graph.DotsList);
        southScroll.setPreferredSize(new Dimension(100, 100));
  		southScroll.setSize(100, 300);
  	    GraphListener graphListener = new GraphListener(this);
  		Panel2 = new JPanel();
  		Panel2.setLayout(new GridBagLayout());
  		Panel2.add(southScroll, new GridBagConstraints(
  				1, 2, 1, 1, 1, 1, 
  				GridBagConstraints.EAST, 
  				GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 1, getSize().height - 220));
  		Panel1.add(Panel2);
    	ButtonOpen.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e){
				int ret = fileopen.showDialog(null, "Открыть файл");              
 		   		if (ret == JFileChooser.APPROVE_OPTION) {
 		   			File file = fileopen.getSelectedFile();
 		   			Graph.func = new Function(file);
 		   			Graph.paint.SetFunc(Graph.func);
 		   			if (Graph.func.GetPointsFlag) 
 		   			{
 		   				Graph.func.createDotsArray();
 		   				Graph.DotsList.setListData(Graph.func.DotsArray.toArray());
 		   				//DotsList.setSelectedIndices(new int[]{1, 2});
 		   				//if (DotsList.isSelectedIndex(2)) System.out.println("!!!");
 		   			}
 		   			spinner.setModel(new SpinnerNumberModel(2, 2, Graph.func.size, 1));
            		Graph.repaint();
 		   		}
 		    }
    	});
    	ButtonApprox.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e){
    			saveScreen("image.png");
    			if (Graph.func.GetPointsFlag)
    			{
  		  	    	if ((int) spinner.getValue() > 2)
  		  	    		Graph.func.nApprox = (int) spinner.getValue();
  					else
  						Graph.func.nApprox = 2;
  		  	    	Graph.func.approx.InitMatrix(Graph.func.nApprox);
  		            Graph.repaint();
  		    	}
  		    }
    	});  
    	ButtonGoToCenter.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e){
    			Graph.PosXVec = 0;
    			Graph.PosYVec = 0;
    			Graph.ScaleVec = 1;
    			Graph.repaint();
  		    }
    	});   
	    ButtonSaveImage.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e){
    			Date date = new Date();
    			String dateString = date.toString();
    			dateString = dateString.replace(" ", "_");
    			dateString = dateString.replace(":", ".");
    			File folder = new File("Image");
    			if (!folder.exists()) {
    				folder.mkdir();
    			}
    			saveScreen("Image/image"+dateString+".png");
  		    }
    	});   
    	CheckBox1.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e){
    			if (CheckBox1.isSelected())
    				Graph.editing = true;
    		    else
    		    	Graph.editing = false;
    	    }
       	});
    	CheckBoxPoly.addActionListener(new ActionListener() {
  		    public void actionPerformed(ActionEvent e){
  		    	if (CheckBoxPoly.isSelected())
  		    		Graph.boolDrawGraphPoly = true;
  		    	else
  		    		Graph.boolDrawGraphPoly = false;
  		    	if (Graph.func.GetPointsFlag) Graph.func.interp.InitMatrix();
  		    	Graph.repaint();
  	       	}
     	});
    	CheckBoxExtraPoly.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e){
    			if (CheckBoxExtraPoly.isSelected())
    				Graph.boolDrawGraphExtraPoly = true;
  		    	else
  		    		Graph.boolDrawGraphExtraPoly = false;
  		    	if (Graph.func.GetPointsFlag) 
  		    	{
  		    	   if (Graph.boolDrawGraphPoly) Graph.func.interp.InitMatrix();
  		    	   if (Graph.boolDrawGraphApprox) Graph.func.approx.InitMatrix(Graph.func.nApprox);
  		    	   if (Graph.boolDrawGraphSpline) Graph.func.spline.build_spline();
  		    	}
  		    	Graph.repaint();
  	       	}
     	});      
    	CheckBoxSpline.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e){
    			if (CheckBoxSpline.isSelected())
    				Graph.boolDrawGraphSpline = true;
 		    	else
 		    		Graph.boolDrawGraphSpline = false;
 		    	if (Graph.func.GetPointsFlag) Graph.func.spline.build_spline();
 		    	Graph.repaint();
 	       	}
    	});
    	CheckBoxApprox.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e){
    			if (CheckBoxApprox.isSelected())
 		    	{
    				Graph.boolDrawGraphApprox = true;
 		    		ButtonApprox.setEnabled(true);
 		    		spinner.setEnabled(true);
 		    	}
 		    	else
 		    	{
 		    		Graph.boolDrawGraphApprox = false;
 		    		ButtonApprox.setEnabled(false);
 		    		spinner.setEnabled(false);
 		    	}
 		    	if (Graph.func.GetPointsFlag) Graph.func.approx.InitMatrix((int) spinner.getValue());
 		    	Graph.repaint();
 	      		}
    	});
  		Graph.addKeyListener(graphListener);
  		Panel1.addKeyListener(graphListener);
  		Panel2.addKeyListener(graphListener);
  		CheckBox1.addKeyListener(graphListener);
  		CheckBoxApprox.addKeyListener(graphListener);
  		CheckBoxExtraPoly.addKeyListener(graphListener);
  		CheckBoxPoly.addKeyListener(graphListener);
  		CheckBoxSpline.addKeyListener(graphListener);
  		ButtonApprox.addKeyListener(graphListener);
  		ButtonSaveImage.addKeyListener(graphListener);
  		ButtonOpen.addKeyListener(graphListener);
  		ButtonGoToCenter.addKeyListener(graphListener);
  		southScroll.addKeyListener(graphListener);
  		Graph.DotsList.addKeyListener(graphListener);
    	Graph.repaint();
    	setVisible(true);
    }
}
