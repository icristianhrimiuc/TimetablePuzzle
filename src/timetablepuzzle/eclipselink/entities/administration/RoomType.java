package timetablepuzzle.eclipselink.entities.administration;

import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="room_types")
public class RoomType{
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
		
	@Column(name="name")
	private String name;
	
	@Column(name="mincapacity")
	private int minCapacity;
	
	@Column(name="maxcapacity")
	private int maxCapacity;
	
	@OneToMany(cascade=CascadeType.ALL,targetEntity=RoomFeature.class)
	@JoinTable(name="roomType_roomFeatures",
    joinColumns=
         @JoinColumn(name="roomType_id"),
    inverseJoinColumns=
         @JoinColumn(name="roomFeature_id"))
	private List<RoomFeature> roomFeatures;
	
	public RoomType()
	{
		this(0,"NoName",0,-1,0,null);
	}

	public RoomType(int id, String name, int minCapacity, int type,
			int maxCapacity, List<RoomFeature> roomFeatures)
	{
		this.id = id;
		setName(name);
		setMinCapacity(minCapacity);
		setMaxCapacity(maxCapacity);
		setRoomFeatures(roomFeatures);
	}

	/**************Getters and setters*****************/

	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String _name) {
		this.name = _name;
	}

	public int getMinCapacity() {
		return minCapacity;
	}

	public void setMinCapacity(int _minCapacity) {
		this.minCapacity = _minCapacity;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(int _maxCapacity) {
		this.maxCapacity = _maxCapacity;
	}

	public List<RoomFeature> getRoomFeatures() {
		return roomFeatures;
	}

	public void setRoomFeatures(List<RoomFeature> _roomFeatures) {
		this.roomFeatures = _roomFeatures;
	}
}
