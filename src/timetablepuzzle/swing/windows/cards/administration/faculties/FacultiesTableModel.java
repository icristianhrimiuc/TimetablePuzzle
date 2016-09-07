package timetablepuzzle.swing.windows.cards.administration.faculties;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import timetablepuzzle.entities.administration.AcademicYear;
import timetablepuzzle.entities.administration.Department;
import timetablepuzzle.entities.administration.Faculty;

class FacultiesTableModel extends AbstractTableModel {
    /**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int Column_Id = 0;
	private static final int Column_Name = 1;
	private static final int Column_Academic_Years = 2;
	private static final int Column_Departments = 3;

	private String[] columnNames = {"Id","Name","Academic Years","Departments"};
    private List<Faculty> data;
    
    public FacultiesTableModel(){}
    
    public void setData(List<Faculty> data)
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
    	Faculty faculty = data.get(rowIndex);
    	Object columnValue = null;
        switch (columnIndex) {
            case Column_Id:
            	columnValue = faculty.getId();
                break;
            case Column_Name:
            	columnValue = faculty.getName();
                break;
            case Column_Academic_Years:
            	columnValue = GetAsListOfAcademicYears(faculty.getAcademicYears()).toString();
                break;
            case Column_Departments:
            	columnValue = GetAsListOfDepartments(faculty.getDepartments()).toString();
                break;
            default:
            	break;
        }
        
        return columnValue;
    }
    
    private List<Department> GetAsListOfDepartments(List<Department> indirectList){
    	List<Department> list = new ArrayList<Department>();
    		list.addAll(indirectList);
    	
    	return list;
    }
    
    private List<AcademicYear> GetAsListOfAcademicYears(List<AcademicYear> indirectList){
    	List<AcademicYear> list = new ArrayList<AcademicYear>();
    		list.addAll(indirectList);
    	
    	return list;
    }
    
    
    @SuppressWarnings("unchecked")
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
    	Faculty faculty = data.get(rowIndex);
    	switch (columnIndex) {
	        case Column_Name:
	        	faculty.setName((String)value);
	            break;
	        case Column_Academic_Years:
	        	faculty.setAcademicYears((List<AcademicYear>)value);
	            break;
	        case Column_Departments:
	        	faculty.setDepartments((List<Department>)value);
	            break;
	        default:
	        	break;
    	}
    	this.data.set(rowIndex, faculty);
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    public Faculty elementAt(int row){
    	return this.data.get(row);
    }
}
