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
@Table(name = "DESCPRECO")
public class DescricaoPreco implements Serializable {

    private static final long serialVersionUID = 464455432268428076L;
    @Id
    @Column(name = "BRAPRELABOR")
    private String laboratorio;
    @Column(name = "BRAPREMEDIC")
    private String medic;
    @Column(name = "BRAPREAPRE")
    private String apre;
    @Column(name = "BRAPRETIPO")
    private String tipo;
    @Column(name = "BRAPREEDICAO")
    private String edicao;
    @Column(name = "BRAPREPORTARIA")
    private String portaria;
    @Column(name = "BRAPREGENERICO")
    private String generico;
    @Column(name = "BRAPREEXCECAO")
    private String excecao;
    @Column(name = "BRAPREALIQ1")
    private String aliq1;
    @Column(name = "BRAPREALIQ2")
    private String aliq2;
    @Column(name = "BRAPREALIQ3")
    private String aliq3;
    @Column(name = "BRAPREALIQ4")
    private String aliq4;
    @Column(name = "BRAPREALIQ5")
    private String aliq5;
    @Column(name = "BRAPREALIQ6")
    private String aliq6;
    @Column(name = "BRAPREIPI")
    private String ipi;
    @Column(name = "BRAPREHIERARQUIA")
    private String hierarquia;
    @Column(name = "BRAPREEAN")
    private String ean;
    @Column(name = "BRAPRECGREM")
    private String grem;
    @Column(name = "BRAPREMEDSOLU")
    private String medSolu;
    @Column(name = "BRAMEDDESCRICAO")
    private String medDescricao;
    @Column(name = "BRAAPRDESCRICAO")
    private String aprDescricao;
    @Column(name = "BRALABDESCRICAO")
    private String labDescricao;

}
