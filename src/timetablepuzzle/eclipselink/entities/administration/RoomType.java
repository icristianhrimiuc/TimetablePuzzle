package timetablepuzzle.eclipselink.entities.administration;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="room_types")
public class RoomType {
	@Id
	@Column(name="external_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int _externalId;
	
	@Column(name="name")
	private String _name;
	
	@Column(name="min_capacity")
	private int _minCapacity;
	
	@Column(name="max_capacity")
	private int _maxCapacity;
	
	@OneToMany(targetEntity=RoomFeature.class)
	@JoinTable(name="roomType_roomFeatures",
    joinColumns=
         @JoinColumn(name="roomType_external_id"),
    inverseJoinColumns=
         @JoinColumn(name="roomFeature_external_id"))
	private List<RoomFeature> _roomFeatures;
	
	public RoomType()
	{
		this(0,"NoName",0,-1,0,null);
	}
	/**
	 * Parameterized constructor
	 * @param externalId
	 * @param name
	 * @param minCapacity
	 * @param type
	 * @param maxCapacity
	 * @param roomFeatures
	 */
	public RoomType(int externalId, String name, int minCapacity, int type,
			int maxCapacity, List<RoomFeature> roomFeatures)
	{
		_externalId = externalId;
		set_name(name);
		set_minCapacity(minCapacity);
		set_maxCapacity(maxCapacity);
		set_roomFeatures(roomFeatures);
	}

	public int get_externalId() {
		return _externalId;
	}

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public int get_minCapacity() {
		return _minCapacity;
	}

	public void set_minCapacity(int _minCapacity) {
		this._minCapacity = _minCapacity;
	}

	public int get_maxCapacity() {
		return _maxCapacity;
	}

	public void set_maxCapacity(int _maxCapacity) {
		this._maxCapacity = _maxCapacity;
	}

	public List<RoomFeature> get_roomFeatures() {
		return _roomFeatures;
	}

	public void set_roomFeatures(List<RoomFeature> _roomFeatures) {
		this._roomFeatures = _roomFeatures;
	}
}
