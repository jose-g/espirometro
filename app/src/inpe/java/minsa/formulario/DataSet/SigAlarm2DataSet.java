package minsa.formulario.DataSet;

public class SigAlarm2DataSet {

    private int id;
    private String descripcion;

    public SigAlarm2DataSet() {
    }

    public SigAlarm2DataSet(int id, String descripcion) {
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
