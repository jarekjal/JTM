package jarekjal.jtm;

import jarekjal.utils.FileUtils;
import jarekjal.utils.ListUtils;
import javafx.scene.media.AudioClip;

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
    private static int ptr = 0;
    private static Predicate<File> isMP3 = (File f) -> f.toString().toLowerCase().endsWith(".mp3");
    AudioClip clip = null;

    public void setDir(File dir) {
        Model.dir = dir;

        List<File> fileList = FileUtils.flatFileTree(this.dir, isMP3);
        System.out.println("Odfiltrowano: " + fileList.size());
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


    public void spacePressed() {
        switch (state){
            case S0: // wybieranie katalogu
                break;
            case S1: // przed rozpoczeciem gry
                clip = new AudioClip(randomFiles.get(ptr).toURI().toString());
                clip.play();
                ptr++; // ustaw na nastepny z listy (bez sprawdzania czy istnieje)
                state = State.S2;
                break;
            case S2:
                if (ptr > randomFiles.size()-1) {
                    clip.stop();
                    ptr = 0;
                    state = State.S1;
                } else {
                    clip.stop();
                    state = State.S1;
                }
                break;
        }

    }

}
