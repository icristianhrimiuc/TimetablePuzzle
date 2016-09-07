package timetablepuzzle.swing.windows.cards.administration.academicYears;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import timetablepuzzle.entities.administration.AcademicSession;
import timetablepuzzle.entities.administration.AcademicYear;

class AcademicYearsTableModel extends AbstractTableModel {
    /**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int Column_Id = 0;
	private static final int Column_Name = 1;
	private static final int Column_First_Academic_Session = 2;
	private static final int Column_Second_Academic_Session = 3;
	private static final int Column_Third_Academic_Session = 4;

	private String[] columnNames = {"Id","Name","First Academic Session","Second Academic Session","Third Academic Session"};
    private List<AcademicYear> data;
    
    public AcademicYearsTableModel(){}
    
    public void setData(List<AcademicYear> data)
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
    	AcademicYear academicYear = data.get(rowIndex);
    	Object columnValue = null;
        switch (columnIndex) {
            case Column_Id:
            	columnValue = academicYear.getId();
                break;
            case Column_Name:
            	columnValue = academicYear.getName();
                break;
            case Column_First_Academic_Session:
            	columnValue = academicYear.getFirstAcademicSession().toString();
                break;
            case Column_Second_Academic_Session:
            	columnValue = academicYear.getSecondAcademicSession().toString();
                break;
            case Column_Third_Academic_Session:
            	AcademicSession third = academicYear.getThirdAcademicSession();
            	if(third != null){
            		columnValue = third.toString();
            	}else{
            		columnValue = "";
            	}
            	break;
            default:
            	break;
        }
        
        return columnValue;
    }
    
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
    	AcademicYear academicYear = data.get(rowIndex);
    	switch (columnIndex) {
        case Column_Name:
        	academicYear.setName((String)value);
            break;
        case Column_First_Academic_Session:
        	academicYear.setFirstAcademicSession((AcademicSession)value);
            break;
        case Column_Second_Academic_Session:
        	academicYear.setSecondAcademicSession((AcademicSession)value);
            break;
        case Column_Third_Academic_Session:
        	academicYear.setThirdAcademicSession((AcademicSession)value);
            break;
        default:
        	break;
    	}
    	this.data.set(rowIndex, academicYear);
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    public AcademicYear elementAt(int row){
    	return this.data.get(row);
    }
}
