package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface DBCommonIn {

    public void insert(List<String> list);

    public void insertMany(List<List<String>> list);

    public ResultSet read(String key);

    public ResultSet readTable();
}
