package com.billingsample.calculation;

import com.billingsample.domain.Bill;

/**
 * Interface for all bill calculators
 * 
 * @author Akhil
 * 
 */
public interface BillCalculator {

	/**
	 * Calculates the net amount payable for the given bill.
	 * 
	 * @param bill the bill
	 */
	public double getNetPayable(Bill bill);

}
