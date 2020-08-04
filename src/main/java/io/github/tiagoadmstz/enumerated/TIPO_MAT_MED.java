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

}
