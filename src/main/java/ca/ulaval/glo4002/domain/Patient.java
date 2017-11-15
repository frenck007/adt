package ca.ulaval.glo4002.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Patient {
	private static final String NO_DEPARTMENT = "<no department>";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private String name;

	@Column
	private PatientStatus status;

	@Column
	private String department = NO_DEPARTMENT;

	public Patient(String name) {
		this.name = name;
		this.status = PatientStatus.ADMITTED;
	}
	
	public Patient() {
		// hibernate
	}

	public void moveToDepartment(String newDepartment) {
		status = PatientStatus.TRANSFERED;
		department = newDepartment;
	}

	public void discharge() {
		status = PatientStatus.DISCHARGED;
		department = NO_DEPARTMENT;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public PatientStatus getStatus() {
		return status;
	}

	public String getDepartment() {
		return department;
	}

}
