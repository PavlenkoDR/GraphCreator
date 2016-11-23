package Graph;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import Another.Pair;

import MathPars.MatchParser;


public class GraphFrame extends JFrame{
	
	
	private static final long serialVersionUID = 1L;
	public GraphPanel Graph;
	private JPanel Panel1;
	private JButton ButtonRemove;
	private JButton ButtonOpen;
	private JButton ButtonApprox;
	private JButton ButtonGoToCenter;
	private JButton ButtonSaveImage;
	private JCheckBox CheckBox1;
	private JCheckBox CheckBoxPoly;
	private JCheckBox CheckBoxApprox;
	private JCheckBox CheckBoxSpline;
	private JCheckBox CheckBoxExtraPoly;
	private JCheckBox CheckBoxLines;
	private JCheckBox CheckBoxDisable;
	private SpinnerModel model;
	private JSpinner spinner;
	private JScrollPane southScroll;
	private JPanel ListPanel;
	private JPanel ButtonsPanel;
	private JPanel CheckBoxPanel;
	private JComboBox<String> ComboBox1;
	

	public static void setQuality(double x)
	{
		Paint.setQuality(x);
	}
	
	public void AddFunc(String s, double x0, double xn, Color c, String name)
	{
		Graph.FunctionList.add(new Pair<Function, Color>(new Function(s, x0, xn, Graph.paint), c));
		//System.out.println(Graph.FunctionList.get(Graph.FunctionList.size() - 1).getL().size);
		Graph.FunctionList.get(Graph.FunctionList.size() - 1);
	   	spinner.setModel(new SpinnerNumberModel(2, 2, Graph.FunctionList.get(Graph.FunctionList.size() - 1).getL().size, 1));
		Graph.DotsList.setListData(Graph.FunctionList.get(Graph.FunctionList.size() - 1).getL().DotsArray.toArray());
		ComboBox1.addItem(name);
    	Graph.repaint();
	}
	
	public void AddFunc(Function f, Color c, String name)
	{
		Graph.FunctionList.add(new Pair<Function, Color>(f, c));
		//System.out.println(Graph.FunctionList.get(Graph.FunctionList.size() - 1).getL().size);
		Graph.FunctionList.get(Graph.FunctionList.size() - 1);
	   	spinner.setModel(new SpinnerNumberModel(2, 2, Graph.FunctionList.get(Graph.FunctionList.size() - 1).getL().size, 1));
		Graph.DotsList.setListData(Graph.FunctionList.get(Graph.FunctionList.size() - 1).getL().DotsArray.toArray());
		ComboBox1.addItem(name);
    	Graph.repaint();
	}
	public void AddFunc(int _size, double[] _X, double[] _F, Color c, String name)
	{	
		Graph.FunctionList.add(new Pair<Function, Color>(new Function(_size, _X, _F), c));
		Graph.FunctionList.get(Graph.FunctionList.size() - 1);
	   	spinner.setModel(new SpinnerNumberModel(2, 2, Graph.FunctionList.get(Graph.FunctionList.size() - 1).getL().size, 1));
		Graph.DotsList.setListData(Graph.FunctionList.get(Graph.FunctionList.size() - 1).getL().DotsArray.toArray());
		ComboBox1.addItem(name);
    	Graph.repaint();
	}
	public void AddFunc(File file, Color c, String name)
	{
		Graph.FunctionList.add(new Pair<Function, Color>(new Function(file), c));
		Graph.FunctionList.get(Graph.FunctionList.size() - 1);
	   	spinner.setModel(new SpinnerNumberModel(2, 2, Graph.FunctionList.get(Graph.FunctionList.size() - 1).getL().size, 1));
		Graph.DotsList.setListData(Graph.FunctionList.get(Graph.FunctionList.size() - 1).getL().DotsArray.toArray());
		ComboBox1.addItem(name);
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
	public void SetDrawLine(boolean f)
	{
 	   Graph.boolDrawGraphLine = f;
 	   CheckBoxLines.setSelected(f);
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
	public GraphFrame(String name, int width, int height)
    {
    	super(name);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
	   	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	//TPanel Panel1 = new TPanel();
    	setLocationRelativeTo(null); //open on center
    	setSize(width, height);

    	//==================================================//
    	//=============== Init components ==================//
    	//==================================================//
    	
    	Graph = new GraphPanel();
    	Panel1 = new JPanel();
	    model = new SpinnerNumberModel();
        spinner = new JSpinner(model);
    	ButtonOpen = new JButton("Открыть файл");
    	ButtonApprox = new JButton("Аппроксимировать");
    	ButtonGoToCenter = new  JButton("Центровать");
    	ButtonSaveImage = new  JButton("Скриншот");
    	CheckBoxLines = new JCheckBox("Линии");
    	CheckBoxExtraPoly = new JCheckBox("Экстраполяция");
    	CheckBoxPoly = new JCheckBox("Полином");
    	CheckBoxApprox = new JCheckBox("Аппроксимация");
    	CheckBoxSpline = new JCheckBox("Сплайн");
    	CheckBox1 = new JCheckBox("Редактирование");
    	ButtonsPanel = new JPanel();
  		ButtonRemove = new JButton("Remove");
  		CheckBoxDisable = new JCheckBox("Enable");
  		CheckBoxPanel = new JPanel();
  		ListPanel = new JPanel();
  	    ComboBox1 = new JComboBox<String>();
    	final JFileChooser fileopen = new JFileChooser(); 
  		Graph.DotsList = new JList<Object>();
  		Graph.DotsList.setLayoutOrientation(JList.VERTICAL);  
  		southScroll = new JScrollPane(Graph.DotsList);

    	//==================================================//
    	//=============== Layouts ==========================//
    	//==================================================//

    	setLayout(new GridBagLayout());
  		ListPanel.setLayout(new GridBagLayout());
  		CheckBoxPanel.setLayout(new FlowLayout());
    	ButtonsPanel.setLayout(new BoxLayout(ButtonsPanel, BoxLayout.Y_AXIS));
    	
    	//==================================================//
    	//=============== Components fill ==================//
    	//==================================================//

    	//*************** Form fill **********************//
    	add(Graph, new 	GridBagConstraints(
    		0, 0, 1, 1, 1, 50, 
			GridBagConstraints.NORTH, 
			GridBagConstraints.CENTER, new Insets(100, 1, 1, 1), 1, 90));
    	add(Panel1, new GridBagConstraints(
			1, 0, 1, 1, 1, 1, 
			GridBagConstraints.NORTH, 
			GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), -80, 1));
    	
    	//*************** Panel1 fill **********************//
    	Panel1.add(ButtonsPanel);
  		Panel1.add(CheckBoxPanel);
  		Panel1.add(ListPanel);
  		
    	//*************** ButtonsPanel fill ****************//
    	ButtonsPanel.add(ButtonOpen);
    	ButtonsPanel.add(CheckBoxLines);
    	ButtonsPanel.add(CheckBoxExtraPoly);
    	ButtonsPanel.add(CheckBoxPoly);
    	ButtonsPanel.add(CheckBoxApprox);
    	ButtonsPanel.add(CheckBoxSpline);
    	ButtonsPanel.add(CheckBox1);
    	ButtonsPanel.add(ButtonGoToCenter);
    	ButtonsPanel.add(ButtonSaveImage);
    	ButtonsPanel.add(ButtonApprox);
    	ButtonsPanel.add(spinner);
    	
    	//*************** CheckBoxPanel fill ***************//
  		CheckBoxPanel.add(ComboBox1);
  		CheckBoxPanel.add(CheckBoxDisable);
  		CheckBoxPanel.add(ButtonRemove);  
    	
    	//==================================================//
    	//=============== Aligments ========================//
    	//==================================================//
  		
    	ButtonOpen.setAlignmentX(Component.LEFT_ALIGNMENT);
    	CheckBoxLines.setAlignmentX(Component.LEFT_ALIGNMENT);
    	CheckBoxExtraPoly.setAlignmentX(Component.LEFT_ALIGNMENT);
    	CheckBoxPoly.setAlignmentX(Component.LEFT_ALIGNMENT);
    	CheckBoxApprox.setAlignmentX(Component.LEFT_ALIGNMENT);
    	CheckBoxSpline.setAlignmentX(Component.LEFT_ALIGNMENT);
    	ButtonGoToCenter.setAlignmentX(Component.LEFT_ALIGNMENT);
    	ButtonSaveImage.setAlignmentX(Component.LEFT_ALIGNMENT);
    	ButtonApprox.setAlignmentX(Component.LEFT_ALIGNMENT);
    	spinner.setAlignmentX(Component.LEFT_ALIGNMENT);

    	//==================================================//
    	//=============== Prefer Sizes =====================//
    	//==================================================//

  		Panel1.setPreferredSize(new Dimension(170, getHeight()));
  		CheckBoxPanel.setPreferredSize(new Dimension(170, 60));
  		ComboBox1.setPreferredSize(new Dimension(170, 25));
  		ListPanel.setPreferredSize(new Dimension(170, getHeight() - ButtonsPanel.getHeight() - CheckBoxPanel.getHeight() - 20));
  		
    	//==================================================//
        if ((Graph.func != null)&&(Graph.func.size >= 2))
        	spinner.setModel(new SpinnerNumberModel(2, 2, Graph.func.size, 1));
        else
        	spinner.setModel(new SpinnerNumberModel(0, 0, 0, 1));
  		ButtonApprox.setEnabled(false);
  		spinner.setEnabled(false);        
  		//Panel1.add(southScroll);
  		ComboBox1.setEditable(true);
  		ButtonRemove.setText("X");

    	//==================================================//
    	//================== Listeners =====================//
    	//==================================================//

  	    GraphListener graphListener = new GraphListener(this);
    	addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
		  		ListPanel.remove(southScroll);
		  		ListPanel.add(southScroll, new GridBagConstraints(
		  				1, 2, 1, 1, 1, 1, 
		  				GridBagConstraints.EAST, 
		  				GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 1, getSize().height));
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
		  		ListPanel.setPreferredSize(new Dimension(170, getHeight() - ButtonsPanel.getHeight() - CheckBoxPanel.getHeight() - 20));
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
		  		ListPanel.remove(southScroll);
		  		ListPanel.add(southScroll, new GridBagConstraints(
		  				1, 2, 1, 1, 1, 1, 
		  				GridBagConstraints.EAST, 
		  				GridBagConstraints.HORIZONTAL, new Insets(1, 1, 1, 1), 1, getSize().height));
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
		  		ListPanel.setPreferredSize(new Dimension(170, getHeight() - ButtonsPanel.getHeight() - CheckBoxPanel.getHeight() - 20));
				Graph.repaint();
			}
    	});
        spinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
        		//JSpinner js = (JSpinner) e.getSource();
        	    //System.out.println("Введенное значение: " + js.getValue());
        	}
        });
    	ButtonOpen.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e){
				int ret = fileopen.showDialog(null, "Открыть файл");              
 		   		if (ret == JFileChooser.APPROVE_OPTION) {
 		   			File file = fileopen.getSelectedFile();
 		   			/*
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
 		   			*/
 		   			AddFunc(file, null, file.getAbsoluteFile().toString());
 		   			ComboBox1.setSelectedIndex(Graph.FunctionList.size() - 1);
 		   		}
 		    }
    	});
    	ButtonApprox.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e){
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
  		    	//if (Graph.func.GetPointsFlag) Graph.func.interp.InitMatrix();
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
  		    	   //if (Graph.boolDrawGraphPoly) Graph.func.interp.InitMatrix();
  		    	   //if (Graph.boolDrawGraphApprox) Graph.func.approx.InitMatrix(Graph.func.nApprox);
  		    	   //if (Graph.boolDrawGraphSpline) Graph.func.spline.build_spline();
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
 		    	//if (Graph.func.GetPointsFlag) Graph.func.spline.build_spline();
 		    	Graph.repaint();
 	       	}
    	});
    	CheckBoxLines.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e){
    			if (CheckBoxLines.isSelected())
    				Graph.boolDrawGraphLine = true;
 		    	else
 		    		Graph.boolDrawGraphLine = false;
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
    	ComboBox1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Graph.selectedID = ComboBox1.getSelectedIndex();
				if (Graph.selectedID != -1)
					Graph.func = Graph.FunctionList.get(Graph.selectedID).getL();
				else
					return;
				CheckBoxDisable.setSelected(Graph.func.Enable);
			   	spinner.setModel(new SpinnerNumberModel(2, 2, Graph.func.size, 1));
				Graph.DotsList.setListData(Graph.func.DotsArray.toArray());
				Thread myThready = new Thread(new Runnable()
				{
					public void run() //Этот метод будет выполняться в побочном потоке
					{
						try {
							for (int i = 0; i < 2000; i++)
							{
								Graph.boolDrawSelectedArea = true;
								Graph.repaint();
								Thread.sleep(1);
							}
							Graph.repaint();
							Graph.boolDrawSelectedArea = false;
							Graph.repaint();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				myThready.start();	//Запуск потока
			}
		});
    	CheckBoxDisable.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Graph.func != null)
					Graph.func.Enable = CheckBoxDisable.isSelected();
				if (Graph.selectedID != -1)
					Graph.FunctionList.get(Graph.selectedID).getL().Enable = CheckBoxDisable.isSelected();
				Graph.repaint();
				
			}
		});
    	/*
    	ButtonRemove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (Graph.selectedID == -1) return;
				Graph.selectedID = ComboBox1.getSelectedIndex();
				if (ComboBox1.getModel().getSize() != 1)
				{
					Graph.FunctionList.remove((Graph.selectedID<0)?0:Graph.selectedID);
					System.out.println(ComboBox1.getModel().getSize());
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ComboBox1.removeItemAt(Graph.selectedID);
					
				}
				else
				{
					Graph.FunctionList.clear();
					ComboBox1.removeAllItems();
				}
				Graph.selectedID = ComboBox1.getSelectedIndex();
				ComboBox1.setSelectedIndex(Graph.selectedID);
				//ComboBox1.setSelectedIndex(Graph.selectedID);
				Graph.repaint();
			}
		});
		*/
    	KeyListener tmpL = new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				/*
				try{
					if (e.getKeyCode() == KeyEvent.VK_ENTER)
					{
						Function tmpfunc;
						if (TextFieldFormulas.getText() == "")
							tmpfunc = new Function("0", Graph.paint);
						else
							tmpfunc = new Function(TextFieldFormulas.getText(), Graph.paint);
						AddFunc(tmpfunc, null, TextFieldFormulas.getText());
						//Graph.func = tmpfunc;
				   		//Graph.paint.SetFunc(Graph.func);
				   		//Graph.repaint();
					}
				} catch (Exception e1) {
					System.err.println("Функция не определена!");
				}
				*/
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			}
		};
  		Graph.addKeyListener(graphListener);
  		Panel1.addKeyListener(graphListener);
  		ListPanel.addKeyListener(graphListener);
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
  		ComboBox1.addKeyListener(graphListener);
    	Graph.repaint();
    	setVisible(true);
    }
}
