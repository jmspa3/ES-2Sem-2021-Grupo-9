package G9.CodeQualityAssessor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observer;

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
		
		//get list with rules
		getThresholds();
		
		DefaultListModel<Threshold> listModel = new DefaultListModel<Threshold>();
		lista.forEach(r -> listModel.addElement(r));
		
		//display rules
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
				RuleHandler.createFile();
				for(Threshold t: lista) {
					RuleHandler.write(t.getName()+";"+t.getArgument(0)+" "+t.getArgument(1)+" "+t.getNumber(0)+";"+t.getArgument(2)+";"+t.getArgument(3)+" "+t.getArgument(4)+" "+t.getNumber(1));
				}
			}
		});
		
		
		
		panel_1.setLayout(new BorderLayout(0, 0));
		panel_1.add(guardar, BorderLayout.SOUTH);
		
		//open rule editor if clicked
		JButton editar = new JButton("Editar");
		panel_1.add(editar);
		editar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			    if (listModel.getSize() == 0) { //No rules
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
		//get crap
		
		//to vizualize
		Threshold exemplo = new Threshold("Long_Method");
		exemplo.insertCondition("LOC_method > ||  CYCLO_method >");
		exemplo.editNumbers(50, 10);
		lista.add(exemplo);
		lista.add(new Threshold("Regra 2"));
		lista.add(new Threshold("Regra 3"));
	}

}
