import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class ConsultaPersonalizada extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField consultaField;
    private JButton consultarButton;
    private JTextArea resultadoArea;

    public ConsultaPersonalizada() {
        super("Consulta Personalizada");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel consultaLabel = new JLabel("Consulta:");
        consultaField = new JTextField(20);

        consultarButton = new JButton("Consultar");
        consultarButton.addActionListener((ActionEvent e) -> {
            ejecutarConsulta();
        });

        resultadoArea = new JTextArea(10, 30);
        resultadoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultadoArea);

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(consultaLabel);
        panel.add(consultaField);
        panel.add(consultarButton);

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        pack();
        setVisible(true);
    }

    ConsultaPersonalizada(String consulta) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void ejecutarConsulta() {
        String consulta = consultaField.getText();

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
        SwingUtilities.invokeLater(ConsultaPersonalizada::new);
    }
}
