package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityMetaData = entitySQLMetaData.getEntityMetaData();
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), rs -> {
            try {
                if (rs.next()) {
                    return entityMetaData.getConstructor().newInstance(getNamesFields(rs));
                }
                throw new RuntimeException();
            } catch (Exception e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        List<T> list = new ArrayList<>();
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
            try {
                while (rs.next()) {
                    list.add(entityMetaData.getConstructor().newInstance(getNamesFields(rs)));
                }
                return list;
            } catch (Exception ex) {
                throw new DataTemplateException(ex);
            }
        }).orElseThrow(RuntimeException::new);
    }

    @Override
    public long insert(Connection connection, T client) {
        try {
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), paramsToExecute(client));
        } catch (Exception ex) {
            throw new DataTemplateException(ex);
        }
    }

    @Override
    public void update(Connection connection, T client) {
        try {
            var list = paramsToExecute(client);
            var field = entityMetaData.getIdField();
            list.add(field.get(client));
            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), list);
        } catch (Exception ex) {
            throw new DataTemplateException(ex);
        }
    }

    private List<Object> paramsToExecute(T client) throws Exception {
        List<Field> fields = entityMetaData.getFieldsWithoutId();
        Object[] params = new Object[fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            String str = getGetterMethodName(fields.get(i));
            if (!str.isEmpty()) {
                params[i] = (client.getClass().getDeclaredMethod(str).invoke(client));
            }
        }
        return Arrays.asList(params);
    }

    private Object[] getNamesFields(ResultSet rs) throws SQLException {
        List<Field> fields = entityMetaData.getAllFields();
        Object[] obj = new Object[fields.size()];

        obj[0] = (rs.getLong(entityMetaData.getIdField().getName()));
        for (int i = 1; i < fields.size(); i++) {
            obj[i] = (rs.getString(fields.get(i).getName()));
        }
        return obj;
    }

    private String getGetterMethodName(Field field) {
        return "get" +
                field.getName().substring(0, 1).toUpperCase() +
                field.getName().substring(1);
    }
}
