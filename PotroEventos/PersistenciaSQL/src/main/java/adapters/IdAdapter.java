package adapters;

public class IdAdapter {

    public static Long stringALong(String idStr){
        return Long.valueOf(idStr);
    }

    public static String LongAString(Long idLong){
        return String.valueOf(idLong);
    }

}
