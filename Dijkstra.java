import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;

/*
 *filename: Dijkstra.java
 *Author:SuizhuShengqi
 *Date 2014.4.26
 *Class:CSC172
 *Lab Session:MW	18:15-19:30
 */

public class Dijkstra {
	
	HashMap<String,Vertex> map;
	List<Vertex> list = new ArrayList<Vertex>();

	Vertex start;
	Dijkstra(HashMap<String,Vertex> map,Vertex start)
	{
	this.map = (HashMap<String, Vertex>) map.clone();
	this.start = start;
	dijkstra(map,start);
	}
	
	public void dijkstra(HashMap<String,Vertex> map,Vertex start)//algorithum
	{
		if(start!=null)
		{
		list = new ArrayList<Vertex>(map.values());
		for(int i = 0; i<list.size();i++)
		{
		Vertex temp = list.get(i);
		temp.dist = Integer.MAX_VALUE;
		if(temp.id.equals(start.id))
			temp.dist = 0.0;
		//temp.known = false;
		temp.path = null;
		}
		
		PriorityQueue<Vertex> pq = new PriorityQueue<>();
		pq.add(start);
		while(!pq.isEmpty())
		{
			Vertex u = pq.poll();
			
			for(int i = 0; i<u.neighbor.size();i++)
			{
				Vertex v = u.neighbor.get(i);
				double alt = u.dist + v.getLength(u, v);
				if(alt<v.dist)
				{
					v.dist = alt;
					v.path = u;
					pq.add(v);
				}
			}
		}
		}
	}
	
 	public List<Vertex> getRoute(Vertex end)
	{
		List<Vertex> list = new ArrayList<Vertex>();
		Vertex find = end;
		while(find!=null)
		{
			list.add(find);
			find = find.path;
		}
		return list;
	}
	
	
}
