package pattern.java.p13_visitor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileFindVisitor extends Visitor {
    private String fileType;
    private ArrayList<File> found = new ArrayList<>();

    public FileFindVisitor(String fileType) {
        this.fileType = fileType;
    }

    @Override
    public void visit(File file) {
        if (file.getName().endsWith(fileType)) {
            found.add(file);
        }
    }

    @Override
    public void visit(Directory directory) {
        Iterator<Entry> it = directory.iterator();
        while (it.hasNext()) {
            Entry entry = it.next();
            entry.accept(this);
        }
    }

    public Iterator<File> getFoundFiles() {
        return found.iterator();
    }
}
