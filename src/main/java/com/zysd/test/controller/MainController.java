package com.zysd.test.controller;

import com.zysd.test.entity.Resp;
import com.zysd.test.entity.TestData;
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

import static javax.print.attribute.standard.ReferenceUriSchemesSupported.HTTP;

/**
 * @author lzzhanglin
 * @date 2019/1/7 20:15
 */

@Controller
public class MainController {


    @Autowired
    private FileService fileService;

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
        List<TestData> dataList = fileService.showData(0, 10);
        request.setAttribute("dataList",dataList);
        return "showData";
    }
}
