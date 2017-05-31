package triangle;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.plaf.synth.SynthSeparatorUI;

public class SierpinskiTriangle {
	public static int width = 500;
	public static int height= 500;
	public static int steps = 0;
	

	JFrame frame;
	JPanel panel;

	@SuppressWarnings("serial")
	public void display() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				int[][] startingTriangle = initialiseStartingPattern(g);
				fill(g, startingTriangle );
				int[][] firsOne = {{20,20},{60,20},{40,40}};
				//fill(g,firsOne);
			}
			private int[][] initialiseStartingPattern(Graphics g) {
				paintBackground(g);
				Dimension position = new Dimension(0,height);
				int maxsize = biggestTriangleWidth();
				int[][]trianglePoints = paintSierpinskiTriangle(g,position, 1, maxsize,true,Color.red);
				//get the coordinates of the left & to/bottom corner of the triangle
				Dimension firstPoint = new Dimension(trianglePoints[0][0], trianglePoints[0][1]);
				//System.out.println("first point" + firstPoint.toString());
				Dimension secondPoint = new Dimension(trianglePoints[1][0], trianglePoints[1][1]); 
				//System.out.println("second point" + secondPoint.toString());
				Dimension thirdPoint = new Dimension(trianglePoints[2][0], trianglePoints[2][1]); 
				//System.out.println("third point" + thirdPoint.toString()+"\n");
				//System.out.println("midpoint between first & second" + getMidpoint(firstPoint,secondPoint));
				//System.out.println("midpoint between first & third" + getMidpoint(firstPoint,thirdPoint));
				//System.out.println("midpoint between second & third" + getMidpoint(secondPoint,thirdPoint)+ "\n");
				
				return paintSierpinskiTriangle(g,getMidpoint(firstPoint,secondPoint),3, maxsize/2 ,false, Color.black);
				
			}

			private void fill(Graphics g, int[][] trianglePoints) {
				//initialisePoints
				Dimension firstPoint = new Dimension(trianglePoints[0][0], trianglePoints[0][1]);
				Dimension secondPoint = new Dimension(trianglePoints[1][0], trianglePoints[1][1]); 
				Dimension thirdPoint = new Dimension(trianglePoints[2][0], trianglePoints[2][1]); 
				int size = (int) Math.abs(secondPoint.getWidth()-firstPoint.getWidth());
				//1 step recursion
			
				int[][] firstOne = paintSierpinskiTriangle(g,getMidpoint(firstPoint,secondPoint),3, size/2 ,false, Color.black);
				if (steps<5){
					steps++;
					System.out.println(steps);
					int[][] firsOne = {{20,20},{60,20},{40,40}};
					fill(g,firsOne);
					System.out.println("beginnRecur");
					
				}
				
				paintSierpinskiTriangle(g,getMidpoint(firstPoint,thirdPoint),2, size/2 ,false, Color.black);
				paintSierpinskiTriangle(g,getMidpoint(secondPoint,thirdPoint),1, size/2 ,false, Color.black);
				
				
			}
		
		};
		panel.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				panel.repaint();
			}
		});
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.CENTER);
		frame.pack();
		frame.setSize(width, height);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SierpinskiTriangle triangle = new SierpinskiTriangle();
		triangle.display();
	}
	
	public void paintBackground(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		//nice border
		g2.setBackground(Color.yellow);
		g2.clearRect(0, 0, width, height);
		//g2.draw3DRect(20, 20, size.width - 40, size.height - 40, true);
	}

	public int[][] paintSierpinskiTriangle(Graphics g,Dimension position,int cornerNumber, int triangleWidth, boolean up, Color color) {
		Graphics2D g2 = (Graphics2D) g;

		int xPosition = (int) position.getWidth();
		int yPosition = (int) position.getHeight();
		int triangleHeight = (int) ((Math.sqrt(3)/2)*triangleWidth);
		
		//initialisiere die 3 Punkte
		int[] corner1 = new int[2]; //left one
		int[] corner2 = new int[2]; //right one
		int[] corner3 = new int[2]; //top/bottom one
		
		//abhängig welcher von welchem Eckpunkt begonnen werden soll
		// [0] x Werte  [1] y Werte
		if(cornerNumber == 1){//wenn linke Ecke
			corner1[0]= xPosition;
			corner1[1]= yPosition;
			corner2[0]= xPosition+triangleWidth;
			corner2[1]= yPosition;
			corner3[0]= xPosition+(triangleWidth/2);
			if(up){corner3[1]= yPosition-triangleHeight;}else{corner3[1]=yPosition+triangleHeight;}
		}else if (cornerNumber==2){//wenn rechte Ecke
			corner1[0]= xPosition-triangleWidth;
			corner1[1]= yPosition;
			corner2[0]= xPosition;
			corner2[1]= yPosition;
			corner3[0]= xPosition-(triangleWidth/2);
			if(up){corner3[1]= yPosition-triangleHeight;}else{corner3[1]=yPosition+triangleHeight;}
		}else if(cornerNumber==3){
			corner1[0]= xPosition-triangleWidth/2;
			if(up){corner1[1]= yPosition+triangleHeight;}else{corner1[1]=yPosition-triangleHeight;}
			corner2[0]= xPosition+triangleWidth/2;
			if(up){corner2[1]= yPosition+triangleHeight;}else{corner2[1]=yPosition-triangleHeight;}
			corner3[0]= xPosition;
			corner3[1]= yPosition;
		}
		
		//draw it
		g2.setColor(color);
		int[] xPoints = {corner1[0],corner2[0],corner3[0]};
		int[] yPoints = {corner1[1],corner2[1],corner3[1]};
		int nPoints = 3;
		g2.drawPolygon(xPoints,yPoints,nPoints);
		g2.fillPolygon(xPoints,yPoints,nPoints);	
		
		//gib die Punkte des Dreiecks zurück(nötig für die MidpointBerechnung)
		int[][] trianglePoints = new int[3][2]; //das erste sind die Drei pukte, das zweite Array sind die x und y Werte
		
		trianglePoints[0][0]=corner1[0];
		trianglePoints[0][1]=corner1[1];
		trianglePoints[1][0]=corner2[0];
		trianglePoints[1][1]=corner2[1];
		trianglePoints[2][0]=corner3[0];
		trianglePoints[2][1]=corner3[1];
		
		return trianglePoints;
	}
	
	public int biggestTriangleWidth(){
		if(height>=width){
			return width;
		}else{
			int result = (int) (height/(Math.sqrt(3)/2));
			return result;
		}
	}
	
	public Dimension getMidpoint(Dimension firstPoint, Dimension secondPoint){
		int pointY = (int) ((firstPoint.getHeight()-secondPoint.getHeight())/2+secondPoint.getHeight());
		int pointX = (int) ((firstPoint.getWidth()-secondPoint.getWidth())/2+secondPoint.getWidth());
		Dimension midpoint = new Dimension(Math.abs(pointX),Math.abs(pointY));
		return midpoint;
	}
}
