package refactoring.java.r02_rcf.case2;

import org.springframework.context.event.SmartApplicationListener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        try {
            SimpleDatabase db = new SimpleDatabase(4,
                    new FileReader("C:\\Projects\\IdeaProjects\\JavaStudy\\src\\main\\java\\refactoring\\java\\r02_rcf\\case2\\dbfile.txt"));
            Iterator<String> it = db.iterator();
            while (it.hasNext()) {
                String key = it.next();
                System.out.println("KEY: \"" + key + "\"");
                System.out.println("VALUE : \"" + db.getValue(key) + "\"");
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
