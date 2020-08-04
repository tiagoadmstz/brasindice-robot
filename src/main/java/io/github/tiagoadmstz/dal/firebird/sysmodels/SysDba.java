package io.github.tiagoadmstz.dal.firebird.sysmodels;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "rdb$relations")
public class SysDba implements Serializable {

    @Id
    @Column(name = "RDB$RELATION_ID")
    private Long relationID;
    @Column(name = "RDB$RELATION_NAME")
    private String relationName;
    @Column(name = "RDB$FLAGS")
    private String flags;
    @Column(name = "RDB$VIEW_BLR")
    private String viewBlr;
    @Column(name = "RDB$VIEW_SOURCE")
    private String viewSource;
    @Column(name = "RDB$DESCRIPTION")
    private String description;
    @Column(name = "RDB$DBKEY_LENGTH")
    private Integer debkeyLength;
    @Column(name = "RDB$FORMAT")
    private String format;
    @Column(name = "RDB$FIELD_ID")
    private Integer fieldId;
    @Column(name = "RDB$SECURITY_CLASS")
    private String securityClass;
    @Column(name = "RDB$OWNER_NAME")
    private String ownerName;
    @Column(name = "RDB$DEFAULT_CLASS")
    private String defaultClass;

}
