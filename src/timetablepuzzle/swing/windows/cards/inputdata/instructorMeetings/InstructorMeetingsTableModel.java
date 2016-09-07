package timetablepuzzle.swing.windows.cards.inputdata.instructorMeetings;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import timetablepuzzle.entities.inputdata.Instructor;
import timetablepuzzle.entities.inputdata.InstructorMeetings;
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
    	Object columnValue = null;
        switch (columnIndex) {
            case Column_Id:
            	columnValue = instructorMeetings.getId();
                break;
            case Column_Instructor:
            	Instructor instructor = instructorMeetings.getInstructor();
            	if(instructor != null){
            		columnValue = instructor.toString();
            	}else{
            		columnValue = "";
            	}
                break;
            case Column_Room:
            	Room room = instructorMeetings.getRoom();
            	if(room != null){
            		columnValue = room.toString();
            	}else{
            		columnValue = "";
            	}
                break;
            case Column_NrOfMeetings:
            	columnValue = instructorMeetings.getNrOfMeetings();
                break;
            default:
            	break;
        }
        
        return columnValue;
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