package timetablepuzzle.swing.windows.cards.administration.academicSessions;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import timetablepuzzle.entities.Solution;
import timetablepuzzle.entities.administration.AcademicSession;

class AcademicSessionsTableModel extends AbstractTableModel {
    /**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int Column_Id = 0;
	private static final int Column_Name = 1;
	private static final int Column_Session_Start_Date= 2;
	private static final int Column_Classes_End_Date = 3;
	private static final int Column_Exams_Start_Date = 4;
	private static final int Column_Session_End_Date = 5;
	private static final int Column_Accepted_Solution = 6;

	private String[] columnNames = {"Id","Name","Session Start Date","Classes End Date","Exams Start Date", "Session End Date","Accepted Solution"};
    private List<AcademicSession> data;
    
    public AcademicSessionsTableModel(){}
    
    public void setData(List<AcademicSession> data)
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
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    	AcademicSession academicSession = data.get(rowIndex);
    	Object colummnValue = null;
        switch (columnIndex) {
            case Column_Id:
            	colummnValue = academicSession.getId();
                break;
            case Column_Name:
            	colummnValue = academicSession.getName();
                break;
            case Column_Session_Start_Date:
            	colummnValue = format.format(academicSession.getSessionStartDate().getTime());
                break;
            case Column_Classes_End_Date:
            	colummnValue = format.format(academicSession.getClassesEndDate().getTime());
                break;
            case Column_Exams_Start_Date:
            	colummnValue = format.format(academicSession.getExamsStartDate().getTime());
                break;
            case Column_Session_End_Date:
            	colummnValue = format.format(academicSession.getSessionEndDate().getTime());
                break;
            case Column_Accepted_Solution:
            	Solution acceptedSolution = academicSession.getAcceptedSolution();
            	if(acceptedSolution != null){
            		colummnValue = acceptedSolution.toString();
            	}else{
            		colummnValue = "";
            	}
                break;
            default:
            	break;
        }
        
        return colummnValue;
    }
    
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
    	AcademicSession academicSession = data.get(rowIndex);
    	switch (columnIndex) {
        case Column_Name:
        	academicSession.setName((String)value);
            break;
        case Column_Session_Start_Date:
        	academicSession.setSessionStartDate((Calendar)value);
            break;
        case Column_Classes_End_Date:
        	academicSession.setClassesEndDate((Calendar)value);
            break;
        case Column_Exams_Start_Date:
        	academicSession.setExamsStartDate((Calendar)value);
            break;
        case Column_Session_End_Date:
        	academicSession.setSessionEndDate((Calendar)value);
            break;
        case Column_Accepted_Solution:
        	academicSession.setAcceptedSolution((Solution)value);
            break;
        default:
        	break;
    	}
    	this.data.set(rowIndex, academicSession);
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    public AcademicSession elementAt(int row){
    	return this.data.get(row);
    }
}
