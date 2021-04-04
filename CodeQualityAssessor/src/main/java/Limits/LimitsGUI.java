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
import javax.swing.SwingUtilities;
import javax.swing.JTextPane;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JPanel;

public class LimitsGUI extends JDialog implements MouseListener  {
	
	//implements ActionListener

	private JFrame frame;
	private JList list;
	private JDialog parent;
	private Threshold th;
	private String regra = "", and = "And", or = "Or", lesser = "<", greater = ">", equal = "=";
	//Nomes dos botões
	private String add_num = "Adicionar Número", editar = "Editar", limpar = "Limpar";
	private String salvar = "Guardar e Sair"; //guardar e sair
	private String guardar = "Guardar"; //guardar a configuração da regra
	private JTextField numero1;
	private JTextField numero2;
	private ArrayList<String> lista = new ArrayList<>();
	private Threshold threshold;
	private JComboBox<String> operador2;
	private JComboBox<String> operador1;
	private JTextField argumento2;
	private JTextField argumento1;
	private JPanel panel;
	private JPanel panel_1;

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
	 * @param listaRegras 
	 * @param actionListener 
	 * @wbp.parser.entryPoint
	 */
	public LimitsGUI(Threshold threshold, JDialog listaRegra) {
		//this.parent = listaRegra;
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
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JLabel regralabel = new JLabel("Regra    :");
		panel_1.add(regralabel, BorderLayout.WEST);
		regralabel.setHorizontalAlignment(SwingConstants.CENTER);
		regralabel.setFont(new Font("Tahoma", Font.PLAIN, 23));
		

		
		JLabel regra_1 = new JLabel(threshold.getName());
		panel_1.add(regra_1, BorderLayout.CENTER);
		regra_1.setFont(new Font("Tahoma", Font.PLAIN, 19));
		regra_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel condicao = new JLabel("Condição");
		panel.add(condicao);
		condicao.setFont(new Font("Tahoma", Font.PLAIN, 15));
		condicao.setHorizontalAlignment(SwingConstants.CENTER);
		
		//-----Primeira condição
		
		argumento1 = new JTextField(threshold.getArgument(0));
		panel.add(argumento1);
		argumento1.setEditable(false);
		argumento1.setColumns(10);	
		
		operador1 = new JComboBox<String>();
		panel.add(operador1);
		operador1.setSelectedItem(threshold.getArgument(1));
		
				numero1 = new JTextField(Integer.toString(threshold.getNumber(0)));
				panel.add(numero1);
				numero1.setColumns(10);
				
				//-----Operador Lógico OR AND
				
				JLabel logicop = new JLabel(threshold.getArgument(2));
				panel.add(logicop);
				
				//-----Segunda condição
				
				argumento2 = new JTextField(threshold.getArgument(3));
				panel.add(argumento2);
				argumento2.setEditable(false);
				argumento2.setColumns(10);
				
		operador2 = new JComboBox<String>();
		panel.add(operador2);
		operador2.setSelectedItem(threshold.getArgument(4));
		
		numero2 = new JTextField(Integer.toString(threshold.getNumber(1)));
		panel.add(numero2);
		numero2.setColumns(10);
		
		JButton guardarTotal = new JButton(salvar);
		guardarTotal.addMouseListener(this); 
		guardarTotal.setFont(new Font("Tahoma", Font.PLAIN, 24));
		frame.getContentPane().add(guardarTotal, BorderLayout.SOUTH);
		lista.forEach(r -> operador1.addItem(r));
		lista.forEach(r -> operador2.addItem(r));

	}
	
	public Threshold getThreshold() {
		return threshold;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		String buttonText = ((JButton) e.getSource()).getText();
		
		if(buttonText.equals(salvar)) {
			threshold.editArgs("arg1", "arg2");
			threshold.editNumbers(Integer.parseInt(numero1.getText()), Integer.parseInt(numero2.getText()));
			threshold.editOperators((String)operador1.getSelectedItem(), (String)operador2.getSelectedItem());
			frame.setVisible(false);
			frame.dispose();
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

