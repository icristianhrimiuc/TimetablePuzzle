package resources;

import java.util.ArrayList;
import java.util.List;

public class OfferingsCreateGenerator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("OfferingDAO offeringDAO = new OfferingJPADAOService();\n");
		System.out.println("CourseOfferingDAO courseOfferingDAO = new CourseOfferingJPADAOService();\n");

		List<String> instructors = new ArrayList<String>();
		instructors.add("AlexandrescuAdrian");
		List<String> rooms = new ArrayList<String>();
		rooms.add("roomC211");

		String lecture;
		String meetings;
		// Anul 4 Sem 1
		lecture = createOffering("Inteligenta Artificiala", 2, "LECTURE", "FULL_TERM", "LeonFlorin", "roomAC02");
		meetings = createOffering("Inteligenta Artificiala", 2, "LABORATORY", "FULL_TERM", "CampanuCorina", "roomC06");
		createCourseOffering("Inteligenta Artificiala", "IA", lecture, meetings);

		lecture = createOffering("Ingineria Programarii", 2, "LECTURE", "FULL_TERM", "LeonFlorin", "roomAC02");
		meetings = createOffering("Ingineria Programarii", 2, "LABORATORY", "FULL_TERM", "CampanuCorina", "roomC06");
		createCourseOffering("Ingineria Programarii", "IP", lecture, meetings);

		lecture = createOffering("Algoritmi Paraleli si Distribuiti", 2, "LECTURE", "FULL_TERM", "CrausMitica",
				"roomAC02");
		meetings = createOffering("Algoritmi Paraleli si Distribuiti", 2, "LABORATORY", "FULL_TERM",
				"AlexandrescuAdrian", "roomC14");
		createCourseOffering("Algoritmi Paraleli si Distribuiti", "AlPD", lecture, meetings);

		lecture = createOffering("Programarea Clientului Web", 2, "LECTURE", "FULL_TERM", "CaraimanSimona", "roomAC21");
		meetings = createOffering("Programarea Clientului Web", 2, "LABORATORY", "FULL_TERM", "GavrilescuMarius",
				"roomC01");
		createCourseOffering("Programarea Clientului Web", "PCW", lecture, meetings);

		lecture = createOffering("Structuri multiprocesor", 2, "LECTURE", "FULL_TERM", "StanAndrei", "roomAC21");
		meetings = createOffering("Structuri multiprocesor", 2, "LABORATORY", "FULL_TERM", "StanAndrei", "roomC214");
		createCourseOffering("Structuri multiprocesor", "SM", lecture, meetings);

		// Anul 4 sem 2
		lecture = createOffering("Limbaje formale si translatoare", 2, "LECTURE", "FULL_TERM", "CrausMitica",
				"roomAC02");
		meetings = createOffering("Limbaje formale si translatoare", 2, "LABORATORY", "FULL_TERM", "CrausMitica",
				"roomC04");
		createCourseOffering("Limbaje formale si translatoare", "LFT", lecture, meetings);

		lecture = createOffering("Regasirea Informatiilor pe Web", 2, "LECTURE", "FULL_TERM", "ArchipAlexandru",
				"roomAC02");
		meetings = createOffering("Regasirea Informatiilor pe Web", 2, "LABORATORY", "FULL_TERM", "ArchipAlexandru",
				"roomC14");
		createCourseOffering("Regasirea Informatiilor pe Web", "RIW", lecture, meetings);

		lecture = createOffering("Baze de date orientate obiect", 2, "LECTURE", "FULL_TERM", "GavrilescuMarius",
				"roomAC02");
		meetings = createOffering("Baze de date orientate obiect", 2, "LABORATORY", "FULL_TERM", "GavrilescuMarius",
				"roomC06");
		createCourseOffering("Baze de date orientate obiect", "BDOO", lecture, meetings);

		lecture = createOffering("Sisteme de timp real", 2, "LECTURE", "FULL_TERM", "CascavalPetru", "roomAC21");
		meetings = createOffering("Sisteme de timp real", 2, "LABORATORY", "FULL_TERM", "CascavalPetru", "roomC212");
		createCourseOffering("Sisteme de timp real", "STR", lecture, meetings);

		// Anul 3 sem 1
		lecture = createOffering("Elemente de grafica asistata pe calculator", 2, "LECTURE", "FULL_TERM", "MantaVasile",
				"roomAC02");
		meetings = createOffering("Elemente de grafica asistata pe calculator", 2, "LABORATORY", "FULL_TERM",
				"HerghelegiuPaul", "roomC213");
		createCourseOffering("Elemente de grafica asistata pe calculator", "EGC", lecture, meetings);

		lecture = createOffering("Modelare si simulare", 2, "LECTURE", "FULL_TERM", "CascavalPetru", "roomAC02");
		meetings = createOffering("Modelare si simulare", 2, "LABORATORY", "FULL_TERM", "CascavalPetru", "roomC212");
		createCourseOffering("Modelare si simulare", "MS", lecture, meetings);

		lecture = createOffering("Achizitionarea si prelucrarea datelor", 2, "LECTURE", "FULL_TERM", "UngureanuFlorina",
				"roomAC02");
		meetings = createOffering("Achizitionarea si prelucrarea datelor", 2, "LABORATORY", "FULL_TERM",
				"UngureanuFlorina", "roomC33");
		createCourseOffering("Achizitionarea si prelucrarea datelor", "APD", lecture, meetings);

		lecture = createOffering("Tehnologia Informatiei", 2, "LECTURE", "FULL_TERM", "AlexandrescuAdrian", "roomAC02");
		meetings = createOffering("Tehnologia Informatiei", 2, "LABORATORY", "FULL_TERM", "AlexandrescuAdrian",
				"roomC211");
		createCourseOffering("Tehnologia Informatiei", "TI", lecture, meetings);

		// Anul 3 sem 2
		lecture = createOffering("Retele de calculatoare", 2, "LECTURE", "FULL_TERM", "BotezatuNicolae", "roomAC02");
		meetings = createOffering("Retele de calculatoare", 2, "LABORATORY", "FULL_TERM", "BotezatuNicolae", "roomC03");
		createCourseOffering("Retele de calculatoare", "RC", lecture, meetings);

		lecture = createOffering("Baze de date", 2, "LECTURE", "FULL_TERM", "ButincuCristian", "roomAC02");
		meetings = createOffering("Baze de date", 2, "LABORATORY", "FULL_TERM", "ButincuCristian", "roomC13");
		createCourseOffering("Baze de date", "BD", lecture, meetings);

		lecture = createOffering("Sisteme de prelucrare grafica", 2, "LECTURE", "FULL_TERM", "MantaVasile", "roomAC02");
		meetings = createOffering("Sisteme de prelucrare grafica", 2, "LABORATORY", "FULL_TERM", "HerghelegiuPaul",
				"roomC213");
		createCourseOffering("Sisteme de prelucrare grafica", "SPG", lecture, meetings);

		lecture = createOffering("Proiectarea structurilor logice programabile", 2, "LECTURE", "FULL_TERM",
				"MonorCatalin", "roomAC21");
		meetings = createOffering("Proiectarea structurilor logice programabile", 2, "LABORATORY", "FULL_TERM",
				"MonorCatalin", "roomC28");
		createCourseOffering("Proiectarea structurilor logice programabile", "PSLP", lecture, meetings);

		lecture = createOffering("Proiectarea cu microprocesoare", 2, "LECTURE", "FULL_TERM", "PantelimonescuFlorin",
				"roomAC21");
		meetings = createOffering("Proiectarea cu microprocesoare", 2, "LABORATORY", "FULL_TERM",
				"PantelimonescuFlorin", "roomC214");
		createCourseOffering("Proiectarea cu microprocesoare", "PM", lecture, meetings);

		// Anul 2 sem 1
		lecture = createOffering("Structuri de date", 2, "LECTURE", "FULL_TERM", "UngureanuFlorina", "roomAC02");
		meetings = createOffering("Structuri de date", 2, "LABORATORY", "FULL_TERM", "UngureanuFlorina", "roomC04");
		createCourseOffering("Structuri de date", "SD", lecture, meetings);

		lecture = createOffering("Programare orientata pe obiecte", 2, "LECTURE", "FULL_TERM", "LupuRobert",
				"roomAC02");
		meetings = createOffering("Programare orientata pe obiecte", 2, "LABORATORY", "FULL_TERM", "LupuRobert",
				"roomC04");
		createCourseOffering("Programare orientata pe obiecte", "POO", lecture, meetings);

		lecture = createOffering("Teoria sistemelor", 2, "LECTURE", "FULL_TERM", "KloetzerMarius", "roomAC02");
		meetings = createOffering("Teoria sistemelor", 2, "LABORATORY", "FULL_TERM", "KloetzerMarius", "roomA113");
		createCourseOffering("Teoria sistemelor", "TS", lecture, meetings);

		// Anul 2 sem 2
		lecture = createOffering("Arhitectura sistemelor de calcul", 3, "LECTURE", "FULL_TERM", "StanAndrei", "roomT4");
		meetings = createOffering("Arhitectura sistemelor de calcul", 2, "LABORATORY", "FULL_TERM", "StanAndrei",
				"roomC33");
		createCourseOffering("Arhitectura sistemelor de calcul", "ASC", lecture, meetings);

		lecture = createOffering("Proiectarea algoritmilor", 3, "LECTURE", "FULL_TERM", "CrausMitica", "roomT4");
		meetings = createOffering("Proiectarea algoritmilor", 2, "LABORATORY", "FULL_TERM", "CrausMitica", "roomC14");
		createCourseOffering("Proiectarea algoritmilor", "PA", lecture, meetings);

		// Anul 1 sem 1
		lecture = createOffering("Matematica discreta", 2, "LECTURE", "FULL_TERM", "SerbanElena", "roomT4");
		meetings = createOffering("Matematica discreta", 2, "LABORATORY", "FULL_TERM", "SerbanElena", "roomC03");
		createCourseOffering("Matematica discreta", "MD", lecture, meetings);

		lecture = createOffering("Algebra si analiza matematica I", 2, "LECTURE", "FULL_TERM", "StrugariuRadu",
				"roomE1");
		meetings = createOffering("Algebra si analiza matematica I", 2, "SEMINARY", "FULL_TERM", "StrugariuRadu",
				"roomAC03");
		createCourseOffering("Algebra si analiza matematica I", "AAMI", lecture, meetings);

		lecture = createOffering("Algebra si analiza matematica II", 2, "LECTURE", "FULL_TERM", "PleteaAriadna",
				"roomE1");
		meetings = createOffering("Algebra si analiza matematica II", 2, "SEMINARY", "FULL_TERM", "PleteaAriadna",
				"roomAC03");
		createCourseOffering("Algebra si analiza matematica II", "AAMII", lecture, meetings);

		lecture = createOffering("Grafica asistata pe calculator", 2, "LECTURE", "FULL_TERM", "TomaAnaMaria", "roomE1");
		meetings = createOffering("Grafica asistata pe calculator", 2, "LABORATORY", "FULL_TERM", "TomaAnaMaria",
				"roomC04");
		createCourseOffering("Grafica asistata pe calculator", "GAC", lecture, meetings);

		// Anul 1 sem 2
		lecture = createOffering("Programarea calculatoarelor", 2, "LECTURE", "FULL_TERM", "SerbanElena", "roomE1");
		meetings = createOffering("Programarea calculatoarelor", 2, "LABORATORY", "FULL_TERM", "SerbanElena",
				"roomC02");
		createCourseOffering("Programarea calculatoarelor", "PC", lecture, meetings);

		lecture = createOffering("Metode numerice", 2, "LECTURE", "FULL_TERM", "MireaLetitia", "roomE1");
		meetings = createOffering("Metode numerice", 2, "LABORATORY", "FULL_TERM", "MireaLetitia", "roomA19");
		createCourseOffering("Metode numerice", "MN", lecture, meetings);

		lecture = createOffering("Fizica", 3, "LECTURE", "FULL_TERM", "CiobanuBrandusa", "roomE1");
		meetings = createOffering("Fizica", 2, "LABORATORY", "FULL_TERM", "CiobanuBrandusa", "roomA19");
		createCourseOffering("Fizica", "Fizica", lecture, meetings);

		// curriculas
		List<String> an4sem1CurriculaC = new ArrayList<String>();
		an4sem1CurriculaC.add("AlPD");
		an4sem1CurriculaC.add("IA");
		an4sem1CurriculaC.add("IP");
		an4sem1CurriculaC.add("PCW");
		List<String> an4sem1CurriculaTI = new ArrayList<String>();
		an4sem1CurriculaTI.add("AlPD");
		an4sem1CurriculaTI.add("IA");
		an4sem1CurriculaTI.add("SM");
		List<String> an4sem2CurriculaC = new ArrayList<String>();
		an4sem2CurriculaC.add("LFT");
		an4sem2CurriculaC.add("STR");
		List<String> an4sem2CurriculaTI = new ArrayList<String>();
		an4sem2CurriculaTI.add("LFT");
		an4sem2CurriculaTI.add("RIW");
		an4sem2CurriculaTI.add("BDOO");

		List<String> an3sem1CurriculaC = new ArrayList<String>();
		an3sem1CurriculaC.add("EGC");
		an3sem1CurriculaC.add("MS");
		an3sem1CurriculaC.add("APD");
		List<String> an3sem1CurriculaTI = new ArrayList<String>();
		an3sem1CurriculaTI.add("EGC");
		an3sem1CurriculaTI.add("MS");
		an3sem1CurriculaTI.add("APD");
		an3sem1CurriculaC.add("TI");

		List<String> an3sem2CurriculaC = new ArrayList<String>();
		an3sem2CurriculaC.add("RC");
		an3sem2CurriculaC.add("BD");
		an3sem2CurriculaC.add("PSLP");
		an3sem2CurriculaC.add("PM");
		List<String> an3sem2CurriculaTI = new ArrayList<String>();
		an3sem2CurriculaTI.add("RC");
		an3sem2CurriculaTI.add("BD");
		an3sem2CurriculaTI.add("SPG");

		List<String> an2sem1CurriculaCTI = new ArrayList<String>();
		an2sem1CurriculaCTI.add("SD");
		an2sem1CurriculaCTI.add("POO");
		an2sem1CurriculaCTI.add("TS");
		List<String> an2sem2CurriculaCTI = new ArrayList<String>();
		an2sem2CurriculaCTI.add("ASC");
		an2sem2CurriculaCTI.add("PA");

		List<String> an1sem1CurriculaCTI = new ArrayList<String>();
		an1sem1CurriculaCTI.add("MD");
		an1sem1CurriculaCTI.add("AAMI");
		an1sem1CurriculaCTI.add("AAMII");
		an1sem1CurriculaCTI.add("GAC");
		List<String> an1sem2CurriculaCTI = new ArrayList<String>();
		an1sem2CurriculaCTI.add("PC");
		an1sem2CurriculaCTI.add("MN");
		an1sem2CurriculaCTI.add("Fizica");

		System.out.println("CurriculaDAO curriculaDAO = new CurriculaJPADAOService();\n");
		createCurriculas("AnulPatruSemIC", "FOURTH", "FIRST", an4sem1CurriculaC);
		createCurriculas("AnulPatruSemITI", "FOURTH", "FIRST", an4sem1CurriculaTI);
		createCurriculas("AnulPatruSemIIC", "FOURTH", "SECOND", an4sem2CurriculaC);
		createCurriculas("AnulPatruSemIITI", "FOURTH", "SECOND", an4sem2CurriculaTI);
		createCurriculas("AnulTreiSemIC", "THIRD", "FIRST", an3sem1CurriculaC);
		createCurriculas("AnulTreiSemITI", "THIRD", "FIRST", an3sem1CurriculaTI);
		createCurriculas("AnulTreiSemIIC", "THIRD", "SECOND", an3sem2CurriculaC);
		createCurriculas("AnulTreiSemIITI", "THIRD", "SECOND", an3sem2CurriculaTI);
		createCurriculas("AnulDoiSemICTI", "SECOND", "FIRST", an2sem1CurriculaCTI);
		createCurriculas("AnulDoiSemIICTI", "SECOND", "SECOND", an2sem2CurriculaCTI);
		createCurriculas("AnulIntaiSemICTI", "FIRST", "FIRST", an1sem1CurriculaCTI);
		createCurriculas("AnulIntaiSemIICTI", "FIRST", "SECOND", an1sem2CurriculaCTI);
		
		
		// Subject areas
		System.out.println("SubjectAreaDAO subjectAreaDAO = new SubjectAreaJPADAOService();\n");
		createSubjectArea("AnulPatruC","C-IV","AnulPatruSemIC","AnulPatruSemIIC");
		createSubjectArea("AnulPatruTI","TI-IV","AnulPatruSemITI","AnulPatruSemIITI");

		createSubjectArea("AnulTreiC","C-III","AnulTreiSemIC","AnulTreiSemIIC");
		createSubjectArea("AnulTreiTI","TI-III","AnulTreiSemITI","AnulTreiSemIITI");

		createSubjectArea("AnulDoiCTI","CTI-II","AnulDoiSemICTI","AnulDoiSemIICTI");
		
		createSubjectArea("AnulIntaiCTI","CTI-I","AnulIntaiSemICTI","AnulIntaiSemIICTI");
		
		// Years Of Study
		System.out.println("YearOfStudyDAO yearOfStudyDAO = new YearOfStudyJPADAOService();\n");
		List<String> anulPatru = new ArrayList<String>();
		anulPatru.add("AnulPatruC");
		anulPatru.add("AnulPatruTI");
		createYearOfStudy("AnulPatru" , "FOURTH", anulPatru);
		
		List<String> anulTrei = new ArrayList<String>();
		anulTrei.add("AnulTreiC");
		anulTrei.add("AnulTreiTI");
		createYearOfStudy("AnulTrei" , "THIRD", anulTrei);

		List<String> anulDoi = new ArrayList<String>();
		anulDoi.add("AnulDoiCTI");
		createYearOfStudy("AnulDoi" , "SECOND", anulDoi);

		List<String> anulIntai = new ArrayList<String>();
		anulIntai.add("AnulIntaiCTI");
		createYearOfStudy("AnulIntai" , "FIRST", anulIntai);
		
		// Departments 
		System.out.println("DepartmentDAO departmentDAO = new DepartmentJPADAOService();\n");
		List<String> calculatoare = new ArrayList<String>();
		calculatoare.add("AnulPatru");
		calculatoare.add("AnulTrei");
		calculatoare.add("AnulDoi");
		calculatoare.add("AnulIntai");
		createDepartment("CTI", calculatoare);
		List<String> automatica = new ArrayList<String>();
		createDepartment("IS", automatica);
	}

	// private static void createOffering(String name, int nrOfTimeSlots, String
	// type, String datePattern,
	// List<String> instructors, List<String> rooms) {
	// String strippedName = name.replace(" ", "");
	// String instrmListName = String.format("instrmList%s", strippedName);
	// String instrmListText = String.format("List<InstructorMeetings> %s = new
	// ArrayList<InstructorMeetings>();\n",
	// instrmListName);
	// instrmListText += String.format("%s.add(", instrmListName);
	//
	// for (int i = 0; i < instructors.size(); i++) {
	// String instructor = instructors.get(i);
	// String room = rooms.get(i);
	// String instrmName = String.format("%s_%s_%s", strippedName, instructor,
	// room);
	// String instrmText = String.format("InstructorMeetings %s = new
	// InstructorMeetings(0, 0, %s, %s);\n",
	// instrmName, instructor, room);
	// System.out.println(instrmText);
	//
	// if (i < instructors.size() - 1) {
	// instrmListText += String.format("%s);\n%s.add(", instrmName,
	// instrmListName);
	// } else {
	// instrmListText += String.format("%s);\n", instrmName);
	// }
	// }
	// System.out.println(instrmListText);
	//
	// String offeringName = strippedName + type;
	// String offeringText = String.format(
	// "Offering %s = new Offering(0, \"%s\", %d, OfferingType.%s,
	// DatePattern.%s, %s);\n", offeringName, name,
	// nrOfTimeSlots, type, datePattern, instrmListName);
	// offeringText += String.format("offeringDAO.merge(%s);\n\n",
	// offeringName);
	//
	// System.out.println(offeringText);
	// }

	private static String createOffering(String name, int nrOfTimeSlots, String type, String datePattern,
			String instructor, String room) {
		String strippedName = name.replace(" ", "");

		String instrmName = String.format("%s_%s_%s", strippedName, instructor, room);
		String instrmText = String.format("InstructorMeetings %s = new InstructorMeetings(0, 0, %s, %s);\n", instrmName,
				instructor, room);
		System.out.println(instrmText);

		String instrmListName = String.format("instrmList%s%s", strippedName, type);
		String instrmListText = String.format("List<InstructorMeetings> %s = new ArrayList<InstructorMeetings>();\n",
				instrmListName);
		instrmListText += String.format("%s.add(%s);\n", instrmListName, instrmName);
		System.out.println(instrmListText);

		String offeringName = strippedName + type;
		String offeringText = String.format(
				"Offering %s = new Offering(0, \"%s\", %d, OfferingType.%s, DatePattern.%s, %s);\n", offeringName, name,
				nrOfTimeSlots, type, datePattern, instrmListName);
		offeringText += String.format("offeringDAO.merge(%s);\n\n", offeringName);

		System.out.println(offeringText);

		return offeringName;
	}

	private static void createCourseOffering(String title, String abbreviation, String lectureName,
			String indivMeetName) {
		String courseOfferingText = String.format(
				"CourseOffering %s = new CourseOffering(0, \"%s\", \"%s\", %s, %s);\n", abbreviation, title,
				abbreviation, lectureName, indivMeetName);
		courseOfferingText += String.format("courseOfferingDAO.merge(%s);\n\n", abbreviation);

		System.out.println(courseOfferingText);
	}

	private static void createCurriculas(String name, String year, String term, List<String> courses) {
		String curriculaName = String.format("%s", name);
		String curriculaListName = String.format("%sCurriculaList", curriculaName);
		String curriculaListText = String.format("List<CourseOffering> %s = new ArrayList<CourseOffering>();\n",
				curriculaListName);

		for (String course : courses) {
			curriculaListText += String.format("%s.add(%s);\n", curriculaListName, course);
		}
		System.out.println(curriculaListText);

		String curriculaText = String.format("Curricula %s = new Curricula(0, \"%s\", CollegeYear.%s, Term.%s, %s);\n",
				curriculaName, name, year, term, curriculaListName);

		curriculaText += String.format("curriculaDAO.merge(%s);\n\n", curriculaName);
		System.out.println(curriculaText);
	}

	private static void createSubjectArea(String name, String abbreviation, String first, String second) {
		String subjectAreaText = String.format("SubjectArea %s = new SubjectArea(0, \"%s\", \"%s\", %s, %s);\n", name,
				name, abbreviation, first, second);

		subjectAreaText += String.format("subjectAreaDAO.merge(%s);\n\n", name);
		System.out.println(subjectAreaText);
	}

	private static void createYearOfStudy(String name, String year, List<String> subjectAreas) {
		String subjectAreasListName = String.format("subjectAreasListFor%s", name);
		String subjectAreasListText = String.format("List<SubjectArea> %s = new ArrayList<SubjectArea>();\n", subjectAreasListName);
		for (String subjectArea : subjectAreas) {
			subjectAreasListText += String.format("%s.add(%s);\n", subjectAreasListName, subjectArea);
		}
		System.out.println(subjectAreasListText);
		
		String yearOfStudyText = String.format("YearOfStudy %s = new YearOfStudy(0, \"%s\", CollegeYear.%s, %s);\n", name, name,
				year, subjectAreasListName);

		yearOfStudyText += String.format("yearOfStudyDAO.merge(%s);\n\n", name);
		System.out.println(yearOfStudyText);
	}

	private static void createDepartment(String name, List<String> yearsOfStudy) {
		String yearsOfStudyListName = String.format("yearsOfStudyListFor%s", name);
		String yearsOfStudyListText = String.format("List<YearOfStudy> %s = new ArrayList<YearOfStudy>();\n", yearsOfStudyListName);
		for (String yearOfStudy : yearsOfStudy) {
			yearsOfStudyListText += String.format("%s.add(%s);\n", yearsOfStudyListName, yearOfStudy);
		}
		System.out.println(yearsOfStudyListText);
		
		String departmentText = String.format("Department %s = new Department(0, \"%s\", %s);\n", name, name, yearsOfStudyListName);

		departmentText += String.format("departmentDAO.merge(%s);\n\n", name);
		System.out.println(departmentText);
	}

}
