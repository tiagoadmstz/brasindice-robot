package io.github.tiagoadmstz.dal.converters;

import org.apache.commons.codec.binary.Hex;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = false)
public class Base64Converter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String string) {
        return null;
    }

    @Override
    public String convertToEntityAttribute(String base64) {
        try {
            if (base64 != null) {
                return String.valueOf(Hex.decodeHex(base64.toCharArray()));
            }
        } catch (Exception ex) {
        }
        return "";
    }

}
