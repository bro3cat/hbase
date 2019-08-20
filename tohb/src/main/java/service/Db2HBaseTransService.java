package service;

import dao.DBCommonDao;
import dao.DBCommonDaoIn;
import dao.table.HBaseTableDaoIn;

public abstract class Db2HBaseTransService implements BaseService {

    private String who = "";
    private String year_month_day_hour_minute_second = "";
    private long seconds = 0;
    protected DBCommonDaoIn dbCommonDao;
    protected HBaseTableDaoIn hBaseDao;

    protected void init(HBaseTableDaoIn hBaseDao, DBCommonDaoIn dbCommonDao) {
        this.hBaseDao = hBaseDao;
        this.dbCommonDao = dbCommonDao;
    }

    public Db2HBaseTransService(HBaseTableDaoIn hBaseDao, DBCommonDaoIn dbCommonDao){
        init(hBaseDao, dbCommonDao);
//        dbCommonDao.
    }

    @Override
    public void setWho(String who) {
        this.who = who;
    }



    @Override
    public void run(){
        fromDb2HBase();fromDbTable2HBase();
    }

    protected abstract void fromDbTable2HBase();{

    }
    protected abstract void fromDb2HBase();{}


    @Override
    public void condition() {

    }

    @Override
    public void setTime(String year_month_day_hour_minute_second) {
        this.year_month_day_hour_minute_second = year_month_day_hour_minute_second;
    }

    @Override
    public void setFrequency(long seconds) {
        this.seconds = seconds;
    }

}
