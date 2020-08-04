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
@Table(name = "BRAAPRESENTACOES")
public class Apresentacao implements Serializable {

    private static final long serialVersionUID = -644221181835600595L;
    @Id
    @Column(name = "BRAAPRCODIGO")
    private String codigo;
    @Column(name = "BRAAPRDESCRICAO")
    private String descricao;
    @Column(name = "BRAAPRFLAG")
    private String flag;
    @Column(name = "BRAAPRQTDE")
    private BigDecimal quantidade;
    @Column(name = "BRAAPRIPI")
    private BigDecimal ipi;
    @Column(name = "FLAGSELECAO")
    private String flagSelecao;
    @Column(name = "NOVA_ORDEM")
    private String novaOrdem;

}
