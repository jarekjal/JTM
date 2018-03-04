package jarekjal.jtm;

import jarekjal.utils.ListUtils;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * Created by ejarjal on 2018-02-22.
 */
public class Model extends Observable {

    private enum State {S0, S1, S2, S3, S4}
    private static State state = State.S0;
    private static List<File> randomFiles = null;
    private static int randomFilesCount;
    private static int ptr = 0;
    private static MediaPlayer player = null;
    private static Stoper stoper = null;
    private static Timeline tl = null;
    private static boolean EOM = false;
    private static Map<File, java.time.Duration> fileDurationMap = new HashMap<>();


    public void setDir(List<File> fileList) {

        Message message;
        fileDurationMap.clear();
        if (player != null) {
            player.stop();
            tl.stop();
            stoper.stop();
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
                if (!fileDurationMap.isEmpty()) {
                    showList();
                }
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
                player.setOnEndOfMedia(() -> {
                    state = State.S4;
                    stoper.stop();
                    EOM = true;
                });
                EOM = false;
                player.play();
                stoper = new Stoper();
                stoper.start();
                //start timera
                tl = new Timeline(new KeyFrame(Duration.millis(10), ae -> updateStoper()));
                tl.setCycleCount(Timeline.INDEFINITE);
                tl.play();
                state = State.S3;
                break;
            case S3: // zatrzymanie pliku, timer zatrzymany, tytulu brak
                player.pause();
                stoper.pause();
                state = State.S4;
                break;
            case S4: // zastopowanie pliku, pokazanie tytulu, sprawdzenie czy ostatni, zwiekszenie pointera, przejscie do S1/S2
                // pokazanie tytulu
                player.stop();
                stoper.stop();
                tl.stop();
                java.time.Duration duration = stoper.getDuration();
                System.out.println("Duration to save: " + duration);
                fileDurationMap.put(randomFiles.get(ptr), duration);
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

    private void showList() {
        Message message = new Message("list", new Object[] {fileDurationMap});
        this.setChanged();
        this.notifyObservers(message);
    }

    public void enterPressed() {
        switch (state){
            case S4:
                if (!EOM) {
                    player.play();
                    stoper.start();
                    state = State.S3;
                }
                break;
        default:
                break;
        }
    }

    private void updateStoper() {
        Message message = new Message("timer", new Object[] {stoper.getDurString()});
        this.setChanged();
        this.notifyObservers(message);
    }

}
