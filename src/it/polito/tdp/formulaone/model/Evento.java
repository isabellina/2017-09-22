package it.polito.tdp.formulaone.model;

import java.time.LocalTime;

public class Evento implements Comparable<Evento>{

	public enum tipoEvento {
		
		
		
		FINISCEGIRO,
		PITSTOP,
		INCIDENTE,
		
		
	}
	
	
	private int durataPitstop ;
	private Pilota pilota;
	private tipoEvento tipo;
	private int giro;
	
	public Evento(tipoEvento tipo, int durataPitstop, Pilota pilota,int giro) {
		super();
		
		this.durataPitstop = durataPitstop;
		this.pilota = pilota;
		this.tipo = tipo;
		this.giro=giro;
	}



	public int getDurataPitstop() {
		return durataPitstop;
	}

	public Pilota getPilota() {
		return pilota;
	}

    public int getGiro() {
    	return this.giro;
    }

	public tipoEvento getTipo() {
		return tipo;
	}



	@Override
	public int compareTo(Evento o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
    
	
}
