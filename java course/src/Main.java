import javax.swing.plaf.synth.SynthTextAreaUI;
import javax.xml.crypto.Data;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

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
                case 3:
                    choicegestionOffres();
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
        String idString = scanner.nextLine();
        scanner.nextLine();

        UUID id = UUID.fromString(idString);
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


        // Set fixed widths for each column
        int idWidth = 36; // UUID length
        int nameWidth = 20;
        int contactWidth = 20;
        int typeWidth = 15;
        int zoneWidth = 15;
        int conditionsWidth = 30;
        int statutWidth = 10;
        int dateWidth = 12;




        System.out.printf("| %-"+idWidth+"s | %-"+nameWidth+"s | %-"+contactWidth+"s | %-"+typeWidth+"s | %-"+zoneWidth+"s | %-"+conditionsWidth+"s | %-"+statutWidth+"s | %-"+dateWidth+"s |\n",
                "ID", "Compagnie", "Contact", "Transport", "Zone", "Conditions", "Statut", "Date");
        System.out.println(new String(new char[idWidth + nameWidth + contactWidth + typeWidth + zoneWidth + conditionsWidth + statutWidth + dateWidth + 11]).replace('\0', '-'));


        List<Partenaire> partenaires = Partenaire.getAllPartenaires();

        for (Partenaire partenaire : partenaires) {
            System.out.printf("| %-"+idWidth+"s | %-"+nameWidth+"s | %-"+contactWidth+"s | %-"+typeWidth+"s | %-"+zoneWidth+"s | %-"+conditionsWidth+"s | %-"+statutWidth+"s | %-"+dateWidth+"s |\n",
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
    private static String truncate(String value, int length) {
        if (value.length() <= length) {
            return value;
        } else {
            return value.substring(0, length - 3) + "..."; // Add ellipsis if truncated
        }
    }


    public static void findOnePartenaire() throws SQLException, ClassNotFoundException, InterruptedException {
        System.out.println("Enter Partenaire Id:");
        String partenaireId = scanner.nextLine();





        UUID partenaireUUID = UUID.fromString(partenaireId);
        Partenaire partenaire = Partenaire.findPartenaireById(partenaireUUID);


        int idWidth = 36; // UUID length
        int nameWidth = 20;
        int contactWidth = 20;
        int typeWidth = 15;
        int zoneWidth = 15;
        int conditionsWidth = 30;
        int statutWidth = 10;
        int dateWidth = 12;






        if (partenaire != null) {








            System.out.printf("| %-"+idWidth+"s | %-"+nameWidth+"s | %-"+contactWidth+"s | %-"+typeWidth+"s | %-"+zoneWidth+"s | %-"+conditionsWidth+"s | %-"+statutWidth+"s | %-"+dateWidth+"s |\n",
                    "ID", "Compagnie", "Contact", "Transport", "Zone", "Conditions", "Statut", "Date");
            System.out.println(new String(new char[idWidth + nameWidth + contactWidth + typeWidth + zoneWidth + conditionsWidth + statutWidth + dateWidth + 11]).replace('\0', '-'));









            System.out.printf("| %-"+idWidth+"s | %-"+nameWidth+"s | %-"+contactWidth+"s | %-"+typeWidth+"s | %-"+zoneWidth+"s | %-"+conditionsWidth+"s | %-"+statutWidth+"s | %-"+dateWidth+"s |\n",
                    partenaire.getId(),
                    truncate(partenaire.getNomCompagnie(), nameWidth),
                    truncate(partenaire.getContactCommercial(), contactWidth),
                    partenaire.getTypeTransport().name(),
                    truncate(partenaire.getZoneGeographique(), zoneWidth),
                    truncate(partenaire.getConditionsSpeciales(), conditionsWidth),
                    partenaire.getStatutPartenaire().name(),
                    partenaire.getDateCreation().toLocalDate().toString());
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println();
            System.out.println("All the contrats associated to this Partenaire are below");
            System.out.println();






            List<Contrats> contrats = partenaire.GetContrats();
            if(contrats != null && !contrats.isEmpty())
            {



                System.out.printf("| %-"+idWidth+"s | %-"+dateWidth+"s | %-"+dateWidth+"s | %-"+typeWidth+"s | %-"+statutWidth+"s | %-"+conditionsWidth+"s |\n",
                        "ID", "DATE_DEBUT", "DATE_FIN", "TARIF_SPECIAL", "STATUT_CONTRAT", "CONDITION_SPECIAL");
                System.out.println(new String(new char[idWidth + dateWidth + dateWidth + typeWidth + statutWidth + conditionsWidth + 11]).replace('\0', '-'));







                for(Contrats contrat : contrats){
                    System.out.printf("| %-"+idWidth+"s | %-"+dateWidth+"s | %-"+dateWidth+"s | %-"+typeWidth+"s | %-"+statutWidth+"s | %-"+conditionsWidth+"s |\n",
                            contrat.getId(),
                            truncate(contrat.getDate_debut().toString() , dateWidth),
                            truncate(contrat.getDate_fin().toString() , dateWidth),
                            contrat.getTarif_special(),
                            truncate(contrat.getStatut_contrat().name() , statutWidth),
                            truncate(contrat.getConditions_accord(), conditionsWidth));

                    System.out.println("--------------------------------------------------------------------------------------------------------------------------------------------");
                    System.out.println();
                }
            }
            else {
                System.out.println("No contracts associated with this Partenaire.");
            }


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
        String partenaireId = scanner.nextLine();


        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        conn = Database.getConnection();





        UUID PartenaireUUID =UUID.fromString(partenaireId);
        Partenaire partenaire = Partenaire.findPartenaireById(PartenaireUUID);

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
                       Partenaire.ModifyPartner(PartenaireUUID , "nom_compagnie" , value );
                       Thread.sleep(2000);
                       break;
                   }

                   case 2 : {

                       System.out.print("Enter The new Contact Commercial : ");
                       String value = scanner.nextLine();
                       Partenaire.ModifyPartner(PartenaireUUID , "contact_commercial" , value );
                       Thread.sleep(2000);
                       break;




                   }

                   case 3 : {
                       System.out.print("Enter The new type_transport : " + Arrays.toString(Partenaire.TypeTransport.values()));
                       String value = scanner.nextLine();
                       Partenaire.ModifyPartner(PartenaireUUID , "type_transport" , value );
                       Thread.sleep(2000);
                       break;
                   }

                   case 4 : {
                       System.out.print("Enter The new zone_geographique: ");
                       String value = scanner.nextLine();
                       Partenaire.ModifyPartner(PartenaireUUID, "zone_geographique" , value );
                       Thread.sleep(2000);
                       break;
                   }

                   case 5 : {
                       System.out.print("Enter The new conditions_speciales: ");
                       String value = scanner.nextLine();
                       Partenaire.ModifyPartner(PartenaireUUID , "conditions_speciales" , value );
                       Thread.sleep(2000);
                       break;
                   }

                   case 6 : {
                       System.out.print("Enter The new statut_partenaire: " + Arrays.toString(Partenaire.StatutPartenaire.values()));
                       String value = scanner.nextLine();
                       Partenaire.ModifyPartner(PartenaireUUID, "statut_partenaire" , value );
                       Thread.sleep(2000);
                       break;
                   }


                   case 7 : {
                       System.out.print("Enter The new date_creation (0000-00-00): ");
                       String value = scanner.nextLine();

                       Date newdate = Date.valueOf(value);
                       Partenaire.ModifyPartner(PartenaireUUID , "date_creation" , String.valueOf(newdate));
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


    public static void choicegestionDucontrats() throws SQLException, ClassNotFoundException, InterruptedException {


        boolean check = false;

       while(check == false) {
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

               case 2:
                   ModifyContrat();
                   break;

               case 3:
                   DeleteContrat();
                   break;

               case 4:
                   FindOneContrat();
                   break;

               case 5:
                   FindAllContrats();
                   break;



               case 6:
                   return;

               default:
                   System.out.println("Invalid Choice , Please try again ");
           }
       }



    }



    public static void AddContract() throws SQLException, ClassNotFoundException {
        System.out.println("So welcome Sir to the contrat adding system . i will be ur guide for this one and im happy to do so!");




        LocalDate date_debut = null;
        LocalDate date_fin = null;


        while (date_debut == null) {
            System.out.println("First of all you should Enter the start date for the contract (YYYY-MM-DD):");
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




        System.out.println("Choose a contrat statut from those examples " + Arrays.toString(Contrats.StatutContrat.values()));

        Contrats.StatutContrat statut_contrat = null;

        while(statut_contrat == null) {
            String statut_contratstr = scanner.nextLine().toLowerCase();

            try {
                statut_contrat = Contrats.StatutContrat.valueOf(statut_contratstr);

            }catch (IllegalArgumentException e){
                System.out.println("invalid contrat statut . Defaulting to encours");
                statut_contrat = Contrats.StatutContrat.encours;
            }
            statut_contrat = Contrats.StatutContrat.valueOf(statut_contratstr);
        }



        System.out.println("Now all you have to do is to provide me with the partner id associated with this contrat we building");


        Partenaire partenaire = null;

        while(partenaire == null){

            try{
                String partenaireId  = scanner.nextLine();
                UUID partenaireUUID = UUID.fromString(partenaireId);
                partenaire = Partenaire.findPartenaireById(partenaireUUID);
            }catch (IllegalArgumentException e) {
                System.out.println("Can you try again we couldnt find the partenaire you looking for ");
            }



        }

        Contrats contrat = new Contrats(date_debut,date_fin,tarif_special,conditions_accord,renouvelable,statut_contrat,partenaire);


    }



    public static void FindAllContrats() throws SQLException, ClassNotFoundException, InterruptedException {

        int idWidth = 36; // UUID length
        int nameWidth = 20;
        int contactWidth = 20;
        int typeWidth = 15;
        int zoneWidth = 15;
        int conditionsWidth = 30;
        int statutWidth = 14;
        int dateWidth = 12;


        System.out.printf("| %-" + idWidth + "s | %-" + dateWidth + "s | %-" + dateWidth + "s | %-" + typeWidth + "s | %-" + statutWidth + "s | %-" + conditionsWidth + "s| %-" + nameWidth + "s |\n",
                "ID", "DATE_DEBUT", "DATE_FIN", "TARIF_SPECIAL", "STATUT_CONTRAT", "CONDITION_SPECIAL", "PARTENAIRE");
        System.out.println(new String(new char[idWidth + dateWidth + dateWidth + typeWidth + statutWidth + conditionsWidth + nameWidth]).replace('\0', '-'));


        List<Contrats> ContratsList = Contrats.GetAllContrats();

        for (Contrats contrat : ContratsList) {
            System.out.printf("| %-" + idWidth + "s | %-" + dateWidth + "s | %-" + dateWidth + "s | %-" + typeWidth + "s | %-" + statutWidth + "s | %-" + conditionsWidth + "s | %-" + nameWidth + "s |\n",
                    contrat.getId(),
                    truncate(contrat.getDate_debut().toString(), dateWidth),
                    truncate(contrat.getDate_fin().toString(), dateWidth),
                    contrat.getTarif_special(),
                    contrat.getStatut_contrat().name(),
                    truncate(contrat.getConditions_accord(), conditionsWidth),
                    truncate(contrat.GetPartenaire().getNomCompagnie(), nameWidth)
            );

            System.out.println(new String(new char[idWidth + dateWidth + dateWidth + typeWidth + statutWidth + conditionsWidth + nameWidth + 11]).replace('\0', '-'));
        }

            boolean check = false;

            System.out.println("Woud you like to leave ? type (quit)");

            while (!check) {
                String choice = scanner.nextLine().trim().toLowerCase();
                if ("quit".equals(choice)) {
                    System.out.println("Quitting.");
                    System.out.println();

                    Thread.sleep(2000);


                    check = true;
                } else {
                    System.out.println("Alright Sir you choose what you wantt");
                    System.out.println("if you changed your mind all you have to do is type (quit)");
                }
            }


        }




        public static boolean FindOneContrat() throws ClassNotFoundException {

                System.out.println("Please give me the Contrat id");
                String id = scanner.nextLine();


            int idWidth = 36; // UUID length
            int nameWidth = 20;
            int contactWidth = 20;
            int typeWidth = 15;
            int zoneWidth = 15;
            int conditionsWidth = 30;
            int statutWidth = 14;
            int dateWidth = 12;



                UUID contratID = UUID.fromString(id);

                Contrats contrat = Contrats.FindOneContrat(contratID);

                if(contrat != null){


                    System.out.printf("| %-" + idWidth + "s | %-" + dateWidth + "s | %-" + dateWidth + "s | %-" + typeWidth + "s | %-" + statutWidth + "s | %-" + conditionsWidth + "s| %-" + nameWidth + "s |\n",
                            "ID", "DATE_DEBUT", "DATE_FIN", "TARIF_SPECIAL", "STATUT_CONTRAT", "CONDITION_SPECIAL", "PARTENAIRE");
                    System.out.println(new String(new char[idWidth + dateWidth + dateWidth + typeWidth + statutWidth + conditionsWidth + nameWidth]).replace('\0', '-'));



                    System.out.printf("| %-" + idWidth + "s | %-" + dateWidth + "s | %-" + dateWidth + "s | %-" + typeWidth + "s | %-" + statutWidth + "s | %-" + conditionsWidth + "s | %-" + nameWidth + "s |\n",
                            contrat.getId(),
                            truncate(contrat.getDate_debut().toString(), dateWidth),
                            truncate(contrat.getDate_fin().toString(), dateWidth),
                            contrat.getTarif_special(),
                            truncate(contrat.getStatut_contrat().name(), statutWidth),
                            truncate(contrat.getConditions_accord(), conditionsWidth),
                            truncate(contrat.GetPartenaire().getNomCompagnie(), nameWidth)
                    );



                            System.out.println(new String(new char[idWidth + nameWidth + contactWidth + typeWidth + zoneWidth + conditionsWidth + statutWidth + dateWidth + 11]).replace('\0', '-'));



                            return true;
                }


                return false;
        }


        public static void DeleteContrat() throws ClassNotFoundException, SQLException {
                System.out.println("Please give me the id of the contrat");
                String value = scanner.nextLine();

                UUID idcontrat = UUID.fromString(value);
                Contrats contrat = Contrats.FindOneContrat(idcontrat);



            int idWidth = 36; // UUID length
            int nameWidth = 20;
            int contactWidth = 20;
            int typeWidth = 15;
            int zoneWidth = 15;
            int conditionsWidth = 30;
            int statutWidth = 14;
            int dateWidth = 12;


            if(contrat != null) {



                System.out.printf("| %-" + idWidth + "s | %-" + dateWidth + "s | %-" + dateWidth + "s | %-" + typeWidth + "s | %-" + statutWidth + "s | %-" + conditionsWidth + "s| %-" + nameWidth + "s |\n",
                        "ID", "DATE_DEBUT", "DATE_FIN", "TARIF_SPECIAL", "STATUT_CONTRAT", "CONDITION_SPECIAL", "PARTENAIRE");
                System.out.println(new String(new char[idWidth + dateWidth + dateWidth + typeWidth + statutWidth + conditionsWidth + nameWidth]).replace('\0', '-'));



                System.out.printf("| %-" + idWidth + "s | %-" + dateWidth + "s | %-" + dateWidth + "s | %-" + typeWidth + "s | %-" + statutWidth + "s | %-" + conditionsWidth + "s | %-" + nameWidth + "s |\n",
                        contrat.getId(),
                        truncate(contrat.getDate_debut().toString(), dateWidth),
                        truncate(contrat.getDate_fin().toString(), dateWidth),
                        contrat.getTarif_special(),
                        contrat.getStatut_contrat().name(),
                        truncate(contrat.getConditions_accord(), conditionsWidth),
                        truncate(contrat.GetPartenaire().getNomCompagnie(), nameWidth)
                );



                System.out.println(new String(new char[idWidth + nameWidth + contactWidth + typeWidth + zoneWidth + conditionsWidth + statutWidth + dateWidth + 11]).replace('\0', '-'));





                System.out.println();
                System.out.println("Are you sure you want to delete this contrat ? (yes/no)");
                String choice = scanner.nextLine().toLowerCase().trim();

                if(choice.equals("yes")){
                    Contrats.DeleteContrat(idcontrat);
                }else{
                    System.out.println("Alright Sir , if its not yes then its a noo :D");
                }
            }

        }



        public static void ModifyContrat() throws InterruptedException, SQLException, ClassNotFoundException {
                        System.out.println("Alright first thing first is what is the contrat id you wanna modify ?");
                        String contratIdString = scanner.nextLine();

                        System.out.println("Alright ill go check if it exist , please wait a bit");
                        Thread.sleep(4000);

                        UUID idcontrat = UUID.fromString(contratIdString);
                        Contrats contrat = Contrats.FindOneContrat(idcontrat);

                        if(contrat != null) {
                            System.out.println("Alright Sir it defenityl exist , please look at the menu and choose what you wantt");


                            boolean check = false;

                            while(!check) {
                                System.out.println();
                                System.out.println(" -----------------------------");
                                System.out.println("|       Modification Kit      |");
                                System.out.println("|                             |");
                                System.out.println("|  1 : Date_debut             |");
                                System.out.println("|  2 : Date_fin               |");
                                System.out.println("|  3 : tarif_special          |");
                                System.out.println("|  4 : conditions_accord      |");
                                System.out.println("|  5 : renouvelable           |");
                                System.out.println("|  6 : statut_contrat         |");
                                System.out.println("|  7 : Partenaire             |");
                                System.out.println("|  8 : leave                  |");
                                System.out.println("|                             |");
                                System.out.println(" -----------------------------");
                                System.out.print("Enter Your Choice : ");
                                int choice = scanner.nextInt();
                                scanner.nextLine();

                                switch (choice) {
                                    case 1:
                                    {
                                        System.out.println("The current date_debut is " + contrat.getDate_debut());
                                        System.out.print("Enter The new date_debut : ");
                                        String value = scanner.nextLine();
                                        Contrats.ModifierContrat(idcontrat , "date_debut" , value );
                                        contrat.setDate_debut(value);
                                        Thread.sleep(2000);
                                        break;
                                    }

                                    case 2 : {

                                        System.out.println("The current date_fin is " + contrat.getDate_fin());
                                        System.out.print("Enter The new date : ");
                                        String value = scanner.nextLine();
                                        Contrats.ModifierContrat(idcontrat , "date_fin" , value );
                                        contrat.setDate_fin(value);
                                        Thread.sleep(2000);




                                        break;
                                    }

                                    case 3 : {

                                        System.out.println("The current tarif special is " + contrat.getTarif_special());
                                        System.out.print("Enter The new tarif special : ");
                                        String value = scanner.nextLine();
                                        Contrats.ModifierContrat(idcontrat , "tarif_special" , value );
                                        contrat.setTarif_special(Float.parseFloat(value));
                                        Thread.sleep(2000);





                                        break;
                                    }

                                    case 4 : {
                                        System.out.println("The current conditions_accord is :  " + contrat.getConditions_accord());
                                        System.out.print("Enter The new conditions_accord : ");
                                        String value = scanner.nextLine();
                                        Contrats.ModifierContrat(idcontrat , "conditions_accord" , value );
                                        contrat.setConditions_accord(value);
                                        Thread.sleep(2000);

                                        break;
                                    }

                                    case 5 : {



                                        System.out.println("The current renouvelable is :  " + contrat.isRenouvelable());
                                        System.out.print("Enter The new renouvelable type : ");
                                        String value = scanner.nextLine();
                                        Contrats.ModifierContrat(idcontrat , "renouvelable" , value );
                                        contrat.setRenouvelable(Boolean.parseBoolean(value));
                                        Thread.sleep(2000);

                                        break;
                                    }

                                    case 6 : {

                                        System.out.println("The current Statut of the contrat is :  " + contrat.getStatut_contrat());
                                        System.out.print("Enter The new Statut of the contrat (" + Arrays.toString(Contrats.StatutContrat.values()) + ")");
                                        String value = scanner.nextLine();
                                        Contrats.ModifierContrat(idcontrat , "statut_contrat" , value );
                                        contrat.setStatut_contrat(Contrats.StatutContrat.valueOf(value));
                                        Thread.sleep(2000);

                                        break;
                                    }


                                    case 7 : {

                                        System.out.println("The current Partenaire is :  " + contrat.GetPartenaire().getNomCompagnie());
                                        System.out.print("Enter The new Partenaire id is : ");
                                        String value = scanner.nextLine();
                                        Contrats.ModifierContrat(idcontrat , "partenaireid" , value );
                                        Partenaire partenaire = Partenaire.findPartenaireById(UUID.fromString(value));
                                        contrat.setPartenaire(partenaire);
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
                        else {
                            System.out.println("Sorry we couldnt find this contrat id");
                        }
            }



    /*

    Ends of Contrat Area
     */





    /*



░█████╗░███████╗███████╗██████╗░███████╗░██████╗
██╔══██╗██╔════╝██╔════╝██╔══██╗██╔════╝██╔════╝
██║░░██║█████╗░░█████╗░░██████╔╝█████╗░░╚█████╗░
██║░░██║██╔══╝░░██╔══╝░░██╔══██╗██╔══╝░░░╚═══██╗
╚█████╔╝██║░░░░░██║░░░░░██║░░██║███████╗██████╔╝
░█████╗░███████╗███████╗██████╗░███████╗░██████╗

░██████╗██████╗░███████╗░█████╗░██╗░█████╗░██╗░░░░░███████╗░██████╗
██╔════╝██╔══██╗██╔════╝██╔══██╗██║██╔══██╗██║░░░░░██╔════╝██╔════╝
╚█████╗░██████╔╝█████╗░░██║░░╚═╝██║███████║██║░░░░░█████╗░░╚█████╗░
░╚═══██╗██╔═══╝░██╔══╝░░██║░░██╗██║██╔══██║██║░░░░░██╔══╝░░░╚═══██╗
██████╔╝██║░░░░░███████╗╚█████╔╝██║██║░░██║███████╗███████╗██████╔╝
╚═════╝░╚═╝░░░░░╚══════╝░╚════╝░╚═╝╚═╝░░╚═╝╚══════╝╚══════╝╚═════╝░

     */




    public static  void choicegestionOffres() throws SQLException, ClassNotFoundException, InterruptedException {

        boolean check = false;

        while(check == false) {
            System.out.println();
            System.out.println(" -----------------------------");
            System.out.println("|                             |");
            System.out.println("|       Gestion Offres        |");
            System.out.println("|  1 : Add Offres             |");
            System.out.println("|  2 : Modifier Offre         |");
            System.out.println("|  3 : Remove Offre           |");
            System.out.println("|  4 : Find an offre          |");
            System.out.println("|  5 : List All Offers        |");
            System.out.println("|  6 : Return                 |");
            System.out.println("|                             |");
            System.out.println(" -----------------------------");
            System.out.print("Enter Your Choice : ");
            int choice = scanner.nextInt();
            scanner.nextLine();



            switch (choice) {
                case 1:
//                    AddOffre();
                    break;
                case 3:
                    DeleteAnOffer();
                    break;
                case 4:
                    FindAnOffer();
                    break;
                case 5:
                    FindAllOffer();
                    break;
                case 6:
                    return;

                default:
                    System.out.println("Invalid Choice , Please try again ");
            }
        }






    }



    /// ADD OFFREES

    public static void AddOffre() throws ClassNotFoundException, SQLException, InterruptedException {
        System.out.println();
                System.out.println("Welcome to Offre creating System");
                System.out.println();
                System.out.println("To start ill be asking you for some informations to establish first likee , which contrat this offre belongs and so.");


                boolean choice = false;



               while(!choice) {

                   System.out.println("So i would like you to give me the Contract id : ");
                   String value = scanner.nextLine();

                   Contrats contrat = Contrats.FindOneContrat(UUID.fromString(value));

                   if(contrat != null && contrat.CheckContractValid(contrat)) {

                       System.out.println("Alright this contracts exist  , now you can procced");
                       System.out.println("First we shall give this offer a name : ");
                       String nom_offre = scanner.nextLine();

                       System.out.println("A little description : ");
                       String description = scanner.nextLine();

                       System.out.println("Now ill ass you for the debut date for the offer (0000-00-00)");
                       LocalDate date_debut = LocalDate.parse(scanner.nextLine());

                       System.out.println("Now ill ass you for the fin date for the offer (0000-00-00)");
                       LocalDate date_fin = LocalDate.parse(scanner.nextLine());

                       System.out.println("And for the valeur reduction");
                       int valeur_reduction = scanner.nextInt();

<<<<<<< HEAD
                       System.out.println("A conditions for the offer :");
                       String conditions = scanner.nextLine();
                       scanner.nextLine();

                       System.out.println("Choose a reduction type for the valeur of reduction one of those two : ( " + Arrays.toString(Offres.TypeReduction.values())  + ")");

                       Offres.TypeReduction type_reduction = null;

                       while(type_reduction == null) {
                           String type_reductionstr = scanner.nextLine().toUpperCase();

                           try {
                               type_reduction = Offres.TypeReduction.valueOf(type_reductionstr);

                           }catch (IllegalArgumentException e){
                               System.out.println("invalid Offer reduction type . Defaulting to MONTANT FIX");
                               type_reduction = Offres.TypeReduction.MONTANTFIX;
                           }
                           type_reduction = Offres.TypeReduction.valueOf(type_reductionstr);
                       }

                       System.out.println("Now All we need is this offer statut : (" + Arrays.toString(Offres.StatutOffre.values())+ ") ");
                       Offres.StatutOffre statut_offre = null;

                       if(statut_offre == null) {
                           String statut_offreStr = scanner.nextLine().toUpperCase();

                           try {
                               statut_offre = Offres.StatutOffre.valueOf(statut_offreStr);
                           }
                           catch(IllegalArgumentException e) {
                               System.out.println("Invalid Offer statut , Defaulting to ACTIVE");
                               statut_offre = Offres.StatutOffre.ACTIVE;
                           }

                           statut_offre = Offres.StatutOffre.valueOf(statut_offreStr);

=======
                       System.out.println("Choose an offer statut : ( " + Arrays.toString(Offres.StatutOffre.values())  + ")");


                       Offres.StatutOffre statut_offre = null;

                       while(statut_offre == null) {
                           String statut_offreStr = scanner.nextLine().toLowerCase();

                           try {
                               statut_offre = Offres.StatutOffre.valueOf(statut_offreStr);

                           }catch (IllegalArgumentException e){
                               System.out.println("invalid contrat statut . Defaulting to encours");
                               statut_offre = Offres.StatutOffre.ACTIVE;
                           }
                           statut_offre = Offres.StatutOffre.valueOf(statut_offreStr);
                       }

                       System.out.println("Choose an offer reduction type : ( " + Arrays.toString(Offres.TypeReduction.values())  + ")");


                       Offres.TypeReduction type_reduction = null;

                       while(statut_offre == null) {
                           String type_reductionStr = scanner.nextLine().toLowerCase();

                           try {
                               type_reduction = Offres.TypeReduction.valueOf(type_reductionStr);

                           }catch (IllegalArgumentException e){
                               System.out.println("invalid contrat statut . Defaulting to encours");
                               type_reduction = Offres.TypeReduction.MONTANTFIX;
                           }
                           type_reduction = Offres.TypeReduction.valueOf(type_reductionStr);
>>>>>>> 1ae488bef07cc010c08a824e13fc8a5310cc61ed
                       }




                       Offres offer = new Offres(nom_offre , description , date_debut , date_fin , valeur_reduction , conditions , type_reduction , statut_offre , contrat);
                       System.out.println("Offer :  " + offer.getNom_offre()+ " succefully created.");
                       choice = true;
                       Thread.sleep(2000);
                       choice = true;
                   }else {
                       System.out.println("Sorry Sir this Contract doesnt exist at all , Or it ended , Please try again");
                   }
               }

    }


<<<<<<< HEAD
    public static void FindAnOffer() throws IllegalArgumentException, SQLException, ClassNotFoundException {



        Boolean choice = false;

        while(!choice) {


            System.out.println("Enter an offer id : ");
            UUID OfferId = UUID.fromString(scanner.nextLine());
            Offres offer = Offres.FindOneOffre(OfferId);


            if(offer !=null) {


                System.out.println("Offer found:");
                System.out.println();
                System.out.println("-------------------------------------------------");
                System.out.println("Offer ID: " + offer.getId());
                System.out.println("Name: " + offer.getNom_offre());
                System.out.println("Description: " + offer.getDescription());
                System.out.println("Start Date: " + offer.getDate_debut());
                System.out.println("End Date: " + offer.getDate_fin());
                System.out.println("Reduction Value: " + offer.getValeur_reduction());
                System.out.println("Conditions: " + offer.getConditions());
                System.out.println("Reduction Type: " + offer.getType_reduction());
                System.out.println("Status: " + offer.getStatut_offre());
                System.out.println("Contract ID: " + offer.getContrat().getId());
                System.out.println("-------------------------------------------------");

                choice = true;
            }
            else {
                System.out.println("Sorry this Offer is invalid ");
            }

        }
    }


    public static void FindAllOffer() throws IllegalArgumentException, ClassNotFoundException, SQLException {
        List<Offres> offers = Offres.GetAllOffres();

        if (offers.isEmpty()) {
            System.out.println("No offers available.");
        }
        else {
            System.out.println("List of all offers:");
            System.out.println("-------------------------------------------------");

            for (Offres offer : offers) {
                System.out.println("Offer ID: " + offer.getId());
                System.out.println("Name: " + offer.getNom_offre());
                System.out.println("Description: " + offer.getDescription());
                System.out.println("Start Date: " + offer.getDate_debut());
                System.out.println("End Date: " + offer.getDate_fin());
                System.out.println("Reduction Value: " + offer.getValeur_reduction());
                System.out.println("Conditions: " + offer.getConditions());
                System.out.println("Reduction Type: " + offer.getType_reduction());
                System.out.println("Status: " + offer.getStatut_offre());
                System.out.println("Contract ID: " + offer.getContrat().getId());
                System.out.println("-------------------------------------------------");
            }
        }


    }


    public static void DeleteAnOffer() throws SQLException , ClassNotFoundException , IllegalArgumentException {

        System.out.println("Please enter an Offer ID : ");





        boolean choice = false;

        while(!choice) {

            String value = scanner.nextLine();
            UUID Offer_id = UUID.fromString(value);
            Offres offre = Offres.FindOneOffre(Offer_id);



            if(offre != null) {
                System.out.println("Ow Here you go we found the Offer you looking for ");
                System.out.println();
                System.out.println("Are you sure you want to delete this offer ? (yes / no )");
                String choix = scanner.nextLine().toLowerCase().trim();

                if(choix.equals("yes")) {
                    System.out.println(Offres.DeleteOffre(Offer_id));
                }
                else {
                    return;
                }




                choice = true;
            }else {
                System.out.println("We couldnt find this offer , please try again ");

            }
        }


    }


=======
>>>>>>> 1ae488bef07cc010c08a824e13fc8a5310cc61ed
}
