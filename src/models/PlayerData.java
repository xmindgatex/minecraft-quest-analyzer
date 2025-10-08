package models;
import java.time.LocalDateTime;

public class PlayerData {
    private String playerId;
    private String questId;
    private double posX, posY, posZ;
    private LocalDateTime timestamp;
    private String eventType; // quest_start, quest_complete, checkpoint, etc
    
    public PlayerData(String playerId, String questId, double posX, double posY, double posZ, String eventType) {
        this.playerId = playerId;
        this.questId = questId;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.eventType = eventType;
        this.timestamp = LocalDateTime.now();
    }
    
    // Getters
    public String getPlayerId() { return playerId; }
    public String getQuestId() { return questId; }
    public double getPosX() { return posX; }
    public double getPosY() { return posY; }
    public double getPosZ() { return posZ; }
    public String getEventType() { return eventType; }
    public LocalDateTime getTimestamp() { return timestamp; }
    
    public String toJson() {
        return String.format(
            "{\"playerId\":\"%s\",\"questId\":\"%s\",\"position\":{\"x\":%.2f,\"y\":%.2f,\"z\":%.2f},\"eventType\":\"%s\",\"timestamp\":\"%s\"}",
            playerId, questId, posX, posY, posZ, eventType, timestamp.toString()
        );
    }
}