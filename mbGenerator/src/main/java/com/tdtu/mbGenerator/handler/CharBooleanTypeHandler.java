package com.tdtu.mbGenerator.handler;

import org.apache.ibatis.type.Alias;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Alias("CharBooleanTypeHandler")
public class CharBooleanTypeHandler extends BaseTypeHandler<Boolean> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedstatement,
                                    int intValue,
                                    Boolean parameter,
                                    JdbcType jdbcType) throws SQLException {
        preparedstatement.setString(intValue, convert(parameter));
    }
    @Override
    public Boolean getNullableResult(ResultSet resultSet,
                                     String columnName) throws SQLException {
            return convert(resultSet.getString(columnName));
    }
    @Override
    public Boolean getNullableResult(ResultSet resultSet,
                                     int columnIndex) throws SQLException {
                return convert(resultSet.getString(columnIndex));
    }
    @Override
    public Boolean getNullableResult(CallableStatement cs,
        int columnIndex) throws SQLException {
                    return convert(cs.getString(columnIndex));
    }

    private String convert(Boolean boolValue) {
        return boolValue ? "1" : "0";
    }

    private Boolean convert(String strValue) {
        return strValue.equals("1");
    }
}
