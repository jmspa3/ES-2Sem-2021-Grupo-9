package Limits;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;

public class LimitsGUI {

	private JFrame frame;
	private JTextField textField_1;
	private Threshold th;
	private String regra = "";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LimitsGUI window = new LimitsGUI();
					window.initialize();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LimitsGUI() {
		
	}
	
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 780, 542);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Regra    :");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblNewLabel.setBounds(80, 42, 145, 47);
		frame.getContentPane().add(lblNewLabel);
		
		JButton andButton = new JButton("And");
		andButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		andButton.setBounds(80, 350, 104, 35);
		frame.getContentPane().add(andButton);
		
		JButton orButton = new JButton("Or");
		orButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		orButton.setBounds(205, 350, 104, 35);
		frame.getContentPane().add(orButton);
		
		JButton higherButton = new JButton(">");
		higherButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		higherButton.setBounds(334, 350, 104, 35);
		frame.getContentPane().add(higherButton);
		
		JButton lowerButton = new JButton("<");
		lowerButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lowerButton.setBounds(448, 350, 104, 35);
		frame.getContentPane().add(lowerButton);
		
		JButton equalButton = new JButton("=");
		equalButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		equalButton.setBounds(574, 350, 98, 35);
		frame.getContentPane().add(equalButton);
		
		JList list = new JList();
		list.setBounds(193, 139, 320, 35);
		frame.getContentPane().add(list);
		
		JLabel lblNewLabel_1 = new JLabel("Condição");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(85, 139, 98, 35);
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton guardarCondicao = new JButton("save");
		guardarCondicao.setFont(new Font("Tahoma", Font.PLAIN, 15));
		guardarCondicao.setBounds(538, 136, 123, 38);
		frame.getContentPane().add(guardarCondicao);
		
		JButton adicionarNumero = new JButton("Adicionar número");
		adicionarNumero.setFont(new Font("Tahoma", Font.PLAIN, 17));
		adicionarNumero.setBounds(193, 266, 165, 35);
		frame.getContentPane().add(adicionarNumero);
		
		textField_1 = new JTextField();
		textField_1.setBounds(80, 265, 104, 36);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JButton guardarTotal = new JButton("Save");
		guardarTotal.setFont(new Font("Tahoma", Font.PLAIN, 24));
		guardarTotal.setBounds(275, 433, 222, 47);
		frame.getContentPane().add(guardarTotal);
		
		JLabel regra = new JLabel("New label");
		regra.setFont(new Font("Tahoma", Font.PLAIN, 19));
		regra.setHorizontalAlignment(SwingConstants.CENTER);
		regra.setBounds(216, 42, 367, 47);
		frame.getContentPane().add(regra);
	}
	
	
	
	
	
}

