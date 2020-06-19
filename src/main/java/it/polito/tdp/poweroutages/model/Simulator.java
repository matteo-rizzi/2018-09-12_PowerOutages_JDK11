package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import it.polito.tdp.poweroutages.model.Event.EventType;

public class Simulator {
	
	private Model model;
	
	public Simulator(Model model) {
		this.model = model;
	}

	// CODA DEGLI EVENTI
	private PriorityQueue<Event> queue;

	// PARAMETRI DI SIMULAZIONE
	private Period numeroDiMesi = Period.ofMonths(100);

	// MODELLO DEL MONDO
	private List<Nerc> nercs;
	private List<PowerOutage> powerOutages;

	// VALORI DA CALCOLARE
	private int catastrofi;
 
	public List<Nerc> getNercs() {
		return nercs;
	}

	public int getCatastrofi() {
		return catastrofi;
	}

	public void setNumeroDiMesi(int numeroDiMesi) {
		this.numeroDiMesi = Period.ofMonths(numeroDiMesi);
	}
	
	public void init() {
		this.powerOutages = new ArrayList<>(this.model.loadAllPowerOutages());
		this.queue = new PriorityQueue<Event>();
		this.nercs = new ArrayList<Nerc>(this.model.getNercs());
		for(Nerc nerc : nercs) {
			nerc.setLibero(true);
			nerc.setNumeroGiorni(0);
			nerc.getDonatori().clear();
		}
		this.catastrofi = 0;
		
		for(PowerOutage po : this.powerOutages) {
			Event e = new Event(EventType.INIZIO_INTERRUZIONE, po.getDateEventBegan(), po.getNerc(), po);
			this.queue.add(e);
		}
	}
	
	public void run() {
		while (!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			System.out.println(e);
			processEvent(e);
		}
	}

	private void processEvent(Event e) {
		switch(e.getType()) {
		case INIZIO_INTERRUZIONE:
			if(this.model.getVicini(e.getNerc()).size() == 0) {
				// nessun nerc può aiutarlo
				catastrofi ++;
			}
			else {
				Nerc assegnato = assegnaNerc(e);
				if(assegnato == null) {
					// nessun vicino disponibile: catastrofe
					catastrofi ++;
				} else {
					// assegno assegnato al nerc corrente
					if(!e.getNerc().getDonatori().contains(assegnato)) {
						e.getNerc().getDonatori().add(assegnato);
						LocalDateTime fineDonazione = e.getTime().plus(numeroDiMesi);
						queue.add(new Event(EventType.FINE_DONAZIONE, fineDonazione, assegnato, e.getPo()));
					}
					assegnato.setLibero(false);
					
					queue.add(new Event(EventType.FINE_INTERRUZIONE, e.getPo().getDateEventFinished(), assegnato, e.getPo()));
				}
			}
			break;
		case FINE_INTERRUZIONE:
			e.getNerc().setLibero(true);
			e.getNerc().incrementaNumeroGiorni(Period.between(e.getPo().getDateEventBegan().toLocalDate(), e.getPo().getDateEventFinished().toLocalDate()).getDays());
			break;
		case FINE_DONAZIONE:
			e.getPo().getNerc().getDonatori().remove(e.getNerc());
			break;
		}
		
	}

	private Nerc assegnaNerc(Event e) {
		Nerc assegnato = null;
		List<Vicino> donazioni = new ArrayList<>();
		for(Vicino vicino : this.model.getVicini(e.getNerc())) {
			if(vicino.getVicino().getDonatori().contains(e.getNerc()) && vicino.getVicino().isLibero()) {
				donazioni.add(vicino);
			}
		}
		
		if(donazioni.size() == 1) {
			assegnato = donazioni.get(0).getVicino();
		}
		else if(donazioni.size() == 0) {
			for(int i = this.model.getVicini(e.getNerc()).size() - 1; i >= 0; i--) {
				if(this.model.getVicini(e.getNerc()).get(i).getVicino().isLibero()) {
					assegnato = this.model.getVicini(e.getNerc()).get(i).getVicino();
					break;
				}
			}
		}
		else {
			for(int i = donazioni.size() - 1; i >= 0; i--) {
				if(this.model.getVicini(e.getNerc()).get(i).getVicino().isLibero()) {
					assegnato = donazioni.get(i).getVicino();
					break;
				}
			}
		}
		
		return assegnato;
	}

}
