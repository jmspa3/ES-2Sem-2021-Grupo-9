package G9.CodeQualityAssessor;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.poi.ss.usermodel.Cell;

import CSQualityControl.CompareCodeSmellsFiles;
import CSQualityControl.QualityControlUtils;
import javax.swing.ImageIcon;
import java.awt.Color;

public class GUI extends JFrame {

	private JPanel contentPane;
	private JTextField txtInsereAPath;
	private JTextField textField;

	private JTable table;
	private String excelPath;
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

		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setColumns(10);
		// to save time / need to change later
		textField.setText("Insira o path do projeto");
		panel.setLayout(new GridLayout(0, 3, 0, 0));
		panel.add(textField);
		
		txtInsereAPath = new JTextField();
		txtInsereAPath.setHorizontalAlignment(SwingConstants.CENTER);

		txtInsereAPath.setColumns(10);
		panel.add(txtInsereAPath);

		// to save time / need to change later
		txtInsereAPath.setText("Insira o path da pasta do ficheiro a comparar");

		JPanel panel_1_2 = new JPanel();
		panel.add(panel_1_2);
		panel_1_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnAbrirXLSX = new JButton("Abrir XLSX");
		panel_1_2.add(btnAbrirXLSX);

		JPanel panel_1_1 = new JPanel();
		panel.add(panel_1_1);
		panel_1_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnProcurar = new JButton("Procurar ");
		panel_1_1.add(btnProcurar);
		
				JButton btnCriarXLSX = new JButton("Criar XLSX");
				panel_1_1.add(btnCriarXLSX);
				
						btnCriarXLSX.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent e) {
								btnCriarAction();
							}
				
							private void btnCriarAction() {
								// write metrics to file
								try {
									new ContentExcel().writeExcel(textField.getText());
									JOptionPane.showMessageDialog(null, "Success!");
								} catch (IOException e) {
									JOptionPane.showMessageDialog(null, "Failure!");
								}
							}
						});

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
						System.out.println("Original:" + file.getAbsolutePath());
					}
				} else if (values == JFileChooser.CANCEL_OPTION) {
					System.out.println("No file is selected");
				} else {
					System.out.println("What did you do!?!");
				}

				excelPath = textField.getText() + File.separator + file.getName() + "_metrics.xlsx";
			}
		});

		JPanel panel_1 = new JPanel();

		panel.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JButton btnProcurarComparar = new JButton("Procurar");
		btnProcurarComparar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnProcurarAction();
			}

			private void btnProcurarAction() {
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

				int values = fc.showOpenDialog(btnProcurar);
				File file = fc.getSelectedFile();

				if (values == JFileChooser.APPROVE_OPTION) {
					if (file.exists()) {
						txtInsereAPath.setText(file.getAbsolutePath());
						System.out.println("Comparado:" + txtInsereAPath.getText());

					}
				} else if (values == JFileChooser.CANCEL_OPTION) {
					System.out.println("No file is selected");
				} else {
					System.out.println("What did you do!?!");
				}
			}
		});
		panel_1.add(btnProcurarComparar);

		JButton btnComparar = new JButton("Comparar");
		panel_1.add(btnComparar);

		btnComparar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnCompararAction();
			}

			private void btnCompararAction() {
				System.out.print("/////////////////");
				System.out.println("original2" + excelPath);
				System.out.println("comparado2" + txtInsereAPath.getText());
				System.out.print("/////////////////");
				CompareCodeSmellsFiles ccs = new CompareCodeSmellsFiles(txtInsereAPath.getText(), excelPath);
				ccs.compareExcelSheets();

			}
		});

		btnAbrirXLSX.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnAbrirAction();
			}

			private void btnAbrirAction() {
				
				ColorRenderer renderer = new ColorRenderer();				

				File file = new File(textField.getText());
				excelPath = textField.getText() + File.separator + file.getName() + "_metrics.xlsx";
				
				int god_col = 0;
				int long_col = 0;
				
				try {
					ContentExcel excel = new ContentExcel();
					excel.setExcelWidthAndHeight(excelPath);
					DefaultTableModel model = new DefaultTableModel(excel.tableHeight, excel.tableWidth);
					table.setModel(model);
					
					excel.setData(excelPath);
					Iterator<Cell> cellIterator = excel.data.iterator();
					short colorCode;
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						model.setValueAt(cell.getStringCellValue(), cell.getRowIndex(), cell.getColumnIndex());
						if(cell.getStringCellValue().toLowerCase().equals("is_god_class"))
							god_col = cell.getColumnIndex();
						else
							if(cell.getStringCellValue().toLowerCase().equals("is_long_method"))
								long_col = cell.getColumnIndex();
						
						colorCode = cell.getCellStyle().getFillForegroundColor();

						switch (colorCode) {
						case 10:
							renderer.redCells.add(cell);
							break;
						case 17:
							renderer.greenCells.add(cell);
							break;
						case 40:
							renderer.blueCells.add(cell);
							break;
						case 53:
							renderer.orangeCells.add(cell);
							break;
						default:
							renderer.whiteCells.add(cell);
							break;
						}
						
					}
					renderer.setColumns(god_col, long_col);
					table.getColumnModel().getColumn(god_col).setCellRenderer(renderer);
					table.getColumnModel().getColumn(long_col).setCellRenderer(renderer);
					
					lblValorPackages.setText(Integer.toString(excel.numberTotalPackages()));
					lblValorClasses.setText(Integer.toString(excel.numberTotalClasses()));
					lblValorMetodos.setText(Integer.toString(excel.numberTotalMethods()));
					lblValorLinhas.setText(Integer.toString(excel.numberTotalLines()));
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});

		JPanel panel_1_2_1 = new JPanel();
		panel.add(panel_1_2_1);
		panel_1_2_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel FalsoNegativo = new JLabel("FalsoNegativo");
		FalsoNegativo.setToolTipText("Aplicação da regra não deteta code smell e a coluna de comparação é TRUE");
		FalsoNegativo.setBackground(Color.RED);
		FalsoNegativo.setOpaque(true);
		panel_1_2_1.add(FalsoNegativo);
		
		JLabel FalsoPositivo = new JLabel("FalsoPositivo");
		FalsoPositivo.setToolTipText("Aplicação da regra deteta code smell e a coluna de comparação é FALSE");
		FalsoPositivo.setBackground(Color.ORANGE);
		FalsoPositivo.setOpaque(true);
		panel_1_2_1.add(FalsoPositivo);
		
		JLabel VerdadeiroNegativo = new JLabel("VerdadeiroNegativo");
		VerdadeiroNegativo.setToolTipText("Aplicação da regra não deteta code smell e a coluna de comparação é FALSE");
		VerdadeiroNegativo.setBackground(Color.CYAN);
		VerdadeiroNegativo.setOpaque(true);
		panel_1_2_1.add(VerdadeiroNegativo);
		
		JLabel VerdadeiroPositivo = new JLabel("VerdadeiroPositivo");
		VerdadeiroPositivo.setToolTipText("Aplicação da regra deteta code smell e a coluna de comparação é TRUE");
		VerdadeiroPositivo.setBackground(Color.GREEN);
		VerdadeiroPositivo.setOpaque(true);
		panel_1_2_1.add(VerdadeiroPositivo);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		JPanel panel_6 = new JPanel();
		contentPane.add(panel_6, BorderLayout.EAST);
		panel_6.setLayout(new BorderLayout(0, 0));

		JPanel panel_2 = new JPanel();
		panel_6.add(panel_2);
		panel_2.setLayout(new GridLayout(4, 2, 0, 0));

		JLabel lblNumeroPackages = new JLabel("  N\u00FAmero Total de Packages:  ");
		lblNumeroPackages.setHorizontalAlignment(SwingConstants.LEFT);
		panel_2.add(lblNumeroPackages);

		lblValorPackages.setHorizontalAlignment(SwingConstants.LEFT);
		panel_2.add(lblValorPackages);

		JLabel lblNumeroClasses = new JLabel("  N\u00FAmero Total de Classes:  ");
		lblNumeroClasses.setHorizontalAlignment(SwingConstants.LEFT);
		panel_2.add(lblNumeroClasses);

		lblValorClasses.setHorizontalAlignment(SwingConstants.LEFT);
		panel_2.add(lblValorClasses);

		JLabel lblNumeroMetodos = new JLabel("  N\u00FAmero Total de M\u00E9todos:  ");
		lblNumeroMetodos.setHorizontalAlignment(SwingConstants.LEFT);
		panel_2.add(lblNumeroMetodos);

		lblValorMetodos.setHorizontalAlignment(SwingConstants.LEFT);
		panel_2.add(lblValorMetodos);

		JLabel lblNumeroLinhas = new JLabel("  N\u00FAmero Total de Linhas:  ");
		lblNumeroLinhas.setHorizontalAlignment(SwingConstants.LEFT);
		panel_2.add(lblNumeroLinhas);

		lblValorLinhas.setHorizontalAlignment(SwingConstants.LEFT);
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

//		JPanel panel_5 = new JPanel();
//		panel_7.add(panel_5, BorderLayout.SOUTH);
//		panel_5.setLayout(new GridLayout(2, 2, 0, 0));
//
//		JLabel lblNumeroAcertos = new JLabel("N\u00FAmero Total de Acertos:");
//		panel_5.add(lblNumeroAcertos);
//
//		panel_5.add(lblValorAcertos);
//
//		JLabel lblNumeroFalhas = new JLabel("N\u00FAmero Total de Falhas:");
//		panel_5.add(lblNumeroFalhas);
//
//		panel_5.add(lblValorFalhas);
	}
}
