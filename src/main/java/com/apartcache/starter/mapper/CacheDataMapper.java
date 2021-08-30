package com.apartcache.starter.mapper;

import com.apartcache.starter.bean.CacheData;
import com.apartcache.starter.bean.SearchCondition;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * Created by desk
 *
 * @date 2021/8/23
 */
public interface CacheDataMapper {


    @Insert("<script>" +
            "INSERT INTO cache_data(\n" +
            "        cacheName,\n" +
            "        clazz,\n" +
            "        method,\n" +
            "        params,\n" +
            "        status,\n" +
            "        second,\n" +
            "        creator,\n" +
            "        createTime,\n" +
            "        updater,\n" +
            "        updateTime\n" +
            "        )\n" +
            "        VALUES(\n" +
            "        #{cacheName},\n" +
            "        #{clazz},\n" +
            "        #{method},\n" +
            "        #{params},\n" +
            "        #{status},\n" +
            "        #{second},\n" +
            "        #{creator},\n" +
            "        NOW(),\n" +
            "        #{updater},\n" +
            "        NOW()\n" +
            "        );" +
            "</script>")
    void add(CacheData data);

    @Update("<script>" +
            "UPDATE cache_data\n" +
            "        SET\n" +
            "        <trim prefixOverrides=\",\">\n" +
            "            <if test=\"cacheName != null and cacheName != ''\">\n" +
            "                cacheName = #{cacheName},\n" +
            "            </if>\n" +
            "            <if test=\"clazz != null and clazz != ''\">\n" +
            "                clazz = #{clazz},\n" +
            "            </if>\n" +
            "            <if test=\"method != null and method != ''\">\n" +
            "                method = #{method},\n" +
            "            </if>\n" +
            "            <if test=\"params != null and params != ''\">\n" +
            "                params = #{params},\n" +
            "            </if>\n" +
            "            <if test=\"status.name() != null and status.name() != ''\">\n" +
            "                status = #{status},\n" +
            "            </if>\n" +
            "            <if test=\"second != null and second != ''\">\n" +
            "                second = #{second},\n" +
            "            </if>\n" +
            "            <if test=\"(clazz != null and clazz != '') or\n" +
            "                    (method != null and method != '') or\n" +
            "                    (params != null and params != '') or\n" +
            "                    (status.name() != null and status.name() != '')\">\n" +
            "                updater = #{updater},\n" +
            "                updateTime = NOW()\n" +
            "            </if>\n" +
            "        </trim>\n" +
            "        WHERE id = #{id};"+
            "</script>")
    void update(CacheData data);

    @Select("SELECT * FROM cache_data WHERE id = #{id};")
    CacheData selectById(long id);

    @Select("<script>" +
            "SELECT  d.*\n" +
            "        FROM cache_data  d\n" +
            "        <where>\n" +
            "            <trim prefixOverrides=\"and\">\n" +
            "                <if test=\"condition.clazz != null and condition.clazz != ''\">\n" +
            "                    d.clazz like CONCAT('%',#{condition.clazz},'%')\n" +
            "                </if>\n" +
            "                <if test=\"condition.method != null and condition.method != ''\">\n" +
            "                    AND d.method like CONCAT('%',#{condition.method},'%')\n" +
            "                </if>\n" +
            "                <if test=\"condition.status.name() != null and condition.status.name() != ''\">\n" +
            "                    AND d.status = #{condition.status.name()}\n" +
            "                </if>\n" +
            "            </trim>\n" +
            "        </where>\n" +
            "        ORDER BY d.updateTime desc, d.createTime desc\n" +
            "        LIMIT #{startIndex},#{num};" +
            "</script>")
    List<CacheData> selectPage(@Param("condition") SearchCondition condition, @Param("startIndex") Integer startIndex, @Param("num") Integer num);

    @Select("SELECT count(*) FROM cache_data d where d.status != 'DELETED';")
    Integer count();

    @Select("SELECT * FROM cache_data;")
    List<CacheData> selectAll();
}
