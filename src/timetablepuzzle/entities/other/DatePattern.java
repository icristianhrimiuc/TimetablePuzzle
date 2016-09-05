package timetablepuzzle.entities.other;

import javax.persistence.*;

@Entity
@Table(name="date_patterns")
public class DatePattern{
	private static final String DateSeparator = ";";
	
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
	
	public String getDates()
	{
		return this.dates;
	}
	
	public void setDates(String dates)
	{
		this.dates = dates;
	}
	
	/************Methods that model the class behavior**************/
	public void addDateInterval(String dateInterval)
	{
		this.dates = this.dates + dateInterval + DateSeparator;
	}

	@Override
	public String toString() {
		return String.format("%s", this.name);
	}

	@Override
	public boolean equals(Object o) {
		boolean equals = (o instanceof DatePattern);
		if (equals) {
			DatePattern other = (DatePattern) o;
			equals = ((this.id == other.getId()) && 
					(this.name.equals(other.getName())) && 
					(this.dates.equals(other.getDates()))
					);
		}
		
		return equals;
	}

	@Override
	public int hashCode() {
		return String.format("DatePattern:%s:%s", Integer.toString(this.id), this.toString()).hashCode();
	}
}
