package timetablepuzzle.swing.windows.cards.administration.users;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import timetablepuzzle.entities.administration.User;
import timetablepuzzle.entities.administration.User.UserType;

class UsersTableModel extends AbstractTableModel {
    /**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int Column_Id = 0;
	private static final int Column_First_Name = 1;
	private static final int Column_Last_Name = 2;
	private static final int Column_Username = 3;
	private static final int Column_User_Type = 4;

	private String[] columnNames = {"Id","First Name","Last Name","Username","User Type"};
    private List<User> data;
    
    public UsersTableModel(){}
    
    public void setData(List<User> data)
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
    	User user = data.get(rowIndex);
    	Object colummnValue = null;
        switch (columnIndex) {
            case Column_Id:
            	colummnValue = user.getId();
                break;
            case Column_First_Name:
            	colummnValue = user.getFirstName();
                break;
            case Column_Last_Name:
            	colummnValue = user.getLastName();
                break;
            case Column_Username:
            	colummnValue = user.getUsername();
                break;
            case Column_User_Type:
            	colummnValue = user.getUserType().toString();
                break;
            default:
            	break;
        }
        
        return colummnValue;
    }
    
    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
    	User user = data.get(rowIndex);
    	switch (columnIndex) {
        case Column_First_Name:
        	user.setFirstName((String)value);
            break;
        case Column_Last_Name:
        	user.setLastName((String)value);
            break;
        case Column_Username:
        	user.setUsername((String)value);
            break;
        case Column_User_Type:
        	user.setUserType((UserType)value);
            break;
        default:
        	break;
    	}
    	this.data.set(rowIndex, user);
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    public User elementAt(int row){
    	return this.data.get(row);
    }
}