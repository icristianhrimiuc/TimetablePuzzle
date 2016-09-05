package timetablepuzzle.swing.windows.cards.other;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import timetablepuzzle.eclipselink.entities.administration.DatePattern;

class DatePatternsTableModel extends AbstractTableModel {
    /**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int Column_Id = 0;
	private static final int Column_Name = 1;
	private static final int Column_Dates = 2;

	private String[] columnNames = {"Id","Name","Dates"};
    private List<DatePattern> data;
    
    public DatePatternsTableModel(){}
    
    public void setData(List<DatePattern> data)
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
    	DatePattern datePattern = data.get(rowIndex);
    	Object colummnValue = null;
        switch (columnIndex) {
            case Column_Id:
            	colummnValue = datePattern.getId();
                break;
            case Column_Name:
            	colummnValue = datePattern.getName();
                break;
            case Column_Dates:
            	colummnValue = datePattern.getDates();
            default:
            	break;
        }
        
        return colummnValue;
    }
    
    @Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
    	DatePattern datePattern = data.get(rowIndex);
        switch (columnIndex) {
        case Column_Name:
        	datePattern.setName((String)value);
            break;
        case Column_Dates:
        	datePattern.setDates((String)value);
            break;
        default:
        	break;
        }
    	this.data.set(rowIndex, datePattern);
        fireTableCellUpdated(rowIndex, columnIndex);
    }
      
    public DatePattern elementAt(int row){
    	return this.data.get(row);
    }
}
