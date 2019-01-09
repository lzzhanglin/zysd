package com.zysd.test.controller;

import com.zysd.test.entity.Resp;
import com.zysd.test.entity.TestData;
import com.zysd.test.mapper.FileMapper;
import com.zysd.test.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;
import java.util.Objects;

import static javax.print.attribute.standard.ReferenceUriSchemesSupported.HTTP;

/**
 * @author lzzhanglin
 * @date 2019/1/7 20:15
 */

@Controller
public class MainController {


    @Autowired
    private FileService fileService;

    @Autowired
    private FileMapper fileMapper;

    @RequestMapping("/main")
    public String   toMain(HttpServletRequest request) {
        return "main";
    }

    @RequestMapping("/upload")
    @ResponseBody
    public Resp upload(@RequestParam("file") MultipartFile file,HttpServletRequest request) {
        if (!file.isEmpty()) {

            String fileName = file.getOriginalFilename();
            try {
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(file.getOriginalFilename())));
                out.write(file.getBytes());
                out.flush();
                out.close();

                fileService.batchImport(fileName, file, request);
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

        } else {
            List<TestData> dataList = fileService.showData((pageNo-1)*pageSize, pageSize);
            request.setAttribute("dataList",dataList);

        }


        return "showData";
    }
}
