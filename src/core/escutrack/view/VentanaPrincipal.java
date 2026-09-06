package core.escutrack.view;

// Necesario para importar funciones del controlador a los botones
import core.escutrack.controller.ControladorHospital;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private ControladorHospital controlador;    

    // Único constructor que exige el controlador y arma la ventana
    public VentanaPrincipal(ControladorHospital controlador) {
    	
        this.controlador = controlador;
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        
        // Absolute Layout NECESARIO que permite mover cosas libremente
        contentPane.setLayout(null); 
        setContentPane(contentPane);
        
        JButton btnRegistrar = new JButton("1. Registrar nuevo Paciente");
        // Al usar Absolute Layout, el botón usa coordenadas y tamaño exactos (x, y, ancho, alto)
        btnRegistrar.setBounds(100, 50, 250, 40); 
        btnRegistrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	
            	try {
            	
	            	String rut = javax.swing.JOptionPane.showInputDialog(VentanaPrincipal.this, "RUT:");
	            	if(rut == null) return; // Si el usuario presiona 'cancelar'
	            	core.escutrack.utils.ValidadorCamposUtils.validarRut(rut);
	            	
	            	String nombre = javax.swing.JOptionPane.showInputDialog(VentanaPrincipal.this, "Nombre:");
	            	if(nombre == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarNombre(nombre);
	            	
	            	String gravedad = javax.swing.JOptionPane.showInputDialog(VentanaPrincipal.this, "Gravedad (ej. estable):");
	            	core.escutrack.utils.ValidadorCamposUtils.validarGravedad(gravedad);
	            	
	            	String depto = javax.swing.JOptionPane.showInputDialog(VentanaPrincipal.this, "Departamento:");
	            	core.escutrack.utils.ValidadorCamposUtils.validarDepartamento(depto);
	            	
	            	String cama = javax.swing.JOptionPane.showInputDialog(VentanaPrincipal.this, "ID Cama:");
	            	core.escutrack.utils.ValidadorCamposUtils.validarIdCama(cama);
	            	
	            	controlador.registrarPaciente(rut, nombre, gravedad, depto, cama);
	                javax.swing.JOptionPane.showMessageDialog(VentanaPrincipal.this, "Paciente registrado exitosamente.");
            	}
            	
            	catch(Exception e) {
            		javax.swing.JOptionPane.showMessageDialog(VentanaPrincipal.this, "[ERROR]: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            	}
                
            }
        });
        contentPane.add(btnRegistrar);
        
        
        JButton btnSalir = new JButton("4. Gaurdar y Salir");
        btnSalir.setBounds(100, 210, 250, 40);
        btnSalir.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		try {
        			controlador.apagarSistema();
        			System.exit(0);
        		}
        		catch(Exception e) {
        			javax.swing.JOptionPane.showMessageDialog(VentanaPrincipal.this, "Error al guardar: " + e.getMessage());
        		}
        	}
        });
        contentPane.add(btnSalir);
        
        /* +++
         * TODO: AQUÍ VAN EL RESTO DE BOTONES
         * Temporalmente solo se agregaron los más esenciales, pero aún
         * faltan el resto.
         * 
         * @author Felipe T.S.
         --- */
        
    }
}