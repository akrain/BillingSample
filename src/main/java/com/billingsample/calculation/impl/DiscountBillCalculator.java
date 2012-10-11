package com.billingsample.calculation.impl;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import com.billingsample.calculation.BillCalculator;
import com.billingsample.domain.Bill;
import com.billingsample.domain.Customer;
import com.billingsample.domain.Item;

/**
 * Calculates the total value of the bill after deducting all discounts eligible
 * for the items and customers given in the bill.
 * 
 * @author Akhil
 * 
 */
public final class DiscountBillCalculator implements BillCalculator {

	private static final int DISCOUNT_PER_HUNDRED = 5;

	/**
	 * Calculates the total amount payable for the given bill. Floors the total
	 * to the number nearest to the second decimal point.
	 * 
	 * @param bill the bill
	 */
	@Override
	public double getNetPayable(Bill bill) {

		double netPayable = 0;
		if (bill != null && bill.getItems() != null) {
			validateCustomer(bill);
			netPayable = totalAfterPercentageDiscounts(bill.getItems(), bill.getCustomer());
			netPayable = applyFlatDiscount(netPayable);
		}

		// Use floor to always round towards lower value. Never over charge the
		// customer. Precision is upto 2 decimal places
		return Math.floor(netPayable * 100) / 100;
	}

	/**
	 * Checks if the customer associated with the bill has id info
	 * 
	 * @param bill
	 * @return True if the customer is valid
	 */
	private boolean validateCustomer(Bill bill) {
		Customer customer = bill.getCustomer();
		if (customer == null || customer.getId() == null) {
			throw new IllegalArgumentException("customer or customerId should not be null");
		}
		return true;
	}

	/**
	 * 
	 * @param amount
	 * @return
	 */
	private double applyFlatDiscount(double amount) {
		int flatDiscount = (int) (amount / 100) * DISCOUNT_PER_HUNDRED;
		return amount - flatDiscount;
	}

	/**
	 * Calculates the total of the items after deducting the percentage based
	 * discount applicable for the given customer
	 * 
	 * @param items
	 * @param customer
	 * @return Total after deducting item discounts
	 */
	private double totalAfterPercentageDiscounts(Collection<Item> items, Customer customer) {
		double totalGrocery = 0;
		double totalNonGrocery = 0;

		// Find the total for grocery and non grocery items
		for (Item item : items) {
			// Ignore items with no price
			if (item != null && item.getPrice() > 0) {
				// Add price of item to appropriate total
				if (item.isGrocery()) {
					totalGrocery += item.getPrice();
				} else {
					totalNonGrocery += item.getPrice();
				}
			}
		}

		// Calculate discountedTotal
		totalNonGrocery = getTotalAfterDiscount(totalNonGrocery, getApplicableDiscount(customer));

		return totalGrocery + totalNonGrocery;
	}

	/**
	 * Subtracts the calculated discount amount from the total
	 * 
	 * @param amount
	 * @param discountMultiplier
	 * @return The total after applying discount
	 */
	private double getTotalAfterDiscount(double amount, double discountMultiplier) {
		double discount = amount * discountMultiplier;
		return amount - discount;
	}

	/**
	 * Get the applicable discount multiplier for the given customer. In case
	 * the user is eligible for multiple discounts, return the highest one
	 * 
	 * @param customer
	 * @return The appropriate DiscountMultiplier
	 */
	private double getApplicableDiscount(Customer customer) {
		// These checks should always be from highest to lowest discount values
		if (customer.isEmployee()) {
			return DiscountMultiplier.ZERO_POINT_THREE.value();
		} else if (customer.isAffiliate()) {
			return DiscountMultiplier.ZERO_POINT_ONE.value();
		} else if (isOldCustomer(customer)) {
			return DiscountMultiplier.ZERO_POINT_ZERO_FIVE.value();
		}
		return DiscountMultiplier.ZERO.value();
	}

	/**
	 * Checks if the given customer has been a customer for more than 2 years
	 * 
	 * @param customer
	 * @return
	 */
	private boolean isOldCustomer(Customer customer) {
		// Check if customer has a previous purchase
		if (customer.getFirstPurchaseDate() != null) {
			Calendar now = Calendar.getInstance();

			// Get calendar for the given date. Also get rid of time part
			Calendar toBeMatched = getSanitizedCalendar(customer.getFirstPurchaseDate());
			// Add 2 years to first purchase date
			toBeMatched.add(Calendar.YEAR, 2);

			// If lastPurchaseDate + 2 years is before now, then return true
			if (toBeMatched.before(now)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns a Calendar for the given date. Also sets the time fields to
	 * 00:00:00
	 * 
	 * @param firstPurchaseDate
	 * @return
	 */
	private Calendar getSanitizedCalendar(Date firstPurchaseDate) {
		Calendar sanitizedCalendar = Calendar.getInstance();
		sanitizedCalendar.setTime(firstPurchaseDate);
		sanitizedCalendar.set(Calendar.HOUR_OF_DAY, 0);
		sanitizedCalendar.set(Calendar.MINUTE, 0);
		sanitizedCalendar.set(Calendar.SECOND, 0);
		return sanitizedCalendar;
	}

	/**
	 * Represents the percentage discount offered. The percentage is calculated
	 * to be in multiplier format. For example, 5% is 5/100 = 0.05
	 * 
	 */
	private static enum DiscountMultiplier {
		ZERO(0), ZERO_POINT_ZERO_FIVE(0.05), ZERO_POINT_ONE(0.1), ZERO_POINT_THREE(0.3);

		private final double value;

		DiscountMultiplier(double value) {
			this.value = value;
		}

		public double value() {
			return this.value;
		}
	}

}