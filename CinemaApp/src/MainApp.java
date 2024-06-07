import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;

public class MainApp extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTable table;
    private DefaultTableModel model;
    private JTextField idField, tituloDistribucionField, tituloOriginalField, generoField, idiomaOriginalField, subtitulosEspanolField, paisesOrigenField, anoProduccionField, urlSitioWebField, duracionHorasField, duracionMinutosField, clasificacionEdadesField, fechaEstrenoField, resumenField, idDirectorField;
    private JButton agregarButton, eliminarButton, actualizarButton, mostrarButton, consultaPersonalizadaButton, consultaOrdenadaButton, consultaMultitablaButton;

    public MainApp() {
        super("Gestión de Cine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Título Distribución");
        model.addColumn("Título Original");
        model.addColumn("Género");
        model.addColumn("Idioma Original");
        model.addColumn("Subtítulos Español");
        model.addColumn("Países Origen");
        model.addColumn("Año Producción");
        model.addColumn("URL Sitio Web");
        model.addColumn("Duración Horas");
        model.addColumn("Duración Minutos");
        model.addColumn("Clasificación Edades");
        model.addColumn("Fecha Estreno");
        model.addColumn("Resumen");
        model.addColumn("ID Director");

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Panel de entrada de datos
        JPanel inputPanel = new JPanel(new GridLayout(15, 2));
        idField = new JTextField();
        tituloDistribucionField = new JTextField();
        tituloOriginalField = new JTextField();
        generoField = new JTextField();
        idiomaOriginalField = new JTextField();
        subtitulosEspanolField = new JTextField();
        paisesOrigenField = new JTextField();
        anoProduccionField = new JTextField();
        urlSitioWebField = new JTextField();
        duracionHorasField = new JTextField();
        duracionMinutosField = new JTextField();
        clasificacionEdadesField = new JTextField();
        fechaEstrenoField = new JTextField();
        resumenField = new JTextField();
        idDirectorField = new JTextField();
        inputPanel.add(new JLabel("ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Título Distribución:"));
        inputPanel.add(tituloDistribucionField);
        inputPanel.add(new JLabel("Título Original:"));
        inputPanel.add(tituloOriginalField);
        inputPanel.add(new JLabel("Género:"));
        inputPanel.add(generoField);
        inputPanel.add(new JLabel("Idioma Original:"));
        inputPanel.add(idiomaOriginalField);
        inputPanel.add(new JLabel("Subtítulos Español:"));
        inputPanel.add(subtitulosEspanolField);
        inputPanel.add(new JLabel("Países Origen:"));
        inputPanel.add(paisesOrigenField);
        inputPanel.add(new JLabel("Año Producción:"));
        inputPanel.add(anoProduccionField);
        inputPanel.add(new JLabel("URL Sitio Web:"));
        inputPanel.add(urlSitioWebField);
        inputPanel.add(new JLabel("Duración Horas:"));
        inputPanel.add(duracionHorasField);
        inputPanel.add(new JLabel("Duración Minutos:"));
        inputPanel.add(duracionMinutosField);
        inputPanel.add(new JLabel("Clasificación Edades:"));
        inputPanel.add(clasificacionEdadesField);
        inputPanel.add(new JLabel("Fecha Estreno:"));
        inputPanel.add(fechaEstrenoField);
        inputPanel.add(new JLabel("Resumen:"));
        inputPanel.add(resumenField);
        inputPanel.add(new JLabel("ID Director:"));
        inputPanel.add(idDirectorField);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new GridLayout(1, 7));
        agregarButton = new JButton("Agregar");
        eliminarButton = new JButton("Eliminar");
        actualizarButton = new JButton("Actualizar");
        mostrarButton = new JButton("Mostrar");
        consultaPersonalizadaButton = new JButton("Consulta Personalizada");
        consultaOrdenadaButton = new JButton("Consulta Ordenada");
        consultaMultitablaButton = new JButton("Consulta Multitabla");

        buttonPanel.add(agregarButton);
        buttonPanel.add(eliminarButton);
        buttonPanel.add(actualizarButton);
        buttonPanel.add(mostrarButton);
        buttonPanel.add(consultaPersonalizadaButton);
        buttonPanel.add(consultaOrdenadaButton);
        buttonPanel.add(consultaMultitablaButton);

        // Añadir ActionListeners a los botones
        agregarButton.addActionListener(e -> agregarPelicula());
        eliminarButton.addActionListener(e -> eliminarPelicula());
        actualizarButton.addActionListener(e -> actualizarPelicula());
        mostrarButton.addActionListener(e -> mostrarPeliculas());
        consultaPersonalizadaButton.addActionListener(e -> consultaPersonalizada());
        consultaOrdenadaButton.addActionListener(e -> consultaOrdenada());
        consultaMultitablaButton.addActionListener(e -> consultaMultitabla());

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    private void agregarPelicula() {
        String tituloDistribucion = tituloDistribucionField.getText();
        String tituloOriginal = tituloOriginalField.getText();
        String genero = generoField.getText();
        String idiomaOriginal = idiomaOriginalField.getText();
        boolean subtitulosEspanol = Boolean.parseBoolean(subtitulosEspanolField.getText());
        String paisesOrigen = paisesOrigenField.getText();
        int anoProduccion = Integer.parseInt(anoProduccionField.getText());
        String urlSitioWeb = urlSitioWebField.getText();
        int duracionHoras = Integer.parseInt(duracionHorasField.getText());
        int duracionMinutos = Integer.parseInt(duracionMinutosField.getText());
        String clasificacionEdades = clasificacionEdadesField.getText();
        Date fechaEstreno = Date.valueOf(fechaEstrenoField.getText());
        String resumen = resumenField.getText();
        int idDirector = Integer.parseInt(idDirectorField.getText());

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gustavoloboappcine", "postgres", "MaryCielo")) {
            String query = "INSERT INTO pelicula (titulo_distribucion, titulo_original, genero, idioma_original, subtitulos_espanol, paises_origen, ano_produccion, url_sitio_web, duracion_horas, duracion_minutos, clasificacion_edades, fecha_estreno_santiago, resumen, id_director) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, tituloDistribucion);
            statement.setString(2, tituloOriginal);
            statement.setString(3, genero);
            statement.setString(4, idiomaOriginal);
            statement.setBoolean(5, subtitulosEspanol);
            statement.setString(6, paisesOrigen);
            statement.setInt(7, anoProduccion);
            statement.setString(8, urlSitioWeb);
            statement.setInt(9, duracionHoras);
            statement.setInt(10, duracionMinutos);
            statement.setString(11, clasificacionEdades);
            statement.setDate(12, fechaEstreno);
            statement.setString(13, resumen);
            statement.setInt(14, idDirector);
            statement.executeUpdate();
            mostrarPeliculas(); // Actualizar la tabla después de agregar
            JOptionPane.showMessageDialog(this, "Película agregada exitosamente");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar la película: " + ex.getMessage());
        }
    }

    private void eliminarPelicula() {
        int id = Integer.parseInt(idField.getText());

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gustavoloboappcine", "postgres", "MaryCielo")) {
            String query = "DELETE FROM pelicula WHERE id_pelicula = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
            mostrarPeliculas(); // Actualizar la tabla después de eliminar
            JOptionPane.showMessageDialog(this, "Película eliminada exitosamente");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar la película: " + ex.getMessage());
        }
    }

    private void actualizarPelicula() {
        int id = Integer.parseInt(idField.getText());
        String tituloDistribucion = tituloDistribucionField.getText();
        String tituloOriginal = tituloOriginalField.getText();
        String genero = generoField.getText();
        String idiomaOriginal = idiomaOriginalField.getText();
        boolean subtitulosEspanol = Boolean.parseBoolean(subtitulosEspanolField.getText());
        String paisesOrigen = paisesOrigenField.getText();
        int anoProduccion = Integer.parseInt(anoProduccionField.getText());
        String urlSitioWeb = urlSitioWebField.getText();
        int duracionHoras = Integer.parseInt(duracionHorasField.getText());
        int duracionMinutos = Integer.parseInt(duracionMinutosField.getText());
        String clasificacionEdades = clasificacionEdadesField.getText();
        Date fechaEstreno = Date.valueOf(fechaEstrenoField.getText());
        String resumen = resumenField.getText();
        int idDirector = Integer.parseInt(idDirectorField.getText());

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gustavoloboappcine", "postgres", "MaryCielo")) {
            String query = "UPDATE pelicula SET titulo_distribucion = ?, titulo_original = ?, genero = ?, idioma_original = ?, subtitulos_espanol = ?, paises_origen = ?, ano_produccion = ?, url_sitio_web = ?, duracion_horas = ?, duracion_minutos = ?, clasificacion_edades = ?, fecha_estreno_santiago = ?, resumen = ?, id_director = ? WHERE id_pelicula = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, tituloDistribucion);
            statement.setString(2, tituloOriginal);
            statement.setString(3, genero);
            statement.setString(4, idiomaOriginal);
            statement.setBoolean(5, subtitulosEspanol);
            statement.setString(6, paisesOrigen);
            statement.setInt(7, anoProduccion);
            statement.setString(8, urlSitioWeb);
            statement.setInt(9, duracionHoras);
            statement.setInt(10, duracionMinutos);
            statement.setString(11, clasificacionEdades);
            statement.setDate(12, fechaEstreno);
            statement.setString(13, resumen);
            statement.setInt(14, idDirector);
            statement.setInt(15, id);
            statement.executeUpdate();
            mostrarPeliculas(); // Actualizar la tabla después de actualizar
            JOptionPane.showMessageDialog(this, "Película actualizada exitosamente");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar la película: " + ex.getMessage());
        }
    }

    private void mostrarPeliculas() {
        model.setRowCount(0); // Limpiar la tabla antes de mostrar los datos

        try (Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/gustavoloboappcine", "postgres", "MaryCielo")) {
            String query = "SELECT * FROM pelicula";
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                model.addRow(new Object[]{
                        resultSet.getInt("id_pelicula"),
                        resultSet.getString("titulo_distribucion"),
                        resultSet.getString("titulo_original"),
                        resultSet.getString("genero"),
                        resultSet.getString("idioma_original"),
                        resultSet.getBoolean("subtitulos_espanol"),
                        resultSet.getString("paises_origen"),
                        resultSet.getInt("ano_produccion"),
                        resultSet.getString("url_sitio_web"),
                        resultSet.getInt("duracion_horas"),
                        resultSet.getInt("duracion_minutos"),
                        resultSet.getString("clasificacion_edades"),
                        resultSet.getDate("fecha_estreno_santiago"),
                        resultSet.getString("resumen"),
                        resultSet.getInt("id_director")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al mostrar las películas: " + ex.getMessage());
        }
    }

    private void consultaPersonalizada() {
        // Implementar lógica para consulta personalizada
    }

    private void consultaOrdenada() {
        // Implementar lógica para consulta ordenada
    }

    private void consultaMultitabla() {
        // Implementar lógica para consulta multitabla
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainApp());
    }
}


