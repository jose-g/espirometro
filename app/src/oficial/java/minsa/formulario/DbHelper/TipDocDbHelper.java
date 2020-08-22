package minsa.formulario.DbHelper;

import android.provider.BaseColumns;

public class TipDocDbHelper {

    public static abstract class TipDocTableC implements BaseColumns {

        public static final String TipDocTableN = "tipodocumento";

        public static final String TipDocIdenti = "TDO_IDENTI";
        public static final String TipDocDescri = "TDO_DESCRI";
        public static final String slug = "slug";

    }

}
