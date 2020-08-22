package minsa.formulario.DbHelper;

import android.provider.BaseColumns;

public class ParentescoDbHelper {

    public static abstract class ParentescoTableC implements BaseColumns {
        public static final String ParentescoTableN = "parentescos";
        public static final String ParentescoIdenti = "PAREN_IDENTI";
        public static final String ParentescoDescri = "PAREN_DESCRI";
    }

}