package com.example.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.entity.Activity;
import com.example.service.ActivityService;
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
@RequestMapping("/api/activity")
public class ActivityController {
    @Resource
    private ActivityService activityService;
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
    public Result<?> save(@RequestBody Activity activity) {
        return Result.success(activityService.save(activity));
    }

    @PutMapping
    public Result<?> update(@RequestBody Activity activity) {
        return Result.success(activityService.updateById(activity));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        activityService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return Result.success(activityService.getById(id));
    }

    @GetMapping
    public Result<?> findAll() {
        return Result.success(activityService.list());
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String name,
                                                @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Activity> query = Wrappers.<Activity>lambdaQuery().orderByDesc(Activity::getId);
        if (StrUtil.isNotBlank(name)) {
            query.like(Activity::getName, name);
        }
        return Result.success(activityService.page(new Page<>(pageNum, pageSize), query));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {

        List<Map<String, Object>> list = CollUtil.newArrayList();

        List<Activity> all = activityService.list();
        for (Activity obj : all) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("主键Id", obj.getId());
            row.put("演出名称", obj.getName());
            row.put("演出起始时间", obj.getShowStartTime());
            row.put("演出结束时间", obj.getShowEndTime());
            row.put("场地城市", obj.getVenueCity());
            row.put("场地名称", obj.getVenueName());
            row.put("场地地址", obj.getVenueAddress());
            row.put("海报图片", obj.getPosterImage());
            row.put("数据来源渠道（秀动：SHOWSTART）", obj.getSourceChannel());
            row.put("数据来源渠道-对象id", obj.getSourceId());
            row.put("来源url", obj.getSourceUrl());
            row.put("线上活动", obj.getOnline());
            row.put("演出者id", obj.getArtistId());
            row.put("创建时间", obj.getCreateTime());
            row.put("更新时间", obj.getUpdateTime());

            list.add(row);
        }

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("演出信息", "UTF-8");
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
        List<Activity> saveList = new ArrayList<>();
        for (List<Object> row : lists) {
            Activity obj = new Activity();
            obj.setName((String) row.get(1));
            obj.setShowStartTime(DateUtil.parseDateTime((String) row.get(2)));
            obj.setShowEndTime(DateUtil.parseDateTime((String) row.get(3)));
            obj.setVenueCity((String) row.get(4));
            obj.setVenueName((String) row.get(5));
            obj.setVenueAddress((String) row.get(6));
            obj.setPosterImage((String) row.get(7));
            obj.setSourceChannel((String) row.get(8));
            obj.setSourceId((String) row.get(9));
            obj.setSourceUrl((String) row.get(10));
            obj.setOnline(Integer.valueOf((String) row.get(11)));
            obj.setArtistId((Long) row.get(12));
            obj.setCreateTime(DateUtil.parseDateTime((String) row.get(13)));
            obj.setUpdateTime(DateUtil.parseDateTime((String) row.get(14)));

            saveList.add(obj);
        }
        activityService.saveBatch(saveList);
        return Result.success();
    }

}
