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
@Table(name = "BRAMEDICAMENTOSUBSTANCIAS")
public class MedicamentoSubstancia implements Serializable {

    @Id
    @Column(name = "BRAMEDSUBCODIGOSUB")
    private String codigoSub;
    @Column(name = "BRAMEDSUBCODIGOMED")
    private String codigoMed;

}
