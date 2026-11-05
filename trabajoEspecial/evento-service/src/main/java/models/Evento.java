@Entity
@Table(name = "evento")
public class Evento {
    @Id
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String tipo;

    @Column(name = "id_organizador", nullable = false)
    private Long organizadorId;

    @ElementCollection
    @CollectionTable(name = "evento_participantes", joinsColumns = @JoinColumn(name = "id_evento"))
    @Column(name = "participante")
    private List<String> participantes = new ArrayList<>();

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_lugar", referencedColumnName = "id", nullable = false)
    private Lugar lugar;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoEvento estado;
}