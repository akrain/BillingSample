package com.billingsample.builder.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.billingsample.calculation.BillCalculator;
import com.billingsample.calculation.impl.DiscountBillCalculator;
import com.billingsample.domain.Bill;
import com.billingsample.domain.Customer;
import com.billingsample.domain.Item;

/**
 * Unit test cases for BillBuilder class
 * 
 * @author Akhil
 * 
 */
public class DiscountBillCalculatorTest {

	private BillCalculator billCalculator;
	private Bill bill;

	@Before
	public void setup() {
		billCalculator = new DiscountBillCalculator();
		bill = new Bill();
	}

	@After
	public void tearDown() {
		billCalculator = null;
		bill = null;
	}

	/**
	 * Bill is null
	 */
	@Test
	public void testNullBill() {
		bill = null;
		billCalculator.getNetPayable(bill);

		// Try to calculate
		double netPayable = billCalculator.getNetPayable(bill);
		Assert.assertEquals(0, netPayable, 0);
	}

	/**
	 * Bill without any items
	 */
	@Test()
	public void testBillWithoutItems() {
		bill.setCustomer(new Customer("JOE12", false, false, new Date()));

		// Try to calculate
		double netPayable = billCalculator.getNetPayable(bill);
		Assert.assertEquals(0, netPayable, 0);
	}

	/**
	 * Bill without a customer
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testBillWithoutCustomer() {
		// Make the bill but don't add customer
		bill.setItems(new ArrayList<Item>());

		// Try to calculate
		billCalculator.getNetPayable(bill);
	}

	/**
	 * Bill with a null customerId
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testBillWithoutCustomerId() {
		// Make the bill but don't add customerId
		bill.setItems(new ArrayList<Item>());
		bill.setCustomer(new Customer());

		billCalculator.getNetPayable(bill);
	}

	/**
	 * Items with zero or negative price are not considered in summation
	 */
	@Test()
	public void testItemsWithInvalidPrice() {
		// Make a bill with invalid item prices
		bill.addItem(new Item("Spinach123", true, 0));
		bill.addItem(new Item("Drink101", false, -10));
		bill.setCustomer(new Customer("JOE12", false, false, new Date()));

		double netPayable = billCalculator.getNetPayable(bill);
		Assert.assertEquals(0, netPayable, 0);
	}

	/**
	 * Customer not eligible for any percentage based discounts. Item is grocery
	 */
	@Test()
	public void testTotalNoDiscountsGrocery() {
		bill.addItem(new Item("Spinach123", true, 22.22));
		bill.addItem(new Item("Onion311", true, 10.43));
		bill.setCustomer(new Customer("JOE12", false, false, new Date()));

		double netPayable = billCalculator.getNetPayable(bill);
		// No discount expected. Value should be total of the two items
		Assert.assertEquals(32.65, netPayable, 0);
	}

	/**
	 * Customer not eligible for any percentage based discounts. Item is not
	 * grocery
	 */
	@Test()
	public void testTotalNoDiscountsNonGrocery() {
		bill.addItem(new Item("Drink101", false, 9.99));
		bill.addItem(new Item("Toothpaste101", false, 5.33));
		bill.setCustomer(new Customer("JOE12", false, false, new Date()));

		double netPayable = billCalculator.getNetPayable(bill);
		Assert.assertEquals(15.32, netPayable, 0);
	}

	/**
	 * Customer is an affiliate
	 */
	@Test()
	public void testAffiliate() {
		bill.addItem(new Item("Drink123", false, 22));
		// Set affiliate flag for customer
		bill.setCustomer(new Customer("AFF1", false, true, new Date()));

		double netPayable = billCalculator.getNetPayable(bill);
		// Expect discounted value
		Assert.assertEquals(19.8, netPayable, 0);
	}

	/**
	 * Customer is an employee
	 */
	@Test()
	public void testEmployee() {
		bill.addItem(new Item("Soap123", false, 22));
		// Customer with employee flag true
		bill.setCustomer(new Customer("EMP1", true, true, new Date()));

		double netPayable = billCalculator.getNetPayable(bill);
		// Expect discounted value
		Assert.assertEquals(15.4, netPayable, 0);
	}

	/**
	 * Customer for more than two years
	 */
	@Test()
	public void testOldCustomer() {
		bill.addItem(new Item("Soap123", false, 22));
		// Customer with first purchase more than 2 years ago
		bill.setCustomer(new Customer("OLD1", false, false, getDate(2010, Calendar.SEPTEMBER, 11)));

		double netPayable = billCalculator.getNetPayable(bill);
		Assert.assertEquals(20.9, netPayable, 0);
	}

	/**
	 * Customer is eligible for all percentage discounts.
	 */
	@Test()
	public void testAllPercentageDiscounts() {
		bill.addItem(new Item("Shampoo555", false, 33.33));
		// Customer is affiliate, emloyee and has been a customer for more than
		// 2 years
		bill.setCustomer(new Customer("OLD1", true, true, getDate(2010, Calendar.SEPTEMBER, 11)));

		double netPayable = billCalculator.getNetPayable(bill);
		Assert.assertEquals(23.33, netPayable, 0);
	}

	/**
	 * Customer is eligible for percentage discounts but item is grocery
	 */
	@Test()
	public void testGroceryItem() {
		// Item is grocery
		bill.addItem(new Item("Nachos901", true, 10));
		bill.setCustomer(new Customer("OLD1", true, true, getDate(2010, Calendar.SEPTEMBER, 11)));

		double netPayable = billCalculator.getNetPayable(bill);
		Assert.assertEquals(10, netPayable, 0);
	}

	/**
	 * Amount after discounts for the bill is
	 */
	@Test()
	public void testDiscountOnTotal() {
		bill.addItem(new Item("Fish123", true, 110));
		bill.addItem(new Item("Nachos901", true, 50));
		bill.addItem(new Item("Soap555", false, 40));
		bill.addItem(new Item("Laptop555", false, 600));

		// Customer is not eligible for percentage discounts
		bill.setCustomer(new Customer("JOE12", false, false, new Date()));

		double netPayable = billCalculator.getNetPayable(bill);
		Assert.assertEquals(760, netPayable, 0);
	}

	/**
	 * Total amount after discounts for the bill is
	 */
	@Test()
	public void testDiscountOnBigBill() {

		bill.addItem(new Item("Spinach123", true, 22));
		bill.addItem(new Item("Onion311", true, 50));
		bill.addItem(new Item("Drink111", false, 30));
		bill.addItem(new Item("Toothpaste101", false, 10));
		bill.addItem(new Item("Fish123", true, 110));
		bill.addItem(new Item("Nachos901", true, 50));
		bill.addItem(new Item("Soap555", false, 40));
		bill.addItem(new Item("Laptop555", false, 600));

		// Customer with affiliate flag true
		bill.setCustomer(new Customer("TOM7", false, true, new Date()));

		double netPayable = billCalculator.getNetPayable(bill);
		Assert.assertEquals(804, netPayable, 0);
	}

	/**
	 * Customer without any previous purchases
	 */
	@Test()
	public void testNewCustomer() {
		bill.addItem(new Item("Drink123", false, 22));
		bill.setCustomer(new Customer("JOE12", false, false, null));

		double netPayable = billCalculator.getNetPayable(bill);
		Assert.assertEquals(22, netPayable, 0);
	}

	/**
	 * Get a date with the given parameters
	 * 
	 * @param year
	 * @param month
	 * @param date
	 * @return
	 */
	private Date getDate(int year, int month, int date) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(year, month, date);
		return gc.getTime();
	}

}
