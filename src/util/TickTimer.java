package util;

import javafx.animation.AnimationTimer;

import java.util.ArrayList;

public class TickTimer {
    private static TickTimer instance;
    private final double FPS = 60.0;
    private ArrayList<Ticker> tickers;
    private AnimationTimer animationTimer;
    private int passedTicks = 0;
    private double lastNanoTime = System.nanoTime();
    private double time = 0;


    private TickTimer() {
        tickers = new ArrayList<>();

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                // calculate time since last update.
                time += currentNanoTime - lastNanoTime;
                lastNanoTime = currentNanoTime;
                passedTicks = (int) Math.floor(time * FPS / 1000000000.0);
                time -= passedTicks / FPS;
                if (passedTicks >= 1) {
                    for (Ticker ticker : tickers) {
                        ticker.action();
                    }
                }
            }
        };
        animationTimer.start();
    }

    public static TickTimer getInstance() {
        if (instance == null) {
            instance = new TickTimer();
        }

        return instance;
    }

    public void addTicker(Ticker ticker) {
        tickers.add(ticker);
    }

    public void removeTicker(Ticker ticker) {
        tickers.removeIf(ticker1 -> ticker1.equals(ticker));
    }

    public interface Ticker {
        void action();
    }
}
