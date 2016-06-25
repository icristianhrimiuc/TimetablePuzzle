package timetablepuzzle.eclipselink.entities.administration;

import javax.persistence.*;

@Entity
@Table(name="buildings")
public class Building {
	@Id
	@Column(name="external_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int _externalId;
	
	@Column(name="name")
	private String _name;
	
	@Column(name="abreviation")
	private String _abreviation;

	@OneToOne(optional=false)
	@JoinColumn(name="location", unique=true, nullable=false, updatable=false)
	private Location _location;
	
	/**
	 * Default constructor
	 */
	public Building()
	{
		this(0,"NoName","NoAbbreviation",new Location());
	}
	
	/**
	 * Create and initialize a building with given parameters
	 * @param externalID
	 * @param name
	 * @param abreviation
	 * @param location
	 */
	public Building(int externalID, String name, String abreviation, Location location)
	{
		_externalId = externalID;
		set_name(name);
		set_abreviation(abreviation);
		if(location != null)
		{
			set_location(location);
		}else{
			set_location(new Location());
		}
	}
	/****************Getters ans setters***************/
	public int get_externalId() {
		return _externalId;
	}

	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public String get_abreviation() {
		return _abreviation;
	}

	public void set_abreviation(String _abreviation) {
		this._abreviation = _abreviation;
	}

	public Location get_location() {
		return _location;
	}

	public void set_location(Location _location) {
		this._location = _location;
	}
}
