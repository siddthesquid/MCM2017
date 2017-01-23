import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

final public class GUISimulator {

    JFrame frame;
    DrawPanel drawPanel;
    public final int CELL_WIDTH = 5;
    public final int CELL_HEIGHT = 5;
    public final int ROAD_PADDING = 20;

    public void init() {
    	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame = new JFrame("Highway Traffic Simulator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        drawPanel = new DrawPanel();
        frame.getContentPane().add(BorderLayout.CENTER, drawPanel);
        frame.setResizable(false);
        frame.setSize(d.width, d.height);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public void updateHighway(Highway h, int beginColumn, int endColumn){
    	drawPanel.setComponents(h, beginColumn, endColumn);
        frame.repaint();
    }
}

class DrawPanel extends JPanel {
	
    private static final long serialVersionUID = 1L;
    
    public final int CELL_WIDTH = 5;
    public final int CELL_HEIGHT = 5;
    public final int ROAD_PADDING = 20;
    
    Highway h;
    int beginColumn;
    int endColumn;
    
    public void setComponents(Highway h, int beginColumn, int endColumn){
    	this.h = h;
    	this.beginColumn = beginColumn;
    	this.endColumn = endColumn;
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        if(h!=null){
	    	endColumn = Math.min(h.highway.size(), endColumn);
	    	int curRoadY = ROAD_PADDING;
	    	int curColumnX = 0;
	    	int lanes = h.maxLanes;
	    	while(beginColumn < endColumn && (curRoadY + lanes + ROAD_PADDING) < getHeight()){
	    		ArrayList<HighwayGridObject> column = h.highway.get(beginColumn);
	    		for(int i = lanes - column.size(); i < lanes;i++){
	    			if(i >= lanes - column.size()){
	    				HighwayGridObject hgo = column.get(i-(lanes - column.size()));
	    				Color c;
	    				if(hgo.hasCar){
	    					switch(hgo.car.velocity){
	    					case 0:
	    						c = Color.white;
	    						break;
	    					case 1:
	    						c = Color.red;
	    						break;
	    					case 2:
	    						c = Color.ORANGE;
	    						break;
	    					case 3:
	    						c = Color.YELLOW;
	    						break;
	    					default:
	    						c = Color.GREEN;
	    						break;
	    					}
	    				} else if(hgo.openSpace) {
	    					c = Color.GRAY;
	    				} else {
	    					c = Color.BLACK;
	    				}
	    				g.setColor(c);
	    				g.fillRect(curColumnX, curRoadY + i*CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
	    				if(hgo.hasCar && hgo.car.exiting){
	    					g.setColor(Color.BLACK);
	    					g.fillRect(curColumnX, curRoadY + i*CELL_HEIGHT, 2, CELL_HEIGHT);
	    				}
	    				if(hgo.hasCar && hgo.car.autonomous){
	    					g.setColor(Color.BLACK);
	    					g.drawRect(curColumnX, curRoadY + i*CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
	    				}
	    			}
	    		}
	    		HighwayGridObject hgo = h.exitLane.get(beginColumn);
				Color c;
				if(hgo.hasCar){
					switch(hgo.car.velocity){
					case 0:
						c = Color.white;
						break;
					case 1:
						c = Color.red;
						break;
					case 2:
						c = Color.ORANGE;
						break;
					case 3:
						c = Color.YELLOW;
						break;
					default:
						c = Color.GREEN;
						break;
					}
				} else if(hgo.openSpace) {
					c = Color.GRAY;
				} else {
					c = Color.BLACK;
				}
				g.setColor(c);
				g.fillRect(curColumnX, curRoadY + lanes*CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
				if(hgo.hasCar && hgo.car.exiting){
					g.setColor(Color.BLACK);
					g.fillRect(curColumnX, curRoadY + lanes*CELL_HEIGHT, 2, CELL_HEIGHT);
				}
				if(hgo.hasCar && hgo.car.autonomous){
					g.setColor(Color.black);
					g.drawRect(curColumnX, curRoadY + lanes*CELL_HEIGHT, CELL_WIDTH, CELL_HEIGHT);
				}
	    		beginColumn++;
	    		curColumnX+=CELL_WIDTH;
	    		if(curColumnX + CELL_WIDTH > getWidth()) {
	    			curColumnX = 0;
	    			curRoadY+=(ROAD_PADDING + (lanes + 1) * CELL_HEIGHT);
	    		}
	    	}
        }
    }
}