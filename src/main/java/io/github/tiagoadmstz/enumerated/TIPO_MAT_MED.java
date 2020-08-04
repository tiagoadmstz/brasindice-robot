package io.github.tiagoadmstz.enumerated;

public enum TIPO_MAT_MED {

    USO_CONSUMO_HOSPILAR(1),
    USO_RESTRITO_HOSPITALAR(2),
    QUIMIOTERAPICO(3),
    ALTO_CUSTO(4);

    private Integer value;

    TIPO_MAT_MED(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public static TIPO_MAT_MED parse(int value) {
        switch (value) {
            default:
            case 1:
                return USO_CONSUMO_HOSPILAR;
            case 2:
                return USO_RESTRITO_HOSPITALAR;
            case 3:
                return QUIMIOTERAPICO;
            case 4:
                return ALTO_CUSTO;
        }
    }

}
