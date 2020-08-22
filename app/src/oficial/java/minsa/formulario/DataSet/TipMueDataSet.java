package minsa.formulario.DataSet;

public class TipMueDataSet {

    private int id_tip_mue;
    private String tip_mue;

    public TipMueDataSet() {
    }

    public TipMueDataSet(int id_tip_mue, String tip_mue) {
        this.id_tip_mue = id_tip_mue;
        this.tip_mue = tip_mue;
    }

    public int getId_tip_mue() {
        return id_tip_mue;
    }

    public void setId_tip_mue(int id_tip_mue) {
        this.id_tip_mue = id_tip_mue;
    }

    public String getTip_mue() {
        return tip_mue;
    }

    public void setTip_mue(String tip_mue) {
        this.tip_mue = tip_mue;
    }

}