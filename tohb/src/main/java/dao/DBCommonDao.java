package dao;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.List;

public class DBCommonDao implements DBDaoIn {
    @Override
    public void insert(List<String> list) {

    }

    @Override
    public void insertMany(List<List<String>> list, int batchSize) {

    }

    @Override
    public ResultSet read(String key) {
        return null;
    }

    @Override
    public ResultSet readTable() {
        return null;
    }

    @Override
    public DatabaseMetaData getMetaData() {
        return null;
    }

    @Override
    public long test(List<List<String>> list, int batchSize) {
        return 0;
    }
}
