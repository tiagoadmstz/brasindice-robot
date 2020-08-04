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
@Table(name = "BRASUBSTANCIAS")
public class Substancia implements Serializable {

    private static final long serialVersionUID = -6370088979215362712L;
    @Id
    @Column(name = "BRASUBCODIGO")
    private String codigo;
    @Column(name = "BRASUBDESCRICAO")
    private String descricao;
    @Column(name = "ONCOLOGICO")
    private String oncologico;

}
