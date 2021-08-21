package com.example.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.entity.Feedback;
import com.example.service.FeedbackService;
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
@RequestMapping("/api/feedback")
public class FeedbackController {
    @Resource
    private FeedbackService feedbackService;
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
    public Result<?> save(@RequestBody Feedback feedback) {
        return Result.success(feedbackService.save(feedback));
    }

    @PutMapping
    public Result<?> update(@RequestBody Feedback feedback) {
        return Result.success(feedbackService.updateById(feedback));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        feedbackService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return Result.success(feedbackService.getById(id));
    }

    @GetMapping
    public Result<?> findAll() {
        return Result.success(feedbackService.list());
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String name,
                                                @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Feedback> query = Wrappers.<Feedback>lambdaQuery().orderByDesc(Feedback::getId);
        if (StrUtil.isNotBlank(name)) {
            query.like(Feedback::getId, name);
        }
        return Result.success(feedbackService.page(new Page<>(pageNum, pageSize), query));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {

        List<Map<String, Object>> list = CollUtil.newArrayList();

        List<Feedback> all = feedbackService.list();
        for (Feedback obj : all) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("主键Id", obj.getId());
            row.put("类型", obj.getType());
            row.put("内容", obj.getContent());
            row.put("联系方式", obj.getContactInfo());
            row.put("用户id", obj.getUserId());
            row.put("演出者id", obj.getArtistId());
            row.put("创建时间", obj.getCreateTime());
            row.put("更新时间", obj.getUpdateTime());

            list.add(row);
        }

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("意见反馈信息", "UTF-8");
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
        List<Feedback> saveList = new ArrayList<>();
        for (List<Object> row : lists) {
            Feedback obj = new Feedback();
            obj.setType((String) row.get(1));
            obj.setContent((String) row.get(2));
            obj.setContactInfo((String) row.get(3));
            obj.setUserId((Long) row.get(4));
            obj.setArtistId((Long) row.get(5));
            obj.setCreateTime(DateUtil.parseDateTime((String) row.get(6)));
            obj.setUpdateTime(DateUtil.parseDateTime((String) row.get(7)));

            saveList.add(obj);
        }
        feedbackService.saveBatch(saveList);
        return Result.success();
    }

}
