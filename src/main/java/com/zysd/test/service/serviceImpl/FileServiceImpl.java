package com.zysd.test.service.serviceImpl;

import com.csvreader.CsvReader;
import com.zysd.test.entity.TestData;
import com.zysd.test.mapper.FileMapper;
import com.zysd.test.service.FileService;
import org.apache.catalina.User;
import org.apache.ibatis.io.ResolverUtil;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Workbook;
import org.omg.CORBA.OBJ_ADAPTER;
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
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.util.*;

import static java.math.BigDecimal.ROUND_HALF_DOWN;

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

        @Override
    public List<TestData> showData(Integer limit, Integer offset) {
            return fileMapper.showData(limit,offset);
    }

    @Override
    public List<Map<String, Object>> dataAnalysis(List<TestData> dataList) {

        List<Map<String, Object>> resultList = new ArrayList<>();

        BigDecimal[] arr1 = new BigDecimal[10];
        BigDecimal[] arr2 = new BigDecimal[10];
        BigDecimal[] arr3 = new BigDecimal[10];
        BigDecimal[] arr4 = new BigDecimal[10];
        BigDecimal[] arr5 = new BigDecimal[10];
        BigDecimal[] arr6 = new BigDecimal[10];
        BigDecimal[] arr7 = new BigDecimal[10];
        BigDecimal[] arr8 = new BigDecimal[10];
        BigDecimal[] arr9 = new BigDecimal[10];
        BigDecimal[] arr10 = new BigDecimal[10];
        BigDecimal[] arr11 = new BigDecimal[10];
        BigDecimal[] arr12 = new BigDecimal[10];
        for (int i =0; i < dataList.size(); i++) {
            arr1[i] = dataList.get(i).getA1();
            arr2[i] = dataList.get(i).getA2();
            arr3[i] = dataList.get(i).getA3();
            arr4[i] = dataList.get(i).getA4();
            arr5[i] = dataList.get(i).getA5();
            arr6[i] = dataList.get(i).getA6();
            arr7[i] = dataList.get(i).getA7();
            arr8[i] = dataList.get(i).getA8();
            arr9[i] = dataList.get(i).getA9();
            arr10[i] = dataList.get(i).getA10();
            arr11[i] = dataList.get(i).getA11();
            arr12[i] = dataList.get(i).getA12();
        }

        BigDecimal avg1 = getAvg(arr1);
        BigDecimal avg2 = getAvg(arr2);
        BigDecimal avg3 = getAvg(arr3);
        BigDecimal avg4 = getAvg(arr4);
        BigDecimal avg5 = getAvg(arr5);
        BigDecimal avg6 = getAvg(arr6);
        BigDecimal avg7 = getAvg(arr7);
        BigDecimal avg8 = getAvg(arr8);
        BigDecimal avg9 = getAvg(arr9);
        BigDecimal avg10 = getAvg(arr10);
        BigDecimal avg11 = getAvg(arr11);
        BigDecimal avg12 = getAvg(arr12);

        BigDecimal std1 = getStd(arr1, avg1);
        BigDecimal std2 = getStd(arr2, avg2);
        BigDecimal std3 = getStd(arr3, avg3);
        BigDecimal std4 = getStd(arr4, avg4);
        BigDecimal std5 = getStd(arr5, avg5);
        BigDecimal std6 = getStd(arr6, avg6);
        BigDecimal std7 = getStd(arr7, avg7);
        BigDecimal std8 = getStd(arr8, avg8);
        BigDecimal std9 = getStd(arr9, avg9);
        BigDecimal std10 = getStd(arr10, avg10);
        BigDecimal std11 = getStd(arr11, avg11);
        BigDecimal std12 = getStd(arr12, avg12);

        Integer ns1 = getNs(arr1, avg1, std1);
        Integer ns2 = getNs(arr2, avg2, std2);
        Integer ns3 = getNs(arr3, avg3, std3);
        Integer ns4 = getNs(arr4, avg4, std4);
        Integer ns5 = getNs(arr5, avg5, std5);
        Integer ns6 = getNs(arr6, avg6, std6);
        Integer ns7 = getNs(arr7, avg7, std7);
        Integer ns8 = getNs(arr8, avg8, std8);
        Integer ns9 = getNs(arr9, avg9, std9);
        Integer ns10 = getNs(arr10, avg10, std10);
        Integer ns11 = getNs(arr11, avg11, std11);
        Integer ns12 = getNs(arr12, avg12, std12);

        Map<String, Object> a1Map = new HashMap<>(16);
        Map<String, Object> a2Map = new HashMap<>(16);
        Map<String, Object> a3Map = new HashMap<>(16);
        Map<String, Object> a4Map = new HashMap<>(16);
        Map<String, Object> a5Map = new HashMap<>(16);
        Map<String, Object> a6Map = new HashMap<>(16);
        Map<String, Object> a7Map = new HashMap<>(16);
        Map<String, Object> a8Map = new HashMap<>(16);
        Map<String, Object> a9Map = new HashMap<>(16);
        Map<String, Object> a10Map = new HashMap<>(16);
        Map<String, Object> a11Map = new HashMap<>(16);
        Map<String, Object> a12Map = new HashMap<>(16);
        Map<String, Object> qualityMap = new HashMap<>(16);
        a1Map.put("avg", avg1);
        a1Map.put("std", std1);
        a1Map.put("ns", ns1);

        a2Map.put("avg", avg2);
        a2Map.put("std", std2);
        a2Map.put("ns", ns2);

        a3Map.put("avg", avg3);
        a3Map.put("std", std3);
        a3Map.put("ns", ns3);

        a4Map.put("avg", avg4);
        a4Map.put("std", std4);
        a4Map.put("ns", ns4);
        
        a5Map.put("avg", avg5);
        a5Map.put("std", std5);
        a5Map.put("ns", ns5);
        
        a6Map.put("avg", avg6);
        a6Map.put("std", std6);
        a6Map.put("ns", ns6);

        a7Map.put("avg", avg7);
        a7Map.put("std", std7);
        a7Map.put("ns", ns7);

        a8Map.put("avg", avg8);
        a8Map.put("std", std8);
        a8Map.put("ns", ns8);

        a9Map.put("avg", avg9);
        a9Map.put("std", std9);
        a9Map.put("ns", ns9);

        a10Map.put("avg", avg10);
        a10Map.put("std", std10);
        a10Map.put("ns", ns10);

        a11Map.put("avg", avg11);
        a11Map.put("std", std11);
        a11Map.put("ns", ns11);

        a12Map.put("avg", avg12);
        a12Map.put("std", std12);
        a12Map.put("ns", ns12);
        String factor = getFactor(dataList);

        qualityMap.put("factor", factor);


        resultList.add(a1Map);
        resultList.add(a2Map);
        resultList.add(a3Map);
        resultList.add(a4Map);
        resultList.add(a5Map);
        resultList.add(a6Map);
        resultList.add(a7Map);
        resultList.add(a8Map);
        resultList.add(a9Map);
        resultList.add(a10Map);
        resultList.add(a11Map);
        resultList.add(a12Map);
        resultList.add(qualityMap);
        return resultList;
    }

    @Override
    public List<TestData> getDataForEdit(Integer limit, Integer offset) {

        return fileMapper.showData(limit, offset);
    }

    @Override
    public TestData getDataById(Long dataId) {
        return fileMapper.getDataById(dataId);
    }


    public static String getFactor(List<TestData> dataList) {

        List<String> list = new ArrayList<>();
        if (dataList.size() > 10) {
            throw new IllegalArgumentException("数组元素数量错误");
        }
        HashSet<String> hashSet = new HashSet<>();
        Map<String, Integer> factorMap = new HashMap<>(16);
        for (int i = 0; i < dataList.size(); i++) {
            boolean isExist = factorMap.containsKey(dataList.get(i).getQuality());
            if (!isExist) {
                factorMap.put(dataList.get(i).getQuality(), 1);
                hashSet.add(dataList.get(i).getQuality());
            } else {
                Integer count = factorMap.get(dataList.get(i).getQuality());
                factorMap.put(dataList.get(i).getQuality(), count + 1);
                hashSet.remove(dataList.get(i).getQuality());
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : hashSet) {
            stringBuilder.append(s);
            stringBuilder.append(", ");

        }
        String str = stringBuilder.toString();
        return str;


    }

    //求平均数
    public static BigDecimal getAvg(BigDecimal[] arr) {
        BigDecimal length = new BigDecimal(arr.length);
        BigDecimal sum = new BigDecimal("0");
        for (int i = 0; i < arr.length; i++) {
            sum = sum.add(arr[i]);
        }
        BigDecimal avg = sum.divide(length);
        avg.setScale(6, ROUND_HALF_DOWN);
        return avg;
    }

    public static BigDecimal getStd(BigDecimal[] arr, BigDecimal avg) {
        BigDecimal length = new BigDecimal(arr.length);
        BigDecimal sum = new BigDecimal("0");
        for (int i = 0; i < arr.length; i++) {
            BigDecimal num1 = arr[i].subtract(avg);
            num1 = num1.multiply(num1);
            sum = sum.add(num1);
        }
        sum = sum.divide(length);
        BigDecimal std = sqrt(sum);
        return std;

    }

    public Integer getNs(BigDecimal[] arr, BigDecimal avg, BigDecimal std) {

        BigDecimal numLow = avg.subtract(std.multiply(new BigDecimal("3")));
        BigDecimal numHigh = avg.add(std.multiply(new BigDecimal("3")));
        Integer ns = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].compareTo(numLow) == -1 || arr[i].compareTo(numHigh) == 1) {
                ns += 1;
            }
        }
        return ns;
    }



    public static BigDecimal sqrt(BigDecimal a) {
        BigDecimal _2=BigDecimal.valueOf(2.0);
        int precision=10;
        //某个操作使用的数字个数；结果舍入到此精度
        MathContext mc = new MathContext(precision, RoundingMode.HALF_UP);
        if (a.compareTo(BigDecimal.ZERO) == 0) {

            return new BigDecimal("0.000000");
        } else {
            BigDecimal x = a;
            int cnt = 0;
            int num = 10;
            while (cnt < num) {
                x = (x.add(a.divide(x, mc))).divide(_2, mc);
                cnt++;
            }
            return x;
        }
    }
    }



