package minsa.formulario.DataSet;

public class CondicionEgresoDataSet {

    private int id;
    private String descripcion;

    public CondicionEgresoDataSet() {
    }

    public CondicionEgresoDataSet(int id, String descripcion) {
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
