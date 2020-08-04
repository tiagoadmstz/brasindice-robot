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
@Table(name = "BRAHIERARQUIA")
public class Hierarquia implements Serializable {

    private static final long serialVersionUID = -6481688903540277252L;
    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "SUPERIOR")
    private Long superior;
    @Column(name = "DESCRICAO")
    private String descricao;
    @Column(name = "NIVEL")
    private Integer nivel;

}
