@Entity
@Table(name = "reserva")
public class Reserva {

    @Id
    private Long id;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(name = "id_evento", nullable = false)
    private Long idEvento;

    @Columng(name = "id_sector", nullable = false)
    private Long idSector;

    private int fila;
    private int asiento;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDateTime fechaVencimiento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoReserva estadoReserva;
}