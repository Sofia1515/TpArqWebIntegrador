@Entity
@Table(name = "lugar")
public class Lugar {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "direccion", nullable = false)
    private String direccion;

    private Double latitud;
    private Double longitud;

    @OneToMany(mappedBy = "lugar", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sector> sectores = new ArrayList();
}