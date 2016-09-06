package timetablepuzzle.swing.windows.cards.inputdata.studentGroups;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import timetablepuzzle.entities.inputdata.StudentGroup;

class StudentGroupsTableModel extends AbstractTableModel {
    /**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int Column_Id = 0;
	private static final int Column_Name = 1;
	private static final int Column_Code = 2;
	private static final int Column_NrOfStudents = 3;
	private static final int Column_Composing_Groups = 4;

	private String[] columnNames = {"Id","Name","Code","Nr. of students","Composing Groups"};
    private List<StudentGroup> data;
    
    public StudentGroupsTableModel(){}
    
    public void setData(List<StudentGroup> data)
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
    	StudentGroup studentGroup = data.get(rowIndex);
    	Object colummnValue = null;
        switch (columnIndex) {
            case Column_Id:
            	colummnValue = studentGroup.getId();
                break;
            case Column_Name:
            	colummnValue = studentGroup.getName();
                break;
            case Column_Code:
            	colummnValue = studentGroup.getCode();
                break;
            case Column_NrOfStudents:
            	colummnValue = Integer.toString(studentGroup.getNrOfStudents());
                break;
            case Column_Composing_Groups:
            	colummnValue = studentGroup.getAllComposingGroupsHierachicallyAsString();
                break;
            default:
            	break;
        }
        
        return colummnValue;
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
    	StudentGroup studentGroup = data.get(rowIndex);
    	switch (columnIndex) {
        case Column_Name:
        	studentGroup.setName((String)value);
            break;
        case Column_Code:
        	studentGroup.setCode((String)value);
            break;
        case Column_NrOfStudents:
        	studentGroup.setNrOfStudents((int)value);
            break;
        case Column_Composing_Groups:
        	studentGroup.setComposingGroups((List<StudentGroup>)value);
            break;
        default:
        	break;
    	}
    	this.data.set(rowIndex, studentGroup);
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    public StudentGroup elementAt(int row){
    	return this.data.get(row);
    }
}
