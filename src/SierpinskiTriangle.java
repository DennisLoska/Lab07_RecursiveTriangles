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
		Graphics2D tri = (Graphics2D) g;
		tri.setColor(Color.blue);
		int[] xPoints = {500, 0, 1000};
		int[] yPoints = {0, 1000, 1000};
		tri.drawPolygon(xPoints , yPoints, 3);
		tri.fillPolygon(xPoints , yPoints, 3);
	}

}
