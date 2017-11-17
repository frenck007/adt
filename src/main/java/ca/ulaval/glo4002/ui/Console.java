package ca.ulaval.glo4002.ui;

import ca.ulaval.glo4002.contexte.Server;

public class Console {

	public static void startCommandPromptLoop() {
		System.out.println("Welcome to GLO-4002's ADT!");
	
		boolean quit = false;
	
		while (!quit) {
			String option = Console.pickOptionFromMenu();
			System.out.println("\n");
			switch (option) {
			case "1": {
				Server.displayPatientList();
				break;
			}
			case "2": {
				Server.movePatient();
				break;
			}
			case "3": {
				Server.dischargePatient();
				break;
			}
			case "q": {
				quit = true;
				break;
			}
			default: {
				System.out.println("Invalid option");
			}
			}
		}
	
		System.out.println("Bye bye");
	
	}

	public static String pickOptionFromMenu() {
		System.out.println("\n");
		System.out.println("-----------------");
		System.out.println("What do you want to do?");
		System.out.println("1) List patients");
		System.out.println("2) Move a patient");
		System.out.println("3) Discharge a patient");
	
		System.out.print("Choice (or q tp quit) : ");
		return Server.scanner.nextLine();
	}

}
