package io.github.tiagoadmstz.util;

import io.github.tiagoadmstz.dal.oracle.models.BrasindiceDataModel;
import io.github.tiagoadmstz.enumerated.PORTARIA_PISCOFINS;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static io.github.tiagoadmstz.util.WordUtils.removeAccentuation;

public abstract class ImportBrasindiceUtil {

    /**
     * Transforms files lines to BrasindiceDataModel list
     *
     * @param edition            String with actual edition number
     * @param editionDate        LocalDate with edition date to all registers
     * @param removeAccentuation Boolean to inform if remove accentuation or not
     * @param cspsUser           String with csps user name to all registers
     * @param files              Array with files with exported registers
     * @return List of the BrasindiceDataModel
     */
    public static List<BrasindiceDataModel> importBrasindice(String edition, LocalDate editionDate, Boolean removeAccentuation, String cspsUser, File... files) {
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
                            .codigoBarraEan(strings.length == 16 ? strings[13] : "")
                            .codigoBrasindiceTiss(strings.length == 16 ? strings[14].replaceAll("\"", "") : strings[13].replaceAll("\"", ""))
                            .codigoTiss(strings.length == 16 ? strings[15].replaceAll("\"", "") : strings[14].replaceAll("\"", ""))
                            .idInsercao(cspsUser)
                            .dataPublicacao(editionDate)
                            .arquivoImportado(file.getName().replace(".txt", ""))
                            .build()
                            .verifyTipoMatMed();
                }).filter(model -> model.getEdicao().equals(edition)).collect(Collectors.toList());
                return modelList;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Collections.emptyList();
    }

}
