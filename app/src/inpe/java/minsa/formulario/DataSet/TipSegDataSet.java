package minsa.formulario.DataSet;

public class TipSegDataSet {

    private int id_tip_seg;
    private String tip_seg;

    public TipSegDataSet() {
    }

    public TipSegDataSet(int id_tip_seg, String tip_seg) {
        this.id_tip_seg = id_tip_seg;
        this.tip_seg = tip_seg;
    }

    public int getId_tip_seg() {
        return id_tip_seg;
    }

    public void setId_tip_seg(int id_tip_seg) {
        this.id_tip_seg = id_tip_seg;
    }

    public String getTip_seg() {
        return tip_seg;
    }

    public void setTip_seg(String tip_seg) {
        this.tip_seg = tip_seg;
    }

}