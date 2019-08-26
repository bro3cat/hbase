package service.db2hb;

import h_utils.dao.TableCommonManager;
import tohb.dao.trans.DBTransUtils;

public interface DB2HTransService {
    public void trans(DBTransUtils dbTransUtils, String fromDb, String fromDbTable, TableCommonManager toWhichHBaseTable);

}
