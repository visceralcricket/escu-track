package core.escutrack.view;

import core.escutrack.controller.ControladorHospital;
import core.escutrack.utils.ValidadorCamposUtils;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaDepartamentos extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public VentanaDepartamentos(VentanaPrincipal ventana, ControladorHospital controlador) {
		setLayout(null);
		
		JButton btnRegistrar = new JButton("1. Agregar Departamento");
		btnRegistrar.setBounds(128, 82, 250, 40); 
		btnRegistrar.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	        	
	        	try {
	            	String depto = javax.swing.JOptionPane.showInputDialog(ventana, "Nombre del nuevo departamento:");
	            	if(depto == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarDepartamento(depto);
	            	
	            	controlador.agregarDepartamento(depto);
	                javax.swing.JOptionPane.showMessageDialog(ventana, "Departamento creado con éxito.");
	        	}
	        	
	        	catch(Exception e) {
	        		javax.swing.JOptionPane.showMessageDialog(ventana, "[ERROR]: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
	        	}
	            
	        }
	    });
	    add(btnRegistrar);
	    
	    JButton btnMostrar = new JButton("2. Mostrar Departamentos");
	    btnMostrar.setBounds(128, 146, 250, 40); 
	    btnMostrar.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	        	
	        	try {
	            	String departamentosEncontrados = controlador.mostrarDepartamentos();
	                javax.swing.JOptionPane.showMessageDialog(ventana, departamentosEncontrados);
	        	}
	        	
	        	catch(Exception e) {
	        		javax.swing.JOptionPane.showMessageDialog(ventana, "[ERROR]: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
	        	}
	            
	        }
	    });
	    add(btnMostrar);
	    
	    /*
	     * Botones de funcionalidades faltantes.
	     * 
	     * @since v0.2.3
	     * @author Felipe T.S.
	     */
	    JButton btnBuscar = new JButton("3. Buscar Departamento");
	    btnBuscar.setBounds(128, 211, 250, 35); 
	    btnBuscar.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	        	try {
	        		String depto = javax.swing.JOptionPane.showInputDialog(ventana, "Nombre del departamento a buscar:");
	            	if(depto == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarDepartamento(depto);
	                javax.swing.JOptionPane.showMessageDialog(ventana, controlador.buscarDepartamento(depto));
	        	}
	        	catch(Exception e) {
	        		javax.swing.JOptionPane.showMessageDialog(ventana, "[ERROR]: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
	        	}
	    	}
	    });
	    add(btnBuscar);
	    
	    JButton btnEditar = new JButton("4. Editar Departamento");
	    btnEditar.setBounds(128, 276, 250, 35); 
	    btnEditar.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	        	try {
	        		String deptoAntiguo = javax.swing.JOptionPane.showInputDialog(ventana, "Nombre actual del departamento:");
	            	if(deptoAntiguo == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarDepartamento(deptoAntiguo);
	            	
	            	String deptoNuevo = javax.swing.JOptionPane.showInputDialog(ventana, "Nuevo nombre:");
	            	if(deptoNuevo == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarDepartamento(deptoNuevo);
	            	
	            	controlador.editarDepartamento(deptoAntiguo, deptoNuevo);
	                javax.swing.JOptionPane.showMessageDialog(ventana, "Departamento editado.");
	        	}
	        	catch(Exception e) {
	        		javax.swing.JOptionPane.showMessageDialog(ventana, "[ERROR]: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
	        	}
	    	}
	    });
	    add(btnEditar);
	    
	    JButton btnEliminar = new JButton("5. Eliminar Departamento");
	    btnEliminar.setBounds(128, 339, 250, 40); 
	    btnEliminar.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	        	
	        	try {
	        		String depto = javax.swing.JOptionPane.showInputDialog(ventana, "Nombre del departamento a eliminar:");
	            	if(depto == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarDepartamento(depto);
	            	
	            	controlador.eliminarDepartamento(depto);
	                javax.swing.JOptionPane.showMessageDialog(ventana, "Departamento eliminado con éxito.");
	        	}
	        	
	        	catch(Exception e) {
	        		javax.swing.JOptionPane.showMessageDialog(ventana, "[ERROR]: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
	        	}
	            
	        }
	    });
	    add(btnEliminar);
	    
	    JButton btnVolver = new JButton("Regresar al menú principal");
	    btnVolver.setBounds(128, 411, 250, 40);
	    btnVolver.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		ventana.mostrarMenuPrincipal();
	    	}
	    });
	    add(btnVolver);
	}   
}
