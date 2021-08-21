package com.example.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.entity.Topic;
import com.example.service.TopicService;
import com.example.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;
import com.example.exception.CustomException;
import cn.hutool.core.util.StrUtil;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/topic")
public class TopicController {
    @Resource
    private TopicService topicService;
    @Resource
    private HttpServletRequest request;

    public User getUser() {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new CustomException("-1", "请登录");
        }
        return user;
    }

    @PostMapping
    public Result<?> save(@RequestBody Topic topic) {
        return Result.success(topicService.save(topic));
    }

    @PutMapping
    public Result<?> update(@RequestBody Topic topic) {
        return Result.success(topicService.updateById(topic));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        topicService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return Result.success(topicService.getById(id));
    }

    @GetMapping
    public Result<?> findAll() {
        return Result.success(topicService.list());
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String name,
                                                @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Topic> query = Wrappers.<Topic>lambdaQuery().orderByDesc(Topic::getId);
        if (StrUtil.isNotBlank(name)) {
            query.like(Topic::getName, name);
        }
        return Result.success(topicService.page(new Page<>(pageNum, pageSize), query));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {

        List<Map<String, Object>> list = CollUtil.newArrayList();

        List<Topic> all = topicService.list();
        for (Topic obj : all) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("主键", obj.getId());
            row.put("话题名称", obj.getName());
            row.put("封面url", obj.getCoverUrl());
            row.put("创建人id", obj.getCreateUserId());
            row.put("最新更新者id", obj.getLastUpdateUserId());
            row.put("演出者id", obj.getArtistId());
            row.put("创建时间", obj.getCreateTime());
            row.put("更新时间", obj.getUpdateTime());

            list.add(row);
        }

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("话题信息", "UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        ServletOutputStream out = response.getOutputStream();
        writer.flush(out, true);
        writer.close();
        IoUtil.close(System.out);
    }

    @GetMapping("/upload/{fileId}")
    public Result<?> upload(@PathVariable String fileId) {
        String basePath = System.getProperty("user.dir") + "/src/main/resources/static/file/";
        List<String> fileNames = FileUtil.listFileNames(basePath);
        String file = fileNames.stream().filter(name -> name.contains(fileId)).findAny().orElse("");
        List<List<Object>> lists = ExcelUtil.getReader(basePath + file).read(1);
        List<Topic> saveList = new ArrayList<>();
        for (List<Object> row : lists) {
            Topic obj = new Topic();
            obj.setName((String) row.get(1));
            obj.setCoverUrl((String) row.get(2));
            obj.setCreateUserId((Long) row.get(3));
            obj.setLastUpdateUserId((Long) row.get(4));
            obj.setArtistId((Long) row.get(5));
            obj.setCreateTime(DateUtil.parseDateTime((String) row.get(6)));
            obj.setUpdateTime(DateUtil.parseDateTime((String) row.get(7)));

            saveList.add(obj);
        }
        topicService.saveBatch(saveList);
        return Result.success();
    }

}
