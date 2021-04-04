package G9.CodeQualityAssessor;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import javax.swing.JScrollPane;
import java.io.File;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI extends JFrame {

	private String projectPath = "";
	private String smellyPath;

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;

	private JLabel lblValorPackages = new JLabel();
	private JLabel lblValorClasses = new JLabel();
	private JLabel lblValorMetodos = new JLabel();
	private JLabel lblValorLinhas = new JLabel();
	
	private JLabel lblValorAcertos = new JLabel();
	private JLabel lblValorFalhas = new JLabel();
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1260, 720);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new BorderLayout(0, 0));

		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(10);

		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnProcurar = new JButton("Procurar");
		btnProcurar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				btnProcurarAction();
			}

			private void btnProcurarAction() {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				int values = fc.showOpenDialog(btnProcurar);
				File file = fc.getSelectedFile();

				if (values == JFileChooser.APPROVE_OPTION) {
					if (file.exists()) {
						textField.setText(file.getAbsolutePath());
						System.out.println(file.getAbsolutePath());
					}
				} else if (values == JFileChooser.CANCEL_OPTION) {
					System.out.println("No file is selected");
				} else {
					System.out.println("What did you do!?!");
				}	
			}
		});
		panel_1.add(btnProcurar);

		JButton btnCriarXLSS = new JButton("Criar XLSS");
		btnCriarXLSS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCriarAction();
			}

			private void btnCriarAction() {
				// get path from textfield
				String t = textField.getText();

				// verify existance of folder

				// do stuff
				ArrayList<String> filepaths;
				ArrayList<String[]> allMetrics;
				
//				for(String filepath : filepaths) {
					//criar objeto metrics
//					allMetrics
//				}

				// maybe open window confirming action
			}
		});

		panel_1.add(btnCriarXLSS);

		JButton btnAbrirXLSS = new JButton("Abrir XLS");
		btnAbrirXLSS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAbrirAction();
			}

			private void btnAbrirAction() {
//				FileReader fr = new FileReader(smellyPath);
//				ArrayList<String[]> data = fr.getData();

				// SET TABLE

				ArrayList<String[]> data = new ArrayList<String[]>();
				String[] columnNames = { "MethodID", "package", "class", "method", "NOM_class", "LOC_class",
						"WMC_class", "is_God_Class", "LOC_method", "CYCLO_method", "is_Long_Method" };
				String[] data1 = { "1", "com.jasml.compiler", "GrammerException", "GrammerException(int,String)", "4",
						"18", "4", "FALSO", "3", "1", "FALSO" };
				String[] data2 = { "1", "com.jasml.compiler", "GrammerException", "GrammerException(int,String)", "4",
						"18", "4", "FALSO", "3", "1", "FALSO" };

				data.add(data1);
				data.add(data2);


				
//				for (String[] singleMetrics : data) {
//					data.add(singleMetrics);
//				}
				
				DefaultTableModel model = new DefaultTableModel();

				for (String s : columnNames) {
					model.addColumn(s);
				}
				for (String[] r : data) {
					model.addRow(r);
				}

				table.setModel(model);

				// SET Labels

				// fr.getMetricas
				// labels.setText();
				lblValorPackages.setText("1");
				lblValorClasses.setText("1");
				lblValorMetodos.setText("1");
				lblValorLinhas.setText("1");
				

			}
		});

		panel_1.add(btnAbrirXLSS);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
//		table = new JTable();
		scrollPane.setViewportView(table);

		JPanel panel_6 = new JPanel();
		contentPane.add(panel_6, BorderLayout.EAST);
		panel_6.setLayout(new BorderLayout(0, 0));

		JPanel panel_2 = new JPanel();
		panel_6.add(panel_2);
		panel_2.setLayout(new GridLayout(4, 2, 0, 0));

		JLabel lblNumeroPackages = new JLabel("N\u00FAmero Total de Packages:");
		lblNumeroPackages.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_2.add(lblNumeroPackages);

		lblValorPackages.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblValorPackages);

		JLabel lblNumeroClasses = new JLabel("N\u00FAmero Total de Classes:");
		lblNumeroClasses.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_2.add(lblNumeroClasses);

		lblValorClasses.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblValorClasses);

		JLabel lblNumeroMetodos = new JLabel("N\u00FAmero Total de M\u00E9todos:");
		lblNumeroMetodos.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_2.add(lblNumeroMetodos);

		lblValorMetodos.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblValorMetodos);

		JLabel lblNumeroLinhas = new JLabel("N\u00FAmero Total de Linhas:");
		lblNumeroLinhas.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_2.add(lblNumeroLinhas);
		
		lblValorLinhas.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblValorLinhas);

		JPanel panel_7 = new JPanel();
		panel_6.add(panel_7, BorderLayout.SOUTH);
		panel_7.setLayout(new BorderLayout(0, 0));

		JButton btnRegras = new JButton("Regras");
		btnRegras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new GUIRegras();
			}
		});
		panel_7.add(btnRegras);

		JPanel panel_5 = new JPanel();
		panel_7.add(panel_5, BorderLayout.SOUTH);
		panel_5.setLayout(new GridLayout(2, 2, 0, 0));

		JLabel lblNumeroAcertos = new JLabel("N\u00FAmero Total de Acertos:");
		panel_5.add(lblNumeroAcertos);

		panel_5.add(lblValorAcertos);

		JLabel lblNumeroFalhas = new JLabel("N\u00FAmero Total de Falhas:");
		panel_5.add(lblNumeroFalhas);

		panel_5.add(lblValorFalhas);
	}

	
	
	private void setStaticComponents() {

	}
}