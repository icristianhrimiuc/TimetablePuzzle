//package resources;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ThreadLocalRandom;
//
//public class StudentGroupsCreateGenerator {
//
//	public static void main(String[] args) {
//		String academicYear = "2016_2017";
//		List<String> departmentNames = new ArrayList<String>();
//		departmentNames.add("IS");
//		departmentNames.add("CTI");
//		List<String> yearsOfStudy = new ArrayList<String>();
//		yearsOfStudy.add("FIRST");
//		yearsOfStudy.add("SECOND");
//		yearsOfStudy.add("THIRD");
//		yearsOfStudy.add("FOURTH");
//		yearsOfStudy.add("FIFTH");
//		yearsOfStudy.add("SIXTH");
//		List<String> subjectAreaNames = new ArrayList<String>();
//		int nrOfGroupsPerYear = 4;
//		int nrOfSemigroupsInGroup = 2;
//
//		GenerateStudentGroups(academicYear, departmentNames, yearsOfStudy, subjectAreaNames, nrOfGroupsPerYear,
//				nrOfSemigroupsInGroup);
//	}
//
//	private static void GenerateStudentGroups(String academicYear, List<String> departmentNames,
//			List<String> yearsOfStudy, List<String> subjectAreasNames, int nrOfGroupsPerYear,
//			int nrOfSemigroupsInGroup) {
//
//		String composingGroupsAcademicYear = String
//				.format("List<StudentGroup> comp%s = new ArrayList<StudentGroup>();\n", academicYear);
//		composingGroupsAcademicYear += String.format("comp%s.add(", academicYear);
//
//		for (int i = 0; i < departmentNames.size(); i++) {
//			String departmentName = departmentNames.get(i);
//			String composingGroupsDepartment = String
//					.format("List<StudentGroup> comp%s = new ArrayList<StudentGroup>();\n", departmentName);
//			composingGroupsDepartment += String.format("comp%s.add(", departmentName);
//
//			for (int j = 1; j <= yearsOfStudy.size(); j++) {
//
//				String yearOfStudy = String.format("%s_%s", yearsOfStudy.get(j - 1), departmentName);
//				String composingGroupsYearOfStudy = String
//						.format("List<StudentGroup> comp%s = new ArrayList<StudentGroup>();\n", yearOfStudy);
//				composingGroupsYearOfStudy += String.format("comp%s.add(", yearOfStudy);
//
//				for (int k = 1; k <= nrOfGroupsPerYear; k++) {
//
//					String gName = String.format("1%d0%d", j, (i * nrOfGroupsPerYear) + k);
//					String composingGroupsGrupa = String
//							.format("List<StudentGroup> comp%s = new ArrayList<StudentGroup>();\n", gName);
//					composingGroupsGrupa += String.format("comp%s.add(", gName);
//
//					for (int l = 0; l < nrOfSemigroupsInGroup; l++) {
//						String letter = ((l % 2 == 0) ? "A" : "B");
//						String sName = String.format("%s%s", gName, letter);
//						int nrOfStudents = ThreadLocalRandom.current().nextInt(20, 30 + 1);
//
//						System.out.println(String.format(
//								"StudentGroup semigrupa%s = new StudentGroup(0, \"Semigrupa\", \"%s\", %d);", sName,
//								sName, nrOfStudents));
//						if (l < nrOfSemigroupsInGroup - 1) {
//							composingGroupsGrupa += String.format("semigrupa%s);\ncomp%s.add(", sName, gName);
//						} else {
//							composingGroupsGrupa += String.format("semigrupa%s);\n", sName, gName);
//						}
//					}
//					System.out.println(composingGroupsGrupa);
//
//					System.out.println(String.format(
//							"StudentGroup grupa%s = new StudentGroup(0, \"Grupa\", \"%s\", comp%s);\n\n", gName,
//							gName, gName));
//
//					if (k < nrOfGroupsPerYear) {
//						composingGroupsYearOfStudy += String.format("grupa%s);\ncomp%s.add(", gName, yearOfStudy);
//					} else {
//						composingGroupsYearOfStudy += String.format("grupa%s);\n", gName, yearOfStudy);
//					}
//				}
//				System.out.println(composingGroupsYearOfStudy);
//
//				System.out.println(String.format(
//						"StudentGroup yearOfStudy%s = new StudentGroup(0, \"YearOfStudy\", \"%s\", comp%s);\n\n",
//						yearOfStudy, yearOfStudy, yearOfStudy));
//
//				if (j < yearsOfStudy.size()) {
//					composingGroupsDepartment += String.format("yearOfStudy%s);\ncomp%s.add(", yearOfStudy,
//							departmentName);
//				} else {
//					composingGroupsDepartment += String.format("yearOfStudy%s);\n", yearOfStudy, departmentName);
//				}
//			}
//			System.out.println(composingGroupsDepartment);
//
//			System.out.println(String.format(
//					"StudentGroup department%s = new StudentGroup(0, \"Department\", \"%s\", comp%s);\n\n",
//					departmentName, departmentName, departmentName));
//
//			if (i < departmentNames.size() - 1) {
//				composingGroupsAcademicYear += String.format("department%s);\ncomp%s.add(", departmentName,
//						academicYear);
//			} else {
//				composingGroupsAcademicYear += String.format("department%s);\n", departmentName, academicYear);
//			}
//		}
//		System.out.println(composingGroupsAcademicYear);
//
//		System.out.println(String.format(
//				"StudentGroup academicYear%s = new StudentGroup(0, \"AcademicYear\", \"%s\", comp%s);\n\n",
//				academicYear, academicYear, academicYear));
//
//		System.out.println(String.format(
//				"StudentGroupDAO studentGroupDAO = new StudentGroupJPADAOService();\nstudentGroupDAO.persist(academicYear%s);",
//				academicYear));
//	}
//}
