package io.github.tiagoadmstz.dal.firebird.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "BRAAUXILIAR")
public class Auxiliar implements Serializable {

    private static final long serialVersionUID = -1285465666205313404L;

    @Id
    @Column(name = "ID1")
    private BigDecimal id1;
    @Id
    @Column(name = "ID2")
    private BigDecimal id2;
    @Id
    @Column(name = "ID3")
    private BigDecimal id3;
    @Column(name = "ID4", unique = true)
    private BigDecimal id4;
    @Column(name = "ID5", unique = true)
    private BigDecimal id5;
    @Column(name = "ID6", unique = true)
    private BigDecimal id6;
    @Column(name = "ID7", unique = true)
    private BigDecimal id7;
    @Column(name = "ID8", unique = true)
    private BigDecimal id8;
    @Column(name = "DESCRICAO1")
    private String descricao1;
    @Column(name = "DESCRICAO2")
    private String descricao2;
    @Column(name = "DESCRICAO3")
    private String descricao3;
    @Column(name = "DESCRICAO4")
    private String descricao4;
    @Column(name = "DESCRICAO5")
    private String descricao5;
    @Column(name = "DESCRICAO6")
    private String descricao6;
    @Column(name = "DESCRICAO7")
    private String descricao7;
    @Column(name = "DESCRICAO8")
    private String descricao8;
    @Column(name = "TEM_MED")
    private String temMed;

}
