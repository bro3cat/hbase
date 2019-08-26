package service.db2hb;

/**
 * [who] will [do what] with [something] at [sometime] at a [certain frequency]
 */
public interface DB2HTimelyService {

    /**
     * 执行人（未实现）
     *
     * @param who
     */
    public void setWho(String who);

    /**
     * 默认的service
     */
    public void defaultService();

    /**
     * 自定义的service
     */
    public void userService();

    /**
     * 执行条件（未实现）
     */
    public void condition();

    /**
     * 开始执行的时刻（未实现）
     *
     * @param year_month_day_hour_minute_second
     */
    public void setTime(String year_month_day_hour_minute_second);

    /**
     * 执行的频率（可继承内置TimeTask，未实现）
     *
     * @param seconds
     */
    public void setFrequency(long seconds);


}
