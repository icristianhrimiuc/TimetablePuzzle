package timetablepuzzle.swing.windows.cards.other;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class WeekPreferencesCellRenderer extends DefaultTableCellRenderer {
	/**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;
	
	public static final Color[] ColorsForPreference = {new Color(0xFF9999), new Color(0xFFCC99), new Color(0xFFFF99),
			new Color(0xE0E0E0), new Color(0x99CCFF), new Color(0x99FFFF), new Color(0x99FF99)};
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int col) {

		// Cells are by default rendered as a JLabel.
		JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

		// Get the status for the current row.
		WeekPreferencesTableModel tableModel = (WeekPreferencesTableModel) table.getModel();
		Color bgColor = ColorsForPreference[tableModel.getCellPreference(row, col).ordinal()];
		l.setBackground(bgColor);

		// Return the JLabel which renders the cell.
		return l;
	}
}
