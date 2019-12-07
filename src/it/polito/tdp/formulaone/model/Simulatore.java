package it.polito.tdp.formulaone.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;


import it.polito.tdp.formulaone.db.FormulaOneDAO;



public class Simulatore {

	
	
	private PriorityQueue<Evento> queue; 
	
	private double probabilità;
	private int durataPit;
	private int numeroGiri;
	private int raceId;
	
	private FormulaOneDAO dao;
	
	List<Pilota> classifica = new LinkedList<Pilota>();
	
	private List<Pilota> listaPiloti = new LinkedList<Pilota>();
	private ArrayList<Boolean> arrayProbabilità = new ArrayList<Boolean>();
	
	public Simulatore() {
		this.dao = new FormulaOneDAO();
	}
	
	
	public void init(double probabilità, int durataPit, int giri, int raceId) {
		Random r = new Random();
		boolean pitstop;
		 this.listaPiloti = new LinkedList<Pilota>(this.dao.getPilotiGara(raceId));
		 System.out.println("lista piloti: " + this.listaPiloti.size());
		 this.numeroGiri = giri;
		 this.raceId = raceId;
		 this.durataPit = durataPit;
		 this.queue = new PriorityQueue<>();
		 this.classifica = this.dao.getPilotiGara(raceId);
		 this.creaListaProbabilita((int) (probabilità*100));
		 
		 for(Pilota p : listaPiloti) {
			 Pilota q = p;
			 q.setTempogiro(this.dao.tempoGiro(raceId, q.getDriveId(), 1));
			 pitstop = this.arrayProbabilità.get(r.nextInt(99)).equals(Boolean.TRUE);
			 
			 if(q.getGiroArrivo() == 1) {
				 this.queue.add(new Evento(Evento.tipoEvento.INCIDENTE,0,q, 1));
			 }
			 
			 else if(pitstop==true) {
				 this.queue.add(new Evento(Evento.tipoEvento.PITSTOP,durataPit,q, 1));
			 }
			 else{
				 this.queue.add(new Evento(Evento.tipoEvento.FINISCEGIRO,0,q, 1));
			 }
			
			 
		 }
		 
		  
	}
	
	
	public void run() {
		System.out.println("RUNNING....");
		int tempoGiro = -1;
		Pilota ptemp = null;
		int giro = 1;
		int tempo=0;
		
		while(!this.queue.isEmpty()) {
			System.out.println("Entering while...");
			Evento ev = this.queue.poll();
			 switch (ev.getTipo()) {
			 
			
			 
			 case PITSTOP:
				 System.out.println("PITSTOP...");
				  tempo = this.dao.tempoGiro(this.raceId, ev.getPilota().getDriveId(),ev.getGiro() )+ this.durataPit;
				  if(tempo<=tempoGiro || tempoGiro==-1) {
						 tempoGiro = tempo;
						 ptemp = ev.getPilota();
					 }
				 break;
				 
			 case FINISCEGIRO:
				 System.out.println("ENDGIRO...");
				  tempo = this.dao.tempoGiro(this.raceId, ev.getPilota().getDriveId(),ev.getGiro() );
				 if(tempo<=tempoGiro || tempoGiro==-1) {
					 tempoGiro = tempo;
					 ptemp = ev.getPilota();
				 }
				 break;
				 
			 default:
				 System.out.println("DEFAULT...");
				 break;
				 
			 }
			 
			if(ev.getGiro() != giro) {
				giro = giro + 1;
				if(ptemp != null) {
					for(Pilota pcl : this.classifica) {
						if(pcl.getDriveId() == ptemp.getDriveId()) {
							pcl.setPunteggio(pcl.getPunteggio() + 1);
						}
					}
				}
			}
			
			if(ev.getGiro() < this.numeroGiri) {
				Random r = new Random();
				 ev.getPilota().setTempogiro(this.dao.tempoGiro(raceId, ev.getPilota().getDriveId(), ev.getGiro()+1));
				 boolean pitstop = this.arrayProbabilità.get(r.nextInt(99)).equals(Boolean.TRUE);
				 
				 if(ev.getPilota().getGiroArrivo() == 1) {
					 this.queue.add(new Evento(Evento.tipoEvento.INCIDENTE,0,ev.getPilota(), ev.getGiro() + 1));
				 }
				 
				 else if(pitstop==true) {
					 this.queue.add(new Evento(Evento.tipoEvento.PITSTOP,durataPit,ev.getPilota(), ev.getGiro() + 1));
				 }
				 else{
					 this.queue.add(new Evento(Evento.tipoEvento.FINISCEGIRO,0,ev.getPilota(), ev.getGiro() + 1));
				 }
			}
		}
	}
	
	
	
	public String getClassifica() {
		String ret = "";
		for(Pilota p : classifica) {
			if(p.getPunteggio()!=0) {
				ret+= p + "\n";
			}
		}
		return ret;
	}
	
	
	
	public void creaListaProbabilita(int p) {
		for(int i=0; i<100 ; i++) {
			if(i < p) {
				this.arrayProbabilità.add(Boolean.TRUE);
			}
			else {
				this.arrayProbabilità.add(Boolean.FALSE);
			}
		}
		System.out.println("size of array: " + this.arrayProbabilità.size());
		Collections.shuffle(this.arrayProbabilità);
	}
	
	
}
