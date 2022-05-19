package icu.fanjie.base.plugin;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSONObject;
import icu.fanjie.SpiderTracker;
import icu.fanjie.base.PrintStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class MysqlStorage extends PrintStorage {
    protected String mysqlJdbcUrl = "jdbc:mysql://127.0.0.1:3306/spider?useSSL=true&useUnicode=true&characterEncoding=utf8";
    protected String userName = "root";
    protected String password = "root";
    protected String tableName = "spider";
    protected DruidDataSource source;
    protected Map<String, String> columns = new LinkedHashMap<>();

    public MysqlStorage() throws SQLException {
        createDruidDataSource();
        createTable();
    }

    public MysqlStorage(String mysqlJdbcUrl, String userName, String password, String tableName) throws SQLException {
        this.mysqlJdbcUrl = mysqlJdbcUrl;
        this.userName = userName;
        this.password = password;
        this.tableName = tableName;
        createDruidDataSource();
        createTable();
    }

    protected void createDruidDataSource() {
        source = new DruidDataSource();
        source.setUrl(mysqlJdbcUrl);
        source.setUsername(userName);
        source.setPassword(password);
        source.setInitialSize(1);
        source.setMaxActive(5);
        source.setMinIdle(1);
    }

    protected void createTable() throws SQLException {
        tableColumns();
        DruidPooledConnection connection = source.getConnection();
        Statement statement = connection.createStatement();
        StringBuilder builder = new StringBuilder();
        builder.append("create table if not exists ").append(tableName).append("(");
        builder.append("\n");
        builder.append("id int unsigned auto_increment,\n");
        for (String key : columns.keySet()) {
            builder.append(String.format("%s text comment '%s',", key, columns.get(key)));
            builder.append("\n");
        }
        builder.append("primary key (id)");
        builder.append("\n");
        builder.append(")engine=InnoDB DEFAULT CHARSET=utf8;");
        boolean execute = statement.execute(builder.toString());
        ResultSet resultSet = statement.executeQuery(String.format("desc %s", tableName));
        List<String> fields = new ArrayList<>();
        while (resultSet.next()) {
            String field = resultSet.getString("Field");
            fields.add(field);
        }
        for (String key : columns.keySet()) {
            if (!fields.contains(key)) {
                String addColumnSql = String.format("alter table %s add column %s text comment '%s'", tableName, key, columns.get(key));
                statement.execute(addColumnSql);
            }
        }
        statement.close();
        connection.close();
        if (execute) {
            throw new SQLException("创建数据库表失败");
        }
    }

    @Override
    public void storage(SpiderTracker tracker) {
        try {
            DruidPooledConnection connection = source.getConnection();
            Statement statement = connection.createStatement();
            Object parser = tracker.getExtraParams().get("parser");
            JSONObject jo = (JSONObject) parser;
            JSONObject parser_content = jo.getJSONObject("parser_content");
            if (parser_content == null || parser_content.size() == 0) {
                return;
            }
            StringBuilder builder = new StringBuilder();
            builder.append("insert into ").append(tableName).append("(");
            builder.append("\n");
            List<String> keys = new ArrayList<>();
            for (String key : parser_content.keySet()) {
                builder.append(String.format("%s", key)).append(",");
                keys.add(key);
            }
            builder.deleteCharAt(builder.length() - 1);
            builder.append(")").append("\n");
            builder.append("values(");
            for (String key : keys) {
                builder.append(String.format("'%s'", parser_content.getString(key))).append(",");
            }
            builder.deleteCharAt(builder.length() - 1);
            builder.append(")").append(";");
            boolean execute = statement.execute(builder.toString());
            if (execute) {
                throw new SQLException("插入数据失败");
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void tableColumns() {
        columns.put("title", "标题");
        columns.put("content", "内容");
    }
}
