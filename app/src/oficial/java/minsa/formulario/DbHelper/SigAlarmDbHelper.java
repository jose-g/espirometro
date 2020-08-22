package minsa.formulario.DbHelper;

import android.provider.BaseColumns;

public class SigAlarmDbHelper {

    public static abstract class TableC implements BaseColumns {
        public static final String TableN = "signosalarmas";
        public static final String id = "id";
        public static final String descripcion = "descripcion";
    }

}