package timetablepuzzle.swing.windows.cards.other;

import java.util.Calendar;

import javax.swing.table.AbstractTableModel;

import timetablepuzzle.eclipselink.entities.administration.DatePattern;

class MonthDatePatternTableModel extends AbstractTableModel {
	/**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;
	
	private static Calendar calendarInstance = Calendar.getInstance();

	private String[] columnNames = { "", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun" };
	private String[] rowNames = { "", "", "", "", "", "", "", "" };
	private DatePattern data;
	private boolean[] activeCells;
	private boolean[] selectedDates;
	private int firstDayOfMonthWeekIndex;
	private int lastDayOfMonth;
	private boolean[] nextSelectionByColumn;
	private boolean[] nextSelectionByRow;
	private boolean nextGlobalPreference;
	
	public MonthDatePatternTableModel(int year, int month) {
		calendarInstance.set(year, month, 1);
		this.firstDayOfMonthWeekIndex = calendarInstance.get(Calendar.DAY_OF_WEEK);
		this.lastDayOfMonth = calendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH);
		int nrOfCells = columnNames.length*rowNames.length;
		this.activeCells = new boolean[nrOfCells];
	}

	public void setData(DatePattern data) {
		this.data = data;
		refreshNextPreferenceOnColumnsAndRows();
		this.fireTableDataChanged();
	}
	
	private void refreshNextPreferenceOnColumnsAndRows(){
		// Set all column preferences to NEUTRAL
		int nrOfColumns = columnNames.length;
		this.nextSelectionByColumn = new TimePreference[nrOfColumns];
		for(int i=0;i<nrOfColumns;i++){
			this.nextSelectionByColumn[i] = TimePreference.PREFFERED;
		}
		
		// Set all row preferences to NEUTRAL
		int nrOfRows = rowNames.length;
		this.nextSelectionByRow = new TimePreference[nrOfRows];
		for(int i=0;i<nrOfRows;i++){
			this.nextSelectionByRow[i] = TimePreference.PREFFERED;
		}
		
		this.nextGlobalPreference = TimePreference.PREFFERED;
	}
	
	public DatePattern getData(){
		return this.data;
	}

	@Override
	public int getColumnCount() {
		return this.columnNames.length;
	}

	@Override
	public int getRowCount() {
		return this.rowNames.length;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(1, columnIndex).getClass();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object columnValue = null;

		if ((rowIndex >= 0) && (rowIndex < getRowCount())) {
			if (rowIndex == 0) {
				if ((columnIndex > 0) && (columnIndex < getColumnCount())) {
					columnValue = this.columnNames[columnIndex];
				} else {
					columnValue = "";
				}
			} else {
				if ((columnIndex > 0) && (columnIndex < getColumnCount())) {
					columnValue = "";
				} else {
					columnValue = this.rowNames[rowIndex];
				}
			}
		}

		return columnValue;
	}

	public TimePreference getCellPreference(int rowIndex, int columnIndex){
    	TimePreference cellPreference = TimePreference.NEUTRAL;

		if ((rowIndex >= 0) && (rowIndex < getRowCount())) {
			if (rowIndex == 0) {
				if ((columnIndex > 0) && (columnIndex < getColumnCount())) {
					cellPreference = this.nextSelectionByColumn[columnIndex-1];
				} else {
					cellPreference = this.nextGlobalPreference;
				}
			} else {
				if ((columnIndex > 0) && (columnIndex < getColumnCount())) {
					cellPreference = this.data.getPreferencesByDayAndTime(DatePattern.DayOfTheWeek.values()[rowIndex-1], columnIndex-1);
				} else {
					cellPreference = this.nextSelectionByRow[rowIndex-1];
				}
			}
		}
    	
    	return cellPreference;
    }
	
	public void setCellPreference(int rowIndex, int columnIndex, TimePreference timePreference){
		if ((rowIndex >= 0) && (rowIndex < getRowCount())) {
			if (rowIndex == 0) {
				if ((columnIndex > 0) && (columnIndex < getColumnCount())) {
					this.nextSelectionByColumn[columnIndex-1] = timePreference;
				} else {
					this.nextGlobalPreference = timePreference;
				}
			} else {
				if ((columnIndex > 0) && (columnIndex < getColumnCount())) {
					this.data.setPreferencesByDayAndTime(DatePattern.DayOfTheWeek.values()[rowIndex-1], timePreference, columnIndex-1);
				} else {
					this.nextSelectionByRow[rowIndex-1] = timePreference;
				}
			}
		}
	}

	public void incrementCellPreference(int rowIndex, int columnIndex) {
		if ((rowIndex >= 0) && (rowIndex < getRowCount())) {
			if (rowIndex == 0) {
				if ((columnIndex > 0) && (columnIndex < getColumnCount())) {
					applyColumnPreferences(columnIndex);
					TimePreference currentPreference = this.nextSelectionByColumn[columnIndex-1];
					this.nextSelectionByColumn[columnIndex-1] = DatePattern.getNextPreference(currentPreference);
				} else {
					applyGlobalPreferenceToAllCells();
					this.nextGlobalPreference = DatePattern.getNextPreference(this.nextGlobalPreference);
				}
			} else {
				if ((columnIndex > 0) && (columnIndex < getColumnCount())) {
					this.data.incrementPreferenceByDayAndTime(DatePattern.DayOfTheWeek.values()[rowIndex-1], columnIndex-1);
				} else {
					applyRowPreferences(rowIndex);
					TimePreference currentPreference = this.nextSelectionByRow[rowIndex-1];
					this.nextSelectionByRow[rowIndex-1] = DatePattern.getNextPreference(currentPreference);
				}
			}
		}
	}
	
	private void applyColumnPreferences(int columnIndex){
		TimePreference columnPreference = this.nextSelectionByColumn[columnIndex-1];
		
		for(int rowIndex=1; rowIndex<rowNames.length; rowIndex++){
			setCellPreference(rowIndex, columnIndex, columnPreference);
		}
	}
	
	private void applyGlobalPreferenceToAllCells(){		
		// Apply to all cells
		for(int columnIndex=0;columnIndex<this.columnNames.length;columnIndex++){
			// Apply to column
			this.nextSelectionByColumn[columnIndex] = this.nextGlobalPreference;
			for(int rowIndex=0; rowIndex<this.rowNames.length; rowIndex++){
				// Apply to row
				this.nextSelectionByRow[rowIndex] = this.nextGlobalPreference;
				// Apply to cell
				setCellPreference(rowIndex, columnIndex, this.nextGlobalPreference);
			}
		}
	}
	
	private void applyRowPreferences(int rowIndex){
		TimePreference rowPreference = this.nextSelectionByRow[rowIndex-1];
		
		for(int columnIndex=1; columnIndex<columnNames.length; columnIndex++){
			setCellPreference(rowIndex, columnIndex, rowPreference);
		}
	}
}
