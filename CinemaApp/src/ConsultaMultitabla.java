import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class ConsultaMultitabla extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextArea resultadoArea;

    public ConsultaMultitabla() {
        super("Consulta Multitabla");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton consultarButton = new JButton("Consultar");
        consultarButton.addActionListener((ActionEvent e) -> {
            ejecutarConsulta();
        });

        resultadoArea = new JTextArea(10, 30);
        resultadoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultadoArea);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(consultarButton, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);

        pack();
        setVisible(true);
    }

    private void ejecutarConsulta() {
        String consulta = "SELECT peliculas.nombre AS nombre_pelicula, peliculas.director, salas.numero_sala, salas.capacidad, funciones.hora_inicio, funciones.hora_fin, funciones.fecha " +
                          "FROM funciones " +
                          "JOIN salas ON funciones.id_sala = salas.id " +
                          "JOIN peliculas ON funciones.id_pelicula = peliculas.id";

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gustavoloboappcine", "postgres", "MaryCielo");
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(consulta)) {

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            StringBuilder sb = new StringBuilder();
            while (resultSet.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    sb.append(metaData.getColumnName(i)).append(": ").append(resultSet.getString(i)).append("\n");
                }
                sb.append("\n");
            }

            resultadoArea.setText(sb.toString());
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al ejecutar la consulta: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ConsultaMultitabla::new);
    }
}
