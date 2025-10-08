import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Sistema de Análise de Quests - Minecraft Educacional ===\n");
        
        // Inicializar o sistema
        QuestAnalyzer analyzer = new QuestAnalyzer();
        
        // Definir as 4 quests principais
        String[] priorityQuests = {
            "quest_fuga",
            "quest_jornada_imd", 
            "quest_conhecendo_imd",
            "quest_organizando_mapas"
        };
        
        // Simular coleta de dados
        System.out.println("📊 Iniciando coleta de dados...\n");
        for (String questId : priorityQuests) {
            analyzer.analyzeQuest(questId);
        }
        
        // Gerar relatório
        System.out.println("\n📈 Gerando relatório de análise...");
        analyzer.generateReport();
        
        System.out.println("\n✅ Análise concluída! Verifique a pasta 'results/'");
    }
}