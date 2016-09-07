package timetablepuzzle.entities.other;

import javax.persistence.*;

@Entity
@Table(name = "room_features")
public class RoomFeature {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "feature")
	private String feature;

	public RoomFeature() {
		this(0, "NoFeature");
	}

	public RoomFeature(int id, String feature) {
		this.id = id;
		setFeature(feature);
	}

	/****************** Getters and setters ****************/
	public int getId() {
		return id;
	}

	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}

	/************************
	 * Methods that model the class behavior
	 ************************/
	@Override
	public String toString() {
		return this.feature;
	}

	@Override
	public boolean equals(Object o) {
		boolean equals = (o instanceof RoomFeature);
		if (equals) {
			RoomFeature other = (RoomFeature) o;
			equals = ((this.id == other.getId()) && (this.feature.equals(other.getFeature())));
		}

		return equals;
	}

	@Override
	public int hashCode() {
		return String.format("RoomFeature:%s:%s", Integer.toString(this.id), this.toString()).hashCode();
	}
}
