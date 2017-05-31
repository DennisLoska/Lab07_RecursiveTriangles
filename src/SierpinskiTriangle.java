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

    @SuppressWarnings("serial")
    public void display() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                height = (int) Math.round(getSize().width * Math.sqrt(3) / 2);
                left = new Point(0, height);
                middle = new Point(getSize().width / 2, 0);
                right = new Point(getSize().width, height);
                paintSierpinskiTriangle(g, getSize(), 5, left, middle, right);
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

    private Point calculateMidPoint(Point first, Point second) {
        int x = (first.x + second.x) / 2;
        int y = (first.y + second.y) / 2;
        Point newMidPoint = new Point(x, y);
        return newMidPoint;
    }

    public void paintSierpinskiTriangle(Graphics g, Dimension size, int recursionLevel, Point left, Point middle, Point right) {
        if (recursionLevel == 1) {
            Graphics2D tri = (Graphics2D) g;
            tri.setColor(Color.blue);
            int[] xPoints = {left.x, middle.x, right.x};
            int[] yPoints = {left.y, middle.y, right.y};
            tri.drawPolygon(xPoints, yPoints, 3);
            tri.fillPolygon(xPoints, yPoints, 3);
        } else {
            Point newTriPoint1 = calculateMidPoint(left, middle);
            Point newTriPoint2 = calculateMidPoint(middle, right);
            Point newTriPoint3 = calculateMidPoint(left, right);

            paintSierpinskiTriangle(g, size, recursionLevel - 1, left, newTriPoint1, newTriPoint3);
            paintSierpinskiTriangle(g, size, recursionLevel - 1, newTriPoint1, middle, newTriPoint2);
            paintSierpinskiTriangle(g, size, recursionLevel - 1, newTriPoint3, newTriPoint2, right);
        }
    }
}
