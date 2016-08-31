package timetablepuzzle.swing.windows.cards.other;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ListBoxesWithTransferableItems extends JPanel {
	/**
	 * Generated value
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String DefaultLeftListBoxHeader = "Left List Box";
	private static final String DefaultRightListBoxHeader = "Right List Box";
	private static final String DefaultLeftToRightButtonText = ">>";
	private static final String DefaultRightToLeftButtonText = "<<";
	
	private JLabel labelForLeftListBox;
	private JLabel labelForRightListBox;
	private JButton transferFromLeftToRightButton;
	private JButton transferFromRightToLeftButton;
	private JList<Object> leftListBox;
	private JList<Object> rightListBox;

	public ListBoxesWithTransferableItems(Object[] listDataForLeftList, Object[] listDataForRightList) {
		this(listDataForLeftList, listDataForRightList, DefaultLeftListBoxHeader, DefaultRightListBoxHeader);
	}
	
	public ListBoxesWithTransferableItems(Object[] listDataForLeftList, Object[] listDataForRightList,
			String leftListBoxLabelTxt, String rightListBoxLabelTxt) {
		this(listDataForLeftList, listDataForRightList, DefaultLeftListBoxHeader,
				DefaultRightListBoxHeader, DefaultLeftToRightButtonText, DefaultRightToLeftButtonText);
	}

	public ListBoxesWithTransferableItems(Object[] listDataForLeftList, Object[] listDataForRightList,
			String leftListBoxLabelText, String rightListBoxLabelText, String leftToRightButtonText, String rightToLeftButtonText) {
		// Labels
		this.labelForLeftListBox = new JLabel(leftListBoxLabelText);
		this.labelForRightListBox = new JLabel(rightListBoxLabelText);
		// Buttons
		this.transferFromLeftToRightButton = new JButton(leftToRightButtonText);
		this.transferFromLeftToRightButton.addActionListener(new ButtonActionListener());
		this.transferFromRightToLeftButton = new JButton(rightToLeftButtonText);
		this.transferFromRightToLeftButton.addActionListener(new ButtonActionListener());
		// JLists
		
		this.leftListBox = new JList<Object>(GetDefaultListModel(listDataForLeftList));
		this.rightListBox = new JList<Object>(GetDefaultListModel(listDataForRightList));
		
		layoutComponents();
	}
	
	private DefaultListModel<Object> GetDefaultListModel(Object[] elements){
		DefaultListModel<Object> model = new DefaultListModel<Object>();
		for(Object element : elements){
			model.addElement(element);
		}
		
		return model;
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
	
	public Enumeration<Object> getRightListElements(){
		return ((DefaultListModel<Object>)this.rightListBox.getModel()).elements();
	}

	public Enumeration<Object> getLeftListElements(){
		return ((DefaultListModel<Object>)this.leftListBox.getModel()).elements();
	}
	
	public void MoveAllFromRightToLeft(){
		DefaultListModel<Object> rightListModel = (DefaultListModel<Object>) this.rightListBox.getModel();
		DefaultListModel<Object> leftListModel = (DefaultListModel<Object>)this.leftListBox.getModel();
		Enumeration<Object> rightListElements = rightListModel.elements();
		while(rightListElements.hasMoreElements()){
			Object element = rightListElements.nextElement();
			if(leftListModel.contains(element))
			{
				leftListModel.addElement(element);
			}
			rightListModel.removeElement(element);
		}
	}
	
	public void SetRightListElements(Object[] rightListElements){
		DefaultListModel<Object> rightListModel = (DefaultListModel<Object>) this.rightListBox.getModel();
		DefaultListModel<Object> leftListModel = (DefaultListModel<Object>)this.leftListBox.getModel();

		MoveAllFromRightToLeft();
		for(Object element : rightListElements){
			if(leftListModel.contains(element)){
				rightListModel.addElement(element);
			}
		}
	}

	/**
	 * ******* Inner Class *************
	 */
	class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton sourceButton = (JButton) e.getSource();
			if (sourceButton == transferFromLeftToRightButton) {
				transferSelectedItemsFromFirstListToSecond(leftListBox, rightListBox);
			} else if (sourceButton == transferFromRightToLeftButton) {
				transferSelectedItemsFromFirstListToSecond(rightListBox, leftListBox);
			}
		}

		private void transferSelectedItemsFromFirstListToSecond(JList<Object> firstList, JList<Object> secondList) {
			if (!firstList.isSelectionEmpty()){
				DefaultListModel<Object> fitstListModel = (DefaultListModel<Object>)firstList.getModel();
				DefaultListModel<Object> secondListModel = (DefaultListModel<Object>)secondList.getModel();
				List<Object> selectedItemsFromFirstList = firstList.getSelectedValuesList();
				for(Object item : selectedItemsFromFirstList ){
					fitstListModel.removeElement(item);
					secondListModel.addElement(item);
				}
			}
		}
	}
}
