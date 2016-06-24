package timetablepuzzle.eclipselink.entities.inputdata;

public class CourseOffering {
	private int _externalId;
	private String _abbreviation;
	private String _title;
	private Offering _lecture;
	private Offering _individualMeetings;
	
	public CourseOffering(int externalId, String abbreviation,
			String title, Offering lecture, Offering individualMeetings)
	{
		_externalId = externalId;
		set_abbreviation(abbreviation);
		set_title(title);
		set_lecture(lecture);
		_individualMeetings = individualMeetings;
	}

	public int get_externalId() {
		return _externalId;
	}

	public String get_abbreviation() {
		return _abbreviation;
	}

	public void set_abbreviation(String _abbreviation) {
		this._abbreviation = _abbreviation;
	}

	public String get_title() {
		return _title;
	}

	public void set_title(String _title) {
		this._title = _title;
	}

	public Offering get_lecture() {
		return _lecture;
	}

	public void set_lecture(Offering _lecture) {
		this._lecture = _lecture;
	}

	public Offering get_individualMeetings() {
		return _individualMeetings;
	}
}
