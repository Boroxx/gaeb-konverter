import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableCellEditor;

public class CellEditor extends AbstractCellEditor implements TableCellEditor {

	JComponent component = new JTextField();
	private List<CellEditorListener> listeners = new ArrayList<CellEditorListener>();

	@Override
	public Object getCellEditorValue() {

		return ((JTextField) component).getText();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

		((JTextField) component).setText((String) value);
		return component;
	}

	public void cancelCellEditing() {
		ChangeEvent event = new ChangeEvent(this);
		for (CellEditorListener listener : listeners.toArray(new CellEditorListener[listeners.size()])) {

			listener.editingStopped(event);
		}
	}

	public void addCellEditorListener(CellEditorListener l) {

	}

}
