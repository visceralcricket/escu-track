package core.escutrack.view;

import core.escutrack.controller.ControladorHospital;
import core.escutrack.utils.ValidadorCamposUtils;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaCamas extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public VentanaCamas(VentanaPrincipal ventana, ControladorHospital controlador) {
		setLayout(null);
		
		JButton btnRegistrar = new JButton("1. Agregar Cama a Departamento");
		btnRegistrar.setBounds(130, 81, 250, 40); 
		btnRegistrar.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	        	
	        	try {
	            	String depto = javax.swing.JOptionPane.showInputDialog(ventana, "Departamento destino:");
	            	if(depto == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarDepartamento(depto);
	            	
	            	String cama = javax.swing.JOptionPane.showInputDialog(ventana, "ID de la nueva Cama:");
	            	if(cama == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarIdCama(cama);
	            	
	            	String prioridad = javax.swing.JOptionPane.showInputDialog(ventana, "Prioridad (entero):");
	            	if(prioridad == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarPrioridadCama(prioridad);
	            	int prioridadEntera = Integer.parseInt(prioridad);
	            	
	            	controlador.agregarCama(depto, cama, prioridadEntera);
	                javax.swing.JOptionPane.showMessageDialog(ventana, "Cama registrada con éxito.");
	        	}
	        	
	        	catch(Exception e) {
	        		javax.swing.JOptionPane.showMessageDialog(ventana, "[ERROR]: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
	        	}
	            
	        }
	    });
	    add(btnRegistrar);
	    
	    JButton btnMostrar = new JButton("2. Mostrar Camas de Departamento particular");
	    btnMostrar.setBounds(130, 142, 250, 40); 
	    btnMostrar.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	        	
	        	try {
	        		String depto = javax.swing.JOptionPane.showInputDialog(ventana, "Nombre del departamento:");
	            	if(depto == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarDepartamento(depto);
	        		
	            	String camasEncontradas = controlador.mostrarCamasPorDepartamento(depto);
	                javax.swing.JOptionPane.showMessageDialog(ventana, camasEncontradas);
	        	}
	        	
	        	catch(Exception e) {
	        		javax.swing.JOptionPane.showMessageDialog(ventana, "[ERROR]: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
	        	}
	            
	        }
	    });
	    add(btnMostrar);
	    
	    JButton btnBuscar = new JButton("3. Buscar Cama específica");
	    btnBuscar.setBounds(130, 205, 250, 40); 
	    btnBuscar.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		
	    		try {
	        		String depto = javax.swing.JOptionPane.showInputDialog(ventana, "Departamento:");
	            	if(depto == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarDepartamento(depto);
	            	
					String idCama = javax.swing.JOptionPane.showInputDialog(ventana, "ID Cama:");
	            	if(idCama == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarIdCama(idCama);

	                javax.swing.JOptionPane.showMessageDialog(ventana, controlador.buscarCama(depto, idCama));
	        	}
	    		catch(Exception e) {
	        		javax.swing.JOptionPane.showMessageDialog(ventana, "[ERROR]: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
	        	}
	    		
	        }
	    });
	    add(btnBuscar);
	    /*
	     * Funcionalidades faltantes de buscar y editar cama.
	     * 
	     * @since v0.2.3
	     * @author Felipe T.S.
	     */
	    JButton btnEditar = new JButton("4. Editar Cama");
	    btnEditar.setBounds(130, 273, 250, 40); 
	    btnEditar.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		
	    		try {
	        		String depto = javax.swing.JOptionPane.showInputDialog(ventana, "Departamento:");
	            	if(depto == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarDepartamento(depto);
	            	
	            	String idViejo = javax.swing.JOptionPane.showInputDialog(ventana, "ID actual de Cama:");
	            	if(idViejo == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarIdCama(idViejo);

					String idNuevo = javax.swing.JOptionPane.showInputDialog(ventana, "Nuevo ID (o el mismo):");
	            	if(idNuevo == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarIdCama(idNuevo);

					String prioridad = javax.swing.JOptionPane.showInputDialog(ventana, "Nueva prioridad:");
	            	if(prioridad == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarPrioridadCama(prioridad);
	            	
	            	controlador.editarCama(depto, idViejo, idNuevo, Integer.parseInt(prioridad));
	                javax.swing.JOptionPane.showMessageDialog(ventana, "Cama editada.");
	        	}
	    		catch(Exception e) {
	        		javax.swing.JOptionPane.showMessageDialog(ventana, "[ERROR]: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
	        	}
	        }
	    });
	    add(btnEditar);
	    
	    JButton btnEliminar = new JButton("5. Eliminar Cama");
	    btnEliminar.setBounds(130, 342, 250, 40); 
	    btnEliminar.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	        	
	        	try {
	        		String depto = javax.swing.JOptionPane.showInputDialog(ventana, "Departamento de la cama a eliminar:");
	            	if(depto == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarDepartamento(depto);
	            	
	            	String cama = javax.swing.JOptionPane.showInputDialog(ventana, "ID de la Cama a eliminar:");
	            	if(cama == null) return;
	            	core.escutrack.utils.ValidadorCamposUtils.validarIdCama(cama);
	            	
	            	controlador.eliminarCama(depto, cama);
	                javax.swing.JOptionPane.showMessageDialog(ventana, "Cama eliminada con éxito.");
	        	}
	        	
	        	catch(Exception e) {
	        		javax.swing.JOptionPane.showMessageDialog(ventana, "[ERROR]: " + e.getMessage(), "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
	        	}
	            
	        }
	    });
	    add(btnEliminar);
	    
	    JButton btnVolver = new JButton("Regresar al menú principal");
	    btnVolver.setBounds(130, 410, 250, 40);
	    btnVolver.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent arg0) {
	    		ventana.mostrarMenuPrincipal();
	    	}
	    });
	    add(btnVolver);
	}   
}
