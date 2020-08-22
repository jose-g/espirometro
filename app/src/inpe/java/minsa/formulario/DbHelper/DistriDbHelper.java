package minsa.formulario.DbHelper;

import android.provider.BaseColumns;

public class DistriDbHelper {

    public static abstract class DistriTableC implements BaseColumns {

        public static final String DistriTableN = "distrito";

        public static final String DepartCodigo = "DEP_CODIGO";
        public static final String ProvinCodigo = "PROV_CODIGO";
        public static final String DistriCodigo = "DIST_CODIGO";
        public static final String DistriDescri = "DIST_DESCRIPCION";
        public static final String DistriUbigeo = "UBIGEO_CODIGO";

    }

}
