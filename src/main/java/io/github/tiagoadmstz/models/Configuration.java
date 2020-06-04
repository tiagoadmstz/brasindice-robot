package io.github.tiagoadmstz.models;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.io.File;
import java.io.Serializable;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Configuration implements Serializable {

    private static final long serialVersionUID = 3663419321029583563L;
    @SerializedName("setup-path")
    private File setupPath = new File("C:/BRASINDICE");
    @SerializedName("is-instaled")
    private Boolean isInstaled = false;
    private String url = "www.brasindice.com.br";
    private String login = "";
    private String password = "";

    public Configuration(String login, String password) {
        this.login = login;
        this.password = password;
    }

}
