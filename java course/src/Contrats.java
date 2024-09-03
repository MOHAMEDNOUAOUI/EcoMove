import javax.print.attribute.standard.DateTimeAtCompleted;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Contrats {


    private UUID id;
    private LocalDate date_debut;
    private LocalDate date_fin;
    private float tarif_special;
    private String conditions_accord;
    private boolean renouvelable;
    private StatutContrat statut_contrat;
    private Partenaire partenaire;

    private List<Offres> offres;


    public enum StatutContrat {encours,termine, suspendu};


    public Contrats ( LocalDate date_debut, LocalDate date_fin , float tarif_special , String conditions_accord, boolean renouvelable , StatutContrat statut_contrat , Partenaire partenaire) {

        this.id = UUID.randomUUID();
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.tarif_special = tarif_special;
        this.conditions_accord = conditions_accord;
        this.renouvelable = renouvelable;
        this.statut_contrat = statut_contrat;
        this.partenaire = partenaire;

        addToDatabase();
    }

    public Contrats (UUID id , LocalDate date_debut, LocalDate date_fin , float tarif_special , String conditions_accord, boolean renouvelable , StatutContrat statut_contrat , Partenaire partenaire) {

        this.id = id;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.tarif_special = tarif_special;
        this.conditions_accord = conditions_accord;
        this.renouvelable = renouvelable;
        this.statut_contrat = statut_contrat;
        this.partenaire = partenaire;

    }






    public void addToDatabase() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = Database.getConnection();

            String sql = "INSERT INTO contrats (id , date_debut , date_fin , tarif_special , conditions_accord , renouvelable , statut_contrat , partenaireid) " +
                    "VALUES (? ,?, ?, ?, ?, ?, ?::StatutContrat, ?)";

            pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1, id);
            pstmt.setDate(2, java.sql.Date.valueOf(String.valueOf(date_debut)));
            pstmt.setDate(3, java.sql.Date.valueOf(String.valueOf(date_fin)));
            pstmt.setFloat(4, tarif_special);  // Assuming `type_transport` is a valid enum
            pstmt.setString(5, conditions_accord);
            pstmt.setBoolean(6, renouvelable);
            pstmt.setString(7, statut_contrat.name()); // Assuming `statut_partenaire` is a valid enum
            pstmt.setObject(8, partenaire.getId());
            pstmt.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }














    public UUID getId() {
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
    public StatutContrat getStatut_contrat() {
        return statut_contrat;
    }
    public Partenaire GetPartenaire() {
        return partenaire;
    }




    public void setId(UUID id) {
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
    public void setStatut_contrat(StatutContrat statut_contrat) {
        this.statut_contrat = statut_contrat;
    }
    public void setPartenaire(Partenaire partenaire) {
        this.partenaire = partenaire;
    }


    public static Contrats fromResultSet(ResultSet rs) throws SQLException, ClassNotFoundException {


        UUID partenaireId = rs.getObject("partenaireid", UUID.class);
        Partenaire partenaire = Partenaire.findPartenaireById(partenaireId);


        return new Contrats(
                rs.getObject("id", UUID.class),
                rs.getDate("date_debut").toLocalDate(),
                rs.getDate("date_fin").toLocalDate(),
                rs.getFloat("tarif_special"),
                rs.getString("conditions_accord"),
                rs.getBoolean("renouvelable"),
                StatutContrat.valueOf(rs.getString("statut_contrat")),
                partenaire
        );
    }




    public static List<Contrats> GetAllContrats() throws ClassNotFoundException , SQLException {
                Connection conn = null;
                PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Contrats> contratsList = new ArrayList<>();

        try {
            conn = Database.getConnection();
            String sql = "SELECT * FROM contrats";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Contrats contrat = Contrats.fromResultSet(rs);
                contratsList.add(contrat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }





        return contratsList;
    }


    public static Contrats FindOneContrat(UUID id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Contrats contrat = null;

        try{
            conn = Database.getConnection();
            String sql = "SELECT contrats.id as contrat_id , partenaire.id as partenaire_id , * from contrats LEFT JOIN partenaire on partenaire.id = contrats.partenaireid WHERE contrats.id = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setObject(1 , id);
            rs = stmt.executeQuery();

            if(rs.next()){

                Partenaire partenaire = Partenaire.fromResultSet(rs);


                contrat = Contrats.fromResultSet(rs);

                contrat.setPartenaire(partenaire);
            }





        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }


         return contrat;
    }



    public static String DeleteContrat(UUID idvalue) throws SQLException {

        Connection conn = null;
        PreparedStatement pstmt = null;


            conn = Database.getConnection();

            String sql = "UPDATE contrats SET statut_contrat = 'termine' WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setObject(1,idvalue);
            pstmt.executeUpdate();


            return "Contrat Deleted succefully";



    }




    public static void ModifierContrat(UUID contractid , String column , String value) throws SQLException , ClassNotFoundException {
            Connection conn = null;
            PreparedStatement pstmt = null ;
            conn = Database.getConnection();
            try {
                String sql = "UPDATE contrats SET " + column + " = ? WHERE id = ?";
                pstmt = conn.prepareStatement(sql);

                    if(column.equals("date_debut") || column.equals("date_fin")){
                        pstmt.setDate(1 , Date.valueOf(value));
                    }
                    else if(column.equals("id") || column.equals("partenaireid")) {
                        pstmt.setObject(1 , UUID.fromString(value));
                    }
                    else if (column.equals("renouvelable")){
                        pstmt.setBoolean(1 , Boolean.parseBoolean(value));
                    }
                    else if(column.equals("tarif_special")) {
                        pstmt.setFloat(1 , Float.parseFloat(value));
                    }
                    else if(column.equals("statut_contrat")) {
                        pstmt.setObject(1 , value , java.sql.Types.OTHER);
                    }
                    else {
                        pstmt.setString(1 , value);
                    }

                    pstmt.setObject(2 , contractid);



                    pstmt.executeUpdate();


                System.out.println("Contract "+column+" has been succefully updated to "+value);

            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
    }


}
