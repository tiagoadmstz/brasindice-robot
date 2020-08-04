package io.github.tiagoadmstz.dal.firebird.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "BRANOVOS")
public class Novo implements Serializable {

    private static final long serialVersionUID = -7510225811285438522L;
    @Id
    @Column(name = "BRANOVOCLAB")
    private String cLab;
    @Column(name = "BRANOVOLABO")
    private String labO;
    @Column(name = "BRANOVOCMED")
    private String cMed;
    @Column(name = "BRANOVOMEDI")
    private String medi;
    @Column(name = "BRANOVOCAPR")
    private String capr;
    @Column(name = "BRANOVOAPRE")
    private String apre;
    @Column(name = "BRANOVOTIPO")
    private String tipo;
    @Column(name = "BRANOVOGGREM")
    private String gGrem;
    @Column(name = "BRANOVOEAN")
    private String ean;
    @Column(name = "BRANOVOTISS")
    private String tiss;
    @Column(name = "TUSS")
    private String tuss;

}
