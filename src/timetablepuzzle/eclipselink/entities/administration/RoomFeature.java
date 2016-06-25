package timetablepuzzle.eclipselink.entities.administration;

import javax.persistence.*;

@Entity
@Table(name="room_features")
public class RoomFeature {
	@Id
	@Column(name="external_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int _externalId;
	
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

	public int get_externalId() {
		return _externalId;
	}

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
