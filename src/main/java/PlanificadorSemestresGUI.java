import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;

public class PlanificadorSemestresGUI extends JFrame {

    private MatterGraph graph;
    private PlanificadorSemestres planner;

    private JSpinner spinnerMaxPorSemestre;
    private JTextArea areaMaterias;
    private JTextArea areaPlan;
    private JLabel labelSemestresMinimos;

    public PlanificadorSemestresGUI() {
        graph = new MatterGraph();
        planner = new PlanificadorSemestres(graph);

        initUI();
        cargarMateriasEnTexto();
    }

    private void initUI() {
        setTitle("Planificador de Semestres - Grupo 7");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800,600);
        setLocationRelativeTo(null);

        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTop.add(new JLabel("Máximo de materias por semestre:"));

        spinnerMaxPorSemestre = new JSpinner(new SpinnerNumberModel(5, 1, 10, 1));
        panelTop.add(spinnerMaxPorSemestre);

        JButton btnCalcular = new JButton("Calcular plan");
        btnCalcular.addActionListener(this::onCalcularPlan);
        panelTop.add(btnCalcular);

        labelSemestresMinimos = new JLabel("Semestres mínimos: -");
        panelTop.add(labelSemestresMinimos);

        areaMaterias = new JTextArea();
        areaMaterias.setEditable(false);
        areaMaterias.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        JScrollPane scrollMaterias = new JScrollPane(areaMaterias);
        scrollMaterias.setBorder(BorderFactory.createTitledBorder("Materias y prerrequisitos"));

        areaPlan = new JTextArea();
        areaPlan.setEditable(false);
        areaPlan.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        JScrollPane scrollPlan = new JScrollPane(areaPlan);
        scrollPlan.setBorder(BorderFactory.createTitledBorder("Plan sugerido por semestres"));

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                scrollMaterias,
                scrollPlan
        );
        splitPane.setResizeWeight(0.5);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panelTop, BorderLayout.NORTH);
        getContentPane().add(splitPane, BorderLayout.CENTER);
    }

    private void cargarMateriasEnTexto() {
        StringBuilder sb = new StringBuilder();
        Map<Integer, Matter> materias = graph.getMatters();
        int x = 0;

        for (Map.Entry<Integer, Matter> entry : materias.entrySet()) {
            int id = entry.getKey();
            Matter m = entry.getValue();
            x++;

            sb.append("ID: ").append(id)
                    .append(" | Nombre: ").append(m.getName())
                    .append("\n   Prerrequisitos: ");

            List<List<Integer>> prereqs = m.getPrerequisites();

            if (prereqs == null || prereqs.isEmpty()) {
                sb.append("Ninguno");
            } else {
                boolean first = true;
                for (List<Integer> grupo : prereqs) {
                    for (Integer preId : grupo) {
                        if (!first) sb.append(", ");
                        sb.append(preId);
                        first = false;
                    }
                }
            }
            sb.append("\n\n");
        }

        areaMaterias.setText(sb.toString());
        areaMaterias.setCaretPosition(0);
    }

    private void onCalcularPlan(ActionEvent e) {
        int maxPorSemestre = (Integer) spinnerMaxPorSemestre.getValue();
        String aux = "";

        try {
            List<List<Integer>> plan = planner.planificarSemestres(maxPorSemestre);
            mostrarPlan(plan);
            int semestresMinimos = planner.calcularSemestresMinimos(maxPorSemestre);
            aux = "Semestres mínimos: " + semestresMinimos;
            labelSemestresMinimos.setText(aux);
        } catch (IllegalStateException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    ex.getMessage(),
                    "paila parceoooo ERROOOR",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "SIA CAIDO DX:\n" + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void mostrarPlan(List<List<Integer>> plan) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== PLAN DE SEMESTRES ===\n\n");
        int i = 0;

        for (int j = 0; j < plan.size(); j++) {
            sb.append("Semestre ").append(j + 1).append(": ");

            List<Integer> materiasSemestre = plan.get(j);
            if (materiasSemestre.isEmpty()) {
                sb.append("(sin materias)");
            } else {
                boolean first = true;
                for (Integer idMateria : materiasSemestre) {
                    Matter m = graph.getMatter(idMateria);
                    String nombre = (m != null) ? m.getName() : "?";
                    if (!first) sb.append(" | ");
                    sb.append(idMateria).append(" - ").append(nombre);
                    first = false;
                    i++;
                }
            }
            sb.append("\n");
        }

        areaPlan.setText(sb.toString());
        areaPlan.setCaretPosition(0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PlanificadorSemestresGUI gui = new PlanificadorSemestresGUI();
            gui.setVisible(true);
        });
    }
}
