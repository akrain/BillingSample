BillingSample
=============

Assumptions:

1. Apply highest discount possible in case the customer is eligible for multiple discounts.
2. The flat 5$ discount on total is calculated only after deduction of percentage discounts.

Prerequisites:
JDK 1.6 and above, Maven 2.2.1 upwards.<br/>
Please ensure that JAVA_HOME is set to the JDK home directory in order to run Maven

Run the unit tests:<br/>
1. Open a terminal<br/>
2. Go to the project root directory<br/>
3. Run the following command:<br/>
    mvn test

Run the application with dummy data:<br/>
1. Open a terminal<br/>
2. Go to the project root directory<br/>
3. Run the following command:<br/>
    mvn install exec:java
