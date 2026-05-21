package dtos;

/**
 *
 * @author Brian Sandoval - 262741
 */
public class EmpleadoDTO {

    private String idEmpleado;
    private String correo;
    private String contrasenia;

    public EmpleadoDTO() {
    }

    public EmpleadoDTO(String idEmpleado, String correo, String contrasenia) {
        this.idEmpleado = idEmpleado;
        this.correo = correo;
        this.contrasenia = contrasenia;
    }

    public EmpleadoDTO(String correo, String contrasenia) {
        this.correo = correo;
        this.contrasenia = contrasenia;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

}
