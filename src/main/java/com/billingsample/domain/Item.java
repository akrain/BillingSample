package com.billingsample.domain;

/**
 * Represents a transacted item in a bill
 * 
 * @author Akhil
 * 
 */
public class Item {

	private String productId;
	private boolean grocery;
	private double price;

	/**
	 * Default constructor
	 */
	public Item() {

	}

	/**
	 * @param productId
	 * @param grocery
	 * @param price
	 */
	public Item(String productId, boolean grocery, double price) {
		this.productId = productId;
		this.grocery = grocery;
		this.price = price;
	}

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}

	/**
	 * @return the grocery flag
	 */
	public boolean isGrocery() {
		return grocery;
	}

	/**
	 * @param grocery the grocery flag to set. True indicates item is grocery.
	 */
	public void setGrocery(boolean grocery) {
		this.grocery = grocery;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Item [productId=" + productId + ", grocery=" + grocery + ", price=" + price + "]";
	}
}
