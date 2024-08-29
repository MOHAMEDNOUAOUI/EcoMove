import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("1 : Gestion du partenaire");
            System.out.println("coming soon");
            System.out.print("Enter Your Choice : ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    choicegestionDupartner();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void choicegestionDupartner() {
        System.out.println("1 : Add Partenaire");
        System.out.println("2 : Modifier Partenaire");
        System.out.println("3 : Remove Partenaire");
        System.out.println("4 : List All Partenaires");
        System.out.println("5 : Return");
        System.out.print("Enter Your Choice : ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice) {
            case 1:
                createpartenair();
                break;
            case 2:
                modifypartenaire();
                break;
            case 3:
                removepartenair();
                break;
            case 4:
                AllPartners();
                break;
            case 5:
                return; // Returns to the previous menu or exits if it's the top level
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    public static void createpartenair() {
        System.out.println("Enter Compagnie name");
        String nom_compagnie = scanner.nextLine();
        System.out.println("Enter Contact Commercial");
        String contact_commercial = scanner.nextLine();

        System.out.println("Enter type of transport (AVION, TRAIN, BUS): ");
        for (Partenaire.TypeTransport type : Partenaire.TypeTransport.values()) {
            System.out.println(type);
        }
        Partenaire.TypeTransport typeTransport = null;
        while (typeTransport == null) {
            String typeTransportStr = scanner.nextLine().toUpperCase();
            try {
                typeTransport = Partenaire.TypeTransport.valueOf(typeTransportStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid transport type. Please enter again.");
            }
        }

        System.out.print("Enter geographic zone: ");
        String zoneGeographique = scanner.nextLine();

        System.out.print("Enter special conditions: ");
        String conditionsSpeciales = scanner.nextLine();

        System.out.println("Enter status (ACTIF, INACTIF, SUSPENDU): ");
        for (Partenaire.StatutPartenaire statut : Partenaire.StatutPartenaire.values()) {
            System.out.println(statut);
        }
        Partenaire.StatutPartenaire statutPartenaire = null;
        while (statutPartenaire == null) {
            String statutPartenaireStr = scanner.nextLine().toUpperCase();
            try {
                statutPartenaire = Partenaire.StatutPartenaire.valueOf(statutPartenaireStr);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid status. Defaulting to ACTIF.");
                statutPartenaire = Partenaire.StatutPartenaire.ACTIF;
            }
        }

        System.out.print("Enter creation date (YYYY-MM-DD): ");
        String dateCreation = scanner.nextLine();

        Partenaire partenaire = new Partenaire(nom_compagnie, contact_commercial, typeTransport, zoneGeographique, conditionsSpeciales, statutPartenaire, dateCreation);
        // Save the partenaire object to the database here
        System.out.println("Partenaire created: " + partenaire);
    }

    public static void modifypartenaire() {
        // Implement modification logic here
    }

    public static void removepartenair() {
        // Implement removal logic here
    }

    public static void AllPartners() {

    }
}
