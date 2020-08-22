package minsa.formulario.DbHelper;

import android.provider.BaseColumns;

public class DatoPacienteDbHelper {

    public static abstract class TableC implements BaseColumns {

        public static final String TableN = "dato_paciente";

        public static final String _id = "_id";
        public static final String _id_interno = "_id_interno";
        public static final String fecha = "fecha";
        public static final String id_tipo_doc = "id_tipo_doc";
        public static final String tipo_doc = "tipo_doc";
        public static final String numero_doc = "numero_doc";
        public static final String nombres = "nombres";
        public static final String paterno = "paterno";
        public static final String materno = "materno";
        public static final String fec_nac = "fec_nac";
        public static final String id_sexo = "id_sexo";
        public static final String sexo = "sexo";
        public static final String celular = "celular";
        public static final String celular_contacto = "celular_contacto";
        public static final String correo = "correo";
        public static final String id_tipo_residencia = "id_tipo_residencia";
        public static final String tipo_residencia = "tipo_residencia";
        public static final String direccion = "direccion";
        public static final String id_departamento = "id_departamento";
        public static final String departamento = "departamento";
        public static final String id_provincia = "id_provincia";
        public static final String provincia = "provincia";
        public static final String id_distrito = "id_distrito";
        public static final String distrito = "distrito";
        public static final String latitud = "latitud";
        public static final String longitud = "longitud";
        public static final String es_pers_salud = "es_pers_salud";
        public static final String id_profesion = "id_profesion";
        public static final String tiene_sintoma = "tiene_sintoma";
        public static final String fecha_sintoma = "fecha_sintoma";
        public static final String otro_sintoma = "otro_sintoma";
        public static final String id_usuario = "id_usuario";
        public static final String id_dato_paciente = "id_dato_paciente";
        public static final String codigo_pais_celular = "codigo_pais_celular";
        public static final String codigo_pais_telefono = "codigo_pais_telefono";
    }

}