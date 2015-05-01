import java.util.ArrayList;
import java.util.List;

/*
 *filename: Vertex.java
 *Author:SuizhuShengqi
 *Date 2014.4.20
 *Class:CSC172
 *Lab Session:MW	18:15-19:30
 */
//hashmap
public class Vertex implements Comparable<Vertex>{//intersection class

	// boolean known;
	 double dist;
	 int setID;
	 double	x;
	 double	y;
	 String id;
	List<Vertex> neighbor = new ArrayList<>();
	 Vertex path;

	 
	 Vertex(String id,double x, double y)
	 {
		this.id = id;
		this.x = x;
		this.y = y;
		this.setID = 0;
	 }
	 
	 Vertex()
	 {
		 this.id = null;
		 this.x = -1;
		 this.y = -1;
	 }

	 public double getLength(Vertex v, Vertex w)
	 {
		double x = v.x-w.x;
		double y = v.y-w.y;
		return Math.sqrt(x*x+y*y);

	 }
	 
	 public void setDist(double i)
	 {
		 this.dist = i;
	 }
	 
	 
	 public void setPath(Vertex v)
	 {
		 this.path = v;
	 }
	 
	 public void setNeighbor(Vertex v)
	 {
		this.neighbor.add(v);
	 }
	 
	 public double getDist()
	 {
		 return dist;
		
	 }
	 
	 public String toString()
	 {
		 return "ID: "+this.id+" X:"+this.x+", Y:"+this.y;
	 }

	 @Override
	public int compareTo(Vertex o) {
		return this.dist>o.dist ? 1 : -1;
	}

}

class Road implements Comparable<Road>//road class is the helper class for the vertex(intersection) class
{
	String id;
	Vertex v = new Vertex();
	Vertex w = new Vertex();
	double distance;
	
	public Road(String id,Vertex v, Vertex w)
	{
		this.v = v;
		this.w = w;
		this.w.path = this.v;
		this.id = id;
		this.distance = Math.pow(Math.pow((v.x-w.x),2)+Math.pow((v.y-w.y),2), 0.5);
		
	}
	
	@Override
	public int compareTo(Road o) {
		return this.distance<o.distance ? -1 : 1;
	}

}

