package analyzers;
import models.*;
import java.util.*;
import java.io.*;

public class QuestAnalyzer {
    private Map<String, Quest> quests;
    private List<PlayerData> collectedData;
    
    public QuestAnalyzer() {
        this.quests = new HashMap<>();
        this.collectedData = new ArrayList<>();
        initializeQuests();
    }
    
    private void initializeQuests() {
        // 4 Quests Principais (Alta Prioridade)
        quests.put("quest_fuga", new Quest(
            "quest_fuga", 
            "Fuga - Conhecendo a Base", 
            "Tutorial", 
            "Zona de Resistência (CC)"
        ));
        
        quests.put("quest_jornada_imd", new Quest(
            "quest_jornada_imd",
            "Jornada até o IMD",
            "Navegação",
            "Campus UFRN"
        ));
        
        quests.put("quest_conhecendo_imd", new Quest(
            "quest_conhecendo_imd",
            "Conhecendo o IMD",
            "Exploração",
            "Instituto Metrópole Digital"
        ));
        
        quests.put("quest_organizando_mapas", new Quest(
            "quest_organizando_mapas",
            "Organizando os Mapas",
            "Cartografia",
            "Superintendência"
        ));
    }
    
    public void analyzeQuest(String questId) {
        Quest quest = quests.get(questId);
        if (quest == null) {
            System.out.println("❌ Quest não encontrada: " + questId);
            return;
        }
        
        System.out.println("🎮 Analisando: " + quest);
        
        // Simular coleta de dados de 5 jogadores
        for (int i = 1; i <= 5; i++) {
            String playerId = "player" + i;
            simulateQuestData(playerId, questId);
        }
        
        System.out.println("   ✓ Dados coletados para " + quest.getName());
    }
    
    private void simulateQuestData(String playerId, String questId) {
        Random rand = new Random();
        
        // Quest Start
        collectedData.add(new PlayerData(
            playerId, questId,
            rand.nextDouble() * 1000, 64, rand.nextDouble() * 1000,
            "quest_start"
        ));
        
        // Checkpoints (3-5 pontos durante a quest)
        int checkpoints = 3 + rand.nextInt(3);
        for (int i = 0; i < checkpoints; i++) {
            collectedData.add(new PlayerData(
                playerId, questId,
                rand.nextDouble() * 1000, 64, rand.nextDouble() * 1000,
                "checkpoint_" + i
            ));
        }
        
        // Quest Complete
        collectedData.add(new PlayerData(
            playerId, questId,
            rand.nextDouble() * 1000, 64, rand.nextDouble() * 1000,
            "quest_complete"
        ));
    }
    
    public void generateReport() {
        try {
            FileWriter writer = new FileWriter("results/analysis_report.md");
            
            writer.write("# Relatório de Análise de Quests\n\n");
            writer.write("## Dados Gerais\n\n");
            writer.write("- **Total de eventos coletados:** " + collectedData.size() + "\n");
            writer.write("- **Quests analisadas:** 4 (Alta Prioridade)\n");
            writer.write("- **Jogadores únicos:** " + getUniquePlayersCount() + "\n\n");
            
            writer.write("## Análise por Quest\n\n");
            
            for (String questId : quests.keySet()) {
                Quest quest = quests.get(questId);
                int eventCount = countEventsForQuest(questId);
                double avgCheckpoints = calculateAvgCheckpoints(questId);
                
                writer.write(String.format("### %s\n", quest.getName()));
                writer.write(String.format("- **Área:** %s\n", quest.getArea()));
                writer.write(String.format("- **Eventos registrados:** %d\n", eventCount));
                writer.write(String.format("- **Checkpoints médios:** %.1f\n", avgCheckpoints));
                writer.write(String.format("- **Taxa de conclusão:** %.1f%%\n\n", calculateCompletionRate(questId)));
            }
            
            writer.write("## Dados Coletados (JSON)\n\n```json\n");
            for (int i = 0; i < Math.min(10, collectedData.size()); i++) {
                writer.write(collectedData.get(i).toJson() + ",\n");
            }
            writer.write("...\n```\n");
            
            writer.close();
            System.out.println("✅ Relatório gerado em results/analysis_report.md");
            
        } catch (IOException e) {
            System.out.println("❌ Erro ao gerar relatório: " + e.getMessage());
        }
    }
    
    private int getUniquePlayersCount() {
        Set<String> uniquePlayers = new HashSet<>();
        for (PlayerData data : collectedData) {
            uniquePlayers.add(data.getPlayerId());
        }
        return uniquePlayers.size();
    }
    
    private int countEventsForQuest(String questId) {
        int count = 0;
        for (PlayerData data : collectedData) {
            if (data.getQuestId().equals(questId)) {
                count++;
            }
        }
        return count;
    }
    
    private double calculateAvgCheckpoints(String questId) {
        int totalCheckpoints = 0;
        int players = 0;
        Set<String> playersInQuest = new HashSet<>();
        
        for (PlayerData data : collectedData) {
            if (data.getQuestId().equals(questId)) {
                playersInQuest.add(data.getPlayerId());
                if (data.getEventType().startsWith("checkpoint")) {
                    totalCheckpoints++;
                }
            }
        }
        
        return playersInQuest.size() > 0 ? (double) totalCheckpoints / playersInQuest.size() : 0;
    }
    
    private double calculateCompletionRate(String questId) {
        int started = 0, completed = 0;
        
        for (PlayerData data : collectedData) {
            if (data.getQuestId().equals(questId)) {
                if (data.getEventType().equals("quest_start")) started++;
                if (data.getEventType().equals("quest_complete")) completed++;
            }
        }
        
        return started > 0 ? (completed * 100.0 / started) : 0;
    }
}