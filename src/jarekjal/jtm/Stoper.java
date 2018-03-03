package jarekjal.jtm;

import java.time.LocalTime;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Stoper {

    private LocalTime startTime;
    private java.time.Duration timeDiff;
    private java.time.Duration cumulativeDiff = java.time.Duration.ZERO;
    private Timeline timeline;

    public Stoper(){
        timeline = new Timeline(new KeyFrame(Duration.millis(1), ae -> updateStoper())); // dokladnosc pomiaru
        timeline.setCycleCount(Timeline.INDEFINITE);
    }


    public void start() {
        startTime = LocalTime.now();
        timeline.play();
    }

    public void stop() {
        timeline.stop();
    }


    public void pause() {
        timeline.pause();
        cumulativeDiff = timeDiff.plus(cumulativeDiff);
        timeDiff = java.time.Duration.ZERO;
    }


    private void updateStoper() {
        timeDiff = java.time.Duration.between(startTime, LocalTime.now());
    }

    public String getDurString(){

        java.time.Duration timeToShow = cumulativeDiff.plus(timeDiff);
        String mils = "";
        int milsInt = timeToShow.getNano() / 1_000_000;
        mils = "" + milsInt;
        if (milsInt < 100) {
            mils = "0" + mils;
        }
        if (milsInt < 10) {
            mils = "0" + mils;
        }

        long secInt = timeToShow.getSeconds() % 60;
        String seconds = "";
        if (secInt < 10) {
            seconds = "0" + secInt;
        } else {
            seconds += secInt;
        }

        String minutes = "" + timeToShow.toMinutes();

        return "" + minutes + ":" + seconds + "." + mils;
    }
}
