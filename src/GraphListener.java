import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;



public class GraphListener implements KeyListener{

	GraphFrame frame;
	GraphListener(GraphFrame _frame){frame = _frame;}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
        String Key = KeyEvent.getKeyText(e.getKeyCode());
        frame.Graph.ActiveKey = e.getKeyCode();
		//System.out.println(Key);
        switch(Key)
        {
        case "2":
        	if (frame.Graph.boolDrawGraphApprox)
        	{
        		frame.Graph.func.nApprox = (frame.Graph.func.nApprox < frame.Graph.func.size)?frame.Graph.func.nApprox + 1:frame.Graph.func.size;
        		frame.Graph.func.approx.InitMatrix(frame.Graph.func.nApprox);
        		frame.repaint();
        	}
           	break;
        case "1":
        	if (frame.Graph.boolDrawGraphApprox)
        	{
        		frame.Graph.func.nApprox = (frame.Graph.func.nApprox > 2)?frame.Graph.func.nApprox - 1:2;
        		frame.Graph.func.approx.InitMatrix(frame.Graph.func.nApprox);
        		frame.repaint();
        	}
           	break;
        }
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//System.out.println("keyReleased");
        frame.Graph.ActiveKey = 0;
	}    
}
