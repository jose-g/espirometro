package minsa.formulario.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PrinciDbHelper extends SQLiteOpenHelper {

    private static final String TAG = PrinciDbHelper.class.getSimpleName();
    public static final int DB_VERSION = 7;
    public static final String DB_NAME = "coronavirus.sqlite";

    public PrinciDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public long savePrinci(String Table_Name, ContentValues contentValues) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert(Table_Name, null,contentValues);
    }

    public long deletePrinci(String Table_Name, String Table_Id, String tableId) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.delete(Table_Name, Table_Id + " LIKE ?", new String[]{tableId});
    }

    public void deleteAll(String Table_Name) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        sqLiteDatabase.execSQL("DELETE FROM " + Table_Name);
    }
    public void TruncateAll(String Table_Name) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        sqLiteDatabase.execSQL("DELETE FROM " + Table_Name);
        sqLiteDatabase.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE name= '"+ Table_Name+"';");

    }
    public int updatePrinci(String Table_Name, ContentValues contentValues, String Table_Id, String campo) {
        return getWritableDatabase().update(Table_Name, contentValues, Table_Id + " LIKE ?", new String[]{campo});
    }

    public Cursor getAllPrinci(String Table_Name) {
        return getReadableDatabase()
                .query(
                        Table_Name,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null);
    }

    public int getCount(String Table_Name) {
        return getReadableDatabase()
                .query(
                        Table_Name,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null).getCount();
    }

    public Cursor getAllPrinciOne(String Table_Name, String Campo, String tableId) {
        return getReadableDatabase().query(
                Table_Name,
                null,
                Campo + " = ?",
                new String[]{tableId},
                null,
                null,
                null);
    }
    //SELECT DISTINCT
//	city
//FROM
//	customers
//	ORDER BY
    public Cursor getDISTINCTOne(String Table_Name, String Campo1, String Campo2) {
        String query = "SELECT DISTINCT "+Campo1+" , "+Campo2 +" FROM "+Table_Name+"";
        return getReadableDatabase().rawQuery(query, null);
    }
    public Cursor getalltwoLikeprinc(String Table_Name, String Campo1, String tableId1, String Campo2, String tableId2) {
        return getReadableDatabase().query(
                Table_Name,
                null,
                Campo1 + " LIKE ? AND "+Campo2 + " = ? ",
                new String[]{tableId1+"%",tableId2},
                null,
                null,
                null);
    }
    public Cursor getLikePrincithree(String Table_Name, String Campo1, String tableId1, String Campo2, String tableId2, String Campo3, String tableId3) {
        return getReadableDatabase().query(
                Table_Name,
                null,
                Campo1 + " LIKE ? AND "+Campo2 + " LIKE ? AND "+Campo3 + " LIKE ? ",
                new String[]{tableId1+"%",tableId2+"%",tableId3+"%"},
                null,
                null,
                null);
    }
    public Cursor getAllPrinciTwo(String Table_Name, String campo1, String valor1, String campo2, String valor2) {
        return getReadableDatabase().query(
                Table_Name,
                null,
                campo1 + " = ? AND " + campo2 + " = ?",
                new String[]{valor1, valor2},
                null,
                null,
                null);
    }

    public Cursor getCustom(String ubigeo) {
        String query = "SELECT * FROM tipo_conglomerado WHERE id IN (SELECT id_tipo_conglomerado FROM conglomerado WHERe ubigeo = '" + ubigeo +"')";
        return getReadableDatabase().rawQuery(query, null);
    }

    public Cursor getAllBetween(String Table_Name, String Table_Campo, int valor1, int valor2) {
        return getReadableDatabase().query(
                Table_Name,
                null,
                Table_Campo + " BETWEEN " + valor1 + " AND " + valor2,
                null,
                null,
                null,
                Table_Campo + " DESC");
    }

    public Cursor getAllTwoValues(String Table_Name, String Campo01, String Campo02, String valor1, String valor2) {
        return getReadableDatabase().query(
                Table_Name,
                null,
                Campo01 + " LIKE ? AND " + Campo02 + " LIKE ?",
                new String[]{valor1,valor2},
                null,
                null,
                null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DatoPacienteDbHelper.TableC.TableN + "(" +
                " " + DatoPacienteDbHelper.TableC._id + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                " " + DatoPacienteDbHelper.TableC._id_interno + " TEXT NOT NULL," +
                " " + DatoPacienteDbHelper.TableC.fecha + " TEXT ," +
                " " + DatoPacienteDbHelper.TableC.id_tipo_doc + " TEXT NOT NULL,\n" +
                " " + DatoPacienteDbHelper.TableC.tipo_doc + " TEXT NOT NULL," +
                " " + DatoPacienteDbHelper.TableC.numero_doc + " TEXT NOT NULL,\n" +
                " " + DatoPacienteDbHelper.TableC.nombres + " TEXT NOT NULL," +
                " " + DatoPacienteDbHelper.TableC.paterno + " TEXT NOT NULL,\n" +
                " " + DatoPacienteDbHelper.TableC.materno + " TEXT NOT NULL," +
                " " + DatoPacienteDbHelper.TableC.fec_nac + " TEXT NOT NULL," +
                " " + DatoPacienteDbHelper.TableC.id_sexo + " TEXT ,\n" +
                " " + DatoPacienteDbHelper.TableC.sexo + " TEXT ," +
                " " + DatoPacienteDbHelper.TableC.celular + " TEXT NOT NULL,\n" +
                " " + DatoPacienteDbHelper.TableC.celular_contacto + " TEXT NOT NULL," +
                " " + DatoPacienteDbHelper.TableC.correo + " TEXT NOT NULL,\n" +
                " " + DatoPacienteDbHelper.TableC.id_tipo_residencia + " INTEGER ," +
                " " + DatoPacienteDbHelper.TableC.tipo_residencia + " TEXT ,\n" +
                " " + DatoPacienteDbHelper.TableC.direccion + " TEXT NOT NULL," +
                " " + DatoPacienteDbHelper.TableC.id_departamento + " TEXT  ,\n" +
                " " + DatoPacienteDbHelper.TableC.departamento + " TEXT  ," +
                " " + DatoPacienteDbHelper.TableC.id_provincia + " TEXT  ," +
                " " + DatoPacienteDbHelper.TableC.provincia + " INTEGER  ," +
                " " + DatoPacienteDbHelper.TableC.id_distrito + " TEXT  ,\n" +
                " " + DatoPacienteDbHelper.TableC.distrito + " TEXT  ," +
                " " + DatoPacienteDbHelper.TableC.latitud + " TEXT," +
                " " + DatoPacienteDbHelper.TableC.longitud + " TEXT," +
                " " + DatoPacienteDbHelper.TableC.es_pers_salud + " INTEGER  ," +
                " " + DatoPacienteDbHelper.TableC.id_profesion + " INTEGER  ," +
                " " + DatoPacienteDbHelper.TableC.tiene_sintoma + " INTEGER  ,\n" +
                " " + DatoPacienteDbHelper.TableC.fecha_sintoma + " TEXT  ," +
                " " + DatoPacienteDbHelper.TableC.otro_sintoma + " TEXT  ," +
                " " + DatoPacienteDbHelper.TableC.id_usuario + " INTEGER  ," +
                " " + DatoPacienteDbHelper.TableC.id_dato_paciente + " INTEGER  ," +
                " " + DatoPacienteDbHelper.TableC.codigo_pais_celular + " TEXT  ," +
                " " + DatoPacienteDbHelper.TableC.codigo_pais_telefono + " TEXT  " +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + DatoPacienteSintomaDbHelper.TableC.TableN + "(" +
                " " + DatoPacienteSintomaDbHelper.TableC._id_dato_paciente + " INTEGER,\n" +
                " " + DatoPacienteSintomaDbHelper.TableC.id_sintoma + " INTEGER NOT NULL," +
                " " + DatoPacienteSintomaDbHelper.TableC.sintoma + " TEXT NOT NULL\n" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + F100DbHelper.TableC.TableN + "(" +
                " " + F100DbHelper.TableC._id + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                " " + F100DbHelper.TableC.fecha + " INTEGER NOT NULL," +
                " " + F100DbHelper.TableC.hora + " TEXT NOT NULL,\n" +
                " " + F100DbHelper.TableC.marca_prueba + " TEXT NOT NULL," +
                " " + F100DbHelper.TableC.lote_prueba + " TEXT NOT NULL,\n" +
                " " + F100DbHelper.TableC.industria_prueba + " TEXT NOT NULL," +
                " " + F100DbHelper.TableC.fecha_prueba + " TEXT NOT NULL,\n" +
                " " + F100DbHelper.TableC.id_procedencia + " INTEGER NOT NULL," +
                " " + F100DbHelper.TableC.procedencia + " TEXT NOT NULL," +
                " " + F100DbHelper.TableC.id_resultado_prueba_rapida + " INTEGER NOT NULL,\n" +
                " " + F100DbHelper.TableC.resultado_prueba_rapida + " TEXT NOT NULL,\n" +
                " " + F100DbHelper.TableC.id_resultado_prueba_rapida_2 + " INTEGER NOT NULL," +
                " " + F100DbHelper.TableC.resultado_prueba_rapida_2 + " TEXT NOT NULL," +
                " " + F100DbHelper.TableC.id_severidad + " INTEGER NOT NULL,\n" +
                " " + F100DbHelper.TableC.severidad + " TEXT NOT NULL,\n" +
                " " + F100DbHelper.TableC.id_trimestre + " INTEGER NOT NULL,\n" +
                " " + F100DbHelper.TableC.trimestre + " TEXT NOT NULL," +
                " " + F100DbHelper.TableC.otro_riesgo + " TEXT NOT NULL,\n" +
                " " + F100DbHelper.TableC.pcr + " INTEGER NOT NULL," +
                " " + F100DbHelper.TableC.observacion + " TEXT NOT NULL,\n" +
                " " + F100DbHelper.TableC.id_tip_doc + " TEXT NOT NULL,\n" +
                " " + F100DbHelper.TableC.tipo_doc + " TEXT NOT NULL,\n" +
                " " + F100DbHelper.TableC.num_doc + " TEXT NOT NULL,\n" +
                " " + F100DbHelper.TableC.paciente + " TEXT NOT NULL,\n" +
                " " + F100DbHelper.TableC.id_usuario + " TEXT NOT NULL," +
                " " + F100DbHelper.TableC.id_f100 + " INTEGER NOT NULL,\n" +
                " " + F100DbHelper.TableC.id_dato_paciente + " INTEGER NOT NULL," +
                " " + F100DbHelper.TableC.conglomerado + " TEXT," +
                " " + F100DbHelper.TableC.id_conglomerado + " TEXT," +
                " " + F100DbHelper.TableC.tipo_conglomerado + " TEXT," +
                " " + F100DbHelper.TableC.id_tipo_conglomerado + " TEXT" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + F100RiesgoDbHelper.TableC.TableN + "(" +
                " " + F100RiesgoDbHelper.TableC._id_f100 + " INTEGER,\n" +
                " " + F100RiesgoDbHelper.TableC.id_riesgo + " INTEGER NOT NULL," +
                " " + F100RiesgoDbHelper.TableC.riesgo + " TEXT NOT NULL\n" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TipSegDbHelper.TipSegTableC.TipSegTableN + "(" +
                " " + TipSegDbHelper.TipSegTableC.TipSegIdenti + " INTEGER PRIMARY KEY,\n" +
                " " + TipSegDbHelper.TipSegTableC.TipSegDescri + " TEXT NOT NULL" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + SigAlarmDbHelper.TableC.TableN + "(" +
                " " + SigAlarmDbHelper.TableC.id + " INTEGER PRIMARY KEY,\n" +
                " " + SigAlarmDbHelper.TableC.descripcion + " TEXT NOT NULL" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + SigAlarm2DbHelper.SigAlarm2TableC.SigAlarm2TableN + "(" +
                " " + SigAlarm2DbHelper.SigAlarm2TableC.SigAlarm2Identi + " INTEGER PRIMARY KEY,\n" +
                " " + SigAlarm2DbHelper.SigAlarm2TableC.SigAlarm2Descri + " TEXT NOT NULL" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + Riesgo2DbHelper.RiesgoTableC.RiesgoTableN + "(" +
                " " + Riesgo2DbHelper.RiesgoTableC.RiesgoIdenti + " INTEGER PRIMARY KEY,\n" +
                " " + Riesgo2DbHelper.RiesgoTableC.RiesgoDescri + " TEXT NOT NULL" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + ParentescoDbHelper.ParentescoTableC.ParentescoTableN + "(" +
                " " + ParentescoDbHelper.ParentescoTableC.ParentescoIdenti + " INTEGER PRIMARY KEY,\n" +
                " " + ParentescoDbHelper.ParentescoTableC.ParentescoDescri + " TEXT NOT NULL" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + PaisDbHelper.PaisTableC.PaisTableN + "(" +
                " " + PaisDbHelper.PaisTableC.PaisIdenti + " INTEGER PRIMARY KEY,\n" +
                " " + PaisDbHelper.PaisTableC.PaisDescri + " TEXT NOT NULL" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TipMueDbHelper.TipMueTableC.TipMueTableN + "(" +
                " " + TipMueDbHelper.TipMueTableC.TipMueIdenti + " INTEGER PRIMARY KEY,\n" +
                " " + TipMueDbHelper.TipMueTableC.TipMueDescri + " TEXT NOT NULL" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + F200DbHelper.TableC.TableN + "(" +
                " " + F200DbHelper.TableC._id + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                " " + F200DbHelper.TableC.id_tip_seg + " INTEGER NOT NULL," +
                " " + F200DbHelper.TableC.tip_seg + " TEXT NOT NULL,\n" +
                " " + F200DbHelper.TableC.otro_seguro + " TEXT NOT NULL," +
                " " + F200DbHelper.TableC.fec_notif + " TEXT NOT NULL,\n" +
                " " + F200DbHelper.TableC.profesion + " TEXT NOT NULL," +
                " " + F200DbHelper.TableC.hospitalizado + " INTEGER NOT NULL,\n" +
                " " + F200DbHelper.TableC.fec_hospi + " TEXT NOT NULL," +
                " " + F200DbHelper.TableC.temperatura + " TEXT NOT NULL," +
                " " + F200DbHelper.TableC.otro_sintoma + " TEXT NOT NULL,\n" +
                " " + F200DbHelper.TableC.otro_riesgo + " TEXT NOT NULL," +
                " " + F100DbHelper.TableC.id_trimestre + " INTEGER NOT NULL,\n" +
                " " + F100DbHelper.TableC.trimestre + " TEXT NOT NULL," +
                " " + F200DbHelper.TableC.ha_viajado + " INTEGER NOT NULL,\n" +
                " " + F200DbHelper.TableC.id_cont_caso_conf + " INTEGER NOT NULL," +
                " " + F200DbHelper.TableC.contacto_caso_confirmado + " TEXT NOT NULL," +
                " " + F200DbHelper.TableC.id_tip_doc_inve + " TEXT NOT NULL,\n" +
                " " + F200DbHelper.TableC.tip_doc_inve + " TEXT NOT NULL,\n" +
                " " + F200DbHelper.TableC.doc_inve + " TEXT NOT NULL," +
                " " + F200DbHelper.TableC.nomb_inve + " TEXT NOT NULL,\n" +
                " " + F200DbHelper.TableC.paterno_inve + " TEXT NOT NULL," +
                " " + F200DbHelper.TableC.materno_inve + " TEXT NOT NULL,\n" +
                " " + F200DbHelper.TableC.observacion + " TEXT NOT NULL," +
                " " + F200DbHelper.TableC.id_tip_doc + " TEXT NOT NULL,\n" +
                " " + F200DbHelper.TableC.tipo_doc + " TEXT NOT NULL,\n" +
                " " + F200DbHelper.TableC.num_doc + " TEXT NOT NULL,\n" +
                " " + F200DbHelper.TableC.paciente + " TEXT NOT NULL,\n" +
                " " + F200DbHelper.TableC.id_usuario + " TEXT NOT NULL," +
                " " + F200DbHelper.TableC.id_f200 + " INTEGER NOT NULL," +
                " " + F200DbHelper.TableC.id_dato_paciente + " INTEGER NOT NULL," +
                " " + F100DbHelper.TableC.conglomerado + " TEXT," +
                " " + F100DbHelper.TableC.id_conglomerado + " TEXT," +
                " " + F100DbHelper.TableC.tipo_conglomerado + " TEXT," +
                " " + F100DbHelper.TableC.id_tipo_conglomerado + " TEXT" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + F200SintomaDbHelper.TableC.TableN + "(" +
                " " + F200SintomaDbHelper.TableC._id_f200 + " INTEGER,\n" +
                " " + F200SintomaDbHelper.TableC.id_sintoma + " INTEGER NOT NULL," +
                " " + F200SintomaDbHelper.TableC.sintoma + " TEXT NOT NULL\n" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + F200RiesgoDbHelper.TableC.TableN + "(" +
                " " + F200RiesgoDbHelper.TableC._id_f200 + " INTEGER,\n" +
                " " + F200RiesgoDbHelper.TableC.id_riesgo + " INTEGER NOT NULL," +
                " " + F200RiesgoDbHelper.TableC.riesgo + " TEXT NOT NULL\n" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + F200ViajeDbHelper.TableC.TableN + "(" +
                " " + F200ViajeDbHelper.TableC._id_f200 + " INTEGER,\n" +
                " " + F200ViajeDbHelper.TableC.id_pais + " INTEGER NOT NULL," +
                " " + F200ViajeDbHelper.TableC.pais + " TEXT NOT NULL,\n" +
                " " + F200ViajeDbHelper.TableC.ciudad + " TEXT NOT NULL\n" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + F200LaboratorioDbHelper.TableC.TableN + "(" +
                " " + F200LaboratorioDbHelper.TableC._id_f200 + " INTEGER,\n" +
                " " + F200LaboratorioDbHelper.TableC.fecha + " TEXT NOT NULL," +
                " " + F200LaboratorioDbHelper.TableC.hora + " TEXT NOT NULL,\n" +
                " " + F200LaboratorioDbHelper.TableC.id_tipo_muestra + " TEXT NOT NULL,\n" +
                " " + F200LaboratorioDbHelper.TableC.tipo_muestra + " TEXT NOT NULL\n" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + F200ContactoDbHelper.TableC.TableN + "(" +
                " " + F200ContactoDbHelper.TableC._id_contacto + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                " " + F200ContactoDbHelper.TableC._id_f200 + " INTEGER,\n" +
                " " + F200ContactoDbHelper.TableC.id_tip_doc + " TEXT NOT NULL,\n" +
                " " + F200ContactoDbHelper.TableC.tip_doc + " TEXT,\n" +
                " " + F200ContactoDbHelper.TableC.num_doc + " TEXT,\n" +
                " " + F200ContactoDbHelper.TableC.fec_nac + " TEXT," +
                " " + F200ContactoDbHelper.TableC.nombres + " TEXT NOT NULL,\n" +
                " " + F200ContactoDbHelper.TableC.paterno + " TEXT NOT NULL,\n" +
                " " + F200ContactoDbHelper.TableC.materno + " TEXT NOT NULL,\n" +
                " " + F200ContactoDbHelper.TableC.id_parentesco + " TEXT NOT NULL," +
                " " + F200ContactoDbHelper.TableC.parentesco + " TEXT NOT NULL,\n" +
                " " + F200ContactoDbHelper.TableC.edad + " INTEGER NOT NULL,\n" +
                " " + F200ContactoDbHelper.TableC.id_sexo + " TEXT NOT NULL,\n" +
                " " + F200ContactoDbHelper.TableC.sexo + " TEXT NOT NULL,\n" +
                " " + F200ContactoDbHelper.TableC.celular + " TEXT,\n" +
                " " + F200ContactoDbHelper.TableC.direccion + " TEXT,\n" +
                " " + F200ContactoDbHelper.TableC.f100 + " INTEGER\n" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + F200ContactoRiesgoDbHelper.TableC.TableN + "(" +
                " " + F200ContactoRiesgoDbHelper.TableC._id_contacto + " INTEGER NOT NULL," +
                " " + F200ContactoRiesgoDbHelper.TableC.id_riesgo + " INTEGER NOT NULL,\n" +
                " " + F200ContactoRiesgoDbHelper.TableC.riesgo + " TEXT NOT NULL\n" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + F300DbHelper.TableC.TableN + "(" +
                " " + F300DbHelper.TableC._id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                " " + F300DbHelper.TableC.fecha + " TEXT NOT NULL,\n" +
                " " + F300DbHelper.TableC.id_tipo_monitoreo + " INTEGER NOT NULL,\n" +
                " " + F300DbHelper.TableC.tipo_monitoreo + " TEXT NOT NULL,\n" +
                " " + F300DbHelper.TableC.realizar_medicion + " INTEGER,\n" +
                " " + F300DbHelper.TableC.presion_arterial + " TEXT," +
                " " + F300DbHelper.TableC.presion_arterial_2 + " TEXT,\n" +
                " " + F300DbHelper.TableC.presion_arterial_media + " TEXT,\n" +
                " " + F300DbHelper.TableC.frecuencia_cardiaca + " TEXT,\n" +
                " " + F300DbHelper.TableC.frecuencia_respiratoria + " TEXT," +
                " " + F300DbHelper.TableC.temperatura + " TEXT,\n" +
                " " + F300DbHelper.TableC.otro_sintoma + " TEXT,\n" +
                " " + F300DbHelper.TableC.otro_alarma + " TEXT,\n" +
                " " + F300DbHelper.TableC.id_evolucion + " INTEGER NOT NULL,\n" +
                " " + F300DbHelper.TableC.evolucion + " TEXT NOT NULL,\n" +
                " " + F300DbHelper.TableC.egreso_clinico + " INTEGER NOT NULL,\n" +
                " " + F300DbHelper.TableC.id_condicion_egreso + " INTEGER,\n" +
                " " + F300DbHelper.TableC.condicion_egreso + " TEXT,\n" +
                " " + F300DbHelper.TableC.observacion + " TEXT,\n" +
                " " + F200DbHelper.TableC.id_tip_doc + " TEXT NOT NULL,\n" +
                " " + F200DbHelper.TableC.tipo_doc + " TEXT NOT NULL,\n" +
                " " + F200DbHelper.TableC.num_doc + " TEXT NOT NULL,\n" +
                " " + F200DbHelper.TableC.paciente + " TEXT NOT NULL,\n" +
                " " + F300DbHelper.TableC.id_usuario + " TEXT NOT NULL," +
                " " + F300DbHelper.TableC.id_f300 + " INTEGER NOT NULL," +
                " " + F300DbHelper.TableC.id_dato_paciente + " INTEGER NOT NULL," +
                " " + F100DbHelper.TableC.conglomerado + " TEXT," +
                " " + F100DbHelper.TableC.id_conglomerado + " TEXT," +
                " " + F100DbHelper.TableC.tipo_conglomerado + " TEXT," +
                " " + F100DbHelper.TableC.id_tipo_conglomerado + " TEXT" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + F300SintomaDbHelper.TableC.TableN + "(" +
                " " + F300SintomaDbHelper.TableC._id_f300 + " INTEGER,\n" +
                " " + F300SintomaDbHelper.TableC.id_sintoma + " INTEGER NOT NULL," +
                " " + F300SintomaDbHelper.TableC.sintoma + " TEXT NOT NULL\n" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + F300AlarmaDbHelper.TableC.TableN + "(" +
                " " + F300AlarmaDbHelper.TableC._id_f300 + " INTEGER,\n" +
                " " + F300AlarmaDbHelper.TableC.id_alarma + " INTEGER NOT NULL," +
                " " + F300AlarmaDbHelper.TableC.alarma + " TEXT NOT NULL\n" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TipConglomeradoDbHelper.TableC.TableN + "(" +
                " " + TipConglomeradoDbHelper.TableC.id + " TEXT,\n" +
                " " + TipConglomeradoDbHelper.TableC.descripcion + " TEXT NOT NULL," +
                " " + TipConglomeradoDbHelper.TableC.habitacional_concurrencia + " TEXT NOT NULL\n" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + ConglomeradoDbHelper.TableC.TableN + "(" +
                " " + ConglomeradoDbHelper.TableC.id + " TEXT,\n" +
                " " + ConglomeradoDbHelper.TableC.nombre + " TEXT NOT NULL," +
                " " + ConglomeradoDbHelper.TableC.numero_puestos + " TEXT,\n" +
                " " + ConglomeradoDbHelper.TableC.tamano_muestral + " TEXT NOT NULL," +
                " " + ConglomeradoDbHelper.TableC.ubigeo + " TEXT,\n" +
                " " + ConglomeradoDbHelper.TableC.direccion + " TEXT NOT NULL," +
                " " + ConglomeradoDbHelper.TableC.latitud + " TEXT,\n" +
                " " + ConglomeradoDbHelper.TableC.longitud + " TEXT NOT NULL," +
                " " + ConglomeradoDbHelper.TableC.id_tipo_conglomerado + " TEXT,\n" +
                " " + ConglomeradoDbHelper.TableC.eliminado + " TEXT NOT NULL," +
                " " + ConglomeradoDbHelper.TableC.id_usuario + " TEXT NOT NULL\n" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + EvolucionDbHelper.TableC.TableN + "(" +
                " " + EvolucionDbHelper.TableC.id + " INTEGER PRIMARY KEY,\n" +
                " " + EvolucionDbHelper.TableC.descripcion + " TEXT NOT NULL" +
                ")");

        if(!isFieldExist(db, TipDocDbHelper.TipDocTableC.TipDocTableN, TipDocDbHelper.TipDocTableC.slug)) {
            db.execSQL(String.format("ALTER TABLE " + TipDocDbHelper.TipDocTableC.TipDocTableN + " ADD COLUMN " + TipDocDbHelper.TipDocTableC.slug + " TEXT NULL"));
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + DatoPacienteDbHelper.TableC.TableN + "(" +
                " " + DatoPacienteDbHelper.TableC._id + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                " " + DatoPacienteDbHelper.TableC.fecha + " TEXT NOT NULL," +
                " " + DatoPacienteDbHelper.TableC.id_tipo_doc + " TEXT NOT NULL,\n" +
                " " + DatoPacienteDbHelper.TableC.tipo_doc + " TEXT NOT NULL," +
                " " + DatoPacienteDbHelper.TableC.numero_doc + " TEXT NOT NULL,\n" +
                " " + DatoPacienteDbHelper.TableC.nombres + " TEXT NOT NULL," +
                " " + DatoPacienteDbHelper.TableC.paterno + " TEXT NOT NULL,\n" +
                " " + DatoPacienteDbHelper.TableC.materno + " TEXT NOT NULL," +
                " " + DatoPacienteDbHelper.TableC.fec_nac + " TEXT NOT NULL," +
                " " + DatoPacienteDbHelper.TableC.id_sexo + " TEXT NOT NULL,\n" +
                " " + DatoPacienteDbHelper.TableC.sexo + " TEXT NOT NULL," +
                " " + DatoPacienteDbHelper.TableC.celular + " TEXT NOT NULL,\n" +
                " " + DatoPacienteDbHelper.TableC.celular_contacto + " TEXT NOT NULL," +
                " " + DatoPacienteDbHelper.TableC.correo + " TEXT NOT NULL,\n" +
                " " + DatoPacienteDbHelper.TableC.id_tipo_residencia + " INTEGER NOT NULL," +
                " " + DatoPacienteDbHelper.TableC.tipo_residencia + " TEXT NOT NULL,\n" +
                " " + DatoPacienteDbHelper.TableC.direccion + " TEXT NOT NULL," +
                " " + DatoPacienteDbHelper.TableC.id_departamento + " TEXT NOT NULL,\n" +
                " " + DatoPacienteDbHelper.TableC.departamento + " TEXT NOT NULL," +
                " " + DatoPacienteDbHelper.TableC.id_provincia + " TEXT NOT NULL," +
                " " + DatoPacienteDbHelper.TableC.provincia + " INTEGER NOT NULL," +
                " " + DatoPacienteDbHelper.TableC.id_distrito + " TEXT NOT NULL,\n" +
                " " + DatoPacienteDbHelper.TableC.distrito + " TEXT NOT NULL," +
                " " + DatoPacienteDbHelper.TableC.latitud + " TEXT," +
                " " + DatoPacienteDbHelper.TableC.longitud + " TEXT," +
                " " + DatoPacienteDbHelper.TableC.es_pers_salud + " INTEGER NOT NULL," +
                " " + DatoPacienteDbHelper.TableC.id_profesion + " INTEGER NOT NULL," +
                " " + DatoPacienteDbHelper.TableC.tiene_sintoma + " INTEGER NOT NULL,\n" +
                " " + DatoPacienteDbHelper.TableC.fecha_sintoma + " TEXT NOT NULL," +
                " " + DatoPacienteDbHelper.TableC.otro_sintoma + " TEXT NOT NULL," +
                " " + DatoPacienteDbHelper.TableC.id_usuario + " INTEGER NOT NULL," +
                " " + DatoPacienteDbHelper.TableC.id_dato_paciente + " INTEGER NOT NULL," +
                " " + DatoPacienteDbHelper.TableC.codigo_pais_celular + " TEXT NOT NULL," +
                " " + DatoPacienteDbHelper.TableC.codigo_pais_telefono + " TEXT NOT NULL" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + DatoPacienteSintomaDbHelper.TableC.TableN + "(" +
                " " + DatoPacienteSintomaDbHelper.TableC._id_dato_paciente + " INTEGER,\n" +
                " " + DatoPacienteSintomaDbHelper.TableC.id_sintoma + " INTEGER NOT NULL," +
                " " + DatoPacienteSintomaDbHelper.TableC.sintoma + " TEXT NOT NULL\n" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + F100DbHelper.TableC.TableN + "(" +
                " " + F100DbHelper.TableC._id + " INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                " " + F100DbHelper.TableC.fecha + " INTEGER NOT NULL," +
                " " + F100DbHelper.TableC.hora + " TEXT NOT NULL,\n" +
                " " + F100DbHelper.TableC.marca_prueba + " TEXT NOT NULL," +
                " " + F100DbHelper.TableC.lote_prueba + " TEXT NOT NULL,\n" +
                " " + F100DbHelper.TableC.industria_prueba + " TEXT NOT NULL," +
                " " + F100DbHelper.TableC.fecha_prueba + " TEXT NOT NULL,\n" +
                " " + F100DbHelper.TableC.id_procedencia + " INTEGER NOT NULL," +
                " " + F100DbHelper.TableC.procedencia + " TEXT NOT NULL," +
                " " + F100DbHelper.TableC.id_resultado_prueba_rapida + " INTEGER NOT NULL,\n" +
                " " + F100DbHelper.TableC.resultado_prueba_rapida + " TEXT NOT NULL,\n" +
                " " + F100DbHelper.TableC.id_resultado_prueba_rapida_2 + " INTEGER NOT NULL," +
                " " + F100DbHelper.TableC.resultado_prueba_rapida_2 + " TEXT NOT NULL," +
                " " + F100DbHelper.TableC.id_severidad + " INTEGER NOT NULL,\n" +
                " " + F100DbHelper.TableC.severidad + " TEXT NOT NULL,\n" +
                " " + F100DbHelper.TableC.id_trimestre + " INTEGER NOT NULL,\n" +
                " " + F100DbHelper.TableC.trimestre + " TEXT NOT NULL," +
                " " + F100DbHelper.TableC.otro_riesgo + " TEXT NOT NULL,\n" +
                " " + F100DbHelper.TableC.pcr + " INTEGER NOT NULL," +
                " " + F100DbHelper.TableC.observacion + " TEXT NOT NULL,\n" +
                " " + F100DbHelper.TableC.id_tip_doc + " TEXT NOT NULL,\n" +
                " " + F100DbHelper.TableC.tipo_doc + " TEXT NOT NULL,\n" +
                " " + F100DbHelper.TableC.num_doc + " TEXT NOT NULL,\n" +
                " " + F100DbHelper.TableC.paciente + " TEXT NOT NULL,\n" +
                " " + F100DbHelper.TableC.id_usuario + " TEXT NOT NULL," +
                " " + F100DbHelper.TableC.id_f100 + " INTEGER NOT NULL,\n" +
                " " + F100DbHelper.TableC.id_dato_paciente + " INTEGER NOT NULL" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + F100RiesgoDbHelper.TableC.TableN + "(" +
                " " + F100RiesgoDbHelper.TableC._id_f100 + " INTEGER,\n" +
                " " + F100RiesgoDbHelper.TableC.id_riesgo + " INTEGER NOT NULL," +
                " " + F100RiesgoDbHelper.TableC.riesgo + " TEXT NOT NULL\n" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + SigAlarm2DbHelper.SigAlarm2TableC.SigAlarm2TableN + "(" +
                " " + SigAlarm2DbHelper.SigAlarm2TableC.SigAlarm2Identi + " INTEGER PRIMARY KEY,\n" +
                " " + SigAlarm2DbHelper.SigAlarm2TableC.SigAlarm2Descri + " TEXT NOT NULL" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + SigAlarmDbHelper.TableC.TableN + "(" +
                " " + SigAlarmDbHelper.TableC.id + " INTEGER PRIMARY KEY,\n" +
                " " + SigAlarmDbHelper.TableC.descripcion + " TEXT NOT NULL" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + SigAlarm2DbHelper.SigAlarm2TableC.SigAlarm2TableN + "(" +
                " " + SigAlarm2DbHelper.SigAlarm2TableC.SigAlarm2Identi + " INTEGER PRIMARY KEY,\n" +
                " " + SigAlarm2DbHelper.SigAlarm2TableC.SigAlarm2Descri + " TEXT NOT NULL" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + Riesgo2DbHelper.RiesgoTableC.RiesgoTableN + "(" +
                " " + Riesgo2DbHelper.RiesgoTableC.RiesgoIdenti + " INTEGER PRIMARY KEY,\n" +
                " " + Riesgo2DbHelper.RiesgoTableC.RiesgoDescri + " TEXT NOT NULL" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + ParentescoDbHelper.ParentescoTableC.ParentescoTableN + "(" +
                " " + ParentescoDbHelper.ParentescoTableC.ParentescoIdenti + " INTEGER PRIMARY KEY,\n" +
                " " + ParentescoDbHelper.ParentescoTableC.ParentescoDescri + " TEXT NOT NULL" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + PaisDbHelper.PaisTableC.PaisTableN + "(" +
                " " + PaisDbHelper.PaisTableC.PaisIdenti + " INTEGER PRIMARY KEY,\n" +
                " " + PaisDbHelper.PaisTableC.PaisDescri + " TEXT NOT NULL" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TipMueDbHelper.TipMueTableC.TipMueTableN + "(" +
                " " + TipMueDbHelper.TipMueTableC.TipMueIdenti + " INTEGER PRIMARY KEY,\n" +
                " " + TipMueDbHelper.TipMueTableC.TipMueDescri + " TEXT NOT NULL" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + F200DbHelper.TableC.TableN + "(" +
                " " + F200DbHelper.TableC._id + " INTEGER PRIMARY KEY,\n" +
                " " + F200DbHelper.TableC.id_tip_seg + " INTEGER NOT NULL," +
                " " + F200DbHelper.TableC.tip_seg + " TEXT NOT NULL,\n" +
                " " + F200DbHelper.TableC.otro_seguro + " TEXT NOT NULL," +
                " " + F200DbHelper.TableC.fec_notif + " TEXT NOT NULL,\n" +
                " " + F200DbHelper.TableC.profesion + " TEXT NOT NULL," +
                " " + F200DbHelper.TableC.hospitalizado + " INTEGER NOT NULL,\n" +
                " " + F200DbHelper.TableC.fec_hospi + " TEXT NOT NULL," +
                " " + F200DbHelper.TableC.temperatura + " TEXT NOT NULL," +
                " " + F200DbHelper.TableC.otro_sintoma + " TEXT NOT NULL,\n" +
                " " + F200DbHelper.TableC.otro_riesgo + " TEXT NOT NULL," +
                " " + F100DbHelper.TableC.id_trimestre + " INTEGER NOT NULL,\n" +
                " " + F100DbHelper.TableC.trimestre + " TEXT NOT NULL," +
                " " + F200DbHelper.TableC.ha_viajado + " INTEGER NOT NULL,\n" +
                " " + F200DbHelper.TableC.id_cont_caso_conf + " INTEGER NOT NULL," +
                " " + F200DbHelper.TableC.contacto_caso_confirmado + " TEXT NOT NULL," +
                " " + F200DbHelper.TableC.id_tip_doc_inve + " TEXT NOT NULL,\n" +
                " " + F200DbHelper.TableC.tip_doc_inve + " TEXT NOT NULL,\n" +
                " " + F200DbHelper.TableC.doc_inve + " TEXT NOT NULL," +
                " " + F200DbHelper.TableC.nomb_inve + " TEXT NOT NULL,\n" +
                " " + F200DbHelper.TableC.paterno_inve + " TEXT NOT NULL," +
                " " + F200DbHelper.TableC.materno_inve + " TEXT NOT NULL,\n" +
                " " + F200DbHelper.TableC.observacion + " TEXT NOT NULL," +
                " " + F200DbHelper.TableC.id_tip_doc + " TEXT NOT NULL,\n" +
                " " + F200DbHelper.TableC.tipo_doc + " TEXT NOT NULL,\n" +
                " " + F200DbHelper.TableC.num_doc + " TEXT NOT NULL,\n" +
                " " + F200DbHelper.TableC.paciente + " TEXT NOT NULL,\n" +
                " " + F200DbHelper.TableC.id_usuario + " TEXT NOT NULL," +
                " " + F200DbHelper.TableC.id_f200 + " INTEGER NOT NULL," +
                " " + F200DbHelper.TableC.id_dato_paciente + " INTEGER NOT NULL" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + F200SintomaDbHelper.TableC.TableN + "(" +
                " " + F200SintomaDbHelper.TableC._id_f200 + " INTEGER,\n" +
                " " + F200SintomaDbHelper.TableC.id_sintoma + " INTEGER NOT NULL," +
                " " + F200SintomaDbHelper.TableC.sintoma + " TEXT NOT NULL\n" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + F200RiesgoDbHelper.TableC.TableN + "(" +
                " " + F200RiesgoDbHelper.TableC._id_f200 + " INTEGER,\n" +
                " " + F200RiesgoDbHelper.TableC.id_riesgo + " INTEGER NOT NULL," +
                " " + F200RiesgoDbHelper.TableC.riesgo + " TEXT NOT NULL\n" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + F200ViajeDbHelper.TableC.TableN + "(" +
                " " + F200ViajeDbHelper.TableC._id_f200 + " INTEGER,\n" +
                " " + F200ViajeDbHelper.TableC.id_pais + " INTEGER NOT NULL," +
                " " + F200ViajeDbHelper.TableC.pais + " TEXT NOT NULL,\n" +
                " " + F200ViajeDbHelper.TableC.ciudad + " TEXT NOT NULL\n" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + F200LaboratorioDbHelper.TableC.TableN + "(" +
                " " + F200LaboratorioDbHelper.TableC._id_f200 + " INTEGER,\n" +
                " " + F200LaboratorioDbHelper.TableC.fecha + " TEXT NOT NULL," +
                " " + F200LaboratorioDbHelper.TableC.hora + " TEXT NOT NULL,\n" +
                " " + F200LaboratorioDbHelper.TableC.id_tipo_muestra + " TEXT NOT NULL,\n" +
                " " + F200LaboratorioDbHelper.TableC.tipo_muestra + " TEXT NOT NULL\n" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + F200ContactoDbHelper.TableC.TableN + "(" +
                " " + F200ContactoDbHelper.TableC._id_contacto + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                " " + F200ContactoDbHelper.TableC._id_f200 + " INTEGER,\n" +
                " " + F200ContactoDbHelper.TableC.id_tip_doc + " TEXT NOT NULL,\n" +
                " " + F200ContactoDbHelper.TableC.tip_doc + " TEXT,\n" +
                " " + F200ContactoDbHelper.TableC.num_doc + " TEXT,\n" +
                " " + F200ContactoDbHelper.TableC.fec_nac + " TEXT," +
                " " + F200ContactoDbHelper.TableC.nombres + " TEXT NOT NULL,\n" +
                " " + F200ContactoDbHelper.TableC.paterno + " TEXT NOT NULL,\n" +
                " " + F200ContactoDbHelper.TableC.materno + " TEXT NOT NULL,\n" +
                " " + F200ContactoDbHelper.TableC.id_parentesco + " TEXT NOT NULL," +
                " " + F200ContactoDbHelper.TableC.parentesco + " TEXT NOT NULL,\n" +
                " " + F200ContactoDbHelper.TableC.edad + " INTEGER NOT NULL,\n" +
                " " + F200ContactoDbHelper.TableC.id_sexo + " TEXT NOT NULL,\n" +
                " " + F200ContactoDbHelper.TableC.sexo + " TEXT NOT NULL,\n" +
                " " + F200ContactoDbHelper.TableC.celular + " TEXT,\n" +
                " " + F200ContactoDbHelper.TableC.direccion + " TEXT,\n" +
                " " + F200ContactoDbHelper.TableC.f100 + " INTEGER\n" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + F200ContactoRiesgoDbHelper.TableC.TableN + "(" +
                " " + F200ContactoRiesgoDbHelper.TableC._id_contacto + " INTEGER NOT NULL," +
                " " + F200ContactoRiesgoDbHelper.TableC.id_riesgo + " INTEGER NOT NULL,\n" +
                " " + F200ContactoRiesgoDbHelper.TableC.riesgo + " TEXT NOT NULL\n" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + F300DbHelper.TableC.TableN + "(" +
                " " + F300DbHelper.TableC._id + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                " " + F300DbHelper.TableC.fecha + " TEXT NOT NULL,\n" +
                " " + F300DbHelper.TableC.id_tipo_monitoreo + " INTEGER NOT NULL,\n" +
                " " + F300DbHelper.TableC.tipo_monitoreo + " TEXT NOT NULL,\n" +
                " " + F300DbHelper.TableC.realizar_medicion + " INTEGER,\n" +
                " " + F300DbHelper.TableC.presion_arterial + " TEXT," +
                " " + F300DbHelper.TableC.presion_arterial_2 + " TEXT,\n" +
                " " + F300DbHelper.TableC.presion_arterial_media + " TEXT,\n" +
                " " + F300DbHelper.TableC.frecuencia_cardiaca + " TEXT,\n" +
                " " + F300DbHelper.TableC.frecuencia_respiratoria + " TEXT," +
                " " + F300DbHelper.TableC.temperatura + " TEXT,\n" +
                " " + F300DbHelper.TableC.otro_sintoma + " TEXT,\n" +
                " " + F300DbHelper.TableC.otro_alarma + " TEXT,\n" +
                " " + F300DbHelper.TableC.id_evolucion + " INTEGER NOT NULL,\n" +
                " " + F300DbHelper.TableC.evolucion + " TEXT NOT NULL,\n" +
                " " + F300DbHelper.TableC.egreso_clinico + " INTEGER NOT NULL,\n" +
                " " + F300DbHelper.TableC.id_condicion_egreso + " INTEGER,\n" +
                " " + F300DbHelper.TableC.condicion_egreso + " TEXT,\n" +
                " " + F300DbHelper.TableC.observacion + " TEXT,\n" +
                " " + F200DbHelper.TableC.id_tip_doc + " TEXT NOT NULL,\n" +
                " " + F200DbHelper.TableC.tipo_doc + " TEXT NOT NULL,\n" +
                " " + F200DbHelper.TableC.num_doc + " TEXT NOT NULL,\n" +
                " " + F200DbHelper.TableC.paciente + " TEXT NOT NULL,\n" +
                " " + F300DbHelper.TableC.id_usuario + " TEXT NOT NULL," +
                " " + F300DbHelper.TableC.id_f300 + " INTEGER NOT NULL," +
                " " + F300DbHelper.TableC.id_dato_paciente + " INTEGER NOT NULL" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + F300SintomaDbHelper.TableC.TableN + "(" +
                " " + F300SintomaDbHelper.TableC._id_f300 + " INTEGER,\n" +
                " " + F300SintomaDbHelper.TableC.id_sintoma + " INTEGER NOT NULL," +
                " " + F300SintomaDbHelper.TableC.sintoma + " TEXT NOT NULL\n" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + F300AlarmaDbHelper.TableC.TableN + "(" +
                " " + F300AlarmaDbHelper.TableC._id_f300 + " INTEGER,\n" +
                " " + F300AlarmaDbHelper.TableC.id_alarma + " INTEGER NOT NULL," +
                " " + F300AlarmaDbHelper.TableC.alarma + " TEXT NOT NULL\n" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TipConglomeradoDbHelper.TableC.TableN + "(" +
                " " + TipConglomeradoDbHelper.TableC.id + " TEXT,\n" +
                " " + TipConglomeradoDbHelper.TableC.descripcion + " TEXT NOT NULL," +
                " " + TipConglomeradoDbHelper.TableC.habitacional_concurrencia + " TEXT NOT NULL\n" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + ConglomeradoDbHelper.TableC.TableN + "(" +
                " " + ConglomeradoDbHelper.TableC.id + " TEXT,\n" +
                " " + ConglomeradoDbHelper.TableC.nombre + " TEXT NOT NULL," +
                " " + ConglomeradoDbHelper.TableC.numero_puestos + " TEXT,\n" +
                " " + ConglomeradoDbHelper.TableC.tamano_muestral + " TEXT NOT NULL," +
                " " + ConglomeradoDbHelper.TableC.ubigeo + " TEXT,\n" +
                " " + ConglomeradoDbHelper.TableC.direccion + " TEXT NOT NULL," +
                " " + ConglomeradoDbHelper.TableC.latitud + " TEXT,\n" +
                " " + ConglomeradoDbHelper.TableC.longitud + " TEXT NOT NULL," +
                " " + ConglomeradoDbHelper.TableC.id_tipo_conglomerado + " TEXT,\n" +
                " " + ConglomeradoDbHelper.TableC.eliminado + " TEXT NOT NULL," +
                " " + ConglomeradoDbHelper.TableC.id_usuario + " TEXT NOT NULL\n" +
                ")");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + EvolucionDbHelper.TableC.TableN + "(" +
                " " + EvolucionDbHelper.TableC.id + " INTEGER PRIMARY KEY,\n" +
                " " + EvolucionDbHelper.TableC.descripcion + " TEXT NOT NULL" +
                ")");

        // Dato paciente
        if(!isFieldExist(db, DatoPacienteDbHelper.TableC.TableN, DatoPacienteDbHelper.TableC.codigo_pais_celular)) {
            db.execSQL(String.format("ALTER TABLE " + DatoPacienteDbHelper.TableC.TableN + " ADD COLUMN " + DatoPacienteDbHelper.TableC.codigo_pais_celular + " TEXT NULL"));
        }

        if(!isFieldExist(db, DatoPacienteDbHelper.TableC.TableN, DatoPacienteDbHelper.TableC.codigo_pais_telefono)) {
            db.execSQL(String.format("ALTER TABLE " + DatoPacienteDbHelper.TableC.TableN + " ADD COLUMN " + DatoPacienteDbHelper.TableC.codigo_pais_telefono + " TEXT NULL"));
        }

        // F100
        if(!isFieldExist(db, F100DbHelper.TableC.TableN, F100DbHelper.TableC.id_conglomerado)) {
            db.execSQL(String.format("ALTER TABLE " + F100DbHelper.TableC.TableN + " ADD COLUMN " + F100DbHelper.TableC.id_conglomerado + " TEXT NULL"));
        }

        if(!isFieldExist(db, F100DbHelper.TableC.TableN, F100DbHelper.TableC.conglomerado)) {
            db.execSQL(String.format("ALTER TABLE " + F100DbHelper.TableC.TableN + " ADD COLUMN " + F100DbHelper.TableC.conglomerado + " TEXT NULL"));
        }

        if(!isFieldExist(db, F100DbHelper.TableC.TableN, F100DbHelper.TableC.id_tipo_conglomerado)) {
            db.execSQL(String.format("ALTER TABLE " + F100DbHelper.TableC.TableN + " ADD COLUMN " + F100DbHelper.TableC.id_tipo_conglomerado + " TEXT NULL"));
        }

        if(!isFieldExist(db, F100DbHelper.TableC.TableN, F100DbHelper.TableC.tipo_conglomerado)) {
            db.execSQL(String.format("ALTER TABLE " + F100DbHelper.TableC.TableN + " ADD COLUMN " + F100DbHelper.TableC.tipo_conglomerado + " TEXT NULL"));
        }

        // F200
        if(!isFieldExist(db, F200DbHelper.TableC.TableN, F200DbHelper.TableC.id_conglomerado)) {
            db.execSQL(String.format("ALTER TABLE " + F200DbHelper.TableC.TableN + " ADD COLUMN " + F200DbHelper.TableC.id_conglomerado + " TEXT NULL"));
        }

        if(!isFieldExist(db, F200DbHelper.TableC.TableN, F200DbHelper.TableC.conglomerado)) {
            db.execSQL(String.format("ALTER TABLE " + F200DbHelper.TableC.TableN + " ADD COLUMN " + F200DbHelper.TableC.conglomerado + " TEXT NULL"));
        }

        if(!isFieldExist(db, F200DbHelper.TableC.TableN, F200DbHelper.TableC.id_tipo_conglomerado)) {
            db.execSQL(String.format("ALTER TABLE " + F200DbHelper.TableC.TableN + " ADD COLUMN " + F200DbHelper.TableC.id_tipo_conglomerado + " TEXT NULL"));
        }

        if(!isFieldExist(db, F200DbHelper.TableC.TableN, F200DbHelper.TableC.tipo_conglomerado)) {
            db.execSQL(String.format("ALTER TABLE " + F200DbHelper.TableC.TableN + " ADD COLUMN " + F200DbHelper.TableC.tipo_conglomerado + " TEXT NULL"));
        }

        // F300
        if(!isFieldExist(db, F300DbHelper.TableC.TableN, F300DbHelper.TableC.id_conglomerado)) {
            db.execSQL(String.format("ALTER TABLE " + F300DbHelper.TableC.TableN + " ADD COLUMN " + F300DbHelper.TableC.id_conglomerado + " TEXT NULL"));
        }

        if(!isFieldExist(db, F300DbHelper.TableC.TableN, F300DbHelper.TableC.conglomerado)) {
            db.execSQL(String.format("ALTER TABLE " + F300DbHelper.TableC.TableN + " ADD COLUMN " + F300DbHelper.TableC.conglomerado + " TEXT NULL"));
        }

        if(!isFieldExist(db, F300DbHelper.TableC.TableN, F300DbHelper.TableC.id_tipo_conglomerado)) {
            db.execSQL(String.format("ALTER TABLE " + F300DbHelper.TableC.TableN + " ADD COLUMN " + F300DbHelper.TableC.id_tipo_conglomerado + " TEXT NULL"));
        }

        if(!isFieldExist(db, F300DbHelper.TableC.TableN, F300DbHelper.TableC.tipo_conglomerado)) {
            db.execSQL(String.format("ALTER TABLE " + F300DbHelper.TableC.TableN + " ADD COLUMN " + F300DbHelper.TableC.tipo_conglomerado + " TEXT NULL"));
        }

        if(!isFieldExist(db, TipDocDbHelper.TipDocTableC.TipDocTableN, TipDocDbHelper.TipDocTableC.slug)) {
            db.execSQL(String.format("ALTER TABLE " + TipDocDbHelper.TipDocTableC.TipDocTableN + " ADD COLUMN " + TipDocDbHelper.TipDocTableC.slug + " TEXT NULL"));
        }

    }

    public boolean isFieldExist(SQLiteDatabase db, String tableName, String fieldName) {
        boolean isExist = false;
        Cursor cursor = db.rawQuery("PRAGMA table_info('" + tableName + "')", null);
        if (cursor.moveToFirst()) {
            do {
                int value = cursor.getColumnIndex("name");
                if(value != -1 && cursor.getString(value).equals(fieldName)){
                    isExist = true;
                }
            } while (cursor.moveToNext());
        }
        return isExist;
    }

}