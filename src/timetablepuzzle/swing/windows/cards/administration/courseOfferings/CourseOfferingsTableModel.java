package timetablepuzzle.swing.windows.cards.administration.courseOfferings;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import timetablepuzzle.entities.administration.CourseOffering;
import timetablepuzzle.entities.inputdata.Offering;

class CourseOfferingsTableModel extends AbstractTableModel {
    /**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int Column_Id = 0;
	private static final int Column_Title = 1;
	private static final int Column_Abbreviation = 2;
	private static final int Column_Lecture = 3;
	private static final int Column_IndividualMeetings = 4;

	private String[] columnNames = {"Id","Title","Abbreviation","Lecture","Individual Meetings"};
    private List<CourseOffering> data;
    
    public CourseOfferingsTableModel(){}
    
    public void setData(List<CourseOffering> data)
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
    	CourseOffering courseOffering = data.get(rowIndex);
    	Object columnValue = null;
        switch (columnIndex) {
            case Column_Id:
            	columnValue = courseOffering.getId();
                break;
            case Column_Title:
            	columnValue = courseOffering.getTitle();
                break;
            case Column_Abbreviation:
            	columnValue = courseOffering.getAbbreviation();
                break;
            case Column_Lecture:
            	Offering lecture = courseOffering.getLecture();
            	if(lecture != null){
            		columnValue = lecture.toString();
            	}else{
            		columnValue = "";
            	}
                break;
            case Column_IndividualMeetings:
            	Offering individualMeeting = courseOffering.getIndividualMeetings();
            	if(individualMeeting != null){
            		columnValue = individualMeeting.toString();
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
    	CourseOffering courseOffering = data.get(rowIndex);
    	switch (columnIndex) {
	        case Column_Title:
	        	courseOffering.setTitle((String)value);
	            break;
	        case Column_Abbreviation:
	        	courseOffering.setAbbreviation((String)value);
	            break;
	        case Column_Lecture:
	        	courseOffering.setLecture((Offering)value);
	            break;
	        case Column_IndividualMeetings:
	        	courseOffering.setIndividualMeetings((Offering)value);
	            break;
	        default:
	        	break;
    	}
    	this.data.set(rowIndex, courseOffering);
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    public CourseOffering elementAt(int row){
    	return this.data.get(row);
    }
}
