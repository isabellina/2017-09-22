package it.polito.tdp.formulaone.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;

public class Race {
	
	private int raceId ;
	private Year year ;
	
	private int circuitId ; // refers to {@link Circuit}
	private String name ;
	
	public Race(int raceId, Year year, int circuitId, String name) {
		super();
		this.raceId = raceId;
		this.year = year;
		
		this.circuitId = circuitId;
		this.name = name;
		
	}
	public int getRaceId() {
		return raceId;
	}
	public void setRaceId(int raceId) {
		this.raceId = raceId;
	}
	public Year getYear() {
		return year;
	}
	public void setYear(Year year) {
		this.year = year;
	}
	
	
	public int getCircuitId() {
		return circuitId;
	}
	public void setCircuitId(int circuitId) {
		this.circuitId = circuitId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return this.getName();
	}

}
