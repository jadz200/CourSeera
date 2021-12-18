package Implemented;

import Interfaces.Instructor;

public class Instructor2 implements Instructor{
	private final String firstName;
	private final String lastName;

	public Instructor2(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public String getFirstName() {
		return this.firstName;
	}

	@Override
	public String getLastName() {
		return this.lastName;
	}
}