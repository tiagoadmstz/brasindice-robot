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
@Table(name = "BRAPRECOS")
public class Preco implements Serializable {

    private static final long serialVersionUID = 5523080698852447110L;
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
    private String descricao;
    @Column(name = "BRALABDESCRICAO")
    private String labDescricao;
    @Column(name = "BRAPREZF")
    private String prezf;
    @Column(name = "BRAPREEX1")
    private String ex1;
    @Column(name = "BRAPREEX2")
    private String ex2;
    @Column(name = "BRAPREEX3")
    private String ex3;
    @Column(name = "BRAPREEX4")
    private String ex4;
    @Column(name = "BRAPREEX5")
    private String ex5;
    @Column(name = "BRAPREEX6")
    private String ex6;
    @Column(name = "BRAPRESTISS")
    private String tiss;
    @Column(name = "BRAPPRE_ZERO")
    private String zero;
    @Column(name = "BRAPPRE_DOZE")
    private String doze;
    @Column(name = "BRAPPRE_ZEROFAB")
    private String zeroFab;
    @Column(name = "BRAPPRE_DOZEFAB")
    private String dozeFab;
    @Column(name = "BRAPPRE_ZFFAB")
    private String zfFab;
    @Column(name = "TUSS")
    private String tuss;
    @Column(name = "NOVA_ORDEM")
    private String novaOrdem;
    @Column(name = "PMC17_5")
    private String pmc175;
    @Column(name = "PFAB17_5")
    private String pFab175;
    @Column(name = "PMCZF17")
    private String pmczf17;
    @Column(name = "PMCZF17_5")
    private String pmczf175;
    @Column(name = "PMCZF18")
    private String pmczf18;
    @Column(name = "PFABZF17")
    private String pfabzf17;
    @Column(name = "PFABZF17_5")
    private String pfabzf175;
    @Column(name = "PFABZF18")
    private String pfabzf18;

}
