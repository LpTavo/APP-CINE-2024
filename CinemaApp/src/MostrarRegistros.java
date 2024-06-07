import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;

public class MostrarRegistros extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable table;
    private DefaultTableModel model;

    public MostrarRegistros() {
        super("Mostrar Películas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombre");
        model.addColumn("Director");

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gustavoloboappcine", "postgres", "MaryCielo")) {
            String query = "SELECT * FROM peliculas";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                String director = resultSet.getString("director");
                model.addRow(new Object[]{id, nombre, director});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al obtener las películas: " + ex.getMessage());
        }

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel);
        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MostrarRegistros::new);
    }
}