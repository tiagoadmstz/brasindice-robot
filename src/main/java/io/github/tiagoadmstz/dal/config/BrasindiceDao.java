package io.github.tiagoadmstz.dal.config;

import io.github.tiagoadmstz.config.BrasindiceRobotConfiguration;
import io.github.tiagoadmstz.interfaces.ExceptionFunction;
import io.github.tiagoadmstz.util.ConfigurationFileUtil;
import org.firebirdsql.gds.impl.GDSType;
import org.firebirdsql.management.FBManager;

import java.io.File;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class BrasindiceDao {

    private final BrasindiceRobotConfiguration brasindiceRobotConfiguration;

    public BrasindiceDao() {
        this.brasindiceRobotConfiguration = new ConfigurationFileUtil().load();
    }

    public Connection getConnection() throws Exception {
        System.load(new File(brasindiceRobotConfiguration.getSetupPath() + "\\fbembed.dll").getPath());
        System.load(new File(brasindiceRobotConfiguration.getSetupPath() + "\\gds32.dll").getPath());

        Driver driver = (Driver) Class.forName("org.firebirdsql.jdbc.FBDriver").newInstance();

        Properties properties1 = new Properties();
        properties1.put("user", "sysdba");
        properties1.put("password", "masterkey");
        properties1.put("RoleName", "RoleName");
        properties1.put("encoding", "UNICODE_FSS");

        return driver.connect("jdbc:firebirdsql:embedded:" + brasindiceRobotConfiguration.getSetupPath() + "\\BRASDB.GDB", properties1);
    }

    public void createDataBase(File file) {
        try {
            String path = file.toString().replace("\\", "/");
            System.loadLibrary(path);
            if (!file.exists()) {
                FBManager manager = new FBManager(GDSType.getType("EMBEDDED"));
                manager.start();
                manager.createDatabase(path, "", "");
                manager.stop();
            }
        } catch (Exception ex) {
        }
    }

    public List<String> getTablesNames(Connection connection) {
        return executeQueryAndReturnSimpleList(connection,
                "select rdb$relation_name " +
                        "from rdb$relations "
                        + "where rdb$view_blr is null "
                        + "and (rdb$system_flag is null or rdb$system_flag = 0)",
                rst -> String.format("Table: %s", rst.getString(1), ""));
    }

    public List<String> getViewsNames(Connection connection) {
        return executeQueryAndReturnSimpleList(connection,
                "select rdb$relation_name"
                        + "from rdb$relations "
                        + "where rdb$view_blr is not null "
                        + "and (rdb$system_flag is null or rdb$system_flag = 0)",
                rst -> String.format("View: %s", rst.getString(1)));
    }

    public List<String> getRelations(Connection connection) {
        return executeQueryAndReturnSimpleList(connection,
                "select * from rdb$relations",
                rst -> String.format("Relations: %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s",
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5),
                        rst.getString(6),
                        rst.getString(7),
                        rst.getString(8),
                        rst.getString(9),
                        rst.getString(10),
                        rst.getString(11),
                        rst.getString(12),
                        rst.getString(13),
                        rst.getString(14),
                        rst.getString(15),
                        rst.getString(16),
                        ""));
    }

    public List<String> getTriggers(Connection connection) {
        return executeQueryAndReturnSimpleList(connection,
                "select * from rdb$triggers",
                rst -> String.format("Trigger: %s, %s, %s, %s, %s, %s, %s, %s, %s, %s",
                        rst.getString(1),
                        rst.getString(2),
                        rst.getString(3),
                        rst.getString(4),
                        rst.getString(5),
                        rst.getString(6),
                        rst.getString(7),
                        rst.getString(8),
                        rst.getString(9),
                        ""));
    }

    public List<String> getFunctions(Connection connection) {
        return executeQueryAndReturnSimpleList(connection,
                "select * from rdb$functions",
                rst -> String.format("Functions: %s",
                        rst.getString(1),
                        ""));
    }

    public List<String> getFilters(Connection connection) {
        return executeQueryAndReturnSimpleList(connection,
                "select * from rdb$filters",
                rst -> String.format("Trigger: %s",
                        rst.getString(1),
                        ""));
    }

    public List<String> getRoles(Connection connection) {
        return executeQueryAndReturnSimpleList(connection,
                "select * from rdb$roles",
                rst -> String.format("Roles: %s",
                        rst.getString(1),
                        ""));
    }

    public List<String> getTransactions(Connection connection) {
        return executeQueryAndReturnSimpleList(connection,
                "select * from rdb$transactions",
                rst -> String.format("Transactions: %s",
                        rst.getString(1),
                        ""));
    }

    public List<String> getProcedures(Connection connection) {
        return executeQueryAndReturnSimpleList(connection,
                "select * from rdb$procedures",
                rst -> String.format("Procedures: %s", rst.getString(1)));
    }

    public List<String> getTablesAndViewsFieldsNames(Connection connection) {
        return executeQueryAndReturnSimpleList(connection,
                "select f.rdb$relation_name, f.rdb$field_name "
                        + "from rdb$relation_fields f "
                        + "join rdb$relations r on f.rdb$relation_name = r.rdb$relation_name "
                        + "and r.rdb$view_blr is null "
                        + "and (r.rdb$system_flag is null or r.rdb$system_flag = 0) "
                        + "order by 1, f.rdb$field_position ",
                rst -> String.format("Relation: %s - Field: %s", rst.getString(1), rst.getString(2)));
    }

    private List<String> executeQueryAndReturnSimpleList(Connection connection, String query, ExceptionFunction<ResultSet, String> function) {
        List<String> list = new ArrayList();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                list.add(function.apply(resultSet));
            }
            statement.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return list;
    }

}
