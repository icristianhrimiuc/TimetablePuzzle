package timetablepuzzle.swing.windows.cards.other;

import javax.swing.table.AbstractTableModel;

import timetablepuzzle.eclipselink.entities.administration.TimePreferences;
import timetablepuzzle.eclipselink.entities.administration.TimePreferences.TimePreference;

class WeekPreferencesTableModel extends AbstractTableModel {
	/**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;

	private String[] columnNames = { "", "08-09", "09-10", "10-11", "11-12", "12-13", "13-14", "14-15",
			"15-16", "16-17", "17-18", "18-19", "19-20", };
	private String[] rowNames = { "", "Mon", "Tue", "Wed", "Thu", "Fri" };
	private TimePreferences data;
	private TimePreference[] nextPreferenceByColumn;
	private TimePreference[] nextPreferenceByRow;
	private TimePreference nextGlobalPreference;
	
	public WeekPreferencesTableModel() {
	}

	public void setData(TimePreferences data) {
		this.data = data;
		refreshNextPreferenceOnColumnsAndRows();
	}
	
	private void refreshNextPreferenceOnColumnsAndRows(){
		// Set all column preferences to NEUTRAL
		int nrOfColumns = columnNames.length;
		this.nextPreferenceByColumn = new TimePreference[nrOfColumns];
		for(int i=0;i<nrOfColumns;i++){
			this.nextPreferenceByColumn[i] = TimePreference.PREFFERED;
		}
		
		// Set all row preferences to NEUTRAL
		int nrOfRows = rowNames.length;
		this.nextPreferenceByRow = new TimePreference[nrOfRows];
		for(int i=0;i<nrOfRows;i++){
			this.nextPreferenceByRow[i] = TimePreference.PREFFERED;
		}
		
		this.nextGlobalPreference = TimePreference.PREFFERED;
	}
	
	public TimePreferences getData(){
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
					cellPreference = this.nextPreferenceByColumn[columnIndex-1];
				} else {
					cellPreference = this.nextGlobalPreference;
				}
			} else {
				if ((columnIndex > 0) && (columnIndex < getColumnCount())) {
					cellPreference = this.data.getPreferencesByDayAndTime(TimePreferences.DayOfTheWeek.values()[rowIndex-1], columnIndex-1);
				} else {
					cellPreference = this.nextPreferenceByRow[rowIndex-1];
				}
			}
		}
    	
    	return cellPreference;
    }
	
	public void setCellPreference(int rowIndex, int columnIndex, TimePreference timePreference){
		if ((rowIndex >= 0) && (rowIndex < getRowCount())) {
			if (rowIndex == 0) {
				if ((columnIndex > 0) && (columnIndex < getColumnCount())) {
					this.nextPreferenceByColumn[columnIndex-1] = timePreference;
				} else {
					this.nextGlobalPreference = timePreference;
				}
			} else {
				if ((columnIndex > 0) && (columnIndex < getColumnCount())) {
					this.data.setPreferencesByDayAndTime(TimePreferences.DayOfTheWeek.values()[rowIndex-1], timePreference, columnIndex-1);
				} else {
					this.nextPreferenceByRow[rowIndex-1] = timePreference;
				}
			}
		}
	}

	public void incrementCellPreference(int rowIndex, int columnIndex) {
		if ((rowIndex >= 0) && (rowIndex < getRowCount())) {
			if (rowIndex == 0) {
				if ((columnIndex > 0) && (columnIndex < getColumnCount())) {
					applyColumnPreferences(columnIndex);
					TimePreference currentPreference = this.nextPreferenceByColumn[columnIndex-1];
					this.nextPreferenceByColumn[columnIndex-1] = TimePreferences.getNextPreference(currentPreference);
				} else {
					applyGlobalPreferenceToAllCells();
					this.nextGlobalPreference = TimePreferences.getNextPreference(this.nextGlobalPreference);
				}
			} else {
				if ((columnIndex > 0) && (columnIndex < getColumnCount())) {
					this.data.incrementPreferenceByDayAndTime(TimePreferences.DayOfTheWeek.values()[rowIndex-1], columnIndex-1);
				} else {
					applyRowPreferences(rowIndex);
					TimePreference currentPreference = this.nextPreferenceByRow[rowIndex-1];
					this.nextPreferenceByRow[rowIndex-1] = TimePreferences.getNextPreference(currentPreference);
				}
			}
		}
	}
	
	private void applyColumnPreferences(int columnIndex){
		TimePreference columnPreference = this.nextPreferenceByColumn[columnIndex-1];
		
		for(int rowIndex=1; rowIndex<rowNames.length; rowIndex++){
			setCellPreference(rowIndex, columnIndex, columnPreference);
		}
	}
	
	private void applyGlobalPreferenceToAllCells(){		
		// Apply to all cells
		for(int columnIndex=0;columnIndex<this.columnNames.length;columnIndex++){
			// Apply to column
			this.nextPreferenceByColumn[columnIndex] = this.nextGlobalPreference;
			for(int rowIndex=0; rowIndex<this.rowNames.length; rowIndex++){
				// Apply to row
				this.nextPreferenceByRow[rowIndex] = this.nextGlobalPreference;
				// Apply to cell
				setCellPreference(rowIndex, columnIndex, this.nextGlobalPreference);
			}
		}
	}
	
	private void applyRowPreferences(int rowIndex){
		TimePreference rowPreference = this.nextPreferenceByRow[rowIndex-1];
		
		for(int columnIndex=1; columnIndex<columnNames.length; columnIndex++){
			setCellPreference(rowIndex, columnIndex, rowPreference);
		}
	}
}
