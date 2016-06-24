package timetablepuzzle.eclipselink.entities.administration;

import java.util.List;

public class SubjectArea {
	private int _externalId;
	private String _name;
	private List<Curricula> _studyTerms;
	
	public SubjectArea(int externalId, String name, List<Curricula> studyTerms)
	{
		_externalId = externalId;
		set_name(name);
		set_studyTerms(studyTerms);
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

	public List<Curricula> get_studyTerms() {
		return _studyTerms;
	}

	public void set_studyTerms(List<Curricula> _studyTerms) {
		this._studyTerms = _studyTerms;
	}
}
