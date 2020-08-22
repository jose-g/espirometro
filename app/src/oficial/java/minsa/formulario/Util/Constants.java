package minsa.formulario.Util;

public class Constants {

    public static final String ID_APP = "4PPS1C0V1D";

    public static final String URL_TIPO_DOCUMENTO = "/servicio-maestros/v0.0.1/tipodocumento";
    public static final String URL_PROFESIONES = "/servicio-maestros/v0.0.1/profesiones";
    public static final String URL_SINTOMAS = "/servicio-maestros/v0.0.1/sintomas";
    public static final String URL_TIPO_RESULTADO = "/servicio-maestros/v0.0.1/tiporesultado";
    public static final String URL_RIESGOS = "/servicio-maestros/v0.0.1/riesgos";
    public static final String URL_TIPOS_SEGUROS = "/servicio-maestros/v0.0.1/tiposseguros";
    public static final String URL_SIGNOS_ALARMAS_2 = "/servicio-maestros/v0.0.1/signosalarmas2";
    public static final String URL_PARENTESCOS = "/servicio-maestros/v0.0.1/parentescos";
    public static final String URL_PAISES = "/servicio-maestros/v0.0.1/paises";
    public static final String URL_TIPOS_MUESTRAS = "/servicio-maestros/v0.0.1/tiposMuestras";
    public static final String URL_SIGNOS_ALARMAS = "/servicio-maestros/v0.0.1/signosalarmas";
    public static final String URL_TIPO_CONGLOMERADO_2 = "/servicio-maestros/v0.0.1/getTipoConglomerado2/";
    public static final String URL_CONGLOMERADO = "/servicio-maestros/v0.0.1/getConglomerado/";
    public static final String URL_LOGIN = "/seguridad-covid/v0.0.1/login";
    public static final String URL_PACIENTE_CONFIRMADO = "/consulta-atencion/v0.0.1/getPacienteConfirmado/";
    public static final String URL_REGISTRO_PACIENTE = "/consulta-atencion/v0.0.1/registro-ficha-paciente";
    public static final String URL_REGISTRO_F100 = "/gestion-ficha-100/v0.0.1/registroFicha100";
    public static final String URL_REGISTRO_F200 = "/gestion-ficha-200/v0.0.1/registro-ficha-200";
    public static final String URL_REGISTRO_F300 = "/gestion-ficha-300/v0.0.1/registra-caso";
    public static final String URL_EVOLUCIONES = "/servicio-maestros/v0.0.1/evoluciones";
    public static final String URL_SOSPECHOSO = "/consulta-atencion/v0.0.1/consulta-sospechoso";
    public static final String URL_ACTUALIZAR_PACIENTE = "/consulta-atencion/v0.0.1/actualizar-ficha-paciente";

    public static class TipoResidencia {
        public static final int INFORMACION_DOMICILIO = 1;
        public static final int LUGAR_DONDE_SE_HOSPEDA = 2;
    }

    public static class PersonalSalud {
        public static final int SI = 1;
        public static final int NO = 2;
    }

    public static class TieneSintoma {
        public static final int SI = 1;
        public static final int NO = 2;
    }

    public static class Sexo {
        public static final int MASCULINO = 1;
        public static final int FEMENINO = 2;
    }

    public static class PCR {
        public static final int SI = 1;
        public static final int NO = 2;
    }

    public static class Flavors {
        public static final String MINSA = "minsa";
        public static final String INPE = "inpe";
    }

}