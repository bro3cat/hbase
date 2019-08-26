package service.db2hb;

public abstract class Db2HTimelyServiceImpl implements DB2HTimelyService {

    private String who = "";
    private String year_month_day_hour_minute_second = "";
    private long seconds = 0;


    @Override
    public void setWho(String who) {
        this.who = who;
    }

    /**
     * 默认service
     */
    @Override
    public void defaultService() {

    }

    /**
     * 用户service
     */
    @Override
    public abstract void userService();


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
