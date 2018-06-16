import java.awt.Component;
import java.text.DecimalFormat;
import java.text.ParseException;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class MengenCellRenderer extends DefaultTableCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5170739342416954272L;
	private static final TableCellRenderer RENDERER = new DefaultTableCellRenderer();

	@Override

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		DecimalFormat format = new DecimalFormat("#0.00");
		String temp = (String) value;
		Number number = null;
		try {
			number = format.parse(temp);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		double d = number.doubleValue();

		String formattedText = format.format(d);
		setText(formattedText);

		return c;

	}

}
