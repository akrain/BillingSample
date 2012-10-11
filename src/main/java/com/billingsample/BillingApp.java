package com.billingsample;

import java.util.Date;

import com.billingsample.calculation.BillCalculator;
import com.billingsample.calculation.impl.DiscountBillCalculator;
import com.billingsample.domain.Bill;
import com.billingsample.domain.Customer;
import com.billingsample.domain.Item;

/**
 * Executable class that displays a dummy bill and find the net amount payable
 * for the same
 * 
 * @author Akhil
 */
public class BillingApp {

	public static void main(String[] args) {

		BillCalculator billCalculator = new DiscountBillCalculator();

		Bill bill = getABill();
		printItems(bill);

		System.out.println("\nNet payable amount is: " + billCalculator.getNetPayable(bill));
	}

	/**
	 * Convenience method creates a dummy bill
	 * 
	 * @return the bill
	 */
	private static Bill getABill() {
		// /New bill
		Bill bill = new Bill();

		// Add some items to the bill
		bill.addItem(new Item("Spinach123", true, 22));
		bill.addItem(new Item("Onion311", true, 50));
		bill.addItem(new Item("Drink111", false, 30));
		bill.addItem(new Item("Cleaner101", false, 10));
		bill.addItem(new Item("Fish123", true, 110));
		bill.addItem(new Item("Nachos901", true, 50));
		bill.addItem(new Item("Container555", false, 40));
		bill.addItem(new Item("Laptop555", false, 600));

		// Customer with affiliate flag true
		bill.setCustomer(new Customer("TOM7", false, true, new Date()));

		return bill;
	}

	private static void printItems(Bill bill) {
		if (bill != null && bill.getItems() != null) {
			System.out.println("Items in the bill");
			for (Item item : bill.getItems()) {
				System.out.println(item);
			}
		}

	}

}
