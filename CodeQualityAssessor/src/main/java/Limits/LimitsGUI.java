package Limits;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Scanner;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;

public class LimitsGUI implements MouseListener {
	
	//implements ActionListener

	private JFrame frame;
	private JList list;
	private JTextField textField_1;
	private JTextField textField_condicao;
	private Threshold th;
	private String regra = "", and = "And", or = "Or", lesser = "<", greater = ">", equal = "=";

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
		
		JButton andButton = new JButton(and);
		andButton.addMouseListener(this);
		andButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		andButton.setBounds(80, 350, 104, 35);
		frame.getContentPane().add(andButton);
		
		JButton orButton = new JButton(or);
		orButton.addMouseListener(this);
		orButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		orButton.setBounds(205, 350, 104, 35);
		frame.getContentPane().add(orButton);
		
		JButton higherButton = new JButton(greater);
		higherButton.addMouseListener(this);
		higherButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		higherButton.setBounds(334, 350, 104, 35);
		frame.getContentPane().add(higherButton);
		
		JButton lowerButton = new JButton(lesser);
		lowerButton.addMouseListener(this);
		lowerButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lowerButton.setBounds(448, 350, 104, 35);
		frame.getContentPane().add(lowerButton);
		
		JButton equalButton = new JButton(equal);
		equalButton.addMouseListener(this);
		equalButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		equalButton.setBounds(574, 350, 98, 35);
		frame.getContentPane().add(equalButton);
		
		/*JList list = new JList<String>();
		list.setBounds(193, 139, 320, 35);
		frame.getContentPane().add(list);*/
		
		textField_condicao = new JTextField();
		textField_condicao.setBounds(193, 139, 320, 35);
		frame.getContentPane().add(textField_condicao);
		textField_condicao.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Condição");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(85, 139, 98, 35);
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton guardarCondicao = new JButton("save");
		guardarCondicao.addMouseListener(this);
		guardarCondicao.setFont(new Font("Tahoma", Font.PLAIN, 15));
		guardarCondicao.setBounds(538, 136, 123, 38);
		frame.getContentPane().add(guardarCondicao);
		
		JButton adicionarNumero = new JButton("Adicionar número");
		adicionarNumero.addMouseListener(this);
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
	
	
	public void logicExpressionAppend(MouseEvent e, String condicionador){  
		String operador;
		switch (condicionador) {
			case "Or":  operador = "|| "; break;      
			case "And":  operador = "&& "; break;	
			case "=":  operador = "== "; break;	
	        default: operador = condicionador + " "; break;      
		}
		
		textField_condicao.setText(textField_condicao.getText() + operador);  
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		String command;
		String buttonText = ((JButton) e.getSource()).getText();
		
		switch (buttonText) {
		
			case "Adicionar número": 
				command = (textField_1.getText());
				logicExpressionAppend(e, command);
				break;
				
			case "save":
				command = textField_condicao.getText();
				verifyCondition(command);
				break;
	
			default:
				command = ((JButton) e.getSource()).getText();
				logicExpressionAppend(e, command);
				break; 
				
		}

	}
	
	
	public void verifyCondition(String condition) {
		/*The purpose of this function is to verify whether the sintaxe of the text in "textField_condicao" 
		 * is valid or not (in terms of how Java processes conditions). These are the rules for the correct
		 * functioning of the function, which work in a cycle until the scanner can't read any more data:
		 * 1st member - Number OR String similar to the way we identify LOC_class and so on
		 * 2nd member - Mandatory operator (<, >, ==)
		 * 3rd member - Equal to first, but in combination of Number - String or String - Number (12 - 2 is valid mathematically but not in this context)
		 * 4th member - Mandatory OR or AND operator, otherwise if there are no more tokens to be read, NULL*/
		
		//v0.1
		
		Scanner scanner =new Scanner(condition);
		while (scanner.hasNext()) {  
			try {
			String first = scanner.next();
			String operator = scanner.next();  
			String third = scanner.next();  
			if (scanner.hasNext()) {
				String logic = scanner.next();
				System.out.println(first + operator + third + logic);
			}
			System.out.println(first + operator + third );
			
			
			} catch (Exception sequence_broken) {
				System.out.println("A condição tem erros na sintaxe pelo que não posso guardá-la");
			}
		}  
		scanner.close();  
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}

