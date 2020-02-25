package domotix.controller.util;

public class StringUtil {
    public static String indent(String s) {
        return s.replaceAll("(?m)^", "\t");
    }

    public static String indent(String s, int n) {
        if (n <= 0) return s;
        for (int i = 0; i < n; i++) {
            s = indent(s);
        }
        return s;
    }

    public static String removeLast(String s, int n) {
        if (n <= 0 || n > s.length()) return s;
        return s.substring(0, s.length() - n);
    }

    public static String removeLast(String s) {
        return removeLast(s, 1);
    }

    public static String componiNome(String fantasia, String categoria) {
        return fantasia + "_" + categoria;
    }

    public static String componiPercorso(String base, String ...figli) {
        String ret = base;
        for (String s : figli){
            ret += " > " + s;
        }
        return ret;
    }
}
