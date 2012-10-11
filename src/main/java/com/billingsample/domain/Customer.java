package com.billingsample.domain;

import java.util.Date;

/**
 * Represents a customer of the retail store
 * 
 * @author Akhil
 * 
 */
public class Customer {

	private String id;
	private boolean employee;
	private boolean affiliate;
	private Date firstPurchaseDate;

	/**
	 * Default constructor
	 */
	public Customer() {

	}

	/**
	 * Intialize a new customer with the given parameters
	 * 
	 * @param id
	 * @param employee
	 * @param affiliate
	 * @param firstPurchaseDate
	 */
	public Customer(String id, boolean employee, boolean affiliate, Date firstPurchaseDate) {
		super();
		this.id = id;
		this.employee = employee;
		this.affiliate = affiliate;
		this.firstPurchaseDate = firstPurchaseDate;
	}

	/**
	 * @return the customer id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the customer id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the employee flag
	 */
	public boolean isEmployee() {
		return employee;
	}

	/**
	 * @param employee the employee flag to set
	 */
	public void setEmployee(boolean employed) {
		this.employee = employed;
	}

	/**
	 * @return the affiliate flag
	 */
	public boolean isAffiliate() {
		return affiliate;
	}

	/**
	 * @param affiliate the affiliate flag to set.
	 */
	public void setAffiliate(boolean affiliated) {
		this.affiliate = affiliated;
	}

	/**
	 * @return the firstPurchaseDate
	 */
	public Date getFirstPurchaseDate() {
		return firstPurchaseDate;
	}

	/**
	 * @param firstPurchaseDate the firstPurchaseDate to set
	 */
	public void setFirstPurchaseDate(Date firstPurchaseDate) {
		this.firstPurchaseDate = firstPurchaseDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [userId=" + id + ", employee=" + employee + ", affiliate=" + affiliate
				+ ", firstPurchaseDate=" + firstPurchaseDate + "]";
	}

}
