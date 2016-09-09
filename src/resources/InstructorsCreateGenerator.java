package resources;

public class InstructorsCreateGenerator {

	private static String[] academicTitles = new String[] { "Asist.ing.","Asist.mat","Asist.fiz", "S.l.dr.ing", "S.l.dr.mat.", "S.l.dr.fiz.",
			"Prof.dr.ing.", "Prof.dr.mat","Prof.dr.fiz.","Conf.dr.ing.","Conf.dr.mat","Conf.dr.fiz.","Lect." };

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String instructorService = "InstructorDAO instructorDAO = new InstructorJPADAOService();\n";
		System.out.println(instructorService);
		createAndSAveInstructor("Leon", "Florin", 6);
		createAndSAveInstructor("Ungureanu", "Florina", 6);
		createAndSAveInstructor("Manta", "Vasile", 6);
		createAndSAveInstructor("Archip", "Alexandru", 6);
		createAndSAveInstructor("Alexandrescu", "Adrian", 0);

		createAndSAveInstructor("Botezatu", "Nicolae", 3);
		createAndSAveInstructor("Herghelegiu", "Paul", 9);
		createAndSAveInstructor("Mirea", "Letitia", 9);
		createAndSAveInstructor("Gavrilescu", "Marius", 3);
		createAndSAveInstructor("Dumbrava", "Stefan", 9);
		createAndSAveInstructor("Campanu", "Corina", 0);
		createAndSAveInstructor("Grosu", "Georgiana", 4);
		createAndSAveInstructor("Calistru", "Catalin", 3);
		createAndSAveInstructor("Ciobanu", "Brandusa", 11);
		createAndSAveInstructor("Pletea", "Ariadna", 10);

		createAndSAveInstructor("Monor", "Catalin", 3);
		createAndSAveInstructor("Pantelimonescu", "Florin", 9);
		createAndSAveInstructor("Serban", "Elena", 9);
		createAndSAveInstructor("Strugariu", "Radu", 10);
		createAndSAveInstructor("Toma", "AnaMaria", 12);
		createAndSAveInstructor("Lupu", "Robert", 3);
		createAndSAveInstructor("Kloetzer", "Marius", 6);
		createAndSAveInstructor("Tigaeru", "Liviu", 5);
		createAndSAveInstructor("Timis", "Mihai", 3);
		createAndSAveInstructor("Cascaval", "Petru", 6);
		createAndSAveInstructor("Stan", "Andrei", 3);
		createAndSAveInstructor("Caraiman", "Simona", 3);
		createAndSAveInstructor("Craus", "Mitica", 6);
		createAndSAveInstructor("Butincu", "Cristian", 3);
		//createAndSAveInstructor("ceva", "ceva", 333);
	}

	private static void createAndSAveInstructor(String firstName, String lastName, int academicTitle) {
		String code = String.format(
				"Instructor %s%s = new Instructor(0, \"%s\", \"%s\", \"%s\", TimePreferences.generateRandomTimePreferences());\n",
				firstName, lastName, firstName, lastName, academicTitles[academicTitle]);
		code += String.format("instructorDAO.merge(%s%s);\n", firstName, lastName);
		System.out.println(code);
	}

}
