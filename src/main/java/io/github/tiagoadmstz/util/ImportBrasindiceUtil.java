package io.github.tiagoadmstz.util;

import io.github.tiagoadmstz.dal.oracle.models.BrasindiceDataModel;
import io.github.tiagoadmstz.enumerated.PORTARIA_PISCOFINS;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static io.github.tiagoadmstz.util.WordUtils.removeAccentuation;

public abstract class ImportBrasindiceUtil {

    /**
     * Import Brasindice file with layout
     * <p>
     * Exemplo:
     * codigo_laboratorio = 001
     * nome_laboratorio = UNIAO QUIMICA
     * codigo_medicamento = 00001
     * nome_medicamento = A CURITYBINA
     * codigo_apresentacao = AJOX
     * nome_apresentacao = Cx. 1 fr.
     * preco_med = 0.00
     * quantidade = 1
     * tipo_preco = PMC
     * preco_unitario = 0.00
     * edicao = 922
     * ipi_med = 0.00
     * portaria = N
     * codigo_ean = 7896006211808
     * codigo_bras_tiss = 0000000001
     * codigo_tiss = 90206584
     * crlft = ?
     */
    public static void importBrasindice(LocalDate editionDate, Boolean removeAccentuation, File... files) {
        try {
            for (File file : files) {
                List<String> lines = IOUtils.readLines(new FileInputStream(file), Charset.forName("ISO-8859-1"));
                List<BrasindiceDataModel> modelList = lines.stream().map(ln -> {
                    String[] strings = ln.split("\",\"");
                    return BrasindiceDataModel.builder()
                            .codigoLaboratorio(Long.parseLong(strings[0].replaceAll("\"", "")))
                            .nomeLaboratorio(strings[1])
                            .codigoMedicamento(Long.parseLong(strings[2]))
                            .nomeMedicamento(removeAccentuation ? removeAccentuation(strings[3]) : strings[3])
                            .codigoApresentacao(strings[4])
                            .nomeApresentacao(removeAccentuation ? removeAccentuation(strings[5]) : strings[5])
                            .precoMedicamento(BigDecimal.valueOf(Double.parseDouble(strings[6])))
                            .quantidade(Long.parseLong(strings[7]))
                            .tipoPreco(strings[8])
                            .precoUnitario(BigDecimal.valueOf(Double.parseDouble(strings[9])))
                            .edicao(strings[10])
                            .ipiMedicamento(BigDecimal.valueOf(Double.parseDouble(strings[11])))
                            .portariaPisCofins(PORTARIA_PISCOFINS.parse(strings[12]))
                            .codigoBarraEan(strings[13])
                            .codigoBrasindiceTiss(strings[14])
                            .codigoTiss(strings.length == 16 ? strings[15].replaceAll("\"", "") : null)
                            .dataPublicacao(editionDate)
                            .arquivoImportado(file.getName().replace(".txt", ""))
                            .build();
                }).collect(Collectors.toList());
                modelList.forEach(System.out::println);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
