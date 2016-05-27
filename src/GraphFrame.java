import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;

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
	public GraphPanel Graph;
	JPanel Panel2;
	JButton Button1;
	JButton Button2;
	JCheckBox CheckBox1;
	SpinnerModel model;
	JSpinner spinner;
	GraphFrame(String name, int width, int height)
    {
    	super(name);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	//TPanel Panel1 = new TPanel();
    	setLocationRelativeTo(null); //open on center
    	setLayout(new GridBagLayout());
    	setSize(width, height);
    	Graph = new GraphPanel();
    	add(Graph, new 	GridBagConstraints(0, 0, 1, 1, 1, 50, 
				GridBagConstraints.NORTH, 
				GridBagConstraints.CENTER, 
				new Insets(1, 1, 1, 1), 
				getSize().width, 
				getSize().height - 50));
    	addComponentListener(new ComponentListener() {
					@Override
				    public void componentResized(ComponentEvent e) {
				    	add(Graph, new 	GridBagConstraints(0, 0, 1, 1, 1, 50, 
								GridBagConstraints.NORTH, 
								GridBagConstraints.CENTER, 
								new Insets(1, 1, 1, 1), 
								getSize().width, 
								getSize().height - 50));    
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
    	Panel2 = new JPanel();
    	add(Panel2, new		GridBagConstraints(0, 1, 1, 1, 1, 1, 
    						GridBagConstraints.SOUTH, 
    						GridBagConstraints.VERTICAL, new Insets(1, 1, 1, 1), 0, 0));
    	Button1 = new JButton("Открыть файл");
    	Button2 = new JButton("Аппроксимировать");
    	final JFileChooser fileopen = new JFileChooser(); 
    	CheckBox1 = new JCheckBox("Редактирование");
        // Сам слушатель:
        ChangeListener listener = new ChangeListener() {
			@Override
            public void stateChanged(ChangeEvent e) {
                JSpinner js = (JSpinner) e.getSource();
                System.out.println("Введенное значение: " + js.getValue());
            }
        };
    // Конец слушателя 
    
        // Объявление модели JSpinner'а
        model = new SpinnerNumberModel();
        //Объявление JSpinner'а, которого будем слушать
        spinner = new JSpinner(model);
        if (Graph.func.size >= 2)
        {
        	spinner.setModel(new SpinnerNumberModel(2, 2, Graph.func.size, 1));
        }
        else
        {
        	spinner.setModel(new SpinnerNumberModel(0, 0, 0, 1));
        }
        spinner.addChangeListener(listener);
    	Panel2.setLayout(new FlowLayout());
    	Panel2.setSize(getSize().width, 200);
    	Panel2.setBackground(Color.DARK_GRAY);
    	Panel2.add(Button1);
    	Panel2.add(spinner);
    	Panel2.add(Button2);
    	Panel2.add(CheckBox1);
    	Button1.addActionListener(new ActionListener() {
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
    	Button2.addActionListener(new ActionListener() {
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
   	    	   {
   	    		   Graph.editing = true;
   	    	   }
   	    	   else
   	    	   {
   	    		   Graph.editing = false;
   	    	   }
   	    		   
   	       }
      	});          
    	setVisible(true);
    }
	public void DrawPolynom()
	{
		Graph.boolDrawGraphPoly = true;
		Graph.repaint();
	}
	public void DrawApprox()
	{
		Graph.boolDrawGraphApprox = true;
		Graph.repaint();
	}
}
