package com.zysd.test.service;

import com.zysd.test.entity.TestData;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author lzzhanglin
 * @date 2019/1/7 20:57
 */

public interface FileService {
    boolean batchImport(String fileName, MultipartFile file, HttpServletRequest request) throws Exception;

    public List<TestData> showData(Integer limit, Integer offset);

    List<Map<String, Object>> dataAnalysis(List<TestData> dataList);
}
