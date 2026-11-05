@Entity
@Table(name= "sector")
public class Sector {
    @Id
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoSector tipo;

    private int capacidadMaxima;
    private int filas;
    private int asientosPorFila;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_lugar", referencedColumnName = "id", nullable = false)
    private Lugar lugar;
}