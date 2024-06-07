import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class EliminarRegistro extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField idField;
    private JButton eliminarButton;

    public EliminarRegistro() {
        super("Eliminar Película");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField(10);

        eliminarButton = new JButton("Eliminar");
        eliminarButton.addActionListener((ActionEvent e) -> {
            eliminarRegistro();
        });

        JPanel panel = new JPanel(new GridLayout(2, 2));
        panel.add(idLabel);
        panel.add(idField);
        panel.add(eliminarButton);

        add(panel);
        pack();
        setVisible(true);
    }

    private void eliminarRegistro() {
        int id = Integer.parseInt(idField.getText());

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gustavoloboappcine", "postgres", "MaryCielo")) {
            String query = "DELETE FROM peliculas WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Registro eliminado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró ningún registro con ese ID.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar el registro: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EliminarRegistro::new);
    }
}
