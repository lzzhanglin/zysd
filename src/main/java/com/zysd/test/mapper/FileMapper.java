package com.zysd.test.mapper;

import com.zysd.test.entity.TestData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.io.ResolverUtil;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @author lzzhanglin
 * @date 2019/1/7 20:59
 */
@Mapper
public interface FileMapper {

    void insertData(List<TestData> list);

    List<TestData> showData(Integer limit, Integer offset);

    Integer getTotal();

    TestData getDataById(@Param(value = "dataId") Long dataId);

    Integer deleteDataById(@Param(value = "dataId") Long dataId);

    Integer updateDataById(TestData testData);

}
