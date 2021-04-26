package view;

import java.awt.Graphics;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.File;
import java.io.IOException;

import model.*;
import controller.Controller;

public class ViewGestion extends JPanel {

	private static JLabel scoreView;
	private static ViewMenu viewMenu;
	private static PieceModel[] pieces = new PieceModel[16];
	private static PlateauView plateau;
	private static Controller controller;
	private static JPanel toolbar = new JPanel();
	private static JPanel scoreBar = new JPanel();
	private static JFrame winViewRed;
	private static JFrame winViewBlue;
	private static JFrame instruct;
	private static final JButton play = new JButton("Jouer");
	private static final JButton instruc = new JButton("Règles");
	private static final JButton quit = new JButton("Quitter");

	public ViewGestion() {
		setLayout(new BorderLayout());
		viewMenu = new ViewMenu();
		add(viewMenu, BorderLayout.CENTER);
		add(createToolbar(), BorderLayout.NORTH);
		createInstructions();
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				setLayout(new BorderLayout());
				plateau = new PlateauView(new PlateauModel());
				add(plateau, BorderLayout.CENTER);
				play.setVisible(false);
				instruc.setVisible(false);
				winViewBlue();
				winViewRed();
				add(createToolbarScore(), BorderLayout.NORTH);
				controller = new Controller(plateau, winViewRed, winViewBlue, scoreView);
			}
		});
		instruc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				instruct.setVisible(true);
			}
		});
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				System.exit(0);
			}
		});
	}

	private JFrame createInstructions() {
		instruct = new JFrame("Instructions");
		instruct.setLayout(new BorderLayout());
		instruct.setLocation(400, 100);
		instruct.setResizable(false);
		ImageIcon img = new ImageIcon("view/images/Instruc.png");
		JLabel instructionLabel = new JLabel(img);
		instruct.add(instructionLabel, BorderLayout.CENTER);
		JPanel options = new JPanel();
		JButton closeInstructions = new JButton("Fermer");
		closeInstructions.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				instruct.setVisible(false);
			}
		});
		options.add(closeInstructions);
		instruct.add(options, BorderLayout.SOUTH);
		instruct.pack();
		instruct.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		instruct.setVisible(false);
		return instruct;
	}

	private JPanel createToolbar() {
		toolbar.setLayout(new FlowLayout());
		toolbar.add(play);
		toolbar.add(instruc);
		toolbar.add(quit);
		return toolbar;
	}

	private JPanel createToolbarScore() {
		scoreBar.setLayout(new FlowLayout());
		scoreView = new JLabel("Rouge = 0 Bleu = 0");
		scoreBar.add(scoreView);
		scoreBar.add(quit);
		return scoreBar;
	}

	private void winViewRed() {
		winViewRed = new JFrame ("Bravo");
		winViewRed.setLocation(400, 100);
		winViewRed.setResizable(false);
		ImageIcon imgRed = new ImageIcon("view/images/victoire_rouge.png");
		JLabel winViewLabel = new JLabel(imgRed);
		winViewRed.add(winViewLabel, BorderLayout.CENTER);
		JPanel optionsViewRed = new JPanel();
		JButton closeWinView = new JButton("Fermer");
		closeWinView.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				winViewRed.setVisible(false);
			}
		});
		optionsViewRed.add(closeWinView);
		winViewRed.add(optionsViewRed, BorderLayout.SOUTH);
		winViewRed.pack();
		winViewRed.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		winViewRed.setVisible(false);
	}

	private void winViewBlue() {
		winViewBlue = new JFrame ("Bravo");
		winViewBlue.setLocation(400, 100);
		winViewBlue.setResizable(false);
		ImageIcon imgBlue = new ImageIcon("view/images/victoire_bleu.png");
		JLabel winViewLabel2 = new JLabel(imgBlue);
		winViewBlue.add(winViewLabel2, BorderLayout.CENTER);
		JPanel optionsViewBlue = new JPanel();
		JButton closeWinView2 = new JButton("Fermer");
		closeWinView2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				winViewBlue.setVisible(false);
			}
		});
		optionsViewBlue.add(closeWinView2);
		winViewBlue.add(optionsViewBlue, BorderLayout.SOUTH);
		winViewBlue.pack();
		winViewBlue.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		winViewBlue.setVisible(false);
	}
}