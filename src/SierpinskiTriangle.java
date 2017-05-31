import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SierpinskiTriangle {
    public static int SIZE = 1000;

    int maxWidth;
    int minWidth = 10;
    int height = (int) ((maxWidth / 2) * Math.sqrt(3));

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
                paintSierpinskiTriangle(g, getSize());
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
        frame.setSize(SIZE, SIZE);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SierpinskiTriangle triangle = new SierpinskiTriangle();
        triangle.display();
    }

    public void paintSierpinskiTriangle(Graphics g, Dimension size) {
        maxWidth = size.width;
        Graphics2D tri = (Graphics2D) g;
        tri.setColor(Color.blue);
        int[] xPoints = {maxWidth / 2, 0, maxWidth};
        int[] yPoints = {size.height - height, size.height, size.height};
        tri.drawPolygon(xPoints, yPoints, 3);
        tri.fillPolygon(xPoints, yPoints, 3);
    }

}
