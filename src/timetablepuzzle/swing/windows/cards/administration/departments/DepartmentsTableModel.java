package timetablepuzzle.swing.windows.cards.administration.departments;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import timetablepuzzle.entities.administration.YearOfStudy;
import timetablepuzzle.entities.administration.Department;

class DepartmentsTableModel extends AbstractTableModel {
    /**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int Column_Id = 0;
	private static final int Column_Name = 1;
	private static final int Column_Years_Of_Study = 2;

	private String[] columnNames = {"Id","Name","Years Of Study"};
    private List<Department> data;
    
    public DepartmentsTableModel(){}
    
    public void setData(List<Department> data)
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
    	Department department = data.get(rowIndex);
    	Object columnValue = null;
        switch (columnIndex) {
            case Column_Id:
            	columnValue = department.getId();
                break;
            case Column_Name:
            	columnValue = department.getName();
                break;
            case Column_Years_Of_Study:
            	columnValue = GetAsList(department.getYearsOfStudy()).toString();
                break;
            default:
            	break;
        }
        
        return columnValue;
    }
    
    private List<YearOfStudy> GetAsList(List<YearOfStudy> nrOfMeetingsPerInstructor){
    	List<YearOfStudy> list = new ArrayList<YearOfStudy>();
    		list.addAll(nrOfMeetingsPerInstructor);
    	
    	return list;
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
    	Department department = data.get(rowIndex);
    	switch (columnIndex) {
	        case Column_Name:
	        	department.setName((String)value);
	            break;
	        case Column_Years_Of_Study:
	        	department.setYearsOfStudy((List<YearOfStudy>)value);
	            break;
	        default:
	        	break;
    	}
    	this.data.set(rowIndex, department);
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    public Department elementAt(int row){
    	return this.data.get(row);
    }
}
