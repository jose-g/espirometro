package minsa.formulario.DataSet;

public class PaisDataSet {

    private int id_pais;
    private String pais;

    public PaisDataSet() {
    }

    public PaisDataSet(int id_pais, String pais) {
        this.id_pais = id_pais;
        this.pais = pais;
    }

    public int getId_pais() {
        return id_pais;
    }

    public void setId_pais(int id_pais) {
        this.id_pais = id_pais;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

}