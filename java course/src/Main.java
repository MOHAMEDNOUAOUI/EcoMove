import javax.xml.crypto.Data;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws SQLException, ClassNotFoundException, InterruptedException {
        while (true) {
            System.out.println();
            System.out.println(" -----------------------------");
            System.out.println("|                             |");
            System.out.println("|           EcoMove           |");
            System.out.println("|  1 : Gestion du partenaire  |");
            System.out.println("|  2 : Gestion Du Contrats    |");
            System.out.println("|  3 : Gestion Du Offres      |");
            System.out.println("|  4 : Gestion Du Billets     |");
            System.out.println("|                             |");
            System.out.println(" -----------------------------");
            System.out.println();
            System.out.print("Enter Your Choice : ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    choicegestionDupartner();
                    break;
                    case 2:
                        choicegestionDucontrats();
                        break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }





    /*

██████╗░░█████╗░██████╗░████████╗███╗░░██╗███████╗██████╗░  ░█████╗░██████╗░███████╗░█████╗░
██╔══██╗██╔══██╗██╔══██╗╚══██╔══╝████╗░██║██╔════╝██╔══██╗  ██╔══██╗██╔══██╗██╔════╝██╔══██╗
██████╔╝███████║██████╔╝░░░██║░░░██╔██╗██║█████╗░░██████╔╝  ███████║██████╔╝█████╗░░███████║
██╔═══╝░██╔══██║██╔══██╗░░░██║░░░██║╚████║██╔══╝░░██╔══██╗  ██╔══██║██╔══██╗██╔══╝░░██╔══██║
██║░░░░░██║░░██║██║░░██║░░░██║░░░██║░╚███║███████╗██║░░██║  ██║░░██║██║░░██║███████╗██║░░██║
╚═╝░░░░░╚═╝░░╚═╝╚═╝░░╚═╝░░░╚═╝░░░╚═╝░░╚══╝╚══════╝╚═╝░░╚═╝  ╚═╝░░╚═╝╚═╝░░╚═╝╚══════╝╚═╝░░╚═╝
     */
    public static void choicegestionDupartner() throws SQLException, ClassNotFoundException, InterruptedException {

        boolean check = false;


        while (check == false) {

            System.out.println();
            System.out.println(" -----------------------------");
            System.out.println("|                             |");
            System.out.println("|      Gestion Partenaire     |");
            System.out.println("|  1 : Add Partenaire         |");
            System.out.println("|  2 : Modifier Partenaire    |");
            System.out.println("|  3 : Remove Partenaire      |");
            System.out.println("|  4 : Find a Partenaire      |");
            System.out.println("|  5 : List All Partenaires   |");
            System.out.println("|  6 : Return                 |");
            System.out.println("|                             |");
            System.out.println(" -----------------------------");
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
                    findOnePartenaire();
                    break;
                case 5:
                    AllPartners();
                    break;

                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }


        }


    }

    public static void createpartenair() throws InterruptedException {
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


        LocalDate date_creation = LocalDate.now();  ;



        Partenaire partenaire = new Partenaire(nom_compagnie, contact_commercial, typeTransport, zoneGeographique, conditionsSpeciales, statutPartenaire , date_creation);

        System.out.println("Partenaire " + partenaire.getNomCompagnie() + " succefully created.");
        Thread.sleep(2000);
    }



    public static void removepartenair() throws SQLException, ClassNotFoundException, InterruptedException {
        System.out.println("First of all, we shall check if we have this partenaire in our database.");
        System.out.println("Could you please give me the ID of the partenaire?");
        int id = scanner.nextInt();
        scanner.nextLine();

       Partenaire partenaire =  Partenaire.findPartenaireById(id);

       if (partenaire == null) {
           System.out.println("Sorry, we couldn't find this partenaire.");
       }
       else {
           System.out.println("Yes, this partenaire exists in our database. Are you sure you want to delete it? (yes/no)");



           boolean check = false;

           while (!check) {

               String choice = scanner.nextLine().trim().toLowerCase();
               if ("yes".equals(choice)) {
                   Partenaire.DeletePartenaire(id);
                   System.out.println("Partenaire has been deleted.");
                   Thread.sleep(2000);
                   check = true;
               } else if ("no".equals(choice)) {
                   System.out.println("Deletion is being canceled.");
                   Thread.sleep(2000);
                   check = true;
               } else {
                   System.out.println("Invalid choice. Please enter 'yes' or 'no'.");
                   System.out.print("Are you sure you want to delete it? (yes/no): ");
               }


           }

       }

    }

    public static void AllPartners() throws SQLException, ClassNotFoundException, InterruptedException {


        int idWidth = 5;
        int nameWidth = 20;
        int contactWidth = 20;
        int typeWidth = 10;
        int zoneWidth = 15;
        int conditionsWidth = 25;
        int statutWidth = 10;
        int dateWidth = 15;




        // Print the table header
        System.out.printf("| %-"+idWidth+"s | %-"+nameWidth+"s | %-"+contactWidth+"s | %-"+typeWidth+"s | %-"+zoneWidth+"s | %-"+conditionsWidth+"s | %-"+statutWidth+"s | %-"+dateWidth+"s |\n",
                "ID", "Compagnie", "Contact", "Transport", "Zone", "Conditions", "Statut", "Date");
        System.out.println(new String(new char[idWidth + nameWidth + contactWidth + typeWidth + zoneWidth + conditionsWidth + statutWidth + dateWidth + 11]).replace('\0', '-'));


        List<Partenaire> partenaires = Partenaire.getAllPartenaires();

        for (Partenaire partenaire : partenaires) {
            System.out.printf("| %-"+idWidth+"d | %-"+nameWidth+"s | %-"+contactWidth+"s | %-"+typeWidth+"s | %-"+zoneWidth+"s | %-"+conditionsWidth+"s | %-"+statutWidth+"s | %-"+dateWidth+"s |\n",
                    partenaire.getId(),
                    truncate(partenaire.getNomCompagnie(), nameWidth),
                    truncate(partenaire.getContactCommercial(), contactWidth),
                    partenaire.getTypeTransport().name(),
                    truncate(partenaire.getZoneGeographique(), zoneWidth),
                    truncate(partenaire.getConditionsSpeciales(), conditionsWidth),
                    partenaire.getStatutPartenaire().name(),
                    partenaire.getDateCreation().toLocalDate().toString());
        }

        System.out.println(new String(new char[idWidth + nameWidth + contactWidth + typeWidth + zoneWidth + conditionsWidth + statutWidth + dateWidth + 11]).replace('\0', '-'));


        boolean check = false;

        System.out.println("Woud you like to leave ? type (quit)");

        while (!check) {
            String choice = scanner.nextLine().trim().toLowerCase();
            if ("quit".equals(choice)) {
                System.out.println("Quitting.");
                System.out.println();

                Thread.sleep(2000);


                check = true;
            }
            else {
                System.out.println("Alright Sir you choose what you wantt");
                System.out.println("if you changed your mind all you have to do is type (quit)");
            }
        }

    }


    // method to fix the sizes of the strings and columns
    private static String truncate(String str, int width) {
        if (str.length() > width - 3) {
            return str.substring(0, width - 3) + "...";
        }
        return str;
    }


    public static void findOnePartenaire() throws SQLException, ClassNotFoundException, InterruptedException {
        System.out.println("Enter Partenaire Id:");
        int partenaireId = scanner.nextInt();
        scanner.nextLine();


        int idWidth = 5;
        int nameWidth = 20;
        int contactWidth = 20;
        int typeWidth = 10;
        int zoneWidth = 15;
        int conditionsWidth = 25;
        int statutWidth = 10;
        int dateWidth = 15;

        Partenaire partenaire = Partenaire.findPartenaireById(partenaireId);

        if (partenaire != null) {








            // Print the table header
            System.out.printf("| %-"+idWidth+"s | %-"+nameWidth+"s | %-"+contactWidth+"s | %-"+typeWidth+"s | %-"+zoneWidth+"s | %-"+conditionsWidth+"s | %-"+statutWidth+"s | %-"+dateWidth+"s |\n",
                    "ID", "Compagnie", "Contact", "Transport", "Zone", "Conditions", "Statut", "Date");
            System.out.println(new String(new char[idWidth + nameWidth + contactWidth + typeWidth + zoneWidth + conditionsWidth + statutWidth + dateWidth + 11]).replace('\0', '-'));









            System.out.printf("| %-"+idWidth+"d | %-"+nameWidth+"s | %-"+contactWidth+"s | %-"+typeWidth+"s | %-"+zoneWidth+"s | %-"+conditionsWidth+"s | %-"+statutWidth+"s | %-"+dateWidth+"s |\n",
                    partenaire.getId(),
                    truncate(partenaire.getNomCompagnie(), nameWidth),
                    truncate(partenaire.getContactCommercial(), contactWidth),
                    partenaire.getTypeTransport().name(),
                    truncate(partenaire.getZoneGeographique(), zoneWidth),
                    truncate(partenaire.getConditionsSpeciales(), conditionsWidth),
                    partenaire.getStatutPartenaire().name(),
                    partenaire.getDateCreation().toLocalDate().toString());


            boolean check = false;

            System.out.println("Woud you like to leave ? type (quit)");

            while (!check) {
                String choice = scanner.nextLine().trim().toLowerCase();
                if ("quit".equals(choice)) {
                    System.out.println("Quitting.");
                    System.out.println();

                    Thread.sleep(2000);


                    check = true;
                }
                else {
                    System.out.println("Alright Sir you choose what you wantt");
                    System.out.println("if you changed your mind all you have to do is type (quit)");
                }
            }

        } else {
            System.out.println("Partenaire not found.");
        }
    }





    public static void modifypartenaire() throws SQLException, ClassNotFoundException, InterruptedException {
        System.out.println("Enter Partenaire Id:");
        int partenaireId = scanner.nextInt();
        scanner.nextLine();


        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        conn = Database.getConnection();






        Partenaire partenaire = Partenaire.findPartenaireById(partenaireId);

       if(partenaire != null) {


           boolean check = false;

           while(!check) {
               System.out.println();
               System.out.println(" -----------------------------");
               System.out.println("|       Modification Kit      |");
               System.out.println("|                             |");
               System.out.println("|  1 : Nom_compagnie          |");
               System.out.println("|  2 : contact_commercial     |");
               System.out.println("|  3 : type_transport         |");
               System.out.println("|  4 : zone_geographique      |");
               System.out.println("|  5 : conditions_speciales   |");
               System.out.println("|  6 : statut_partenaire      |");
               System.out.println("|  7 : date_creation          |");
               System.out.println("|  8 : leave                  |");
               System.out.println("|                             |");
               System.out.println(" -----------------------------");
               System.out.print("Enter Your Choice : ");
               int choice = scanner.nextInt();
               scanner.nextLine();

               switch (choice) {
                   case 1:
                   {
                       System.out.print("Enter The new compagnie name : ");
                       String value = scanner.nextLine();
                       Partenaire.ModifyPartner(partenaireId , "nom_compagnie" , value );
                       Thread.sleep(2000);
                       break;
                   }

                   case 2 : {

                       System.out.print("Enter The new Contact Commercial : ");
                       String value = scanner.nextLine();
                       Partenaire.ModifyPartner(partenaireId , "contact_commercial" , value );
                       Thread.sleep(2000);
                       break;




                   }

                   case 3 : {
                       System.out.print("Enter The new type_transport : " + Arrays.toString(Partenaire.TypeTransport.values()));
                       String value = scanner.nextLine();
                       Partenaire.ModifyPartner(partenaireId , "type_transport" , value );
                       Thread.sleep(2000);
                       break;
                   }

                   case 4 : {
                       System.out.print("Enter The new zone_geographique: ");
                       String value = scanner.nextLine();
                       Partenaire.ModifyPartner(partenaireId , "zone_geographique" , value );
                       Thread.sleep(2000);
                       break;
                   }

                   case 5 : {
                       System.out.print("Enter The new conditions_speciales: ");
                       String value = scanner.nextLine();
                       Partenaire.ModifyPartner(partenaireId , "conditions_speciales" , value );
                       Thread.sleep(2000);
                       break;
                   }

                   case 6 : {
                       System.out.print("Enter The new statut_partenaire: " + Arrays.toString(Partenaire.StatutPartenaire.values()));
                       String value = scanner.nextLine();
                       Partenaire.ModifyPartner(partenaireId , "statut_partenaire" , value );
                       Thread.sleep(2000);
                       break;
                   }


                   case 7 : {
                       System.out.print("Enter The new date_creation (0000-00-00): ");
                       String value = scanner.nextLine();

                       Date newdate = Date.valueOf(value);
                       Partenaire.ModifyPartner(partenaireId , "date_creation" , String.valueOf(newdate));
                       Thread.sleep(2000);
                       break;
                   }




                   case 8 :
                       return;
                   default:
                       System.out.println("invalid Choice Please try again");
               }


           }


       }


    }











    /*

░█████╗░░█████╗░███╗░░██╗████████╗██████╗░░█████╗░████████╗  ░█████╗░██████╗░███████╗░█████╗░
██╔══██╗██╔══██╗████╗░██║╚══██╔══╝██╔══██╗██╔══██╗╚══██╔══╝  ██╔══██╗██╔══██╗██╔════╝██╔══██╗
██║░░╚═╝██║░░██║██╔██╗██║░░░██║░░░██████╔╝███████║░░░██║░░░  ███████║██████╔╝█████╗░░███████║
██║░░██╗██║░░██║██║╚████║░░░██║░░░██╔══██╗██╔══██║░░░██║░░░  ██╔══██║██╔══██╗██╔══╝░░██╔══██║
╚█████╔╝╚█████╔╝██║░╚███║░░░██║░░░██║░░██║██║░░██║░░░██║░░░  ██║░░██║██║░░██║███████╗██║░░██║
░╚════╝░░╚════╝░╚═╝░░╚══╝░░░╚═╝░░░╚═╝░░╚═╝╚═╝░░╚═╝░░░╚═╝░░░  ╚═╝░░╚═╝╚═╝░░╚═╝╚══════╝╚═╝░░╚═╝
     */


    public static void choicegestionDucontrats() {


       while(true) {
           System.out.println();
           System.out.println(" -----------------------------");
           System.out.println("|                             |");
           System.out.println("|       Gestion Contracts     |");
           System.out.println("|  1 : Add Contract           |");
           System.out.println("|  2 : Modifier Contract      |");
           System.out.println("|  3 : Remove Contract        |");
           System.out.println("|  4 : Find a Contract        |");
           System.out.println("|  5 : List All Contracts     |");
           System.out.println("|  6 : Return                 |");
           System.out.println("|                             |");
           System.out.println(" -----------------------------");
           System.out.print("Enter Your Choice : ");
           int choice = scanner.nextInt();
           scanner.nextLine();



           switch (choice) {
               case 1:
                   AddContract();
                   break;
           }
       }



    }



    public static void AddContract() {
        System.out.println("So welcome Sir to the contrat adding system . i wil be ur guide for this one and im happy to do so!");
        System.out.println("First of all you should give the partner id associated with this contrat we building");
        int partenaireId = scanner.nextInt();
        scanner.nextLine();



        LocalDate date_debut = null;
        LocalDate date_fin = null;


        while (date_debut == null) {
            System.out.println("Enter the start date for the contract (YYYY-MM-DD):");
            String dateDebutString = scanner.nextLine();
            try {
                date_debut = LocalDate.parse(dateDebutString);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }


        while (date_fin == null) {
            System.out.println("Enter the end date for the contract (YYYY-MM-DD):");
            String dateFinString = scanner.nextLine();
            try {
                date_fin = LocalDate.parse(dateFinString);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            }
        }





        System.out.println("Now you may enter a tarif special for the contrat");
        Float tarif_special = null;
        while (tarif_special == null) {
            try {
                tarif_special = scanner.nextFloat();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid float value.");
                scanner.nextLine();
            }
        }



        System.out.println("And also you should enter a conditons accord for the contrats");
        String conditions_accord = scanner.nextLine();





        System.out.println("Is this contract renewable? (true/false):");
        boolean renouvelable = false;
        while (!scanner.hasNextBoolean()) {
            System.out.println("Invalid input. Please enter 'true' or 'false'.");
            scanner.next();
        }
        renouvelable = scanner.nextBoolean();
        scanner.nextLine();




        System.out.println("Choose a contrat statut from those examples " + Arrays.toString(Contrats.statut_contrat.values()));

        Contrats.statut_contrat statut_contrat = null;

        while(statut_contrat == null) {
            String statut_contratstr = scanner.nextLine().toLowerCase();

            try {
                statut_contrat = Contrats.statut_contrat.valueOf(statut_contratstr);

            }catch (IllegalArgumentException e){
                System.out.println("invalid contrat statut . Defaulting to encours");
                statut_contrat = Contrats.statut_contrat.encours;
            }
            statut_contrat = Contrats.statut_contrat.valueOf(statut_contratstr);
        }





        Contrats contrat = new Contrats(date_debut,date_fin,tarif_special,conditions_accord,renouvelable,statut_contrat,partenaireId);


    }

    /*

    Ends of Contrat Area
     */
}
