package timetablepuzzle.swing.windows.cards.courseTimetabling.timetable;

import java.util.Comparator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import timetablepuzzle.entities.inputdata.StudentGroup;
import timetablepuzzle.usecases.solution.TimeslotPattern;
import timetablepuzzle.entities.Solution;
import timetablepuzzle.entities.Class;

public class TimetableTableModel extends AbstractTableModel {
	/**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;

	private String[] columnNames;
	private String[] rowNames;
	private Solution data;
	private List<StudentGroup> sortedStudentGroups;
	private int dayIndex;

	public TimetableTableModel(Solution data, StudentGroup studentGroup, int dayIndex) {
		this.data = data;
		this.sortedStudentGroups = studentGroup.getLeafGroups();
		this.sortedStudentGroups.sort(Comparator.comparing(StudentGroup::toString));
		this.dayIndex = dayIndex;

		// Generate the column names
		int nrOfColumns = this.sortedStudentGroups.size() + 1;
		this.columnNames = new String[nrOfColumns];
		this.columnNames[0] = "";

		for (int i = 1; i < nrOfColumns; i++) {
			this.columnNames[i] = this.sortedStudentGroups.get(i - 1).getCode();
		}

		int nrOfRows = TimeslotPattern.NrOfTimeSlotsPerDay + 1;
		this.rowNames = new String[nrOfRows];
		this.rowNames[0] = "";
		for (int i = 1; i < nrOfRows; i++) {
			this.rowNames[i] = String.format("%02d-%02d", i + 7, i + 8);
		}
	}

	public void setData(Solution data) {
		this.data = data;
		this.fireTableDataChanged();
	}

	public Solution getData() {
		return this.data;
	}

	@Override
	public int getColumnCount() {
		return this.columnNames.length;
	}

	@Override
	public int getRowCount() {
		return this.rowNames.length;
	}

	@Override
	public java.lang.Class<?> getColumnClass(int columnIndex) {
		return getValueAt(1, columnIndex).getClass();
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object columnValue = null;

		if ((rowIndex >= 0) && (rowIndex < getRowCount())) {
			if (rowIndex == 0) {
				if ((columnIndex > 0) && (columnIndex < getColumnCount())) {
					columnValue = this.columnNames[columnIndex];
				} else {
					columnValue = "";
				}
			} else {
				if ((columnIndex > 0) && (columnIndex < getColumnCount())) {
					// here is where i should write the class data
					int dayAndTimeSlot = this.dayIndex * TimeslotPattern.NrOfTimeSlotsPerDay + rowIndex - 1;
					int studentGroupId = this.sortedStudentGroups.get(columnIndex - 1).getId();
					int classId = this.data.GetStudentGroupAssignment(studentGroupId, dayAndTimeSlot);
					Class aClass = this.data.GetClassById(classId);
					if (aClass == null) {
						columnValue = "";
					} else {
						columnValue = aClass.getDisplayName();
					}
				} else {
					columnValue = this.rowNames[rowIndex];
				}
			}
		}

		return columnValue;
	}
}
