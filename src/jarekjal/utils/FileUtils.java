package jarekjal.utils;

/**
 * Created by ejarjal on 2018-02-22.
 */
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FileUtils {

    private static List<File> result;

    private static List<File> flatFileTreeRecurently(File startDir) {

        File[] files = startDir.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                flatFileTreeRecurently(f);
            } else {
                result.add(f);
            }
        }
        return result;
    }

//testowa wersja
    private static List<File> flatFileTreeRecurently2(File startDir, Predicate<File> condition) {

        File[] files = startDir.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                flatFileTreeRecurently2(f, condition);
            } else {
                if (condition.test(f)) {
                    result.add(f);
                }
            }
        }
        return result;
    }


    public static List<File> flatFileTree(File startDir, Predicate<File> condition) {

        if (startDir == null | !startDir.exists() | !startDir.isDirectory())
            throw new IllegalArgumentException("Starting directory must exist");

        result = new ArrayList<>();
        return flatFileTreeRecurently2(startDir, condition);
    }

    public static List<File> flatFileTree(File startDir) {

        return flatFileTree(startDir, (File f) -> true);
    }

    public static void main(String[] args) {

        File f = new File("C:\\Users\\ejarjal\\Dropbox\\jarek\\Aranze");
        System.out.println(flatFileTree(f));
        System.out.println(flatFileTree(f).size());
        f = new File("C:\\Users\\ejarjal\\Dropbox\\jarek\\FakeMp3");
        System.out.println(flatFileTree(f));
        System.out.println(flatFileTree(f).size());
        f = new File("C:\\Users\\ejarjal\\Dropbox\\jarek\\Aranze");
        List<File> l = flatFileTree(f);
        System.out.println(l.get(0));
        f = new File("C:\\Users\\ejarjal\\Dropbox\\jarek\\FakeMp3");
        System.out.println(flatFileTree(f));
        System.out.println(flatFileTree(f).size());
        System.out.println(l.get(0));
    }

}
