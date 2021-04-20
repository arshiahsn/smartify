package smartcity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;


@Entity
public class Location{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private double latitude; 
	private double longitude;

	public Location(){

	}
	public Location(double latitude, double longitude) { 
		this.latitude = latitude; 
		this.longitude = longitude; 
	}
	public void setLatitude(double latitude){
		this.latitude = latitude;
	}
	public double getLatitude(){
		return this.latitude;
	}
	public void setLongitude(double longitude){
		this.longitude = longitude;
	}
	public double getLongitude(){
		return this.longitude;
	}	  

	@Override
	public String toString(){
		return "Location{" +
				"latitude=" + latitude +
				", longitude=" + longitude +
				'}';
	}
}
