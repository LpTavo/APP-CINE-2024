import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class AgregarRegistro extends JFrame {

    private static final long serialVersionUID = 1L;
    private JLabel lblNombre, lblDirector;
    private JTextField txtNombre, txtDirector;
    private JButton btnAgregar;

    public AgregarRegistro() {
        super("Agregar Película");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lblNombre = new JLabel("Nombre:");
        lblDirector = new JLabel("Director:");
        txtNombre = new JTextField(20);
        txtDirector = new JTextField(20);
        btnAgregar = new JButton("Agregar");

        btnAgregar.addActionListener((ActionEvent e) -> {
            agregarPelicula();
        });

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(lblNombre);
        panel.add(txtNombre);
        panel.add(lblDirector);
        panel.add(txtDirector);
        panel.add(btnAgregar);

        add(panel);
        pack();
        setVisible(true);
    }

    private void agregarPelicula() {
        String nombre = txtNombre.getText();
        String director = txtDirector.getText();

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gustavoloboappcine", "postgres", "MaryCielo")) {
            String query = "INSERT INTO peliculas (nombre, director) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, nombre);
            statement.setString(2, director);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "Película agregada exitosamente");
                txtNombre.setText("");
                txtDirector.setText("");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar la película: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AgregarRegistro::new);
    }
}
