package minsa.formulario.DbHelper;

import android.provider.BaseColumns;

public class UsuariDbHelper {

    public static abstract class UsuariTableC implements BaseColumns {

        public static final String UsuariTableN = "usuario";

        public static final String UsuariCodigo = "USU_CODIGO";
        public static final String UsuariIdenti = "USU_IDENTI";
        public static final String UsuariUsuIng = "USU_USUING";
        public static final String UsuariConIng = "USU_CONING";
        public static final String UsuariTipDoc = "USU_TIPDOC";
        public static final String UsuariNroDoc = "USU_NRODOC";
        public static final String UsuariNombre = "USU_NOMBRE";
        public static final String UsuariApePat = "USU_APEPAT";
        public static final String UsuariApeMat = "USU_APEMAT";
        public static final String UsuariActivo = "USU_ACTIVO";

    }

}
