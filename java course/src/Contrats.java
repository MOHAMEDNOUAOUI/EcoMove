import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Contrats {


    private int id;
    private LocalDate date_debut;
    private LocalDate date_fin;
    private float tarif_special;
    private String conditions_accord;
    private boolean renouvelable;
    private statut_contrat statut_contrat;
    private int partenaireId;


    public enum statut_contrat {encours,termine, suspendu};


    public Contrats (int id , LocalDate date_debut,LocalDate date_fin , float tarif_special , String conditions_accord,boolean renouvelable , statut_contrat statut_contrat , int partenaireId) {

        this.id = id;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.tarif_special = tarif_special;
        this.conditions_accord = conditions_accord;
        this.renouvelable = renouvelable;
        this.statut_contrat = statut_contrat;
        this.partenaireId = partenaireId;
    }

    public Contrats (LocalDate date_debut , LocalDate date_fin , float tarif_special , String conditions_accord, boolean renouvelable , statut_contrat statut_contrat , int partenaireId) {

        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.tarif_special = tarif_special;
        this.conditions_accord = conditions_accord;
        this.renouvelable = renouvelable;
        this.statut_contrat = statut_contrat;
        this.partenaireId = partenaireId;

        addToDatabase();
    }




    public void addToDatabase() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Database.getConnection();

            String sql = "INSERT INTO contrats (date_debut , date_fin , tarif_special , conditions_accord , renouvelable , statut_contrat , partenaireId) " +
                    "VALUES (?, ?, ?, ?, ?, ?::statut_contrat, ?)";

            pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, java.sql.Date.valueOf(String.valueOf(date_debut)));
            pstmt.setDate(2, java.sql.Date.valueOf(String.valueOf(date_fin)));
            pstmt.setFloat(3, tarif_special);  // Assuming `type_transport` is a valid enum
            pstmt.setString(4, conditions_accord);
            pstmt.setBoolean(5, renouvelable);
            pstmt.setString(6, statut_contrat.name()); // Assuming `statut_partenaire` is a valid enum
            pstmt.setInt(7, partenaireId);
            pstmt.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }














    public int getId() {
        return id;
    }
    public LocalDate getDate_debut() {
        return date_debut;
    }
    public LocalDate getDate_fin() {
        return date_fin;
    }
    public float getTarif_special() {
        return tarif_special;
    }
    public String getConditions_accord() {
        return conditions_accord;
    }
    public boolean isRenouvelable() {
        return renouvelable;
    }
    public statut_contrat getStatut_contrat() {
        return statut_contrat;
    }
    public int getPartenaireId() {
        return partenaireId;
    }




    public void setId(int id) {
        this.id = id;
    }
    public void setDate_debut(String date_debut) {
        this.date_debut = LocalDate.parse(date_debut);
    }
    public void setDate_fin(String date_fin) {
        this.date_fin = LocalDate.parse(date_fin);
    }
    public void setTarif_special(float tarif_special) {
        this.tarif_special = tarif_special;
    }
    public void setConditions_accord(String conditions_accord) {
        this.conditions_accord = conditions_accord;
    }
    public void setRenouvelable(boolean renouvelable) {
        this.renouvelable = renouvelable;
    }
    public void setStatut_contrat(statut_contrat statut_contrat) {
        this.statut_contrat = statut_contrat;
    }
    public void setPartenaireId(int partenaireId) {
        this.partenaireId = partenaireId;
    }









}
