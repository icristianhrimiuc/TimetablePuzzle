package timetablepuzzle.swing.windows.cards.administration.instructorMeetings;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import timetablepuzzle.entities.administration.InstructorMeetings;
import timetablepuzzle.entities.inputdata.Instructor;
import timetablepuzzle.entities.inputdata.Room;

class InstructorMeetingsTableModel extends AbstractTableModel {
    /**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int Column_Id = 0;
	private static final int Column_Instructor = 1;
	private static final int Column_Room = 2;
	private static final int Column_NrOfMeetings = 3;

	private String[] columnNames = {"Id","Instructor","Room","NrOfMeetings"};
    private List<InstructorMeetings> data;
    
    public InstructorMeetingsTableModel(){}
    
    public void setData(List<InstructorMeetings> data)
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
    	InstructorMeetings instructorMeetings = data.get(rowIndex);
    	Object colummnValue = null;
        switch (columnIndex) {
            case Column_Id:
            	colummnValue = instructorMeetings.getId();
                break;
            case Column_Instructor:
            	colummnValue = instructorMeetings.getInstructor().toString();
                break;
            case Column_Room:
            	colummnValue = instructorMeetings.getRoom().toString();
                break;
            case Column_NrOfMeetings:
            	colummnValue = instructorMeetings.getNrOfMeetings();
                break;
            default:
            	break;
        }
        
        return colummnValue;
    }
    
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
    	InstructorMeetings instructorMeetings = data.get(rowIndex);
    	switch (columnIndex) {
        case Column_Instructor:
        	instructorMeetings.setInstructor((Instructor)value);
            break;
        case Column_Room:
        	instructorMeetings.setRoom((Room)value);
            break;
        case Column_NrOfMeetings:
        	instructorMeetings.setNrOfMeetings((int)value);
            break;
        default:
        	break;
    	}
    	this.data.set(rowIndex, instructorMeetings);
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    public InstructorMeetings elementAt(int row){
    	return this.data.get(row);
    }
}