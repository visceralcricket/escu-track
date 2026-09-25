package core.escutrack.view;

import core.escutrack.controller.ControladorHospital;
import core.escutrack.utils.ValidadorCamposUtils;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaPacientes extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public VentanaPacientes(VentanaPrincipal ventana, ControladorHospital controlador) {
		setLayout(null);
		
		JButton btnRegistrar = new JButton("1. Registrar nuevo Paciente");
	    // Al usar Absolute Layout, el botón usa coordenadas y tamaño exactos (x, y, ancho, alto)
	    btnRegistrar.setBounds(128, 84, 250, 40); 
	    btnRegistrar.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	        	
	        	try {
	        	
	            	String rut = javax.swing.JOptionPane.showInputDialog(ventana, "RUT:");
	            	if(rut == null) return; // Si el usuario presiona 'cancelar'
	            	core.escutrack.utils.ValidadorCamposUtils.validarRut(rut);
	            	
	            	String nombre = javax.swing.JOptionPane.showInputDialog(ventana, "Nombre:");
	            	if(nombre == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarNombre(nombre);
	            	
	            	String gravedad = javax.swing.JOptionPane.showInputDialog(ventana, "Gravedad (ej. estable):");
	            	if(gravedad == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarGravedad(gravedad);
	            	
	            	String depto = javax.swing.JOptionPane.showInputDialog(ventana, "Departamento:");
	            	if(depto == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarDepartamento(depto);
	            	
	            	String cama = javax.swing.JOptionPane.showInputDialog(ventana, "ID Cama:");
	            	if(cama == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarIdCama(cama);
	            	
	            	controlador.registrarPaciente(rut, nombre, gravedad, depto, cama);
	                javax.swing.JOptionPane.showMessageDialog(ventana, "Paciente registrado exitosamente.");
	        	}
	        	
	        	catch(Exception e) {
	        		javax.swing.JOptionPane.showMessageDialog(ventana, "[ERROR]: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
	        	}
	            
	        }
	    });
	    add(btnRegistrar);
	    
	    // Editado formato de "Mostrar paciente específico" a simplemente "buscar paciente".
	    JButton btnBuscar = new JButton("2. Buscar Paciente");
	    btnBuscar.setBounds(128, 149, 250, 40); 
	    btnBuscar.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	        	
	        	try {
	            	String depto = javax.swing.JOptionPane.showInputDialog(ventana, "Departamento:");
	            	if(depto == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarDepartamento(depto);
	            	
	            	String cama = javax.swing.JOptionPane.showInputDialog(ventana, "ID Cama:");
	            	if(cama == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarIdCama(cama);
	            	
	            	String pacienteEncontrado = controlador.mostrarPaciente(cama, depto);
	                javax.swing.JOptionPane.showMessageDialog(ventana, "--- BÚSQUEDA DE PACIENTE --- \n\n" + pacienteEncontrado);
	        	}
	        	
	        	catch(Exception e) {
	        		javax.swing.JOptionPane.showMessageDialog(ventana, "[ERROR]: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
	        	}
	            
	        }
	    });
	    add(btnBuscar);
	    
	    JButton btnFiltrarGravedad = new JButton("3. Filtrar Pacientes por gravedad");
	    btnFiltrarGravedad.setBounds(128, 213, 250, 40); 
	    btnFiltrarGravedad.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	        	
	        	try {
	            	String gravedad = javax.swing.JOptionPane.showInputDialog(ventana, "Ingrese gravedad a filtrar:");
	            	if(gravedad == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarGravedad(gravedad);
	            	
	            	String pacientes = controlador.filtrarPorGravedad(gravedad);
	                javax.swing.JOptionPane.showMessageDialog(ventana, "--- PACIENTE ENCONTRADOS ---\n\n" + pacientes);
	        	}
	        	
	        	catch(Exception e) {
	        		javax.swing.JOptionPane.showMessageDialog(ventana, "[ERROR]: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
	        	}
	            
	        }
	    });
	    add(btnFiltrarGravedad);
	    
	    JButton btnEditar = new JButton("4. Editar Pacientes");
	    btnEditar.setBounds(128, 279, 250, 40); 
	    btnEditar.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	        	
	    		try {
	            	String depto = javax.swing.JOptionPane.showInputDialog(ventana, "Departamento:");
	            	if(depto == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarDepartamento(depto);
	            	
	            	String cama = javax.swing.JOptionPane.showInputDialog(ventana, "ID Cama:");
	            	if(cama == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarIdCama(cama);
	            	
					String nuevoNombre = javax.swing.JOptionPane.showInputDialog(ventana, "Nuevo nombre:");
	            	if(nuevoNombre == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarNombre(nuevoNombre);
	            	
					String nuevaGravedad = javax.swing.JOptionPane.showInputDialog(ventana, "Nueva gravedad:");
	            	if(nuevaGravedad == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarGravedad(nuevaGravedad);

					controlador.editarPaciente(depto, cama, nuevoNombre, nuevaGravedad);
	                javax.swing.JOptionPane.showMessageDialog(ventana, "Paciente editado.");
	        	}
	    		catch(Exception e) {
	        		javax.swing.JOptionPane.showMessageDialog(ventana, "[ERROR]: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
	        	}
	        }
	    });
	    add(btnEditar);
	    
	    JButton btnEliminar= new JButton("5. Dar de alta (Eliminar)");
	    btnEliminar.setBounds(128, 346, 250, 40); 
	    btnEliminar.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	        	
	    		try {
	            	String depto = javax.swing.JOptionPane.showInputDialog(ventana, "Departamento:");
	            	if(depto == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarDepartamento(depto);
	            	
	            	String cama = javax.swing.JOptionPane.showInputDialog(ventana, "ID Cama del paciente a eliminar:");
	            	if(cama == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarIdCama(cama);
	            	
					controlador.eliminarPaciente(cama, depto);
	                javax.swing.JOptionPane.showMessageDialog(ventana, "Paciente dado de alta (eliminado).");
	        	}
	    		catch(Exception e) {
	        		javax.swing.JOptionPane.showMessageDialog(ventana, "[ERROR]: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
	        	}
	        }
	    });
	    add(btnEliminar);
	    
	    JButton btnVolver = new JButton("Regresar al menú principal");
	    btnVolver.setBounds(128, 412, 250, 40);
	    btnVolver.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		ventana.mostrarMenuPrincipal();
	    	}
	    });
	    add(btnVolver);
	}   
}
