package timetablepuzzle.swing.windows.cards.administration.curriculas;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import timetablepuzzle.entities.administration.CourseOffering;
import timetablepuzzle.entities.administration.Curricula;
import timetablepuzzle.entities.administration.Curricula.Term;
import timetablepuzzle.entities.administration.YearOfStudy.CollegeYear;

class CurriculasTableModel extends AbstractTableModel {
    /**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int Column_Id = 0;
	private static final int Column_Name = 1;
	private static final int Column_Year = 2;
	private static final int Column_Term = 3;
	private static final int Column_Courses = 4;

	private String[] columnNames = {"Id","Name","Year","Term","Courses"};
    private List<Curricula> data;
    
    public CurriculasTableModel(){}
    
    public void setData(List<Curricula> data)
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
    	Curricula curricula = data.get(rowIndex);
    	Object columnValue = null;
        switch (columnIndex) {
            case Column_Id:
            	columnValue = curricula.getId();
                break;
            case Column_Name:
            	columnValue = curricula.getName();
                break;
            case Column_Year:
            	columnValue = curricula.getYear().toString();
                break;
            case Column_Term:
            	columnValue = curricula.getTerm().toString();
                break;
            case Column_Courses:
            	columnValue = GetAsList(curricula.getCourses()).toString();
                break;
            default:
            	break;
        }
        
        return columnValue;
    }
    
    private List<CourseOffering> GetAsList(List<CourseOffering> nrOfMeetingsPerInstructor){
    	List<CourseOffering> list = new ArrayList<CourseOffering>();
    		list.addAll(nrOfMeetingsPerInstructor);
    	
    	return list;
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
    	Curricula curricula = data.get(rowIndex);
    	switch (columnIndex) {
	        case Column_Name:
	        	curricula.setName((String)value);
	            break;
	        case Column_Year:
	        	curricula.setYear((CollegeYear)value);
	            break;
	        case Column_Term:
	        	curricula.setTerm((Term)value);
	            break;
	        case Column_Courses:
	        	curricula.setCourses((List<CourseOffering>)value);
	            break;
	        default:
	        	break;
    	}
    	this.data.set(rowIndex, curricula);
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    public Curricula elementAt(int row){
    	return this.data.get(row);
    }
}
