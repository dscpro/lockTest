package com.proceduretest;

public class Employee {
	private String name="123";
	private double salary=1322;

	public Employee() {
	}

	public Employee(String n, double s, int year, int month, int day) {
		name = n;
		salary = s;
	}

	public String getName() {
		return name;
	}

	public double getSalary() {
		return salary;
	}

	public void raiseSalary(double byPercent) {
		double raise = salary * byPercent / 100;
		salary += raise;
	}

}
