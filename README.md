# Brasindice Robot
This project make Download, install and update database file GDB of the digital magazine Brasindice

# Requeriments

Install Winrar software

# External configuration file
./config/brasindiceRobot/configuration.json
```
{
    "setup-path": {
        "path": "C:\\Brasindice"
    },
    "winrar-path": "C:\\Program Files\\Winrar",
    "is-instaled": false,
    "base-url": "http://www.brasindiceRobot.com.br",
    "url-login": "http://www.brasindiceRobot.com.br/Admin/LoginUsuario",
    "url-download": "http://www.brasindiceRobot.com.br/Arquivos/bras8-5.rar",
    "url-edition-archives-list": "http://www.brasindiceRobot.com.br/Admin/Arquivos",
    "url-uploads": "http://www.brasindiceRobot.com.br/Uploads",
    "last-edition": "950c.GDB",
    "last-edition-date": "05/06/2020",
    "remove-accentuation": true,
    "delete-exported-files": false,
    "save-into-network-database": false,
    "export-file-names": {
        "pmc": "PMC_ddMMyyyy",
        "pfb": "PFB_ddMMyyyy",
        "solucao": "Solucao_ddMMyyyy",
        "material": "Material_ddMMyyyy",
    }, 
    "csps-user": "USER_ID"
    "login": "12345",
    "password": "XXXXXXXX"
}
```

# YAML confiration file - Database configuration
./src/main/resources/application.yml
```
spring:
  datasource:
    brasindice:
      url: jdbc:firebirdsql:embedded:%s\\BRASDB.GDB?encoding=UNICODE_FSS
      username: root
      password: root
    csps:
      url: jdbc:oracle:thin:@localhost:1521:base
      username: root
      password: root
```

## Installation and updating 
![knowhow](https://github.com/tiagoadmstz/brasindice-robot/blob/master/kh.gif)

## Exporting files and inserting into outer database
![knowhow2](https://github.com/tiagoadmstz/brasindice-robot/blob/master/kh2.gif)