package timetablepuzzle.eclipselink.entities.administration;

import javax.persistence.*;

@Entity
@Table(name="room_features")
public class RoomFeature{
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="feature")
	private String feature;

	public RoomFeature()
	{
		this(0,"NoFeature");
	}

	public RoomFeature(int id, String feature)
	{
		this.id = id;
		setFeature(feature);
	}
	
	/******************Getters and setters****************/
	public int getId() {
		return id;
	}
	public String getFeature() {
		return feature;
	}

	public void setFeature(String feature) {
		this.feature = feature;
	}	
}
