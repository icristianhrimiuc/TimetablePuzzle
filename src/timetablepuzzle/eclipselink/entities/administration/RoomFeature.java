package timetablepuzzle.eclipselink.entities.administration;

import javax.persistence.*;

import timetablepuzzle.eclipselink.entities.E;

@Entity
@Table(name="room_features")
public class RoomFeature extends E{	
	@Column(name="feature")
	private String _feature;
	
	/**
	 * Default Constructor
	 */
	public RoomFeature()
	{
		this(0,"NoFeature");
	}
	
	/**
	 * Parameterized Constructor
	 * @param externalId
	 * @param feature
	 */
	public RoomFeature(int externalId, String feature)
	{
		set_externalId(externalId);
		set_feature(feature);
	}
	
	/******************Getters and setters****************/
	public void set_externalId(int _externalId) {
		this._externalId = _externalId;
	}

	public String get_feature() {
		return _feature;
	}

	public void set_feature(String _feature) {
		this._feature = _feature;
	}	
}
