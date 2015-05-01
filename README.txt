=== Project4 READEME ===
Author:Suizhu Shengqi
E-mail:ssuizhu@u.rochester.edu
Class:CSC172
Lab Session:MW:6:15-7:30

=== About Project4===
The project is using basic graphs to draw out the monroe county.
First I put the data in the prepPanel.
I used hashmap to store the input, and then used kruskal's algorithm to get the
Minimum spanning tree(MST).
Then I pass in the two hashmaps and the MST, did the graphics thing, added the 
background, imported the dijkstra algorithm.
For the dijkstra's algorithm and kruskal's algorithm I made class for them,
for the kruska method, created a helper class "disjset"

Used java's priority queue, arraylist and hash map to achieve the algorithm.

=== How to Run==
 in cmd type in: javac Project4.java then
	type in: java Project4

	to enter own routes, do it in the text field, or put route data in infile.txt
	
=== Files in Project4 ===
Project4.java			Main Class
PrepPanel.java			Preparation class
GraphClass.java			Graphing class
Dijkstra.java			Helper class for finding the shortest path
Kruskal.java			Helper class for finding the MST
DisjSets.java			Helper class for Kruskal.java
Vertex.java				Intersection class
Road.java				Road class
map.jpg					Background
infile.txt				File input
monroe-county.tab		Data needed for making the graph
README.txt				this file

=== Specia Thanks ===
Google Map				Providing the background
Wikipedia				Pseudocode for the dijkstra algorithm