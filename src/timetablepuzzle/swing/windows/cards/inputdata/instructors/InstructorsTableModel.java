package timetablepuzzle.swing.windows.cards.inputdata.instructors;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import timetablepuzzle.entities.inputdata.Instructor;
import timetablepuzzle.entities.other.TimePreferences;

class InstructorsTableModel extends AbstractTableModel {
    /**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int Column_Id = 0;
	private static final int Column_First_Name = 1;
	private static final int Column_Last_Name = 2;
	private static final int Column_Academic_Title = 3;
	private static final int Column_TimePreferences = 4;

	private String[] columnNames = {"Id","First Name","Last Name","Academic Title","Time Preferences"};
    private List<Instructor> data;
    
    public InstructorsTableModel(){}
    
    public void setData(List<Instructor> data)
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
    	Instructor instructor = data.get(rowIndex);
    	Object colummnValue = null;
        switch (columnIndex) {
            case Column_Id:
            	colummnValue = instructor.getId();
                break;
            case Column_First_Name:
            	colummnValue = instructor.getFirstName();
                break;
            case Column_Last_Name:
            	colummnValue = instructor.getLastName();
                break;
            case Column_Academic_Title:
            	colummnValue = instructor.getAcademicTitle();
                break;
            case Column_TimePreferences:
            	colummnValue = instructor.getTimePreferences().toString();
                break;
            default:
            	break;
        }
        
        return colummnValue;
    }
    
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
    	Instructor instructor = data.get(rowIndex);
    	switch (columnIndex) {
        case Column_First_Name:
        	instructor.setFirstName((String)value);
            break;
        case Column_Last_Name:
        	instructor.setLastName((String)value);
            break;
        case Column_Academic_Title:
        	instructor.setAcademicTitle((String)value);
            break;
        case Column_TimePreferences:
        	instructor.setTimePreferences((TimePreferences)value);
            break;
        default:
        	break;
    	}
    	this.data.set(rowIndex, instructor);
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    public Instructor elementAt(int row){
    	return this.data.get(row);
    }
}