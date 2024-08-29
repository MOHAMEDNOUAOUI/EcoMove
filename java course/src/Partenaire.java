public class Partenaire {


    private  int id;
    private String nom_compagnie;
    private String contact_commercial;


    public enum TypeTransport { AVION, TRAIN, BUS }
    private TypeTransport type_transport;


    private String zone_geographique;
    private String conditions_speciales;


    public enum StatutPartenaire { ACTIF, INACTIF, SUSPENDU }
    private StatutPartenaire statut_partenaire;


    private String date_creation;



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
    }



    public int getId() {
        return this.id;
    }
    public String getNomCompagnie() {
        return this.nom_compagnie;
    }

    public String getContactCommercial() {
        return this.contact_commercial;
    }

    public TypeTransport getTypeTransport() {
        return this.type_transport;
    }


    public String getZoneGeo() {
        return this.zone_geographique;
    }

    public String getConditionsSpeciales() {
        return this.conditions_speciales;
    }


    public StatutPartenaire getStatutPartenaire() {
        return this.statut_partenaire;
    }

    public String getDateCreation() {
        return this.date_creation;
    }


    public void setId(int id) {
    this.id = id;
    }

    public void setNomCompagnie(String name) {
        this.nom_compagnie = name;
    }

    public void setContactCommercial(String Contact) {
        this.contact_commercial = Contact;
    }

    public void setTypeTransport (TypeTransport type_transport) {
        this.type_transport = type_transport;
    }

    public void setZoneGeographique( String Zone ) {
        this.zone_geographique = Zone;
    }

    public void setConditionsSpeciales(String Condition) {
        this.contact_commercial = Condition;
    }

    public void setStatutPartenaire(StatutPartenaire statut_partenaire) {
        this.statut_partenaire = statut_partenaire;
    }

    public void setDateCreation(String date_creation){
        this.date_creation = date_creation;
    }



}
