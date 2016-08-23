package timetablepuzzle.eclipselink.entities.administration;

import java.util.HashMap;
import javax.persistence.*;

@Entity
@Table(name="subject_areas")
public class SubjectArea{
	public static enum Term{FIRST,SECOND,THIRD,FOURTH,UNASSIGNED};
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="name")
	private String name;
	
	@OneToMany(cascade=CascadeType.ALL,targetEntity=Curricula.class)
	@JoinTable(name="subjectarea_curriculas",
    joinColumns=
         @JoinColumn(name="subjectarea_id"),
    inverseJoinColumns=
         @JoinColumn(name="curricula_id"))
	private HashMap<Term,Curricula> curriculasToStudyByTerm;
	
	public SubjectArea()
	{
		this(0,"NoName",new HashMap<Term,Curricula>());
	}

	public SubjectArea(int id, String name, HashMap<Term,Curricula> curriculasToStudyByTerm)
	{
		this.id = id;
		setName(name);
		this.curriculasToStudyByTerm = curriculasToStudyByTerm;
	}
	
	/********************Getters and setters****************/
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Curricula getCurriculaToStudyByTerm(Term term) {
		return curriculasToStudyByTerm.get(term);
	}	
	
	public void setCurriculaToStudyByTerm(Term term, Curricula curriculasToStudyByTerm)
	{
		this.curriculasToStudyByTerm.put(term, curriculasToStudyByTerm);
	}
	
	/*******************Methods that model the class behavior*******************/
}
