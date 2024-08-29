import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
        this.date_creation = date_creation;

        addToDatabase();
    }

    public void addToDatabase() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Database.getConnection();

            String sql = "INSERT INTO partenaires (nom_compagnie, contact_commercial, type_transport, zone_geographique, conditions_speciales, statut_partenaire, date_creation) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, nom_compagnie);
            pstmt.setString(2, contact_commercial);
            pstmt.setString(3, type_transport.toString());
            pstmt.setString(4, zone_geographique);
            pstmt.setString(5, conditions_speciales);
            pstmt.setString(6, date_creation);
            pstmt.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomCompagnie() {
        return this.nom_compagnie;
    }

    public void setNomCompagnie(String nom_compagnie) {
        this.nom_compagnie = nom_compagnie;
    }

    public String getContactCommercial() {
        return this.contact_commercial;
    }

    public void setContactCommercial(String contact_commercial) {
        this.contact_commercial = contact_commercial;
    }

    public TypeTransport getTypeTransport() {
        return this.type_transport;
    }

    public void setTypeTransport(TypeTransport type_transport) {
        this.type_transport = type_transport;
    }

    public String getZoneGeographique() {
        return this.zone_geographique;
    }

    public void setZoneGeographique(String zone_geographique) {
        this.zone_geographique = zone_geographique;
    }

    public String getConditionsSpeciales() {
        return this.conditions_speciales;
    }

    public void setConditionsSpeciales(String conditions_speciales) {
        this.conditions_speciales = conditions_speciales;
    }

    public StatutPartenaire getStatutPartenaire() {
        return this.statut_partenaire;
    }

    public void setStatutPartenaire(StatutPartenaire statut_partenaire) {
        this.statut_partenaire = statut_partenaire;
    }

    public String getDateCreation() {
        return this.date_creation;
    }

    public void setDateCreation(String date_creation) {
        this.date_creation = date_creation;
    }






}
