package util;

import javafx.animation.AnimationTimer;

import java.util.ArrayList;

public class TickTimer {
    private static TickTimer instance;
    private final double FPS = 60.0;
    private ArrayList<Ticker> tickers;
    private ArrayList<Ticker> softDeleteTickers;
    private double lastNanoTime = System.nanoTime();
    private double time = 0;


    private TickTimer() {
        tickers = new ArrayList<>();
        softDeleteTickers = new ArrayList<>();

        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                // calculate time since last update.
                time += currentNanoTime - lastNanoTime;
                lastNanoTime = currentNanoTime;
                int passedTicks = (int) Math.floor(time * FPS / 1000000000.0);
                time -= passedTicks / FPS;
                if (passedTicks >= 1) {
                    for (Ticker remove : softDeleteTickers) {
                        tickers.removeIf(ticker1 -> ticker1.equals(remove));
                    }
                    softDeleteTickers.clear();

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
        softDeleteTickers.add(ticker);
    }

    public interface Ticker {
        void action();
    }
}
