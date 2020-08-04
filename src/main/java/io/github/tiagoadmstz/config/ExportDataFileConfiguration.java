package io.github.tiagoadmstz.config;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ExportDataFileConfiguration implements Serializable {

    private static final long serialVersionUID = 1239815675582039503L;
    private String pmc = "PMC_ddMMyyyy";
    private String pfb = "PFB_ddMMyyyy";
    private String solucao = "Solucao_ddMMyyyy";
    private String material = "Material_ddMMyyyy";

}
