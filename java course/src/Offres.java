import org.w3c.dom.Text;

import javax.xml.crypto.Data;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

public class Offres {

    private UUID id;
    private String nom_offre;
    private String description;
    private LocalDate date_debut;
    private LocalDate date_fin;
    private int valeur_reduction;
    private String conditions;

    private StatutOffre statut_offre;
    private TypeReduction type_reduction;

    private Contrats contrat;

    enum TypeReduction {POURECENTAGE , MONTANTFIX }
    enum StatutOffre {ACTIVE , EXPIREE , SUSPENDU};


    private Offres (UUID id , String nom_offre , String description , LocalDateTime date_debut , LocalDateTime date_fin ,  int valeur_reduction  , StatutOffre statut_offre , String conditions , TypeReduction type_reduction , Contrats contrats) throws SQLException, ClassNotFoundException {
        this.id = UUID.randomUUID();
        this.nom_offre = nom_offre;
        this.description = description;
        this.date_debut = LocalDate.from(date_debut);
        this.date_fin = LocalDate.from(date_fin);
        this.valeur_reduction = valeur_reduction;
        this.conditions = conditions;
        this.type_reduction = type_reduction;
        this.statut_offre = statut_offre;
    }


    private Offres (String nom_offre , String description , LocalDateTime date_debut , LocalDateTime date_fin ,  int valeur_reduction  , StatutOffre statut_offre ,  String conditions ,  TypeReduction type_reduction , Contrats contrats) throws SQLException, ClassNotFoundException {
                    this.id = UUID.randomUUID();
                    this.nom_offre = nom_offre;
                    this.description = description;
                    this.date_debut = LocalDate.from(date_debut);
                    this.date_fin = LocalDate.from(date_fin);
                    this.valeur_reduction = valeur_reduction;
                    this.conditions = conditions;
                    this.type_reduction = type_reduction;
                    this.statut_offre = statut_offre;


                    addToDatabase();
    }


    public void addToDatabase() throws SQLException , ClassNotFoundException {
        Connection conn = null;
        PreparedStatement pstmt = null;


        try {
            conn = Database.getConnection();
            String sql = "INSERT INTO OFFERS (id , nom_offre , description , date_debut , date_fin , valeur_reduction , conditions , statut_offre , type_reduction , contratid ) VALUES (?,?,?,?,?,?,?,?::StatutOffre,?::TypeReduction,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1 , id);
            pstmt.setString(2 , nom_offre);
            pstmt.setString(3 , description);
            pstmt.setDate(4, Date.valueOf(date_debut));
            pstmt.setDate(5 , Date.valueOf(date_fin));
            pstmt.setInt(6, valeur_reduction);
            pstmt.setString(7 , conditions);
            pstmt.setString(8 , statut_offre.name());
            pstmt.setString(9 , type_reduction.name());
            pstmt.setObject(10 , contrat.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }



    public void setId (UUID id) {
        this.id = id;
    }

    public void setNom_offre(String nom_offre) {
        this.nom_offre = nom_offre;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public void setDate_debut(LocalDate date_debut) {
        this.date_debut = date_debut;
    }

    public void setDate_fin (LocalDate date_fin) {
        this.date_fin = date_fin;
    }

    public void setValeur_reduction(int valeur_reduction) {
        this.valeur_reduction = valeur_reduction;
    }

    public void setConditions (String conditions) {
        this.conditions = conditions;
    }

    public void setStatut_offre (StatutOffre statut_offre) {
        this.statut_offre = statut_offre;
    }

    public void setType_reduction (TypeReduction type_reduction) {
        this.type_reduction = type_reduction;
    }

    public void setContrat( Contrats contrat ) {
        this.contrat = contrat;
    }


    public UUID getId() {
        return this.id;
    }

    public String getNom_offre() {
        return this.nom_offre;
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDate getDate_debut() {
        return this.date_debut;
    }

    public LocalDate getDate_fin() {
        return this.date_fin;
    }

    public int getValeur_reduction() {
        return this.valeur_reduction;
    }

    public String getConditions() {
        return this.conditions;
    }

    public StatutOffre getStatut_offre() {
        return this.statut_offre;
    }

    public TypeReduction getType_reduction() {
        return this.type_reduction;
    }

    public Contrats getContrat() {
        return this.contrat;
    }





    public static Offres fromResultSet (ResultSet rs) throws  SQLException , ClassNotFoundException {
        UUID contratID = rs.getObject("contratid" , UUID.class);
        Contrats contrats = Contrats.FindOneContrat(contratID);
        return new Offres(
                rs.getObject("id", UUID.class),
                rs.getString("nom_offre"),
                rs.getString("description"),
                rs.getTimestamp("date_debut").toLocalDateTime(),
                rs.getTimestamp("date_fin").toLocalDateTime(),
                rs.getInt("valeur_reduction"),
                StatutOffre.valueOf(rs.getString("statut_offre")),
                rs.getString("conditions"),
                TypeReduction.valueOf(rs.getString("type_reduction")),
                contrats
        );
    }


//    public static Offres FindOneOffre(UUID id) throws SQLException , ClassNotFoundException {
//
//        Connection conn = null;
//        PreparedStatement pstmt = null;
//        ResultSet rs = null;
//
//
//        try {
//            conn = Database.getConnection();
//            String sql = "Select contrats.id as contrat_id , offers.id as offer_ic ,  * from Offers JOIN contrats on contrats.id = offers.contratid where offers.id = ? ";
//
//        }
//    }






}
