# Lab Report
## Exercise 07: Recursive Triangles

_Autoren: Dennis Loska, 30.05.2017_

## First set up a Window that can handle drawing. Can you get the Window to draw an equilateral triangle? What is the largest one you can get on the screen? You can use this scaffold: https://github.com/htw-imi-info2/SierpinskyTriangleStart

Zunächst haben wir das Projekt mit Hilfe des angegebenen Links erstellt und getestet, ob es funktioniert. Beim Starten der Klasse war ein leeres Canvas bzw. JFrame samt JPanel zu sehen - soweit so gut. Nun wird in der paintSierpinskiTriangle()-Methode das Dreieck gezeichnet:

'''java

	public void paintSierpinskiTriangle(Graphics g, Dimension size) {
		Graphics2D g2 = (Graphics2D) g;
		Graphics2D tri = (Graphics2D) g;
		g2.setBackground(Color.white);
		g2.clearRect(0, 0, size.width, size.height);
		g2.draw3DRect(20, 20, size.width - 40, size.height - 40, true);

		tri.setColor(Color.blue);

		int[] xPoints = {100, 50, 150};
		int[] yPoints = {100, 200, 200};
		tri.drawPolygon(xPoints , yPoints, 3);
		tri.fillPolygon(xPoints , yPoints, 3);
	}
	
'''

Mit der drawPolygon()-Methode wird das Dreieck mit Hilfe der xPoints und yPoints gezeichnet. Außerdem wird noch die Hintergrundfarbe des Dreiecks verändert.
## Once you can draw the triangle, now draw a triangle that connects the midpoints of each of the lines. You now have 4 triangles. For each of the three outer triangles, recursively draw a triangle that connects the midpoints. What is your termination condition, what is the measure?

## Expand your triangle drawing algorithm to draw in a specific color. Choose a different color for every level of the algorithm.

## Fill the middle triangle on each step with an appropriate color. Choose the size of the first triangle depending on what size the window is. Redraw the triangle when the window is resized.