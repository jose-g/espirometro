package minsa.formulario.DataSet;

public class SintomDataSet {

    private int id;
    private String descripcion;

    public SintomDataSet() {
    }

    public SintomDataSet(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}