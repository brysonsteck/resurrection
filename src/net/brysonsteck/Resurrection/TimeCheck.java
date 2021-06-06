package net.brysonsteck.Resurrection;

import java.util.concurrent.TimeUnit;

class TimeCheck {
    long millis;

    public TimeCheck(long millis) {
        this.millis = millis;
    }

    public String formatTime() {
        String formattedTime = String.format("%d hrs, %d min, %d sec",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        return formattedTime;
    }
}
