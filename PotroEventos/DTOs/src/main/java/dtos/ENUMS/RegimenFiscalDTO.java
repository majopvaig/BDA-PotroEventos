/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package dtos.ENUMS;

/**
 *
 * @author aaron
 */
public enum RegimenFiscalDTO {
   
    // Personas Morales
    R_601("601", "General de Ley Personas Morales"),
    R_603("603", "Personas Morales con Fines no Lucrativos"),
    R_620("620", "Sociedades Cooperativas de Producción"),
    R_623("623", "Opcional para Grupos de Sociedades"),
    R_624("624", "Coordinados"),
    
    // Personas Físicas - Empresariales
    R_612("612", "Personas Físicas con Actividades Empresariales y Profesionales"),
    R_621("621", "Incorporación Fiscal (RIF)"),
    R_626("626", "Régimen Simplificado de Confianza (RESICO)"),
    R_622("622", "Actividades Agrícolas, Ganaderas, Silvícolas y Pesqueras"),
    R_625("625", "Actividades Empresariales con ingresos a través de Plataformas Tecnológicas"),
    
    // Personas Físicas - Ingresos varios
    R_605("605", "Sueldos y Salarios e Ingresos Asimilados a Salarios"),
    R_606("606", "Arrendamiento"),
    R_608("608", "Demás ingresos"),
    R_610("610", "Residentes en el Extranjero sin Establecimiento Permanente"),
    R_611("611", "Ingresos por Dividendos"),
    R_614("614", "Ingresos por intereses"),
    R_616("616", "Sin obligaciones fiscales");
    
    private final String codigo;
    private final String descripcion;
    
    RegimenFiscalDTO(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    @Override
    public String toString() {
        return codigo + " - " + descripcion;
    }
}
