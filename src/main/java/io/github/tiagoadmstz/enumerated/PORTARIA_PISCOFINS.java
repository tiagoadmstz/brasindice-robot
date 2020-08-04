package io.github.tiagoadmstz.enumerated;

public enum PORTARIA_PISCOFINS {

    SIM("S"), NAO("N");

    private String value;

    PORTARIA_PISCOFINS(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static PORTARIA_PISCOFINS parse(String string) {
        return "S".equals(string.trim()) ? PORTARIA_PISCOFINS.SIM : PORTARIA_PISCOFINS.NAO;
    }

}
