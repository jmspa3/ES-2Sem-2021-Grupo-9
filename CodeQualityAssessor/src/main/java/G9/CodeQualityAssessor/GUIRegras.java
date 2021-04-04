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

public class GUIRegras {
	private ArrayList<Threshold> lista = new ArrayList<GUIRegras.Threshold>();

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
		JList<Threshold> jl = new JList<Threshold>(listModel);
		jl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jl.setLayoutOrientation(JList.VERTICAL);
		
		//open rule editor if clicked
		JButton b = new JButton("Editar");
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			    if (listModel.getSize() == 0) { //No rules
			        b.setEnabled(false);
			    } else { //Selected an index.
//			    	new LimitsGUI(jl.getSelectedValue());
			    }
			}});
		
		
		listaRegras.add(b, BorderLayout.SOUTH);
		JScrollPane panel = new JScrollPane(jl);
		listaRegras.add(panel, BorderLayout.CENTER);
		listaRegras.setVisible(true);
	}

	private void getThresholds() {
		//get crap
		
		//to vizualize
		lista.add(new Threshold("Regra 1"));
		lista.add(new Threshold("Regra 2"));
		lista.add(new Threshold("Regra 3"));
	}

	private class Threshold {
		String name;
		
		public Threshold(String name) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
}
