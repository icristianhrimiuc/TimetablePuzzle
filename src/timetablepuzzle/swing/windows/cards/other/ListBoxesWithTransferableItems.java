/*
 * GUI Component that has two list boxes with items that can be transferred between them.
 *
 * Author: Santosh Shanbhag
 */
package timetablepuzzle.swing.windows.cards.other;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;

public class ListBoxesWithTransferableItems extends JPanel {
	/**
	 * Generated value
	 */
	private static final long serialVersionUID = 1L;
	
	private JLabel labelForLeftListBox = new JLabel("Left List Box");
	private JLabel labelForRightListBox = new JLabel("Right List Box");
	private JList<Object> leftListBox;
	private JList<Object> rightListBox;
	private JButton transferFromLeftToRightButton = new JButton(">>");
	private JButton transferFromRightToLeftButton = new JButton("<<");

	public ListBoxesWithTransferableItems(Object[] listDataForLeftList, Object[] listDataForRightList) {
		init(listDataForLeftList, listDataForRightList);
	}

	public ListBoxesWithTransferableItems(Object[] listDataForLeftList, Object[] listDataForRightList,
			String leftListBoxLabelTxt, String rightListBoxLabelTxt) {
		labelForLeftListBox.setText(leftListBoxLabelTxt);
		labelForRightListBox.setText(rightListBoxLabelTxt);
		init(listDataForLeftList, listDataForRightList);
	}
	
	public List<Object> getRightListBoxData(){
		ListModel<Object> rightListModel = this.rightListBox.getModel();
		
		List<Object> dataList = new ArrayList<Object>();
		for(int i=0; i < rightListModel.getSize(); i++){
		     dataList.add(rightListModel.getElementAt(i));  
		}
		
		return dataList;
	}

	private void init(Object[] listDataForLeftList, Object[] listDataForRightList) {
		leftListBox = new JList<Object>(listDataForLeftList);
		rightListBox = new JList<Object>(listDataForRightList);
		layoutComponents();
	}

	private void layoutComponents() {
		add(createPanelForListBoxWithLabel(leftListBox, labelForLeftListBox));
		JPanel buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.add(transferFromLeftToRightButton, BorderLayout.NORTH);
		buttonPanel.add(transferFromRightToLeftButton, BorderLayout.SOUTH);
		add(buttonPanel);
		add(createPanelForListBoxWithLabel(rightListBox, labelForRightListBox));
	}
	
	private JPanel createPanelForListBoxWithLabel(JList<Object> listBox, JLabel label) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(label, BorderLayout.NORTH);
		JScrollPane scrollPaneForListBox = new JScrollPane(listBox);
		panel.add(scrollPaneForListBox, BorderLayout.CENTER);
		return panel;
	}

	private void transferSelectedItemFromLeftListBoxToRight() {
		if (isItemSelectedInLeftListBox()){
			Object selectedItemFromLeftListBox = getSelectedItemFromLeftListBox();

			DefaultListModel<Object> leftListModel = (DefaultListModel<Object>) this.leftListBox.getModel();
			leftListModel.removeElement(selectedItemFromLeftListBox);

			DefaultListModel<Object> rightListModel = (DefaultListModel<Object>) this.rightListBox.getModel();
			rightListModel.addElement(selectedItemFromLeftListBox);
		}
	}

	private boolean isItemSelectedInLeftListBox(){
		return this.leftListBox.getSelectedIndex() != -1;
	}
	
	private Object getSelectedItemFromLeftListBox(){
		return this.leftListBox.getSelectedValue();
	}

	private void transferSelectedItemFromRightListBoxToLeft() {
		if (isItemSelectedInRightListBox()){
			Object selectedItemFromRightListBox = getSelectedItemFromRightListBox();

			DefaultListModel<Object> rightListModel = (DefaultListModel<Object>) this.rightListBox.getModel();
			rightListModel.removeElement(selectedItemFromRightListBox);

			DefaultListModel<Object> leftListModel = (DefaultListModel<Object>) this.leftListBox.getModel();
			leftListModel.addElement(selectedItemFromRightListBox);
		}
	}

	private boolean isItemSelectedInRightListBox(){
		return this.rightListBox.getSelectedIndex() != -1;
	}
	
	private Object getSelectedItemFromRightListBox(){
		return this.rightListBox.getSelectedValue();
	}

	/**
	 * ******* Inner Class *************
	 */
	class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton sourceButton = (JButton) e.getSource();
			if (sourceButton == transferFromLeftToRightButton) {
				transferSelectedItemFromLeftListBoxToRight();
			} else if (sourceButton == transferFromRightToLeftButton) {
				transferSelectedItemFromRightListBoxToLeft();
			}
		}
	}
}
