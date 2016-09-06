package timetablepuzzle.swing.windows.cards.inputdata.rooms;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import timetablepuzzle.entities.inputdata.Room;
import timetablepuzzle.entities.other.Building;
import timetablepuzzle.entities.other.RoomType;
import timetablepuzzle.entities.other.TimePreferences;

class RoomsTableModel extends AbstractTableModel {
    /**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int Column_Id = 0;
	private static final int Column_Name = 1;
	private static final int Column_Code = 2;
	private static final int Column_Capacity = 3;
	private static final int Column_Type = 4;
	private static final int Column_Building = 5;
	private static final int Column_TimePreferences = 6;

	private String[] columnNames = {"Id","Name","Code","Capacity","Type","Building","Time Preferences"};
    private List<Room> data;
    
    public RoomsTableModel(){}
    
    public void setData(List<Room> data)
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
    	Room room = data.get(rowIndex);
    	Object colummnValue = null;
        switch (columnIndex) {
            case Column_Id:
            	colummnValue = room.getId();
                break;
            case Column_Name:
            	colummnValue = room.getName();
                break;
            case Column_Code:
            	colummnValue = room.getCode();
                break;
            case Column_Capacity:
            	colummnValue = Integer.toString(room.getCapacity());
                break;
            case Column_Type:
            	colummnValue = room.getType().toString();
                break;
            case Column_Building:
            	colummnValue = room.getBuilding().toString();
                break;
            case Column_TimePreferences:
            	colummnValue = room.getTimePreferences().toString();
                break;
            default:
            	break;
        }
        
        return colummnValue;
    }
    
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
    	Room room = data.get(rowIndex);
    	switch (columnIndex) {
        case Column_Name:
        	room.setName((String)value);
            break;
        case Column_Code:
        	room.setCode((String)value);
            break;
        case Column_Capacity:
        	room.setCapacity((int)value);
            break;
        case Column_Type:
        	room.setType((RoomType)value);
            break;
        case Column_Building:
        	room.setBuilding((Building)value);
            break;
        case Column_TimePreferences:
        	room.setTimePreferences((TimePreferences)value);
            break;
        default:
        	break;
    	}
    	this.data.set(rowIndex, room);
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    public Room elementAt(int row){
    	return this.data.get(row);
    }
}
