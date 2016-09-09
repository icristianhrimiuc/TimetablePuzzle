package timetablepuzzle.swing.windows.cards.courseTimetabling.solutions;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import timetablepuzzle.entities.Solution;
import timetablepuzzle.entities.Class;

class SolutionsTableModel extends AbstractTableModel {
    /**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int Column_Id = 0;
	private static final int Column_Name = 1;
	private static final int Column_Classes = 2;

	private String[] columnNames = {"Id","Name","Classes"};
    private List<Solution> data;
    
    public SolutionsTableModel(){}
    
    public void setData(List<Solution> data)
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
    public java.lang.Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0, columnIndex).getClass();
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
    	Solution solution = data.get(rowIndex);
    	Object columnValue = null;
        switch (columnIndex) {
            case Column_Id:
            	columnValue = solution.getId();
                break;
            case Column_Name:
            	columnValue = solution.getName();
                break;
            case Column_Classes:
            	List<Class> classes = new ArrayList<Class>();
            	classes.addAll(solution.GetAssignedClasses());
            	classes.addAll(solution.GetUnassignedClasses());
            	columnValue = classes.toString();
                break;
            default:
            	break;
        }
        
        return columnValue;
    }
    
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
    	Solution solution = data.get(rowIndex);
    	switch (columnIndex) {
	        case Column_Name:
	        	solution.setName((String)value);
	            break;
	        default:
	        	break;
    	}
    	this.data.set(rowIndex, solution);
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    public Solution elementAt(int row){
    	return this.data.get(row);
    }
}
