import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

public class ActualizarRegistro extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField idField;
    private JTextField nombreField;
    private JTextField directorField;
    private JButton actualizarButton;

    public ActualizarRegistro() {
        super("Actualizar Película");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField(10);

        JLabel nombreLabel = new JLabel("Nombre:");
        nombreField = new JTextField(20);

        JLabel directorLabel = new JLabel("Director:");
        directorField = new JTextField(20);

        actualizarButton = new JButton("Actualizar");
        actualizarButton.addActionListener((ActionEvent e) -> {
            actualizarRegistro();
        });

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(idLabel);
        panel.add(idField);
        panel.add(nombreLabel);
        panel.add(nombreField);
        panel.add(directorLabel);
        panel.add(directorField);
        panel.add(actualizarButton);

        add(panel);
        pack();
        setVisible(true);
    }

    private void actualizarRegistro() {
        int id = Integer.parseInt(idField.getText());
        String nombre = nombreField.getText();
        String director = directorField.getText();

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gustavoloboappcine", "postgres", "MaryCielo")) {
            String query = "UPDATE peliculas SET nombre = ?, director = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, nombre);
            statement.setString(2, director);
            statement.setInt(3, id);
            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Registro actualizado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró ningún registro con ese ID.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar el registro: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ActualizarRegistro::new);
    }
}
