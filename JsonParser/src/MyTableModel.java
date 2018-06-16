import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

public class MyTableModel extends AbstractTableModel {


	/**
	 * 
	 */
	
	 
	private static final long serialVersionUID = 3761272200732594980L;
	private String[] beschriftung;
	private ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
	private ArrayList<ArrayList<Boolean>> rowBools = new ArrayList<ArrayList<Boolean>>();
	private ArrayList<Boolean> colBools = new ArrayList<Boolean>();


	public TableModelListener tblmodellistener;

	public MyTableModel(ArrayList<ArrayList<String>> data, String[] beschriftung) {
		this.data = data;
		this.beschriftung = beschriftung;

		this.rowBools.add(colBools);

	}

	@Override
	public int getRowCount() {

		return data.size();
	}

	@Override
	public int getColumnCount() {

		return beschriftung.length;
	}

	public String getColumnName(int column) {
		return beschriftung[column];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		return data.get(rowIndex).get(columnIndex);
	}

	public void setValueAt(Object value, int row, int col) {

		ArrayList<String> temp = new ArrayList<String>();
		temp = data.get(row);
		DecimalFormat format = new DecimalFormat("#0.00");

		if (col == 7) {
			format.applyPattern("#0.000");
		}
		Number number = null;
		try {
			number = format.parse((String) value);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double d = number.doubleValue();
		String formattedText = format.format(d);

		temp.set(col, formattedText);
		data.set(row, temp);
		fireTableCellUpdated(row, col);

	}

	public String format(double value) {
		DecimalFormat format = new DecimalFormat("#0.00");
		String formattedText = format.format(value);

		return formattedText;
	}

	public String format(String value) {
		DecimalFormat format = new DecimalFormat("#0.00");

		String formattedText = format.format(value);

		return formattedText;
	}

}
