package com.billingsample.domain;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Class that holds a collection of bill items and the customer that is making
 * the purchase
 * 
 * @author Akhil
 * 
 */
public class Bill {

	private Collection<Item> items;
	private Customer Customer;

	/**
	 * @return the customer
	 */
	public Customer getCustomer() {
		return Customer;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(Customer customer) {
		Customer = customer;
	}

	/**
	 * @return the items
	 */
	public Collection<Item> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public void setItems(Collection<Item> items) {
		this.items = items;
	}

	/**
	 * Convenience method to add an item to the bill
	 * 
	 * @param item
	 */
	public void addItem(Item item) {
		// If items is null, then initialize it with an ArrayList
		if (null == this.items) {
			this.items = new ArrayList<Item>();
		}
		// Add the item to the collection
		this.items.add(item);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Bill [items=" + items + ", Customer=" + Customer + "]";
	}
}
