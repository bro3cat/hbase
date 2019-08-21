package tohb.service;

/**
 * [who] will [do what] with [something] at [sometime] at a [certain frequency]
 */
public interface BaseService {

    public void setWho(String who);

    /**
     * 通常用来service启动
     */
    public void run();

    public void condition();

    public void setTime(String year_month_day_hour_minute_second);

    public void setFrequency(long seconds);

//    public void

}
