package InicioSesionEmpleado;

import dtos.EmpleadoDTO;
import excepciones.EmpleadoException;

/**
 *
 * @author Brian Sandoval - 262741
 */
public class FachadaInicioSesionEmpleado implements IInicioSesionEmpleado {

    private final ControlInicioSesionEmpleado control = ControlInicioSesionEmpleado.getInstance();

    @Override
    public EmpleadoDTO obtenerEmpleado(EmpleadoDTO empleado) throws EmpleadoException {
        return control.obtenerEmpleado(empleado);
    }

    @Override
    public EmpleadoDTO getEmpleadoSesion() {
        return control.getEmpleadoSesion();
    }

    @Override
    public void cerrarSesion() {
        control.cerrarSesion();
    }

}
