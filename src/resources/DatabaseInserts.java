//package resources;
//
//import java.util.ArrayList;
//import java.util.GregorianCalendar;
//import java.util.List;
//
//import timetablepuzzle.eclipselink.DAO.JPA.services.administration.UserJPADAOService;
//import timetablepuzzle.eclipselink.DAO.JPA.services.inputdata.InstructorJPADAOService;
//import timetablepuzzle.eclipselink.DAO.JPA.services.inputdata.RoomJPADAOService;
//import timetablepuzzle.eclipselink.DAO.JPA.services.inputdata.StudentGroupJPADAOService;
//import timetablepuzzle.eclipselink.DAO.JPA.services.other.BuildingJPADAOService;
//import timetablepuzzle.eclipselink.DAO.JPA.services.other.LocationJPADAOService;
//import timetablepuzzle.eclipselink.DAO.JPA.services.other.RoomFeatureJPADAOService;
//import timetablepuzzle.eclipselink.DAO.JPA.services.other.RoomTypeJPADAOService;
//import timetablepuzzle.eclipselink.DAO.interfaces.inputdata.InstructorDAO;
//import timetablepuzzle.eclipselink.DAO.interfaces.inputdata.RoomDAO;
//import timetablepuzzle.eclipselink.DAO.interfaces.inputdata.StudentGroupDAO;
//import timetablepuzzle.eclipselink.DAO.interfaces.other.BuildingDAO;
//import timetablepuzzle.eclipselink.DAO.interfaces.other.LocationDAO;
//import timetablepuzzle.eclipselink.DAO.interfaces.other.RoomFeatureDAO;
//import timetablepuzzle.eclipselink.DAO.interfaces.other.RoomTypeDAO;
//import timetablepuzzle.entities.Solution;
//import timetablepuzzle.entities.administration.AcademicSession;
//import timetablepuzzle.entities.administration.AcademicYear;
//import timetablepuzzle.entities.administration.Department;
//import timetablepuzzle.entities.administration.Faculty;
//import timetablepuzzle.entities.administration.User;
//import timetablepuzzle.entities.administration.YearOfStudy;
//import timetablepuzzle.entities.administration.Curricula.Term;
//import timetablepuzzle.entities.inputdata.Instructor;
//import timetablepuzzle.entities.inputdata.Room;
//import timetablepuzzle.entities.inputdata.StudentGroup;
//import timetablepuzzle.entities.other.Building;
//import timetablepuzzle.entities.other.Location;
//import timetablepuzzle.entities.other.RoomFeature;
//import timetablepuzzle.entities.other.RoomType;
//import timetablepuzzle.entities.other.TimePreferences;
//import timetablepuzzle.swing.PasswordAuthentication;
//
//public class DatabaseInserts {
//
//public static void main( String[ ] args ) {
//      Location loc1 = new Location(1,"Bulevardul Profesor Dr. doc. Dimitrie Mangeron 27",47.1539684,27.5939719);
//      Location loc2 = new Location(2,"Bulevardul Profesor Dimitrie Mangeron 21-23",47.1531076,27.5931271);
//      Location loc3 = new Location(2,"Bulevardul Profesor Dimitrie Mangeron 67",47.1539041,27.5960851);      
//      Location loc4 = new Location(4,"Bulevardul Profesor Dimitrie Mangeron 73",47.1549328,27.6010847);
//      Location loc5 = new Location(5,"Bulevardul Carol I 11",47.1747539,27.5711491);
//      Location loc6 = new Location(6,"Campus Tudor vladimirescu, langa T25",47.1549932,27.6110771);
//      LocationDAO locationDAO = new LocationJPADAOService();
//      locationDAO.persist(loc1);
//      locationDAO.persist(loc2);
//      locationDAO.persist(loc3);
//      locationDAO.persist(loc4);
//      locationDAO.persist(loc5);
//      locationDAO.persist(loc6);
//      
//      Building build1 = new Building(1, "Facultatea de Automatica si Calculatoare", "AC", loc1);
//      Building build2 = new Building(2, "Facultatea de Inginerie Electrica Energetica si Informatica Aplicata", "IEEIA", loc2);
//      Building build3 = new Building(3, "Rectorat Universitatea Tehnica Gh. Asachi", "Rectorat", loc3);
//      Building build4 = new Building(4, "Facultatea de Inginerie Chimica si Protectia Mediului", "ICPM", loc4);
//      Building build5 = new Building(5, "Facultatea de Electronica, Telecomunicatii si Tehnologia Informatiei", "ETTI", loc5);
//      Building build6 = new Building(6, "Sala de Sport Universitatea Tehnica Gh. Asachi", "Sala de Sport", loc6);
//      BuildingDAO buildingDAO = new BuildingJPADAOService();
//      buildingDAO.persist(build1);
//      buildingDAO.persist(build2);
//      buildingDAO.persist(build3);
//      buildingDAO.persist(build4);
//      buildingDAO.persist(build5);
//      buildingDAO.persist(build6);      
//      
//      RoomFeature feat1 = new RoomFeature(1,"Proiector");
//      RoomFeature feat2 = new RoomFeature(2,"Tabla");
//      RoomFeature feat3 = new RoomFeature(3,"Mese si scaune");
//      RoomFeature feat4 = new RoomFeature(4,"Calculatoare");
//      RoomFeature feat5 = new RoomFeature(5,"Analizoare digitale");
//      RoomFeature feat6 = new RoomFeature(6,"Microcontrolere");
//      RoomFeature feat7 = new RoomFeature(7,"Emulator 8086");
//      RoomFeature feat8 = new RoomFeature(8,"Ecipament pt. retele");
//      RoomFeature feat9 = new RoomFeature(9,"Brat mecanic");
//      RoomFeatureDAO roomFeatureDAO = new RoomFeatureJPADAOService();
//      roomFeatureDAO.persist(feat1);
//      roomFeatureDAO.persist(feat2);
//      roomFeatureDAO.persist(feat3);
//      roomFeatureDAO.persist(feat4);
//      roomFeatureDAO.persist(feat5);
//      roomFeatureDAO.persist(feat6);
//      roomFeatureDAO.persist(feat7);
//      roomFeatureDAO.persist(feat8);
//      roomFeatureDAO.persist(feat9);
//      
//      List<RoomFeature> cursFeatures = new ArrayList<RoomFeature>();
//      cursFeatures.add(feat1);
//      cursFeatures.add(feat2);
//      cursFeatures.add(feat3);
//      
//      List<RoomFeature> seminaryFeatures = new ArrayList<RoomFeature>();
//      seminaryFeatures.add(feat1);
//      seminaryFeatures.add(feat2);
//      seminaryFeatures.add(feat3);
//      
//      List<RoomFeature> laboratoryFeatures = new ArrayList<RoomFeature>();
//      laboratoryFeatures.add(feat1);
//      laboratoryFeatures.add(feat2);
//      laboratoryFeatures.add(feat3);
//      laboratoryFeatures.add(feat4);
//      
//      List<RoomFeature> electricalLabFeatures = new ArrayList<RoomFeature>();
//      electricalLabFeatures.add(feat1);
//      electricalLabFeatures.add(feat2);
//      electricalLabFeatures.add(feat3);
//      electricalLabFeatures.add(feat4);
//      electricalLabFeatures.add(feat5);
//      electricalLabFeatures.add(feat6);     
//      
//      RoomType rtype1 = new RoomType(1, "Sala de curs", 80, 100, cursFeatures);
//      RoomType rtype2 = new RoomType(1, "Sala de seminar", 30, 40, seminaryFeatures);
//      RoomType rtype3 = new RoomType(1, "Sala de laborator", 20, 30, laboratoryFeatures);
//      RoomType rtype4 = new RoomType(1, "Sala de laborator electronic", 20, 30, electricalLabFeatures);
//      RoomTypeDAO roomTypeDAO = new RoomTypeJPADAOService();
//      roomTypeDAO.persist(rtype1);
//      roomTypeDAO.persist(rtype2);
//      roomTypeDAO.persist(rtype3);
//      roomTypeDAO.persist(rtype4);
//      
//      Instructor instr1 = new Instructor(1, "Leon", "Florin", "Prof. Dr. Ing.", new TimePreferences());
//      Instructor instr2 = new Instructor(2, "Ungureanu", "Florina", "Prof. Dr. Ing.", new TimePreferences());
//      Instructor instr3 = new Instructor(3, "Manta", "Vasile-Ion", "Prof. Dr. Ing.", new TimePreferences());
//      Instructor instr4 = new Instructor(4, "Archip", "Alexandru", "Prof. Dr. Ing.", new TimePreferences());
//      Instructor instr5 = new Instructor(5, "Adrian", "Alexandrescu", "Prof. Dr. Ing.", new TimePreferences());
//      InstructorDAO instructorDAO = new InstructorJPADAOService();
//      instructorDAO.persist(instr1);
//      instructorDAO.persist(instr2);
//      instructorDAO.persist(instr3);
//      instructorDAO.persist(instr4);
//      instructorDAO.persist(instr5);
//      
//      Room room1 = new Room(1, "Sala de curs Calculatoare", "AC-01", 100, rtype1, build1, new TimePreferences());
//      Room room2 = new Room(2, "Sala de curs Automatica", "AC-02", 100, rtype1, build1, new TimePreferences());
//      Room room3 = new Room(3, "Cabinet de Matematica", "AC-32", 40, rtype2, build1, new TimePreferences());
//      Room room4 = new Room(4, "Sala de curs Calculatoare", "AC-33", 40, rtype2, build1, new TimePreferences());
//      Room room5 = new Room(5, "Laborator de Informatica", "C-01", 30, rtype3, build1, new TimePreferences());
//      Room room6 = new Room(5, "Laborator de Informatica", "C-02", 30, rtype3, build1, new TimePreferences());
//      Room room7 = new Room(5, "Laborator de Informatica", "C-03", 30, rtype3, build1, new TimePreferences());
//      Room room8 = new Room(5, "Laborator de Informatica", "C-04", 30, rtype3, build1, new TimePreferences());
//      Room room9 = new Room(5, "Laborator de Informatica", "C-05", 30, rtype3, build1, new TimePreferences());
//      Room room10 = new Room(5, "Laborator de Informatica", "C-06", 30, rtype3, build1, new TimePreferences());
//      RoomDAO roomDAO = new RoomJPADAOService();
//      roomDAO.persist(room1);
//      roomDAO.persist(room2);
//      roomDAO.persist(room3);
//      roomDAO.persist(room4);
//      roomDAO.persist(room5);
//      roomDAO.persist(room6);
//      roomDAO.persist(room7);
//      roomDAO.persist(room8);
//      roomDAO.persist(room9);
//      roomDAO.persist(room10);   
//
//      // Insert StudentGroups here
//      StudentGroup semigrupa1101A = new StudentGroup(0, "Semigrupa", "1101A", 25);
//      StudentGroup semigrupa1101B = new StudentGroup(0, "Semigrupa", "1101B", 30);
//      List<StudentGroup> comp1101 = new ArrayList<StudentGroup>();
//      comp1101.add(semigrupa1101A);
//      comp1101.add(semigrupa1101B);
//
//      StudentGroup grupa1101 = new StudentGroup(0, "Grupa", "1101", comp1101);
//
//
//      StudentGroup semigrupa1102A = new StudentGroup(0, "Semigrupa", "1102A", 25);
//      StudentGroup semigrupa1102B = new StudentGroup(0, "Semigrupa", "1102B", 21);
//      List<StudentGroup> comp1102 = new ArrayList<StudentGroup>();
//      comp1102.add(semigrupa1102A);
//      comp1102.add(semigrupa1102B);
//
//      StudentGroup grupa1102 = new StudentGroup(0, "Grupa", "1102", comp1102);
//
//
//      StudentGroup semigrupa1103A = new StudentGroup(0, "Semigrupa", "1103A", 29);
//      StudentGroup semigrupa1103B = new StudentGroup(0, "Semigrupa", "1103B", 28);
//      List<StudentGroup> comp1103 = new ArrayList<StudentGroup>();
//      comp1103.add(semigrupa1103A);
//      comp1103.add(semigrupa1103B);
//
//      StudentGroup grupa1103 = new StudentGroup(0, "Grupa", "1103", comp1103);
//
//
//      StudentGroup semigrupa1104A = new StudentGroup(0, "Semigrupa", "1104A", 29);
//      StudentGroup semigrupa1104B = new StudentGroup(0, "Semigrupa", "1104B", 23);
//      List<StudentGroup> comp1104 = new ArrayList<StudentGroup>();
//      comp1104.add(semigrupa1104A);
//      comp1104.add(semigrupa1104B);
//
//      StudentGroup grupa1104 = new StudentGroup(0, "Grupa", "1104", comp1104);
//
//
//      List<StudentGroup> compFIRST_IS = new ArrayList<StudentGroup>();
//      compFIRST_IS.add(grupa1101);
//      compFIRST_IS.add(grupa1102);
//      compFIRST_IS.add(grupa1103);
//      compFIRST_IS.add(grupa1104);
//
//      StudentGroup yearOfStudyFIRST_IS = new StudentGroup(0, "YearOfStudy", "FIRST_IS", compFIRST_IS);
//
//
//      StudentGroup semigrupa1201A = new StudentGroup(0, "Semigrupa", "1201A", 30);
//      StudentGroup semigrupa1201B = new StudentGroup(0, "Semigrupa", "1201B", 23);
//      List<StudentGroup> comp1201 = new ArrayList<StudentGroup>();
//      comp1201.add(semigrupa1201A);
//      comp1201.add(semigrupa1201B);
//
//      StudentGroup grupa1201 = new StudentGroup(0, "Grupa", "1201", comp1201);
//
//
//      StudentGroup semigrupa1202A = new StudentGroup(0, "Semigrupa", "1202A", 25);
//      StudentGroup semigrupa1202B = new StudentGroup(0, "Semigrupa", "1202B", 30);
//      List<StudentGroup> comp1202 = new ArrayList<StudentGroup>();
//      comp1202.add(semigrupa1202A);
//      comp1202.add(semigrupa1202B);
//
//      StudentGroup grupa1202 = new StudentGroup(0, "Grupa", "1202", comp1202);
//
//
//      StudentGroup semigrupa1203A = new StudentGroup(0, "Semigrupa", "1203A", 20);
//      StudentGroup semigrupa1203B = new StudentGroup(0, "Semigrupa", "1203B", 24);
//      List<StudentGroup> comp1203 = new ArrayList<StudentGroup>();
//      comp1203.add(semigrupa1203A);
//      comp1203.add(semigrupa1203B);
//
//      StudentGroup grupa1203 = new StudentGroup(0, "Grupa", "1203", comp1203);
//
//
//      StudentGroup semigrupa1204A = new StudentGroup(0, "Semigrupa", "1204A", 22);
//      StudentGroup semigrupa1204B = new StudentGroup(0, "Semigrupa", "1204B", 23);
//      List<StudentGroup> comp1204 = new ArrayList<StudentGroup>();
//      comp1204.add(semigrupa1204A);
//      comp1204.add(semigrupa1204B);
//
//      StudentGroup grupa1204 = new StudentGroup(0, "Grupa", "1204", comp1204);
//
//
//      List<StudentGroup> compSECOND_IS = new ArrayList<StudentGroup>();
//      compSECOND_IS.add(grupa1201);
//      compSECOND_IS.add(grupa1202);
//      compSECOND_IS.add(grupa1203);
//      compSECOND_IS.add(grupa1204);
//
//      StudentGroup yearOfStudySECOND_IS = new StudentGroup(0, "YearOfStudy", "SECOND_IS", compSECOND_IS);
//
//
//      StudentGroup semigrupa1301A = new StudentGroup(0, "Semigrupa", "1301A", 20);
//      StudentGroup semigrupa1301B = new StudentGroup(0, "Semigrupa", "1301B", 28);
//      List<StudentGroup> comp1301 = new ArrayList<StudentGroup>();
//      comp1301.add(semigrupa1301A);
//      comp1301.add(semigrupa1301B);
//
//      StudentGroup grupa1301 = new StudentGroup(0, "Grupa", "1301", comp1301);
//
//
//      StudentGroup semigrupa1302A = new StudentGroup(0, "Semigrupa", "1302A", 30);
//      StudentGroup semigrupa1302B = new StudentGroup(0, "Semigrupa", "1302B", 21);
//      List<StudentGroup> comp1302 = new ArrayList<StudentGroup>();
//      comp1302.add(semigrupa1302A);
//      comp1302.add(semigrupa1302B);
//
//      StudentGroup grupa1302 = new StudentGroup(0, "Grupa", "1302", comp1302);
//
//
//      StudentGroup semigrupa1303A = new StudentGroup(0, "Semigrupa", "1303A", 21);
//      StudentGroup semigrupa1303B = new StudentGroup(0, "Semigrupa", "1303B", 22);
//      List<StudentGroup> comp1303 = new ArrayList<StudentGroup>();
//      comp1303.add(semigrupa1303A);
//      comp1303.add(semigrupa1303B);
//
//      StudentGroup grupa1303 = new StudentGroup(0, "Grupa", "1303", comp1303);
//
//
//      StudentGroup semigrupa1304A = new StudentGroup(0, "Semigrupa", "1304A", 26);
//      StudentGroup semigrupa1304B = new StudentGroup(0, "Semigrupa", "1304B", 20);
//      List<StudentGroup> comp1304 = new ArrayList<StudentGroup>();
//      comp1304.add(semigrupa1304A);
//      comp1304.add(semigrupa1304B);
//
//      StudentGroup grupa1304 = new StudentGroup(0, "Grupa", "1304", comp1304);
//
//
//      List<StudentGroup> compTHIRD_IS = new ArrayList<StudentGroup>();
//      compTHIRD_IS.add(grupa1301);
//      compTHIRD_IS.add(grupa1302);
//      compTHIRD_IS.add(grupa1303);
//      compTHIRD_IS.add(grupa1304);
//
//      StudentGroup yearOfStudyTHIRD_IS = new StudentGroup(0, "YearOfStudy", "THIRD_IS", compTHIRD_IS);
//
//
//      StudentGroup semigrupa1401A = new StudentGroup(0, "Semigrupa", "1401A", 24);
//      StudentGroup semigrupa1401B = new StudentGroup(0, "Semigrupa", "1401B", 28);
//      List<StudentGroup> comp1401 = new ArrayList<StudentGroup>();
//      comp1401.add(semigrupa1401A);
//      comp1401.add(semigrupa1401B);
//
//      StudentGroup grupa1401 = new StudentGroup(0, "Grupa", "1401", comp1401);
//
//
//      StudentGroup semigrupa1402A = new StudentGroup(0, "Semigrupa", "1402A", 20);
//      StudentGroup semigrupa1402B = new StudentGroup(0, "Semigrupa", "1402B", 29);
//      List<StudentGroup> comp1402 = new ArrayList<StudentGroup>();
//      comp1402.add(semigrupa1402A);
//      comp1402.add(semigrupa1402B);
//
//      StudentGroup grupa1402 = new StudentGroup(0, "Grupa", "1402", comp1402);
//
//
//      StudentGroup semigrupa1403A = new StudentGroup(0, "Semigrupa", "1403A", 26);
//      StudentGroup semigrupa1403B = new StudentGroup(0, "Semigrupa", "1403B", 21);
//      List<StudentGroup> comp1403 = new ArrayList<StudentGroup>();
//      comp1403.add(semigrupa1403A);
//      comp1403.add(semigrupa1403B);
//
//      StudentGroup grupa1403 = new StudentGroup(0, "Grupa", "1403", comp1403);
//
//
//      StudentGroup semigrupa1404A = new StudentGroup(0, "Semigrupa", "1404A", 24);
//      StudentGroup semigrupa1404B = new StudentGroup(0, "Semigrupa", "1404B", 27);
//      List<StudentGroup> comp1404 = new ArrayList<StudentGroup>();
//      comp1404.add(semigrupa1404A);
//      comp1404.add(semigrupa1404B);
//
//      StudentGroup grupa1404 = new StudentGroup(0, "Grupa", "1404", comp1404);
//
//
//      List<StudentGroup> compFOURTH_IS = new ArrayList<StudentGroup>();
//      compFOURTH_IS.add(grupa1401);
//      compFOURTH_IS.add(grupa1402);
//      compFOURTH_IS.add(grupa1403);
//      compFOURTH_IS.add(grupa1404);
//
//      StudentGroup yearOfStudyFOURTH_IS = new StudentGroup(0, "YearOfStudy", "FOURTH_IS", compFOURTH_IS);
//
//
//      StudentGroup semigrupa1501A = new StudentGroup(0, "Semigrupa", "1501A", 22);
//      StudentGroup semigrupa1501B = new StudentGroup(0, "Semigrupa", "1501B", 23);
//      List<StudentGroup> comp1501 = new ArrayList<StudentGroup>();
//      comp1501.add(semigrupa1501A);
//      comp1501.add(semigrupa1501B);
//
//      StudentGroup grupa1501 = new StudentGroup(0, "Grupa", "1501", comp1501);
//
//
//      StudentGroup semigrupa1502A = new StudentGroup(0, "Semigrupa", "1502A", 21);
//      StudentGroup semigrupa1502B = new StudentGroup(0, "Semigrupa", "1502B", 20);
//      List<StudentGroup> comp1502 = new ArrayList<StudentGroup>();
//      comp1502.add(semigrupa1502A);
//      comp1502.add(semigrupa1502B);
//
//      StudentGroup grupa1502 = new StudentGroup(0, "Grupa", "1502", comp1502);
//
//
//      StudentGroup semigrupa1503A = new StudentGroup(0, "Semigrupa", "1503A", 25);
//      StudentGroup semigrupa1503B = new StudentGroup(0, "Semigrupa", "1503B", 22);
//      List<StudentGroup> comp1503 = new ArrayList<StudentGroup>();
//      comp1503.add(semigrupa1503A);
//      comp1503.add(semigrupa1503B);
//
//      StudentGroup grupa1503 = new StudentGroup(0, "Grupa", "1503", comp1503);
//
//
//      StudentGroup semigrupa1504A = new StudentGroup(0, "Semigrupa", "1504A", 22);
//      StudentGroup semigrupa1504B = new StudentGroup(0, "Semigrupa", "1504B", 21);
//      List<StudentGroup> comp1504 = new ArrayList<StudentGroup>();
//      comp1504.add(semigrupa1504A);
//      comp1504.add(semigrupa1504B);
//
//      StudentGroup grupa1504 = new StudentGroup(0, "Grupa", "1504", comp1504);
//
//
//      List<StudentGroup> compFIFTH_IS = new ArrayList<StudentGroup>();
//      compFIFTH_IS.add(grupa1501);
//      compFIFTH_IS.add(grupa1502);
//      compFIFTH_IS.add(grupa1503);
//      compFIFTH_IS.add(grupa1504);
//
//      StudentGroup yearOfStudyFIFTH_IS = new StudentGroup(0, "YearOfStudy", "FIFTH_IS", compFIFTH_IS);
//
//
//      StudentGroup semigrupa1601A = new StudentGroup(0, "Semigrupa", "1601A", 22);
//      StudentGroup semigrupa1601B = new StudentGroup(0, "Semigrupa", "1601B", 25);
//      List<StudentGroup> comp1601 = new ArrayList<StudentGroup>();
//      comp1601.add(semigrupa1601A);
//      comp1601.add(semigrupa1601B);
//
//      StudentGroup grupa1601 = new StudentGroup(0, "Grupa", "1601", comp1601);
//
//
//      StudentGroup semigrupa1602A = new StudentGroup(0, "Semigrupa", "1602A", 30);
//      StudentGroup semigrupa1602B = new StudentGroup(0, "Semigrupa", "1602B", 27);
//      List<StudentGroup> comp1602 = new ArrayList<StudentGroup>();
//      comp1602.add(semigrupa1602A);
//      comp1602.add(semigrupa1602B);
//
//      StudentGroup grupa1602 = new StudentGroup(0, "Grupa", "1602", comp1602);
//
//
//      StudentGroup semigrupa1603A = new StudentGroup(0, "Semigrupa", "1603A", 22);
//      StudentGroup semigrupa1603B = new StudentGroup(0, "Semigrupa", "1603B", 24);
//      List<StudentGroup> comp1603 = new ArrayList<StudentGroup>();
//      comp1603.add(semigrupa1603A);
//      comp1603.add(semigrupa1603B);
//
//      StudentGroup grupa1603 = new StudentGroup(0, "Grupa", "1603", comp1603);
//
//
//      StudentGroup semigrupa1604A = new StudentGroup(0, "Semigrupa", "1604A", 27);
//      StudentGroup semigrupa1604B = new StudentGroup(0, "Semigrupa", "1604B", 28);
//      List<StudentGroup> comp1604 = new ArrayList<StudentGroup>();
//      comp1604.add(semigrupa1604A);
//      comp1604.add(semigrupa1604B);
//
//      StudentGroup grupa1604 = new StudentGroup(0, "Grupa", "1604", comp1604);
//
//
//      List<StudentGroup> compSIXTH_IS = new ArrayList<StudentGroup>();
//      compSIXTH_IS.add(grupa1601);
//      compSIXTH_IS.add(grupa1602);
//      compSIXTH_IS.add(grupa1603);
//      compSIXTH_IS.add(grupa1604);
//
//      StudentGroup yearOfStudySIXTH_IS = new StudentGroup(0, "YearOfStudy", "SIXTH_IS", compSIXTH_IS);
//
//
//      List<StudentGroup> compIS = new ArrayList<StudentGroup>();
//      compIS.add(yearOfStudyFIRST_IS);
//      compIS.add(yearOfStudySECOND_IS);
//      compIS.add(yearOfStudyTHIRD_IS);
//      compIS.add(yearOfStudyFOURTH_IS);
//      compIS.add(yearOfStudyFIFTH_IS);
//      compIS.add(yearOfStudySIXTH_IS);
//
//      StudentGroup departmentIS = new StudentGroup(0, "Department", "IS", compIS);
//
//
//      StudentGroup semigrupa1105A = new StudentGroup(0, "Semigrupa", "1105A", 30);
//      StudentGroup semigrupa1105B = new StudentGroup(0, "Semigrupa", "1105B", 29);
//      List<StudentGroup> comp1105 = new ArrayList<StudentGroup>();
//      comp1105.add(semigrupa1105A);
//      comp1105.add(semigrupa1105B);
//
//      StudentGroup grupa1105 = new StudentGroup(0, "Grupa", "1105", comp1105);
//
//
//      StudentGroup semigrupa1106A = new StudentGroup(0, "Semigrupa", "1106A", 23);
//      StudentGroup semigrupa1106B = new StudentGroup(0, "Semigrupa", "1106B", 29);
//      List<StudentGroup> comp1106 = new ArrayList<StudentGroup>();
//      comp1106.add(semigrupa1106A);
//      comp1106.add(semigrupa1106B);
//
//      StudentGroup grupa1106 = new StudentGroup(0, "Grupa", "1106", comp1106);
//
//
//      StudentGroup semigrupa1107A = new StudentGroup(0, "Semigrupa", "1107A", 21);
//      StudentGroup semigrupa1107B = new StudentGroup(0, "Semigrupa", "1107B", 26);
//      List<StudentGroup> comp1107 = new ArrayList<StudentGroup>();
//      comp1107.add(semigrupa1107A);
//      comp1107.add(semigrupa1107B);
//
//      StudentGroup grupa1107 = new StudentGroup(0, "Grupa", "1107", comp1107);
//
//
//      StudentGroup semigrupa1108A = new StudentGroup(0, "Semigrupa", "1108A", 24);
//      StudentGroup semigrupa1108B = new StudentGroup(0, "Semigrupa", "1108B", 25);
//      List<StudentGroup> comp1108 = new ArrayList<StudentGroup>();
//      comp1108.add(semigrupa1108A);
//      comp1108.add(semigrupa1108B);
//
//      StudentGroup grupa1108 = new StudentGroup(0, "Grupa", "1108", comp1108);
//
//
//      List<StudentGroup> compFIRST_CTI = new ArrayList<StudentGroup>();
//      compFIRST_CTI.add(grupa1105);
//      compFIRST_CTI.add(grupa1106);
//      compFIRST_CTI.add(grupa1107);
//      compFIRST_CTI.add(grupa1108);
//
//      StudentGroup yearOfStudyFIRST_CTI = new StudentGroup(0, "YearOfStudy", "FIRST_CTI", compFIRST_CTI);
//
//
//      StudentGroup semigrupa1205A = new StudentGroup(0, "Semigrupa", "1205A", 24);
//      StudentGroup semigrupa1205B = new StudentGroup(0, "Semigrupa", "1205B", 21);
//      List<StudentGroup> comp1205 = new ArrayList<StudentGroup>();
//      comp1205.add(semigrupa1205A);
//      comp1205.add(semigrupa1205B);
//
//      StudentGroup grupa1205 = new StudentGroup(0, "Grupa", "1205", comp1205);
//
//
//      StudentGroup semigrupa1206A = new StudentGroup(0, "Semigrupa", "1206A", 24);
//      StudentGroup semigrupa1206B = new StudentGroup(0, "Semigrupa", "1206B", 25);
//      List<StudentGroup> comp1206 = new ArrayList<StudentGroup>();
//      comp1206.add(semigrupa1206A);
//      comp1206.add(semigrupa1206B);
//
//      StudentGroup grupa1206 = new StudentGroup(0, "Grupa", "1206", comp1206);
//
//
//      StudentGroup semigrupa1207A = new StudentGroup(0, "Semigrupa", "1207A", 24);
//      StudentGroup semigrupa1207B = new StudentGroup(0, "Semigrupa", "1207B", 20);
//      List<StudentGroup> comp1207 = new ArrayList<StudentGroup>();
//      comp1207.add(semigrupa1207A);
//      comp1207.add(semigrupa1207B);
//
//      StudentGroup grupa1207 = new StudentGroup(0, "Grupa", "1207", comp1207);
//
//
//      StudentGroup semigrupa1208A = new StudentGroup(0, "Semigrupa", "1208A", 25);
//      StudentGroup semigrupa1208B = new StudentGroup(0, "Semigrupa", "1208B", 24);
//      List<StudentGroup> comp1208 = new ArrayList<StudentGroup>();
//      comp1208.add(semigrupa1208A);
//      comp1208.add(semigrupa1208B);
//
//      StudentGroup grupa1208 = new StudentGroup(0, "Grupa", "1208", comp1208);
//
//
//      List<StudentGroup> compSECOND_CTI = new ArrayList<StudentGroup>();
//      compSECOND_CTI.add(grupa1205);
//      compSECOND_CTI.add(grupa1206);
//      compSECOND_CTI.add(grupa1207);
//      compSECOND_CTI.add(grupa1208);
//
//      StudentGroup yearOfStudySECOND_CTI = new StudentGroup(0, "YearOfStudy", "SECOND_CTI", compSECOND_CTI);
//
//
//      StudentGroup semigrupa1305A = new StudentGroup(0, "Semigrupa", "1305A", 24);
//      StudentGroup semigrupa1305B = new StudentGroup(0, "Semigrupa", "1305B", 26);
//      List<StudentGroup> comp1305 = new ArrayList<StudentGroup>();
//      comp1305.add(semigrupa1305A);
//      comp1305.add(semigrupa1305B);
//
//      StudentGroup grupa1305 = new StudentGroup(0, "Grupa", "1305", comp1305);
//
//
//      StudentGroup semigrupa1306A = new StudentGroup(0, "Semigrupa", "1306A", 26);
//      StudentGroup semigrupa1306B = new StudentGroup(0, "Semigrupa", "1306B", 24);
//      List<StudentGroup> comp1306 = new ArrayList<StudentGroup>();
//      comp1306.add(semigrupa1306A);
//      comp1306.add(semigrupa1306B);
//
//      StudentGroup grupa1306 = new StudentGroup(0, "Grupa", "1306", comp1306);
//
//
//      StudentGroup semigrupa1307A = new StudentGroup(0, "Semigrupa", "1307A", 27);
//      StudentGroup semigrupa1307B = new StudentGroup(0, "Semigrupa", "1307B", 29);
//      List<StudentGroup> comp1307 = new ArrayList<StudentGroup>();
//      comp1307.add(semigrupa1307A);
//      comp1307.add(semigrupa1307B);
//
//      StudentGroup grupa1307 = new StudentGroup(0, "Grupa", "1307", comp1307);
//
//
//      StudentGroup semigrupa1308A = new StudentGroup(0, "Semigrupa", "1308A", 21);
//      StudentGroup semigrupa1308B = new StudentGroup(0, "Semigrupa", "1308B", 22);
//      List<StudentGroup> comp1308 = new ArrayList<StudentGroup>();
//      comp1308.add(semigrupa1308A);
//      comp1308.add(semigrupa1308B);
//
//      StudentGroup grupa1308 = new StudentGroup(0, "Grupa", "1308", comp1308);
//
//
//      List<StudentGroup> compTHIRD_CTI = new ArrayList<StudentGroup>();
//      compTHIRD_CTI.add(grupa1305);
//      compTHIRD_CTI.add(grupa1306);
//      compTHIRD_CTI.add(grupa1307);
//      compTHIRD_CTI.add(grupa1308);
//
//      StudentGroup yearOfStudyTHIRD_CTI = new StudentGroup(0, "YearOfStudy", "THIRD_CTI", compTHIRD_CTI);
//
//
//      StudentGroup semigrupa1405A = new StudentGroup(0, "Semigrupa", "1405A", 29);
//      StudentGroup semigrupa1405B = new StudentGroup(0, "Semigrupa", "1405B", 26);
//      List<StudentGroup> comp1405 = new ArrayList<StudentGroup>();
//      comp1405.add(semigrupa1405A);
//      comp1405.add(semigrupa1405B);
//
//      StudentGroup grupa1405 = new StudentGroup(0, "Grupa", "1405", comp1405);
//
//
//      StudentGroup semigrupa1406A = new StudentGroup(0, "Semigrupa", "1406A", 25);
//      StudentGroup semigrupa1406B = new StudentGroup(0, "Semigrupa", "1406B", 22);
//      List<StudentGroup> comp1406 = new ArrayList<StudentGroup>();
//      comp1406.add(semigrupa1406A);
//      comp1406.add(semigrupa1406B);
//
//      StudentGroup grupa1406 = new StudentGroup(0, "Grupa", "1406", comp1406);
//
//
//      StudentGroup semigrupa1407A = new StudentGroup(0, "Semigrupa", "1407A", 20);
//      StudentGroup semigrupa1407B = new StudentGroup(0, "Semigrupa", "1407B", 28);
//      List<StudentGroup> comp1407 = new ArrayList<StudentGroup>();
//      comp1407.add(semigrupa1407A);
//      comp1407.add(semigrupa1407B);
//
//      StudentGroup grupa1407 = new StudentGroup(0, "Grupa", "1407", comp1407);
//
//
//      StudentGroup semigrupa1408A = new StudentGroup(0, "Semigrupa", "1408A", 24);
//      StudentGroup semigrupa1408B = new StudentGroup(0, "Semigrupa", "1408B", 21);
//      List<StudentGroup> comp1408 = new ArrayList<StudentGroup>();
//      comp1408.add(semigrupa1408A);
//      comp1408.add(semigrupa1408B);
//
//      StudentGroup grupa1408 = new StudentGroup(0, "Grupa", "1408", comp1408);
//
//
//      List<StudentGroup> compFOURTH_CTI = new ArrayList<StudentGroup>();
//      compFOURTH_CTI.add(grupa1405);
//      compFOURTH_CTI.add(grupa1406);
//      compFOURTH_CTI.add(grupa1407);
//      compFOURTH_CTI.add(grupa1408);
//
//      StudentGroup yearOfStudyFOURTH_CTI = new StudentGroup(0, "YearOfStudy", "FOURTH_CTI", compFOURTH_CTI);
//
//
//      StudentGroup semigrupa1505A = new StudentGroup(0, "Semigrupa", "1505A", 30);
//      StudentGroup semigrupa1505B = new StudentGroup(0, "Semigrupa", "1505B", 20);
//      List<StudentGroup> comp1505 = new ArrayList<StudentGroup>();
//      comp1505.add(semigrupa1505A);
//      comp1505.add(semigrupa1505B);
//
//      StudentGroup grupa1505 = new StudentGroup(0, "Grupa", "1505", comp1505);
//
//
//      StudentGroup semigrupa1506A = new StudentGroup(0, "Semigrupa", "1506A", 28);
//      StudentGroup semigrupa1506B = new StudentGroup(0, "Semigrupa", "1506B", 28);
//      List<StudentGroup> comp1506 = new ArrayList<StudentGroup>();
//      comp1506.add(semigrupa1506A);
//      comp1506.add(semigrupa1506B);
//
//      StudentGroup grupa1506 = new StudentGroup(0, "Grupa", "1506", comp1506);
//
//
//      StudentGroup semigrupa1507A = new StudentGroup(0, "Semigrupa", "1507A", 21);
//      StudentGroup semigrupa1507B = new StudentGroup(0, "Semigrupa", "1507B", 23);
//      List<StudentGroup> comp1507 = new ArrayList<StudentGroup>();
//      comp1507.add(semigrupa1507A);
//      comp1507.add(semigrupa1507B);
//
//      StudentGroup grupa1507 = new StudentGroup(0, "Grupa", "1507", comp1507);
//
//
//      StudentGroup semigrupa1508A = new StudentGroup(0, "Semigrupa", "1508A", 20);
//      StudentGroup semigrupa1508B = new StudentGroup(0, "Semigrupa", "1508B", 29);
//      List<StudentGroup> comp1508 = new ArrayList<StudentGroup>();
//      comp1508.add(semigrupa1508A);
//      comp1508.add(semigrupa1508B);
//
//      StudentGroup grupa1508 = new StudentGroup(0, "Grupa", "1508", comp1508);
//
//
//      List<StudentGroup> compFIFTH_CTI = new ArrayList<StudentGroup>();
//      compFIFTH_CTI.add(grupa1505);
//      compFIFTH_CTI.add(grupa1506);
//      compFIFTH_CTI.add(grupa1507);
//      compFIFTH_CTI.add(grupa1508);
//
//      StudentGroup yearOfStudyFIFTH_CTI = new StudentGroup(0, "YearOfStudy", "FIFTH_CTI", compFIFTH_CTI);
//
//
//      StudentGroup semigrupa1605A = new StudentGroup(0, "Semigrupa", "1605A", 20);
//      StudentGroup semigrupa1605B = new StudentGroup(0, "Semigrupa", "1605B", 25);
//      List<StudentGroup> comp1605 = new ArrayList<StudentGroup>();
//      comp1605.add(semigrupa1605A);
//      comp1605.add(semigrupa1605B);
//
//      StudentGroup grupa1605 = new StudentGroup(0, "Grupa", "1605", comp1605);
//
//
//      StudentGroup semigrupa1606A = new StudentGroup(0, "Semigrupa", "1606A", 24);
//      StudentGroup semigrupa1606B = new StudentGroup(0, "Semigrupa", "1606B", 20);
//      List<StudentGroup> comp1606 = new ArrayList<StudentGroup>();
//      comp1606.add(semigrupa1606A);
//      comp1606.add(semigrupa1606B);
//
//      StudentGroup grupa1606 = new StudentGroup(0, "Grupa", "1606", comp1606);
//
//
//      StudentGroup semigrupa1607A = new StudentGroup(0, "Semigrupa", "1607A", 30);
//      StudentGroup semigrupa1607B = new StudentGroup(0, "Semigrupa", "1607B", 29);
//      List<StudentGroup> comp1607 = new ArrayList<StudentGroup>();
//      comp1607.add(semigrupa1607A);
//      comp1607.add(semigrupa1607B);
//
//      StudentGroup grupa1607 = new StudentGroup(0, "Grupa", "1607", comp1607);
//
//
//      StudentGroup semigrupa1608A = new StudentGroup(0, "Semigrupa", "1608A", 22);
//      StudentGroup semigrupa1608B = new StudentGroup(0, "Semigrupa", "1608B", 24);
//      List<StudentGroup> comp1608 = new ArrayList<StudentGroup>();
//      comp1608.add(semigrupa1608A);
//      comp1608.add(semigrupa1608B);
//
//      StudentGroup grupa1608 = new StudentGroup(0, "Grupa", "1608", comp1608);
//
//
//      List<StudentGroup> compSIXTH_CTI = new ArrayList<StudentGroup>();
//      compSIXTH_CTI.add(grupa1605);
//      compSIXTH_CTI.add(grupa1606);
//      compSIXTH_CTI.add(grupa1607);
//      compSIXTH_CTI.add(grupa1608);
//
//      StudentGroup yearOfStudySIXTH_CTI = new StudentGroup(0, "YearOfStudy", "SIXTH_CTI", compSIXTH_CTI);
//
//
//      List<StudentGroup> compCTI = new ArrayList<StudentGroup>();
//      compCTI.add(yearOfStudyFIRST_CTI);
//      compCTI.add(yearOfStudySECOND_CTI);
//      compCTI.add(yearOfStudyTHIRD_CTI);
//      compCTI.add(yearOfStudyFOURTH_CTI);
//      compCTI.add(yearOfStudyFIFTH_CTI);
//      compCTI.add(yearOfStudySIXTH_CTI);
//
//      StudentGroup departmentCTI = new StudentGroup(0, "Department", "CTI", compCTI);
//
//
//      List<StudentGroup> comp2016_2017 = new ArrayList<StudentGroup>();
//      comp2016_2017.add(departmentIS);
//      comp2016_2017.add(departmentCTI);
//
//      StudentGroup academicYear2016_2017 = new StudentGroup(0, "AcademicYear", "2016_2017", comp2016_2017);
//
//
//      StudentGroupDAO studentGroupDAO = new StudentGroupJPADAOService();
//      studentGroupDAO.persist(academicYear2016_2017);
//
//
//
//
//      PasswordAuthentication passAuth = new PasswordAuthentication();
//      char[] password = {'a','d','m','i','n'};
//      
//      AcademicSession fall = new AcademicSession(0,"Toamna-Iarna", Term.FIRST,
//              new GregorianCalendar(2016,10,3), new GregorianCalendar(2017,1,20),new GregorianCalendar(2017,1,23),new GregorianCalendar(2017,2,5), new Solution());
//      AcademicSession spring = new AcademicSession(0,"Primavara-Vara",Term.SECOND,
//              new GregorianCalendar(2017,2,13), new GregorianCalendar(2017,5,26),new GregorianCalendar(2017,5,29),new GregorianCalendar(2017,6,18), new Solution());
//      AcademicYear lastViewedAcadYear = new AcademicYear(0,"2016-2017", academicYear2016_2017, fall, spring);
//      Department cti = new Department(0,"CTI",new ArrayList<YearOfStudy>());
//      Department is = new Department(0,"IS",new ArrayList<YearOfStudy>());
//      List<Department> departments = new ArrayList<Department>();
//      departments.add(cti);
//      departments.add(is);
//      List<AcademicYear> academicYears =  new ArrayList<AcademicYear>();
//      academicYears.add(lastViewedAcadYear);
//      Faculty lastViewedFaculty = new Faculty(0,"Automatica si Calculatoare", academicYears, departments);
//      User user = new User(0,"Johny","Test","admin",passAuth.hash(password),User.UserType.ADMIN,lastViewedAcadYear, fall, lastViewedFaculty);
//      
//      UserJPADAOService aux = new UserJPADAOService();
//      aux.persist( user );
//   }
//}