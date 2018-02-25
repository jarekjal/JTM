package jarekjal.jtm;

import jarekjal.utils.FileUtils;
import jarekjal.utils.ListUtils;
import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.function.Predicate;

/**
 * Created by ejarjal on 2018-02-22.
 */
public class Model extends Observable {

    private enum State {S0, S1, S2, S3, S4, S5, S6}
    private State state = State.S0;
    private static File dir = null;
    private static List<File> randomFiles = null;
    private static final int DEFAULT_COUNT = 7;
    private static int randomFilesCount = DEFAULT_COUNT;
    private static Predicate<File> isMP3 = (File f) -> f.toString().toLowerCase().endsWith(".mp3");



    public void setDir(File dir) {
        Model.dir = dir;

        List<File> fileList = FileUtils.flatFileTree(this.dir, isMP3);
        System.out.println("odfiltrowano: " + fileList.size());
        if (fileList.isEmpty()) {
            this.state = State.S0;
            this.setChanged();
            this.notifyObservers();


        } else {
            randomFilesCount = fileList.size()> DEFAULT_COUNT ? DEFAULT_COUNT : fileList.size();
            this.randomFiles = ListUtils.randomSublistOf(fileList, randomFilesCount);
            this.state = State.S1;
            this.setChanged();
            this.notifyObservers();
        }


    }




}
