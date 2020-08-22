package minsa.formulario.DbHelper;

import android.provider.BaseColumns;

public class DepartDbHelper {

    public static abstract class DepartTableC implements BaseColumns {

        public static final String DepartTableN = "departamento";

        public static final String DepartCodigo = "DEP_CODIGO";
        public static final String DepartDescri = "DEP_DESCRIPCION";
        public static final String DepartUbigeo = "UBIGEO_CODIGO";

    }

}
