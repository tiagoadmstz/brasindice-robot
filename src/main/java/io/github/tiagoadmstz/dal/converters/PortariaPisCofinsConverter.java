package io.github.tiagoadmstz.dal.converters;

import io.github.tiagoadmstz.enumerated.PORTARIA_PISCOFINS;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PortariaPisCofinsConverter implements AttributeConverter<PORTARIA_PISCOFINS, String> {

    @Override
    public String convertToDatabaseColumn(PORTARIA_PISCOFINS portaria_piscofins) {
        return portaria_piscofins.getValue();
    }

    @Override
    public PORTARIA_PISCOFINS convertToEntityAttribute(String string) {
        return PORTARIA_PISCOFINS.parse(string);
    }

}
