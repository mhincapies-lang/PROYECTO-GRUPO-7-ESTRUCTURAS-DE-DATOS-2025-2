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
     * Planifica semestres sin mostrar mensajes de progreso (modo silencioso).
     */
    public List<List<Integer>> planificarSemestresSilencioso(int maxPerSemester) {
        
        Map<Integer, Matter> matters = graph.getAllMatters();
        List<List<Integer>> semestresPredef = definirSemestresPredefinidos();
        List<List<Integer>> resultado = new ArrayList<>();
        Set<Integer> cursadas = new HashSet<>();
        Map<Integer, Integer> indegree = calcularIndegree(matters);
        
        // Procesar semestres predefinidos (sin output)
        for (List<Integer> semestrePred : semestresPredef) {
            List<Integer> semestreValido = new ArrayList<>();
            
            for (int idMateria : semestrePred) {
                if (!matters.containsKey(idMateria)) continue;
                
                if (puedeCursar(idMateria, cursadas, matters)) {
                    semestreValido.add(idMateria);
                    cursadas.add(idMateria);
                }
            }
            
            if (!semestreValido.isEmpty()) {
                resultado.add(semestreValido);
            }
        }
        
        // Actualizar indegree
        actualizarIndegree(indegree, cursadas, matters);
        
        // Crear cola con materias disponibles
        ArrayQueue<Integer> cola = new ArrayQueue<>(matters.size() + 5);
        for (int id : matters.keySet()) {
            if (!cursadas.contains(id) && indegree.get(id) == 0) {
                cola.enqueue(id);
            }
        }
        
        // Continuar con planificación automática (sin output)
        while (!cola.isEmpty()) {
            List<Integer> semestre = new ArrayList<>();
            
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
                            break;
                        }
                    }
                }
            }
        }
        
        return resultado;
    }

    /**
     * Planificador con semestres predefinidos iniciales y continuación automática.
     * MANEJA CORRECTAMENTE PREREQUISITOS OR.
     */
    public List<List<Integer>> planificarSemestres(int maxPerSemester) {
        
        Map<Integer, Matter> matters = graph.getAllMatters();
        
        // Definir la estructura de semestres predefinidos
        List<List<Integer>> semestresPredef = definirSemestresPredefinidos();
        
        List<List<Integer>> resultado = new ArrayList<>();
        Set<Integer> cursadas = new HashSet<>();
        
        // Calcular indegree inicial
        Map<Integer, Integer> indegree = calcularIndegree(matters);
        
        System.out.println("\n=== PROCESANDO SEMESTRES PREDEFINIDOS ===\n");
        
        // Procesar semestres predefinidos
        for (int i = 0; i < semestresPredef.size(); i++) {
            List<Integer> semestrePred = semestresPredef.get(i);
            List<Integer> semestreValido = new ArrayList<>();
            
            System.out.println("Semestre " + (i + 1) + " (predefinido):");
            
            for (int idMateria : semestrePred) {
                // Verificar que la materia existe en el grafo
                if (!matters.containsKey(idMateria)) {
                    System.out.println("  [X] Materia " + idMateria + " no existe en el grafo");
                    continue;
                }
                
                Matter materia = matters.get(idMateria);
                
                // Verificar que se pueden cursar (prerrequisitos cumplidos)
                if (puedeCursar(idMateria, cursadas, matters)) {
                    semestreValido.add(idMateria);
                    cursadas.add(idMateria);
                    System.out.println("  [OK] " + idMateria + " - " + materia.getName());
                } else {
                    System.out.println("  [X] " + idMateria + " - " + materia.getName() + 
                                     " (faltan prerrequisitos)");
                    mostrarPrerequisitos(materia, cursadas);
                }
            }
            
            if (!semestreValido.isEmpty()) {
                resultado.add(semestreValido);
            }
            System.out.println();
        }
        
        // Actualizar indegree después de materias cursadas
        actualizarIndegree(indegree, cursadas, matters);
        
        // Crear cola con materias disponibles
        ArrayQueue<Integer> cola = new ArrayQueue<>(matters.size() + 5);
        for (int id : matters.keySet()) {
            if (!cursadas.contains(id) && indegree.get(id) == 0) {
                cola.enqueue(id);
            }
        }
        
        System.out.println("=== CONTINUANDO CON PLANIFICACION AUTOMATICA ===\n");
        
        // Continuar con planificación automática de materias restantes
        int numSemestre = resultado.size() + 1;
        while (!cola.isEmpty()) {
            List<Integer> semestre = new ArrayList<>();
            
            // Llenar el semestre hasta el máximo permitido
            while (semestre.size() < maxPerSemester && !cola.isEmpty()) {
                int cur = cola.dequeue();
                semestre.add(cur);
                cursadas.add(cur);
            }
            
            resultado.add(semestre);
            
            System.out.println("Semestre " + numSemestre + " (automatico):");
            for (int id : semestre) {
                Matter m = matters.get(id);
                System.out.println("  - " + id + " - " + m.getName());
            }
            System.out.println();
            numSemestre++;
            
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
                    mostrarPrerequisitos(m, cursadas);
                }
            }
        } else {
            System.out.println("\n[OK] Todas las materias fueron planificadas exitosamente.");
        }
        
        return resultado;
    }
    
    /**
     * Define los semestres predefinidos iniciales.
     */
    private List<List<Integer>> definirSemestresPredefinidos() {
        List<List<Integer>> semestres = new ArrayList<>();
        
        // SEMESTRE 1: Matemáticas básicas, Lecto-escritura
        semestres.add(Arrays.asList(
            1000001,  // Matemáticas Básicas
            1000002,  // Lecto-Escritura
            2015168,  // Fundamentos de matemáticas
            2015184   // Lógica Matemática
        ));
        
        // SEMESTRE 2: Cálculo diferencial, Intro IS, Programación, Pensamiento sistémico
        semestres.add(Arrays.asList(
            1000004,  // Cálculo diferencial
            2025975,  // Introducción a la Ingeniería de Sistemas y Computación
            2015734,  // Programación de Computadores
            2016703   // Pensamiento Sistémico
        ));
        

        return semestres;
    }
    
    /**
     * Calcula el indegree inicial de todas las materias.
     * Maneja correctamente prerequisitos OR.
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
     * Actualiza el indegree después de que algunas materias ya fueron cursadas.
     */
    private void actualizarIndegree(Map<Integer, Integer> indegree, 
                                     Set<Integer> cursadas, 
                                     Map<Integer, Matter> matters) {
        for (Matter m : matters.values()) {
            if (cursadas.contains(m.getId())) continue;
            
            List<List<Integer>> prereqs = m.getPrerequisites();
            if (prereqs == null) continue;
            
            int gruposSatisfechos = 0;
            
            for (List<Integer> grupo : prereqs) {
                // Verificar si al menos uno del grupo OR está cursado
                boolean grupoSatisfecho = false;
                for (int prereqId : grupo) {
                    if (cursadas.contains(prereqId)) {
                        grupoSatisfecho = true;
                        break;
                    }
                }
                if (grupoSatisfecho) {
                    gruposSatisfechos++;
                }
            }
            
            indegree.put(m.getId(), prereqs.size() - gruposSatisfechos);
        }
    }
    
    /**
     * Verifica si una materia puede cursarse dado el conjunto de materias ya cursadas.
     * Maneja correctamente prerequisitos OR.
     */
    private boolean puedeCursar(int idMateria, Set<Integer> cursadas, Map<Integer, Matter> matters) {
        Matter materia = matters.get(idMateria);
        if (materia == null) return false;
        
        List<List<Integer>> prereqs = materia.getPrerequisites();
        if (prereqs == null || prereqs.isEmpty()) {
            return true; // No tiene prerrequisitos
        }
        
        // Verificar que TODOS los grupos de prerrequisitos estén satisfechos
        for (List<Integer> grupo : prereqs) {
            // Para cada grupo (OR), verificar que al menos uno esté cursado
            boolean grupoSatisfecho = false;
            for (int prereqId : grupo) {
                if (cursadas.contains(prereqId)) {
                    grupoSatisfecho = true;
                    break;
                }
            }
            
            if (!grupoSatisfecho) {
                return false; // Falta satisfacer este grupo
            }
        }
        
        return true;
    }
    
    /**
     * Muestra los prerrequisitos de una materia y cuáles faltan.
     */
    private void mostrarPrerequisitos(Matter materia, Set<Integer> cursadas) {
        List<List<Integer>> prereqs = materia.getPrerequisites();
        if (prereqs == null || prereqs.isEmpty()) return;
        
        System.out.println("      Prerrequisitos:");
        for (int i = 0; i < prereqs.size(); i++) {
            List<Integer> grupo = prereqs.get(i);
            System.out.print("      Grupo " + (i + 1) + ": ");
            
            if (grupo.size() == 1) {
                int prereqId = grupo.get(0);
                Matter prereqMateria = graph.getMatter(prereqId);
                String nombre = prereqMateria != null ? prereqMateria.getName() : "Desconocida";
                String estado = cursadas.contains(prereqId) ? "[OK]" : "[X]";
                System.out.println(estado + " " + prereqId + " - " + nombre);
            } else {
                System.out.println("(OR entre las siguientes):");
                for (int prereqId : grupo) {
                    Matter prereqMateria = graph.getMatter(prereqId);
                    String nombre = prereqMateria != null ? prereqMateria.getName() : "Desconocida";
                    String estado = cursadas.contains(prereqId) ? "[OK]" : "[X]";
                    System.out.println("         " + estado + " " + prereqId + " - " + nombre);
                }
            }
        }
    }

    /**
     * Imprime el plan de semestres.
     */
    public void imprimirPlan(List<List<Integer>> plan) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("           PLAN DE ESTUDIOS - RESUMEN FINAL");
        System.out.println("=".repeat(60) + "\n");
        
        int totalMaterias = 0;
        
        for (int i = 0; i < plan.size(); i++) {
            System.out.println("Semestre " + (i + 1) + " (" + plan.get(i).size() + " materias)");
            System.out.println("-".repeat(60));
            for (int id : plan.get(i)) {
                Matter m = graph.getMatter(id);
                System.out.println("  " + id + " - " + (m != null ? m.getName() : "Materia no encontrada"));
            }
            System.out.println();
            totalMaterias += plan.get(i).size();
        }
        
        System.out.println("=".repeat(60));
        System.out.println("Total de semestres: " + plan.size());
        System.out.println("Total de materias: " + totalMaterias);
        System.out.println("Tiempo estimado: " + (plan.size() / 2.0) + " años");
        System.out.println("=".repeat(60));
    }
}
