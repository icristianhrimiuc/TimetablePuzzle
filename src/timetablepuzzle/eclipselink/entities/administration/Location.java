package timetablepuzzle.eclipselink.entities.administration;

import javax.persistence.*;

@Entity
@Table(name="locations")
public class Location{
	/***********Regular properties*************/
	@Id
	@Column(name="external_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int _externalId;
	
	@Column(name="longitude")
    private double _longitude;
	
	@Column(name="latitude")
    private double _latitude;
   
    public Location()
    {
    	this(0,0,0);
    }
    /**
     * Parameterized constructor. Create and initialize a point with given 
     * (latitude, longitude) specified in degrees
     * @param latitude
     * @param longitude
     */
    public Location(int externalId, double latitude, double longitude) {
    	this._externalId = externalId;
        this.set_latitude(latitude);
        this.set_longitude(longitude);
    }
    
    /***********Getters and setters*****************/

	public int get_externalId() {
		return _externalId;
	}
	
	public double get_longitude() {
		return _longitude;
	}

	public void set_longitude(double _longitude) {
		this._longitude = _longitude;
	}

	public double get_latitude() {
		return _latitude;
	}

	public void set_latitude(double _latitude) {
		this._latitude = _latitude;
	}
	/****************Methods that model the class behavior********************/
    /**
     * Return distance between this location and that location
     * measured in kilometers
     * @param that
     * @return
     */
    public double distanceTo(Location that) {
        int meanEarthRadius = 6371; // km
        double dLat = Math.toRadians(Math.abs(this._latitude - that._latitude));
        double dLon = Math.toRadians(Math.abs(this._longitude - that._longitude));
        double lat1 = Math.toRadians(this._latitude);
        double lat2 = Math.toRadians(that._latitude);

        // Great circle distance in radians, using the harvesine formula
        // as it is numerically better-conditioned for small distances
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2); 
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        
        return meanEarthRadius * c;
    }

    /**
     * Return string representation of this point
     */
    public String toString() {
        return " (" + _latitude + ", " + _longitude + ")";
    }
}
