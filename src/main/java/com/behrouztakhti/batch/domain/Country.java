package com.behrouztakhti.batch.domain;


import jakarta.persistence.*;


/**
 * This is COUNTRY entity.
 * @author behrouz.takhti@gmail.com
 */
@Entity
@Table(name = "COUNTRY")
public class Country {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Long id;

	@Column(name = "UNLOCODE")
	private String unloCode;

	@Column(name = "NAME")
	private String name;

	public Country(String unloCode, String name) {
		this.unloCode = unloCode;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUnloCode() {
		return unloCode;
	}

	public void setUnloCode(String unloCode) {
		this.unloCode = unloCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}