/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Entitys.ENUMS;

/**
 *
 * @author aaron
 */
public enum UsoCfdi {
    
    // Generales
    G01("G01", "Adquisición de mercancías"),
    G02("G02", "Devoluciones, descuentos o bonificaciones"),
    G03("G03", "Gastos en general"),
    
    // Inversiones
    I01("I01", "Construcciones"),
    I02("I02", "Mobiliario y equipo de oficina por inversiones"),
    I03("I03", "Equipo de transporte"),
    I04("I04", "Equipo de cómputo y accesorios"),
    I05("I05", "Dados, troqueles, moldes, matrices y herramental"),
    I06("I06", "Comunicaciones telefónicas"),
    I07("I07", "Comunicaciones satelitales"),
    I08("I08", "Otra maquinaria y equipo"),
    
    // Deducciones personales
    D01("D01", "Honorarios médicos, dentales y gastos hospitalarios"),
    D02("D02", "Gastos médicos por incapacidad o discapacidad"),
    D03("D03", "Gastos funerarios"),
    D04("D04", "Donativos"),
    D05("D05", "Intereses reales efectivamente pagados por créditos hipotecarios"),
    D06("D06", "Aportaciones voluntarias al SAR"),
    D07("D07", "Primas por seguros de gastos médicos"),
    D08("D08", "Gastos de transportación escolar obligatoria"),
    D09("D09", "Depósitos en cuentas para el ahorro"),
    D10("D10", "Pagos por servicios educativos"),
    
    // Otros
    CP01("CP01", "Pagos"),
    CN01("CN01", "Nómina"),
    S01("S01", "Sin efectos fiscales"),
    P01("P01", "Por definir");
    
    private final String codigo;
    private final String descripcion;
    
    UsoCfdi(String codigo, String descripcion) {
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
