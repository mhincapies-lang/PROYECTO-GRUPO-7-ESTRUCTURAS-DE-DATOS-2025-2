public class main {

    public static void main(String[] args) {

        // 1. Crear el grafo de materias
        MatterGraph graph = new MatterGraph();

        // 2. Crear el planificador
        PlanificadorSemestres planner = new PlanificadorSemestres(graph);

        // 3. Definir cuántas materias máximas por semestre
        int maxMateriasPorSemestre = 5;

        // 4. Calcular el plan
        var plan = planner.planificarSemestres(maxMateriasPorSemestre);

        // 5. Imprimir el plan
        planner.imprimirPlan(plan);

        // 6. Mostrar número mínimo de semestres
        System.out.println(
            "\nSemestres mínimos: " + planner.calcularSemestresMinimos(maxMateriasPorSemestre)
        );
    }
}
