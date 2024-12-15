package com.example.platformerplain.model;

/**
 * Interface representing an observer that listens to changes in the game model.
 * Implementations of this interface can be used to react to specific events,
 * such as score changes or enemy kills.
 */
public interface GameModelObserver {

    /**
     * Notifies the observer when the score has changed.
     *
     * @param newScore the new score value after the change
     */
    void onScoreChanged(int newScore);

    /**
     * Notifies the observer when an enemy has been killed.
     *
     * @param totalKilled the total number of enemies killed so far
     */
    void onEnemyKilled(int totalKilled);
}
