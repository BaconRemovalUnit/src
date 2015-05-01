import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

/*
 *filename: Prim.java
 *Author:SuizhuShengqi
 *Date 2014.4.29
 *Class:CSC172
 *Lab Session:MW	18:15-19:30
 */

public class Kruskal {
	
	int numVerticies;
	Road test;
	
	List<Vertex> vertex = new ArrayList<Vertex>();
	List<Road> road = new ArrayList<Road>();
	ArrayList<Road> mst = new ArrayList<>();
	PriorityQueue<Road> pq = new PriorityQueue<>();

	public Kruskal(HashMap<String,Vertex> vmap,HashMap<String,Road> rmap)
	{
		vertex = new ArrayList<Vertex>(vmap.values());
		road = new ArrayList<Road>(rmap.values());
		pq = new PriorityQueue<>(road);
		numVerticies = vertex.size();
		kruskal();
	}
	
	public void kruskal()
	{
		int uset;
		int vset;
		DisjSets ds = new DisjSets(numVerticies);
		System.out.println(numVerticies);
		while(!pq.isEmpty())
		{
			Road e = pq.poll();
			uset = ds.find(e.v.setID);
			vset = ds.find(e.w.setID);

			if(uset != vset)
			{
				this.mst.add(e);
				ds.union(uset,vset);
			}
		}
	}
	
	public ArrayList<Road> getMST()
	{
		return mst;
	}
}
