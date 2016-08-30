package timetablepuzzle.eclipselink.entities.administration;

import javax.persistence.*;

@Entity
@Table(name="locations")
public class Location{
	/***********Regular properties*************/
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="address")
	private String address;
	
	@Column(name="longitude")
    private double longitude;
	
	@Column(name="latitude")
    private double latitude;
   
    public Location()
    {
    	this(0,"No Address",0,0);
    }

    public Location(int id, String address, double latitude, double longitude) {
    	this.id = id;
    	this.address = address;
        this.setLatitude(latitude);
        this.setLongitude(longitude);
    }
    
    /***********Getters and setters*****************/

	public int getId() {
		return this.id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	/****************Methods that model the class behavior********************/
    public double DistanceTo(Location that) {
        int meanEarthRadiusInKm = 6371;
        double dLat = Math.toRadians(Math.abs(this.latitude - that.latitude));
        double dLon = Math.toRadians(Math.abs(this.longitude - that.longitude));
        double lat1 = Math.toRadians(this.latitude);
        double lat2 = Math.toRadians(that.latitude);

        // Great circle distance in radians, using the harvesine formula
        // as it is numerically better-conditioned for small distances
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2); 
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        
        return meanEarthRadiusInKm * c;
    }

    public String toString() {
        return " (" + latitude + ", " + longitude + ")";
    }
}
