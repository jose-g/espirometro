package minsa.formulario.DataSet;

public class CodigoPaisDataSet {

    private String cod_telefono;
    private String sufijo;

    public CodigoPaisDataSet(String cod_telefono, String sufijo) {
        this.cod_telefono = cod_telefono;
        this.sufijo = sufijo;
    }

    public String getCod_telefono() {
        return cod_telefono;
    }

    public void setCod_telefono(String cod_telefono) {
        this.cod_telefono = cod_telefono;
    }

    public String getSufijo() {
        return sufijo;
    }

    public void setSufijo(String sufijo) {
        this.sufijo = sufijo;
    }

}