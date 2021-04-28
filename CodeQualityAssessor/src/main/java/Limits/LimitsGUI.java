package Limits;

/**
 * LimitsGUI enables changes to the selected Threshold
 * 
 * @author Jose/Tiago
 */

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import javax.swing.JPanel;

public class LimitsGUI extends JDialog implements MouseListener  {
	
	//implements ActionListener

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JFrame framo;
	//Nomes dos botões
	private String salvar = "Alterar"; //Alterar condição e sair
	private JTextField numero1;
	private JTextField numero2;
	private ArrayList<String> listaOperadores = new ArrayList<>();
	private ArrayList<String> listaArgumentos = new ArrayList<>();
	private ArrayList<String> listaOpCondicional = new ArrayList<>();
	private Threshold threshold;
	private JComboBox<String> operador2;
	private JComboBox<String> operador1;
	private JComboBox<String> argumento2;
	private JComboBox<String> argumento1;
	private JPanel panel;
	private JPanel panel_1;
	private JComboBox<String> logicOrAnd;

	/**
	 * Launch the application.
	 */
	
	/* Test main 
	 * 
	 * public static void main(String[] args) {
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
	}*/

	/**
	 * Create the application.
	 * @param listaRegras 
	 * @param actionListener 
	 * @wbp.parser.entryPoint
	 */
	public LimitsGUI(Threshold threshold, JDialog listaRegra) {
		//this.parent = listaRegra;
		initialize(threshold);
		framo.setVisible(true);
	}
	
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(Threshold threshold) {		
		this.threshold = threshold;
		listaOperadores.add(">");
		listaOperadores.add("<");
		
		listaOpCondicional.add("||");
		listaOpCondicional.add("&&");
		
		listaArgumentos.add("NOM_class");
		listaArgumentos.add("LOC_class");
		listaArgumentos.add("WMC_class");
		listaArgumentos.add("LOC_method");
		listaArgumentos.add("CYCLO_method");

		
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		listaOperadores.forEach(r -> listModel.addElement(r));
		
		framo = new JFrame();

		framo.setBounds(100, 100, 780, 542);
		framo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		framo.getContentPane().setLayout(new BorderLayout(0, 0));
		
		panel_1 = new JPanel();
		framo.getContentPane().add(panel_1, BorderLayout.NORTH);
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
		framo.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel condicao = new JLabel("Condição");
		panel.add(condicao);
		condicao.setFont(new Font("Tahoma", Font.PLAIN, 15));
		condicao.setHorizontalAlignment(SwingConstants.CENTER);
		
		//-----Primeira condição
		
		argumento1 = new JComboBox<String>();
		panel.add(argumento1);
		
		operador1 = new JComboBox<String>();
		panel.add(operador1);

		
				numero1 = new JTextField(Integer.toString(threshold.getNumber(0)));
				panel.add(numero1);
				numero1.setColumns(10);
				
				//-----Operador Lógico OR AND
						
				logicOrAnd = new JComboBox<String>();
				panel.add(logicOrAnd);
				
		

				
				//-----Segunda condição
				
				argumento2 = new JComboBox<String>();
				panel.add(argumento2);
				
		operador2 = new JComboBox<String>();
		panel.add(operador2);
		
		
		numero2 = new JTextField(Integer.toString(threshold.getNumber(1)));
		panel.add(numero2);
		numero2.setColumns(10);
		
		JButton guardarTotal = new JButton(salvar);
		guardarTotal.addMouseListener(this); 
		guardarTotal.setFont(new Font("Tahoma", Font.PLAIN, 24));
		framo.getContentPane().add(guardarTotal, BorderLayout.SOUTH);
		listaOperadores.forEach(r -> operador1.addItem(r));
		listaOperadores.forEach(r -> operador2.addItem(r));
		
		listaArgumentos.forEach(r -> argumento1.addItem(r));
		listaArgumentos.forEach(r -> argumento2.addItem(r));
		
		listaOpCondicional.forEach(r -> logicOrAnd.addItem(r));
		
		argumento1.setSelectedItem(threshold.getArgument(0));
		operador1.setSelectedItem(threshold.getArgument(1));
		logicOrAnd.setSelectedItem(threshold.getArgument(2));
		argumento2.setSelectedItem(threshold.getArgument(3));	
		operador2.setSelectedItem(threshold.getArgument(4));

	}
	
	public Threshold getThreshold() {
		return threshold;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		String buttonText = ((JButton) e.getSource()).getText();
		
		if(buttonText.equals(salvar)) {
			threshold.editNumbers(Integer.parseInt(numero1.getText()), Integer.parseInt(numero2.getText()));
			threshold.editOperators((String) operador1.getSelectedItem(), (String) operador2.getSelectedItem());
			threshold.editArgs((String) argumento1.getSelectedItem(), (String) argumento2.getSelectedItem());
			threshold.editConditionalOp((String) logicOrAnd.getSelectedItem());
			framo.setVisible(false);
			framo.dispose();
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

