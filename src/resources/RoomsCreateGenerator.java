package resources;


public class RoomsCreateGenerator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    String service = "RoomDAO roomDAO = new RoomJPADAOService();";
	    System.out.println(service);
	    createAndSaveRoom("Sala de curs","AC0-1",100,"curs","AC");
	    createAndSaveRoom("Sala de curs","AC0-2",100,"curs","AC");
	    createAndSaveRoom("Sala de curs","T3",100,"curs","Rectorat");
	    createAndSaveRoom("Sala de curs","T4",100,"curs","Rectorat");
	    createAndSaveRoom("Sala de curs","E1",100,"curs","IEEIA");
	    createAndSaveRoom("Sala de curs","E2",100,"curs","IEEIA");
	    
	    createAndSaveRoom("Sala de seminar","AC0-3",40,"seminar","AC");
	    createAndSaveRoom("Sala de seminar","AC2-1",40,"seminar","AC");
	    createAndSaveRoom("Sala de seminar","AC2-2",40,"seminar","AC");
	    createAndSaveRoom("Sala de seminar","AC2-3",40,"seminar","AC");
	    createAndSaveRoom("Sala de seminar","AC3-1",40,"seminar","AC");
	    createAndSaveRoom("Sala de seminar","AC3-2",40,"seminar","AC");
	    createAndSaveRoom("Sala de seminar","AC3-3",40,"seminar","AC");
	    
	    createAndSaveRoom("Laborator de Informatica","C0-1",30,"laborator","AC");
	    createAndSaveRoom("Laborator de Informatica","C0-2",30,"laborator","AC");
	    createAndSaveRoom("Laborator de Informatica","C0-3",30,"laborator","AC");
	    createAndSaveRoom("Laborator de Informatica","C0-4",30,"laborator","AC");
	    createAndSaveRoom("Laborator de Informatica","C0-5",30,"laborator","AC");
	    createAndSaveRoom("Laborator de Informatica","C0-6",30,"laborator","AC");
	    
	    createAndSaveRoom("Laborator de Informatica","C1-3",30,"laborator","AC");
	    createAndSaveRoom("Laborator de Informatica","C1-4",30,"laborator","AC");
	    createAndSaveRoom("Laborator de Informatica","C1-5",30,"laborator","AC");
	    createAndSaveRoom("Laborator de Informatica","C1-6",30,"laborator","AC");
	    
	    createAndSaveRoom("Laborator de BPC","C2-8",30,"laborator","AC");
	    createAndSaveRoom("Laborator de Informatica","C2-9",30,"laborator","AC");
	    createAndSaveRoom("Laborator de Informatica","C2-10",30,"laborator","AC");
	    createAndSaveRoom("Laborator de Informatica","C2-11",30,"laborator","AC");
	    createAndSaveRoom("Laborator de Informatica","C2-12",30,"laborator","AC");
	    createAndSaveRoom("Laborator de Informatica","C2-13",30,"laborator","AC");
	    createAndSaveRoom("Laborator de Informatica","C2-14",30,"laborator","AC");
	    createAndSaveRoom("Laborator de Informatica","C3-3",30,"laborator","AC");
	    
	    createAndSaveRoom("Laborator de Informatica","A0-1",30,"laborator","AC");
	    createAndSaveRoom("Laborator de Informatica","A0-2",30,"laborator","AC");
	    createAndSaveRoom("Laborator de Informatica","A0-3",30,"laborator","AC");
	    createAndSaveRoom("Laborator de Informatica","A0-4",30,"laborator","AC");
	    createAndSaveRoom("Laborator de Informatica","A0-5",30,"laborator","AC");
	    createAndSaveRoom("Laborator de Informatica","A0-6",30,"laborator","AC");
	    
	    createAndSaveRoom("Laborator de Informatica","A1-3",30,"laborator","AC");
	    createAndSaveRoom("Laborator de Informatica","A1-4",30,"laborator","AC");
	    createAndSaveRoom("Laborator de Informatica","A1-9",30,"laborator","AC");
	    createAndSaveRoom("Laborator de Informatica","A1-13",30,"laborator","AC");
	    
	    createAndSaveRoom("Laborator de Informatica","A2-7",30,"laborator","AC");
	    createAndSaveRoom("Laborator de Informatica","A2-9",30,"laborator","AC");
	    createAndSaveRoom("Laborator de Informatica","A2-10",30,"laborator","AC");
	    createAndSaveRoom("Laborator de Informatica","A2-11",30,"laborator","AC");
	    createAndSaveRoom("Laborator de Informatica","A2-12",30,"laborator","AC");
	    createAndSaveRoom("Laborator de Informatica","A2-13",30,"laborator","AC");
	    createAndSaveRoom("Laborator de Informatica","A2-14",30,"laborator","AC");
	    createAndSaveRoom("Laborator de Informatica","A3-3",30,"laborator","AC");
	    
	    createAndSaveRoom("Laborator de Informatica","T9",30,"laborator","Rectorat");
	    createAndSaveRoom("Laborator de Electronica","I-23",30,"laborator","ETTI");
	    createAndSaveRoom("Cabinet de Engleza","CH-V",30,"laborator","ICPM");
	    // createAndSaveRoom("ceva","ceva",1111,"ceva","ceva");
	}

	private static void createAndSaveRoom(String name, String code, int capacity, String roomType, String building) {
		String varName = code.replace("-","");
		String text = String.format(
				"Room room%s = new Room(0, \"%s\", \"%s\", %d, %s, %s, new TimePreferences());\n",
				varName, name, code, capacity, roomType, building);
		text += String.format("roomDAO.merge(room%s);\n", varName);
		System.out.println(text);
	}	
}
