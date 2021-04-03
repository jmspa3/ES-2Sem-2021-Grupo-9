package G9.CodeQualityAssessor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import Limits.*;

public class GUIRegras {
	private ArrayList<Threshold> lista = new ArrayList<Threshold>();

	public GUIRegras() {
		JFrame listaRegras = new JFrame();
		listaRegras.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		listaRegras.setBounds(100, 100, 400, 700);
		listaRegras.setLayout(new BorderLayout());
		listaRegras.setResizable(false);
		
		//get list with rules
		getThresholds();
		
		DefaultListModel<Threshold> listModel = new DefaultListModel<Threshold>();
		lista.forEach(r -> listModel.addElement(r));
		
		//display rules
		JList<Threshold> mostrar_regras = new JList<Threshold>(listModel);
		mostrar_regras.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		mostrar_regras.setLayoutOrientation(JList.VERTICAL);
		
		//open rule editor if clicked
		JButton editar = new JButton("Editar");
		editar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			    if (listModel.getSize() == 0) { //No rules
			        editar.setEnabled(false);
			    } else { //Selected an index.
			    	new LimitsGUI(mostrar_regras.getSelectedValue());
			    }
			}});
		
		
		listaRegras.add(editar, BorderLayout.SOUTH);
		JScrollPane panel = new JScrollPane(mostrar_regras);
		listaRegras.add(panel, BorderLayout.CENTER);
		listaRegras.setVisible(true);
	}

	private void getThresholds() {
		//get crap
		
		//to vizualize
		Threshold exemplo = new Threshold("thr exemplo");
		exemplo.insertCondition("WMC_class > || LOC_method ==");
		lista.add(exemplo);
		lista.add(new Threshold("Regra 2"));
		lista.add(new Threshold("Regra 3"));
	}

}
