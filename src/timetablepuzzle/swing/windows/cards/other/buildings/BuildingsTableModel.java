package timetablepuzzle.swing.windows.cards.other.buildings;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import timetablepuzzle.entities.other.Building;

class BuildingsTableModel extends AbstractTableModel {
    /**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int Column_Id = 0;
	private static final int Column_Name = 1;
	private static final int Column_Abbreviation = 2;
	private static final int Column_Address = 3;
	private static final int Column_Latitude = 4;
	private static final int Column_Longitude = 5;

	private String[] columnNames = {"Id","Name","Abbreviation","Address","Latitude", "Longitude"};
    private List<Building> data;
    
    public BuildingsTableModel(){}
    
    public void setData(List<Building> data)
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
    	Building building = data.get(rowIndex);
    	Object colummnValue = null;
        switch (columnIndex) {
            case Column_Id:
            	colummnValue = building.getId();
                break;
            case Column_Name:
            	colummnValue = building.getName();
                break;
            case Column_Abbreviation:
            	colummnValue = building.getAbbreviation();
                break;
            case Column_Address:
            	colummnValue = building.getLocation().getAddress();
                break;
            case Column_Latitude:
            	colummnValue = building.getLocation().getLatitude();
                break;
            case Column_Longitude:
            	colummnValue = building.getLocation().getLongitude();
                break;
            default:
            	break;
        }
        
        return colummnValue;
    }
    
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
    	Building building = data.get(rowIndex);
    	switch (columnIndex) {
        case Column_Name:
        	building.setName((String)value);
            break;
        case Column_Abbreviation:
        	building.setAbbreviation((String)value);
            break;
        case Column_Address:
        	building.getLocation().setAddress((String)value);
            break;
        case Column_Latitude:
        	building.getLocation().setLatitude((Integer)value);
            break;
        case Column_Longitude:
        	building.getLocation().setLongitude((Integer)value);
            break;
        default:
        	break;
    	}
    	this.data.set(rowIndex, building);
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    public Building elementAt(int row){
    	return this.data.get(row);
    }
}