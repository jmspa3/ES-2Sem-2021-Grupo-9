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
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import Limits.*;
import javax.swing.JPanel;

public class GUIRegras {
	private ArrayList<Threshold> lista = new ArrayList<Threshold>();
	private JFrame listaRegras;

	public GUIRegras()  {
		listaRegras = new JFrame();
		listaRegras.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		listaRegras.setBounds(100, 100, 400, 700);
		listaRegras.getContentPane().setLayout(new BorderLayout());
		listaRegras.setResizable(false);
		
		//Get list with rules
		getThresholds();
		
		DefaultListModel<Threshold> listModel = new DefaultListModel<Threshold>();
		lista.forEach(r -> listModel.addElement(r));
		
		//Display rules
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
				
				
			}
		});
		
		
		
		panel_1.setLayout(new BorderLayout(0, 0));
		panel_1.add(guardar, BorderLayout.SOUTH);
		
		//Open rule editor if clicked
		JButton editar = new JButton("Editar");
		panel_1.add(editar);
		editar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			    if (listModel.getSize() == 0) { //No rules //Isn't this kind of useless since we always create the two rules: is_Long_Method and is_God_Class?
			        editar.setEnabled(false);
			    } else { //Selected an index.
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
	


	private void getThresholds() {
		//Create our custom Thresholds
		
		Threshold is_Long_Method = new Threshold("is_Long_Method");
		is_Long_Method.insertCondition("LOC_method > || CYCLO_method >");
		is_Long_Method.editNumbers(50, 10);
		
		Threshold is_God_Class = new Threshold("is_God_Class");
		is_God_Class.insertCondition("WMC_class > || NOM_class >");
		is_God_Class.editNumbers(50, 10);
		
		lista.add(is_Long_Method);
		lista.add(is_God_Class);
	}

}
