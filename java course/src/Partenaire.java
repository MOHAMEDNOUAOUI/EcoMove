import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Partenaire {

    private int id;
    private String nom_compagnie;
    private String contact_commercial;
    private TypeTransport type_transport;
    private String zone_geographique;
    private String conditions_speciales;
    private StatutPartenaire statut_partenaire;
    private Date date_creation;

    public enum TypeTransport { AVION, TRAIN, BUS }
    public enum StatutPartenaire { ACTIF, INACTIF, SUSPENDU }

    public Partenaire(String nom_compagnie, String contact_commercial, TypeTransport type_transport,
                      String zone_geographique, String conditions_speciales,
                      StatutPartenaire statut_partenaire, LocalDate date_creation) {


        this.nom_compagnie = nom_compagnie;
        this.contact_commercial = contact_commercial;
        this.type_transport = type_transport;
        this.zone_geographique = zone_geographique;
        this.conditions_speciales = conditions_speciales;
        this.statut_partenaire = statut_partenaire;
        this.date_creation = Date.valueOf(date_creation);

        addToDatabase();
    }

    public Partenaire(int id, String nom_compagnie, String contact_commercial, TypeTransport type_transport,
                      String zone_geographique, String conditions_speciales,
                      StatutPartenaire statut_partenaire, LocalDate date_creation) {
        this.id = id;
        this.nom_compagnie = nom_compagnie;
        this.contact_commercial = contact_commercial;
        this.type_transport = type_transport;
        this.zone_geographique = zone_geographique;
        this.conditions_speciales = conditions_speciales;
        this.statut_partenaire = statut_partenaire;
        this.date_creation = Date.valueOf(date_creation);
    }

    public void addToDatabase() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Database.getConnection();

            String sql = "INSERT INTO partenaire (nom_compagnie, contact_commercial, type_transport, zone_geographique, conditions_speciales, statut_partenaire, date_creation) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nom_compagnie);
            pstmt.setString(2, contact_commercial);
            pstmt.setString(3, type_transport.name());  // Assuming `type_transport` is a valid enum
            pstmt.setString(4, zone_geographique);
            pstmt.setString(5, conditions_speciales);
            pstmt.setString(6, statut_partenaire.name()); // Assuming `statut_partenaire` is a valid enum
            pstmt.setDate(7, date_creation);
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

    public Date getDateCreation() {
        return date_creation;
    }

    public void setDateCreation(String date_creation) {
        this.date_creation = Date.valueOf(date_creation);
    }




    public static Partenaire fromResultSet(ResultSet rs) throws SQLException {
        return new Partenaire(
                rs.getInt("id"),
                rs.getString("nom_compagnie"),
                rs.getString("contact_commercial"),
                TypeTransport.valueOf(rs.getString("type_transport")),
                rs.getString("zone_geographique"),
                rs.getString("conditions_speciales"),
                StatutPartenaire.valueOf(rs.getString("statut_partenaire")),
                rs.getDate("date_creation").toLocalDate()
        );
    }


    public static Partenaire findPartenaireById(int id) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Partenaire partenaire = null;

        try {
            conn = Database.getConnection();
            String sql = "SELECT * FROM partenaire WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                // Instantiate the Partenaire object from ResultSet
                partenaire = new Partenaire(
                        rs.getInt("id"),
                        rs.getString("nom_compagnie"),
                        rs.getString("contact_commercial"),
                        TypeTransport.valueOf(rs.getString("type_transport")),
                        rs.getString("zone_geographique"),
                        rs.getString("conditions_speciales"),
                        StatutPartenaire.valueOf(rs.getString("statut_partenaire")),
                        rs.getDate("date_creation").toLocalDate()
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return partenaire;
    }



    public static List<Partenaire> getAllPartenaires() throws ClassNotFoundException , SQLException {
                List<Partenaire> partenaires = new ArrayList<Partenaire>();
        Connection conn = null;
            Statement stmt= null;
            ResultSet rs = null;


            try{
                conn = Database.getConnection();
                String sql = "SELECT * FROM partenaire";
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    Partenaire partenaire = Partenaire.fromResultSet(rs);
                    partenaires.add(partenaire);
                }



            }catch(SQLException e){
                e.printStackTrace();
            }

        return partenaires;

    }




    public static String DeletePartenaire(int id) throws SQLException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "DELETE FROM partenaire WHERE id = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1 , id);

        conn = pstmt.getConnection();
        pstmt.executeUpdate();


        return "This partenaire is deleted succefully";
    }




    public static void ModifyPartner(int id  , String column , String value) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;


        conn = Database.getConnection();


        String sql = "UPDATE partenaire SET " + column + " = ? WHERE id = ?";
        pstmt = conn.prepareStatement(sql);
        if(column.equals("date_creation")){
            pstmt.setDate(1 , Date.valueOf(value));
        }else{
            pstmt.setString(1 , value);
        }

        pstmt.setInt(2 , id);


        pstmt.executeUpdate();

        System.out.println("New Partner "+column+" has been succefully updated to "+value);
    }


}
