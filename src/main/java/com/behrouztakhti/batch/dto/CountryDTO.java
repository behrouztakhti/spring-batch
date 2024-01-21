package com.behrouztakhti.batch.dto;

/**
 * This is CountryDTO. We use this while reader reads data from csv file.
 * @author behrouz.takhti@gmail.com
 */
public class CountryDTO {
	private String unloCode;
	private String name;

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