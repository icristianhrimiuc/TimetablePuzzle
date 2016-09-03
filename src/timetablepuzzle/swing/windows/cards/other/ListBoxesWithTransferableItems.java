package timetablepuzzle.swing.windows.cards.other;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.BoxLayout;
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
	
	public ListBoxesWithTransferableItems() {
		this(new Object[]{}, new Object[]{}, DefaultLeftListBoxHeader, DefaultRightListBoxHeader);
	}
	
	public ListBoxesWithTransferableItems(Object[] listDataForLeftList, Object[] listDataForRightList) {
		this(listDataForLeftList, listDataForRightList, DefaultLeftListBoxHeader, DefaultRightListBoxHeader);
	}
	
	public ListBoxesWithTransferableItems(Object[] listDataForLeftList, Object[] listDataForRightList,
			String leftListBoxLabelTxt, String rightListBoxLabelTxt) {
		this(listDataForLeftList, listDataForRightList, leftListBoxLabelTxt,
				rightListBoxLabelTxt, DefaultLeftToRightButtonText, DefaultRightToLeftButtonText);
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
		
		LayoutComponents();
	}
	
	private DefaultListModel<Object> GetDefaultListModel(Object[] elements){
		DefaultListModel<Object> model = new DefaultListModel<Object>();
		for(Object element : elements){
			model.addElement(element);
		}
		
		return model;
	}

	private void LayoutComponents() {
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(CreatePanelForListBoxWithLabel(leftListBox, labelForLeftListBox));
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));
		buttonsPanel.add(transferFromLeftToRightButton);
		buttonsPanel.add(transferFromRightToLeftButton);
		this.add(buttonsPanel);
		this.add(CreatePanelForListBoxWithLabel(rightListBox, labelForRightListBox));
	}
	
	private JPanel CreatePanelForListBoxWithLabel(JList<Object> listBox, JLabel label) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(label);
		JScrollPane scrollPaneForListBox = new JScrollPane(listBox);
		double preferedHeight = scrollPaneForListBox.getPreferredSize().getHeight();
		scrollPaneForListBox.setPreferredSize(new Dimension((int)preferedHeight, 150));
		panel.add(scrollPaneForListBox);
		return panel;
	}

	public void ReInitializeLists(Object[] listDataForLeftList, Object[] listDataForRightList) {
		DefaultListModel<Object> leftListModel = (DefaultListModel<Object>)this.leftListBox.getModel();
		DefaultListModel<Object> rightListModel = (DefaultListModel<Object>) this.rightListBox.getModel();
		leftListModel.removeAllElements();
		rightListModel.removeAllElements();
		for(Object element : listDataForLeftList){
			leftListModel.addElement(element);
		}
		for(Object element : listDataForRightList){
			rightListModel.addElement(element);
		}
	}
	
	public List<Object> GetLeftListSelectedElements(){
		return this.leftListBox.getSelectedValuesList();
	}

	public List<Object> GetRightListSelectedElements(){
		return this.rightListBox.getSelectedValuesList();
	}

	public List<Object> GetLeftListElements(){
		return GetListElements(this.leftListBox);
	}

	public List<Object> GetRightListElements(){
		return GetListElements(this.rightListBox);
	}
	
	private List<Object> GetListElements(JList<Object> list){
		List<Object> elements = new ArrayList<Object>();
		Enumeration<Object> elementsData = ((DefaultListModel<Object>)list.getModel()).elements();
		
		while(elementsData.hasMoreElements()){
			elements.add(elementsData.nextElement());
		}
		
		return elements;
	}

	/**
	 * ******* Inner Class *************
	 */
	class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton sourceButton = (JButton) e.getSource();
			if (sourceButton == transferFromLeftToRightButton) {
				TransferSelectedItemsFromFirstListToSecond(leftListBox, rightListBox);
			} else if (sourceButton == transferFromRightToLeftButton) {
				TransferSelectedItemsFromFirstListToSecond(rightListBox, leftListBox);
			}
		}

		private void TransferSelectedItemsFromFirstListToSecond(JList<Object> firstList, JList<Object> secondList) {
			if (!firstList.isSelectionEmpty()){
				DefaultListModel<Object> firstListModel = (DefaultListModel<Object>)firstList.getModel();
				DefaultListModel<Object> secondListModel = (DefaultListModel<Object>)secondList.getModel();
				List<Object> selectedItemsFromFirstList = firstList.getSelectedValuesList();
				for(Object item : selectedItemsFromFirstList ){
					firstListModel.removeElement(item);
					secondListModel.addElement(item);
				}
			}
		}
	}
}
