package minsa.formulario.DbHelper;

import android.provider.BaseColumns;

public class ConglomeradoDbHelper {

    public static abstract class TableC implements BaseColumns {

        public static final String TableN = "conglomerado";

        public static final String id = "id";
        public static final String nombre = "nombre";
        public static final String numero_puestos = "numero_puestos";
        public static final String tamano_muestral = "tamano_muestral";
        public static final String ubigeo = "ubigeo";
        public static final String direccion = "direccion";
        public static final String latitud = "latitud";
        public static final String longitud = "longitud";
        public static final String id_tipo_conglomerado = "id_tipo_conglomerado";
        public static final String eliminado = "eliminado";
        public static final String id_usuario = "id_usuario";

    }

}
