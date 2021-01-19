package vu;

import controller.AutomateSimulator;
import controller.Controller;
import fabrique.TypeAutomate;
import model.Grille;
import observateur.Observateur;

import static java.lang.Math.random;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.NumberFormat;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.text.NumberFormatter;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JSpinner;
import javax.swing.JFormattedTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.UIManager;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 * @author Cristian Serban
 * l'interface graphique
 */
public class Vu extends JFrame implements Observateur {
	private JFrame frame;
	private JPanel mainPanel;
	private JPanel gameArea;
	private JPanel controlArea;
	private JPanel AutomateCPanel;
	private JPanel ExtensionCPanel;
	private JButton startButton;
	private JButton setGridButton;
	private JButton pauseButton;
	private JButton nextIterationButton;
	private JRadioButton conwayRButton;
	private JRadioButton brianRButton;
	private JRadioButton fredkinRButton;
	private JRadioButton periodiciteRButton;
	private JRadioButton repetitionRButton;
	private JRadioButton pasRButton;
	private JRadioButton automatiqueRButton;
	private JComboBox vitesseBox;
	private ButtonGroup automateGroup;
	private ButtonGroup extensionGroup;
	private ButtonGroup modeGroup;
	private JButton[][] buttonGrille;

	private JLabel vitesseLabel;

	private Controller controller;

	Image imgWhite;
	Image imgBlack;
	Image imgBlue;

	private GridLayout gridLayout;
	private Thread automatique;
	private int threadSpeed = 1000;
	private boolean run = false;
	private boolean permission = true;
	private Grille observe;
	private JTextField widthField;
	private JTextField heightField;

	/**
	 * constructeur qui initialise les éléments graphiques et démarre le thread d'exécution
	 */
	public Vu() {
		controller = new Controller();
		initializeElements();
		
		automatique = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!automatique.isInterrupted()) {
					if (run) {
						try {
							Thread.sleep(threadSpeed);
							controller.nextIteration();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					}
				}
			}
		});
		automatique.start();
		
	}

	/**
	 * récupérer les valeurs de dimension dans les champs de texte et crée une nouvelle grille
	 */
	private void createGrid() {
		if (checkValue(widthField.getText()) && checkValue(heightField.getText())) {
			int width = Integer.parseInt(widthField.getText());
			int height = Integer.parseInt(heightField.getText());
			initializeGrille(width, height);
		} else {
			JOptionPane.showMessageDialog(controlArea, "Taille invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	/**
	 * @param width la largeur de la grille
	 * @param height la taille de la grille
	 * crée la grille du automat et affiche la grille de boutons 
	 */
	private void initializeGrille(int width, int height) {
		if (buttonGrille != null) {
			Component[] componentList = gameArea.getComponents();
			for (Component c : componentList) {
				if (c instanceof JButton)
					gameArea.remove(c);
			}
		}

		gridLayout = new GridLayout(height, width);
		gameArea.setLayout(gridLayout);
		controller.creerGrille(width, height);
		observe = controller.getGrille();
		observe.ajouteObservateurs(this);

		buttonGrille = new JButton[height][width];
		for (int i = 0; i < height; i++)
			for (int j = 0; j < width; j++) {
				JButton button = new JButton();
				button.setName(i + " " + j);
				button.setIcon(new ImageIcon(imgWhite));
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (permission) {
							String str = button.getName();
							String[] split = str.split(" ");
							int a = Integer.parseInt(split[0]);
							int b = Integer.parseInt(split[1]);
							drawGrille(controller.nextEtat(a, b));
						}
					}
				});
				gameArea.add(button);
				buttonGrille[i][j] = button;
			}
		gameArea.setVisible(false);
		gameArea.setVisible(true);
	}

	
	/**
	 * @param grille la grille de l'automate
	 * itère à travers la grille et recolore les cellules en fonction de leur état
	 */
	private void drawGrille(Grille grille) {
		for (int i = 0; i < grille.getHeight(); i++)
			for (int j = 0; j < grille.getWidth(); j++) {
				int etat = grille.getCellules()[i][j].getEtat();
				switch (etat) {
				case 0:
					buttonGrille[i][j].setIcon(new ImageIcon(imgWhite));
					break;
				case 1:
					buttonGrille[i][j].setIcon(new ImageIcon(imgBlack));
					break;
				case 2:
					buttonGrille[i][j].setIcon(new ImageIcon(imgBlue));
					break;
				}
			}
	}

	
	/**
	 * arrête l'application et revient à l'état initial
	 */
	private void resetAll() {
		widthField.setEnabled(true);
		heightField.setEnabled(true);
		conwayRButton.setEnabled(true);
		fredkinRButton.setEnabled(true);
		brianRButton.setEnabled(true);
		periodiciteRButton.setEnabled(true);
		repetitionRButton.setEnabled(true);
		automatiqueRButton.setEnabled(true);
		pasRButton.setEnabled(true);
		setGridButton.setEnabled(true);
		startButton.setEnabled(true);
		pauseButton.setVisible(false);
		pauseButton.setText("Pause");
		nextIterationButton.setVisible(false);
		
		controller.clearGrille();
		
		run = false;
		permission = true;
		
	}
	
	
	/**
	 * redessine la grille lorsque l'observateur notifie un changement
	 */
	@Override
	public void misAjour() {
		drawGrille(controller.getGrille());
	}

	/**
	 * @param number valeur d'un champ de texte
	 * @return true si le string est un entier positif ou faux sinon
	 */
	private boolean checkValue(String number) {
		if (number == null) {
			return false;
		}
		try {
			int i = Integer.parseInt(number);
			if (i < 0)
				return false;
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	/**
	 * démarre l'application et désactive le menu d'options
	 */
	private void startAutomat() {
		widthField.setEnabled(false);
		heightField.setEnabled(false);
		conwayRButton.setEnabled(false);
		fredkinRButton.setEnabled(false);
		brianRButton.setEnabled(false);
		periodiciteRButton.setEnabled(false);
		repetitionRButton.setEnabled(false);
		automatiqueRButton.setEnabled(false);
		pasRButton.setEnabled(false);
		setGridButton.setEnabled(false);
		startButton.setEnabled(false);

		if (repetitionRButton.isSelected())
			controller.creerExtension("Repetition");
		if (periodiciteRButton.isSelected())
			controller.creerExtension("Periodicite");
		if (conwayRButton.isSelected())
			controller.creerAutomate("Conway");
		if (fredkinRButton.isSelected())
			controller.creerAutomate("Fredkin");
		if (brianRButton.isSelected())
			controller.creerAutomate("Brian");
		if (pasRButton.isSelected()) {
			nextIterationButton.setVisible(true);
		}
		if (automatiqueRButton.isSelected()) {
			pauseButton.setVisible(true);
			run = true;
		}
		
		permission = false;
	}
	
	/**
	 * initialise les éléments de l'interface graphique
	 */
	private void initializeElements() {
		setTitle("Automates Cellulaire");
		setSize(1200, 725);
		setLocation(350, 200);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		mainPanel = new JPanel();
		mainPanel.setVisible(true);
		getContentPane().setLayout(null);
		mainPanel.setBounds(0, 0, 1184, 661);
		mainPanel.setLayout(null);
		getContentPane().add(mainPanel);

		try {
			imgWhite = ImageIO.read(getClass().getResource("/white.png"));
			imgBlack = ImageIO.read(getClass().getResource("/black.jpg"));
			imgBlue = ImageIO.read(getClass().getResource("/blue.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		gameArea = new JPanel();
		gameArea.setBounds(10, 11, 863, 639);
		gameArea.setBorder(BorderFactory.createLineBorder(Color.black));
		gameArea.setVisible(true);
		mainPanel.add(gameArea);

		controlArea = new JPanel();
		controlArea.setBounds(883, 11, 291, 639);
		controlArea.setBorder(BorderFactory.createLineBorder(Color.black));
		controlArea.setVisible(true);
		mainPanel.add(controlArea);

		startButton = new JButton("Start"); // add click permission
		startButton.setBounds(10, 605, 101, 23);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startAutomat();
			}
		});
		controlArea.setLayout(null);
		controlArea.add(startButton);

		AutomateCPanel = new JPanel();
		AutomateCPanel
				.setBorder(new TitledBorder(null, "Automate", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		AutomateCPanel.setBounds(10, 109, 271, 141);
		controlArea.add(AutomateCPanel);
		AutomateCPanel.setLayout(null);

		conwayRButton = new JRadioButton("Le jeu de la vie");
		conwayRButton.setSelected(true);
		conwayRButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		conwayRButton.setBounds(21, 26, 123, 23);
		AutomateCPanel.add(conwayRButton);

		brianRButton = new JRadioButton("Brian's Brain");
		brianRButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		brianRButton.setBounds(21, 62, 109, 23);
		AutomateCPanel.add(brianRButton);

		fredkinRButton = new JRadioButton("Fredkin");
		fredkinRButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		fredkinRButton.setBounds(21, 98, 109, 23);
		AutomateCPanel.add(fredkinRButton);

		automateGroup = new ButtonGroup();
		automateGroup.add(conwayRButton);
		automateGroup.add(fredkinRButton);
		automateGroup.add(brianRButton);

		ExtensionCPanel = new JPanel();
		ExtensionCPanel
				.setBorder(new TitledBorder(null, "Extension", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		ExtensionCPanel.setBounds(10, 261, 271, 107);
		controlArea.add(ExtensionCPanel);
		ExtensionCPanel.setLayout(null);

		periodiciteRButton = new JRadioButton("Periodicite");
		periodiciteRButton.setSelected(true);
		periodiciteRButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		periodiciteRButton.setBounds(6, 27, 109, 23);
		ExtensionCPanel.add(periodiciteRButton);

		repetitionRButton = new JRadioButton("Repetition");
		repetitionRButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		repetitionRButton.setBounds(6, 63, 109, 23);
		ExtensionCPanel.add(repetitionRButton);

		extensionGroup = new ButtonGroup();
		extensionGroup.add(repetitionRButton);
		extensionGroup.add(periodiciteRButton);

		nextIterationButton = new JButton("Suivant");
		nextIterationButton.setVisible(false);
		nextIterationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.nextIteration();
			}
		});
		nextIterationButton.setBounds(181, 605, 100, 23);
		controlArea.add(nextIterationButton);

		pauseButton = new JButton("Pause");
		pauseButton.setVisible(false);
		pauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				run = !run;
				if (run)
					pauseButton.setText("Pause");
				else
					pauseButton.setText("Reprendre");
			}
		});
		pauseButton.setBounds(181, 605, 100, 23);
		controlArea.add(pauseButton);

		JPanel gridCPanel = new JPanel();
		gridCPanel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Grille", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		gridCPanel.setBounds(10, 11, 271, 87);
		controlArea.add(gridCPanel);
		gridCPanel.setLayout(null);

		JLabel widthLabel = new JLabel("Largeur:");
		widthLabel.setBounds(10, 26, 59, 14);
		gridCPanel.add(widthLabel);

		JLabel heightLabel = new JLabel("Taille:");
		heightLabel.setBounds(150, 26, 44, 14);
		gridCPanel.add(heightLabel);

		NumberFormat amountFormat = NumberFormat.getInstance();
		NumberFormatter amountFormatter = new NumberFormatter(amountFormat);

		amountFormatter.setValueClass(Integer.class);
		amountFormatter.setMinimum(0);
		amountFormatter.setMaximum(1001);
		amountFormatter.setAllowsInvalid(false);
		amountFormatter.setCommitsOnValidEdit(true);

		setGridButton = new JButton("Confirmer");
		setGridButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createGrid();
			}
		});
		setGridButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		setGridButton.setBounds(150, 53, 111, 23);
		gridCPanel.add(setGridButton);

		widthField = new JTextField();
		widthField.setBounds(69, 23, 71, 20);
		gridCPanel.add(widthField);
		widthField.setColumns(10);

		heightField = new JTextField();
		heightField.setColumns(10);
		heightField.setBounds(192, 23, 69, 20);
		gridCPanel.add(heightField);

		JPanel modePanel = new JPanel();
		modePanel.setBorder(new TitledBorder(null, "Mode", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		modePanel.setBounds(10, 379, 271, 107);
		controlArea.add(modePanel);
		modePanel.setLayout(null);

		automatiqueRButton = new JRadioButton("Automatique");
		automatiqueRButton.setSelected(true);
		automatiqueRButton.setBounds(6, 22, 109, 23);
		automatiqueRButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vitesseLabel.setVisible(true);
				vitesseBox.setVisible(true);
			}
		});
		modePanel.add(automatiqueRButton);

		pasRButton = new JRadioButton("Pas-a-pas");
		pasRButton.setBounds(6, 62, 109, 23);
		pasRButton.setVisible(true);
		pasRButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vitesseLabel.setVisible(false);
				vitesseBox.setVisible(false);
			}
		});
		modePanel.add(pasRButton);

		modeGroup = new ButtonGroup();
		modeGroup.add(pasRButton);
		modeGroup.add(automatiqueRButton);

		vitesseLabel = new JLabel("Vitesse");
		vitesseLabel.setBounds(121, 26, 46, 14);
		modePanel.add(vitesseLabel);

		vitesseBox = new JComboBox();
		vitesseBox.setModel(new DefaultComboBoxModel(new String[] { "lente", "moyenne", "vite" }));
		vitesseBox.setBounds(177, 23, 61, 20);
		vitesseBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switch (vitesseBox.getSelectedIndex()) {
				case 0:
					threadSpeed = 1000;
					break;
				case 1:
					threadSpeed = 500;
					break;
				case 2:
					threadSpeed = 200;
					break;
				}
			}
		});
		modePanel.add(vitesseBox);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu menu = new JMenu("Grille");
		menuBar.add(menu);
		JMenuItem resetMenu = new JMenuItem("Reset");
		resetMenu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetAll();
			}
			
		});
		menu.add(resetMenu);

		setVisible(true);
	}
}
