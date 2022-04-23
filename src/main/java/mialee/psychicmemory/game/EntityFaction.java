package mialee.psychicmemory.game;

/**
 * The faction an entity belongs to.
 * Each has an accompanying arraylist in {@link mialee.psychicmemory.game.EntityBank}, except for Player which is stored directly in {@link mialee.psychicmemory.game.World}.
 */
public enum EntityFaction {
    PLAYER,
    ENEMY,
    PLAYER_BULLET,
    ENEMY_BULLET,
    GRAPHIC
}
