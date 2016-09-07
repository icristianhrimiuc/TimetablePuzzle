package timetablepuzzle.entities.other;

import javax.persistence.*;

@Entity
@Table(name = "locations")
public class Location {
	/*********** Regular properties *************/
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "address")
	private String address;

	@Column(name = "longitude")
	private double longitude;

	@Column(name = "latitude")
	private double latitude;

	public Location() {
		this(0, "No Address", 0, 0);
	}

	public Location(int id, String address, double latitude, double longitude) {
		this.id = id;
		this.address = address;
		this.setLatitude(latitude);
		this.setLongitude(longitude);
	}

	/*********** Getters and setters *****************/

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

	/****************
	 * Methods that model the class behavior
	 ********************/
	public double DistanceTo(Location that) {
		int meanEarthRadiusInKm = 6371;
		double dLat = Math.toRadians(Math.abs(this.latitude - that.latitude));
		double dLon = Math.toRadians(Math.abs(this.longitude - that.longitude));
		double lat1 = Math.toRadians(this.latitude);
		double lat2 = Math.toRadians(that.latitude);

		// Great circle distance in radians, using the harvesine formula
		// as it is numerically better-conditioned for small distances
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return meanEarthRadiusInKm * c;
	}

	@Override
	public String toString() {
		return String.format("%s( %.6f , %.6f )", this.address, this.latitude, this.longitude);
	}

	@Override
	public boolean equals(Object o) {
		boolean equals = (o instanceof Location);
		if (equals) {
			Location other = (Location) o;
			equals = ((this.id == other.getId()) && (this.address.equals(other.getAddress()))
					&& (this.latitude == other.getLatitude()) && (this.longitude == other.getLongitude()));
		}

		return equals;
	}

	@Override
	public int hashCode() {
		return String.format("Location:%s:%s", Integer.toString(this.id), this.toString()).hashCode();
	}
}
