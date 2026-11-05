@Entity
@Table(name = "compra")
public class Compra {

    @Id
    private Long id;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(name = "id_evento", nullable = false)
    private Long idEvento;

    @Column(name = "monto_total", nullable = false)
    private Double montoTotal;

    @Column(name = "medio_pago", nullable = false)
    private String medioDePago;

    @Column(name = "fecha_compra", nullable = false)
    private LocalDateTime fechaCompra;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Entrada> entradas = new ArrayList<>();
}