@Entity
@Table(name = "entradas")
public class Entrada {

    @Id
    private Long id;

    @ManyToOne(optional = false)
    @Column(name = "id_compra", referencedColumnName = "id", nullable = false)
    private Compra compra;

    @Column(name = "id_sector", nullable = false)
    private Long idSector;

    private int fila;
    private int asiento;

    @Column(name = "codigo_qr", nullable = false, unique = true)
    private String CodigoQR;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoEntrada estadoEntrada;
}