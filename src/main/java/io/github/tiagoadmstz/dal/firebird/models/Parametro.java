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
@Table(name = "BRAPARAMETROS")
public class Parametro implements Serializable {

    private static final long serialVersionUID = 6433168010791113609L;
    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "BRAPARAESTADO")
    private String paraEstado;
    @Column(name = "BRANUMEROEDICAO")
    private String numeroEdicao;
    @Column(name = "BRADATAEDICAO")
    private String dataEdicao;
    @Column(name = "BRAMOSTRAEAN")
    private String mostraEan;
    @Column(name = "BRAPARAMETROS")
    private String parametros;

}
