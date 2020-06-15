package io.github.tiagoadmstz.util;

import javax.swing.*;

public abstract class MESSAGES {

    public static boolean UPDATE(boolean firstIstallation) {
        if (!firstIstallation) {
            return JOptionPane.showConfirmDialog(null, "Existe uma atualização disponível para o Brasindice, deseja atualizar agora?") == 1;
        }
        return true;
    }

    public static void UPDATE_SUCCESS(boolean firstInstalletion) {
        if (!firstInstalletion) {
            JOptionPane.showMessageDialog(null, "Atualização reliazada com exito, aguarde o sistema configurar o banco de dados do Brasindice");
        }
    }

}
