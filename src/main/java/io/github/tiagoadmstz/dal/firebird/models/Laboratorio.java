package io.github.tiagoadmstz.dal.firebird.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "BRALABORATORIOS")
public class Laboratorio implements Serializable {

    private static final long serialVersionUID = -1271833397066829276L;
    @Id
    @Column(name = "BRALABCODIGO")
    private Long codigo;
    @Column(name = "BRALABDESCRICAO")
    private String descricao;
    @Column(name = "BRALABFLAG")
    private String flag;
    @Column(name = "BRALABMED")
    private String med;
    @Column(name = "BRALABMAT")
    private String mat;
    @Column(name = "BRALABSOL")
    private String sol;
    @Column(name = "FLAGSELECAO")
    private String flagSelecao;
    @Column(name = "NOVA_ORDEM")
    private Integer novaOrdem;
    @ManyToOne
    @JoinTable(name = "BRALABORATORIOSUBSTANCIAS",
            joinColumns = @JoinColumn(name = "BRALABCODIGO", referencedColumnName = "BRALABCODIGO"),
            inverseJoinColumns = @JoinColumn(name = "BRALABSUBS", referencedColumnName = "BRASUBCODIGO")
    )
    private Substancia substancia;
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "BRALABSUBSDESCRICAO", insertable = false, updatable = false)
    @CollectionTable(name = "BRALABORATORIOSUBSTANCIAS", joinColumns = @JoinColumn(name = "BRALABCODIGO"))
    private List<String> labSubsDescricao;

}
