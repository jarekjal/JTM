package jarekjal.jtm;

import jarekjal.utils.FileUtils;
import jarekjal.utils.ListUtils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.io.File;
import java.util.List;
import java.util.Observable;
import java.util.function.Predicate;

/**
 * Created by ejarjal on 2018-02-22.
 */
public class Model extends Observable {

    private enum State {S0, S1, S2, S3, S4}
    private static State state = State.S0;
    private static File dir = null;
    private static List<File> randomFiles = null;
    private static final int DEFAULT_COUNT = 7;
    private static int randomFilesCount = DEFAULT_COUNT;
    private static int ptr = 0;
    private static Predicate<File> isMP3 = (File f) -> f.toString().toLowerCase().endsWith(".mp3");
    private static MediaPlayer player = null;
    Timeline tl = null;

    public void setDir(File dir) {
        Model.dir = dir;
        Message message;
        if (player != null) {
            player.stop();
            message = new Message("timer", new String[] {"stop"});
            this.setChanged();
            this.notifyObservers(message);
        }
        List<File> fileList = FileUtils.flatFileTree(this.dir, isMP3);
        System.out.println("Odfiltrowano: " + fileList.size());

        if (fileList.isEmpty()) {
            message = new Message("dir", new String[] {"Directory not set!" , ""});
            this.state = State.S0;
        } else {
            randomFilesCount = fileList.size()> DEFAULT_COUNT ? DEFAULT_COUNT : fileList.size();
            this.randomFiles = ListUtils.randomSublistOf(fileList, randomFilesCount);
            message = new Message("dir", new String[] {this.dir.toString(), ""+fileList.size()});
            this.state = State.S1;
        }
        this.setChanged();
        this.notifyObservers(message);

    }


    public void spacePressed() {
        Message message;
        switch (state){
            case S0: // po wlaczeniu, niewybrany katalog
                break;
            case S1: // katalog wybrany, stworzona lista wylosowanych plikow, ustawiony pointer na pierwszym utworze
                ptr = 0;
                //wyzerowanie timera
                message = new Message("clear", null);
                this.setChanged();
                this.notifyObservers(message);
                state = State.S2;
                break;
            case S2:// odgrywanie pliku, timer uruchomiony
                message = new Message("clear", null);
                this.setChanged();
                this.notifyObservers(message);
                Media media = new Media(randomFiles.get(ptr).toURI().toString());
                player = new MediaPlayer(media);
                player.play();
                //start timera
                tl = new Timeline(new KeyFrame(Duration.millis(10), ae -> updateStoper()));
                tl.setCycleCount(Timeline.INDEFINITE);
                tl.play();
                state = State.S3;
                break;
            case S3: // zatrzymanie pliku, timer zatrzymany, tytulu brak
                player.stop();
                // stop timera
                tl.stop();
                state = State.S4;
                break;
            case S4: // pokazanie tytulu, sprawdzenie czy ostatni, zwiekszenie pointera, przejscie do S1/S2
                // pokazanie tytulu
                message = new Message("title", new String[] {randomFiles.get(ptr).getName().toString()});
                this.setChanged();
                this.notifyObservers(message);
                ptr++;
                if (ptr > randomFiles.size()-1) {
                    state = State.S1;
                } else {
                    state = State.S2;
                }
                break;
            default:
                break;
        }
    }

    private void updateStoper() {
        Message message = new Message("timer", new String[] {""+player.getCurrentTime().toMillis()});
        this.setChanged();
        this.notifyObservers(message);
    }


}
