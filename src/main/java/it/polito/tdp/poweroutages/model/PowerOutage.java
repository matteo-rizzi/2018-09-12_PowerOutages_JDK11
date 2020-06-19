package it.polito.tdp.poweroutages.model;

import java.time.LocalDateTime;

public class PowerOutage {

	private int id;
	private Nerc nerc;
	private LocalDateTime dateEventBegan;
	private LocalDateTime dateEventFinished;

	public PowerOutage(int id, Nerc nerc, LocalDateTime dateEventBegan, LocalDateTime dateEventFinished) {
		super();
		this.id = id;
		this.nerc = nerc;
		this.dateEventBegan = dateEventBegan;
		this.dateEventFinished = dateEventFinished;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Nerc getNerc() {
		return nerc;
	}

	public void setNerc(Nerc nerc) {
		this.nerc = nerc;
	}

	public LocalDateTime getDateEventBegan() {
		return dateEventBegan;
	}

	public void setDateEventBegan(LocalDateTime dateEventBegan) {
		this.dateEventBegan = dateEventBegan;
	}

	public LocalDateTime getDateEventFinished() {
		return dateEventFinished;
	}

	public void setDateEventFinished(LocalDateTime dateEventFinished) {
		this.dateEventFinished = dateEventFinished;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		PowerOutage other = (PowerOutage) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PowerOutage [id=" + id + ", nerc=" + nerc + ", dateEventBegan=" + dateEventBegan
				+ ", dateEventFinished=" + dateEventFinished + "]";
	}

}
