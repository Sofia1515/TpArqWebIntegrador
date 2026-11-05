@Entity
@Table(name = "precios_sectores_eventos")
public class PrecioSectorEvento {
    @Id
    private Long id;

    @ManyToOne(optional = false)
    @Column(name = "id_evento", referencedColumnName = "id", nullable = false)
    private Evento evento;

    @ManyToOne(optional = false)
    @Column(name = "id_sector", referencedColumnName = "id", nullable = false)
    private Sector sector;

    @Column(nullable = false)
    private double precio;
}