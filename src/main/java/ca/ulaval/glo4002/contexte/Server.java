package ca.ulaval.glo4002.contexte;

import java.util.Collection;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import ca.ulaval.glo4002.applicationService.ServiceLocator;
import ca.ulaval.glo4002.domain.Patient;
import ca.ulaval.glo4002.persistence.EntityManagerProvider;
import ca.ulaval.glo4002.persistence.HibernatePatientRepository;
import ca.ulaval.glo4002.ui.Console;

public class Server {
	public static Scanner scanner;

	public static EntityManagerFactory entityManagerFactory;
	public static HibernatePatientRepository patientRepository;
	public static EntityManager entityManager;

	public static void main(String[] args) {
		initializeServiceLocator();
		fillDatabase();

		try (Scanner scanner = new Scanner(System.in)) {
			Server.scanner = scanner;
			Console.startCommandPromptLoop();
		}

		System.exit(0);
	}

	private static void initializeServiceLocator() {
		ServiceLocator.INSTANCE.register(HibernatePatientRepository.class, new HibernatePatientRepository());
		ServiceLocator.INSTANCE.register(EntityManagerFactory.class, Persistence.createEntityManagerFactory("adt"));
	}

	private static void fillDatabase() {

		EntityManager entityManager = createEntityManager();

		entityManager.getTransaction().begin();

		Patient pierre = new Patient("Pierre");
		patientRepository.persist(pierre);

		Patient marie = new Patient("Marie");
		marie.moveToDepartment("ICU");
		patientRepository.persist(marie);

		entityManager.getTransaction().commit();
	}

	private static EntityManager createEntityManager() {
		patientRepository = ServiceLocator.INSTANCE.resolve(HibernatePatientRepository.class);

		entityManagerFactory = ServiceLocator.INSTANCE.resolve(EntityManagerFactory.class);
		entityManager = entityManagerFactory.createEntityManager();
		EntityManagerProvider.setEntityManager(entityManager);

		return entityManager;
	}

	public static void displayPatientList() {
		Collection<Patient> patients = patientRepository.findAll();

		for (Patient patient : patients) {
			System.out.println(String.format("%d : %s (status = %s, department = %s)", patient.getId(),
					patient.getName(), patient.getStatus(), patient.getDepartment()));
		}
	}

	public static void movePatient() {
		System.out.println("First, you must select a patient : ");
		displayPatientList();

		System.out.print("Patient ID to move : ");
		int patientId = Integer.parseInt(scanner.nextLine());

		System.out.print("New department : ");
		String newDepartment = scanner.nextLine();

		entityManager.getTransaction().begin();
		Patient patient = patientRepository.findById(patientId);
		patient.moveToDepartment(newDepartment);
		patientRepository.persist(patient);
		entityManager.getTransaction().commit();
	}

	public static void dischargePatient() {
		System.out.println("First, you must select a patient : ");
		displayPatientList();

		System.out.print("Patient ID to discharge : ");
		int patientId = Integer.parseInt(scanner.nextLine());

		entityManager.getTransaction().begin();
		Patient patient = patientRepository.findById(patientId);
		patient.discharge();
		patientRepository.persist(patient);
		entityManager.getTransaction().commit();
	}

}
