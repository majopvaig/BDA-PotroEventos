package daos;

import Entitys.AsientoEvento;
import Entitys.Asistencia;
import Entitys.Boleto;
import Entitys.ENUMS.EstadoBoleto;
import Entitys.ENUMS.ReservacionEstado;
import Entitys.Empleado;
import Entitys.Reservacion;
import entidadesmongo.ReporteAsistencia;
import excepciones.PersistenciaException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Pruebas unitarias para ReservacionDAO. Requiere una instancia de MongoDB
 * corriendo en localhost:27017 con la base de datos "potro_eventos".
 *
 * Ejecutar en orden con @TestMethodOrder para que los datos insertados en
 * pruebas "buenas" estén disponibles para las siguientes.
 *
 * @author Brian Sandoval - 262741
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReservacionDAOTest {

    private static ReservacionDAO dao;

    // ─── IDs reales de tu base de datos ───────────────────────────────────────
    // Cambia estos valores por IDs que existan en tu colección
    private static final String ID_USUARIO_EXISTENTE = "6a0d3cd8c5a69f148aed5618";
    private static final String ID_EVENTO_EXISTENTE = "6a0d3d47827351936d441536"; // "Show de Talentos"
    private static final String TOKEN_EXISTENTE = "c180add2-186c-429b-8777-e686f59a178b";
    private static final String ID_RESERVACION_EXISTENTE = "6a0d73650368b734346d9d4f";
    private static final String ID_EMPLEADO_EXISTENTE = "6a0d3e3cceeef465e744152e";

    // IDs que NO existen en la base de datos
    private static final String ID_INEXISTENTE = "000000000000000000000000";
    private static final String TOKEN_INEXISTENTE = "token-que-no-existe-jamas";

    @BeforeAll
    static void setUp() {
        // Obtenemos la instancia singleton del DAO
        dao = ReservacionDAO.getInstance();
    }

    // =========================================================================
    // guardarReservacion
    // =========================================================================
    /**
     * PRUEBA BUENA — guardarReservacion Crea una reservación válida con todos
     * sus campos y la guarda. Se espera que retorne true y no lance
     * excepciones.
     */
    @Test
    @Order(1)
    @DisplayName("guardarReservacion — BUENA: guarda correctamente una reservación válida")
    void guardarReservacion_buena() throws PersistenciaException {
        // Arrange: construir una reservación mínima válida
        Reservacion reservacion = new Reservacion();
        reservacion.setTotal(0.0);
        reservacion.setEstado(ReservacionEstado.ACTIVA);

        Boleto boleto = new Boleto();
        boleto.setToken(java.util.UUID.randomUUID().toString());
        boleto.setEstadoBoleto(EstadoBoleto.ACTIVO);
        boleto.setPrecio(0.0);
        boleto.setCodigoQR("/src/main/resources/qrs-boletos/test_" + System.currentTimeMillis() + ".png");
        reservacion.setBoleto(boleto);

        // Act
        boolean resultado = dao.guardarReservacion(reservacion);

        // Assert
        assertTrue(resultado, "La reservación debería guardarse exitosamente");
    }

    /**
     * PRUEBA MALA — guardarReservacion Intenta guardar null como reservación.
     * Se espera que lance PersistenciaException con mensaje descriptivo.
     */
    @Test
    @Order(2)
    @DisplayName("guardarReservacion — MALA: lanza excepción al recibir reservación nula")
    void guardarReservacion_mala_nula() {
        // Act & Assert
        PersistenciaException ex = assertThrows(
                PersistenciaException.class,
                () -> dao.guardarReservacion(null),
                "Debería lanzar PersistenciaException al guardar null"
        );
        assertNotNull(ex.getMessage(), "El mensaje de la excepción no debe ser nulo");
    }

    // =========================================================================
    // obtenerReservacionesUsuario
    // =========================================================================
    /**
     * PRUEBA BUENA — obtenerReservacionesUsuario Consulta las reservaciones de
     * un usuario que SÍ existe en la base. Se espera una lista no nula (puede
     * estar vacía si no tiene reservaciones, pero no debe lanzar excepción).
     */
//    @Test
//    @Order(3)
//    @DisplayName("obtenerReservacionesUsuario — BUENA: retorna lista para usuario existente")
//    void obtenerReservacionesUsuario_buena() throws PersistenciaException {
//        // Act
//        List<Reservacion> reservaciones = dao.obtenerReservacionesUsuario(ID_USUARIO_EXISTENTE);
//
//        // Assert
//        assertNotNull(reservaciones, "La lista no debe ser nula");
//        assertFalse(reservaciones.isEmpty(), "El usuario debería tener al menos una reservación");
//    }
    /**
     * PRUEBA MALA — obtenerReservacionesUsuario Pasa un ID con formato inválido
     * (no es un ObjectId de 24 chars). Se espera que lance
     * PersistenciaException.
     */
    @Test
    @Order(4)
    @DisplayName("obtenerReservacionesUsuario — MALA: lanza excepción con ID de formato inválido")
    void obtenerReservacionesUsuario_mala_idInvalido() {
        // Act & Assert
        assertThrows(
                PersistenciaException.class,
                () -> dao.obtenerReservacionesUsuario("id-no-valido-$$"),
                "Debería lanzar PersistenciaException con un ID malformado"
        );
    }

    // =========================================================================
    // obtenerBoleto
    // =========================================================================
    /**
     * PRUEBA BUENA — obtenerBoleto Obtiene el boleto de una reservación que
     * existe en la base. Se espera un objeto Boleto no nulo con token válido.
     */
//    @Test
//    @Order(5)
//    @DisplayName("obtenerBoleto — BUENA: retorna boleto para reservación existente")
//    void obtenerBoleto_buena() throws PersistenciaException {
//        // Act
//        Boleto boleto = dao.obtenerBoleto(ID_RESERVACION_EXISTENTE);
//
//        // Assert
//        assertNotNull(boleto, "El boleto no debe ser nulo");
//        assertNotNull(boleto.getToken(), "El token del boleto no debe ser nulo");
//    }
    /**
     * PRUEBA MALA — obtenerBoleto Busca el boleto de una reservación con ID que
     * no existe. Se espera que retorne null sin lanzar excepción.
     */
    @Test
    @Order(6)
    @DisplayName("obtenerBoleto — MALA: retorna null para reservación inexistente")
    void obtenerBoleto_mala_inexistente() throws PersistenciaException {
        // Act
        Boleto boleto = dao.obtenerBoleto(ID_INEXISTENTE);

        // Assert
        assertNull(boleto, "Debería retornar null para una reservación que no existe");
    }

    // =========================================================================
    // buscarPorToken
    // =========================================================================
    /**
     * PRUEBA BUENA — buscarPorToken Busca un boleto con un token que existe en
     * la base de datos. Se espera un Boleto no nulo cuyo token coincida.
     */
    @Test
    @Order(7)
    @DisplayName("buscarPorToken — BUENA: encuentra boleto con token existente")
    void buscarPorToken_buena() throws PersistenciaException {

        // Arrange
        Reservacion reservacion = new Reservacion();
        reservacion.setTotal(0.0);
        reservacion.setEstado(ReservacionEstado.ACTIVA);

        Boleto boletoGuardado = new Boleto();
        String token = java.util.UUID.randomUUID().toString();

        boletoGuardado.setToken(token);
        boletoGuardado.setEstadoBoleto(EstadoBoleto.ACTIVO);
        boletoGuardado.setPrecio(0.0);
        boletoGuardado.setCodigoQR("/src/main/resources/qrs-boletos/test_token_"
                + System.currentTimeMillis() + ".png");

        reservacion.setBoleto(boletoGuardado);

        dao.guardarReservacion(reservacion);

        // Act
        Boleto boleto = dao.buscarPorToken(token);

        // Assert
        assertNotNull(boleto, "El boleto no debe ser nulo");
        assertEquals(token, boleto.getToken(), "El token debe coincidir");
    }

    /**
     * PRUEBA MALA — buscarPorToken Pasa un token nulo. Se espera que lance
     * PersistenciaException.
     */
    @Test
    @Order(8)
    @DisplayName("buscarPorToken — MALA: lanza excepción con token nulo")
    void buscarPorToken_mala_nulo() {
        // Act & Assert
        assertThrows(
                PersistenciaException.class,
                () -> dao.buscarPorToken(null),
                "Debería lanzar PersistenciaException con token nulo"
        );
    }

    /**
     * PRUEBA BUENA — actualizarEstado El token puede estar en cualquier estado,
     * el DAO retorna true solo si MongoDB modificó algo. Si ya estaba USADO,
     * retorna false — ambos son válidos.
     */
    @Test
    @Order(9)
    @DisplayName("actualizarEstado — BUENA: no lanza excepción con boleto válido")
    void actualizarEstado_buena() throws PersistenciaException {
        // Arrange
        Boleto boleto = new Boleto();
        boleto.setToken(TOKEN_EXISTENTE);
        boleto.setEstadoBoleto(EstadoBoleto.USADO);

        // Act — no debe lanzar excepción, el resultado true/false depende del estado previo
        boolean resultado = dao.actualizarEstado(boleto);

        // Assert: solo verificamos que ejecutó sin explotar
        // true = se modificó, false = ya tenía ese estado (ambos son correctos)
        assertNotNull(boleto.getToken(), "El token debe seguir siendo válido tras la operación");
    }

    /**
     * PRUEBA MALA — actualizarEstado Pasa un boleto nulo. Se espera que lance
     * PersistenciaException.
     */
    @Test
    @Order(10)
    @DisplayName("actualizarEstado — MALA: lanza excepción con boleto nulo")
    void actualizarEstado_mala_nulo() {
        // Act & Assert
        assertThrows(
                PersistenciaException.class,
                () -> dao.actualizarEstado(null),
                "Debería lanzar PersistenciaException con boleto nulo"
        );
    }

    // =========================================================================
    // registrarAsistencia
    // =========================================================================
    /**
     * PRUEBA BUENA — registrarAsistencia Registra la asistencia de un boleto
     * ACTIVO con empleado válido. Se espera que retorne el objeto Asistencia
     * sin lanzar excepción.
     *
     * NOTA: Para que funcione el boleto debe estar en estado ACTIVO. Si el
     * token ya fue marcado como USADO, cambia TOKEN_EXISTENTE por el token de
     * un boleto en estado ACTIVO.
     */
    @Test
    @Order(11)
    @DisplayName("registrarAsistencia — BUENA: registra asistencia correctamente")
    void registrarAsistencia_buena() throws PersistenciaException {

        // Arrange
        Reservacion reservacion = new Reservacion();
        reservacion.setTotal(0.0);
        reservacion.setEstado(ReservacionEstado.ACTIVA);

        Boleto boleto = new Boleto();
        boleto.setToken(java.util.UUID.randomUUID().toString());
        boleto.setEstadoBoleto(EstadoBoleto.ACTIVO);
        boleto.setPrecio(0.0);
        boleto.setCodigoQR("/src/main/resources/qrs-boletos/test_asistencia_"
                + System.currentTimeMillis() + ".png");

        reservacion.setBoleto(boleto);

        // Guardar reservación primero
        boolean guardado = dao.guardarReservacion(reservacion);

        assertTrue(guardado, "La reservación debe guardarse correctamente");

        Empleado empleado = new Empleado();
        empleado.setIdEmpleado(ID_EMPLEADO_EXISTENTE);

        Asistencia asistencia = new Asistencia();
        asistencia.setEmpleado(empleado);

        // Act
        Asistencia resultado = dao.registrarAsistencia(boleto, asistencia);

        // Assert
        assertNotNull(resultado, "La asistencia registrada no debe ser nula");
        assertNotNull(resultado.getEmpleado(), "La asistencia debe contener empleado");
        assertEquals(
                ID_EMPLEADO_EXISTENTE,
                resultado.getEmpleado().getIdEmpleado(),
                "El empleado registrado debe coincidir"
        );
    }

    /**
     * PRUEBA MALA — registrarAsistencia Intenta registrar asistencia con boleto
     * nulo. Se espera que lance PersistenciaException.
     */
    @Test
    @Order(12)
    @DisplayName("registrarAsistencia — MALA: lanza excepción con boleto nulo")
    void registrarAsistencia_mala_nulo() {
        // Arrange
        Asistencia asistencia = new Asistencia();

        // Act & Assert
        assertThrows(
                PersistenciaException.class,
                () -> dao.registrarAsistencia(null, asistencia),
                "Debería lanzar PersistenciaException con boleto nulo"
        );
    }

    // =========================================================================
    // obtenerReporteAsistencia
    // =========================================================================
    /**
     * PRUEBA BUENA — obtenerReporteAsistencia Solicita el reporte de un evento
     * que tiene reservaciones en la base. Se espera un ReporteAsistencia no
     * nulo con totales >= 0.
     */
    @Test
    @Order(13)
    @DisplayName("obtenerReporteAsistencia — BUENA: retorna reporte para evento existente")
    void obtenerReporteAsistencia_buena() {
        // Act
        ReporteAsistencia reporte = dao.obtenerReporteAsistencia(ID_EVENTO_EXISTENTE);

        // Assert
        assertNotNull(reporte, "El reporte no debe ser nulo");
        assertTrue(reporte.getAsistidos() >= 0, "Los asistidos no pueden ser negativos");
        assertTrue(reporte.getPendientes() >= 0, "Los pendientes no pueden ser negativos");
        assertEquals(
                reporte.getAsistidos() + reporte.getPendientes(),
                reporte.getTotalVendidos(),
                "El total debe ser la suma de asistidos + pendientes"
        );
    }

    /**
     * PRUEBA MALA — obtenerReporteAsistencia Solicita el reporte de un evento
     * que NO existe. Se espera un ReporteAsistencia con todos los valores en 0
     * (el DAO retorna (0,0) ante cualquier error o sin resultados).
     */
    @Test
    @Order(14)
    @DisplayName("obtenerReporteAsistencia — MALA: retorna reporte vacío para evento inexistente")
    void obtenerReporteAsistencia_mala_inexistente() {
        // Act
        ReporteAsistencia reporte = dao.obtenerReporteAsistencia(ID_INEXISTENTE);

        // Assert
        assertNotNull(reporte, "El reporte no debe ser nulo aunque el evento no exista");
        assertEquals(0, reporte.getAsistidos(), "Sin evento no debe haber asistidos");
        assertEquals(0, reporte.getPendientes(), "Sin evento no debe haber pendientes");
        assertEquals(0, reporte.getTotalVendidos(), "Sin evento el total debe ser 0");
    }

    // =========================================================================
    // obtenerAsientosConAsistencia
    // =========================================================================
    /**
     * PRUEBA BUENA — obtenerAsientosConAsistencia Consulta los asientos
     * asistidos de un evento con asistencias registradas. Se espera una lista
     * no vacía de AsientoEvento.
     */
    @Test
    @Order(15)
    @DisplayName("obtenerAsientosConAsistencia — BUENA: retorna asientos asistidos para evento existente")
    void obtenerAsientosConAsistencia_buena() throws PersistenciaException {
        // Act
        List<AsientoEvento> asientos = dao.obtenerAsientosConAsistencia(ID_EVENTO_EXISTENTE);

        // Assert
        assertNotNull(asientos, "La lista no debe ser nula");
        // Puede estar vacía si es un evento sin asientos asignados (gratis/general)
        // pero nunca debe lanzar excepción
    }

    /**
     * PRUEBA MALA — obtenerAsientosConAsistencia ID malformado lanza
     * IllegalArgumentException desde ObjectId, que el DAO NO envuelve en
     * PersistenciaException. Se verifica la excepción real.
     */
    @Test
    @Order(16)
    @DisplayName("obtenerAsientosConAsistencia — MALA: lanza excepción con ID de formato inválido")
    void obtenerAsientosConAsistencia_mala_idInvalido() {
        // Act & Assert: el DAO lanza IllegalArgumentException porque ObjectId
        // falla antes de llegar al catch de MongoException
        assertThrows(
                IllegalArgumentException.class,
                () -> dao.obtenerAsientosConAsistencia("formato@@invalido"),
                "Debería lanzar IllegalArgumentException con un ID malformado"
        );
    }
}
