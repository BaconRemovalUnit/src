import javax.swing.JFrame;

/*
 *filename: Project4.java
 *Author:SuizhuShengqi
 *Date 2014.4.22
 *Class:CSC172
 *Lab Session:MW	
 *18:15-19:30
 */

public class Project4 {
	public static void main (String[] args)
	{

		PrepPanel prepframe = new PrepPanel("Now Loading...");
		prepframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		prepframe.setSize(400,200);
		prepframe.setResizable(false);
		prepframe.setLocationRelativeTo(null); 
		prepframe.setVisible(true);
		
		JFrame frame = new JFrame("UR Lost");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(new GraphPanel(prepframe.getIntersection(),prepframe.getRoad(),prepframe.getMST()));
		prepframe.dispose();
		frame.setSize(650,630);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null); 
		frame.setVisible(true);
				
		}
}
