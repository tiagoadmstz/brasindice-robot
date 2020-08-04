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
@Table(name = "BRAESTADOS")
public class Estado implements Serializable {

    private static final long serialVersionUID = 6002565622066333674L;
    @Id
    @Column(name = "ESTADO")
    private String estado;
    @Column(name = "DESCRICAO")
    private String descricao;
    @Column(name = "ALIQUOTA")
    private String aliquota;
    @Column(name = "ICMS_ONCOLOGICO")
    private String icmsOncologico;
    @Column(name = "ALIQUOTAGENERICO")
    private String aliquotaGenerico;

}
