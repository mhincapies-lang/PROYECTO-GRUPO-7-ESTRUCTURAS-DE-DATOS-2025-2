import java.util.*;

public class PlanificadorSemestres {

    private MatterGraph graph;

    public PlanificadorSemestres(MatterGraph graph) {
        this.graph = graph;
    }

    /**
     * Calcula el número mínimo de semestres.
     */
    public int calcularSemestresMinimos(int maxPerSemester) {
        return planificarSemestres(maxPerSemester).size();
    }

    /**
     * Planifica semestres automáticamente sin semestres predefinidos.
     */
    public List<List<Integer>> planificarSemestres(int maxPerSemester) {
        
        Map<Integer, Matter> matters = graph.getMatters();
        List<List<Integer>> resultado = new ArrayList<>();
        Set<Integer> cursadas = new HashSet<>();
        
        // Calcular indegree inicial
        Map<Integer, Integer> indegree = calcularIndegree(matters);
        
        // Crear cola con materias disponibles (sin prerequisitos)
        ArrayQueue<Integer> cola = new ArrayQueue<>(matters.size() + 5);
        for (int id : matters.keySet()) {
            if (indegree.get(id) == 0) {
                cola.enqueue(id);
            }
        }
        
        // Planificación automática
        while (!cola.isEmpty()) {
            List<Integer> semestre = new ArrayList<>();
            
            // Llenar el semestre hasta el máximo permitido
            while (semestre.size() < maxPerSemester && !cola.isEmpty()) {
                int cur = cola.dequeue();
                semestre.add(cur);
                cursadas.add(cur);
            }
            
            resultado.add(semestre);
            
            // Reducir indegree de materias dependientes
            for (int aprobada : semestre) {
                for (Matter m : matters.values()) {
                    if (cursadas.contains(m.getId())) continue;
                    
                    List<List<Integer>> prereqs = m.getPrerequisites();
                    if (prereqs == null) continue;
                    
                    for (List<Integer> grupo : prereqs) {
                        // Manejar prerequisitos OR
                        boolean tienePrereq = false;
                        for (int prereqId : grupo) {
                            if (prereqId == aprobada) {
                                tienePrereq = true;
                                break;
                            }
                        }
                        
                        if (tienePrereq) {
                            int nuevo = indegree.get(m.getId()) - 1;
                            indegree.put(m.getId(), nuevo);
                            
                            if (nuevo == 0) {
                                cola.enqueue(m.getId());
                            }
                            break; // Solo reducir una vez por grupo OR
                        }
                    }
                }
            }
        }
        
        // Validación final
        if (cursadas.size() != matters.size()) {
            System.out.println("\n[!] ADVERTENCIA: No se cursaron todas las materias.");
            System.out.println("Cursadas: " + cursadas.size() + " de " + matters.size());
            
            // Mostrar materias no cursadas
            System.out.println("\nMaterias no cursadas:");
            for (int id : matters.keySet()) {
                if (!cursadas.contains(id)) {
                    Matter m = matters.get(id);
                    System.out.println("  - " + id + ": " + m.getName());
                    List<List<Integer>> prereqs = m.getPrerequisites();
                    if (prereqs != null) {
                        System.out.println("    Prerrequisitos: " + prereqs);
                    }
                }
            }
        }
        
        return resultado;
    }
    
    /**
     * Calcula el indegree inicial de todas las materias.
     */
    private Map<Integer, Integer> calcularIndegree(Map<Integer, Matter> matters) {
        Map<Integer, Integer> indegree = new HashMap<>();
        
        for (int id : matters.keySet()) {
            indegree.put(id, 0);
        }
        
        for (Matter m : matters.values()) {
            List<List<Integer>> prereqs = m.getPrerequisites();
            if (prereqs == null || prereqs.isEmpty()) continue;
            
            // Cada grupo de prerequisitos (lista interna) es un requisito
            // Si hay múltiples elementos en un grupo, es OR
            // Si hay múltiples grupos, todos son AND
            indegree.put(m.getId(), prereqs.size());
        }
        
        return indegree;
    }

    /**
     * Imprime el plan de semestres.
     */
    public void imprimirPlan(List<List<Integer>> plan) {
        System.out.println("============================================================");
        System.out.println("           PLAN DE ESTUDIOS - RESUMEN FINAL");
        System.out.println("============================================================\n");
        
        int totalMaterias = 0;
        
        for (int i = 0; i < plan.size(); i++) {
            System.out.println("Semestre " + (i + 1) + " (" + plan.get(i).size() + " materias)");
            System.out.println("------------------------------------------------------------");
            for (int id : plan.get(i)) {
                Matter m = graph.getMatter(id);
                System.out.println("  " + id + " - " + (m != null ? m.getName() : "Materia no encontrada"));
            }
            System.out.println();
            totalMaterias += plan.get(i).size();
        }
        
        System.out.println("============================================================");
        System.out.println("Total de semestres: " + plan.size());
        System.out.println("Total de materias: " + totalMaterias);
        System.out.println("Tiempo estimado: " + (plan.size() / 2.0) + " años");
        System.out.println("============================================================");
    }
}
