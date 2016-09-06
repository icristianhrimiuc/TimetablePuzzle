package timetablepuzzle.swing.windows.cards.other.timePreferences;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import timetablepuzzle.entities.other.TimePreferences;

class TimePreferencesTableModel extends AbstractTableModel {
    /**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int Column_Id = 0;
	private static final int Column_Name = 1;
	private static final int Column_MonPreerences = 2;
	private static final int Column_TuePreferences = 3;
	private static final int Column_WedPreferences = 4;
	private static final int Column_ThuPreferences = 5;
	private static final int Column_FriPreferences = 6;

	private String[] columnNames = {"Id","Name","Monday Preferences","Tuesday Preferences",
			"Wednesday Preferences","Thursday Preferences","Friday Preferences"};
    private List<TimePreferences> data;
    
    public TimePreferencesTableModel(){}
    
    public void setData(List<TimePreferences> data)
    {
    	this.data = data;
		this.fireTableDataChanged();
    }
    
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public int getRowCount() {
    	int nrOfRows = 0;
    	if(data != null)
    	{
    		nrOfRows = data.size();
    	}
    	
        return nrOfRows;
    }
    
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	TimePreferences timePreferences = data.get(rowIndex);
    	Object colummnValue = null;
        switch (columnIndex) {
            case Column_Id:
            	colummnValue = timePreferences.getId();
                break;
            case Column_Name:
            	colummnValue = timePreferences.getName();
                break;
            case Column_MonPreerences:
            	colummnValue = timePreferences.getMonPreferences();
                break;
            case Column_TuePreferences:
            	colummnValue = timePreferences.getTuePreferences();
                break;
            case Column_WedPreferences:
            	colummnValue = timePreferences.getWedPreferences();
                break;
            case Column_ThuPreferences:
            	colummnValue = timePreferences.getThuPreferences();
                break;
            case Column_FriPreferences:
            	colummnValue = timePreferences.getFriPreferences();
                break;
            default:
            	break;
        }
        
        return colummnValue;
    }
    
    @Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
    	TimePreferences timePreferences = data.get(rowIndex);
        switch (columnIndex) {
        case Column_MonPreerences:
        	timePreferences.setMonPreferences((String)value);
            break;
        case Column_Name:
        	timePreferences.setName((String)value);
            break;
        case Column_TuePreferences:
        	timePreferences.setTuePreferences((String)value);
            break;
        case Column_WedPreferences:
        	timePreferences.setWedPreferences((String)value);
            break;
        case Column_ThuPreferences:
        	timePreferences.setThuPreferences((String)value);
            break;
        case Column_FriPreferences:
        	timePreferences.setFriPreferences((String)value);
            break;
        default:
        	break;
        }
    	this.data.set(rowIndex, timePreferences);
        fireTableCellUpdated(rowIndex, columnIndex);
    }
      
    public TimePreferences elementAt(int row){
    	return this.data.get(row);
    }
}
