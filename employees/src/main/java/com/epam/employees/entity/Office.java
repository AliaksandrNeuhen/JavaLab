package com.epam.employees.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Office {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	private String description;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FK_ID_ADRESS")
	private Adress adress;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FK_ID_COMPANY")
	private Company company;
	
	// TODO Add formula
	private int countOfEmployees;
	
	public Office() {}
	public Office(long id, String description,
			Adress adress, Company company) {
		setId(id);
		setDescription(description);
		setAdress(adress);
		setCompany(company);
	}
	public Office(String description,
			Adress adress, Company company) {
		setDescription(description);
		setAdress(adress);
		setCompany(company);
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Adress getAdress() {
		return adress;
	}
	public void setAdress(Adress adress) {
		this.adress = adress;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public int getCountOfEmployees() {
		return countOfEmployees;
	}
	public void setCountOfEmployees(int countOfEmployees) {
		this.countOfEmployees = countOfEmployees;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adress == null) ? 0 : adress.hashCode());
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + countOfEmployees;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Office other = (Office) obj;
		if (adress == null) {
			if (other.adress != null)
				return false;
		} else if (!adress.equals(other.adress))
			return false;
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (countOfEmployees != other.countOfEmployees)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return getClass().getSimpleName() + " [id=" + id + ", description=" + description
				+ ", adress=" + adress + ", company=" + company
				+ ", countOfEmployees=" + countOfEmployees + "]";
	}
}
