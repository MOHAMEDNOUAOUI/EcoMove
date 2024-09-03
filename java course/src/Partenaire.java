import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Partenaire {

    private UUID id;
    private String nom_compagnie;
    private String contact_commercial;
    private TypeTransport type_transport;
    private String zone_geographique;
    private String conditions_speciales;
    private StatutPartenaire statut_partenaire;
    private Date date_creation;
    private List<Contrats> contrats;

    public enum TypeTransport { AVION, TRAIN, BUS }
    public enum StatutPartenaire { ACTIF, INACTIF, SUSPENDU }

    public Partenaire(String nom_compagnie, String contact_commercial, TypeTransport type_transport,
                      String zone_geographique, String conditions_speciales,
                      StatutPartenaire statut_partenaire, LocalDate date_creation) {

        this.id = UUID.randomUUID();
        this.nom_compagnie = nom_compagnie;
        this.contact_commercial = contact_commercial;
        this.type_transport = type_transport;
        this.zone_geographique = zone_geographique;
        this.conditions_speciales = conditions_speciales;
        this.statut_partenaire = statut_partenaire;
        this.date_creation = Date.valueOf(date_creation);
        this.contrats = new ArrayList<>();

        addToDatabase();
    }

    public Partenaire(UUID id ,String nom_compagnie, String contact_commercial, TypeTransport type_transport,
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
        this.contrats = new ArrayList<>();
    }



    public void addToDatabase() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Database.getConnection();

            String sql = "INSERT INTO partenaire (id , nom_compagnie, contact_commercial, type_transport, zone_geographique, conditions_speciales, statut_partenaire, date_creation) " +
                    "VALUES (? , ? , ?, ?::type_transport , ? , ? , ?::statut_partenaire , ? )";

            pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, id);
            pstmt.setString(2, nom_compagnie);
            pstmt.setString(3, contact_commercial);
            pstmt.setString(4, type_transport.name());
            pstmt.setString(5, zone_geographique);
            pstmt.setString(6, conditions_speciales);
            pstmt.setString(7, statut_partenaire.name());
            pstmt.setDate(8, date_creation);
            pstmt.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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


    public List<Contrats> GetContrats() {
        return contrats;
    }

    public void SetContrats (Contrats contrats) {
        this.contrats.add(contrats);
    }



    public static Partenaire fromResultSet(ResultSet rs) throws SQLException , ClassNotFoundException {
        return new Partenaire(
                rs.getObject("id" , UUID.class),
                rs.getString("nom_compagnie"),
                rs.getString("contact_commercial"),
                TypeTransport.valueOf(rs.getString("type_transport")),
                rs.getString("zone_geographique"),
                rs.getString("conditions_speciales"),
                StatutPartenaire.valueOf(rs.getString("statut_partenaire")),
                rs.getDate("date_creation").toLocalDate()
        );
    }


    public static Partenaire findPartenaireById(UUID idvalue) throws SQLException, ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Partenaire partenaire = null;

        try {
            conn = Database.getConnection();
            String sql = "SELECT partenaire.id AS partenaire_id, contrats.id AS contrat_id, * " +
                    "FROM partenaire " +
                    "LEFT JOIN contrats ON contrats.partenaireid = partenaire.id " +
                    "WHERE partenaire.id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, idvalue);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                partenaire = Partenaire.fromResultSet(rs);

                do {
                    UUID contratID = rs.getObject("contrat_id" , UUID.class);
                    if(contratID != null){
                        LocalDate date_debut = rs.getDate("date_debut").toLocalDate();
                        LocalDate date_fin = rs.getDate("date_fin").toLocalDate();
                        float tarif_special = rs.getFloat("tarif_special");
                        String conditions_accord = rs.getString("conditions_accord");
                        boolean renouvelable = rs.getBoolean("renouvelable");
                        Contrats.StatutContrat statutContrat = Contrats.StatutContrat.valueOf(rs.getString("statut_contrat"));

                        Contrats contrat = new Contrats(
                                contratID,
                                date_debut,
                                date_fin,
                                tarif_special,
                                conditions_accord,
                                renouvelable,
                                statutContrat,
                                partenaire
                        );

                        partenaire.SetContrats(contrat);
                    }
                }while(rs.next());
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




    public static String DeletePartenaire(UUID id) throws SQLException {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;


        conn = Database.getConnection();
        String sql = "Update partenaire SET statut_partenaire = 'SUSPENDU' WHERE id = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setObject(1 , id);

        pstmt.executeUpdate();


        return "This partenaire is deleted succefully";
    }




    public static void ModifyPartner(UUID id  , String column , String value) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;


        conn = Database.getConnection();


        String sql = "UPDATE partenaire SET " + column + " = ? WHERE id = ?";
        pstmt = conn.prepareStatement(sql);
        if(column.equals("date_creation")){
            pstmt.setDate(1 , Date.valueOf(value));
        }else if (column.equals("type_transport") || column.equals("statut_partenaire")) {
            pstmt.setObject(1, value, java.sql.Types.OTHER);
        }
        else{
            pstmt.setString(1 , value);
        }

        pstmt.setObject(2 , id);


        pstmt.executeUpdate();

        System.out.println("New Partner "+column+" has been succefully updated to "+value);
    }


}
