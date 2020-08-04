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
@Table(name = "BRAPRECOSSUBSTANCIAS")
public class PrecoSubstancia implements Serializable {

    private static final long serialVersionUID = 8808511881248390409L;
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
    @Column(name = "BRAMEDSUBCODIGOSUB")
    private String medSubCodigoSub;
    @Column(name = "BRAMEDDESCRICAO")
    private String medDescricao;
    @Column(name = "BRAAPRDESCRICAO")
    private String aprDescricao;
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
    @Column(name = "BRAPRE_ZERO")
    private String zero;
    @Column(name = "BRAPRE_DOZE")
    private String doze;
    @Column(name = "BRAPRE_ZEROFAB")
    private String zeroFab;
    @Column(name = "BRAPRE_DOZEFAB")
    private String dozeFab;
    @Column(name = "BRAPPRE_ZFFAB")
    private String zfFab;
    @Column(name = "NOVA_ORDEM")
    private String novaOrdem;

}
