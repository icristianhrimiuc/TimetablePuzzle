package timetablepuzzle.swing.windows.cards.common;

import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

public class CustomComboBoxModel<E> extends AbstractListModel<E> implements ComboBoxModel<E> {
	/**
	 * Generated field
	 */
	private static final long serialVersionUID = 1L;

	private List<E> data;
	private E selectedItem;

	public CustomComboBoxModel() {
	}

	public void setData(List<E> data) {
		this.data = data;
		this.selectedItem = null;
		this.fireContentsChanged(this, 0, getSize());
	}

	@Override
	public int getSize() {
		return this.data.size();
	}

	@Override
	public E getElementAt(int index) {
		E element = null;
		if ((index >= 0) && (index < getSize())) {
			element = this.data.get(index);
		}

		return element;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setSelectedItem(Object anItem) {
		if (this.data.contains(anItem)) {
			this.selectedItem = (E)anItem;
		}
	}

	@Override
	public E getSelectedItem() {
		return selectedItem;
	}
}
