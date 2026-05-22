/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entitys;

/**
 *
 * @author maria
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

    public ReporteAsistencia(long asistidos, long pendientes, long totalVendidos) {
        this.asistidos = asistidos;
        this.pendientes = pendientes;
        this.totalVendidos = totalVendidos;
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
