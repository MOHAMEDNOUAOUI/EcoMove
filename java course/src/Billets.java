public class Billets {
    private int id;
    private String prix_achat;
    private String prix_vente;
    private String date_vente;
    private statut_billet statut_billet;

    private Partenaire.TypeTransport type_transport;

    public enum statut_billet {VENDU,ANNULE,ENATTENTE};




    Billets (String prix_achat , String prix_vente , String date_vente , statut_billet statut_billet , Partenaire.TypeTransport type_transport) {

    }

}
