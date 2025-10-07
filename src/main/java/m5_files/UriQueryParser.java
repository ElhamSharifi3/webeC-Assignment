package m5_files;

import java.util.*;

public class UriQueryParser {

    public static void main(String[] args) {
        testParse("a=1"     ,"{a=[1]}");
        testParse("a="      ,"{a=[]}");
        testParse("a"       ,"{a=[]}");
        testParse("="       ,"{}");
        testParse(""        ,"{}");
        testParse("a=1=2"   ,"{a=[1=2]}");
        testParse("a=1&b=2" ,"{a=[1], b=[2]}");
        testParse("a=1&a=2" ,"{a=[1, 2]}");
    }

    private static void testParse(String queryString, String mapString) {
        final String actual = UriQueryParser.parse(queryString).toString();
        if (actual.equals(mapString)) {
            System.out.print(".");
        } else {
            System.out.println("\n query " + queryString + " expected " + mapString + " but was " + actual);
        }
    }

    public static Map<String, List<String>> parse(String queryString) {
        var result = new LinkedHashMap<String, List<String>>(16);
        for (String param : queryString.split("&")) {
            final String[] keyValuePair = param.split("=",2);
            String key   = keyValuePair[0];
            String value = keyValuePair.length > 1 ? keyValuePair[1] : "";
            if (key.isEmpty()) {
                continue;
            }
            List<String> oldValues = result.getOrDefault(key, new LinkedList<>());
            oldValues.addLast(value);
            result.put(key, oldValues);
        }
        return result;
    }
}
