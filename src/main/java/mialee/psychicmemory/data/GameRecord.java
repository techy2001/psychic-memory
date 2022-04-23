package mialee.psychicmemory.data;

/**
 * A simple record keeping track of a score and name of player who achieved it.
 * Stored this way to make use of a custom {@link #compareTo(GameRecord)}.
 * @param name The name of the player.
 * @param score The score they achieved.
 */
public record GameRecord(String name, int score) implements Comparable<GameRecord> {
    @Override
    public int compareTo(GameRecord o) {
        return this.score - o.score;
    }
}
