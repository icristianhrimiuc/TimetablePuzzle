package timetablepuzzle.swing.windows.cards.administration.subjectAreas;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import timetablepuzzle.entities.administration.Curricula;
import timetablepuzzle.entities.administration.SubjectArea;
import timetablepuzzle.entities.administration.Curricula.Term;

class SubjectAreasTableModel extends AbstractTableModel {
    /**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int Column_Id = 0;
	private static final int Column_Name = 1;
	private static final int Column_Abbreviation = 2;
	private static final int Column_First_Term_Curricula = 3;
	private static final int Column_Second_Term_Curricula = 4;
	private static final int Column_Third_Term_Curricula = 5;

	private String[] columnNames = {"Id","Name","Abbreviation","First Term Curricula","Second Term Curricula","Third Term Curricula"};
    private List<SubjectArea> data;
    
    public SubjectAreasTableModel(){}
    
    public void setData(List<SubjectArea> data)
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
    	SubjectArea subjectArea = data.get(rowIndex);
    	Object columnValue = null;
        switch (columnIndex) {
            case Column_Id:
            	columnValue = subjectArea.getId();
                break;
            case Column_Name:
            	columnValue = subjectArea.getName();
                break;
            case Column_Abbreviation:
            	columnValue = subjectArea.getAbbreviation();
                break;
            case Column_First_Term_Curricula:
            	columnValue = subjectArea.getCurriculaToStudyByTerm(Term.FIRST).toString();
                break;
            case Column_Second_Term_Curricula:
            	columnValue = subjectArea.getCurriculaToStudyByTerm(Term.SECOND).toString();
                break;
            case Column_Third_Term_Curricula:
            	columnValue = subjectArea.getCurriculaToStudyByTerm(Term.THIRD).toString();
                break;
            default:
            	break;
        }
        
        return columnValue;
    }
    
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex) {
    	SubjectArea subjectArea = data.get(rowIndex);
    	switch (columnIndex) {
	        case Column_Name:
	        	subjectArea.setName((String)value);
	            break;
	        case Column_Abbreviation:
	        	subjectArea.setAbbreviation((String)value);
	            break;
	        case Column_First_Term_Curricula:
	        	subjectArea.setCurriculaToStudyByTerm(Term.FIRST,(Curricula)value);
	            break;
	        case Column_Second_Term_Curricula:
	        	subjectArea.setCurriculaToStudyByTerm(Term.SECOND,(Curricula)value);
	            break;
	        case Column_Third_Term_Curricula:
	        	subjectArea.setCurriculaToStudyByTerm(Term.THIRD,(Curricula)value);
	            break;
	        default:
	        	break;
    	}
    	this.data.set(rowIndex, subjectArea);
        fireTableCellUpdated(rowIndex, columnIndex);
    }
    
    public SubjectArea elementAt(int row){
    	return this.data.get(row);
    }
}

