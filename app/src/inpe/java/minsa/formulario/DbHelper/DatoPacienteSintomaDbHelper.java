package minsa.formulario.DbHelper;

import android.provider.BaseColumns;

public class DatoPacienteSintomaDbHelper {

    public static abstract class TableC implements BaseColumns {

        public static final String TableN = "dato_paciente_sintoma";

        public static final String _id_dato_paciente = "_id_dato_paciente";
        public static final String id_sintoma = "id_sintoma";
        public static final String sintoma = "sintoma";
    }

}
