package io.github.tiagoadmstz.dal.oracle.models;

import io.github.tiagoadmstz.dal.converters.PortariaPisCofinsConverter;
import io.github.tiagoadmstz.dal.converters.TipoMatMedConverter;
import io.github.tiagoadmstz.enumerated.PORTARIA_PISCOFINS;
import io.github.tiagoadmstz.enumerated.TIPO_MAT_MED;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "CAD_BRASINDICE", indexes = {
        @Index(name = "R_CB_ID_INSERCAO", columnList = "ID_INSERCAO"),
        @Index(name = "XIF3CAD_BRASINDICE", columnList = "NOME_MEDICAMENTO"),
        @Index(name = "XIF4CAD_BRASINDICE", columnList = "COD_TISS"),
        @Index(name = "XIF5CAD_BRASINDICE", columnList = "CODIGO_MEDICAMENTO"),
        @Index(name = "XPKCAD_BRASINDICE", columnList = "SEQUENCIAL,DATA_IMPORTACAO,CODIGO_LABORATORIO,CODIGO_MEDICAMENTO,CODIGO_APRESENTACAO"),
        @Index(name = "XPP001CAD_BRASINDICE", columnList = "CODIGO_LABORATORIO,CODIGO_MEDICAMENTO,DATA_IMPORTACAO"),
        @Index(name = "XPP002CAD_BRASINDICE", columnList = "DATA_IMPORTACAO")
})
@SequenceGenerator(name = "SEQ_CAD_BRASINDICE", allocationSize = 1)
public class BrasindiceDataModel implements Serializable {

    private static final long serialVersionUID = -7947433967808749873L;
    @Id
    @Column(name = "SEQUENCIAL", length = 20)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CAD_BRASINDICE")
    private Long sequencial;
    @Builder.Default
    @Column(name = "ID_INSERCAO")
    private String idInsercao = "PLANO";
    @Builder.Default
    @Column(name = "DATA_IMPORTACAO", columnDefinition = "date")
    private LocalDate dataImportacao = LocalDate.now();
    @Column(name = "CODIGO_LABORATORIO", length = 12)
    private Long codigoLaboratorio;
    @Column(name = "NOME_LABORATORIO", length = 40)
    private String nomeLaboratorio;
    @Column(name = "CODIGO_MEDICAMENTO", length = 12)
    private Long codigoMedicamento;
    @Column(name = "NOME_MEDICAMENTO", length = 80)
    private String nomeMedicamento;
    @Column(name = "CODIGO_APRESENTACAO", length = 4)
    private String codigoApresentacao;
    @Column(name = "NOME_APRESENTACAO", length = 150)
    private String nomeApresentacao;
    @Column(name = "PRECO_MEDICAMENTO", length = 12, scale = 2)
    private BigDecimal precoMedicamento;
    @Column(name = "QUANTIDADE", length = 4)
    private Long quantidade;
    @Column(name = "TIPO_PRECO", length = 4)
    private String tipoPreco;
    @Column(name = "PRECO_UNITARIO", length = 12, scale = 2)
    private BigDecimal precoUnitario;
    @Column(name = "EDICAO", length = 5)
    private String edicao;
    @Column(name = "IPI_MEDICAMENTO", length = 5, scale = 2)
    private BigDecimal ipiMedicamento;
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "PORTARIA_PISCOFINS", length = 1)
    @Convert(converter = PortariaPisCofinsConverter.class)
    private PORTARIA_PISCOFINS portariaPisCofins = PORTARIA_PISCOFINS.NAO;
    @Column(name = "CODIGO_BARRA_EAN", length = 13)
    private String codigoBarraEan;
    @Column(name = "CODIGO_BRASINDICE_TISS", length = 10)
    private String codigoBrasindiceTiss;
    @Column(name = "COD_TISS", length = 8)
    private String codigoTiss;
    @Column(name = "DATA_PUBLICACAO", columnDefinition = "date")
    private LocalDate dataPublicacao;
    @Builder.Default
    @Column(name = "DATA_INSERCAO", columnDefinition = "date")
    private LocalDate dataInsercao = LocalDate.now();
    @Column(name = "ARQUIVO_IMPORTADO", length = 50)
    private String arquivoImportado;
    @Builder.Default
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "TIPO_MAT_MED", length = 1)
    @Convert(converter = TipoMatMedConverter.class)
    private TIPO_MAT_MED tipoMatMed = TIPO_MAT_MED.USO_CONSUMO_HOSPILAR;

    public BrasindiceDataModel verifyTipoMatMed() {
        tipoMatMed = nomeMedicamento.toUpperCase().contains("RESTRITO HOSP") ? TIPO_MAT_MED.USO_RESTRITO_HOSPITALAR : TIPO_MAT_MED.USO_CONSUMO_HOSPILAR;
        return this;
    }

}
