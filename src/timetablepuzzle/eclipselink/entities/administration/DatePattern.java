package timetablepuzzle.eclipselink.entities.administration;

import javax.persistence.*;

@Entity
@Table(name="date_patterns")
public class DatePattern{
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="dates")
	private String dates;
	
	public DatePattern()
	{
		this(0,"NoName","");
	}
	
	public DatePattern(int id, String name, String dates)
	{
		this.id = id;
		setName(name);
		setDates(dates);
	}
	
	/**************Getters and setters******************/
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setDates(String dates)
	{
		this.dates = dates;
	}
	
	/************Methods that model the class behavior**************/
	public void addDateInterval(String dateInterval)
	{
		this.dates = this.dates + dateInterval + ";";
	}
	
	public String[] getDates()
	{
		return this.dates.split(";");
	}
}
