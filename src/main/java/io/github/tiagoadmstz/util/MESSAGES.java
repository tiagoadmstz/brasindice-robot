package io.github.tiagoadmstz.util;

import javax.swing.*;

public abstract class MESSAGES {

    public static boolean UPDATE(boolean firstIstallation) {
        if (!firstIstallation) {
            return JOptionPane.showConfirmDialog(null, "Existe uma atualização disponível para o Brasindice, deseja atualizar agora?") == 0;
        }
        return true;
    }

    public static void UPDATE_SUCCESS(boolean firstInstalletion) {
        if (!firstInstalletion) {
            JOptionPane.showMessageDialog(null, "Atualização reliazada com exito, aguarde o sistema configurar o banco de dados do Brasindice");
        }
    }

    public static boolean EXPORT_DATA_FILES() {
        return JOptionPane.showConfirmDialog(null, "Deseja exportar os arquivos do Brasindice agora?") == 0;
    }

    public static void EXPORT_DATA_FILES_SUCCESS(boolean success) {
        if (success) {
            JOptionPane.showMessageDialog(null, "Exportação reliazada com sucesso");
        } else {
            JOptionPane.showMessageDialog(null, "Erro ao tentar exportar os arquivos, tente novamente mais tarde");
        }
    }

}
