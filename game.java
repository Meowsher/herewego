import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Arrays;
import java.io.*;
import java.util.*;

public class game extends JFrame implements MouseListener{
  private int x;
  private int y;
  private int node_count = 0;
  private int node_print = 0;
  private int size = 20;
  static Color Current_color = null;
  private ArrayList<Node> nodeList = new ArrayList<Node>();
  private int xPart;
  private int yPart;
  private boolean paintGame;
  public final static String COMMENT = "//";
  private ArrayList<Edge> edgeList = new ArrayList<Edge>(); 
  
  

  static String Col_name[] = {"black","red","green","blue","purple",
  "white","yellow","pink","brown","orange","cyan","gray"};

  static Color colors[] =
  {Color.black,Color.red,Color.green,Color.blue,new Color(128,0,128),
  Color.white,Color.yellow,new Color(255,0,144),new Color(92,51,23),
  new Color(253,106,2),Color.cyan,Color.gray};

  public game() {
    setSize(1000,650);
    setLocationRelativeTo(null);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    addMouseListener(this);
    setBackground(Color.LIGHT_GRAY);
    ReadGraph();
    getNodes();
    paintGame();
    
  }

  public void paint(Graphics g) {
  	if (paintGame){
  	  for (int i=0; i<nodeList.size(); i++){
  	  	  x = nodeList.get(i).getX();
  	  	  y = nodeList.get(i).getY();
  	  	  g.drawOval(x-10, y-10, 20, 20);
  	  }
  	  for(int i = 0; i<edgeList.size(); i++){ 
  	  g.drawLine(	  
  	  	  nodeList.get(edgeList.get(i).Node1).getX(), 
  	  	  nodeList.get(edgeList.get(i).Node1).getY(),
  	  	  nodeList.get(edgeList.get(i).Node2).getX(),
  	  	  nodeList.get(edgeList.get(i).Node2).getY()
  	  	  );
  	  }
  	  g.setColor(Color.black);
  	  g.drawLine(30,60,30,550);
  	  g.drawLine(30,60,900,60);
  	  g.drawLine(900,60,900,550);  	 
  	  g.drawLine(30,550,900,550);
  	}
   else{
  		g.setColor(Current_color);
  		g.fillOval(x-10,y-10,20,20);
  		
  	}
    
}
public void mousePressed(MouseEvent e)   {
  x = e.getX();
  y = e.getY();
  paintGame = false;
  if(withinNodes(x,y)){
  	for(int j = 0; j<nodeList.size();j++){
  		if (x < nodeList.get(j).getX() + 10 &&
  	 	 	 x > nodeList.get(j).getX() - 10 &&
  	  	  	 y < nodeList.get(j).getY() + 10 &&
  	  	  	 y > nodeList.get(j).getY() - 10){
  	  	 			x = nodeList.get(j).getX();
  	  	 			y = nodeList.get(j).getY();
  	
  	  	 			System.out.println(x + " " + y);
  	  	 			repaint();
    		}
    }
  }
  
  else{System.out.println("out of bounds");}
}

private boolean withinNodes(int x,int y){
  for(int j = 0; j<nodeList.size();j++){
  	  if (x < nodeList.get(j).getX() + 10 &&
  	  	  x > nodeList.get(j).getX() - 10 &&
  	  	  y < nodeList.get(j).getY() + 10 &&
  	  	  y > nodeList.get(j).getY() - 10){
  	  		return true;
  	  	  }
  }
  return false;
}

public static void main(String args[]) {
  createGame();
}

  public static void createGame(){
    game c = new game();
    JToolBar tool = new JToolBar();
    tool.setFloatable(false);

    @SuppressWarnings("unchecked") JComboBox box = new JComboBox(new String[]{ //had to surpress a warning here, changes nothing.
      "black","red","green","blue","purple","white","yellow","pink","brown","orange","cyan"
      ,"gray"
    });
    tool.add(box);

    box.addActionListener (new ActionListener () {
    public void actionPerformed(ActionEvent e) {
      for (int i = 0; i < 12; i++) {
        if( box.getSelectedItem() == Col_name[i]){
          Current_color = colors[i];
        }
      }
    }
});

    Container contentPane = c.getContentPane();
    contentPane.add(tool, BorderLayout.NORTH);
    c.setVisible(true);
  }
  private boolean checkMinDistance(int aX, int aY, int bX, int bY){
    double eu1 = Math.pow(aX - bX, 2);
    double eu2 = Math.pow(aY - bY, 2);
    return (Math.sqrt(eu1 + eu2)>70);
  }



  public void mouseReleased(MouseEvent e ){}
  public void mouseEntered(MouseEvent e){}     
  public void mouseExited(MouseEvent e){}
  public void mouseClicked(MouseEvent e) {}

//generates a node from where all other nodes are randomly placed while keeping the necessary distance from eachother.
  public void getNodes(){
  	  if(node_count==0) {
  	  	  System.out.println("there are no nodes");
  	  	  return;
  	  }
  	  	  int i = 0;
  	  	  while (i < node_count){
  	  	  	  int randomX = ThreadLocalRandom.current().nextInt(40, 890);
  	  	  	  int randomY = ThreadLocalRandom.current().nextInt(70, 540);
  	  	  	  
  	  	  	  
  	  	  	  	  for (int x=0; x<nodeList.size(); x++){
  	  	  	  	  	  xPart = nodeList.get(x).getX();
  	  	  	  	  	  yPart = nodeList.get(x).getY();
  	  	  	  	  	  if (!checkMinDistance(randomX, randomY, xPart, yPart)){
  	  	  	  	  	  			break;
  	  	  	  	  	  }
  	  	  	  	  }
  	  	  	  	 if (!checkMinDistance(randomX, randomY, xPart, yPart)){
  	  	  	  	 	 continue;
  	  	  	  	 	}
  	  	  	  	 	
  	  	  	  	 Node n = new Node();
  	  	  	 	 n.setLocation(randomX,randomY);
  	  	  	 	 nodeList.add(n);
  	  	  	 	 i++;
  	  	  }
  }	

  		  
  	  	  

  	  
  	  	 
  	  	  /*
  	  	   //checking if the euclid distance is working and that we're getting the right locations.
  	  	  for (int x=0; x<nodeList.size(); x++){
  	  	  	  System.out.println(nodeList.get(x).getX());
  	  	  	  System.out.println(nodeList.get(x).getY());
  	  	  	  System.out.println("-");
  	  	  	  */
  	  	  
  public void paintGame(){
  	  if(paintGame){
  	  	  paintGame = false;
  	  	  System.out.print("paintGame is false now");
  	  }
  	 else{
  	  	  paintGame = true;
  	  }
  	  repaint();
  	 
  	  //System.out.println("node was here");
  	  
  }
  
//this is a slightly edited version of the code steven provided us with in the first phase of the project.  
public void ReadGraph(){							
			boolean seen[] = null;
			File inputfile = new File("GeneratedGraph.txt");
			
			//! n is the number of vertices in the graph
			int n = -1;
			node_count = n;
			
			//! m is the number of edges in the graph
			int m = -1;
			
			
			try 	{ 
			    	FileReader fr = new FileReader(inputfile);
			        BufferedReader br = new BufferedReader(fr);

			        String record = new String();
					
					//! -----------------------------------------
			        while ((record = br.readLine()) != null)
						{
						if( record.startsWith("//") ) continue;
						break; // Saw a line that did not start with a comment -- time to start reading the data in!
						}
	
					if( record.startsWith("VERTICES = ") )
						{
						n = Integer.parseInt( record.substring(11) );					
						System.out.println(COMMENT + " Number of vertices = "+n);
						}

					seen = new boolean[n+1];	
						
					record = br.readLine();
					
					if( record.startsWith("EDGES = ") )
						{
						m = Integer.parseInt( record.substring(8) );					
						}
												
					for( int d=0; d<m; d++)
						{
						record = br.readLine();
						String data[] = record.split(" ");
						if( data.length != 2 )
								{
								System.out.println("Error! Malformed edge line: "+record);
								System.exit(0);
								}
						Edge f = new Edge();
						edgeList.add(f);
						
						f.setNode1(Integer.parseInt(data[0]));
						f.setNode2(Integer.parseInt(data[1]));

						seen[ f.Node1 ] = true;
						seen[ f.Node2 ] = true;
						
						System.out.println(COMMENT + " Edge: "+ f.Node1 +" "+f.Node2);
				
						}
									
					String surplus = br.readLine();
					if( surplus != null )
						{
						if( surplus.length() >= 2 ) System.out.println(COMMENT + " Warning: there appeared to be data in your file after the last edge: '"+surplus+"'");						
						}
					
					}
			catch (IOException ex)
				{ 
		        // catch possible io errors from readLine()
			    System.out.println("Error! Problem reading file "+inputfile);
				System.exit(0);
				}

			for( int x=1; x<=n; x++ )
				{
				if( seen[x] == false )
					{
				System.out.println(COMMENT + " Warning: vertex "+x+" didn't appear in any edge : it will be considered a disconnected vertex on its own.");
					}
				}
			}
		}
 	 
 	 	 
 	 	 
 	 	 
 	 



  


  	
  
  	
  
  
  	  

/*
int cola  = (int)(Math.random() * 255);
int colb  = (int)(Math.random() * 255);
int colc  = (int)(Math.random() * 255);
Color rand = new Color(cola, colb, colc);
 */

 class Node {
 	 private int nodeX;
 	 private int nodeY;		
 	 	public Node(){
 	 	}
 	 	
 	 	public void setLocation(int x,int y){
 	 		nodeX = x;
 	 		nodeY = y;
 		}
 		public int[] getLocation(){
 			int[] location = {nodeX, nodeY};
 			return location;
 		}
		
 		public int getX(){
 			return nodeX;
 		}
 		public int getY(){
 			return nodeY;
 		}
 		public void saveColor(){
 			
 		}
 }

 class Edge{
 	 public int Node1;
 	 public int Node2;
 	 public Edge(){
 	 }
 	 
 	 public void setNode1(int a){
 	 	 Node1 = a;
 	 }
 	 public void setNode2(int a){
 	 	 Node2 = a;
 }
 }
 	 
 	 


	
		
