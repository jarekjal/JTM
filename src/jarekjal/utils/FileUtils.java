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


    private static List<File> flatFileTreeRecurently(File startDir, Predicate<File> condition) {

        File[] files = startDir.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                flatFileTreeRecurently(f, condition);
            } else {
                if (condition.test(f)) {
                    result.add(f);
                }
            }
        }
        return result;
    }


    public static List<File> flatFileTree(File startDir, Predicate<File> condition) {

        if (startDir == null || !startDir.exists() || !startDir.isDirectory())
            throw new IllegalArgumentException("Starting directory must exist");

        result = new ArrayList<>();
        return flatFileTreeRecurently(startDir, condition);
    }

    public static List<File> flatFileTree(File startDir) {

        return flatFileTree(startDir, (File f) -> true);
    }

    public static void main(String[] args) {

    }

}
