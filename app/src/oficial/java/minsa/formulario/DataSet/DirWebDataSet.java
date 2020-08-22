package minsa.formulario.DataSet;

import minsa.formulario.BuildConfig;

public class DirWebDataSet {

    private static DirWebDataSet instance;

    private String DirWebApiUrl = BuildConfig.BASE_URL;
    private String DirWebAuthor = BuildConfig.TOKEN_SSL;
    private String DirWebConTyp = "application/x-www-form-urlencoded";
    private String DirWebUseNam = BuildConfig.USR_TK_SLL;
    private String DirWebPasswo = BuildConfig.PWD_TK_SLL;
    private String DirWebGraTyp = "password";
    private String DirWebAuthorL = "Bearer ";
    private String DirWebConTypL = "application/json";

    public static final String DirWebSLL = BuildConfig.BASE_SSL;

    private DirWebDataSet() {}

    public static synchronized DirWebDataSet getInstance(){
        if(instance==null){
            instance = new DirWebDataSet();
        }
        return instance;
    }

    public String getDirWebApiUrl() {
        return DirWebApiUrl;
    }
    public void setDirWebApiUrl(String DirWebApiUrl) {
        this.DirWebApiUrl = DirWebApiUrl;
    }

    public String getDirWebAuthor() {
        return DirWebAuthor;
    }
    public void setDirWebAuthor(String DirWebAuthor) {
        this.DirWebAuthor = DirWebAuthor;
    }

    public String getDirWebConTyp() {
        return DirWebConTyp;
    }
    public void setDirWebConTyp(String DirWebConTyp) {
        this.DirWebConTyp = DirWebConTyp;
    }

    public String getDirWebUseNam() {
        return DirWebUseNam;
    }
    public void setDirWebUseNam(String DirWebUseNam) {
        this.DirWebUseNam = DirWebUseNam;
    }

    public String getDirWebPasswo() {
        return DirWebPasswo;
    }
    public void setDirWebPasswo(String DirWebPasswo) {
        this.DirWebPasswo = DirWebPasswo;
    }

    public String getDirWebGraTyp() {
        return DirWebGraTyp;
    }
    public void setDirWebGraTyp(String DirWebGraTyp) {
        this.DirWebGraTyp = DirWebGraTyp;
    }

    public String getDirWebAuthorL() {
        return DirWebAuthorL;
    }
    public void setDirWebAuthorL(String DirWebAuthorL) {
        this.DirWebAuthorL = DirWebAuthorL;
    }

    public String getDirWebConTypL() {
        return DirWebConTypL;
    }
    public void setDirWebConTypL(String DirWebConTypL) {
        this.DirWebConTypL = DirWebConTypL;
    }

}
