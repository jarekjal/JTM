package jarekjal.jtm;

import java.time.LocalTime;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Stoper {

    private LocalTime startTime;
    private LocalTime currentTime;
    private java.time.Duration timeDiff;
    private Text text;
    private Timeline timeline;

    public Stoper(Text t) {
        text = t;
    }

    public void start() {

        startTime = LocalTime.now();
        timeline = new Timeline(new KeyFrame(Duration.millis(1), ae -> updateStoper())); // dokladnosc
        // pomiaru
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public java.time.Duration stop() {
        currentTime = LocalTime.now();
        timeline.stop();
        return timeDiff;

    }

    private void updateStoper() {
        currentTime = LocalTime.now();
        timeDiff = java.time.Duration.between(startTime, currentTime);
        text.setText(generujNapis());
    }

    private String generujNapis() {
        String mils = "";
        int milsInt = timeDiff.getNano() / 1000000;
        mils = "" + milsInt;
        if (milsInt < 100) {
            mils = "0" + mils;
        }
        if (milsInt < 10) {
            mils = "0" + mils;
        }

        long secInt = timeDiff.getSeconds() % 60;
        String seconds = "";
        if (secInt < 10) {
            seconds = "0" + secInt;
        } else {
            seconds += secInt;
        }

        String minutes = "" + timeDiff.toMinutes();

        String napis = "" + minutes + ":" + seconds + "." + mils;
        return napis;
    }

}
