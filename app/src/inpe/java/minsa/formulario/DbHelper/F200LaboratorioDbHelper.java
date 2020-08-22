package minsa.formulario.DbHelper;

import android.provider.BaseColumns;

public class F200LaboratorioDbHelper {

    public static abstract class TableC implements BaseColumns {

        public static final String TableN = "f200_laboratorio";

        public static final String _id_f200 = "_id_f200";
        public static final String fecha = "fecha";
        public static final String hora = "hora";
        public static final String id_tipo_muestra = "id_tipo_muestra";
        public static final String tipo_muestra = "tipo_muestra";

    }

}
