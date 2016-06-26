package timetablepuzzle.eclipselink.entities;

import javax.persistence.*;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class E {
	// Empty abstract class to be inherited by all entities 
	@Id
	@Column(name="external_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int _externalId;
	
	/***********************Getters and setters*******************/
	public int get_externalId() {
		return _externalId;
	}
}
