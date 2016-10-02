import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;

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


public class GraphFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	private GraphPanel Graph;
	private JPanel Panel1;
	private JButton ButtonOpen;
	private JButton ButtonApprox;
	private JCheckBox CheckBox1;
	private JCheckBox CheckBoxPoly;
	private JCheckBox CheckBoxApprox;
	private JCheckBox CheckBoxSpline;
	private JCheckBox CheckBoxExtraPoly;
	private SpinnerModel model;
	private JSpinner spinner;
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
	GraphFrame(String name, int width, int height)
    	{
    		super(name);
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
				add(Graph, new 	GridBagConstraints(
					0, 0, 1, 1, 1, 1, 
					GridBagConstraints.NORTH, 
					GridBagConstraints.CENTER, 
					new Insets(1, 1, 1, 1), 
					getSize().width, 
					getSize().height));    
			}
			@Override
			public void componentHidden(ComponentEvent arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void componentMoved(ComponentEvent arg0) {
				// TODO Auto-generated method stub
			}
			@Override
			public void componentShown(ComponentEvent arg0) {
				// TODO Auto-generated method stub
			}
    		});
    		setLocationRelativeTo(null); //open on center
    		setLayout(new GridBagLayout());
    		Panel1 = new JPanel();
    		add(Panel1, new GridBagConstraints(
					1, 0, 1, 1, 1, 1, 
					GridBagConstraints.NORTH, 
					GridBagConstraints.HORIZONTAL, new Insets(20, 1, 1, 1), 200, 0));
    		ButtonOpen = new JButton("Открыть файл");
    		ButtonApprox = new JButton("Аппроксимировать");
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
    		Panel1.add(ButtonApprox);
    		Panel1.add(spinner);
  		   	ButtonApprox.setVisible(false);
  		   	spinner.setVisible(false);
    		ButtonOpen.addActionListener(new ActionListener() {
 			public void actionPerformed(ActionEvent e){
				int ret = fileopen.showDialog(null, "Открыть файл");              
 		       		if (ret == JFileChooser.APPROVE_OPTION) {
 		       			File file = fileopen.getSelectedFile();
 		       			Graph.func = new Function(file);
 		       			Graph.paint.SetFunc(Graph.func);
  		    	   		spinner.setModel(new SpinnerNumberModel(2, 2, Graph.func.size, 1));
        	    		Graph.repaint();
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
  		    	   {
  		    		   Graph.boolDrawGraphPoly = true;
  		    	   }
  		    	   else
  		    	   {
  		    		   Graph.boolDrawGraphPoly = false;
  		    	   }
  		    	   if (Graph.func.GetPointsFlag) Graph.func.interp.InitMatrix();
  		    	   Graph.repaint();
  	       		}
     		});
    		CheckBoxExtraPoly.addActionListener(new ActionListener() {
  		       public void actionPerformed(ActionEvent e){
  		    	   if (CheckBoxExtraPoly.isSelected())
  		    	   {
  		    		   Graph.boolDrawGraphExtraPoly = true;
  		    	   }
  		    	   else
  		    	   {
  		    		   Graph.boolDrawGraphExtraPoly = false;
  		    	   }
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
 		    	   {
 		    		   Graph.boolDrawGraphSpline = true;
 		    	   }
 		    	   else
 		    	   {
 		    		   Graph.boolDrawGraphSpline = false;
 		    	   }
 		    	  if (Graph.func.GetPointsFlag) Graph.func.spline.build_spline();
 		    	   Graph.repaint();
 	       		}
    		});
    		CheckBoxApprox.addActionListener(new ActionListener() {
 		       public void actionPerformed(ActionEvent e){
 		    	   if (CheckBoxApprox.isSelected())
 		    	   {
 		    		   Graph.boolDrawGraphApprox = true;
 		    		   ButtonApprox.setVisible(true);
 		    		   spinner.setVisible(true);
 		    	   }
 		    	   else
 		    	   {
 		    		   Graph.boolDrawGraphApprox = false;
 		    		   ButtonApprox.setVisible(false);
 		    		   spinner.setVisible(false);
 		    	   }
 		    	   if (Graph.func.GetPointsFlag) Graph.func.approx.InitMatrix((int) spinner.getValue());
 		    	   Graph.repaint();
 	       		}
    		});
    		Graph.repaint();
    		setVisible(true);
    	}
}
