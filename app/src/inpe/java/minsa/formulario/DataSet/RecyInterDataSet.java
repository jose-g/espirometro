package minsa.formulario.DataSet;

public class RecyInterDataSet {

    private String id;
    private String apellidos;
    private String nombres;
    private String tipo_doc;
    private String doc;
    private byte foto;

    private String estado;
    private String fecha;

    public RecyInterDataSet() {
    }

    public RecyInterDataSet(String id, String apellidos, String nombres, String tipo_doc, String doc, byte foto) {
        this.id = id;
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.tipo_doc = tipo_doc;
        this.doc = doc;
        this.foto = foto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getTipo_doc() {
        return tipo_doc;
    }

    public void setTipo_doc(String tipo_doc) {
        this.tipo_doc = tipo_doc;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public byte getFoto() {
        return foto;
    }

    public void setFoto(byte foto) {
        this.foto = foto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }


}