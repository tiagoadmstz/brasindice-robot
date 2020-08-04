package io.github.tiagoadmstz.dal.converters;

import io.github.tiagoadmstz.enumerated.TIPO_MAT_MED;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TipoMatMedConverter implements AttributeConverter<TIPO_MAT_MED, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TIPO_MAT_MED tipo_mat_med) {
        return tipo_mat_med.getValue();
    }

    @Override
    public TIPO_MAT_MED convertToEntityAttribute(Integer integer) {
        return TIPO_MAT_MED.parse(integer);
    }

}
