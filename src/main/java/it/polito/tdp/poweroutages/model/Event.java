package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class Event implements Comparable<Event>{

	public enum EventType {
		INIZIO_INTERRUZIONE, FINE_INTERRUZIONE, FINE_DONAZIONE
	}

	private EventType type;
	private LocalDateTime time;
	private Nerc nerc; // per l'evento INIZIO_INTERRUZIONE il nerc è quello che ha bisogno di energia,
						// per l'evento FINE_INTERRUZIONE è quello che ha fornito energia
	private PowerOutage po;

	public Event(EventType type, LocalDateTime time, Nerc nerc, PowerOutage po) {
		super();
		this.type = type;
		this.time = time;
		this.nerc = nerc;
		this.po = po;
	}

	public PowerOutage getPo() {
		return po;
	}

	public void setPo(PowerOutage po) {
		this.po = po;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public Nerc getNerc() {
		return nerc;
	}

	public void setNerc(Nerc nerc) {
		this.nerc = nerc;
	}

	@Override
	public int compareTo(Event other) {
		return this.time.compareTo(other.time);
	}

	@Override
	public String toString() {
		return "Event [type=" + type + ", time=" + time + ", nerc=" + nerc + ", po=" + po + "]";
	}

}
