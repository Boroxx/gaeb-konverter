import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

public class Display {

	private JFrame frame;
	private JTable table, aufmassTable;
	private JScrollPane jscrollpane, jAufmassScrollPane;
	private JPanel panelScrollbar, panel, panelmid, panelbottom;
	private JTextField search, saveName;

	private MyTableModel tablemodel_lv, tablemodel_aufmass;

	private JButton selectedToAufmassBtn, searchBtn, DEBUG, speichernBtn;

	private String[] beschriftung_lv = { "Position", "Einheit", "Kurztext" };
	private String[] beschriftung_aufmass = { "Position", "Einheit", "Kurztext", "Anzahl", "Laenge", "Breite", "Tiefe",
			"Gesamt" };
	private static ArrayList<ArrayList<String>> tempList = new ArrayList<ArrayList<String>>();
	// private List<CellEditor> listeners = new ArrayList<CellEditor>();

	private TableRowSorter<MyTableModel> sorter;
	private final int WIDTH;
	private final int HEIGHT;
	private CSVParser csvparser;

	public Display() {

		frame = new JFrame("GAEB-MODUL \u00a9 Boris Tenelsen");
		//frame.setPreferredSize(new Dimension(1920, 1000));
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		panelScrollbar = new JPanel();
		panelmid = new JPanel();
		panelbottom = new JPanel();

		search = new JTextField("");
		saveName = new JTextField("beispieldatei");
		search.setPreferredSize(new Dimension(500, 20));
		saveName.setPreferredSize(new Dimension(300, 20));
		// Tables einlesen
		init();
		 Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		 WIDTH = screenSize.width;
		 HEIGHT = screenSize.height;
		
		jscrollpane = new JScrollPane(table);
		jAufmassScrollPane = new JScrollPane(aufmassTable);
		
		jscrollpane.setPreferredSize(new Dimension((int) (WIDTH/2.2),(int)(HEIGHT/1.5)));
		jAufmassScrollPane.setPreferredSize(new Dimension((int) (WIDTH/2.2),(int) (HEIGHT/1.5)));

		panel.add(jscrollpane);
		panelScrollbar.add(jAufmassScrollPane);
		panelmid.add(selectedToAufmassBtn);
		panelbottom.add(search);
		panelbottom.add(searchBtn);
		panelbottom.add(saveName);
		panelbottom.add(speichernBtn);
		panelbottom.add(DEBUG);

		frame.add(panelScrollbar, BorderLayout.EAST);
		frame.add(panelmid, BorderLayout.CENTER);
		frame.add(panel, BorderLayout.WEST);
		frame.add(panelbottom, BorderLayout.SOUTH);
		frame.pack();
		frame.setVisible(true);

		csvparser = new CSVParser(saveName);

	}

	private void init() {

		tablemodel_lv = new MyTableModel(Dateimanager.positionen, beschriftung_lv) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int col) {

				return false;

			}

		};

		tablemodel_aufmass = new MyTableModel(tempList, beschriftung_aufmass) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int col) {
				if (col > 2)
					return true;
				else
					return false;

			}

		};

		table = new JTable(tablemodel_lv);
		aufmassTable = new JTable(tablemodel_aufmass);

		sorter = new TableRowSorter<MyTableModel>(tablemodel_lv);
		table.setRowSorter(sorter);

		initSelectorButton();
		initSearchButton();
		// initCellEditable();

		initDEBUGbutton();
		initSpeicherButton();

	}

	private void initSpeicherButton() {
		speichernBtn = new JButton("Exportieren");
		speichernBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				double val = 0;
				DecimalFormat format = new DecimalFormat("#0.000");
				Number number = null;
				for (int x = 0; x < tablemodel_aufmass.getRowCount(); x++) {
					val = 1;
					for (int i = 3; i < 7; i++) {

						try {
							number = format.parse(tablemodel_aufmass.getValueAt(x, i).toString());
						} catch (ParseException e1) {

							e1.printStackTrace();
						}
						double dtemp = number.doubleValue();

						val = val * dtemp;

					}

					String d = format.format(val);
					System.out.println("RAUS: " + d);
					tablemodel_aufmass.setValueAt(d, x, 7);
					System.out.println(val);

				}
				csvparser.storeData();
			}
		});

	}

	private void initDEBUGbutton() {

		DEBUG = new JButton("Debug");

	}

	/*
	 * private void initCellEditable() {
	 * 
	 * for (int i = 3; i < 8; i++) { TableColumn col =
	 * aufmassTable.getColumnModel().getColumn(i); CellEditor c = new
	 * CellEditor();
	 * 
	 * col.setCellEditor(c);
	 * 
	 * listeners.add(c);
	 * 
	 * } }
	 */
	private void initSearchButton() {

		searchBtn = new JButton("Suche");
		searchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String text = search.getText();

				if (text.length() == 0) {
					sorter.setRowFilter(null);
				} else {
					sorter.setRowFilter(RowFilter.regexFilter(text));
				}
			}
		});

	}

	private void initSelectorButton() {

		/*
		 * initaliesere Button um aus dem LeistungsVerzeichnis in die Aufmass
		 * Tabelle zu kopieren enthält Actionlistener und erweitert die
		 * ArrayListe die der MyTableModel Klasse angehört
		 */

		selectedToAufmassBtn = new JButton("->");// TODO Auto-generated method
													// stub
		selectedToAufmassBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				ArrayList<String> temp = new ArrayList<String>();

				for (int i = 0; i < 3; i++) {

					temp.add((String) table.getValueAt(table.getSelectedRow(), i));

				}

				temp.add("1,00");

				for (int i = 1; i < 5; i++) {
					temp.add("1,00");
				}

				tempList.add(temp);
				initFormatting();
				tablemodel_aufmass.fireTableDataChanged();

			}

		});
	}

	public static ArrayList<ArrayList<String>> getUpdatedAufmass() {
		return tempList;
	}

	public void initFormatting() {

		for (int col = 3; col < aufmassTable.getColumnCount(); col++) {
			aufmassTable.getColumnModel().getColumn(col).setCellRenderer(new MengenCellRenderer());

		}
	}

}
