package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {
    private final EntityClassMetaData<T> entityMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityMetaData) {
        this.entityMetaData = entityMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return "SELECT * FROM " + entityMetaData.getName();
    }

    @Override
    public String getSelectByIdSql() {
        StringBuilder sbFieldName = new StringBuilder();
        for (Field field : entityMetaData.getAllFields()) {
            if (sbFieldName.isEmpty()) {
                sbFieldName.append(field.getName());
            } else {
                sbFieldName.append(", ");
                sbFieldName.append(field.getName());
            }
        }
        return "SELECT " + sbFieldName + " FROM " + entityMetaData.getName() +
                " WHERE " + entityMetaData.getIdField().getName() + " =? ;";
    }

    @Override
    public String getInsertSql() {
        StringBuilder sbFieldsName = new StringBuilder();
        StringBuilder sbParamCount = new StringBuilder();
        List<Field> fields = entityMetaData.getFieldsWithoutId();
        int size = fields.size();
        for (int i = 0; i < size; i++) {
            sbFieldsName.append(fields.get(i).getName());
            sbParamCount.append("?");
            if (i != size - 1) {
                sbFieldsName.append(", ");
                sbParamCount.append(", ");
            }
        }
        return "INSERT INTO " + entityMetaData.getName() + " (" + sbFieldsName + ") " +
                "VALUES (" + sbParamCount + ");";
    }

    @Override
    public String getUpdateSql() {
        StringBuilder sbFieldsName = new StringBuilder();
        List<Field> fields = entityMetaData.getFieldsWithoutId();
        int size = fields.size();
        for (int i = 0; i < size; i++) {
            sbFieldsName.append(fields.get(i).getName());
            sbFieldsName.append(" = ?");
            if (i != size - 1) {
                sbFieldsName.append(", ");
            }
        }
        String s = entityMetaData.getIdField().getName() + " =?";
        return "UPDATE " + entityMetaData.getName() + " SET " + " (" + sbFieldsName + ") " +
                "WHERE (" + s + ");";
    }

    public EntityClassMetaData<T> getEntityMetaData() {
        return entityMetaData;
    }
}
