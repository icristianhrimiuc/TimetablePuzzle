package resources;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import timetablepuzzle.eclipselink.DAO.JPA.services.administration.CourseOfferingJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.administration.CurriculaJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.administration.DepartmentJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.administration.FacultyJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.administration.SubjectAreaJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.administration.UserJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.administration.YearOfStudyJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.inputdata.InstructorJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.inputdata.OfferingJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.inputdata.RoomJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.inputdata.StudentGroupJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.other.BuildingJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.other.LocationJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.other.RoomFeatureJPADAOService;
import timetablepuzzle.eclipselink.DAO.JPA.services.other.RoomTypeJPADAOService;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.CourseOfferingDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.CurriculaDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.DepartmentDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.FacultyDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.SubjectAreaDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.administration.YearOfStudyDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.inputdata.InstructorDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.inputdata.OfferingDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.inputdata.RoomDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.inputdata.StudentGroupDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.other.BuildingDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.other.LocationDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.other.RoomFeatureDAO;
import timetablepuzzle.eclipselink.DAO.interfaces.other.RoomTypeDAO;
import timetablepuzzle.entities.administration.AcademicSession;
import timetablepuzzle.entities.administration.AcademicYear;
import timetablepuzzle.entities.administration.CourseOffering;
import timetablepuzzle.entities.administration.Curricula;
import timetablepuzzle.entities.administration.Department;
import timetablepuzzle.entities.administration.Faculty;
import timetablepuzzle.entities.administration.SubjectArea;
import timetablepuzzle.entities.administration.User;
import timetablepuzzle.entities.administration.YearOfStudy;
import timetablepuzzle.entities.administration.YearOfStudy.CollegeYear;
import timetablepuzzle.entities.administration.Curricula.Term;
import timetablepuzzle.entities.inputdata.Instructor;
import timetablepuzzle.entities.inputdata.InstructorMeetings;
import timetablepuzzle.entities.inputdata.Offering;
import timetablepuzzle.entities.inputdata.Offering.DatePattern;
import timetablepuzzle.entities.inputdata.Offering.OfferingType;
import timetablepuzzle.entities.inputdata.Room;
import timetablepuzzle.entities.inputdata.StudentGroup;
import timetablepuzzle.entities.other.Building;
import timetablepuzzle.entities.other.Location;
import timetablepuzzle.entities.other.RoomFeature;
import timetablepuzzle.entities.other.RoomType;
import timetablepuzzle.entities.other.TimePreferences;
import timetablepuzzle.swing.PasswordAuthentication;

public class DatabaseInserts {

	public static void main(String[] args) {
		Location loc1 = new Location(0, "Bulevardul Profesor Dr. doc. Dimitrie Mangeron 27", 47.1539684, 27.5939719);
		Location loc2 = new Location(0, "Bulevardul Profesor Dimitrie Mangeron 21-23", 47.1531076, 27.5931271);
		Location loc3 = new Location(0, "Bulevardul Profesor Dimitrie Mangeron 67", 47.1539041, 27.5960851);
		Location loc4 = new Location(0, "Bulevardul Profesor Dimitrie Mangeron 73", 47.1549328, 27.6010847);
		Location loc5 = new Location(0, "Bulevardul Carol I 11", 47.1747539, 27.5711491);
		Location loc6 = new Location(0, "Campus Tudor vladimirescu, langa T25", 47.1549932, 27.6110771);
		LocationDAO locationDAO = new LocationJPADAOService();
		locationDAO.merge(loc1);
		locationDAO.merge(loc2);
		locationDAO.merge(loc3);
		locationDAO.merge(loc4);
		locationDAO.merge(loc5);
		locationDAO.merge(loc6);

		Building AC = new Building(1, "Facultatea de Automatica si Calculatoare", "AC", loc1);
		Building IEEIA = new Building(2, "Facultatea de Inginerie Electrica Energetica si Informatica Aplicata",
				"IEEIA", loc2);
		Building Rectorat = new Building(3, "Rectorat Universitatea Tehnica Gh. Asachi", "Rectorat", loc3);
		Building ICPM = new Building(4, "Facultatea de Inginerie Chimica si Protectia Mediului", "ICPM", loc4);
		Building ETTI = new Building(5, "Facultatea de Electronica, Telecomunicatii si Tehnologia Informatiei", "ETTI",
				loc5);
		Building Sport = new Building(6, "Sala de Sport Universitatea Tehnica Gh. Asachi", "Sala de Sport", loc6);
		BuildingDAO buildingDAO = new BuildingJPADAOService();
		buildingDAO.merge(AC);
		buildingDAO.merge(IEEIA);
		buildingDAO.merge(Rectorat);
		buildingDAO.merge(ICPM);
		buildingDAO.merge(ETTI);
		buildingDAO.merge(Sport);

		RoomFeature feat1 = new RoomFeature(0, "Proiector");
		RoomFeature feat2 = new RoomFeature(0, "Tabla");
		RoomFeature feat3 = new RoomFeature(0, "Mese si scaune");
		RoomFeature feat4 = new RoomFeature(0, "Calculatoare");
		RoomFeature feat5 = new RoomFeature(0, "Analizoare digitale");
		RoomFeature feat6 = new RoomFeature(0, "Microcontrolere");
		RoomFeature feat7 = new RoomFeature(0, "Emulator 8086");
		RoomFeature feat8 = new RoomFeature(0, "Ecipament pt. retele");
		RoomFeature feat9 = new RoomFeature(0, "Brat mecanic");
		RoomFeatureDAO roomFeatureDAO = new RoomFeatureJPADAOService();
		roomFeatureDAO.merge(feat1);
		roomFeatureDAO.merge(feat2);
		roomFeatureDAO.merge(feat3);
		roomFeatureDAO.merge(feat4);
		roomFeatureDAO.merge(feat5);
		roomFeatureDAO.merge(feat6);
		roomFeatureDAO.merge(feat7);
		roomFeatureDAO.merge(feat8);
		roomFeatureDAO.merge(feat9);

		List<RoomFeature> cursFeatures = new ArrayList<RoomFeature>();
		cursFeatures.add(feat1);
		cursFeatures.add(feat2);
		cursFeatures.add(feat3);

		List<RoomFeature> seminaryFeatures = new ArrayList<RoomFeature>();
		seminaryFeatures.add(feat1);
		seminaryFeatures.add(feat2);
		seminaryFeatures.add(feat3);

		List<RoomFeature> laboratoryFeatures = new ArrayList<RoomFeature>();
		laboratoryFeatures.add(feat1);
		laboratoryFeatures.add(feat2);
		laboratoryFeatures.add(feat3);
		laboratoryFeatures.add(feat4);

		List<RoomFeature> electricalLabFeatures = new ArrayList<RoomFeature>();
		electricalLabFeatures.add(feat1);
		electricalLabFeatures.add(feat2);
		electricalLabFeatures.add(feat3);
		electricalLabFeatures.add(feat4);
		electricalLabFeatures.add(feat5);
		electricalLabFeatures.add(feat6);

		RoomType curs = new RoomType(0, "Sala de curs", 80, 100, cursFeatures);
		RoomType seminar = new RoomType(0, "Sala de seminar", 30, 40, seminaryFeatures);
		RoomType laborator = new RoomType(0, "Sala de laborator", 20, 30, laboratoryFeatures);
		RoomType labElectronic = new RoomType(0, "Sala de laborator electronic", 20, 30, electricalLabFeatures);
		RoomTypeDAO roomTypeDAO = new RoomTypeJPADAOService();
		roomTypeDAO.merge(curs);
		roomTypeDAO.merge(seminar);
		roomTypeDAO.merge(laborator);
		roomTypeDAO.merge(labElectronic);

		// Insert instructors here
		InstructorDAO instructorDAO = new InstructorJPADAOService();

		Instructor LeonFlorin = new Instructor(0, "Leon", "Florin", "Prof.dr.ing.",
				TimePreferences.generateRandomTimePreferences());
		instructorDAO.merge(LeonFlorin);

		Instructor UngureanuFlorina = new Instructor(0, "Ungureanu", "Florina", "Prof.dr.ing.",
				TimePreferences.generateRandomTimePreferences());
		instructorDAO.merge(UngureanuFlorina);

		Instructor MantaVasile = new Instructor(0, "Manta", "Vasile", "Prof.dr.ing.",
				TimePreferences.generateRandomTimePreferences());
		instructorDAO.merge(MantaVasile);

		Instructor ArchipAlexandru = new Instructor(0, "Archip", "Alexandru", "Prof.dr.ing.",
				TimePreferences.generateRandomTimePreferences());
		instructorDAO.merge(ArchipAlexandru);

		Instructor AlexandrescuAdrian = new Instructor(0, "Alexandrescu", "Adrian", "Asist.ing.",
				TimePreferences.generateRandomTimePreferences());
		instructorDAO.merge(AlexandrescuAdrian);

		Instructor BotezatuNicolae = new Instructor(0, "Botezatu", "Nicolae", "S.l.dr.ing",
				TimePreferences.generateRandomTimePreferences());
		instructorDAO.merge(BotezatuNicolae);

		Instructor HerghelegiuPaul = new Instructor(0, "Herghelegiu", "Paul", "Conf.dr.ing.",
				TimePreferences.generateRandomTimePreferences());
		instructorDAO.merge(HerghelegiuPaul);

		Instructor MireaLetitia = new Instructor(0, "Mirea", "Letitia", "Conf.dr.ing.",
				TimePreferences.generateRandomTimePreferences());
		instructorDAO.merge(MireaLetitia);

		Instructor GavrilescuMarius = new Instructor(0, "Gavrilescu", "Marius", "S.l.dr.ing",
				TimePreferences.generateRandomTimePreferences());
		instructorDAO.merge(GavrilescuMarius);

		Instructor DumbravaStefan = new Instructor(0, "Dumbrava", "Stefan", "Conf.dr.ing.",
				TimePreferences.generateRandomTimePreferences());
		instructorDAO.merge(DumbravaStefan);

		Instructor CampanuCorina = new Instructor(0, "Campanu", "Corina", "Asist.ing.",
				TimePreferences.generateRandomTimePreferences());
		instructorDAO.merge(CampanuCorina);

		Instructor GrosuGeorgiana = new Instructor(0, "Grosu", "Georgiana", "S.l.dr.mat.",
				TimePreferences.generateRandomTimePreferences());
		instructorDAO.merge(GrosuGeorgiana);

		Instructor CalistruCatalin = new Instructor(0, "Calistru", "Catalin", "S.l.dr.ing",
				TimePreferences.generateRandomTimePreferences());
		instructorDAO.merge(CalistruCatalin);

		Instructor CiobanuBrandusa = new Instructor(0, "Ciobanu", "Brandusa", "Conf.dr.fiz.",
				TimePreferences.generateRandomTimePreferences());
		instructorDAO.merge(CiobanuBrandusa);

		Instructor PleteaAriadna = new Instructor(0, "Pletea", "Ariadna", "Conf.dr.mat",
				TimePreferences.generateRandomTimePreferences());
		instructorDAO.merge(PleteaAriadna);

		Instructor MonorCatalin = new Instructor(0, "Monor", "Catalin", "S.l.dr.ing",
				TimePreferences.generateRandomTimePreferences());
		instructorDAO.merge(MonorCatalin);

		Instructor PantelimonescuFlorin = new Instructor(0, "Pantelimonescu", "Florin", "Conf.dr.ing.",
				TimePreferences.generateRandomTimePreferences());
		instructorDAO.merge(PantelimonescuFlorin);

		Instructor SerbanElena = new Instructor(0, "Serban", "Elena", "Conf.dr.ing.",
				TimePreferences.generateRandomTimePreferences());
		instructorDAO.merge(SerbanElena);

		Instructor StrugariuRadu = new Instructor(0, "Strugariu", "Radu", "Conf.dr.mat",
				TimePreferences.generateRandomTimePreferences());
		instructorDAO.merge(StrugariuRadu);

		Instructor TomaAnaMaria = new Instructor(0, "Toma", "AnaMaria", "Lect.",
				TimePreferences.generateRandomTimePreferences());
		instructorDAO.merge(TomaAnaMaria);

		Instructor LupuRobert = new Instructor(0, "Lupu", "Robert", "S.l.dr.ing",
				TimePreferences.generateRandomTimePreferences());
		instructorDAO.merge(LupuRobert);

		Instructor KloetzerMarius = new Instructor(0, "Kloetzer", "Marius", "Prof.dr.ing.",
				TimePreferences.generateRandomTimePreferences());
		instructorDAO.merge(KloetzerMarius);

		Instructor TigaeruLiviu = new Instructor(0, "Tigaeru", "Liviu", "S.l.dr.fiz.",
				TimePreferences.generateRandomTimePreferences());
		instructorDAO.merge(TigaeruLiviu);

		Instructor TimisMihai = new Instructor(0, "Timis", "Mihai", "S.l.dr.ing",
				TimePreferences.generateRandomTimePreferences());
		instructorDAO.merge(TimisMihai);

		Instructor CascavalPetru = new Instructor(0, "Cascaval", "Petru", "Prof.dr.ing.",
				TimePreferences.generateRandomTimePreferences());
		instructorDAO.merge(CascavalPetru);

		Instructor StanAndrei = new Instructor(0, "Stan", "Andrei", "S.l.dr.ing",
				TimePreferences.generateRandomTimePreferences());
		instructorDAO.merge(StanAndrei);

		Instructor CaraimanSimona = new Instructor(0, "Caraiman", "Simona", "S.l.dr.ing",
				TimePreferences.generateRandomTimePreferences());
		instructorDAO.merge(CaraimanSimona);

		Instructor CrausMitica = new Instructor(0, "Craus", "Mitica", "Prof.dr.ing.",
				TimePreferences.generateRandomTimePreferences());
		instructorDAO.merge(CrausMitica);

		Instructor ButincuCristian = new Instructor(0, "Butincu", "Cristian", "S.l.dr.ing",
				TimePreferences.generateRandomTimePreferences());
		instructorDAO.merge(ButincuCristian);

		// Insert rooms here
		RoomDAO roomDAO = new RoomJPADAOService();
		Room roomAC01 = new Room(0, "Sala de curs", "AC0-1", 100, curs, AC, new TimePreferences());
		roomDAO.merge(roomAC01);

		Room roomAC02 = new Room(0, "Sala de curs", "AC0-2", 100, curs, AC, new TimePreferences());
		roomDAO.merge(roomAC02);

		Room roomT3 = new Room(0, "Sala de curs", "T3", 100, curs, Rectorat, new TimePreferences());
		roomDAO.merge(roomT3);

		Room roomT4 = new Room(0, "Sala de curs", "T4", 100, curs, Rectorat, new TimePreferences());
		roomDAO.merge(roomT4);

		Room roomE1 = new Room(0, "Sala de curs", "E1", 100, curs, IEEIA, new TimePreferences());
		roomDAO.merge(roomE1);

		Room roomE2 = new Room(0, "Sala de curs", "E2", 100, curs, IEEIA, new TimePreferences());
		roomDAO.merge(roomE2);

		Room roomAC03 = new Room(0, "Sala de seminar", "AC0-3", 40, seminar, AC, new TimePreferences());
		roomDAO.merge(roomAC03);

		Room roomAC21 = new Room(0, "Sala de seminar", "AC2-1", 40, seminar, AC, new TimePreferences());
		roomDAO.merge(roomAC21);

		Room roomAC22 = new Room(0, "Sala de seminar", "AC2-2", 40, seminar, AC, new TimePreferences());
		roomDAO.merge(roomAC22);

		Room roomAC23 = new Room(0, "Sala de seminar", "AC2-3", 40, seminar, AC, new TimePreferences());
		roomDAO.merge(roomAC23);

		Room roomAC31 = new Room(0, "Sala de seminar", "AC3-1", 40, seminar, AC, new TimePreferences());
		roomDAO.merge(roomAC31);

		Room roomAC32 = new Room(0, "Sala de seminar", "AC3-2", 40, seminar, AC, new TimePreferences());
		roomDAO.merge(roomAC32);

		Room roomAC33 = new Room(0, "Sala de seminar", "AC3-3", 40, seminar, AC, new TimePreferences());
		roomDAO.merge(roomAC33);

		Room roomC01 = new Room(0, "Laborator de Informatica", "C0-1", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomC01);

		Room roomC02 = new Room(0, "Laborator de Informatica", "C0-2", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomC02);

		Room roomC03 = new Room(0, "Laborator de Informatica", "C0-3", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomC03);

		Room roomC04 = new Room(0, "Laborator de Informatica", "C0-4", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomC04);

		Room roomC05 = new Room(0, "Laborator de Informatica", "C0-5", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomC05);

		Room roomC06 = new Room(0, "Laborator de Informatica", "C0-6", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomC06);

		Room roomC13 = new Room(0, "Laborator de Informatica", "C1-3", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomC13);

		Room roomC14 = new Room(0, "Laborator de Informatica", "C1-4", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomC14);

		Room roomC15 = new Room(0, "Laborator de Informatica", "C1-5", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomC15);

		Room roomC16 = new Room(0, "Laborator de Informatica", "C1-6", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomC16);

		Room roomC28 = new Room(0, "Laborator de BPC", "C2-8", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomC28);

		Room roomC29 = new Room(0, "Laborator de Informatica", "C2-9", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomC29);

		Room roomC210 = new Room(0, "Laborator de Informatica", "C2-10", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomC210);

		Room roomC211 = new Room(0, "Laborator de Informatica", "C2-11", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomC211);

		Room roomC212 = new Room(0, "Laborator de Informatica", "C2-12", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomC212);

		Room roomC213 = new Room(0, "Laborator de Informatica", "C2-13", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomC213);

		Room roomC214 = new Room(0, "Laborator de Informatica", "C2-14", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomC214);

		Room roomC33 = new Room(0, "Laborator de Informatica", "C3-3", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomC33);

		Room roomA01 = new Room(0, "Laborator de Informatica", "A0-1", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomA01);

		Room roomA02 = new Room(0, "Laborator de Informatica", "A0-2", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomA02);

		Room roomA03 = new Room(0, "Laborator de Informatica", "A0-3", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomA03);

		Room roomA04 = new Room(0, "Laborator de Informatica", "A0-4", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomA04);

		Room roomA05 = new Room(0, "Laborator de Informatica", "A0-5", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomA05);

		Room roomA06 = new Room(0, "Laborator de Informatica", "A0-6", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomA06);

		Room roomA13 = new Room(0, "Laborator de Informatica", "A1-3", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomA13);

		Room roomA14 = new Room(0, "Laborator de Informatica", "A1-4", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomA14);

		Room roomA19 = new Room(0, "Laborator de Informatica", "A1-9", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomA19);

		Room roomA113 = new Room(0, "Laborator de Informatica", "A1-13", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomA113);

		Room roomA27 = new Room(0, "Laborator de Informatica", "A2-7", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomA27);

		Room roomA29 = new Room(0, "Laborator de Informatica", "A2-9", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomA29);

		Room roomA210 = new Room(0, "Laborator de Informatica", "A2-10", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomA210);

		Room roomA211 = new Room(0, "Laborator de Informatica", "A2-11", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomA211);

		Room roomA212 = new Room(0, "Laborator de Informatica", "A2-12", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomA212);

		Room roomA213 = new Room(0, "Laborator de Informatica", "A2-13", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomA213);

		Room roomA214 = new Room(0, "Laborator de Informatica", "A2-14", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomA214);

		Room roomA33 = new Room(0, "Laborator de Informatica", "A3-3", 30, laborator, AC, new TimePreferences());
		roomDAO.merge(roomA33);

		Room roomT9 = new Room(0, "Laborator de Informatica", "T9", 30, laborator, Rectorat, new TimePreferences());
		roomDAO.merge(roomT9);

		Room roomI23 = new Room(0, "Laborator de Electronica", "I-23", 30, laborator, ETTI, new TimePreferences());
		roomDAO.merge(roomI23);

		Room roomCHV = new Room(0, "Cabinet de Engleza", "CH-V", 30, laborator, ICPM, new TimePreferences());
		roomDAO.merge(roomCHV);

		// Insert StudentGroups here
		StudentGroup semigrupa1101A = new StudentGroup(0, "Semigrupa", "1101A", 24);
		StudentGroup semigrupa1101B = new StudentGroup(0, "Semigrupa", "1101B", 20);
		List<StudentGroup> comp1101 = new ArrayList<StudentGroup>();
		comp1101.add(semigrupa1101A);
		comp1101.add(semigrupa1101B);

		StudentGroup grupa1101 = new StudentGroup(0, "Grupa", "1101", comp1101);


		StudentGroup semigrupa1102A = new StudentGroup(0, "Semigrupa", "1102A", 28);
		StudentGroup semigrupa1102B = new StudentGroup(0, "Semigrupa", "1102B", 23);
		List<StudentGroup> comp1102 = new ArrayList<StudentGroup>();
		comp1102.add(semigrupa1102A);
		comp1102.add(semigrupa1102B);

		StudentGroup grupa1102 = new StudentGroup(0, "Grupa", "1102", comp1102);


		StudentGroup semigrupa1103A = new StudentGroup(0, "Semigrupa", "1103A", 25);
		StudentGroup semigrupa1103B = new StudentGroup(0, "Semigrupa", "1103B", 20);
		List<StudentGroup> comp1103 = new ArrayList<StudentGroup>();
		comp1103.add(semigrupa1103A);
		comp1103.add(semigrupa1103B);

		StudentGroup grupa1103 = new StudentGroup(0, "Grupa", "1103", comp1103);


		StudentGroup semigrupa1104A = new StudentGroup(0, "Semigrupa", "1104A", 26);
		StudentGroup semigrupa1104B = new StudentGroup(0, "Semigrupa", "1104B", 27);
		List<StudentGroup> comp1104 = new ArrayList<StudentGroup>();
		comp1104.add(semigrupa1104A);
		comp1104.add(semigrupa1104B);

		StudentGroup grupa1104 = new StudentGroup(0, "Grupa", "1104", comp1104);


		List<StudentGroup> compFIRST_IS_IS = new ArrayList<StudentGroup>();
		compFIRST_IS_IS.add(grupa1101);
		compFIRST_IS_IS.add(grupa1102);
		compFIRST_IS_IS.add(grupa1103);
		compFIRST_IS_IS.add(grupa1104);

		StudentGroup specializareaFIRST_IS_IS = new StudentGroup(0, "Specializare", "IS", compFIRST_IS_IS);

		List<StudentGroup> compFIRST_IS = new ArrayList<StudentGroup>();
		compFIRST_IS.add(specializareaFIRST_IS_IS);

		StudentGroup yearOfStudyFIRST_IS = new StudentGroup(0, "YearOfStudy", "FIRST", compFIRST_IS);


		StudentGroup semigrupa1201A = new StudentGroup(0, "Semigrupa", "1201A", 30);
		StudentGroup semigrupa1201B = new StudentGroup(0, "Semigrupa", "1201B", 29);
		List<StudentGroup> comp1201 = new ArrayList<StudentGroup>();
		comp1201.add(semigrupa1201A);
		comp1201.add(semigrupa1201B);

		StudentGroup grupa1201 = new StudentGroup(0, "Grupa", "1201", comp1201);


		StudentGroup semigrupa1202A = new StudentGroup(0, "Semigrupa", "1202A", 21);
		StudentGroup semigrupa1202B = new StudentGroup(0, "Semigrupa", "1202B", 22);
		List<StudentGroup> comp1202 = new ArrayList<StudentGroup>();
		comp1202.add(semigrupa1202A);
		comp1202.add(semigrupa1202B);

		StudentGroup grupa1202 = new StudentGroup(0, "Grupa", "1202", comp1202);


		StudentGroup semigrupa1203A = new StudentGroup(0, "Semigrupa", "1203A", 27);
		StudentGroup semigrupa1203B = new StudentGroup(0, "Semigrupa", "1203B", 27);
		List<StudentGroup> comp1203 = new ArrayList<StudentGroup>();
		comp1203.add(semigrupa1203A);
		comp1203.add(semigrupa1203B);

		StudentGroup grupa1203 = new StudentGroup(0, "Grupa", "1203", comp1203);


		StudentGroup semigrupa1204A = new StudentGroup(0, "Semigrupa", "1204A", 28);
		StudentGroup semigrupa1204B = new StudentGroup(0, "Semigrupa", "1204B", 21);
		List<StudentGroup> comp1204 = new ArrayList<StudentGroup>();
		comp1204.add(semigrupa1204A);
		comp1204.add(semigrupa1204B);

		StudentGroup grupa1204 = new StudentGroup(0, "Grupa", "1204", comp1204);


		List<StudentGroup> compSECOND_IS_IS = new ArrayList<StudentGroup>();
		compSECOND_IS_IS.add(grupa1201);
		compSECOND_IS_IS.add(grupa1202);
		compSECOND_IS_IS.add(grupa1203);
		compSECOND_IS_IS.add(grupa1204);

		StudentGroup specializareaSECOND_IS_IS = new StudentGroup(0, "Specializare", "IS", compSECOND_IS_IS);

		List<StudentGroup> compSECOND_IS = new ArrayList<StudentGroup>();
		compSECOND_IS.add(specializareaSECOND_IS_IS);

		StudentGroup yearOfStudySECOND_IS = new StudentGroup(0, "YearOfStudy", "SECOND", compSECOND_IS);


		StudentGroup semigrupa1301A = new StudentGroup(0, "Semigrupa", "1301A", 26);
		StudentGroup semigrupa1301B = new StudentGroup(0, "Semigrupa", "1301B", 20);
		List<StudentGroup> comp1301 = new ArrayList<StudentGroup>();
		comp1301.add(semigrupa1301A);
		comp1301.add(semigrupa1301B);

		StudentGroup grupa1301 = new StudentGroup(0, "Grupa", "1301", comp1301);


		StudentGroup semigrupa1302A = new StudentGroup(0, "Semigrupa", "1302A", 20);
		StudentGroup semigrupa1302B = new StudentGroup(0, "Semigrupa", "1302B", 27);
		List<StudentGroup> comp1302 = new ArrayList<StudentGroup>();
		comp1302.add(semigrupa1302A);
		comp1302.add(semigrupa1302B);

		StudentGroup grupa1302 = new StudentGroup(0, "Grupa", "1302", comp1302);


		List<StudentGroup> compTHIRD_IS_I = new ArrayList<StudentGroup>();
		compTHIRD_IS_I.add(grupa1301);
		compTHIRD_IS_I.add(grupa1302);

		StudentGroup specializareaTHIRD_IS_I = new StudentGroup(0, "Specializare", "I", compTHIRD_IS_I);

		StudentGroup semigrupa1303A = new StudentGroup(0, "Semigrupa", "1303A", 26);
		StudentGroup semigrupa1303B = new StudentGroup(0, "Semigrupa", "1303B", 26);
		List<StudentGroup> comp1303 = new ArrayList<StudentGroup>();
		comp1303.add(semigrupa1303A);
		comp1303.add(semigrupa1303B);

		StudentGroup grupa1303 = new StudentGroup(0, "Grupa", "1303", comp1303);


		StudentGroup semigrupa1304A = new StudentGroup(0, "Semigrupa", "1304A", 23);
		StudentGroup semigrupa1304B = new StudentGroup(0, "Semigrupa", "1304B", 29);
		List<StudentGroup> comp1304 = new ArrayList<StudentGroup>();
		comp1304.add(semigrupa1304A);
		comp1304.add(semigrupa1304B);

		StudentGroup grupa1304 = new StudentGroup(0, "Grupa", "1304", comp1304);


		List<StudentGroup> compTHIRD_IS_A = new ArrayList<StudentGroup>();
		compTHIRD_IS_A.add(grupa1303);
		compTHIRD_IS_A.add(grupa1304);

		StudentGroup specializareaTHIRD_IS_A = new StudentGroup(0, "Specializare", "A", compTHIRD_IS_A);

		List<StudentGroup> compTHIRD_IS = new ArrayList<StudentGroup>();
		compTHIRD_IS.add(specializareaTHIRD_IS_I);
		compTHIRD_IS.add(specializareaTHIRD_IS_A);

		StudentGroup yearOfStudyTHIRD_IS = new StudentGroup(0, "YearOfStudy", "THIRD", compTHIRD_IS);


		StudentGroup semigrupa1401A = new StudentGroup(0, "Semigrupa", "1401A", 30);
		StudentGroup semigrupa1401B = new StudentGroup(0, "Semigrupa", "1401B", 22);
		List<StudentGroup> comp1401 = new ArrayList<StudentGroup>();
		comp1401.add(semigrupa1401A);
		comp1401.add(semigrupa1401B);

		StudentGroup grupa1401 = new StudentGroup(0, "Grupa", "1401", comp1401);


		StudentGroup semigrupa1402A = new StudentGroup(0, "Semigrupa", "1402A", 21);
		StudentGroup semigrupa1402B = new StudentGroup(0, "Semigrupa", "1402B", 20);
		List<StudentGroup> comp1402 = new ArrayList<StudentGroup>();
		comp1402.add(semigrupa1402A);
		comp1402.add(semigrupa1402B);

		StudentGroup grupa1402 = new StudentGroup(0, "Grupa", "1402", comp1402);


		List<StudentGroup> compFOURTH_IS_I = new ArrayList<StudentGroup>();
		compFOURTH_IS_I.add(grupa1401);
		compFOURTH_IS_I.add(grupa1402);

		StudentGroup specializareaFOURTH_IS_I = new StudentGroup(0, "Specializare", "I", compFOURTH_IS_I);

		StudentGroup semigrupa1403A = new StudentGroup(0, "Semigrupa", "1403A", 20);
		StudentGroup semigrupa1403B = new StudentGroup(0, "Semigrupa", "1403B", 20);
		List<StudentGroup> comp1403 = new ArrayList<StudentGroup>();
		comp1403.add(semigrupa1403A);
		comp1403.add(semigrupa1403B);

		StudentGroup grupa1403 = new StudentGroup(0, "Grupa", "1403", comp1403);


		StudentGroup semigrupa1404A = new StudentGroup(0, "Semigrupa", "1404A", 30);
		StudentGroup semigrupa1404B = new StudentGroup(0, "Semigrupa", "1404B", 30);
		List<StudentGroup> comp1404 = new ArrayList<StudentGroup>();
		comp1404.add(semigrupa1404A);
		comp1404.add(semigrupa1404B);

		StudentGroup grupa1404 = new StudentGroup(0, "Grupa", "1404", comp1404);


		List<StudentGroup> compFOURTH_IS_A = new ArrayList<StudentGroup>();
		compFOURTH_IS_A.add(grupa1403);
		compFOURTH_IS_A.add(grupa1404);

		StudentGroup specializareaFOURTH_IS_A = new StudentGroup(0, "Specializare", "A", compFOURTH_IS_A);

		List<StudentGroup> compFOURTH_IS = new ArrayList<StudentGroup>();
		compFOURTH_IS.add(specializareaFOURTH_IS_I);
		compFOURTH_IS.add(specializareaFOURTH_IS_A);

		StudentGroup yearOfStudyFOURTH_IS = new StudentGroup(0, "YearOfStudy", "FOURTH", compFOURTH_IS);


		StudentGroup semigrupa1501A = new StudentGroup(0, "Semigrupa", "1501A", 20);
		StudentGroup semigrupa1501B = new StudentGroup(0, "Semigrupa", "1501B", 20);
		List<StudentGroup> comp1501 = new ArrayList<StudentGroup>();
		comp1501.add(semigrupa1501A);
		comp1501.add(semigrupa1501B);

		StudentGroup grupa1501 = new StudentGroup(0, "Grupa", "1501", comp1501);


		StudentGroup semigrupa1502A = new StudentGroup(0, "Semigrupa", "1502A", 21);
		StudentGroup semigrupa1502B = new StudentGroup(0, "Semigrupa", "1502B", 25);
		List<StudentGroup> comp1502 = new ArrayList<StudentGroup>();
		comp1502.add(semigrupa1502A);
		comp1502.add(semigrupa1502B);

		StudentGroup grupa1502 = new StudentGroup(0, "Grupa", "1502", comp1502);


		List<StudentGroup> compFIFTH_IS_SCI = new ArrayList<StudentGroup>();
		compFIFTH_IS_SCI.add(grupa1501);
		compFIFTH_IS_SCI.add(grupa1502);

		StudentGroup specializareaFIFTH_IS_SCI = new StudentGroup(0, "Specializare", "SCI", compFIFTH_IS_SCI);

		StudentGroup semigrupa1503A = new StudentGroup(0, "Semigrupa", "1503A", 25);
		StudentGroup semigrupa1503B = new StudentGroup(0, "Semigrupa", "1503B", 26);
		List<StudentGroup> comp1503 = new ArrayList<StudentGroup>();
		comp1503.add(semigrupa1503A);
		comp1503.add(semigrupa1503B);

		StudentGroup grupa1503 = new StudentGroup(0, "Grupa", "1503", comp1503);


		StudentGroup semigrupa1504A = new StudentGroup(0, "Semigrupa", "1504A", 25);
		StudentGroup semigrupa1504B = new StudentGroup(0, "Semigrupa", "1504B", 25);
		List<StudentGroup> comp1504 = new ArrayList<StudentGroup>();
		comp1504.add(semigrupa1504A);
		comp1504.add(semigrupa1504B);

		StudentGroup grupa1504 = new StudentGroup(0, "Grupa", "1504", comp1504);


		List<StudentGroup> compFIFTH_IS_SCA = new ArrayList<StudentGroup>();
		compFIFTH_IS_SCA.add(grupa1503);
		compFIFTH_IS_SCA.add(grupa1504);

		StudentGroup specializareaFIFTH_IS_SCA = new StudentGroup(0, "Specializare", "SCA", compFIFTH_IS_SCA);

		List<StudentGroup> compFIFTH_IS = new ArrayList<StudentGroup>();
		compFIFTH_IS.add(specializareaFIFTH_IS_SCI);
		compFIFTH_IS.add(specializareaFIFTH_IS_SCA);

		StudentGroup yearOfStudyFIFTH_IS = new StudentGroup(0, "YearOfStudy", "FIFTH", compFIFTH_IS);


		StudentGroup semigrupa1601A = new StudentGroup(0, "Semigrupa", "1601A", 30);
		StudentGroup semigrupa1601B = new StudentGroup(0, "Semigrupa", "1601B", 27);
		List<StudentGroup> comp1601 = new ArrayList<StudentGroup>();
		comp1601.add(semigrupa1601A);
		comp1601.add(semigrupa1601B);

		StudentGroup grupa1601 = new StudentGroup(0, "Grupa", "1601", comp1601);


		StudentGroup semigrupa1602A = new StudentGroup(0, "Semigrupa", "1602A", 20);
		StudentGroup semigrupa1602B = new StudentGroup(0, "Semigrupa", "1602B", 26);
		List<StudentGroup> comp1602 = new ArrayList<StudentGroup>();
		comp1602.add(semigrupa1602A);
		comp1602.add(semigrupa1602B);

		StudentGroup grupa1602 = new StudentGroup(0, "Grupa", "1602", comp1602);


		List<StudentGroup> compSIXTH_IS_SCI = new ArrayList<StudentGroup>();
		compSIXTH_IS_SCI.add(grupa1601);
		compSIXTH_IS_SCI.add(grupa1602);

		StudentGroup specializareaSIXTH_IS_SCI = new StudentGroup(0, "Specializare", "SCI", compSIXTH_IS_SCI);

		StudentGroup semigrupa1603A = new StudentGroup(0, "Semigrupa", "1603A", 25);
		StudentGroup semigrupa1603B = new StudentGroup(0, "Semigrupa", "1603B", 29);
		List<StudentGroup> comp1603 = new ArrayList<StudentGroup>();
		comp1603.add(semigrupa1603A);
		comp1603.add(semigrupa1603B);

		StudentGroup grupa1603 = new StudentGroup(0, "Grupa", "1603", comp1603);


		StudentGroup semigrupa1604A = new StudentGroup(0, "Semigrupa", "1604A", 22);
		StudentGroup semigrupa1604B = new StudentGroup(0, "Semigrupa", "1604B", 26);
		List<StudentGroup> comp1604 = new ArrayList<StudentGroup>();
		comp1604.add(semigrupa1604A);
		comp1604.add(semigrupa1604B);

		StudentGroup grupa1604 = new StudentGroup(0, "Grupa", "1604", comp1604);


		List<StudentGroup> compSIXTH_IS_SCA = new ArrayList<StudentGroup>();
		compSIXTH_IS_SCA.add(grupa1603);
		compSIXTH_IS_SCA.add(grupa1604);

		StudentGroup specializareaSIXTH_IS_SCA = new StudentGroup(0, "Specializare", "SCA", compSIXTH_IS_SCA);

		List<StudentGroup> compSIXTH_IS = new ArrayList<StudentGroup>();
		compSIXTH_IS.add(specializareaSIXTH_IS_SCI);
		compSIXTH_IS.add(specializareaSIXTH_IS_SCA);

		StudentGroup yearOfStudySIXTH_IS = new StudentGroup(0, "YearOfStudy", "SIXTH", compSIXTH_IS);


		List<StudentGroup> compIS = new ArrayList<StudentGroup>();
		compIS.add(yearOfStudyFIRST_IS);
		compIS.add(yearOfStudySECOND_IS);
		compIS.add(yearOfStudyTHIRD_IS);
		compIS.add(yearOfStudyFOURTH_IS);
		compIS.add(yearOfStudyFIFTH_IS);
		compIS.add(yearOfStudySIXTH_IS);

		StudentGroup departmentIS = new StudentGroup(0, "Department", "IS", compIS);


		StudentGroup semigrupa1105A = new StudentGroup(0, "Semigrupa", "1105A", 20);
		StudentGroup semigrupa1105B = new StudentGroup(0, "Semigrupa", "1105B", 26);
		List<StudentGroup> comp1105 = new ArrayList<StudentGroup>();
		comp1105.add(semigrupa1105A);
		comp1105.add(semigrupa1105B);

		StudentGroup grupa1105 = new StudentGroup(0, "Grupa", "1105", comp1105);


		StudentGroup semigrupa1106A = new StudentGroup(0, "Semigrupa", "1106A", 20);
		StudentGroup semigrupa1106B = new StudentGroup(0, "Semigrupa", "1106B", 30);
		List<StudentGroup> comp1106 = new ArrayList<StudentGroup>();
		comp1106.add(semigrupa1106A);
		comp1106.add(semigrupa1106B);

		StudentGroup grupa1106 = new StudentGroup(0, "Grupa", "1106", comp1106);


		StudentGroup semigrupa1107A = new StudentGroup(0, "Semigrupa", "1107A", 26);
		StudentGroup semigrupa1107B = new StudentGroup(0, "Semigrupa", "1107B", 21);
		List<StudentGroup> comp1107 = new ArrayList<StudentGroup>();
		comp1107.add(semigrupa1107A);
		comp1107.add(semigrupa1107B);

		StudentGroup grupa1107 = new StudentGroup(0, "Grupa", "1107", comp1107);


		StudentGroup semigrupa1108A = new StudentGroup(0, "Semigrupa", "1108A", 25);
		StudentGroup semigrupa1108B = new StudentGroup(0, "Semigrupa", "1108B", 20);
		List<StudentGroup> comp1108 = new ArrayList<StudentGroup>();
		comp1108.add(semigrupa1108A);
		comp1108.add(semigrupa1108B);

		StudentGroup grupa1108 = new StudentGroup(0, "Grupa", "1108", comp1108);


		List<StudentGroup> compFIRST_CTI_CTI = new ArrayList<StudentGroup>();
		compFIRST_CTI_CTI.add(grupa1105);
		compFIRST_CTI_CTI.add(grupa1106);
		compFIRST_CTI_CTI.add(grupa1107);
		compFIRST_CTI_CTI.add(grupa1108);

		StudentGroup specializareaFIRST_CTI_CTI = new StudentGroup(0, "Specializare", "CTI", compFIRST_CTI_CTI);

		List<StudentGroup> compFIRST_CTI = new ArrayList<StudentGroup>();
		compFIRST_CTI.add(specializareaFIRST_CTI_CTI);

		StudentGroup yearOfStudyFIRST_CTI = new StudentGroup(0, "YearOfStudy", "FIRST", compFIRST_CTI);


		StudentGroup semigrupa1205A = new StudentGroup(0, "Semigrupa", "1205A", 30);
		StudentGroup semigrupa1205B = new StudentGroup(0, "Semigrupa", "1205B", 30);
		List<StudentGroup> comp1205 = new ArrayList<StudentGroup>();
		comp1205.add(semigrupa1205A);
		comp1205.add(semigrupa1205B);

		StudentGroup grupa1205 = new StudentGroup(0, "Grupa", "1205", comp1205);


		StudentGroup semigrupa1206A = new StudentGroup(0, "Semigrupa", "1206A", 23);
		StudentGroup semigrupa1206B = new StudentGroup(0, "Semigrupa", "1206B", 23);
		List<StudentGroup> comp1206 = new ArrayList<StudentGroup>();
		comp1206.add(semigrupa1206A);
		comp1206.add(semigrupa1206B);

		StudentGroup grupa1206 = new StudentGroup(0, "Grupa", "1206", comp1206);


		StudentGroup semigrupa1207A = new StudentGroup(0, "Semigrupa", "1207A", 30);
		StudentGroup semigrupa1207B = new StudentGroup(0, "Semigrupa", "1207B", 26);
		List<StudentGroup> comp1207 = new ArrayList<StudentGroup>();
		comp1207.add(semigrupa1207A);
		comp1207.add(semigrupa1207B);

		StudentGroup grupa1207 = new StudentGroup(0, "Grupa", "1207", comp1207);


		StudentGroup semigrupa1208A = new StudentGroup(0, "Semigrupa", "1208A", 30);
		StudentGroup semigrupa1208B = new StudentGroup(0, "Semigrupa", "1208B", 24);
		List<StudentGroup> comp1208 = new ArrayList<StudentGroup>();
		comp1208.add(semigrupa1208A);
		comp1208.add(semigrupa1208B);

		StudentGroup grupa1208 = new StudentGroup(0, "Grupa", "1208", comp1208);


		List<StudentGroup> compSECOND_CTI_CTI = new ArrayList<StudentGroup>();
		compSECOND_CTI_CTI.add(grupa1205);
		compSECOND_CTI_CTI.add(grupa1206);
		compSECOND_CTI_CTI.add(grupa1207);
		compSECOND_CTI_CTI.add(grupa1208);

		StudentGroup specializareaSECOND_CTI_CTI = new StudentGroup(0, "Specializare", "CTI", compSECOND_CTI_CTI);

		List<StudentGroup> compSECOND_CTI = new ArrayList<StudentGroup>();
		compSECOND_CTI.add(specializareaSECOND_CTI_CTI);

		StudentGroup yearOfStudySECOND_CTI = new StudentGroup(0, "YearOfStudy", "SECOND", compSECOND_CTI);


		StudentGroup semigrupa1305A = new StudentGroup(0, "Semigrupa", "1305A", 24);
		StudentGroup semigrupa1305B = new StudentGroup(0, "Semigrupa", "1305B", 26);
		List<StudentGroup> comp1305 = new ArrayList<StudentGroup>();
		comp1305.add(semigrupa1305A);
		comp1305.add(semigrupa1305B);

		StudentGroup grupa1305 = new StudentGroup(0, "Grupa", "1305", comp1305);


		StudentGroup semigrupa1306A = new StudentGroup(0, "Semigrupa", "1306A", 20);
		StudentGroup semigrupa1306B = new StudentGroup(0, "Semigrupa", "1306B", 27);
		List<StudentGroup> comp1306 = new ArrayList<StudentGroup>();
		comp1306.add(semigrupa1306A);
		comp1306.add(semigrupa1306B);

		StudentGroup grupa1306 = new StudentGroup(0, "Grupa", "1306", comp1306);


		List<StudentGroup> compTHIRD_CTI_C = new ArrayList<StudentGroup>();
		compTHIRD_CTI_C.add(grupa1305);
		compTHIRD_CTI_C.add(grupa1306);

		StudentGroup specializareaTHIRD_CTI_C = new StudentGroup(0, "Specializare", "C", compTHIRD_CTI_C);

		StudentGroup semigrupa1307A = new StudentGroup(0, "Semigrupa", "1307A", 27);
		StudentGroup semigrupa1307B = new StudentGroup(0, "Semigrupa", "1307B", 30);
		List<StudentGroup> comp1307 = new ArrayList<StudentGroup>();
		comp1307.add(semigrupa1307A);
		comp1307.add(semigrupa1307B);

		StudentGroup grupa1307 = new StudentGroup(0, "Grupa", "1307", comp1307);


		StudentGroup semigrupa1308A = new StudentGroup(0, "Semigrupa", "1308A", 20);
		StudentGroup semigrupa1308B = new StudentGroup(0, "Semigrupa", "1308B", 20);
		List<StudentGroup> comp1308 = new ArrayList<StudentGroup>();
		comp1308.add(semigrupa1308A);
		comp1308.add(semigrupa1308B);

		StudentGroup grupa1308 = new StudentGroup(0, "Grupa", "1308", comp1308);


		List<StudentGroup> compTHIRD_CTI_TI = new ArrayList<StudentGroup>();
		compTHIRD_CTI_TI.add(grupa1307);
		compTHIRD_CTI_TI.add(grupa1308);

		StudentGroup specializareaTHIRD_CTI_TI = new StudentGroup(0, "Specializare", "TI", compTHIRD_CTI_TI);

		List<StudentGroup> compTHIRD_CTI = new ArrayList<StudentGroup>();
		compTHIRD_CTI.add(specializareaTHIRD_CTI_C);
		compTHIRD_CTI.add(specializareaTHIRD_CTI_TI);

		StudentGroup yearOfStudyTHIRD_CTI = new StudentGroup(0, "YearOfStudy", "THIRD", compTHIRD_CTI);


		StudentGroup semigrupa1405A = new StudentGroup(0, "Semigrupa", "1405A", 26);
		StudentGroup semigrupa1405B = new StudentGroup(0, "Semigrupa", "1405B", 24);
		List<StudentGroup> comp1405 = new ArrayList<StudentGroup>();
		comp1405.add(semigrupa1405A);
		comp1405.add(semigrupa1405B);

		StudentGroup grupa1405 = new StudentGroup(0, "Grupa", "1405", comp1405);


		StudentGroup semigrupa1406A = new StudentGroup(0, "Semigrupa", "1406A", 27);
		StudentGroup semigrupa1406B = new StudentGroup(0, "Semigrupa", "1406B", 20);
		List<StudentGroup> comp1406 = new ArrayList<StudentGroup>();
		comp1406.add(semigrupa1406A);
		comp1406.add(semigrupa1406B);

		StudentGroup grupa1406 = new StudentGroup(0, "Grupa", "1406", comp1406);


		List<StudentGroup> compFOURTH_CTI_C = new ArrayList<StudentGroup>();
		compFOURTH_CTI_C.add(grupa1405);
		compFOURTH_CTI_C.add(grupa1406);

		StudentGroup specializareaFOURTH_CTI_C = new StudentGroup(0, "Specializare", "C", compFOURTH_CTI_C);

		StudentGroup semigrupa1407A = new StudentGroup(0, "Semigrupa", "1407A", 26);
		StudentGroup semigrupa1407B = new StudentGroup(0, "Semigrupa", "1407B", 28);
		List<StudentGroup> comp1407 = new ArrayList<StudentGroup>();
		comp1407.add(semigrupa1407A);
		comp1407.add(semigrupa1407B);

		StudentGroup grupa1407 = new StudentGroup(0, "Grupa", "1407", comp1407);


		StudentGroup semigrupa1408A = new StudentGroup(0, "Semigrupa", "1408A", 28);
		StudentGroup semigrupa1408B = new StudentGroup(0, "Semigrupa", "1408B", 20);
		List<StudentGroup> comp1408 = new ArrayList<StudentGroup>();
		comp1408.add(semigrupa1408A);
		comp1408.add(semigrupa1408B);

		StudentGroup grupa1408 = new StudentGroup(0, "Grupa", "1408", comp1408);


		List<StudentGroup> compFOURTH_CTI_TI = new ArrayList<StudentGroup>();
		compFOURTH_CTI_TI.add(grupa1407);
		compFOURTH_CTI_TI.add(grupa1408);

		StudentGroup specializareaFOURTH_CTI_TI = new StudentGroup(0, "Specializare", "TI", compFOURTH_CTI_TI);

		List<StudentGroup> compFOURTH_CTI = new ArrayList<StudentGroup>();
		compFOURTH_CTI.add(specializareaFOURTH_CTI_C);
		compFOURTH_CTI.add(specializareaFOURTH_CTI_TI);

		StudentGroup yearOfStudyFOURTH_CTI = new StudentGroup(0, "YearOfStudy", "FOURTH", compFOURTH_CTI);


		StudentGroup semigrupa1505A = new StudentGroup(0, "Semigrupa", "1505A", 25);
		StudentGroup semigrupa1505B = new StudentGroup(0, "Semigrupa", "1505B", 24);
		List<StudentGroup> comp1505 = new ArrayList<StudentGroup>();
		comp1505.add(semigrupa1505A);
		comp1505.add(semigrupa1505B);

		StudentGroup grupa1505 = new StudentGroup(0, "Grupa", "1505", comp1505);


		StudentGroup semigrupa1506A = new StudentGroup(0, "Semigrupa", "1506A", 22);
		StudentGroup semigrupa1506B = new StudentGroup(0, "Semigrupa", "1506B", 22);
		List<StudentGroup> comp1506 = new ArrayList<StudentGroup>();
		comp1506.add(semigrupa1506A);
		comp1506.add(semigrupa1506B);

		StudentGroup grupa1506 = new StudentGroup(0, "Grupa", "1506", comp1506);


		List<StudentGroup> compFIFTH_CTI_CI = new ArrayList<StudentGroup>();
		compFIFTH_CTI_CI.add(grupa1505);
		compFIFTH_CTI_CI.add(grupa1506);

		StudentGroup specializareaFIFTH_CTI_CI = new StudentGroup(0, "Specializare", "CI", compFIFTH_CTI_CI);

		StudentGroup semigrupa1507A = new StudentGroup(0, "Semigrupa", "1507A", 26);
		StudentGroup semigrupa1507B = new StudentGroup(0, "Semigrupa", "1507B", 27);
		List<StudentGroup> comp1507 = new ArrayList<StudentGroup>();
		comp1507.add(semigrupa1507A);
		comp1507.add(semigrupa1507B);

		StudentGroup grupa1507 = new StudentGroup(0, "Grupa", "1507", comp1507);


		StudentGroup semigrupa1508A = new StudentGroup(0, "Semigrupa", "1508A", 28);
		StudentGroup semigrupa1508B = new StudentGroup(0, "Semigrupa", "1508B", 25);
		List<StudentGroup> comp1508 = new ArrayList<StudentGroup>();
		comp1508.add(semigrupa1508A);
		comp1508.add(semigrupa1508B);

		StudentGroup grupa1508 = new StudentGroup(0, "Grupa", "1508", comp1508);


		List<StudentGroup> compFIFTH_CTI_SDTW = new ArrayList<StudentGroup>();
		compFIFTH_CTI_SDTW.add(grupa1507);
		compFIFTH_CTI_SDTW.add(grupa1508);

		StudentGroup specializareaFIFTH_CTI_SDTW = new StudentGroup(0, "Specializare", "SDTW", compFIFTH_CTI_SDTW);

		List<StudentGroup> compFIFTH_CTI = new ArrayList<StudentGroup>();
		compFIFTH_CTI.add(specializareaFIFTH_CTI_CI);
		compFIFTH_CTI.add(specializareaFIFTH_CTI_SDTW);

		StudentGroup yearOfStudyFIFTH_CTI = new StudentGroup(0, "YearOfStudy", "FIFTH", compFIFTH_CTI);


		StudentGroup semigrupa1605A = new StudentGroup(0, "Semigrupa", "1605A", 25);
		StudentGroup semigrupa1605B = new StudentGroup(0, "Semigrupa", "1605B", 24);
		List<StudentGroup> comp1605 = new ArrayList<StudentGroup>();
		comp1605.add(semigrupa1605A);
		comp1605.add(semigrupa1605B);

		StudentGroup grupa1605 = new StudentGroup(0, "Grupa", "1605", comp1605);


		StudentGroup semigrupa1606A = new StudentGroup(0, "Semigrupa", "1606A", 24);
		StudentGroup semigrupa1606B = new StudentGroup(0, "Semigrupa", "1606B", 24);
		List<StudentGroup> comp1606 = new ArrayList<StudentGroup>();
		comp1606.add(semigrupa1606A);
		comp1606.add(semigrupa1606B);

		StudentGroup grupa1606 = new StudentGroup(0, "Grupa", "1606", comp1606);


		List<StudentGroup> compSIXTH_CTI_CI = new ArrayList<StudentGroup>();
		compSIXTH_CTI_CI.add(grupa1605);
		compSIXTH_CTI_CI.add(grupa1606);

		StudentGroup specializareaSIXTH_CTI_CI = new StudentGroup(0, "Specializare", "CI", compSIXTH_CTI_CI);

		StudentGroup semigrupa1607A = new StudentGroup(0, "Semigrupa", "1607A", 25);
		StudentGroup semigrupa1607B = new StudentGroup(0, "Semigrupa", "1607B", 26);
		List<StudentGroup> comp1607 = new ArrayList<StudentGroup>();
		comp1607.add(semigrupa1607A);
		comp1607.add(semigrupa1607B);

		StudentGroup grupa1607 = new StudentGroup(0, "Grupa", "1607", comp1607);


		StudentGroup semigrupa1608A = new StudentGroup(0, "Semigrupa", "1608A", 30);
		StudentGroup semigrupa1608B = new StudentGroup(0, "Semigrupa", "1608B", 29);
		List<StudentGroup> comp1608 = new ArrayList<StudentGroup>();
		comp1608.add(semigrupa1608A);
		comp1608.add(semigrupa1608B);

		StudentGroup grupa1608 = new StudentGroup(0, "Grupa", "1608", comp1608);


		List<StudentGroup> compSIXTH_CTI_SDTW = new ArrayList<StudentGroup>();
		compSIXTH_CTI_SDTW.add(grupa1607);
		compSIXTH_CTI_SDTW.add(grupa1608);

		StudentGroup specializareaSIXTH_CTI_SDTW = new StudentGroup(0, "Specializare", "SDTW", compSIXTH_CTI_SDTW);

		List<StudentGroup> compSIXTH_CTI = new ArrayList<StudentGroup>();
		compSIXTH_CTI.add(specializareaSIXTH_CTI_CI);
		compSIXTH_CTI.add(specializareaSIXTH_CTI_SDTW);

		StudentGroup yearOfStudySIXTH_CTI = new StudentGroup(0, "YearOfStudy", "SIXTH", compSIXTH_CTI);


		List<StudentGroup> compCTI = new ArrayList<StudentGroup>();
		compCTI.add(yearOfStudyFIRST_CTI);
		compCTI.add(yearOfStudySECOND_CTI);
		compCTI.add(yearOfStudyTHIRD_CTI);
		compCTI.add(yearOfStudyFOURTH_CTI);
		compCTI.add(yearOfStudyFIFTH_CTI);
		compCTI.add(yearOfStudySIXTH_CTI);

		StudentGroup departmentCTI = new StudentGroup(0, "Department", "CTI", compCTI);


		List<StudentGroup> comp2016_2017 = new ArrayList<StudentGroup>();
		comp2016_2017.add(departmentIS);
		comp2016_2017.add(departmentCTI);

		StudentGroup academicYear2016_2017 = new StudentGroup(0, "AcademicYear", "2016_2017", comp2016_2017);


		StudentGroupDAO studentGroupDAO = new StudentGroupJPADAOService();
		studentGroupDAO.merge(academicYear2016_2017);


		// Insert Offerings here
		OfferingDAO offeringDAO = new OfferingJPADAOService();

		CourseOfferingDAO courseOfferingDAO = new CourseOfferingJPADAOService();

		InstructorMeetings InteligentaArtificiala_LeonFlorin_roomAC02 = new InstructorMeetings(0, 0, LeonFlorin, roomAC02);

		List<InstructorMeetings> instrmListInteligentaArtificialaLECTURE = new ArrayList<InstructorMeetings>();
		instrmListInteligentaArtificialaLECTURE.add(InteligentaArtificiala_LeonFlorin_roomAC02);

		Offering InteligentaArtificialaLECTURE = new Offering(0, "Inteligenta Artificiala", 2, OfferingType.LECTURE, DatePattern.FULL_TERM, instrmListInteligentaArtificialaLECTURE);
		offeringDAO.merge(InteligentaArtificialaLECTURE);


		InstructorMeetings InteligentaArtificiala_CampanuCorina_roomC06 = new InstructorMeetings(0, 0, CampanuCorina, roomC06);

		List<InstructorMeetings> instrmListInteligentaArtificialaLABORATORY = new ArrayList<InstructorMeetings>();
		instrmListInteligentaArtificialaLABORATORY.add(InteligentaArtificiala_CampanuCorina_roomC06);

		Offering InteligentaArtificialaLABORATORY = new Offering(0, "Inteligenta Artificiala", 2, OfferingType.LABORATORY, DatePattern.FULL_TERM, instrmListInteligentaArtificialaLABORATORY);
		offeringDAO.merge(InteligentaArtificialaLABORATORY);


		CourseOffering IA = new CourseOffering(0, "Inteligenta Artificiala", "IA", InteligentaArtificialaLECTURE, InteligentaArtificialaLABORATORY);
		courseOfferingDAO.merge(IA);


		InstructorMeetings IngineriaProgramarii_LeonFlorin_roomAC02 = new InstructorMeetings(0, 0, LeonFlorin, roomAC02);

		List<InstructorMeetings> instrmListIngineriaProgramariiLECTURE = new ArrayList<InstructorMeetings>();
		instrmListIngineriaProgramariiLECTURE.add(IngineriaProgramarii_LeonFlorin_roomAC02);

		Offering IngineriaProgramariiLECTURE = new Offering(0, "Ingineria Programarii", 2, OfferingType.LECTURE, DatePattern.FULL_TERM, instrmListIngineriaProgramariiLECTURE);
		offeringDAO.merge(IngineriaProgramariiLECTURE);


		InstructorMeetings IngineriaProgramarii_CampanuCorina_roomC06 = new InstructorMeetings(0, 0, CampanuCorina, roomC06);

		List<InstructorMeetings> instrmListIngineriaProgramariiLABORATORY = new ArrayList<InstructorMeetings>();
		instrmListIngineriaProgramariiLABORATORY.add(IngineriaProgramarii_CampanuCorina_roomC06);

		Offering IngineriaProgramariiLABORATORY = new Offering(0, "Ingineria Programarii", 2, OfferingType.LABORATORY, DatePattern.FULL_TERM, instrmListIngineriaProgramariiLABORATORY);
		offeringDAO.merge(IngineriaProgramariiLABORATORY);


		CourseOffering IP = new CourseOffering(0, "Ingineria Programarii", "IP", IngineriaProgramariiLECTURE, IngineriaProgramariiLABORATORY);
		courseOfferingDAO.merge(IP);


		InstructorMeetings AlgoritmiParalelisiDistribuiti_CrausMitica_roomAC02 = new InstructorMeetings(0, 0, CrausMitica, roomAC02);

		List<InstructorMeetings> instrmListAlgoritmiParalelisiDistribuitiLECTURE = new ArrayList<InstructorMeetings>();
		instrmListAlgoritmiParalelisiDistribuitiLECTURE.add(AlgoritmiParalelisiDistribuiti_CrausMitica_roomAC02);

		Offering AlgoritmiParalelisiDistribuitiLECTURE = new Offering(0, "Algoritmi Paraleli si Distribuiti", 2, OfferingType.LECTURE, DatePattern.FULL_TERM, instrmListAlgoritmiParalelisiDistribuitiLECTURE);
		offeringDAO.merge(AlgoritmiParalelisiDistribuitiLECTURE);


		InstructorMeetings AlgoritmiParalelisiDistribuiti_AlexandrescuAdrian_roomC14 = new InstructorMeetings(0, 0, AlexandrescuAdrian, roomC14);

		List<InstructorMeetings> instrmListAlgoritmiParalelisiDistribuitiLABORATORY = new ArrayList<InstructorMeetings>();
		instrmListAlgoritmiParalelisiDistribuitiLABORATORY.add(AlgoritmiParalelisiDistribuiti_AlexandrescuAdrian_roomC14);

		Offering AlgoritmiParalelisiDistribuitiLABORATORY = new Offering(0, "Algoritmi Paraleli si Distribuiti", 2, OfferingType.LABORATORY, DatePattern.FULL_TERM, instrmListAlgoritmiParalelisiDistribuitiLABORATORY);
		offeringDAO.merge(AlgoritmiParalelisiDistribuitiLABORATORY);


		CourseOffering AlPD = new CourseOffering(0, "Algoritmi Paraleli si Distribuiti", "AlPD", AlgoritmiParalelisiDistribuitiLECTURE, AlgoritmiParalelisiDistribuitiLABORATORY);
		courseOfferingDAO.merge(AlPD);


		InstructorMeetings ProgramareaClientuluiWeb_CaraimanSimona_roomAC21 = new InstructorMeetings(0, 0, CaraimanSimona, roomAC21);

		List<InstructorMeetings> instrmListProgramareaClientuluiWebLECTURE = new ArrayList<InstructorMeetings>();
		instrmListProgramareaClientuluiWebLECTURE.add(ProgramareaClientuluiWeb_CaraimanSimona_roomAC21);

		Offering ProgramareaClientuluiWebLECTURE = new Offering(0, "Programarea Clientului Web", 2, OfferingType.LECTURE, DatePattern.FULL_TERM, instrmListProgramareaClientuluiWebLECTURE);
		offeringDAO.merge(ProgramareaClientuluiWebLECTURE);


		InstructorMeetings ProgramareaClientuluiWeb_GavrilescuMarius_roomC01 = new InstructorMeetings(0, 0, GavrilescuMarius, roomC01);

		List<InstructorMeetings> instrmListProgramareaClientuluiWebLABORATORY = new ArrayList<InstructorMeetings>();
		instrmListProgramareaClientuluiWebLABORATORY.add(ProgramareaClientuluiWeb_GavrilescuMarius_roomC01);

		Offering ProgramareaClientuluiWebLABORATORY = new Offering(0, "Programarea Clientului Web", 2, OfferingType.LABORATORY, DatePattern.FULL_TERM, instrmListProgramareaClientuluiWebLABORATORY);
		offeringDAO.merge(ProgramareaClientuluiWebLABORATORY);


		CourseOffering PCW = new CourseOffering(0, "Programarea Clientului Web", "PCW", ProgramareaClientuluiWebLECTURE, ProgramareaClientuluiWebLABORATORY);
		courseOfferingDAO.merge(PCW);


		InstructorMeetings Structurimultiprocesor_StanAndrei_roomAC21 = new InstructorMeetings(0, 0, StanAndrei, roomAC21);

		List<InstructorMeetings> instrmListStructurimultiprocesorLECTURE = new ArrayList<InstructorMeetings>();
		instrmListStructurimultiprocesorLECTURE.add(Structurimultiprocesor_StanAndrei_roomAC21);

		Offering StructurimultiprocesorLECTURE = new Offering(0, "Structuri multiprocesor", 2, OfferingType.LECTURE, DatePattern.FULL_TERM, instrmListStructurimultiprocesorLECTURE);
		offeringDAO.merge(StructurimultiprocesorLECTURE);


		InstructorMeetings Structurimultiprocesor_StanAndrei_roomC214 = new InstructorMeetings(0, 0, StanAndrei, roomC214);

		List<InstructorMeetings> instrmListStructurimultiprocesorLABORATORY = new ArrayList<InstructorMeetings>();
		instrmListStructurimultiprocesorLABORATORY.add(Structurimultiprocesor_StanAndrei_roomC214);

		Offering StructurimultiprocesorLABORATORY = new Offering(0, "Structuri multiprocesor", 2, OfferingType.LABORATORY, DatePattern.FULL_TERM, instrmListStructurimultiprocesorLABORATORY);
		offeringDAO.merge(StructurimultiprocesorLABORATORY);


		CourseOffering SM = new CourseOffering(0, "Structuri multiprocesor", "SM", StructurimultiprocesorLECTURE, StructurimultiprocesorLABORATORY);
		courseOfferingDAO.merge(SM);


		InstructorMeetings Limbajeformalesitranslatoare_CrausMitica_roomAC02 = new InstructorMeetings(0, 0, CrausMitica, roomAC02);

		List<InstructorMeetings> instrmListLimbajeformalesitranslatoareLECTURE = new ArrayList<InstructorMeetings>();
		instrmListLimbajeformalesitranslatoareLECTURE.add(Limbajeformalesitranslatoare_CrausMitica_roomAC02);

		Offering LimbajeformalesitranslatoareLECTURE = new Offering(0, "Limbaje formale si translatoare", 2, OfferingType.LECTURE, DatePattern.FULL_TERM, instrmListLimbajeformalesitranslatoareLECTURE);
		offeringDAO.merge(LimbajeformalesitranslatoareLECTURE);


		InstructorMeetings Limbajeformalesitranslatoare_CrausMitica_roomC04 = new InstructorMeetings(0, 0, CrausMitica, roomC04);

		List<InstructorMeetings> instrmListLimbajeformalesitranslatoareLABORATORY = new ArrayList<InstructorMeetings>();
		instrmListLimbajeformalesitranslatoareLABORATORY.add(Limbajeformalesitranslatoare_CrausMitica_roomC04);

		Offering LimbajeformalesitranslatoareLABORATORY = new Offering(0, "Limbaje formale si translatoare", 2, OfferingType.LABORATORY, DatePattern.FULL_TERM, instrmListLimbajeformalesitranslatoareLABORATORY);
		offeringDAO.merge(LimbajeformalesitranslatoareLABORATORY);


		CourseOffering LFT = new CourseOffering(0, "Limbaje formale si translatoare", "LFT", LimbajeformalesitranslatoareLECTURE, LimbajeformalesitranslatoareLABORATORY);
		courseOfferingDAO.merge(LFT);


		InstructorMeetings RegasireaInformatiilorpeWeb_ArchipAlexandru_roomAC02 = new InstructorMeetings(0, 0, ArchipAlexandru, roomAC02);

		List<InstructorMeetings> instrmListRegasireaInformatiilorpeWebLECTURE = new ArrayList<InstructorMeetings>();
		instrmListRegasireaInformatiilorpeWebLECTURE.add(RegasireaInformatiilorpeWeb_ArchipAlexandru_roomAC02);

		Offering RegasireaInformatiilorpeWebLECTURE = new Offering(0, "Regasirea Informatiilor pe Web", 2, OfferingType.LECTURE, DatePattern.FULL_TERM, instrmListRegasireaInformatiilorpeWebLECTURE);
		offeringDAO.merge(RegasireaInformatiilorpeWebLECTURE);


		InstructorMeetings RegasireaInformatiilorpeWeb_ArchipAlexandru_roomC14 = new InstructorMeetings(0, 0, ArchipAlexandru, roomC14);

		List<InstructorMeetings> instrmListRegasireaInformatiilorpeWebLABORATORY = new ArrayList<InstructorMeetings>();
		instrmListRegasireaInformatiilorpeWebLABORATORY.add(RegasireaInformatiilorpeWeb_ArchipAlexandru_roomC14);

		Offering RegasireaInformatiilorpeWebLABORATORY = new Offering(0, "Regasirea Informatiilor pe Web", 2, OfferingType.LABORATORY, DatePattern.FULL_TERM, instrmListRegasireaInformatiilorpeWebLABORATORY);
		offeringDAO.merge(RegasireaInformatiilorpeWebLABORATORY);


		CourseOffering RIW = new CourseOffering(0, "Regasirea Informatiilor pe Web", "RIW", RegasireaInformatiilorpeWebLECTURE, RegasireaInformatiilorpeWebLABORATORY);
		courseOfferingDAO.merge(RIW);


		InstructorMeetings Bazededateorientateobiect_GavrilescuMarius_roomAC02 = new InstructorMeetings(0, 0, GavrilescuMarius, roomAC02);

		List<InstructorMeetings> instrmListBazededateorientateobiectLECTURE = new ArrayList<InstructorMeetings>();
		instrmListBazededateorientateobiectLECTURE.add(Bazededateorientateobiect_GavrilescuMarius_roomAC02);

		Offering BazededateorientateobiectLECTURE = new Offering(0, "Baze de date orientate obiect", 2, OfferingType.LECTURE, DatePattern.FULL_TERM, instrmListBazededateorientateobiectLECTURE);
		offeringDAO.merge(BazededateorientateobiectLECTURE);


		InstructorMeetings Bazededateorientateobiect_GavrilescuMarius_roomC06 = new InstructorMeetings(0, 0, GavrilescuMarius, roomC06);

		List<InstructorMeetings> instrmListBazededateorientateobiectLABORATORY = new ArrayList<InstructorMeetings>();
		instrmListBazededateorientateobiectLABORATORY.add(Bazededateorientateobiect_GavrilescuMarius_roomC06);

		Offering BazededateorientateobiectLABORATORY = new Offering(0, "Baze de date orientate obiect", 2, OfferingType.LABORATORY, DatePattern.FULL_TERM, instrmListBazededateorientateobiectLABORATORY);
		offeringDAO.merge(BazededateorientateobiectLABORATORY);


		CourseOffering BDOO = new CourseOffering(0, "Baze de date orientate obiect", "BDOO", BazededateorientateobiectLECTURE, BazededateorientateobiectLABORATORY);
		courseOfferingDAO.merge(BDOO);


		InstructorMeetings Sistemedetimpreal_CascavalPetru_roomAC21 = new InstructorMeetings(0, 0, CascavalPetru, roomAC21);

		List<InstructorMeetings> instrmListSistemedetimprealLECTURE = new ArrayList<InstructorMeetings>();
		instrmListSistemedetimprealLECTURE.add(Sistemedetimpreal_CascavalPetru_roomAC21);

		Offering SistemedetimprealLECTURE = new Offering(0, "Sisteme de timp real", 2, OfferingType.LECTURE, DatePattern.FULL_TERM, instrmListSistemedetimprealLECTURE);
		offeringDAO.merge(SistemedetimprealLECTURE);


		InstructorMeetings Sistemedetimpreal_CascavalPetru_roomC212 = new InstructorMeetings(0, 0, CascavalPetru, roomC212);

		List<InstructorMeetings> instrmListSistemedetimprealLABORATORY = new ArrayList<InstructorMeetings>();
		instrmListSistemedetimprealLABORATORY.add(Sistemedetimpreal_CascavalPetru_roomC212);

		Offering SistemedetimprealLABORATORY = new Offering(0, "Sisteme de timp real", 2, OfferingType.LABORATORY, DatePattern.FULL_TERM, instrmListSistemedetimprealLABORATORY);
		offeringDAO.merge(SistemedetimprealLABORATORY);


		CourseOffering STR = new CourseOffering(0, "Sisteme de timp real", "STR", SistemedetimprealLECTURE, SistemedetimprealLABORATORY);
		courseOfferingDAO.merge(STR);


		InstructorMeetings Elementedegraficaasistatapecalculator_MantaVasile_roomAC02 = new InstructorMeetings(0, 0, MantaVasile, roomAC02);

		List<InstructorMeetings> instrmListElementedegraficaasistatapecalculatorLECTURE = new ArrayList<InstructorMeetings>();
		instrmListElementedegraficaasistatapecalculatorLECTURE.add(Elementedegraficaasistatapecalculator_MantaVasile_roomAC02);

		Offering ElementedegraficaasistatapecalculatorLECTURE = new Offering(0, "Elemente de grafica asistata pe calculator", 2, OfferingType.LECTURE, DatePattern.FULL_TERM, instrmListElementedegraficaasistatapecalculatorLECTURE);
		offeringDAO.merge(ElementedegraficaasistatapecalculatorLECTURE);


		InstructorMeetings Elementedegraficaasistatapecalculator_HerghelegiuPaul_roomC213 = new InstructorMeetings(0, 0, HerghelegiuPaul, roomC213);

		List<InstructorMeetings> instrmListElementedegraficaasistatapecalculatorLABORATORY = new ArrayList<InstructorMeetings>();
		instrmListElementedegraficaasistatapecalculatorLABORATORY.add(Elementedegraficaasistatapecalculator_HerghelegiuPaul_roomC213);

		Offering ElementedegraficaasistatapecalculatorLABORATORY = new Offering(0, "Elemente de grafica asistata pe calculator", 2, OfferingType.LABORATORY, DatePattern.FULL_TERM, instrmListElementedegraficaasistatapecalculatorLABORATORY);
		offeringDAO.merge(ElementedegraficaasistatapecalculatorLABORATORY);


		CourseOffering EGC = new CourseOffering(0, "Elemente de grafica asistata pe calculator", "EGC", ElementedegraficaasistatapecalculatorLECTURE, ElementedegraficaasistatapecalculatorLABORATORY);
		courseOfferingDAO.merge(EGC);


		InstructorMeetings Modelaresisimulare_CascavalPetru_roomAC02 = new InstructorMeetings(0, 0, CascavalPetru, roomAC02);

		List<InstructorMeetings> instrmListModelaresisimulareLECTURE = new ArrayList<InstructorMeetings>();
		instrmListModelaresisimulareLECTURE.add(Modelaresisimulare_CascavalPetru_roomAC02);

		Offering ModelaresisimulareLECTURE = new Offering(0, "Modelare si simulare", 2, OfferingType.LECTURE, DatePattern.FULL_TERM, instrmListModelaresisimulareLECTURE);
		offeringDAO.merge(ModelaresisimulareLECTURE);


		InstructorMeetings Modelaresisimulare_CascavalPetru_roomC212 = new InstructorMeetings(0, 0, CascavalPetru, roomC212);

		List<InstructorMeetings> instrmListModelaresisimulareLABORATORY = new ArrayList<InstructorMeetings>();
		instrmListModelaresisimulareLABORATORY.add(Modelaresisimulare_CascavalPetru_roomC212);

		Offering ModelaresisimulareLABORATORY = new Offering(0, "Modelare si simulare", 2, OfferingType.LABORATORY, DatePattern.FULL_TERM, instrmListModelaresisimulareLABORATORY);
		offeringDAO.merge(ModelaresisimulareLABORATORY);


		CourseOffering MS = new CourseOffering(0, "Modelare si simulare", "MS", ModelaresisimulareLECTURE, ModelaresisimulareLABORATORY);
		courseOfferingDAO.merge(MS);


		InstructorMeetings Achizitionareasiprelucrareadatelor_UngureanuFlorina_roomAC02 = new InstructorMeetings(0, 0, UngureanuFlorina, roomAC02);

		List<InstructorMeetings> instrmListAchizitionareasiprelucrareadatelorLECTURE = new ArrayList<InstructorMeetings>();
		instrmListAchizitionareasiprelucrareadatelorLECTURE.add(Achizitionareasiprelucrareadatelor_UngureanuFlorina_roomAC02);

		Offering AchizitionareasiprelucrareadatelorLECTURE = new Offering(0, "Achizitionarea si prelucrarea datelor", 2, OfferingType.LECTURE, DatePattern.FULL_TERM, instrmListAchizitionareasiprelucrareadatelorLECTURE);
		offeringDAO.merge(AchizitionareasiprelucrareadatelorLECTURE);


		InstructorMeetings Achizitionareasiprelucrareadatelor_UngureanuFlorina_roomC33 = new InstructorMeetings(0, 0, UngureanuFlorina, roomC33);

		List<InstructorMeetings> instrmListAchizitionareasiprelucrareadatelorLABORATORY = new ArrayList<InstructorMeetings>();
		instrmListAchizitionareasiprelucrareadatelorLABORATORY.add(Achizitionareasiprelucrareadatelor_UngureanuFlorina_roomC33);

		Offering AchizitionareasiprelucrareadatelorLABORATORY = new Offering(0, "Achizitionarea si prelucrarea datelor", 2, OfferingType.LABORATORY, DatePattern.FULL_TERM, instrmListAchizitionareasiprelucrareadatelorLABORATORY);
		offeringDAO.merge(AchizitionareasiprelucrareadatelorLABORATORY);


		CourseOffering APD = new CourseOffering(0, "Achizitionarea si prelucrarea datelor", "APD", AchizitionareasiprelucrareadatelorLECTURE, AchizitionareasiprelucrareadatelorLABORATORY);
		courseOfferingDAO.merge(APD);


		InstructorMeetings TehnologiaInformatiei_AlexandrescuAdrian_roomAC02 = new InstructorMeetings(0, 0, AlexandrescuAdrian, roomAC02);

		List<InstructorMeetings> instrmListTehnologiaInformatieiLECTURE = new ArrayList<InstructorMeetings>();
		instrmListTehnologiaInformatieiLECTURE.add(TehnologiaInformatiei_AlexandrescuAdrian_roomAC02);

		Offering TehnologiaInformatieiLECTURE = new Offering(0, "Tehnologia Informatiei", 2, OfferingType.LECTURE, DatePattern.FULL_TERM, instrmListTehnologiaInformatieiLECTURE);
		offeringDAO.merge(TehnologiaInformatieiLECTURE);


		InstructorMeetings TehnologiaInformatiei_AlexandrescuAdrian_roomC211 = new InstructorMeetings(0, 0, AlexandrescuAdrian, roomC211);

		List<InstructorMeetings> instrmListTehnologiaInformatieiLABORATORY = new ArrayList<InstructorMeetings>();
		instrmListTehnologiaInformatieiLABORATORY.add(TehnologiaInformatiei_AlexandrescuAdrian_roomC211);

		Offering TehnologiaInformatieiLABORATORY = new Offering(0, "Tehnologia Informatiei", 2, OfferingType.LABORATORY, DatePattern.FULL_TERM, instrmListTehnologiaInformatieiLABORATORY);
		offeringDAO.merge(TehnologiaInformatieiLABORATORY);


		CourseOffering TI = new CourseOffering(0, "Tehnologia Informatiei", "TI", TehnologiaInformatieiLECTURE, TehnologiaInformatieiLABORATORY);
		courseOfferingDAO.merge(TI);


		InstructorMeetings Reteledecalculatoare_BotezatuNicolae_roomAC02 = new InstructorMeetings(0, 0, BotezatuNicolae, roomAC02);

		List<InstructorMeetings> instrmListReteledecalculatoareLECTURE = new ArrayList<InstructorMeetings>();
		instrmListReteledecalculatoareLECTURE.add(Reteledecalculatoare_BotezatuNicolae_roomAC02);

		Offering ReteledecalculatoareLECTURE = new Offering(0, "Retele de calculatoare", 2, OfferingType.LECTURE, DatePattern.FULL_TERM, instrmListReteledecalculatoareLECTURE);
		offeringDAO.merge(ReteledecalculatoareLECTURE);


		InstructorMeetings Reteledecalculatoare_BotezatuNicolae_roomC03 = new InstructorMeetings(0, 0, BotezatuNicolae, roomC03);

		List<InstructorMeetings> instrmListReteledecalculatoareLABORATORY = new ArrayList<InstructorMeetings>();
		instrmListReteledecalculatoareLABORATORY.add(Reteledecalculatoare_BotezatuNicolae_roomC03);

		Offering ReteledecalculatoareLABORATORY = new Offering(0, "Retele de calculatoare", 2, OfferingType.LABORATORY, DatePattern.FULL_TERM, instrmListReteledecalculatoareLABORATORY);
		offeringDAO.merge(ReteledecalculatoareLABORATORY);


		CourseOffering RC = new CourseOffering(0, "Retele de calculatoare", "RC", ReteledecalculatoareLECTURE, ReteledecalculatoareLABORATORY);
		courseOfferingDAO.merge(RC);


		InstructorMeetings Bazededate_ButincuCristian_roomAC02 = new InstructorMeetings(0, 0, ButincuCristian, roomAC02);

		List<InstructorMeetings> instrmListBazededateLECTURE = new ArrayList<InstructorMeetings>();
		instrmListBazededateLECTURE.add(Bazededate_ButincuCristian_roomAC02);

		Offering BazededateLECTURE = new Offering(0, "Baze de date", 2, OfferingType.LECTURE, DatePattern.FULL_TERM, instrmListBazededateLECTURE);
		offeringDAO.merge(BazededateLECTURE);


		InstructorMeetings Bazededate_ButincuCristian_roomC13 = new InstructorMeetings(0, 0, ButincuCristian, roomC13);

		List<InstructorMeetings> instrmListBazededateLABORATORY = new ArrayList<InstructorMeetings>();
		instrmListBazededateLABORATORY.add(Bazededate_ButincuCristian_roomC13);

		Offering BazededateLABORATORY = new Offering(0, "Baze de date", 2, OfferingType.LABORATORY, DatePattern.FULL_TERM, instrmListBazededateLABORATORY);
		offeringDAO.merge(BazededateLABORATORY);


		CourseOffering BD = new CourseOffering(0, "Baze de date", "BD", BazededateLECTURE, BazededateLABORATORY);
		courseOfferingDAO.merge(BD);


		InstructorMeetings Sistemedeprelucraregrafica_MantaVasile_roomAC02 = new InstructorMeetings(0, 0, MantaVasile, roomAC02);

		List<InstructorMeetings> instrmListSistemedeprelucraregraficaLECTURE = new ArrayList<InstructorMeetings>();
		instrmListSistemedeprelucraregraficaLECTURE.add(Sistemedeprelucraregrafica_MantaVasile_roomAC02);

		Offering SistemedeprelucraregraficaLECTURE = new Offering(0, "Sisteme de prelucrare grafica", 2, OfferingType.LECTURE, DatePattern.FULL_TERM, instrmListSistemedeprelucraregraficaLECTURE);
		offeringDAO.merge(SistemedeprelucraregraficaLECTURE);


		InstructorMeetings Sistemedeprelucraregrafica_HerghelegiuPaul_roomC213 = new InstructorMeetings(0, 0, HerghelegiuPaul, roomC213);

		List<InstructorMeetings> instrmListSistemedeprelucraregraficaLABORATORY = new ArrayList<InstructorMeetings>();
		instrmListSistemedeprelucraregraficaLABORATORY.add(Sistemedeprelucraregrafica_HerghelegiuPaul_roomC213);

		Offering SistemedeprelucraregraficaLABORATORY = new Offering(0, "Sisteme de prelucrare grafica", 2, OfferingType.LABORATORY, DatePattern.FULL_TERM, instrmListSistemedeprelucraregraficaLABORATORY);
		offeringDAO.merge(SistemedeprelucraregraficaLABORATORY);


		CourseOffering SPG = new CourseOffering(0, "Sisteme de prelucrare grafica", "SPG", SistemedeprelucraregraficaLECTURE, SistemedeprelucraregraficaLABORATORY);
		courseOfferingDAO.merge(SPG);


		InstructorMeetings Proiectareastructurilorlogiceprogramabile_MonorCatalin_roomAC21 = new InstructorMeetings(0, 0, MonorCatalin, roomAC21);

		List<InstructorMeetings> instrmListProiectareastructurilorlogiceprogramabileLECTURE = new ArrayList<InstructorMeetings>();
		instrmListProiectareastructurilorlogiceprogramabileLECTURE.add(Proiectareastructurilorlogiceprogramabile_MonorCatalin_roomAC21);

		Offering ProiectareastructurilorlogiceprogramabileLECTURE = new Offering(0, "Proiectarea structurilor logice programabile", 2, OfferingType.LECTURE, DatePattern.FULL_TERM, instrmListProiectareastructurilorlogiceprogramabileLECTURE);
		offeringDAO.merge(ProiectareastructurilorlogiceprogramabileLECTURE);


		InstructorMeetings Proiectareastructurilorlogiceprogramabile_MonorCatalin_roomC28 = new InstructorMeetings(0, 0, MonorCatalin, roomC28);

		List<InstructorMeetings> instrmListProiectareastructurilorlogiceprogramabileLABORATORY = new ArrayList<InstructorMeetings>();
		instrmListProiectareastructurilorlogiceprogramabileLABORATORY.add(Proiectareastructurilorlogiceprogramabile_MonorCatalin_roomC28);

		Offering ProiectareastructurilorlogiceprogramabileLABORATORY = new Offering(0, "Proiectarea structurilor logice programabile", 2, OfferingType.LABORATORY, DatePattern.FULL_TERM, instrmListProiectareastructurilorlogiceprogramabileLABORATORY);
		offeringDAO.merge(ProiectareastructurilorlogiceprogramabileLABORATORY);


		CourseOffering PSLP = new CourseOffering(0, "Proiectarea structurilor logice programabile", "PSLP", ProiectareastructurilorlogiceprogramabileLECTURE, ProiectareastructurilorlogiceprogramabileLABORATORY);
		courseOfferingDAO.merge(PSLP);


		InstructorMeetings Proiectareacumicroprocesoare_PantelimonescuFlorin_roomAC21 = new InstructorMeetings(0, 0, PantelimonescuFlorin, roomAC21);

		List<InstructorMeetings> instrmListProiectareacumicroprocesoareLECTURE = new ArrayList<InstructorMeetings>();
		instrmListProiectareacumicroprocesoareLECTURE.add(Proiectareacumicroprocesoare_PantelimonescuFlorin_roomAC21);

		Offering ProiectareacumicroprocesoareLECTURE = new Offering(0, "Proiectarea cu microprocesoare", 2, OfferingType.LECTURE, DatePattern.FULL_TERM, instrmListProiectareacumicroprocesoareLECTURE);
		offeringDAO.merge(ProiectareacumicroprocesoareLECTURE);


		InstructorMeetings Proiectareacumicroprocesoare_PantelimonescuFlorin_roomC214 = new InstructorMeetings(0, 0, PantelimonescuFlorin, roomC214);

		List<InstructorMeetings> instrmListProiectareacumicroprocesoareLABORATORY = new ArrayList<InstructorMeetings>();
		instrmListProiectareacumicroprocesoareLABORATORY.add(Proiectareacumicroprocesoare_PantelimonescuFlorin_roomC214);

		Offering ProiectareacumicroprocesoareLABORATORY = new Offering(0, "Proiectarea cu microprocesoare", 2, OfferingType.LABORATORY, DatePattern.FULL_TERM, instrmListProiectareacumicroprocesoareLABORATORY);
		offeringDAO.merge(ProiectareacumicroprocesoareLABORATORY);


		CourseOffering PM = new CourseOffering(0, "Proiectarea cu microprocesoare", "PM", ProiectareacumicroprocesoareLECTURE, ProiectareacumicroprocesoareLABORATORY);
		courseOfferingDAO.merge(PM);


		InstructorMeetings Structuridedate_UngureanuFlorina_roomAC02 = new InstructorMeetings(0, 0, UngureanuFlorina, roomAC02);

		List<InstructorMeetings> instrmListStructuridedateLECTURE = new ArrayList<InstructorMeetings>();
		instrmListStructuridedateLECTURE.add(Structuridedate_UngureanuFlorina_roomAC02);

		Offering StructuridedateLECTURE = new Offering(0, "Structuri de date", 2, OfferingType.LECTURE, DatePattern.FULL_TERM, instrmListStructuridedateLECTURE);
		offeringDAO.merge(StructuridedateLECTURE);


		InstructorMeetings Structuridedate_UngureanuFlorina_roomC04 = new InstructorMeetings(0, 0, UngureanuFlorina, roomC04);

		List<InstructorMeetings> instrmListStructuridedateLABORATORY = new ArrayList<InstructorMeetings>();
		instrmListStructuridedateLABORATORY.add(Structuridedate_UngureanuFlorina_roomC04);

		Offering StructuridedateLABORATORY = new Offering(0, "Structuri de date", 2, OfferingType.LABORATORY, DatePattern.FULL_TERM, instrmListStructuridedateLABORATORY);
		offeringDAO.merge(StructuridedateLABORATORY);


		CourseOffering SD = new CourseOffering(0, "Structuri de date", "SD", StructuridedateLECTURE, StructuridedateLABORATORY);
		courseOfferingDAO.merge(SD);


		InstructorMeetings Programareorientatapeobiecte_LupuRobert_roomAC02 = new InstructorMeetings(0, 0, LupuRobert, roomAC02);

		List<InstructorMeetings> instrmListProgramareorientatapeobiecteLECTURE = new ArrayList<InstructorMeetings>();
		instrmListProgramareorientatapeobiecteLECTURE.add(Programareorientatapeobiecte_LupuRobert_roomAC02);

		Offering ProgramareorientatapeobiecteLECTURE = new Offering(0, "Programare orientata pe obiecte", 2, OfferingType.LECTURE, DatePattern.FULL_TERM, instrmListProgramareorientatapeobiecteLECTURE);
		offeringDAO.merge(ProgramareorientatapeobiecteLECTURE);


		InstructorMeetings Programareorientatapeobiecte_LupuRobert_roomC04 = new InstructorMeetings(0, 0, LupuRobert, roomC04);

		List<InstructorMeetings> instrmListProgramareorientatapeobiecteLABORATORY = new ArrayList<InstructorMeetings>();
		instrmListProgramareorientatapeobiecteLABORATORY.add(Programareorientatapeobiecte_LupuRobert_roomC04);

		Offering ProgramareorientatapeobiecteLABORATORY = new Offering(0, "Programare orientata pe obiecte", 2, OfferingType.LABORATORY, DatePattern.FULL_TERM, instrmListProgramareorientatapeobiecteLABORATORY);
		offeringDAO.merge(ProgramareorientatapeobiecteLABORATORY);


		CourseOffering POO = new CourseOffering(0, "Programare orientata pe obiecte", "POO", ProgramareorientatapeobiecteLECTURE, ProgramareorientatapeobiecteLABORATORY);
		courseOfferingDAO.merge(POO);


		InstructorMeetings Teoriasistemelor_KloetzerMarius_roomAC02 = new InstructorMeetings(0, 0, KloetzerMarius, roomAC02);

		List<InstructorMeetings> instrmListTeoriasistemelorLECTURE = new ArrayList<InstructorMeetings>();
		instrmListTeoriasistemelorLECTURE.add(Teoriasistemelor_KloetzerMarius_roomAC02);

		Offering TeoriasistemelorLECTURE = new Offering(0, "Teoria sistemelor", 2, OfferingType.LECTURE, DatePattern.FULL_TERM, instrmListTeoriasistemelorLECTURE);
		offeringDAO.merge(TeoriasistemelorLECTURE);


		InstructorMeetings Teoriasistemelor_KloetzerMarius_roomA113 = new InstructorMeetings(0, 0, KloetzerMarius, roomA113);

		List<InstructorMeetings> instrmListTeoriasistemelorLABORATORY = new ArrayList<InstructorMeetings>();
		instrmListTeoriasistemelorLABORATORY.add(Teoriasistemelor_KloetzerMarius_roomA113);

		Offering TeoriasistemelorLABORATORY = new Offering(0, "Teoria sistemelor", 2, OfferingType.LABORATORY, DatePattern.FULL_TERM, instrmListTeoriasistemelorLABORATORY);
		offeringDAO.merge(TeoriasistemelorLABORATORY);


		CourseOffering TS = new CourseOffering(0, "Teoria sistemelor", "TS", TeoriasistemelorLECTURE, TeoriasistemelorLABORATORY);
		courseOfferingDAO.merge(TS);


		InstructorMeetings Arhitecturasistemelordecalcul_StanAndrei_roomT4 = new InstructorMeetings(0, 0, StanAndrei, roomT4);

		List<InstructorMeetings> instrmListArhitecturasistemelordecalculLECTURE = new ArrayList<InstructorMeetings>();
		instrmListArhitecturasistemelordecalculLECTURE.add(Arhitecturasistemelordecalcul_StanAndrei_roomT4);

		Offering ArhitecturasistemelordecalculLECTURE = new Offering(0, "Arhitectura sistemelor de calcul", 3, OfferingType.LECTURE, DatePattern.FULL_TERM, instrmListArhitecturasistemelordecalculLECTURE);
		offeringDAO.merge(ArhitecturasistemelordecalculLECTURE);


		InstructorMeetings Arhitecturasistemelordecalcul_StanAndrei_roomC33 = new InstructorMeetings(0, 0, StanAndrei, roomC33);

		List<InstructorMeetings> instrmListArhitecturasistemelordecalculLABORATORY = new ArrayList<InstructorMeetings>();
		instrmListArhitecturasistemelordecalculLABORATORY.add(Arhitecturasistemelordecalcul_StanAndrei_roomC33);

		Offering ArhitecturasistemelordecalculLABORATORY = new Offering(0, "Arhitectura sistemelor de calcul", 2, OfferingType.LABORATORY, DatePattern.FULL_TERM, instrmListArhitecturasistemelordecalculLABORATORY);
		offeringDAO.merge(ArhitecturasistemelordecalculLABORATORY);


		CourseOffering ASC = new CourseOffering(0, "Arhitectura sistemelor de calcul", "ASC", ArhitecturasistemelordecalculLECTURE, ArhitecturasistemelordecalculLABORATORY);
		courseOfferingDAO.merge(ASC);


		InstructorMeetings Proiectareaalgoritmilor_CrausMitica_roomT4 = new InstructorMeetings(0, 0, CrausMitica, roomT4);

		List<InstructorMeetings> instrmListProiectareaalgoritmilorLECTURE = new ArrayList<InstructorMeetings>();
		instrmListProiectareaalgoritmilorLECTURE.add(Proiectareaalgoritmilor_CrausMitica_roomT4);

		Offering ProiectareaalgoritmilorLECTURE = new Offering(0, "Proiectarea algoritmilor", 3, OfferingType.LECTURE, DatePattern.FULL_TERM, instrmListProiectareaalgoritmilorLECTURE);
		offeringDAO.merge(ProiectareaalgoritmilorLECTURE);


		InstructorMeetings Proiectareaalgoritmilor_CrausMitica_roomC14 = new InstructorMeetings(0, 0, CrausMitica, roomC14);

		List<InstructorMeetings> instrmListProiectareaalgoritmilorLABORATORY = new ArrayList<InstructorMeetings>();
		instrmListProiectareaalgoritmilorLABORATORY.add(Proiectareaalgoritmilor_CrausMitica_roomC14);

		Offering ProiectareaalgoritmilorLABORATORY = new Offering(0, "Proiectarea algoritmilor", 2, OfferingType.LABORATORY, DatePattern.FULL_TERM, instrmListProiectareaalgoritmilorLABORATORY);
		offeringDAO.merge(ProiectareaalgoritmilorLABORATORY);


		CourseOffering PA = new CourseOffering(0, "Proiectarea algoritmilor", "PA", ProiectareaalgoritmilorLECTURE, ProiectareaalgoritmilorLABORATORY);
		courseOfferingDAO.merge(PA);


		InstructorMeetings Matematicadiscreta_SerbanElena_roomT4 = new InstructorMeetings(0, 0, SerbanElena, roomT4);

		List<InstructorMeetings> instrmListMatematicadiscretaLECTURE = new ArrayList<InstructorMeetings>();
		instrmListMatematicadiscretaLECTURE.add(Matematicadiscreta_SerbanElena_roomT4);

		Offering MatematicadiscretaLECTURE = new Offering(0, "Matematica discreta", 2, OfferingType.LECTURE, DatePattern.FULL_TERM, instrmListMatematicadiscretaLECTURE);
		offeringDAO.merge(MatematicadiscretaLECTURE);


		InstructorMeetings Matematicadiscreta_SerbanElena_roomC03 = new InstructorMeetings(0, 0, SerbanElena, roomC03);

		List<InstructorMeetings> instrmListMatematicadiscretaLABORATORY = new ArrayList<InstructorMeetings>();
		instrmListMatematicadiscretaLABORATORY.add(Matematicadiscreta_SerbanElena_roomC03);

		Offering MatematicadiscretaLABORATORY = new Offering(0, "Matematica discreta", 2, OfferingType.LABORATORY, DatePattern.FULL_TERM, instrmListMatematicadiscretaLABORATORY);
		offeringDAO.merge(MatematicadiscretaLABORATORY);


		CourseOffering MD = new CourseOffering(0, "Matematica discreta", "MD", MatematicadiscretaLECTURE, MatematicadiscretaLABORATORY);
		courseOfferingDAO.merge(MD);


		InstructorMeetings AlgebrasianalizamatematicaI_StrugariuRadu_roomE1 = new InstructorMeetings(0, 0, StrugariuRadu, roomE1);

		List<InstructorMeetings> instrmListAlgebrasianalizamatematicaILECTURE = new ArrayList<InstructorMeetings>();
		instrmListAlgebrasianalizamatematicaILECTURE.add(AlgebrasianalizamatematicaI_StrugariuRadu_roomE1);

		Offering AlgebrasianalizamatematicaILECTURE = new Offering(0, "Algebra si analiza matematica I", 2, OfferingType.LECTURE, DatePattern.FULL_TERM, instrmListAlgebrasianalizamatematicaILECTURE);
		offeringDAO.merge(AlgebrasianalizamatematicaILECTURE);


		InstructorMeetings AlgebrasianalizamatematicaI_StrugariuRadu_roomAC03 = new InstructorMeetings(0, 0, StrugariuRadu, roomAC03);

		List<InstructorMeetings> instrmListAlgebrasianalizamatematicaISEMINARY = new ArrayList<InstructorMeetings>();
		instrmListAlgebrasianalizamatematicaISEMINARY.add(AlgebrasianalizamatematicaI_StrugariuRadu_roomAC03);

		Offering AlgebrasianalizamatematicaISEMINARY = new Offering(0, "Algebra si analiza matematica I", 2, OfferingType.SEMINARY, DatePattern.FULL_TERM, instrmListAlgebrasianalizamatematicaISEMINARY);
		offeringDAO.merge(AlgebrasianalizamatematicaISEMINARY);


		CourseOffering AAMI = new CourseOffering(0, "Algebra si analiza matematica I", "AAMI", AlgebrasianalizamatematicaILECTURE, AlgebrasianalizamatematicaISEMINARY);
		courseOfferingDAO.merge(AAMI);


		InstructorMeetings AlgebrasianalizamatematicaII_PleteaAriadna_roomE1 = new InstructorMeetings(0, 0, PleteaAriadna, roomE1);

		List<InstructorMeetings> instrmListAlgebrasianalizamatematicaIILECTURE = new ArrayList<InstructorMeetings>();
		instrmListAlgebrasianalizamatematicaIILECTURE.add(AlgebrasianalizamatematicaII_PleteaAriadna_roomE1);

		Offering AlgebrasianalizamatematicaIILECTURE = new Offering(0, "Algebra si analiza matematica II", 2, OfferingType.LECTURE, DatePattern.FULL_TERM, instrmListAlgebrasianalizamatematicaIILECTURE);
		offeringDAO.merge(AlgebrasianalizamatematicaIILECTURE);


		InstructorMeetings AlgebrasianalizamatematicaII_PleteaAriadna_roomAC03 = new InstructorMeetings(0, 0, PleteaAriadna, roomAC03);

		List<InstructorMeetings> instrmListAlgebrasianalizamatematicaIISEMINARY = new ArrayList<InstructorMeetings>();
		instrmListAlgebrasianalizamatematicaIISEMINARY.add(AlgebrasianalizamatematicaII_PleteaAriadna_roomAC03);

		Offering AlgebrasianalizamatematicaIISEMINARY = new Offering(0, "Algebra si analiza matematica II", 2, OfferingType.SEMINARY, DatePattern.FULL_TERM, instrmListAlgebrasianalizamatematicaIISEMINARY);
		offeringDAO.merge(AlgebrasianalizamatematicaIISEMINARY);


		CourseOffering AAMII = new CourseOffering(0, "Algebra si analiza matematica II", "AAMII", AlgebrasianalizamatematicaIILECTURE, AlgebrasianalizamatematicaIISEMINARY);
		courseOfferingDAO.merge(AAMII);


		InstructorMeetings Graficaasistatapecalculator_TomaAnaMaria_roomE1 = new InstructorMeetings(0, 0, TomaAnaMaria, roomE1);

		List<InstructorMeetings> instrmListGraficaasistatapecalculatorLECTURE = new ArrayList<InstructorMeetings>();
		instrmListGraficaasistatapecalculatorLECTURE.add(Graficaasistatapecalculator_TomaAnaMaria_roomE1);

		Offering GraficaasistatapecalculatorLECTURE = new Offering(0, "Grafica asistata pe calculator", 2, OfferingType.LECTURE, DatePattern.FULL_TERM, instrmListGraficaasistatapecalculatorLECTURE);
		offeringDAO.merge(GraficaasistatapecalculatorLECTURE);


		InstructorMeetings Graficaasistatapecalculator_TomaAnaMaria_roomC04 = new InstructorMeetings(0, 0, TomaAnaMaria, roomC04);

		List<InstructorMeetings> instrmListGraficaasistatapecalculatorLABORATORY = new ArrayList<InstructorMeetings>();
		instrmListGraficaasistatapecalculatorLABORATORY.add(Graficaasistatapecalculator_TomaAnaMaria_roomC04);

		Offering GraficaasistatapecalculatorLABORATORY = new Offering(0, "Grafica asistata pe calculator", 2, OfferingType.LABORATORY, DatePattern.FULL_TERM, instrmListGraficaasistatapecalculatorLABORATORY);
		offeringDAO.merge(GraficaasistatapecalculatorLABORATORY);


		CourseOffering GAC = new CourseOffering(0, "Grafica asistata pe calculator", "GAC", GraficaasistatapecalculatorLECTURE, GraficaasistatapecalculatorLABORATORY);
		courseOfferingDAO.merge(GAC);


		InstructorMeetings Programareacalculatoarelor_SerbanElena_roomE1 = new InstructorMeetings(0, 0, SerbanElena, roomE1);

		List<InstructorMeetings> instrmListProgramareacalculatoarelorLECTURE = new ArrayList<InstructorMeetings>();
		instrmListProgramareacalculatoarelorLECTURE.add(Programareacalculatoarelor_SerbanElena_roomE1);

		Offering ProgramareacalculatoarelorLECTURE = new Offering(0, "Programarea calculatoarelor", 2, OfferingType.LECTURE, DatePattern.FULL_TERM, instrmListProgramareacalculatoarelorLECTURE);
		offeringDAO.merge(ProgramareacalculatoarelorLECTURE);


		InstructorMeetings Programareacalculatoarelor_SerbanElena_roomC02 = new InstructorMeetings(0, 0, SerbanElena, roomC02);

		List<InstructorMeetings> instrmListProgramareacalculatoarelorLABORATORY = new ArrayList<InstructorMeetings>();
		instrmListProgramareacalculatoarelorLABORATORY.add(Programareacalculatoarelor_SerbanElena_roomC02);

		Offering ProgramareacalculatoarelorLABORATORY = new Offering(0, "Programarea calculatoarelor", 2, OfferingType.LABORATORY, DatePattern.FULL_TERM, instrmListProgramareacalculatoarelorLABORATORY);
		offeringDAO.merge(ProgramareacalculatoarelorLABORATORY);


		CourseOffering PC = new CourseOffering(0, "Programarea calculatoarelor", "PC", ProgramareacalculatoarelorLECTURE, ProgramareacalculatoarelorLABORATORY);
		courseOfferingDAO.merge(PC);


		InstructorMeetings Metodenumerice_MireaLetitia_roomE1 = new InstructorMeetings(0, 0, MireaLetitia, roomE1);

		List<InstructorMeetings> instrmListMetodenumericeLECTURE = new ArrayList<InstructorMeetings>();
		instrmListMetodenumericeLECTURE.add(Metodenumerice_MireaLetitia_roomE1);

		Offering MetodenumericeLECTURE = new Offering(0, "Metode numerice", 2, OfferingType.LECTURE, DatePattern.FULL_TERM, instrmListMetodenumericeLECTURE);
		offeringDAO.merge(MetodenumericeLECTURE);


		InstructorMeetings Metodenumerice_MireaLetitia_roomA19 = new InstructorMeetings(0, 0, MireaLetitia, roomA19);

		List<InstructorMeetings> instrmListMetodenumericeLABORATORY = new ArrayList<InstructorMeetings>();
		instrmListMetodenumericeLABORATORY.add(Metodenumerice_MireaLetitia_roomA19);

		Offering MetodenumericeLABORATORY = new Offering(0, "Metode numerice", 2, OfferingType.LABORATORY, DatePattern.FULL_TERM, instrmListMetodenumericeLABORATORY);
		offeringDAO.merge(MetodenumericeLABORATORY);


		CourseOffering MN = new CourseOffering(0, "Metode numerice", "MN", MetodenumericeLECTURE, MetodenumericeLABORATORY);
		courseOfferingDAO.merge(MN);


		InstructorMeetings Fizica_CiobanuBrandusa_roomE1 = new InstructorMeetings(0, 0, CiobanuBrandusa, roomE1);

		List<InstructorMeetings> instrmListFizicaLECTURE = new ArrayList<InstructorMeetings>();
		instrmListFizicaLECTURE.add(Fizica_CiobanuBrandusa_roomE1);

		Offering FizicaLECTURE = new Offering(0, "Fizica", 3, OfferingType.LECTURE, DatePattern.FULL_TERM, instrmListFizicaLECTURE);
		offeringDAO.merge(FizicaLECTURE);


		InstructorMeetings Fizica_CiobanuBrandusa_roomA19 = new InstructorMeetings(0, 0, CiobanuBrandusa, roomA19);

		List<InstructorMeetings> instrmListFizicaLABORATORY = new ArrayList<InstructorMeetings>();
		instrmListFizicaLABORATORY.add(Fizica_CiobanuBrandusa_roomA19);

		Offering FizicaLABORATORY = new Offering(0, "Fizica", 2, OfferingType.LABORATORY, DatePattern.FULL_TERM, instrmListFizicaLABORATORY);
		offeringDAO.merge(FizicaLABORATORY);


		CourseOffering Fizica = new CourseOffering(0, "Fizica", "Fizica", FizicaLECTURE, FizicaLABORATORY);
		courseOfferingDAO.merge(Fizica);


		CurriculaDAO curriculaDAO = new CurriculaJPADAOService();

		List<CourseOffering> AnulPatruSemICCurriculaList = new ArrayList<CourseOffering>();
		AnulPatruSemICCurriculaList.add(AlPD);
		AnulPatruSemICCurriculaList.add(IA);
		AnulPatruSemICCurriculaList.add(IP);
		AnulPatruSemICCurriculaList.add(PCW);

		Curricula AnulPatruSemIC = new Curricula(0, "AnulPatruSemIC", CollegeYear.FOURTH, Term.FIRST, AnulPatruSemICCurriculaList);
		curriculaDAO.merge(AnulPatruSemIC);


		List<CourseOffering> AnulPatruSemITICurriculaList = new ArrayList<CourseOffering>();
		AnulPatruSemITICurriculaList.add(AlPD);
		AnulPatruSemITICurriculaList.add(IA);
		AnulPatruSemITICurriculaList.add(SM);

		Curricula AnulPatruSemITI = new Curricula(0, "AnulPatruSemITI", CollegeYear.FOURTH, Term.FIRST, AnulPatruSemITICurriculaList);
		curriculaDAO.merge(AnulPatruSemITI);


		List<CourseOffering> AnulPatruSemIICCurriculaList = new ArrayList<CourseOffering>();
		AnulPatruSemIICCurriculaList.add(LFT);
		AnulPatruSemIICCurriculaList.add(STR);

		Curricula AnulPatruSemIIC = new Curricula(0, "AnulPatruSemIIC", CollegeYear.FOURTH, Term.SECOND, AnulPatruSemIICCurriculaList);
		curriculaDAO.merge(AnulPatruSemIIC);


		List<CourseOffering> AnulPatruSemIITICurriculaList = new ArrayList<CourseOffering>();
		AnulPatruSemIITICurriculaList.add(LFT);
		AnulPatruSemIITICurriculaList.add(RIW);
		AnulPatruSemIITICurriculaList.add(BDOO);

		Curricula AnulPatruSemIITI = new Curricula(0, "AnulPatruSemIITI", CollegeYear.FOURTH, Term.SECOND, AnulPatruSemIITICurriculaList);
		curriculaDAO.merge(AnulPatruSemIITI);


		List<CourseOffering> AnulTreiSemICCurriculaList = new ArrayList<CourseOffering>();
		AnulTreiSemICCurriculaList.add(EGC);
		AnulTreiSemICCurriculaList.add(MS);
		AnulTreiSemICCurriculaList.add(APD);
		AnulTreiSemICCurriculaList.add(TI);

		Curricula AnulTreiSemIC = new Curricula(0, "AnulTreiSemIC", CollegeYear.THIRD, Term.FIRST, AnulTreiSemICCurriculaList);
		curriculaDAO.merge(AnulTreiSemIC);


		List<CourseOffering> AnulTreiSemITICurriculaList = new ArrayList<CourseOffering>();
		AnulTreiSemITICurriculaList.add(EGC);
		AnulTreiSemITICurriculaList.add(MS);
		AnulTreiSemITICurriculaList.add(APD);

		Curricula AnulTreiSemITI = new Curricula(0, "AnulTreiSemITI", CollegeYear.THIRD, Term.FIRST, AnulTreiSemITICurriculaList);
		curriculaDAO.merge(AnulTreiSemITI);


		List<CourseOffering> AnulTreiSemIICCurriculaList = new ArrayList<CourseOffering>();
		AnulTreiSemIICCurriculaList.add(RC);
		AnulTreiSemIICCurriculaList.add(BD);
		AnulTreiSemIICCurriculaList.add(PSLP);
		AnulTreiSemIICCurriculaList.add(PM);

		Curricula AnulTreiSemIIC = new Curricula(0, "AnulTreiSemIIC", CollegeYear.THIRD, Term.SECOND, AnulTreiSemIICCurriculaList);
		curriculaDAO.merge(AnulTreiSemIIC);


		List<CourseOffering> AnulTreiSemIITICurriculaList = new ArrayList<CourseOffering>();
		AnulTreiSemIITICurriculaList.add(RC);
		AnulTreiSemIITICurriculaList.add(BD);
		AnulTreiSemIITICurriculaList.add(SPG);

		Curricula AnulTreiSemIITI = new Curricula(0, "AnulTreiSemIITI", CollegeYear.THIRD, Term.SECOND, AnulTreiSemIITICurriculaList);
		curriculaDAO.merge(AnulTreiSemIITI);


		List<CourseOffering> AnulDoiSemICTICurriculaList = new ArrayList<CourseOffering>();
		AnulDoiSemICTICurriculaList.add(SD);
		AnulDoiSemICTICurriculaList.add(POO);
		AnulDoiSemICTICurriculaList.add(TS);

		Curricula AnulDoiSemICTI = new Curricula(0, "AnulDoiSemICTI", CollegeYear.SECOND, Term.FIRST, AnulDoiSemICTICurriculaList);
		curriculaDAO.merge(AnulDoiSemICTI);


		List<CourseOffering> AnulDoiSemIICTICurriculaList = new ArrayList<CourseOffering>();
		AnulDoiSemIICTICurriculaList.add(ASC);
		AnulDoiSemIICTICurriculaList.add(PA);

		Curricula AnulDoiSemIICTI = new Curricula(0, "AnulDoiSemIICTI", CollegeYear.SECOND, Term.SECOND, AnulDoiSemIICTICurriculaList);
		curriculaDAO.merge(AnulDoiSemIICTI);


		List<CourseOffering> AnulIntaiSemICTICurriculaList = new ArrayList<CourseOffering>();
		AnulIntaiSemICTICurriculaList.add(MD);
		AnulIntaiSemICTICurriculaList.add(AAMI);
		AnulIntaiSemICTICurriculaList.add(AAMII);
		AnulIntaiSemICTICurriculaList.add(GAC);

		Curricula AnulIntaiSemICTI = new Curricula(0, "AnulIntaiSemICTI", CollegeYear.FIRST, Term.FIRST, AnulIntaiSemICTICurriculaList);
		curriculaDAO.merge(AnulIntaiSemICTI);


		List<CourseOffering> AnulIntaiSemIICTICurriculaList = new ArrayList<CourseOffering>();
		AnulIntaiSemIICTICurriculaList.add(PC);
		AnulIntaiSemIICTICurriculaList.add(MN);
		AnulIntaiSemIICTICurriculaList.add(Fizica);

		Curricula AnulIntaiSemIICTI = new Curricula(0, "AnulIntaiSemIICTI", CollegeYear.FIRST, Term.SECOND, AnulIntaiSemIICTICurriculaList);
		curriculaDAO.merge(AnulIntaiSemIICTI);


		SubjectAreaDAO subjectAreaDAO = new SubjectAreaJPADAOService();

		SubjectArea AnulPatruC = new SubjectArea(0, "AnulPatruC", "C", AnulPatruSemIC, AnulPatruSemIIC);
		subjectAreaDAO.merge(AnulPatruC);


		SubjectArea AnulPatruTI = new SubjectArea(0, "AnulPatruTI", "TI", AnulPatruSemITI, AnulPatruSemIITI);
		subjectAreaDAO.merge(AnulPatruTI);


		SubjectArea AnulTreiC = new SubjectArea(0, "AnulTreiC", "C", AnulTreiSemIC, AnulTreiSemIIC);
		subjectAreaDAO.merge(AnulTreiC);


		SubjectArea AnulTreiTI = new SubjectArea(0, "AnulTreiTI", "TI", AnulTreiSemITI, AnulTreiSemIITI);
		subjectAreaDAO.merge(AnulTreiTI);


		SubjectArea AnulDoiCTI = new SubjectArea(0, "AnulDoiCTI", "CTI", AnulDoiSemICTI, AnulDoiSemIICTI);
		subjectAreaDAO.merge(AnulDoiCTI);


		SubjectArea AnulIntaiCTI = new SubjectArea(0, "AnulIntaiCTI", "CTI", AnulIntaiSemICTI, AnulIntaiSemIICTI);
		subjectAreaDAO.merge(AnulIntaiCTI);


		YearOfStudyDAO yearOfStudyDAO = new YearOfStudyJPADAOService();

		List<SubjectArea> subjectAreasListForFOURTH_CTI = new ArrayList<SubjectArea>();
		subjectAreasListForFOURTH_CTI.add(AnulPatruC);
		subjectAreasListForFOURTH_CTI.add(AnulPatruTI);

		YearOfStudy FOURTH_CTI = new YearOfStudy(0, "FOURTH_CTI", CollegeYear.FOURTH, subjectAreasListForFOURTH_CTI);
		yearOfStudyDAO.merge(FOURTH_CTI);


		List<SubjectArea> subjectAreasListForTHIRD_CTI = new ArrayList<SubjectArea>();
		subjectAreasListForTHIRD_CTI.add(AnulTreiC);
		subjectAreasListForTHIRD_CTI.add(AnulTreiTI);

		YearOfStudy THIRD_CTI = new YearOfStudy(0, "THIRD_CTI", CollegeYear.THIRD, subjectAreasListForTHIRD_CTI);
		yearOfStudyDAO.merge(THIRD_CTI);


		List<SubjectArea> subjectAreasListForSECOND_CTI = new ArrayList<SubjectArea>();
		subjectAreasListForSECOND_CTI.add(AnulDoiCTI);

		YearOfStudy SECOND_CTI = new YearOfStudy(0, "SECOND_CTI", CollegeYear.SECOND, subjectAreasListForSECOND_CTI);
		yearOfStudyDAO.merge(SECOND_CTI);


		List<SubjectArea> subjectAreasListForFIRST_CTI = new ArrayList<SubjectArea>();
		subjectAreasListForFIRST_CTI.add(AnulIntaiCTI);

		YearOfStudy FIRST_CTI = new YearOfStudy(0, "FIRST_CTI", CollegeYear.FIRST, subjectAreasListForFIRST_CTI);
		yearOfStudyDAO.merge(FIRST_CTI);


		DepartmentDAO departmentDAO = new DepartmentJPADAOService();

		List<YearOfStudy> yearsOfStudyListForCTI = new ArrayList<YearOfStudy>();
		yearsOfStudyListForCTI.add(FOURTH_CTI);
		yearsOfStudyListForCTI.add(THIRD_CTI);
		yearsOfStudyListForCTI.add(SECOND_CTI);
		yearsOfStudyListForCTI.add(FIRST_CTI);

		Department CTI = new Department(0, "CTI", yearsOfStudyListForCTI);
		departmentDAO.merge(CTI);


		List<YearOfStudy> yearsOfStudyListForIS = new ArrayList<YearOfStudy>();

		Department IS = new Department(0, "IS", yearsOfStudyListForIS);
		departmentDAO.merge(IS);




		
		// From here i wrote everything by hand
		PasswordAuthentication passAuth = new PasswordAuthentication();

		AcademicSession fall = new AcademicSession(0, "Toamna-Iarna", Term.FIRST, new GregorianCalendar(2016, 10, 3),
				new GregorianCalendar(2017, 1, 20), new GregorianCalendar(2017, 1, 23),
				new GregorianCalendar(2017, 2, 5), null);
		AcademicSession spring = new AcademicSession(0, "Primavara-Vara", Term.SECOND,
				new GregorianCalendar(2017, 2, 13), new GregorianCalendar(2017, 5, 26),
				new GregorianCalendar(2017, 5, 29), new GregorianCalendar(2017, 6, 18), null);
		AcademicYear academicYear = new AcademicYear(0, "2016-2017", academicYear2016_2017, fall, spring);
		List<Department> departments = new ArrayList<Department>();
		departments.add(CTI);
		departments.add(IS);
		List<AcademicYear> academicYears = new ArrayList<AcademicYear>();
		academicYears.add(academicYear);
		Faculty faculty = new Faculty(0, "Automatica si Calculatoare", academicYears, departments);
		FacultyDAO facultyDAO = new FacultyJPADAOService();
		facultyDAO.merge(faculty);

		// Insert users here
		char[] passwordAdmin = { 'a', 'd', 'm', 'i', 'n' };
		User userAdmin = new User(0, "Johny", "Test", "admin", passAuth.hash(passwordAdmin), User.UserType.ADMIN);
		char[] passwordSecretary = { 's', 'c', 't', 'r' };
		User userSecretary = new User(0, "Johny", "Test", "sctr", passAuth.hash(passwordSecretary),
				User.UserType.SECRETARY);
		char[] passwordInstructor = { 'i', 'n', 's', 't', 'r' };
		User userInstructor = new User(0, "Johny", "Test", "instr", passAuth.hash(passwordInstructor),
				User.UserType.INSTRUCTOR);
		char[] passwordGuest = { 'g', 'u', 'e', 's', 't' };
		User userGuest = new User(0, "Johny", "Test", "guest", passAuth.hash(passwordGuest), User.UserType.GUEST);

		UserJPADAOService userDAO = new UserJPADAOService();
		userDAO.merge(userAdmin);
		userDAO.merge(userSecretary);
		userDAO.merge(userInstructor);
		userDAO.merge(userGuest);
	}
}