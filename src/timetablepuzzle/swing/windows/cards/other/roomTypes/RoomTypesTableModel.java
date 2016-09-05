package timetablepuzzle.swing.windows.cards.other.roomTypes;

import java.util.Comparator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import timetablepuzzle.entities.other.RoomFeature;
import timetablepuzzle.entities.other.RoomType;

class RoomTypesTableModel extends AbstractTableModel {
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
    
    public RoomTypesTableModel(){}
    
    public void setData(List<RoomType> data)
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
            	List<RoomFeature> roomFeatures = roomType.getRoomFeatures();
            	roomFeatures.sort(Comparator.comparing(RoomFeature::toString));
            	colummnValue = GetFeatures(roomFeatures);
                break;
            default:
            	break;
        }
        
        return colummnValue;
    }
     
	public String GetFeatures(List<RoomFeature> roomFeatures){
		String features= "";
		for(RoomFeature roomFeature : roomFeatures){
			features += roomFeature.getFeature() + ";";
		}
		
		return features;
	}
    
    @Override
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
    	this.data.set(rowIndex, roomType);
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    public RoomType elementAt(int row){
    	return this.data.get(row);
    }
}
