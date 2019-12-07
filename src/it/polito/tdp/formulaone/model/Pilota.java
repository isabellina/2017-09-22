package it.polito.tdp.formulaone.model;

import java.time.LocalTime;

public class Pilota {

	private int driveId;
	private int tempogiro;
	private int punteggio;
	private int giroArrivo;
	
	
	public Pilota(int driveId,int tempogiro, int punteggio, int giroArrivo) {
		
		this.driveId = driveId;
		this.tempogiro = tempogiro;
		this.punteggio = punteggio;
		this.giroArrivo = giroArrivo;
	}


	public int getDriveId() {
		return driveId;
	}


	public void setDriveId(int driveId) {
		this.driveId = driveId;
	}


	public int getTempogiro() {
		return tempogiro;
	}


	public void setTempogiro(int tempogiro) {
		this.tempogiro = tempogiro;
	}


	public int getPunteggio() {
		return punteggio;
	}


	public void setPunteggio(int punteggio) {
		this.punteggio = punteggio;
	}
	
	public int getGiroArrivo() {
		return this.giroArrivo;
	}


	@Override
	public String toString() {
		return "Pilota [driveId=" + driveId + ", tempogiro=" + tempogiro + ", punteggio=" + punteggio + "]";
	}
	
	
	
	
	
	
	
}
