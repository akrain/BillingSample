BillingSample
=============

Assumptions:

1. Apply highest discount possible in case the customer is eligible for multiple discounts.
2. The flat 5$ discount on total is calculated only after deduction of percentage discounts.

Prerequisites:
Maven 2.2.1 upwards

Run the unit tests:
1. Open a terminal
2. Go to the project root directory
3. Run the following command:
    mvn test

Run the application with dummy data:
1. Open a terminal
2. Go to the project root directory
3. Run the following command:
    mvn install exec:java
