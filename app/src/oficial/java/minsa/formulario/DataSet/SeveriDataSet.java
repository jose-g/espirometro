package minsa.formulario.DataSet;

public class SeveriDataSet {

    private int id;
    private String descripcion;
    private String detalle;

    public SeveriDataSet() {
    }

    public SeveriDataSet(int id, String descripcion, String detalle) {
        this.id = id;
        this.descripcion = descripcion;
        this.detalle = detalle;
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

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

}
