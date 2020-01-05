package src.Dungeon;

public final class Main {
    private Main() {
    }
     /**
     * Main class.
     * @param args arguments
     **/
    public static void main(String[] args) {
        Level level = new Level();
        System.out.println(level.getLevelDescription());
    }
}


