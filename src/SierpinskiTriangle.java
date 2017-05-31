import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SierpinskiTriangle {
    private int height;
    private Point left;
    private Point middle;
    private Point right;
    private JPanel panel;

    public static void main(String[] args) {
        SierpinskiTriangle triangle = new SierpinskiTriangle();
        triangle.display();
    }

    /*
        Zeichnet das JFrame und Panel der größe SIZE 1000 und ruft zum ersten mal paintSierpinskiTriangle() auf.
        Hier kann außerdem im Paramter dieser Methode die Anzahl der rekursiven Aufrufe erstellt werden. Bei
        recursionLevel = 1 wird z.B. nur 1 Dreieck gezeichnet und bei 2 drei Stück.
     */
    @SuppressWarnings("serial")
    public void display() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                //hier werden die Punkte für ein gleichseitiges Dreieck berechnet
                //es wird getSize() benutzt, damit das entstandene Dreieck auch responsive ist.
                height = (int) Math.round(getSize().width * Math.sqrt(3) / 2);
                left = new Point(0, height);
                middle = new Point(getSize().width / 2, 0);
                right = new Point(getSize().width, height);
                //sozusagen Beginn der Rekursion, falls recursionLevel > 1 ist.
                paintSierpinskiTriangle(g, getSize(), 8, left, middle, right);
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
        int SIZE = 1000;
        frame.setSize(SIZE, SIZE);
        frame.setVisible(true);
    }

    /*
        Berechnet den Mittelpunkt der Dreiecksseite, welche durch den ersten und zweiten Punkt definiert wird.
     */
    private Point calculateMidPoint(Point first, Point second) {
        int x = (first.x + second.x) / 2;
        int y = (first.y + second.y) / 2;
        Point newMidPoint = new Point(x, y);
        return newMidPoint;
    }

    /*
        Zeichnet alle Dreiecke und wird auch rekursiv aufgerufen - Erster aufruf erfolgt in der display()-Methode
     */
    public void paintSierpinskiTriangle(Graphics g, Dimension size, int recursionLevel, Point left, Point middle, Point right) {
        Graphics2D tri = (Graphics2D) g;
        if (recursionLevel == 1) {
            //Erstellt ein Dreieck anhand der im Parameter übergebenen Punkte und deren Koordinaten
            int[] xPoints = {left.x, middle.x, right.x};
            int[] yPoints = {left.y, middle.y, right.y};
            tri.drawPolygon(xPoints, yPoints, 3);
            tri.fillPolygon(xPoints, yPoints, 3);
        } else {
            //Wenn das Level nicht 1 ist, werden zunächst die 3 Mittelpunkte jeder Seite berechnet
            Point newTriPoint1 = calculateMidPoint(left, middle);
            Point newTriPoint2 = calculateMidPoint(middle, right);
            Point newTriPoint3 = calculateMidPoint(left, right);

            /*
            Rekursion-Funktionalität, indem das Level der Rekursion pro Aufruf um 1 verringert wird
            Hierbei wird die Rekursion für jede Seite eines jeweiligen Dreiecks angewendet, daher auch 3 Aufrufe
            1 pro Dreiecksseite.
             */
            paintSierpinskiTriangle(g, size, recursionLevel - 1, left, newTriPoint1, newTriPoint3);
            paintSierpinskiTriangle(g, size, recursionLevel - 1, newTriPoint1, middle, newTriPoint2);
            paintSierpinskiTriangle(g, size, recursionLevel - 1, newTriPoint3, newTriPoint2, right);
        }
    }
}
