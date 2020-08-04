package io.github.tiagoadmstz.dal.firebird.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "BRAMEDICAMENTOS")
public class Medicamento implements Serializable {

    private static final long serialVersionUID = -4681124745424733870L;
    @Id
    @Column(name = "BRAMEDCODIGO")
    private String codigo;
    @Column(name = "BRAMEDDESCRICAO", columnDefinition = "blob")//BRALABSUBSDESCRICAO
    private String descricao;
    @Column(name = "BRAMEDFLAG")
    private String flag;
    @Column(name = "BRAMEDTIPO")
    private String tipo;
    @Column(name = "SELECAO")
    private String selecao;
    @Column(name = "RESTRITO")
    private String restrito;
    @Column(name = "NOVA_ORDEM")
    private Integer novaOrdem;
    @Column(name = "PARENTERAL")
    private String parenteral;
    @Column(name = "LIBERADO")
    private String liberado;
    @ManyToOne
    @JoinTable(name = "BRAMEDICAMENTOSUBSTANCIAS",
            joinColumns = @JoinColumn(name = "BRAMEDSUBCODIGOMED", referencedColumnName = "BRAMEDCODIGO"),
            inverseJoinColumns = @JoinColumn(name = "BRAMEDSUBCODIGOSUB", referencedColumnName = "BRASUBCODIGO")
    )
    private Substancia substancia;

}
