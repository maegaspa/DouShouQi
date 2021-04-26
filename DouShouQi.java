import java.awt.Graphics;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import view.ViewGestion;
import view.ViewMenu;
import view.SetupFrame;

public class DouShouQi implements Runnable {

	@Override
	public void run() {
		final JFrame frame = new JFrame ("DouShouQi");
		frame.setResizable(false);
		frame.setPreferredSize(new Dimension(800, 800));
		final ViewGestion viewGestion = new ViewGestion();
		frame.add(viewGestion);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new DouShouQi());
	}
}
