import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;




import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;



/*
 *filename: GraphPanel.java
 *Author:SuizhuShengqi
 *Date 2014.4.22
 *Class:CSC172
 *Lab Session:MW	18:15-19:30
 */

public class GraphPanel extends JPanel {
	
	int clicks;
	private Image background;
	private JCheckBox showDot;
	private JCheckBox showRoad;
	private JCheckBox showMST;
	private JCheckBox showRoute;
	private JTextField text1 = new JTextField("");
	private JTextField text2 = new JTextField("");
	private JButton b1 = new JButton("Import Location");
	private Point location = new Point(0,0);
	String start;
	String end;

	HashMap<String,Vertex> InterList = new HashMap();
	HashMap<String,Road> RoadList = new HashMap();
	List<Road> mst;
	List<Vertex> route;
	Vertex startpoint = null;
	Vertex endpoint = null;
	ArrayList<Vertex> road = new ArrayList<>();

	public GraphPanel(final HashMap<String,Vertex> InterList,HashMap<String,Road> RoadList,List<Road> MST)
	{
		this.mst = MST;
		this.InterList = InterList;
		this.RoadList = RoadList;
		ButtonListener listener = new ButtonListener();
		setLayout(null);//clear layout
		
		showDot = new JCheckBox( "show intersections" );
		showDot.setSelected(false);
		showDot.setForeground( Color.WHITE );
		showDot.setContentAreaFilled(false);
		showDot.setFont( new Font( "Arial",1, 15 ) );
		showDot.setBounds( 480, 55, 160, 40 );
		showDot.addActionListener(listener);
		showRoad = new JCheckBox( "show road" );
		showRoad.setSelected(true);
		showRoad.setForeground( Color.WHITE );
		showRoad.setContentAreaFilled(false);
		showRoad.setFont( new Font( "Arial",1, 15 ) );
		showRoad.setBounds( 480, 30, 160, 40 );
		showRoad.addActionListener(listener);
		showMST = new JCheckBox( "show MST" );
		showMST.setSelected(false);
		showMST.setForeground( Color.WHITE );
		showMST.setContentAreaFilled(false);
		showMST.setFont( new Font( "Arial",1, 15 ) );
		showMST.setBounds( 480, 80, 160, 40 );
		showMST.addActionListener(listener);
		showRoute = new JCheckBox( "show route" );
		showRoute.setSelected(true);
		showRoute.setForeground( Color.WHITE );
		showRoute.setContentAreaFilled(false);
		showRoute.setFont( new Font( "Arial",1, 15 ) );
		showRoute.setBounds( 480, 5, 160, 40 );
		showRoute.addActionListener(listener);
	    
		text1.setBounds(370,20, 100, 20);
		text2.setBounds(370,50, 100, 20);
		add(text1);//input start point
		add(text2);//input end point
		
	    b1.addActionListener(new ActionListener() {
	    	
	        public void actionPerformed(ActionEvent e) {
	        	String[] scanin = scanInput();
	        	if(scanin!=null)
	        	{
	        	startpoint = InterList.get(scanin[0]);
	        	endpoint = InterList.get(scanin[1]);
	        	clicks=2;
	        	repaint();
	        	}
	        }
	        
			private String[] scanInput() {//input from the file
				try
				{
					String[] lines = new String[2];
					File input = new File("infile.txt");
					BufferedReader reader = new BufferedReader(new FileReader(input));
					String currline = reader.readLine();
					String[] readin =  currline.split("	");
					lines[0] = readin[1];
					currline = reader.readLine();
					readin = currline.split("	");
					lines[1] = readin[1];
					reader.close();
					return lines;
				}
				catch(Exception e)
				{
					JOptionPane.showMessageDialog(null, "Please check infile.txt", "Incorrect Input",2);

				}
				return null;
			}
	       });
	    
	    b1.setBounds(340,80, 130, 20);
		TextFieldHandler handler = new TextFieldHandler();//processing input

		//check boxes
		add(showDot);
		add(showRoad);
		add(showMST);
		add(showRoute);
		add(b1);
		
		try {
			background = ImageIO.read( getClass().getResource( "map.jpg" ) );//*special thanks to Google co.*
		} catch( Exception e ) {
			JOptionPane.showMessageDialog(null, "Background image not found", "Error",0);
		}
		
		ArrayList<Vertex> toArr = MaptoVertexList(InterList);		
		text1.addKeyListener(handler);
		text2.addKeyListener(handler);
		LocationListener listen = new LocationListener();
		addMouseListener(listen);
		addMouseMotionListener(listen);
	}
	
	public void setStart(Vertex a)
	{
		if(a==null)
			this.startpoint = null;
		else
		this.startpoint = InterList.get(a.id);
	}
	
	public void setEnd(Vertex b)
	{
		if(b==null)
			this.startpoint = null;
		else
		this.endpoint = InterList.get(b.id);
	}
	
	public void paintComponent (Graphics g)
	{
			Dijkstra getRoute = new Dijkstra(this.InterList,this.startpoint);//class for the dijkstra algorithm
			Line2D drawLine;
			Color c = new Color(255,255,255);
			g.setColor(c);
		   Graphics2D g2d = (Graphics2D)g;
		   ArrayList list = MaptoRoadList(RoadList);//road list
			route = getRoute.getRoute(endpoint);//calculating the route

			g.drawImage(background,0,-15, this);//adding map picture to the background
		   //show all the roads
		   if(showRoad.isSelected())
		   for(int i =0; i<list.size();i++)
		   {
			   Road r = (Road) list.get(i);
			   Line2D line = new Line2D.Double(r.v.x,r.v.y,r.w.x,r.w.y);
			   g2d.draw(line);
		   }
		   //show all the intersections
		   if(showDot.isSelected())
			   for(int i =0; i<list.size();i++)
			   {
				   g.setColor(Color.blue);
				   Road r = (Road) list.get(i);
				   Line2D line = new Line2D.Double(r.v.x,r.v.y,r.v.x,r.v.y);
				   Line2D line2 = new Line2D.Double(r.w.x,r.w.y,r.w.x,r.w.y); 
				   g2d.draw(line);
				   g2d.draw(line2);
			   }
		   
		   //show the MST
		   if(showMST.isSelected())
		   {
		   	for(int i = 0; i<mst.size();i++)
		   	{
		   		g.setColor(Color.RED);
		   		Road road = (Road)mst.get(i);  		
		   		Line2D drawMST = new Line2D.Double(road.v.x,road.v.y,road.w.x,road.w.y);
		   		
		   		g2d.draw(drawMST);
		   	}
		   }
		   
		   //draw the shortest path
		   if(showRoute.isSelected())
		   drawList(route,g2d);
		   
		   g.setColor(Color.white);
			Font tr = new Font("Calibri", Font.BOLD, 15);
			g.setFont(tr);
			g.drawString("Start Point", 300, 35);
			g.drawString(" End  Point", 300, 65);
			g.setColor(Color.yellow);
			g.drawString("Made by Shengqi Suizhu", 490, 599);
			g.drawString("Backgournd provided by Google", 0,599);
	}

	private void drawList(List<Vertex> list, Graphics2D g2d) {

		for(int i =0; i<list.size();i++)
		{
			Vertex a = list.get(i);

			if(a.path!=null)
			{
			Line2D temp = new Line2D.Double(a.x,a.y,a.path.x,a.path.y);
			g2d.setColor(Color.green);
			g2d.setStroke(new BasicStroke(3));
			g2d.draw(temp);
			}
		}

		if(startpoint!=null&&endpoint!=null)
		{
		Line2D beg = new Line2D.Double(startpoint.x,startpoint.y,startpoint.x,startpoint.y);
		Line2D end = new Line2D.Double(endpoint.x,endpoint.y,endpoint.x,endpoint.y);
		g2d.setColor(Color.yellow);
		g2d.setStroke(new BasicStroke(6));
		g2d.draw(beg);
		g2d.draw(end);
		}
		g2d.setStroke(new BasicStroke(1));
		
		if(startpoint==null||endpoint==null)
		drawMouseLocation(g2d);
	}


	public void drawMouseLocation(Graphics2D g2d) {
		Vertex dot = getNewVertex(location);
		Line2D circle = new Line2D.Double(dot.x,dot.y,dot.x,dot.y);
		g2d.setColor(Color.yellow);
		g2d.setStroke(new BasicStroke(5));
		g2d.draw(circle);
		g2d.setStroke(new BasicStroke(1));
	}

	public static ArrayList<Road> MaptoRoadList(HashMap<String, Road> map)
	{
		  ArrayList<Road> list = new ArrayList<Road>();
		  for(String key : map.keySet()){
		   list.add(map.get(key));
		  }
		  return list;
	}
	
	public static ArrayList<Vertex> MaptoVertexList(HashMap<String, Vertex> map)
	{
		  ArrayList<Vertex> list = new ArrayList<Vertex>();
		  for(String key : map.keySet()){
		   list.add(map.get(key));
		  }
		  return list;
	}
	
	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			repaint();
		}
	}

	private class TextFieldHandler implements KeyListener//manual input
	{
		public void keyPressed(KeyEvent arg0) {  }
		public void keyTyped(KeyEvent arg0) {  }
		public void keyReleased(KeyEvent event) {
			
		JTextField a = (JTextField)event.getComponent();
		
		if(a==text1)
		{
			try{
				start = a.getText();
				startpoint = InterList.get(start);
				repaint();
			}
			catch(NumberFormatException e){
				}
			
		}
		//a.getText()
		else	if(a == text2)
			{
				try{
				end = a.getText();
				endpoint = InterList.get(end);
				repaint();
				}
				catch(NumberFormatException e){
					}
			}
		}
	}

	private class LocationListener implements MouseListener,MouseMotionListener{//get cursor location
		boolean click = false;
		Vertex current = getNewVertex(location);
		public void mouseMoved(MouseEvent e){
			
			//update text field
			if(startpoint!=null)
			text1.setText(startpoint.id);
			if(endpoint!=null)
			text2.setText(endpoint.id);
			
			//update location
			location = e.getPoint();
			
			//update the closest vertex
			Vertex update = getNewVertex(location);
			
			//update star tpoint location
			if(startpoint==null)
			text1.setText(update.id);

			//update the endpoint
			if(startpoint!=null&&clicks!=2)
			endpoint = update;

			//update map
			repaint();
		}
		public void mousePressed(MouseEvent e){}
		public void mouseReleased(MouseEvent e){}
		public void mouseClicked(MouseEvent e)
		{
			click = true;
			Vertex temp = getNewVertex(location);
			
			if(startpoint==null&&endpoint==null)
			{
			setStart(temp);
			clicks = 1;
			}
			else if(clicks==1)
			{
			endpoint = temp;	
			clicks = 2;
			}
			else if(clicks==2||(startpoint!=null&&endpoint!=null))
			{
			startpoint = null;
			endpoint =null;
			text1.setText("");
			text2.setText("");
			clicks=0;
			repaint();
			}
		}
		public void mouseEntered(MouseEvent e){}
		public void mouseExited(MouseEvent e){}
		public void mouseDragged(MouseEvent e){}
	}

	
	private double getDistance(Point mouse, Vertex a)
	{
		return Math.pow(Math.pow((mouse.x-a.x),2)+Math.pow((mouse.y-a.y),2),0.5);
	}
	
	public Vertex getNewVertex(Point location) {
		List<Vertex> list = new ArrayList<Vertex>(InterList.values());
		Vertex min = list.get(0);
		double minDst = getDistance(location,min);
		for(int i = 1;i<list.size();i++)
		{
			Vertex temp = list.get(i);
			double tempDst = getDistance(location,temp);
			if(tempDst<minDst)
			{
				min = temp;
				minDst = tempDst;
			}
		}
		return min; 
	}
}