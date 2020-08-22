package minsa.formulario.DbHelper;

import android.provider.BaseColumns;

public class TipSegDbHelper {

    public static abstract class TipSegTableC implements BaseColumns {
        public static final String TipSegTableN = "tiposeguro";
        public static final String TipSegIdenti = "TSEG_IDENTI";
        public static final String TipSegDescri = "TSEG_DESCRI";
    }

}