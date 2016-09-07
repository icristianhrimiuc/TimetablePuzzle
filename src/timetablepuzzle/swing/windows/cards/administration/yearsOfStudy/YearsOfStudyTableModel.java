package timetablepuzzle.swing.windows.cards.administration.yearsOfStudy;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import timetablepuzzle.entities.administration.SubjectArea;
import timetablepuzzle.entities.administration.YearOfStudy;
import timetablepuzzle.entities.administration.YearOfStudy.CollegeYear;

class YearsOfStudyTableModel extends AbstractTableModel {
    /**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int Column_Id = 0;
	private static final int Column_Name = 1;
	private static final int Column_College_Year = 2;
	private static final int Column_Subject_Areas = 3;

	private String[] columnNames = {"Id","Name","College Year","Subject Areas"};
    private List<YearOfStudy> data;
    
    public YearsOfStudyTableModel(){}
    
    public void setData(List<YearOfStudy> data)
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
    	YearOfStudy yearOfStudy = data.get(rowIndex);
    	Object columnValue = null;
        switch (columnIndex) {
            case Column_Id:
            	columnValue = yearOfStudy.getId();
                break;
            case Column_Name:
            	columnValue = yearOfStudy.getName();
                break;
            case Column_College_Year:
            	columnValue = yearOfStudy.getCollegeYear().toString();
                break;
            case Column_Subject_Areas:
            	columnValue = GetAsList(yearOfStudy.getSubjectAreas()).toString();
                break;
            default:
            	break;
        }
        
        return columnValue;
    }
    
    private List<SubjectArea> GetAsList(List<SubjectArea> indirectList){
    	List<SubjectArea> list = new ArrayList<SubjectArea>();
    		list.addAll(indirectList);
    	
    	return list;
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
    	YearOfStudy yearOfStudy = data.get(rowIndex);
    	switch (columnIndex) {
	        case Column_Name:
	        	yearOfStudy.setName((String)value);
	            break;
	        case Column_College_Year:
	        	yearOfStudy.setCollegeYear((CollegeYear)value);
	            break;
	        case Column_Subject_Areas:
	        	yearOfStudy.setSubjectAreas((List<SubjectArea>)value);
	            break;
	        default:
	        	break;
    	}
    	this.data.set(rowIndex, yearOfStudy);
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    public YearOfStudy elementAt(int row){
    	return this.data.get(row);
    }
}
