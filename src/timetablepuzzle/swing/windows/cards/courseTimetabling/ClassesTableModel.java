package timetablepuzzle.swing.windows.cards.courseTimetabling;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import timetablepuzzle.entities.inputdata.Instructor;
import timetablepuzzle.entities.inputdata.Offering;
import timetablepuzzle.entities.inputdata.Room;
import timetablepuzzle.entities.inputdata.StudentGroup;
import timetablepuzzle.entities.Class;
import timetablepuzzle.entities.administration.Curricula.Term;
import timetablepuzzle.entities.administration.YearOfStudy.CollegeYear;

class ClassesTableModel extends AbstractTableModel {
	/**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;

	private static final int Column_Id = 0;
	private static final int Column_Course_Title = 1;
	private static final int Column_Course_Abbreviation = 2;
	private static final int Column_Department_Name = 3;
	private static final int Column_College_Year = 4;
	private static final int Column_Subject_Area_Name = 5;
	private static final int Column_Term = 6;
	private static final int Column_Offering = 7;
	private static final int Column_Assigned_Room = 8;
	private static final int Column_Assigned_Instructor = 9;
	private static final int Column_Assigned_Parent_Student_Group = 10;

	private String[] columnNames = { "Id", "Course Title", "Course Abbreviation", "Department Name",
			"College Year", "Subject Area Name", "Term", "Offering", "Assigned Room", "Assigned Instructor",
			"Assigned Student Group" };
	private List<Class> data;

	public ClassesTableModel() {
	}

	public void setData(List<Class> data) {
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
		if (data != null) {
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
		Class aClass = data.get(rowIndex);
		Object columnValue = null;
		switch (columnIndex) {
		case Column_Id:
			columnValue = aClass.getId();
			break;
		case Column_Course_Title:
			columnValue = aClass.getCourseTitle();
			break;
		case Column_Course_Abbreviation:
			columnValue = aClass.getCourseAbbreviation();
			break;
		case Column_Department_Name:
			columnValue = aClass.getDepartmentName();
			break;
		case Column_College_Year:
			columnValue = aClass.getCollegeYear().toString();
			break;
		case Column_Subject_Area_Name:
			columnValue = aClass.getSubjectAreaName();
			break;
		case Column_Term:
			columnValue = aClass.getTerm().toString();
			break;
		case Column_Offering:
			Offering offering = aClass.getOffering();
			if (offering != null) {
				columnValue = offering.toString();
			} else {
				columnValue = "";
			}
			break;
		case Column_Assigned_Room:
			Room assignedRoom = aClass.getAssignedRoom();
			if (assignedRoom != null) {
				columnValue = assignedRoom.toString();
			} else {
				columnValue = "";
			}
			break;
		case Column_Assigned_Instructor:
			Instructor assignedInstructor = aClass.getAssignedInstructor();
			if (assignedInstructor != null) {
				columnValue = assignedInstructor.toString();
			} else {
				columnValue = "";
			}
			break;
		case Column_Assigned_Parent_Student_Group:
			StudentGroup assignedParentStudentGroup = aClass.getAssignedParentStudentGroup();
			if (assignedParentStudentGroup != null) {
				columnValue = assignedParentStudentGroup.toString();
			} else {
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
		Class aClass = data.get(rowIndex);
		switch (columnIndex) {
		case Column_Course_Title:
			aClass.setCourseTitle((String) value);
			break;
		case Column_Course_Abbreviation:
			aClass.setCourseAbbreviation((String) value);
			break;
		case Column_Department_Name:
			aClass.setDepartmentName((String) value);
			break;
		case Column_College_Year:
			aClass.setCollegeYear((CollegeYear) value);
			break;
		case Column_Subject_Area_Name:
			aClass.setSubjectAreaName((String) value);
			break;
		case Column_Term:
			aClass.setTerm((Term) value);
			break;
		case Column_Offering:
			aClass.setOffering((Offering) value);
			break;
		case Column_Assigned_Room:
			aClass.setAssignedRoom((Room) value);
			break;
		case Column_Assigned_Instructor:
			aClass.setAssignedInstructor((Instructor) value);
			break;
		case Column_Assigned_Parent_Student_Group:
			aClass.setAssignedParentStudentGroup((StudentGroup) value);
			break;
		default:
			break;
		}
		this.data.set(rowIndex, aClass);
		fireTableCellUpdated(rowIndex, columnIndex);
	}

	public Class elementAt(int row) {
		return this.data.get(row);
	}
}
