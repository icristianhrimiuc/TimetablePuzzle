package timetablepuzzle.swing.windows.cards.inputdata.offerings;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import timetablepuzzle.entities.inputdata.InstructorMeetings;
import timetablepuzzle.entities.inputdata.Offering;
import timetablepuzzle.entities.inputdata.Offering.DatePattern;
import timetablepuzzle.entities.inputdata.Offering.OfferingType;

class OfferingsTableModel extends AbstractTableModel {
    /**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int Column_Id = 0;
	private static final int Column_Name = 1;
	private static final int Column_Nr_Of_Timeslots = 2;
	private static final int Column_Type = 3;
	private static final int Column_Date_Pattern = 4;
	private static final int Column_Instructor_Meetings = 5;

	private String[] columnNames = {"Id","Name","Nr. of Time Slots","Type","Date Pattern","Instructor Meetings"};
    private List<Offering> data;
    
    public OfferingsTableModel(){}
    
    public void setData(List<Offering> data)
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
    	Offering offering = data.get(rowIndex);
    	Object columnValue = null;
        switch (columnIndex) {
            case Column_Id:
            	columnValue = offering.getId();
                break;
            case Column_Name:
            	columnValue = offering.getName();
                break;
            case Column_Nr_Of_Timeslots:
            	columnValue = Integer.toString(offering.getNrOfTimeSlots());
                break;
            case Column_Type:
            	columnValue = offering.getType().toString();
                break;
            case Column_Date_Pattern:
            	columnValue = offering.getDatePattern().toString();
                break;
            case Column_Instructor_Meetings:
            	columnValue = GetAsList(offering.getNrOfMeetingsPerInstructor()).toString();
                break;
            default:
            	break;
        }
        
        return columnValue;
    }
    
    private List<InstructorMeetings> GetAsList(List<InstructorMeetings> nrOfMeetingsPerInstructor){
    	List<InstructorMeetings> list = new ArrayList<InstructorMeetings>();
    		list.addAll(nrOfMeetingsPerInstructor);
    	
    	return list;
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
    	Offering offering = data.get(rowIndex);
    	switch (columnIndex) {
	        case Column_Name:
	        	offering.setName((String)value);
	            break;
	        case Column_Nr_Of_Timeslots:
	        	offering.setNrOfTimeSlots((int)value);
	            break;
	        case Column_Type:
	        	offering.setType((OfferingType)value);
	            break;
	        case Column_Date_Pattern:
	        	offering.setDatePattern((DatePattern)value);
	            break;
	        case Column_Instructor_Meetings:
	        	offering.setNrOfMeetingsPerInstructor((List<InstructorMeetings>)value);
	            break;
	        default:
	        	break;
    	}
    	this.data.set(rowIndex, offering);
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    public Offering elementAt(int row){
    	return this.data.get(row);
    }
}
