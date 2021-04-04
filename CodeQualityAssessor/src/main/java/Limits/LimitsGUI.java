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
	//Nomes dos botões
	private String add_num = "Adicionar Número", editar = "Editar", limpar = "Limpar";
	private String salvar = "Guardar e Sair"; //guardar e sair
	private String guardar = "Guardar"; //guardar a configuração da regra


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
		
		//-----------------OPERATORS----------------------
		
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
		
		//-----------------END----------------------
		
		/*JList list = new JList<String>();
		list.setBounds(193, 139, 320, 35);
		frame.getContentPane().add(list);*/
		
		textField_condicao = new JTextField();
		textField_condicao.setEditable(false);
		textField_condicao.setBounds(193, 139, 320, 35);
		frame.getContentPane().add(textField_condicao);
		textField_condicao.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Condição");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(85, 139, 98, 35);
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton guardarCondicao = new JButton(guardar);
		guardarCondicao.addMouseListener(this);
		guardarCondicao.setFont(new Font("Tahoma", Font.PLAIN, 15));
		guardarCondicao.setBounds(538, 136, 123, 38);
		frame.getContentPane().add(guardarCondicao);
		
		JButton modificarCondicao = new JButton(editar);
		modificarCondicao.addMouseListener(this);
		modificarCondicao.setFont(new Font("Tahoma", Font.PLAIN, 15));
		modificarCondicao.setBounds(650, 136, 123, 38);
		frame.getContentPane().add(modificarCondicao);
		
		JButton limparCondicao = new JButton(limpar);
		limparCondicao.addMouseListener(this);
		limparCondicao.setFont(new Font("Tahoma", Font.PLAIN, 15));
		limparCondicao.setBounds(650, 200, 123, 38);
		frame.getContentPane().add(limparCondicao);	
		
		JButton adicionarNumero = new JButton(add_num);
		adicionarNumero.addMouseListener(this);
		adicionarNumero.setFont(new Font("Tahoma", Font.PLAIN, 17));
		adicionarNumero.setBounds(193, 266, 165, 35);
		frame.getContentPane().add(adicionarNumero);
		
		textField_1 = new JTextField();
		textField_1.setBounds(80, 265, 104, 36);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JButton guardarTotal = new JButton(salvar);
		guardarTotal.addMouseListener(this); //falta funcionalidade
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
	        default: operador = condicionador.trim() + " "; break;      
		}
		
		textField_condicao.setText(textField_condicao.getText() + operador);  
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		String command;
		String buttonText = ((JButton) e.getSource()).getText();
		
		/*switch (buttonText) {
		
			case "Adicionar número": 
				command = (textField_1.getText());
				logicExpressionAppend(e, command);
				break;
				
			case "save":
				command = textField_condicao.getText();
				th = new Threshold(command, "teste");

				break;
				
			case "Edit":
				command = textField_condicao.getText();
				th.editCondition(command);
				
				break;
				
			case "Save":
				//botão Save de baixo, falta funcionalidade
				break;
	
			default:
				command = ((JButton) e.getSource()).getText();
				logicExpressionAppend(e, command);
				break; 
				
		}*/
		
/*Change to if-else statement.
 * switch case does not allow to pass a variable in a case statement due to its compilation.
 */
		
		if(buttonText.equals(add_num)) {
			command = (textField_1.getText());
			logicExpressionAppend(e, command);

		} else if(buttonText.equals(guardar)) {
			command = textField_condicao.getText();
			th = new Threshold(command, "teste");

		} else if(buttonText.equals(editar)) {
			command = textField_condicao.getText();
			th.editCondition(command);
			
		} else if(buttonText.equals(salvar)) {
			

		} else if(buttonText.equals(limpar)) {
			textField_condicao.setText("");
			
		} else {
			command = ((JButton) e.getSource()).getText();
			logicExpressionAppend(e, command);
		}
			

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

