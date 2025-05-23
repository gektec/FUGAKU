package com.fugaku.platformer.model;

import com.fugaku.platformer.data.Constants;
import com.fugaku.platformer.data.LevelData;
import com.fugaku.platformer.entities.Entity;
import com.fugaku.platformer.entities.EntityFactory;
import com.fugaku.platformer.entities.EntityType;
import com.fugaku.platformer.entities.moveable.Enemy;
import com.fugaku.platformer.entities.tile.Coin;
import com.fugaku.platformer.entities.tile.Ladder;
import com.fugaku.platformer.entities.tile.Spike;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.input.KeyCode;

/**
 * Initializes the level by creating entities based on the specified layout.
 * Responsible for generating the player, platforms, enemies, spikes, ladders, and goals.
 * @author Changyu Li, Zelin Xia
 * @date 2024/11/23
 */
public class LevelInitializer {
    private final HashMap<KeyCode, Boolean> keys;
    private final Pane gameRoot;
    private Entity player;

    private final ArrayList<Entity> collidableMap;
    private final ArrayList<Enemy> enemyMap;
    private final ArrayList<Spike> spikeMap;
    private final ArrayList<Ladder> ladderMap;
    private final ArrayList<Coin> coinMap;

    /**
     * Constructs a LevelInitializer with the specified parameters.
     * @param keys the hashmap to store key states.
     * @param gameRoot the pane where game entities will be rendered.
     * @param collidableMap the list of collidable entities in the level.
     * @param enemyMap the list of enemies in the level.
     * @param spikeMap the list of spikes in the level.
     * @param ladderMap the list of ladders in the level.
     */
    public LevelInitializer(HashMap<KeyCode, Boolean> keys, Pane gameRoot,
                            ArrayList<Entity> collidableMap, ArrayList<Enemy> enemyMap,
                            ArrayList<Spike> spikeMap, ArrayList<Ladder> ladderMap, ArrayList<Coin> coinMap) {
        this.keys = keys;
        this.gameRoot = gameRoot;
        this.collidableMap = collidableMap;
        this.enemyMap = enemyMap;
        this.spikeMap = spikeMap;
        this.ladderMap = ladderMap;
        this.coinMap = coinMap;
    }

    /**
     * Generates the level entities based on the current level layout.
     * Resets previous state and creates new entities accordingly.
     * @param currentLevel the index of the current level to generate.
     * @return the player entity.
     */
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
                        if (i > 0 && LevelData.Levels[currentLevel][i - 1].charAt(j) == 'H' && LevelData.Levels[currentLevel][i + 1].charAt(j) == 'H') {
                            adjacencyCode = (int)(Math.random() * 2) + 1; // 1 or 2
                        } else if (i < LevelData.Levels[currentLevel].length - 1 && LevelData.Levels[currentLevel][i + 1].charAt(j) == 'H') {
                            adjacencyCode = 0;
                        } else {
                            adjacencyCode = 3;
                        }
                        Ladder ladder = (Ladder) createEntity(EntityType.LADDER, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE, adjacencyCode);
                        ladderMap.add(ladder);
                        break;
                    case 'G':
                        Entity goal = createEntity(EntityType.GOAL, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE, 50);
                        collidableMap.add(goal);
                        break;
                    case 'E':
                        Enemy enemy = (Enemy) createEntity(EntityType.ENEMY, j * Constants.TILE_SIZE + (Constants.TILE_SIZE - Constants.ENEMY_SIZE)/2, i * Constants.TILE_SIZE+ (Constants.TILE_SIZE - Constants.ENEMY_SIZE)/2, Constants.ENEMY_SIZE, Constants.ENEMY_SIZE, 70);
                        enemyMap.add(enemy);
                        break;
                    case 's':
                        adjacencyCode = calculateAdjacencyCode(LevelData.Levels[currentLevel], i, j, 'S');
                        createEntity(EntityType.SPIKE, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE, adjacencyCode);
                        break;
                    case 'S':
                        adjacencyCode = calculateAdjacencyCode(LevelData.Levels[currentLevel], i, j, 's') + 16;
                        Spike spikeBody = (Spike) createEntity(EntityType.SPIKE, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.SPIKE_SIZE, Constants.SPIKE_SIZE, adjacencyCode);
                        spikeMap.add(spikeBody);
                        break;
                    case 'D':
                        createEntity(EntityType.DECORATION, j * Constants.TILE_SIZE, i * Constants.TILE_SIZE, Constants.TILE_SIZE, Constants.TILE_SIZE, 0);
                        break;
                    case 'C':
                        Coin coin = (Coin) createEntity(EntityType.COIN, j * Constants.TILE_SIZE + (Constants.TILE_SIZE - Constants.COIN_SIZE)/2, i * Constants.TILE_SIZE+ (Constants.TILE_SIZE - Constants.COIN_SIZE)/2, Constants.COIN_SIZE, Constants.COIN_SIZE, 0);
                        coinMap.add(coin);
                        break;
                }
            }
        }
        return player;
    }

    /**
     * Creates an entity of the specified type with the given parameters.
     * @param type the type of the entity to create.
     * @param x the x-coordinate position of the entity.
     * @param y the y-coordinate position of the entity.
     * @param w the width of the entity.
     * @param h the height of the entity.
     * @param index the index used for certain entities.
     * @return the created entity or null if creation failed.
     */
    private Entity createEntity(EntityType type, int x, int y, int w, int h, int index) {
        Entity entity = EntityFactory.createEntity(type, x, y, w, h, index);
        if (GameModel.isDebugMode()) gameRoot.getChildren().add(entity.hitBox());
        else gameRoot.getChildren().add(entity.canvas());
        return entity;
    }

    /**
     * Calculates the adjacency code for the given position in the level based on neighboring tiles.
     * @param level the current level's string array.
     * @param i the row index of the specific tile.
     * @param j the column index of the specific tile.
     * @param target the character representing the target tile type.
     * @return the calculated adjacency code based on neighboring tiles.
     */
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
