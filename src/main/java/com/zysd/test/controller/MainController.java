package com.zysd.test.controller;

import com.zysd.test.entity.*;
import com.zysd.test.mapper.FileMapper;
import com.zysd.test.service.FileService;
import com.zysd.test.service.serviceImpl.FileServiceImpl;
import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static javax.print.attribute.standard.ReferenceUriSchemesSupported.HTTP;

/**
 * @author lzzhanglin
 * @date 2019/1/7 20:15
 */

@Controller
public class MainController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private FileService fileService;

    @Autowired
    private FileMapper fileMapper;

    @RequestMapping({"/main","/"})
    public String   toMain(HttpServletRequest request) {
        return "main";
    }

    @RequestMapping("/upload")
    @ResponseBody
    public Resp upload(@RequestParam("file") MultipartFile file,HttpServletRequest request) {
        if (!file.isEmpty()) {

            String fileName = file.getOriginalFilename();
            try {
                String fileNewName = FileServiceImpl.getRandomFileName(fileName, 10);
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(fileNewName)));
                out.write(file.getBytes());
                out.flush();
                out.close();

                fileService.batchImport(fileName, file, request);
                //向数据库写入一条导入文件记录
                fileService.uploadFile(fileNewName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new Resp("success", "upload file success");

        }
        return new Resp("failed", "file is null");

    }

    @RequestMapping("/showData")
    public String showData(HttpServletRequest request) {

        Integer pageNo = 1;
        Integer pageSize = 10;

        if (!Objects.equals(null, request.getParameter("pageNo"))) {
            pageNo = Integer.valueOf(request.getParameter("pageNo"));

        }
        if (!Objects.equals(null, request.getParameter("pageSize"))) {
            pageSize = Integer.valueOf(request.getParameter("pageSize"));

        }
        Integer limit = (pageNo - 1) * pageSize;

        Integer total = fileMapper.getTotal();
        if (pageNo == 1) {
            request.setAttribute("isFirstPage", true);
        } else {
            request.setAttribute("isFirstPage",false);
        }
        //设置总页数，如果可以整除，则不用加一 否则需要加一
        if (total % pageSize != 0) {
            request.setAttribute("totalPages", total / pageSize + 1);

        }else {
            request.setAttribute("totalPages",total / pageSize );

        }
        if (pageNo == total / pageSize + 1 && total % pageSize != 0) {
            request.setAttribute("isLastPage",true);
        } else if (pageNo == total / pageSize && total % pageSize == 0) {
            request.setAttribute("isLastPage", true);
        } else {
            request.setAttribute("isLastPage",false);
        }
        //第一次默认赋值
        if ((Objects.equals(null, request.getParameter("pageNo")) ||
                Objects.equals(null, request.getParameter("pageSize")))) {
            request.setAttribute("pageNo", 1);
            request.setAttribute("pageSize", 10);
        } else {
            request.setAttribute("pageNo",pageNo);
            request.setAttribute("pageSize",pageSize);
        }

        if (pageNo == 1) {
            List<TestData> dataList = fileService.showData(0, 10);
            request.setAttribute("dataList",dataList);
            List<Map<String, Object>> analysisList = fileService.dataAnalysis(dataList);
            request.setAttribute("analysisList",analysisList);


        } else {
            List<TestData> dataList = fileService.showData((pageNo-1)*pageSize, pageSize);
            request.setAttribute("dataList",dataList);
            List<Map<String, Object>> analysisList = fileService.dataAnalysis(dataList);
            request.setAttribute("analysisList",analysisList);


        }


        return "showData";
    }

    @RequestMapping("/allFile")
    public String getAllFile(HttpServletRequest request) {
        List<UploadFile> uploadFileList = new ArrayList<>();
        uploadFileList = fileMapper.getAllFile();
        request.setAttribute("allFileList", uploadFileList);
        return "allFile";
    }


    @RequestMapping("/editData")
    public String editData(HttpServletRequest request) {
        return "dataManage";
    }

    @RequestMapping("/getDataForEdit")
    @ResponseBody
    public ReturnPage<List<TestData>> getDataForEdit(@RequestBody QueryParams querryParams) {
        Integer limit =querryParams.getLimit();
        Integer offset = querryParams.getOffset();
        List<TestData> dataList = fileService.getDataForEdit(limit, offset);
        Integer total = fileMapper.getTotal();
        ReturnPage<List<TestData>> returnPage = new ReturnPage<>(1,total,dataList);
        return returnPage;

    }

    @RequestMapping("/getDataInfo")
    @ResponseBody
    public TestData getDataInfo(HttpServletRequest request) {
        Long dataId = Long.valueOf(request.getParameter("dataId"));
        return fileService.getDataById(dataId);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Resp delete(HttpServletRequest request) {
        Long dataId = Long.valueOf(request.getParameter("dataId"));
        Integer resultRow = fileMapper.deleteDataById(dataId);
        if (resultRow == 1) {
            return new Resp("success", "delete data success");
        } else {
            return new Resp("failed", "delete file failed");
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public Resp update(HttpServletRequest request) {
        TestData testData = new TestData();
        testData.setDataId(Long.valueOf(request.getParameter("dataId")));
        testData.setA1(new BigDecimal(request.getParameter("a1")));
        testData.setA2(new BigDecimal(request.getParameter("a2")));
        testData.setA3(new BigDecimal(request.getParameter("a3")));
        testData.setA4(new BigDecimal(request.getParameter("a4")));
        testData.setA5(new BigDecimal(request.getParameter("a5")));
        testData.setA6(new BigDecimal(request.getParameter("a6")));
        testData.setA7(new BigDecimal(request.getParameter("a7")));
        testData.setA8(new BigDecimal(request.getParameter("a8")));
        testData.setA9(new BigDecimal(request.getParameter("a9")));
        testData.setA10(new BigDecimal(request.getParameter("a10")));
        testData.setA11(new BigDecimal(request.getParameter("a11")));
        testData.setA12(new BigDecimal(request.getParameter("a12")));
        testData.setQuality(request.getParameter("quality"));
        Integer resultRow = fileMapper.updateDataById(testData);
        if (resultRow == 1) {
            return new Resp("success", "update data success");
        } else {
            return new Resp("failed", "update data failed");
        }

    }

    @RequestMapping("/download")
    public void getDownload( HttpServletRequest request, HttpServletResponse response,
                             @RequestHeader(required = false) String
            range) throws FileNotFoundException {
        // Get your file stream from wherever.
        String downloadFileName = request.getParameter("fileName");
        String fullPath = ResourceUtils.getURL("classpath:").getPath();
        int lenOfPath = fullPath.length();
        fullPath = fullPath.substring(0, lenOfPath - 15);
        fullPath = fullPath + downloadFileName;

        logger.info("下载路径:" + fullPath);


        //**********************************

            //文件目录
            File music = new File(fullPath);


            //开始下载位置
            long startByte = 0;
            //结束下载位置
            long endByte = music.length() - 1;
        if (endByte == -1) {
            throw new IllegalArgumentException("文件为空");
        }

            //有range的话
            if (range != null && range.contains("bytes=") && range.contains("-")) {
                range = range.substring(range.lastIndexOf("=") + 1).trim();
                String ranges[] = range.split("-");
                try {
                    //判断range的类型
                    if (ranges.length == 1) {
                        //类型一：bytes=-2343
                        if (range.startsWith("-")) {
                            endByte = Long.parseLong(ranges[0]);
                        }
                        //类型二：bytes=2343-
                        else if (range.endsWith("-")) {
                            startByte = Long.parseLong(ranges[0]);
                        }
                    }
                    //类型三：bytes=22-2343
                    else if (ranges.length == 2) {
                        startByte = Long.parseLong(ranges[0]);
                        endByte = Long.parseLong(ranges[1]);
                    }

                } catch (NumberFormatException e) {
                    startByte = 0;
                    endByte = music.length() - 1;
                }
            }

            //要下载的长度（为啥要加一问小学数学老师去）
            long contentLength = endByte - startByte + 1;
            //文件名
            String fileName = music.getName();
            //文件类型
            String contentType = request.getServletContext().getMimeType(fileName);


            //各种响应头设置
            //参考资料：https://www.ibm.com/developerworks/cn/java/joy-down/index.html
            //坑爹地方一：看代码
            response.setHeader("Accept-Ranges", "bytes");
            //坑爹地方二：http状态码要为206
            response.setStatus(response.SC_PARTIAL_CONTENT);
            response.setContentType(contentType);
            response.setHeader("Content-Type", contentType);
            //这里文件名换你想要的，inline表示浏览器直接实用（我方便测试用的）
            //参考资料：http://hw1287789687.iteye.com/blog/2188500
            response.setHeader("Content-Disposition", "inline;filename=test.mp3");
            response.setHeader("Content-Length", String.valueOf(contentLength));
            //坑爹地方三：Content-Range，格式为
            // [要下载的开始位置]-[结束位置]/[文件总大小]
            response.setHeader("Content-Range", "bytes " + startByte + "-" + endByte + "/" + music.length());


            BufferedOutputStream outputStream = null;
            RandomAccessFile randomAccessFile = null;
            //已传送数据大小
            long transmitted = 0;
            try {
                randomAccessFile = new RandomAccessFile(music, "r");
                outputStream = new BufferedOutputStream(response.getOutputStream());
                byte[] buff = new byte[4096];
                int len = 0;
                randomAccessFile.seek(startByte);
                //坑爹地方四：判断是否到了最后不足4096（buff的length）个byte这个逻辑（(transmitted + len) <= contentLength）要放前面！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
                //不然会会先读取randomAccessFile，造成后面读取位置出错，找了一天才发现问题所在
                while ((transmitted + len) <= contentLength && (len = randomAccessFile.read(buff)) != -1) {
                    outputStream.write(buff, 0, len);
                    transmitted += len;
                    //停一下，方便测试，用的时候删了就行了
                    Thread.sleep(10);
                }
                //处理不足buff.length部分
                if (transmitted < contentLength) {
                    len = randomAccessFile.read(buff, 0, (int) (contentLength - transmitted));
                    outputStream.write(buff, 0, len);
                    transmitted += len;
                }

                outputStream.flush();
                response.flushBuffer();
                randomAccessFile.close();
                System.out.println("下载完毕：" + startByte + "--->" + endByte + "：" + transmitted);

            } catch (ClientAbortException e) {
                System.out.println("用户停止下载：" + startByte + "--->" + endByte + "：" + transmitted);
                //捕获此异常表示拥护停止下载
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (randomAccessFile != null) {
                        randomAccessFile.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }





