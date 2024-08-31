import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Partenaire {

    private int id;
    private String nom_compagnie;
    private String contact_commercial;
    private TypeTransport type_transport;
    private String zone_geographique;
    private String conditions_speciales;
    private StatutPartenaire statut_partenaire;
    private String date_creation;

    public enum TypeTransport { AVION, TRAIN, BUS }
    public enum StatutPartenaire { ACTIF, INACTIF, SUSPENDU }

    public Partenaire(String nom_compagnie, String contact_commercial, TypeTransport type_transport,
                      String zone_geographique, String conditions_speciales,
                      StatutPartenaire statut_partenaire, String date_creation) {




        this.nom_compagnie = nom_compagnie;
        this.contact_commercial = contact_commercial;
        this.type_transport = type_transport;
        this.zone_geographique = zone_geographique;
        this.conditions_speciales = conditions_speciales;
        this.statut_partenaire = statut_partenaire;

        addToDatabase();
    }

    public void addToDatabase() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Database.getConnection();

            String sql = "INSERT INTO partenaire (nom_compagnie, contact_commercial, type_transport, zone_geographique, conditions_speciales, statut_partenaire , date_creation) " +
                    "VALUES (?, ?, ?::type_transport, ?, ?, ?::statut_partenaire , date_creation)";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nom_compagnie);
            pstmt.setString(2, contact_commercial);
            pstmt.setString(3, type_transport.name());
            pstmt.setString(4, zone_geographique);
            pstmt.setString(5, conditions_speciales);
            pstmt.setString(6,statut_partenaire.name());
            pstmt.setDate(7, Date.valueOf(date_creation));
            pstmt.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomCompagnie() {
        return nom_compagnie;
    }

    public void setNomCompagnie(String nom_compagnie) {
        this.nom_compagnie = nom_compagnie;
    }

    public String getContactCommercial() {
        return contact_commercial;
    }

    public void setContactCommercial(String contact_commercial) {
        this.contact_commercial = contact_commercial;
    }

    public TypeTransport getTypeTransport() {
        return type_transport;
    }

    public void setTypeTransport(TypeTransport type_transport) {
        this.type_transport = type_transport;
    }

    public String getZoneGeographique() {
        return zone_geographique;
    }

    public void setZoneGeographique(String zone_geographique) {
        this.zone_geographique = zone_geographique;
    }

    public String getConditionsSpeciales() {
        return conditions_speciales;
    }

    public void setConditionsSpeciales(String conditions_speciales) {
        this.conditions_speciales = conditions_speciales;
    }

    public StatutPartenaire getStatutPartenaire() {
        return statut_partenaire;
    }

    public void setStatutPartenaire(StatutPartenaire statut_partenaire) {
        this.statut_partenaire = statut_partenaire;
    }

    public String getDateCreation() {
        return date_creation;
    }

    public void setDateCreation(String date_creation) {
        this.date_creation = date_creation;
    }



//    public static ArrayList<Partenaire> getAllPartenair() throws ClassNotFoundException , SQLException {
//            Connection conn = null;
//            PreparedStatement pstmt = null;
//            try{
//
//                conn = Database.getConnection();
//
//                Statement stmt = conn.createStatement();
//                ResultSet rs = stmt.executeQuery("select * from partenaire");
//
//                ArrayList<Partenaire> PartenaireList = new ArrayList<>();
//                while(rs.next()) {
//                    Partenaire partenaire = new Partenaire(rs.getString("id") , rs.getString("nom_compagnie"),rs.getString("contact_commercial"), rs.getString("type_transport") , rs.getString("zone_geographique") , rs.getArray("conditions_speciales") , rs.getString("statut_partenaire"));
//                }
//
//                return rs;
//
//            }catch(SQLException e){
//                e.printStackTrace();
//            }
//
//
//
//    }




}
