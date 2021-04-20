package com.SmartCity.smartcity;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;


@Entity
public class RealDistance {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private Long row;
	private Long col;
	private double distValue;
	private double durValue;
	
	private Date lastUpdate; 
	
	public RealDistance(Long row, Long col, double distValue, double durValue) {
		this.row = row;
		this.col = col;
		this.distValue = distValue;
		this.durValue = durValue;
		this.lastUpdate = new Date();
	}
	public RealDistance(){
		
	}
	public Date getLastUpdate(){
		return this.lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate){
		this.lastUpdate = lastUpdate;
	}
	
	@PreUpdate
	protected void onUpdate() {
		lastUpdate = new Date();
	}
	@PrePersist
	protected void onCreate() {
		lastUpdate = new Date();
	}
	
	@Override
	public String toString(){
		return "RealDistance{" +
				"row=" + row +
				", column=" + col +
				", distValue=" + distValue +
				", durValue=" + durValue +
				'}';
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRow() {
		return row;
	}
	public void setRow(Long row) {
		this.row = row;
	}
	public Long getCol() {
		return col;
	}
	public void setCol(Long col) {
		this.col = col;
	}
	public double getDistValue() {
		return distValue;
	}
	public void setDistValue(double distValue) {
		this.distValue = distValue;
	}
	public double getDurValue() {
		return durValue;
	}
	public void setDurValue(double durValue) {
		this.durValue = durValue;
	}
}