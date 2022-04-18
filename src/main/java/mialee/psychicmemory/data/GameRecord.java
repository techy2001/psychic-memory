package mialee.psychicmemory.data;

public record GameRecord(String name, int score) implements Comparable<GameRecord> {
    @Override
    public int compareTo(GameRecord o) {
        return this.score - o.score;
    }
}
