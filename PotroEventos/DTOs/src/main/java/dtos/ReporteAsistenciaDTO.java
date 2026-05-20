package dtos;

/**
 * @author Brian Sandoval - 262741
 */
public class ReporteAsistenciaDTO {

    private final long asistidos;
    private final long pendientes;
    private final long totalVendidos;

    public ReporteAsistenciaDTO(long asistidos, long pendientes) {
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
