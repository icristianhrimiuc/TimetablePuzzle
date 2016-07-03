package timetablepuzzle.eclipselink.entities.administration;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name="subject_areas")
public class SubjectArea{
	/***********Static fields*************/
	public static enum Term{FIRST,SECOND,THIRD,FOURTH,UNASSIGNED};
	/***********Regular fields*************/	
	@Id
	@Column(name="external_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected int _externalId;
	
	@Column(name="name")
	private String _name;
	
	@OneToMany(cascade=CascadeType.ALL,targetEntity=Curricula.class)
	@JoinTable(name="subjectArea_curriculas",
    joinColumns=
         @JoinColumn(name="subjectArea_external_id"),
    inverseJoinColumns=
         @JoinColumn(name="curricula_external_id"))
	private List<Curricula> _studyTerms;
	
	public SubjectArea()
	{
		this(0,"NoName",new ArrayList<Curricula>());
	}
	
	/**
	 * Parameterized constructor
	 * @param externalId
	 * @param name
	 * @param studyTerms
	 */
	public SubjectArea(int externalId, String name, List<Curricula> studyTerms)
	{
		_externalId = externalId;
		set_name(name);
		_studyTerms = studyTerms;
	}
	
	/********************Getters and setters****************/

	public int get_externalId() {
		return _externalId;
	}
	
	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public List<Curricula> get_studyTerms() {
		return _studyTerms;
	}

	public void set_studyTerms(List<Curricula> _studyTerms) {
		this._studyTerms = _studyTerms;
	}
	/*******************Methods that model the class behavior*******************/
}
