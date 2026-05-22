package adapters;

public class IdAdapter {

    public static Long stringALong(String idStr){
        return (idStr == null) ? null : Long.valueOf(idStr);
    }

    public static String LongAString(Long idLong){
        return (idLong== null) ? null : String.valueOf(idLong);
    }

}
