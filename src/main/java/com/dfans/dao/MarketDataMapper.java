package com.dfans.dao;

import java.util.List;
import java.util.Map;

/**
 * 市场数据DAO
 * 
 * @author zhangyn
 * 
 */
/**
 * @author Lenovo
 *
 */
public interface MarketDataMapper {

    /**
     * 挂牌公司统计-日
     * 
     * @param param
     *            过滤条件
     * @return 统计结果
     */
    List<Map> selectComInfoByDay(Map param);

    /**
     * 挂牌公司统计-图表数据
     * 
     * @param param
     * @return 日期，数据
     */
    List<Map> selectComListByStat(Map param);

    /**
     * 交易数据统计-日/周/月
     * 
     * @param param
     *            过滤条件
     * @return 统计结果
     */
    List<Map> selectTradingInfo(Map param);

    /**
     * 交易数据统计-图表数据
     * 
     * @param param
     *            过滤条件
     * @return 统计结果
     */
    List<Map> selectTradingList(Map<String, String> param);

    /**
     * 股票发行统计-周
     * 
     * @param param
     *            过滤条件
     * @return 统计结果
     */
    List<Map> selectShareListByWeek(Map<String, String> param);

    /**
     * 股票发行统计-月
     * 
     * @param param
     *            过滤条件
     * @return 统计结果
     */
    List<Map> selectShareListByMonth(Map<String, String> param);

    /**
     * 股票发型统计图表数据
     * 
     * @param param
     *            过滤条件
     * @return 统计结果
     */
    List<Map> selectShareList(Map<String, String> param);

    /**
     * 交易数据排名
     * 
     * @param param
     *            过滤条件
     * @return 统计结果
     */
    List<Map> selectTradingRank(Map<String, String> param);

    /**
     * 在审挂牌公司
     * 
     * @param param
     * @return
     */
    List<Map> selectExamList(Map<String, Object> param);

    /**
     * 获取最大日期
     * 
     * @param param
     * @return
     */
    List<Map> selectMaxDateCom(Map<String, String> param);

    /**
     * 获取最大日期
     * 
     * @param param
     * @return
     */
    List<Map> selectMaxDateTra(Map<String, String> param);

    /**
     * 获取拥有数据的最大日期 -股票发行统计
     * 
     * @param param
     * @return
     */
    List<Map> selectShareMaxDate(Map<String, String> param);

    /**
     * 获取拥有数据的最大日期 -交易排名统计
     * 
     * @return
     */
    List<Map> selectTradingMaxDate(Map<String, String> param);

    /**
     * 获取挂牌公司最小日期
     * 
     * @return
     */
    List<Map> selectMinDateGpgs();

    /**
     * 获取交易数据最小日期
     * 
     * @return
     */
    List<Map> selectMinDateJysj(Map<String, Object> param);

    /**
     * 获取交易排名最小日期
     * 
     * @param param
     * @return
     */
    List<Map> selectMinDateJypm(Map<String, Object> param);

    /**
     * 获取股票发行最小日期
     * 
     * @param param
     * @return
     */
    List<Map> selectMinDateGpfx();

}
