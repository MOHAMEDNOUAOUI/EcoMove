public class Contrats {


    private int id;
    private String date_debut;
    private String date_fin;
    private String tarif_special;
    private String conditions_accord;
    private boolean renouvelable;
    private statut_contrat statut_contrat;


    public enum statut_contrat {encours,termine, suspendu};


    Contrats (String date_debut,String date_fin , String tarif_special , String conditions_accord,boolean renouvelable) {

        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.tarif_special = tarif_special;
        this.conditions_accord = conditions_accord;
        this.renouvelable = renouvelable;
    }
}
