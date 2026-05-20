package entidadesmongo;

/**
 *
 *
 * @author Brian Sandoval - 262741
 */
public class ReporteAsistencia {

    private final long asistidos;
    private final long pendientes;
    private final long totalVendidos;

    public ReporteAsistencia(long asistidos, long pendientes) {
        this.asistidos = asistidos;
        this.pendientes = pendientes;
        this.totalVendidos = asistidos + pendientes;
    }

    public long getAsistidos() {
        return asistidos;
    }

    public long getPendientes() {
        return pendientes;
    }

    public long getTotalVendidos() {
        return totalVendidos;
    }
}
