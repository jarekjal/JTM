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

    private enum State {S0, S1, S2, S3, S4, S5}
    private static State state = State.S0;
    private static List<File> randomFiles = null;
    private static int randomFilesCount;
    private static int ptr = 0;
    private static MediaPlayer player = null;
    private static Timeline tl = null;


    public void setDir(List<File> fileList) {

        Message message;
        if (player != null) {
            player.stop();
            tl.stop();
        }
        if (fileList.isEmpty()) {
            message = new Message("dir", new String[] {"Directory not set!" , ""});
            state = State.S0;
        } else {
            randomFilesCount = Properties.getInstance().getNumber();
            randomFiles = ListUtils.randomSublistOf(fileList, randomFilesCount);
            message = new Message("dir", new String[] {Properties.getInstance().getDirectory().toString(), ""+fileList.size()});
            state = State.S1;
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
                message = new Message("counter", new String[]{""+(ptr+1)+"/"+randomFilesCount});
                this.setChanged();
                this.notifyObservers(message);
                Media media = new Media(randomFiles.get(ptr).toURI().toString());
                player = new MediaPlayer(media);
                player.setOnEndOfMedia(() -> state = State.S4);
                player.play();
                //start timera
                tl = new Timeline(new KeyFrame(Duration.millis(10), ae -> updateStoper()));
                tl.setCycleCount(Timeline.INDEFINITE);
                tl.play();
                state = State.S3;
                break;
            case S3: // zatrzymanie pliku, timer zatrzymany, tytulu brak
                player.pause();
                state = State.S4;
                break;
            case S4: // zastopowanie pliku, pokazanie tytulu, sprawdzenie czy ostatni, zwiekszenie pointera, przejscie do S1/S2
                // pokazanie tytulu
                player.stop();
                tl.stop();
                message = new Message("title", new String[] {randomFiles.get(ptr).getName()});
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

    public void enterPressed() {
        switch (state){
            case S4:
                state = State.S5; // czy to potrzebne?
                player.play();
                state = State.S3;
                break;
        default:
                break;
        }
    }

    private void updateStoper() {

        Message message = new Message("timer", new Object[] {player.getCurrentTime()});
        this.setChanged();
        this.notifyObservers(message);
    }


}
