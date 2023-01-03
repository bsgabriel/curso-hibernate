package curso.util;

import java.util.Arrays;
import java.util.Collection;

public class StringUtils {

    public static String stringListToString(Collection<String> collection) {
        return stringListToString(collection, ";");
    }

    public static String stringListToString(Collection<String> collection, String separator) {
        StringBuilder strReturn = new StringBuilder();
        for (String str : collection) {
            strReturn.append(str);
            strReturn.append(separator);
        }

        if (strReturn.lastIndexOf(";") == strReturn.length() - 1) {
            strReturn.deleteCharAt(strReturn.length() - 1);
        }

        return strReturn.toString();
    }

    public static Collection<String> stringToStringList(String string) {
        return stringToStringList(string, ";");
    }

    public static Collection<String> stringToStringList(String string, String separator) {
        return Arrays.asList(string.split(separator));
    }

    public static boolean isBlank(String str) {
        if (str == null)
            return true;

        if (str.isBlank())
            return true;

        return false;
    }

}
