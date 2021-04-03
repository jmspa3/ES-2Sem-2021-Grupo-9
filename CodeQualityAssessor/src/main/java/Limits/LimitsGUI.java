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
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JComboBox;

public class LimitsGUI implements MouseListener {
	
	//implements ActionListener

	private JFrame frame;
	private JList list;
	private Threshold th;
	private String regra = "", and = "And", or = "Or", lesser = "<", greater = ">", equal = "=";
	//Nomes dos botões
	private String add_num = "Adicionar Número", editar = "Editar", limpar = "Limpar";
	private String salvar = "Guardar e Sair"; //guardar e sair
	private String guardar = "Guardar"; //guardar a configuração da regra
	private JTextField textField;
	private JTextField textField_1;
	private ArrayList<String> lista = new ArrayList<>();
	private Threshold threshold;
	private JComboBox<String> comboBox_1;
	private JComboBox<String> comboBox;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {		
					//LimitsGUI window = new LimitsGUI();
					//window.initialize();
					//window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public LimitsGUI(Threshold threshold) {
		initialize(threshold);
		frame.setVisible(true);
	}
	
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Threshold threshold) {		
		this.threshold = threshold;
		lista.add(">");
		lista.add("<");
		lista.add("==");
		
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		lista.forEach(r -> listModel.addElement(r));
		
		frame = new JFrame();
		frame.setBounds(100, 100, 780, 542);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Regra    :");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblNewLabel.setBounds(80, 42, 145, 47);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Condição");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(10, 215, 98, 35);
		frame.getContentPane().add(lblNewLabel_1);
		
		JButton guardarTotal = new JButton(salvar);
		guardarTotal.addMouseListener(this); //falta funcionalidade
		guardarTotal.setFont(new Font("Tahoma", Font.PLAIN, 24));
		guardarTotal.setBounds(275, 433, 222, 47);
		frame.getContentPane().add(guardarTotal);
		

		
		JLabel regra = new JLabel(threshold.getName());
		regra.setFont(new Font("Tahoma", Font.PLAIN, 19));
		regra.setHorizontalAlignment(SwingConstants.CENTER);
		regra.setBounds(216, 42, 367, 47);
		frame.getContentPane().add(regra);
		
		textField = new JTextField(threshold.getNumber(0));
		textField.setBounds(290, 224, 86, 20);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel(threshold.getArgument(2));
		lblNewLabel_2.setBounds(386, 227, 46, 14);
		frame.getContentPane().add(lblNewLabel_2);
		
		textField_1 = new JTextField(threshold.getNumber(1));
		textField_1.setColumns(10);
		textField_1.setBounds(654, 224, 86, 20);
		frame.getContentPane().add(textField_1);
		
		comboBox = new JComboBox<String>();
		lista.forEach(r -> comboBox.addItem(r));
		comboBox.setSelectedItem(threshold.getArgument(1));

		comboBox.setBounds(216, 223, 64, 22);
		frame.getContentPane().add(comboBox);
		
		comboBox_1 = new JComboBox<String>();
		lista.forEach(r -> comboBox_1.addItem(r));
		comboBox_1.setSelectedItem(threshold.getArgument(4));
		comboBox_1.setBounds(582, 223, 62, 22);
		frame.getContentPane().add(comboBox_1);
		
		textField_2 = new JTextField(threshold.getArgument(3));
		textField_2.setBounds(486, 224, 86, 20);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField(threshold.getArgument(0));
		textField_3.setBounds(118, 224, 86, 20);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);
	}
	
	
	/*public void logicExpressionAppend(MouseEvent e, String condicionador){  
		String operador;
		switch (condicionador) {
			case "Or":  operador = "|| "; break;      
			case "And":  operador = "&& "; break;	
			case "=":  operador = "== "; break;	
	        default: operador = condicionador.trim() + " "; break;      
		}
		
		textField_condicao.setText(textField_condicao.getText() + operador);  
	}*/
	
	@Override
	public void mousePressed(MouseEvent e) {
		String command;
		String buttonText = ((JButton) e.getSource()).getText();
		
		if(buttonText.equals(salvar)) {
			threshold.editArgs("arg1", "arg2");
			threshold.editNumbers(Integer.parseInt(textField.getText()), Integer.parseInt(textField_1.getText()));
			threshold.editOperators((String)comboBox.getSelectedItem(), (String)comboBox_1.getSelectedItem());
		}
		
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
		
		/*if(buttonText.equals(add_num)) {
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
		}*/
			

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

