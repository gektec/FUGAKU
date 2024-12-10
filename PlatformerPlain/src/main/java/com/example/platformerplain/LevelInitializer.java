package com.example.platformerplain;

import com.example.platformerplain.entities.*;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;

public class LevelInitializer {
    private HashMap<KeyCode, Boolean> keys;
    private Pane gameRoot;
    private Pane uiRoot;
    private Pane backgroundRoot;
    private Entity player;

    private ArrayList<Entity> collidableMap;
    private ArrayList<Enemy> enemyMap;
    private ArrayList<Spike> spikeMap;
    private ArrayList<Ladder> ladderMap;

    public LevelInitializer(HashMap<KeyCode, Boolean> keys, Pane gameRoot, Pane uiRoot, Pane backgroundRoot,
                            ArrayList<Entity> collidableMap, ArrayList<Enemy> enemyMap,
                            ArrayList<Spike> spikeMap, ArrayList<Ladder> ladderMap) {
        this.keys = keys;
        this.gameRoot = gameRoot;
        this.uiRoot = uiRoot;
        this.backgroundRoot = backgroundRoot;
        this.collidableMap = collidableMap;
        this.enemyMap = enemyMap;
        this.spikeMap = spikeMap;
        this.ladderMap = ladderMap;
    }

    public Entity generateLevel(int currentLevel) {
        keys.clear();
        collidableMap.clear();
        enemyMap.clear();

        int adjacencyCode;
        for (int i = 0; i < LevelData.Levels[currentLevel].length; i++) {
            String line = LevelData.Levels[currentLevel][i];
            for (int j = 0; j < line.length(); j++) {
                switch (line.charAt(j)) {
                    case 'P':
                        player = createEntity(EntityType.PLAYER, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT, 0);
                        break;
                    case 'M':
                        adjacencyCode = calculateAdjacencyCode(LevelData.Levels[currentLevel], i, j, 'M');
                        Entity platform = createEntity(EntityType.PLATFORM, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE, adjacencyCode);
                        collidableMap.add(platform);
                        break;
                    case 'H':
                        // Additional logic for 'H'
                        break;
                    case 'G':
                        Entity goal = createEntity(EntityType.GOAL, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE, 50);
                        collidableMap.add(goal);
                        break;
                    case 'E':
                        Enemy enemy = (Enemy) createEntity(EntityType.ENEMY, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.PLAYER_SIZE, Constants.PLAYER_SIZE, 70);
                        enemyMap.add(enemy);
                        break;
                    case 's':
                        adjacencyCode = calculateAdjacencyCode(LevelData.Levels[currentLevel], i, j, 'S');
                        Spike spike = (Spike) createEntity(EntityType.SPIKE, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE, adjacencyCode);
                        spikeMap.add(spike);
                        break;
                    case 'S':
                        adjacencyCode = calculateAdjacencyCode(LevelData.Levels[currentLevel], i, j, 's') + 16;
                        Spike spikeBody = (Spike) createEntity(EntityType.SPIKE, j * Constants.TILE_SIZE + 2, i * Constants.TILE_SIZE + 2, Constants.TILE_SIZE - 4, Constants.TILE_SIZE - 4, adjacencyCode);
                        spikeMap.add(spikeBody);
                        break;
                    case 'D':
                        createEntity(EntityType.DECORATION, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE, 0);
                        break;
                }
            }
        }
        return player;
    }

    private Entity createEntity(EntityType type, int x, int y, int w, int h, int index) {
        Entity entity = EntityFactory.createEntity(type, x, y, w, h, index);
        if (entity != null) {
            if (Main.getInstance().isDebugMode) gameRoot.getChildren().add(entity.hitBox());
            else gameRoot.getChildren().add(entity.canvas());
        }
        return entity;
    }

    private int calculateAdjacencyCode(String[] level, int i, int j, char target) {
        int adjacencyCode = 0;
        if (i > 0 && level[i - 1].charAt(j) == target) {
            adjacencyCode += 1; // got a neighbor above
        }
        if (j < level[i].length() - 1 && level[i].charAt(j + 1) == target) {
            adjacencyCode += 2; // got a neighbor to the right
        }
        if (i < level.length - 1 && level[i + 1].charAt(j) == target) {
            adjacencyCode += 4; // got a neighbor below
        }
        if (j > 0 && level[i].charAt(j - 1) == target) {
            adjacencyCode += 8; // got a neighbor to the left
        }
        return adjacencyCode;
    }
}
