package minsa.formulario.DataSet;

public class UsuariDataSet {

    private static UsuariDataSet instance;

    private String UsuariIdenti;
    private String UsuariNroDoc;
    private String UsuariNomApe;
    private String UsuarioNom;
    private String UsuarioPaterno;
    private String UsuarioMaterno;
    private String UsuarioIdTipoDoc;

    private String es_conglomerado = "";
    private String id_conglomerado = "";

    private UsuariDataSet(){}

    public String getUsuariIdenti() {
        return UsuariIdenti;
    }
    public void setUsuariIdenti(String UsuariIdenti) {
        this.UsuariIdenti = UsuariIdenti;
    }

    public String getUsuariNroDoc() {
        return UsuariNroDoc;
    }
    public void setUsuariNroDoc(String UsuariNroDoc) {
        this.UsuariNroDoc = UsuariNroDoc;
    }

    public String getUsuariNomApe() {
        return UsuariNomApe;
    }
    public void setUsuariNomApe(String UsuariNomApe) {
        this.UsuariNomApe = UsuariNomApe;
    }

    public String getUsuarioNom() {
        return UsuarioNom;
    }

    public void setUsuarioNom(String usuarioNom) {
        UsuarioNom = usuarioNom;
    }

    public String getUsuarioPaterno() {
        return UsuarioPaterno;
    }

    public void setUsuarioPaterno(String usuarioPaterno) {
        UsuarioPaterno = usuarioPaterno;
    }

    public String getUsuarioMaterno() {
        return UsuarioMaterno;
    }

    public void setUsuarioMaterno(String usuarioMaterno) {
        UsuarioMaterno = usuarioMaterno;
    }

    public String getUsuarioIdTipoDoc() {
        return UsuarioIdTipoDoc;
    }

    public void setUsuarioIdTipoDoc(String usuarioIdTipoDoc) {
        UsuarioIdTipoDoc = usuarioIdTipoDoc;
    }

    public String getEs_conglomerado() {
        return es_conglomerado;
    }

    public void setEs_conglomerado(String es_conglomerado) {
        this.es_conglomerado = es_conglomerado;
    }

    public String getId_conglomerado() {
        return id_conglomerado;
    }

    public void setId_conglomerado(String id_conglomerado) {
        this.id_conglomerado = id_conglomerado;
    }

    public static synchronized UsuariDataSet getInstance(){
        if(instance==null){
            instance=new UsuariDataSet();
        }
        return instance;
    }

}
