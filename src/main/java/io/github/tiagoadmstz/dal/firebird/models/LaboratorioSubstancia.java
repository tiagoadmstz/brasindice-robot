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
@Table(name = "BRALABORATORIOSUBSTANCIAS")
public class LaboratorioSubstancia implements Serializable {

    private static final long serialVersionUID = -2741369214559427976L;
    @Id
    @Column(name = "BRALABCODIGO")
    private Long codigo;
    @Column(name = "BRALABSUBS")
    private String subs;
    @Column(name = "BRALABSUBSDESCRICAO")
    private String descricao;

}
