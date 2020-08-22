package minsa.formulario.DbHelper;

import android.provider.BaseColumns;

public class EvolucionDbHelper {

    public static abstract class TableC implements BaseColumns {
        public static final String TableN = "evolucion";
        public static final String id = "id";
        public static final String descripcion = "descripcion";
    }

}