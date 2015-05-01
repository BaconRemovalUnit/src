/*
 *filename: DisjSets.java
 *Author:SuizhuShengqi
 *Date 2014.4.22
 *Class:CSC172
 *Lab Session:MW	18:15-19:30
 */

public class DisjSets {
	int[] s;
	
	public DisjSets(int numElements)
	{
		s = new int[numElements];
		for(int i =0; i<s.length;i++)
			s[i] = -1;
	}
	
	public void union(int root1,int root2)
	{

		if(s[root2]<s[root1])
			s[root1] = root2;
		else
		{
			if(s[root1] == s[root2])
				s[root1]--;
			s[root2] = root1;
		}

	}
	
	public int find(int x)
	{

		if(s[x]<0)
			return x;
		else
			return s[x] = find(s[x]);
	}

}
