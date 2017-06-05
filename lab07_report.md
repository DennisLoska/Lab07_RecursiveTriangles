# Lab Report
## Exercise 07: Recursive Triangles

_Autoren: Dennis Loska, 30.05.2017_

## First set up a Window that can handle drawing. Can you get the Window to draw an equilateral triangle? What is the largest one you can get on the screen? You can use this scaffold: https://github.com/htw-imi-info2/SierpinskyTriangleStart

Zunächst haben wir das Projekt mit Hilfe des angegebenen Links erstellt und getestet, ob es funktioniert. Beim Starten der Klasse war ein leeres Canvas bzw. JFrame samt JPanel zu sehen - soweit so gut. Nun wird in der paintSierpinskiTriangle()-Methode das Dreieck gezeichnet:



```java
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
```



Mit der drawPolygon()-Methode wird das Dreieck mit Hilfe der xPoints und yPoints gezeichnet. Außerdem wird noch die Hintergrundfarbe des Dreiecks verändert.

## Once you can draw the triangle, now draw a triangle that connects the midpoints of each of the lines. You now have 4 triangles. For each of the three outer triangles, recursively draw a triangle that connects the midpoints. What is your termination condition, what is the measure?



```java
public void paintSierpinskiTriangle(Graphics g, Dimension size) {
	
}
```

Hierzu haben wir den Code von der Paint SierpinskiTriangle ändern müssen um sie erstens flexibel und auf alle Dreiecke anwendbar zu mache und als zweistes - um aus ihr eine Rekursive Methode machen.

Als Parameter für die paintST... Methode erstezten wir das Dimension Objet mit Drei Point Objekten - dadurch hatten wir die Möglichkeit, von diesen Drei Punkten aus mit einer neuen getMidpointMethode die Punkte für die weiteren Dreiecke zu berechnen.  Mit diesen Änderungen konnte uns unsere Methode bereits alle möglichen Dreiecke zeichnen, jetzt fehlte nur noch "Recursion".

Dies haben wir  umgesetzt, indem wir einen integer "recursionLevel" als weiteren Parameter zur Methode hinzufügten. Falls dieser 1 betrug wurde das Dreieck basierend auf den Punkten gezeichnet falls nicht, wurden die Midpoints dieses Dreiecks(dessen Punkte als Parameter davor in die Methode eingegeben wurden) berechnet und dann in 3 mal die paintSierpinskiTriangle Methode rekursiv aufgerufen. als Parameter für die Rekursiv aufgerufene Methode wurden die neu mit den midpoints ermittelten Koordinaten von Dreiecken, welche das erste in 4 neue Dreiecke aufteilen, eingegeben. Dabei wurde der Recursion Level parameter immer um 1 verringert, dies führte dazu das die drei aufgerufenen Fill Methoden wieder 9 weitere Aufrufen und so wieter bis schließlich paintST Methoden mit dem Recursion Level 1 als Parameter aufgerufen wurden, welche nun auch wirklic die ganzen berechneten kleinen Dreiecke zeichneten. 

![triangleBefore](C:\Users\Windows 10\Desktop\collab 7\Lab07_RecursiveTriangles\triangleBefore.PNG)

```java

            Point newTriPoint1 = calculateMidPoint(left, middle);
            Point newTriPoint2 = calculateMidPoint(middle, right);
            Point newTriPoint3 = calculateMidPoint(left, right);
            
            paintSierpinskiTriangle(g, recursionLevel - 1, left, newTriPoint1, newTriPoint3);
            paintSierpinskiTriangle(g, recursionLevel - 1, newTriPoint1, middle, newTriPoint2);
            paintSierpinskiTriangle(g, recursionLevel - 1, newTriPoint3, newTriPoint2, right);
        }
    }
```

## Expand your triangle drawing algorithm to draw in a specific color. Choose a different color for every level of the algorithm.

In der letzten Aufgabe haben wir geschafft die Dreiecke aufzumalen, jedoch nur die kleinen Dreiecke, die man als Hintergrund betrachtenn kann, alle hatten die gleiche Farbe. Die Mittleren- größeren Dreiecke blieben farblos, um diese zu färben - fügten wir in jeder paintSierpinskiTirangle Methode noch eine rekursiv aufgerufene paintSierpinskiMethode hinzu, die das mittlere Dreieck zeichnet.  Dies reichte aber nicht aus, denn dieses Mittlere Dreieck musste anders als die drei "Außendreiecke" aussehen. Dazu haben wir einen weiteren Parameter in unsere paintST methode hinzugefügt - den interger color. Basierend auf diesem wurde dann mit einem switch Case die Farbe für das Dreieck mit der Methode setColor(Color.getHSBColor()) gesetzt. die Farbe wurde dann ähnlich wie der Recursion Level um 1 verringert an die weiteren Drei Dreiecke übergeben, sodass sie sich genauso wie der recursionLevel bei jedem durchlauf ändert.



```java
            Point newTriPoint1 = calculateMidPoint(left, middle);
            Point newTriPoint2 = calculateMidPoint(middle, right);
            Point newTriPoint3 = calculateMidPoint(left, right);

            paintSierpinskiTriangle(g, recursionLevel - 1, left, middle,rigth, , color);
            
            paintSierpinskiTriangle(g, recursionLevel - 1, left, newTriPoint1, newTriPoint3, color-1);
            paintSierpinskiTriangle(g, recursionLevel - 1, newTriPoint1, middle, newTriPoint2, color-1);
            paintSierpinskiTriangle(g, recursionLevel - 1, newTriPoint3, newTriPoint2, right, color-1);
        }
    }
```



![triangleFinito](C:\Users\Windows 10\Desktop\collab 7\Lab07_RecursiveTriangles\triangleFinito.PNG)

## Fill the middle triangle on each step with an appropriate color. Choose the size of the first triangle depending on what size the window is. Redraw the triangle when the window is resized.

Damit jedes innere Dreieck haben wir einen Switch Case implementiert, welcher auf den übergebenen Parameter `color` hört. Dieser Parameter wird mit jeder Rukursionsstufe um 1 verringert wodurch jedes Dreieck eine neue Farbe erhält.

Die Anpassungsfähigkeit des erste Dreiecks wurde durch die Verwendung von relativen Werten des Frames realisiert. 

```java
if (getSize().height >= getSize().width) {
                    int height = (int) Math.round(getSize().width * Math.sqrt(3) / 2);
                    left = new Point(0, height);
                    middle = new Point(getSize().width / 2, 0);
                    right = new Point(getSize().width, height);
                } else {
                    int height = getSize().height;
                    int width = (int) (height / (Math.sqrt(3) / 2));
                    left = new Point(0, height);
                    middle = new Point(width / 2, 0);
                    right = new Point(width, height);
                }
```

Durch die Verwendung von `getSize().height` und `getSize().width` wird bei jeder neuen Framegröße die Koordinaten der Dreiecke neu berechnet und somit eine Anpassungsfähigkeit realisiert.