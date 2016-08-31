package timetablepuzzle.swing.windows.cards.other;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import timetablepuzzle.eclipselink.entities.administration.RoomFeature;
import timetablepuzzle.eclipselink.entities.administration.RoomType;

class RoomTypeTableModel extends AbstractTableModel {
    /**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int Column_Id = 0;
	private static final int Column_Name = 1;
	private static final int Column_Min_Capacity = 2;
	private static final int Column_Max_Capacity = 3;
	private static final int Column_Room_Features = 4;

	private String[] columnNames = {"Id","Name","Min Capacity","Max Capacity","Room Features"};
    private List<RoomType> data;
    
    public RoomTypeTableModel(){}
    
    public void setData(List<RoomType> data)
    {
    	this.data = data;
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
    	int nrOfRows = 0;
    	if(data != null)
    	{
    		nrOfRows = data.size();
    	}
    	
        return nrOfRows;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
    	RoomType roomType = data.get(rowIndex);
    	Object colummnValue = null;
        switch (columnIndex) {
            case Column_Id:
            	colummnValue = roomType.getId();
                break;
            case Column_Name:
            	colummnValue = roomType.getName();
                break;
            case Column_Min_Capacity:
            	colummnValue = roomType.getMinCapacity();
                break;
            case Column_Max_Capacity:
            	colummnValue = roomType.getMaxCapacity();
                break;
            case Column_Room_Features:
            	colummnValue = roomType.GetFeatures();
                break;
            default:
            	break;
        }
        
        return colummnValue;
    }

	@SuppressWarnings("unchecked")
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
    	RoomType roomType = data.get(rowIndex);
    	switch (columnIndex) {
	        case Column_Name:
	        	roomType.setName((String)value);
	            break;
	        case Column_Min_Capacity:
	        	roomType.setMinCapacity(((int)value));
	            break;
	        case Column_Max_Capacity:
	        	roomType.setMaxCapacity((int)value);
	            break;
	        case Column_Room_Features:
	        	roomType.setRoomFeatures((List<RoomFeature>)value);
	            break;
	        default:
	        	break;
    	}
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    public RoomType elementAt(int row){
    	return this.data.get(row);
    }
}
