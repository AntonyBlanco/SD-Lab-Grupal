package base_de_datos_jdbc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainApp extends JFrame {

    public MainApp() {
        super("Bienvenido al Sistema de Gestión");

        // Configuración del layout
        setLayout(new BorderLayout());

        // Etiqueta de bienvenida
        JLabel lblBienvenida = new JLabel("Bienvenido al sistema de Gestión", SwingConstants.CENTER);
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 24));
        add(lblBienvenida, BorderLayout.NORTH);

        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(5, 1, 10, 10));

        // Botón para ir a la gestión de proyectos
        JButton btnGestionProyectos = new JButton("Ir a Gestión de Proyectos");
        btnGestionProyectos.setFont(new Font("Arial", Font.PLAIN, 18));
        btnGestionProyectos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Al hacer clic en el botón, se abre la ventana de gestión de proyectos
                abrirGestionProyectos();
            }
        });
        panelBotones.add(btnGestionProyectos);

        // Botón para ir a la gestión de departamentos
        JButton btnGestionDepartamentos = new JButton("Ir a Gestión de Departamentos");
        btnGestionDepartamentos.setFont(new Font("Arial", Font.PLAIN, 18));
        btnGestionDepartamentos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Al hacer clic en el botón, se abre la ventana de gestión de departamentos
                abrirGestionDepartamentos();
            }
        });
        panelBotones.add(btnGestionDepartamentos);

        // Botón para ir a la gestión de ingenieros
        JButton btnGestionIngenieros = new JButton("Ir a Gestión de Ingenieros");
        btnGestionIngenieros.setFont(new Font("Arial", Font.PLAIN, 18));
        btnGestionIngenieros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Al hacer clic en el botón, se abre la ventana de gestión de ingenieros
                abrirGestionIngenieros();
            }
        });
        panelBotones.add(btnGestionIngenieros);

        // Botón para ir a la gestión de asignaciones
        JButton btnGestionAsignaciones = new JButton("Ir a Gestión de Asignaciones");
        btnGestionAsignaciones.setFont(new Font("Arial", Font.PLAIN, 18));
        btnGestionAsignaciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Al hacer clic en el botón, se abre la ventana de gestión de asignaciones
                abrirGestionAsignaciones();
            }
        });
        panelBotones.add(btnGestionAsignaciones);

        // Botón para salir
        JButton btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Arial", Font.PLAIN, 18));
        btnSalir.addActionListener(e -> System.exit(0));
        panelBotones.add(btnSalir);

        add(panelBotones, BorderLayout.CENTER);

        // Configuración de la ventana principal
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void abrirGestionProyectos() {
        // Ocultar la ventana de bienvenida y mostrar la ventana de gestión de proyectos
        this.setVisible(false);
        ConsultaIngenierosProyecto gestionProyectos = new ConsultaIngenierosProyecto(this);
        gestionProyectos.setVisible(true);
    }

    private void abrirGestionDepartamentos() {
        // Ocultar la ventana de bienvenida y mostrar la ventana de gestión de departamentos
        this.setVisible(false);
        ConsultaDepartamentos gestionDepartamentos = new ConsultaDepartamentos(this);
        gestionDepartamentos.setVisible(true);
    }

    private void abrirGestionIngenieros() {
        // Ocultar la ventana de bienvenida y mostrar la ventana de gestión de ingenieros
        this.setVisible(false);
        ConsultaIngenieros gestionIngenieros = new ConsultaIngenieros(this);
        gestionIngenieros.setVisible(true);
    }

    private void abrirGestionAsignaciones() {
        // Ocultar la ventana de bienvenida y mostrar la ventana de gestión de asignaciones
        this.setVisible(false);
        ConsultaAsignaciones gestionAsignaciones = new ConsultaAsignaciones(this);
        gestionAsignaciones.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainApp mainApp = new MainApp();
            mainApp.setVisible(true);
        });
    }
}
