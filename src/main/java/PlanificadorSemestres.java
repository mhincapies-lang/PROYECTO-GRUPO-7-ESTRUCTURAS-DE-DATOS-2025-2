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

        // ============================
        // Calcular indegree inicial
        // ============================
        Map<Integer, Integer> indegree = calcularIndegree(matters);

        // =======================================================
        // Construir dependencias inversas sin tocar clases externas
        // dependientes[x] = materias que dependen de x
        // =======================================================
        Map<Integer, List<Integer>> dependientes = new HashMap<>();
        for (int id : matters.keySet()) dependientes.put(id, new ArrayList<>());

        for (Matter m : matters.values()) {
            List<List<Integer>> prereqs = m.getPrerequisites();
            if (prereqs == null) continue;

            for (List<Integer> grupo : prereqs) {
                for (int pid : grupo) {
                    // Asegurarse de inicializar lista por si el pid no existe en matters (robusto)
                    dependientes.computeIfAbsent(pid, k -> new ArrayList<>()).add(m.getId());
                }
            }
        }

        // ============================
        // Materias disponibles inicialmente (indegree == 0)
        // ============================
        ArrayQueue<Integer> cola = new ArrayQueue<>(matters.size() + 5);
        for (int id : matters.keySet()) {
            if (indegree.getOrDefault(id, 0) == 0) {
                cola.enqueue(id);
            }
        }

        // ============================
        // Planificación por semestres (Kahn + llenar por capacidad)
        // ============================
        while (!cola.isEmpty()) {

            List<Integer> semestre = new ArrayList<>();

            // Llenar semestre hasta el máximo permitido
            while (semestre.size() < maxPerSemester && !cola.isEmpty()) {
                int cur = cola.dequeue();
                if (!cursadas.contains(cur)) {
                    semestre.add(cur);
                    cursadas.add(cur);
                }
            }

            resultado.add(semestre);

            // Reducir indegree de materias dependientes de
            // cada materia del semestre (re-evaluando grupos AND/OR)
            for (int aprobada : semestre) {

                List<Integer> deps = dependientes.get(aprobada);
                if (deps == null) continue;

                for (int depId : deps) {

                    if (cursadas.contains(depId)) continue;

                    Matter dep = matters.get(depId);
                    if (dep == null) continue; // robustez

                    List<List<Integer>> prereqs = dep.getPrerequisites();
                    if (prereqs == null || prereqs.isEmpty()) {
                        // no prereqs: debería haber indegree 0 ya
                        if (indegree.getOrDefault(depId, 0) != 0) {
                            indegree.put(depId, 0);
                            cola.enqueue(depId);
                        }
                        continue;
                    }

                    // Recalcular cuántos grupos (AND) quedan pendientes:
                    int gruposPendientes = 0;
                    for (List<Integer> grupo : prereqs) {
                        boolean grupoSatisfecho = false;
                        for (int pid : grupo) {
                            if (cursadas.contains(pid)) {
                                grupoSatisfecho = true;
                                break;
                            }
                        }
                        if (!grupoSatisfecho) gruposPendientes++;
                    }

                    int prev = indegree.getOrDefault(depId, 0);
                    indegree.put(depId, gruposPendientes);

                    if (prev > 0 && gruposPendientes == 0) {
                        cola.enqueue(depId);
                    }
                }
            }
        }

        // ===================================================
        // Validación final (si quedan materias no cursadas)
        // ===================================================
        if (cursadas.size() != matters.size()) {
            System.out.println("\n[!] ADVERTENCIA: No se cursaron todas las materias.");
            System.out.println("Cursadas: " + cursadas.size() + " de " + matters.size());

            System.out.println("\nMaterias no cursadas:");
            for (int id : matters.keySet()) {
                if (!cursadas.contains(id)) {
                    Matter m = matters.get(id);
                    System.out.println("  - " + id + ": " + (m != null ? m.getName() : "Materia no encontrada"));
                    List<List<Integer>> prereqs = (m != null ? m.getPrerequisites() : null);
                    if (prereqs != null) {
                        System.out.println("    Prerrequisitos: " + prereqs);
                    }
                }
            }
        }

        // ===================================================
        // Añadir semestre extra de trabajo de grado/pasantía
        // ID simbólico: -1
        // ===================================================
        List<Integer> trabajoDeGrado = new ArrayList<>();
        trabajoDeGrado.add(-1);  // ID especial que representa TRABAJO DE GRADO / PASANTÍA
        resultado.add(trabajoDeGrado);

        return resultado;
    }

    /**
     * Calcula el indegree inicial de todas las materias.
     * Cada grupo interno de prereqs representa un requisito (AND entre grupos).
     */
    private Map<Integer, Integer> calcularIndegree(Map<Integer, Matter> matters) {
        Map<Integer, Integer> indegree = new HashMap<>();

        for (int id : matters.keySet()) {
            indegree.put(id, 0);
        }

        for (Matter m : matters.values()) {
            List<List<Integer>> prereqs = m.getPrerequisites();
            if (prereqs == null || prereqs.isEmpty()) continue;

            // Cada grupo (lista interna) es tratado como una "unidad" que debe satisfacerse (AND).
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
                if (id == -1) {
                    // Impresión legible para el semestre extra
                    System.out.println("  TRABAJO DE GRADO / PASANTÍA");
                } else {
                    Matter m = graph.getMatter(id);
                    System.out.println("  " + id + " - " + (m != null ? m.getName() : "Materia no encontrada"));
                }
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
