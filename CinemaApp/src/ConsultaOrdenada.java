import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class ConsultaOrdenada extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField consultaField;
    private JButton consultarButton;
    private JComboBox<String> orderByComboBox;
    private JTextArea resultadoArea;

    public ConsultaOrdenada() {
        super("Consulta Ordenada");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel consultaLabel = new JLabel("Consulta:");
        consultaField = new JTextField(20);

        consultarButton = new JButton("Consultar");
        consultarButton.addActionListener((ActionEvent e) -> {
            ejecutarConsulta();
        });

        JLabel orderByLabel = new JLabel("Ordenar por:");
        orderByComboBox = new JComboBox<>(new String[]{"id", "nombre", "director"}); // Cambiar según los campos de la tabla
        orderByComboBox.setSelectedIndex(0); // Por defecto, ordenar por el primer campo

        resultadoArea = new JTextArea(10, 30);
        resultadoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultadoArea);

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(consultaLabel);
        panel.add(consultaField);
        panel.add(orderByLabel);
        panel.add(orderByComboBox);
        panel.add(consultarButton);

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    ConsultaOrdenada(String orderBy) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void ejecutarConsulta() {
        String consulta = consultaField.getText();
        String orderBy = (String) orderByComboBox.getSelectedItem();

        consulta += " ORDER BY " + orderBy; // Agregar cláusula ORDER BY

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
        SwingUtilities.invokeLater(ConsultaOrdenada::new);
    }
}
