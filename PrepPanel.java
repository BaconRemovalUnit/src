import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 *filename: PrepPanel.java
 *Author:SuizhuShengqi
 *Date 2014.4.24
 *Class:CSC172
 *Lab Session:MW	18:15-19:30
 */

public class PrepPanel extends JFrame{
	
	String name;
	int currline = 1;
	int setVal = 0;
	String line;
	String[] readin;
	HashMap<String,Vertex> InterList = new HashMap();
	HashMap<String,Road> RoadList = new HashMap();
	List<Road> mst;
	ArrayList road = new ArrayList();
	private JLabel label = new JLabel("Now Loading...");
	
	BufferedReader reader = null;
	File input = new File("monroe-county.tab");
	PrepPanel(String name)
	{
		this.name = name;
		this.setTitle(name);
		setLayout(null);
		label.setFont(new Font( "Arial", Font.BOLD, 20 ) );
		label.setBounds(100, 50, 200, 50);
		add(label,BorderLayout.WEST);
		
	try
	{
		reader = new BufferedReader(new FileReader(input));
		
		while((line = reader.readLine()) != null)//creating the hash table
		{
			currline++;
			readin = line.split("	");
			if(readin[0].equals("i"))
			{
			Vertex temp = new Vertex(readin[1],Double.parseDouble(readin[2]),Double.parseDouble(readin[3]));
			temp.setID = setVal++;
			InterList.put(readin[1], temp);
			
			}
			else if(readin[0].equals("r"))
			{
			Vertex t1 = InterList.get(readin[2]);
			Vertex t2 = InterList.get(readin[3]);
			Road temp2 = new Road(readin[1],t1,t2);
			RoadList.put(readin[1], temp2);
			
			t1.setNeighbor(t2);
			t2.setNeighbor(t1);
			InterList.put(t1.id, t1);
			InterList.put(t2.id, t2);
			}
		}
			List<Road> roads = MaptoList(RoadList);
			Kruskal cal = new Kruskal(InterList,RoadList);
			mst = cal.getMST();
			

	}
	
	catch(Exception e)
	{
		JOptionPane.showMessageDialog(null, "Please check monroe-county.tab", "Incorrect Input",2);
	}
	}
	
	public void paintComponent(Graphics g)
	{
		g.drawLine(0,0,100,100);
	}
	
	public HashMap<String,Road> getRoad()
	{
		return RoadList;
	}
	
	public HashMap<String,Vertex> getIntersection()
	{
		return InterList;	
	}
	
	public List<Road> getMST()
	{
		return mst;
	}
	


	public static ArrayList<Road> MaptoList(HashMap<String, Road> map)
	{
		  ArrayList<Road> list = new ArrayList<Road>();
		  for(String key : map.keySet()){
		   list.add(map.get(key));
		  }
		  return list;
	}
}
