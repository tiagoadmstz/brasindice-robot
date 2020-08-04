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
@Table(name = "BRAEXCLUIDOS")
public class Excluido implements Serializable {

    private static final long serialVersionUID = -605806353844110965L;
    @Id
    @Column(name = "BRAANTECLAB")
    private String antecLab;
    @Column(name = "BRAANTELABO")
    private String anteLabo;
    @Column(name = "BRAANTECMED")
    private String antecMed;
    @Column(name = "BRAANTEMEDI")
    private String anteMedi;
    @Column(name = "BRAANTECAPR")
    private String anteCapr;
    @Column(name = "BRAANTETIPO")
    private String anteTipo;
    @Column(name = "BRAANTEGGREM")
    private String anteGgrem;
    @Column(name = "BRAANTEEAN")
    private String anteEan;
    @Column(name = "BRAANTETISS")
    private String anteTiss;
    @Column(name = "TUSS")
    private String tuss;

}
