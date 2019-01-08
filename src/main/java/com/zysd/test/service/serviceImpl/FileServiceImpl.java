package com.zysd.test.service.serviceImpl;

import com.csvreader.CsvReader;
import com.zysd.test.entity.TestData;
import com.zysd.test.mapper.FileMapper;
import com.zysd.test.service.FileService;
import org.apache.catalina.User;
import org.apache.ibatis.io.ResolverUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author lzzhanglin
 * @date 2019/1/7 20:57
 */
@Service("fileService")
public class FileServiceImpl implements FileService {


    private Logger logger = LoggerFactory.getLogger(this.getClass());
        @Autowired
        private FileMapper fileMapper;

        @Override
        public boolean batchImport(String fileName, MultipartFile file, HttpServletRequest request) throws Exception {
            if (!fileName.endsWith(".csv")) {
                return false;
            }

            List<String[]> csvList = new ArrayList<>();
            InputStream is = file.getInputStream();
            String path = "C:\\Users\\zl950\\Desktop";
            //上传文件的真实路径
                String filePath = path+ File.separator+fileName;

            CsvReader reader = new CsvReader(is, ',', Charset.forName("UTF-8"));
            // 跳过表头   如果需要表头的话，不要写这句。
            reader.readHeaders();
            // 逐行读入除表头的数据
            while (reader.readRecord()) {
                csvList.add(reader.getValues());
            }
            List<TestData> dataList = new ArrayList<>();
            //设置i=1 避免第一次操作就取余数为零
            for (int i = 1; i < csvList.size()+1; i++) {
                TestData testData = new TestData();

                String[] arr = csvList.get(i-1);
                testData.setA1(new BigDecimal(arr[0]));
                testData.setA2(new BigDecimal(arr[1]));
                testData.setA3(new BigDecimal(arr[2]));
                testData.setA4(new BigDecimal(arr[3]));
                testData.setA5(new BigDecimal(arr[4]));
                testData.setA6(new BigDecimal(arr[5]));
                testData.setA7(new BigDecimal(arr[6]));
                testData.setA8(new BigDecimal(arr[7]));
                testData.setA9(new BigDecimal(arr[8]));
                testData.setA10(new BigDecimal(arr[9]));
                testData.setA11(new BigDecimal(arr[10]));
                testData.setA12(new BigDecimal(arr[11]));
                testData.setQuality(arr[12]);
                dataList.add(testData);
                //每次插入1000条数据，若一次插入全部数据，
                // MySQL会报错com.mysql.jdbc.PacketTooBigException: Packet for query is too large
                if (i % 1000 == 0) {
                    fileMapper.insertData(dataList);
                    dataList.clear();
                }
            }
            //将最后零散的数据进行插入
            fileMapper.insertData(dataList);

          return true;
        }

    public List<TestData> showData(Integer limit, Integer offset) {
            return fileMapper.showData(limit,offset);
    }
    }



