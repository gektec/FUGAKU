package com.example.platformerplain.model;

public interface GameModelObserver {
    void onScoreChanged(int newScore);
    void onEnemyKilled(int totalKilled);
}
