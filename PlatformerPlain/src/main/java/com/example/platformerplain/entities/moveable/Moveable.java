package com.example.platformerplain.entities.moveable;

import com.example.platformerplain.entities.Entity;
import com.example.platformerplain.entities.EntityType;
import javafx.scene.Node;

/**
 * <h3>PlatformerPlain</h3>
 *
 * @author Changyu Li
 * @description <p>Composite node</p>
 * @date 2024-12-15 18:58
 **/
abstract class Moveable extends Entity {


    /**
     * @return
     */
    @Override
    protected boolean isAnimated() {
        return true;
    }


}
