package minsa.formulario.DataSet;

public class TestInformationF200DataSet {

    private String type_test;
    private String date;
    private String result;

    public TestInformationF200DataSet(String type_test, String date, String result) {
        this.type_test = type_test;
        this.date = date;
        this.result = result;
    }

    public String getType_test() {
        return type_test;
    }

    public void setType_test(String type_test) {
        this.type_test = type_test;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

}
