package minsa.formulario.DbHelper;

import android.provider.BaseColumns;

public class TipConglomeradoDbHelper {

    public static abstract class TableC implements BaseColumns {

        public static final String TableN = "tipo_conglomerado";

        public static final String id = "id";
        public static final String descripcion = "descripcion";
        public static final String habitacional_concurrencia = "habitacional_concurrencia";

    }

}
