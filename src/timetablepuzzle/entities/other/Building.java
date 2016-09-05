package timetablepuzzle.entities.other;

import javax.persistence.*;

@Entity
@Table(name="buildings")
public class Building{
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="abbreviation")
	private String abbreviation;

	@OneToOne(cascade=CascadeType.ALL,optional=false)
	@JoinColumn(name="location_id", unique=true, nullable=false, updatable=false)
	private Location location;
	
	public Building()
	{
		this(0,"NoName","NoAbbreviation",new Location());
	}
	
	public Building(int id, String name, String abbreviation, Location location)
	{
		this.id = id;
		setName(name);
		setAbbreviation(abbreviation);
		setLocation(location);
	}
	
	/****************Getters and setters***************/
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	/**********************Methods that model the class behavior*****************************/
	@Override
	public String toString() {
		return String.format("%s(%s)", this.name, this.abbreviation);
	}

	@Override
	public boolean equals(Object o) {
		boolean equals = (o instanceof Building);
		if (equals) {
			Building other = (Building) o;
			equals = ((this.id == other.getId()) && 
					(this.name.equals(other.getName())) && 
					(this.abbreviation.equals(other.getAbbreviation())) && 
					(this.location.equals(other.getLocation()))
					);
		}
		
		return equals;
	}

	@Override
	public int hashCode() {
		return String.format("Building:%s:%s", Integer.toString(this.id), this.toString()).hashCode();
	}	
}