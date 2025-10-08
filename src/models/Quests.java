package models;

public class Quest {
    private String id;
    private String name;
    private String difficulty;
    private String[] objectives;
    private String area;
    
    public Quest(String id, String name, String difficulty, String area) {
        this.id = id;
        this.name = name;
        this.difficulty = difficulty;
        this.area = area;
    }
    
    // Getters e Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDifficulty() { return difficulty; }
    public String getArea() { return area; }
    
    @Override
    public String toString() {
        return String.format("Quest: %s [%s] - Área: %s", name, difficulty, area);
    }
}