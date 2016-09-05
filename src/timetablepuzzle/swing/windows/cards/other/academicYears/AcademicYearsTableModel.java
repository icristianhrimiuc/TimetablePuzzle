package timetablepuzzle.swing.windows.cards.other.academicYears;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import timetablepuzzle.entities.Solution;
import timetablepuzzle.entities.other.AcademicSession;
import timetablepuzzle.entities.other.AcademicYear;

class AcademicYearsTableModel extends AbstractTableModel {
    /**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int Column_Id = 0;
	private static final int Column_Year_Period = 1;
	private static final int Column_Academic_Sessions = 2;

	private String[] columnNames = {"Id","Year Period","Academic Sessions"};
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
    	Object colummnValue = null;
        switch (columnIndex) {
            case Column_Id:
            	colummnValue = academicYear.getId();
                break;
            case Column_Name:
            	colummnValue = academicYear.getName();
                break;
            case Column_Session_Start_Date:
            	colummnValue = format.format(academicYear.getSessionStartDate().getTime());
                break;
            case Column_Classes_End_Date:
            	colummnValue = format.format(academicYear.getClassesEndDate().getTime());
                break;
            case Column_Exams_Start_Date:
            	colummnValue = format.format(academicYear.getExamsStartDate().getTime());
                break;
            case Column_Session_End_Date:
            	colummnValue = format.format(academicYear.getSessionEndDate().getTime());
                break;
            case Column_Accepted_Solution:
            	colummnValue = academicYear.getAcceptedSolution().toString();
                break;
            default:
            	break;
        }
        
        return colummnValue;
    }
    
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
    	AcademicYear academicYear = data.get(rowIndex);
    	switch (columnIndex) {
        case Column_Name:
        	academicYear.setName((String)value);
            break;
        case Column_Session_Start_Date:
        	academicYear.setSessionStartDate((Calendar)value);
            break;
        case Column_Classes_End_Date:
        	academicYear.setClassesEndDate((Calendar)value);
            break;
        case Column_Exams_Start_Date:
        	academicYear.setExamsStartDate((Calendar)value);
            break;
        case Column_Session_End_Date:
        	academicYear.setSessionEndDate((Calendar)value);
            break;
        case Column_Accepted_Solution:
        	academicYear.setAcceptedSolution((Solution)value);
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
