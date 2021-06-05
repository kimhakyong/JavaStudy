package refactoring.java.r02_rcf.case2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleDatabase {
    private Map<String, String> _map = new HashMap<String, String>();

    // before
    public SimpleDatabase(int type, Reader r) throws IOException {
        if (type == 1) constructType1(r);
        else if (type == 2) constructType2(r);
        else if (type == 3) constructType3(r);
        else if (type == 4) constructType4(r);
    }

    public void constructType1(Reader r1) throws IOException {
        BufferedReader r2 = new BufferedReader(r1);
        boolean flag = false;
        String tmp;

        while (!flag) {
            tmp = r2.readLine();
            if (tmp == null) {
                flag = true;
            } else {
                boolean flag2 = true;
                StringBuffer s1 = new StringBuffer();
                StringBuffer s2 = new StringBuffer();
                for (int i = 0; i < tmp.length(); i++) {
                    char tmp2 = tmp.charAt(i);
                    if (flag2) {
                        if (tmp2 == '=') {
                            flag2 = false;
                        } else {
                            s1.append(tmp2);
                        }
                    } else {
                        s2.append(tmp2);
                    }
                }
                String ss1 = s1.toString();
                String ss2 = s2.toString();
                _map.put(ss1, ss2);
            }
        }
    }

    // after 1
    public void constructType2(Reader r) throws IOException {
        BufferedReader reader = new BufferedReader(r);

        while (true) {
            String line = reader.readLine();

            if (line == null) break;

            int equalIndex = line.indexOf("=");
            if (equalIndex > 0) {
                String key = line.substring(0, equalIndex);
                String value = line.substring(equalIndex + 1, line.length());
                _map.put(key, value);
            }
        }
    }

    // after 2
    public void constructType3(Reader r) throws IOException {
        BufferedReader reader = new BufferedReader(r);

        while (true) {
            String line = reader.readLine();

            if (line == null) break;

            String[] values = line.split("=");
            if (values.length == 2) {
                _map.put(values[0], values[1]);
            }
        }
    }

    // after 3
    public void constructType4(Reader r) throws IOException {
        BufferedReader reader = new BufferedReader(r);

        Pattern _pattern = Pattern.compile("([^=]+)=(.*)");
        while (true) {
            String line = reader.readLine();
            if (line == null) break;

            Matcher matcher = _pattern.matcher(line);
            if (matcher.matches()) {
                String key = matcher.group(1);
                String value = matcher.group(2);
                _map.put(key, value);
            }
        }
    }

    public void putValue(String key, String value) {
        _map.put(key, value);
    }

    public String getValue(String key) {
        return _map.get(key);
    }

    public Iterator<String> iterator() {
        return _map.keySet().iterator();
    }
}
