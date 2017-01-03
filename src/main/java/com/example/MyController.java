package com.example;



import com.alibaba.fastjson.JSON;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by maoyusu on 2017/1/2.
 */
@Controller
@RequestMapping("/")
public class MyController {

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;


    @RequestMapping("post")
    @ResponseBody
    public void post() {
        File file = new File("/Users/maoyusu/faker.json");
        Long fileLength = file.length();
        byte[] content = new byte[fileLength.intValue()];
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            in.read(content);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String json = new String(content);
        content=null;
        System.gc();
        try {
            System.out.println("gc1");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<FakeProfile> fakeProfiles = JSON.parseArray(json, FakeProfile.class);
        json=null;
        System.gc();
        try {
            System.out.println("gc2");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.batch(fakeProfiles);
        fakeProfiles = null;
        System.gc();

    }

    private void batch(List<FakeProfile> fakeProfiles) {
        String sql = "insert into mys.profile (name, sex, blood_group, birth_date, user_name, mail, residence, company, address, job, ssn, website)" +
            "values(:name, :sex, :bloodGroup, :birthDate, :userName, :mail, :residence, :company, :address, :job, :ssn, :website)";
        SqlParameterSource[] batch = SqlParameterSourceUtils.createBatch(fakeProfiles.toArray());
        namedParameterJdbcTemplate.batchUpdate(sql, batch);
    }
}
