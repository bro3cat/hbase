package dao.hbase;

import java.util.List;

public abstract class HBaseModel implements HBaseInterface {
    @Override
    public List<String> getHBaseTableNames() {
        return null;
    }
}
