package minsa.formulario.DbHelper;

import android.provider.BaseColumns;

public class ProvinDbHelper {

    public static abstract class ProvinTableC implements BaseColumns {

        public static final String ProvinTableN = "provincia";

        public static final String DepartCodigo = "DEP_CODIGO";
        public static final String ProvinCodigo = "PROV_CODIGO";
        public static final String ProvinDescri = "PROV_DESCRIPCION";
        public static final String ProvinUbigeo = "UBIGEO_CODIGO";

    }

}
