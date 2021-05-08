package G9.CodeQualityAssessor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import Limits.*;
import javax.swing.JPanel;

/**
 * GUIRegras builds an user interface to define and change code smell rules
 * 
 * @author Carlos
 */
public class GUIRegras {
	private ArrayList<Threshold> lista = new ArrayList<Threshold>();
	private JFrame listaRegras;

	public GUIRegras() {
		listaRegras = new JFrame();
		listaRegras.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		listaRegras.setBounds(100, 100, 400, 700);
		listaRegras.getContentPane().setLayout(new BorderLayout());
		listaRegras.setResizable(false);

		getThresholds();

		DefaultListModel<Threshold> listModel = new DefaultListModel<Threshold>();
		lista.forEach(r -> listModel.addElement(r));

		JList<Threshold> mostrar_regras = new JList<Threshold>(listModel);
		mostrar_regras.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		mostrar_regras.setLayoutOrientation(JList.VERTICAL);
		JScrollPane panel = new JScrollPane(mostrar_regras);
		listaRegras.getContentPane().add(panel, BorderLayout.CENTER);

		JPanel panel_1 = new JPanel();
		listaRegras.getContentPane().add(panel_1, BorderLayout.SOUTH);

		JButton guardar = new JButton("Guardar");
		guardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RuleHandler.deleteFile();
				RuleHandler.clearData();
				RuleHandler.createFile();
		
				for (Threshold t : lista) {
					RuleHandler.write(t.getName() + ";" + t.getArgument(0) + ";" + t.getArgument(1) + ";"
							+ t.getNumber(0) + ";" + t.getArgument(2) + ";" + t.getArgument(3) + ";" + t.getArgument(4)
							+ ";" + t.getNumber(1));
					
				}
				
			}
		});

		panel_1.setLayout(new BorderLayout(0, 0));
		panel_1.add(guardar, BorderLayout.SOUTH);

		
		JButton editar = new JButton("Editar");
		panel_1.add(editar);
		
		JButton criar = new JButton("Criar");
		criar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                String name = JOptionPane.showInputDialog(listaRegras, "Nome da Regra", null);
    			Threshold th = new Threshold(name);
    			lista.add(th);
    			listModel.addElement(th);
    			
			}
		});
		panel_1.add(criar, BorderLayout.WEST);
		
		JButton apagar = new JButton("Apagar");
		panel_1.add(apagar, BorderLayout.EAST);
		apagar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selected = mostrar_regras.getSelectedIndex();
				if(selected >= 0 || selected < listModel.size()) {
					lista.remove(mostrar_regras.getSelectedValue());
	    			listModel.remove(mostrar_regras.getSelectedIndex());
				}
				
			}
		});
		
		editar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (listModel.getSize() == 0) { 
												
					editar.setEnabled(false);
				} else { 
					JDialog regra = new JDialog(listaRegras);
					regra.setModal(true);
					LimitsGUI editarRegra = new LimitsGUI(mostrar_regras.getSelectedValue(), regra);
					saveThreshold(editarRegra.getThreshold());
				}

			}

			private void saveThreshold(Threshold threshold) {
				Threshold old = mostrar_regras.getSelectedValue();
				old = threshold;
				mostrar_regras.setSelectedValue(old, false);
			}
		});
		listaRegras.setVisible(true);
	}

	
	/**
	 * Reads Code Smells rules from RuleHandler and presents them as a list
	 */
	private void getThresholds() {
		
		String rulesLine = RuleHandler.getRules();
		if (!rulesLine.isEmpty()) {
			String[] rules = rulesLine.split("\n", lista.size());
			
			
			boolean isLong = false;
			boolean isGod = false;
			for (String rule : rules) {	
				lista.add(ruleToThreshold(rule));
				String[] arr = rule.split(";", 8);
				if(arr[0].toLowerCase().equals("is_long_method")) isLong = true;
				else if(arr[0].toLowerCase().equals("is_god_class")) isGod = true;
			}
			
			if(!isLong) {
				Threshold is_Long_Method = new Threshold("is_long_method");
				is_Long_Method.insertCondition("LOC_method > && CYCLO_method >");
				is_Long_Method.editNumbers(35, 11);
				lista.add(is_Long_Method);

			}
			
			if(!isGod) {
				Threshold is_God_Class = new Threshold("is_god_class");
				is_God_Class.insertCondition("WMC_class > || NOM_class >");
				is_God_Class.editNumbers(250, 40);
				lista.add(is_God_Class);

			}
			
		} else {

			Threshold is_Long_Method = new Threshold("is_Long_Method");
			is_Long_Method.insertCondition("LOC_method > && CYCLO_method >");
			is_Long_Method.editNumbers(35, 11);

			Threshold is_God_Class = new Threshold("is_God_Class");
			is_God_Class.insertCondition("WMC_class > || NOM_class >");
			is_God_Class.editNumbers(250, 40);


			lista.add(is_Long_Method);
			lista.add(is_God_Class);
		}
	}

	
	/**
	 * 
	 * Given a rule as a type String returns a Threshold
	 * 
	 * @param rule
	 * @return Threshold object
	 */
	public Threshold ruleToThreshold(String rule) {
		String[] arr = rule.split(";", 8);

		System.out.println(arr[1] + " " + arr[2] + " " + arr[4] + " " + arr[5] + " " + arr[6]);

		Threshold t = new Threshold(arr[0]);
		t.insertCondition(arr[1] + " " + arr[2] + " " + arr[4] + " " + arr[5] + " " + arr[6]);
		t.editNumbers(Integer.parseInt(arr[3].trim()), Integer.parseInt(arr[7].trim()));

		return t;

	}

}
