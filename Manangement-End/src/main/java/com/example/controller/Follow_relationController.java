package com.example.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.entity.Follow_relation;
import com.example.service.Follow_relationService;
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
@RequestMapping("/api/follow_relation")
public class Follow_relationController {
    @Resource
    private Follow_relationService follow_relationService;
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
    public Result<?> save(@RequestBody Follow_relation follow_relation) {
        return Result.success(follow_relationService.save(follow_relation));
    }

    @PutMapping
    public Result<?> update(@RequestBody Follow_relation follow_relation) {
        return Result.success(follow_relationService.updateById(follow_relation));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        follow_relationService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return Result.success(follow_relationService.getById(id));
    }

    @GetMapping
    public Result<?> findAll() {
        return Result.success(follow_relationService.list());
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String name,
                                                @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Follow_relation> query = Wrappers.<Follow_relation>lambdaQuery().orderByDesc(Follow_relation::getId);
        if (StrUtil.isNotBlank(name)) {
            query.like(Follow_relation::getId, name);
        }
        return Result.success(follow_relationService.page(new Page<>(pageNum, pageSize), query));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {

        List<Map<String, Object>> list = CollUtil.newArrayList();

        List<Follow_relation> all = follow_relationService.list();
        for (Follow_relation obj : all) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("主键Id", obj.getId());
            row.put("用户id", obj.getUserId());
            row.put("目标类型（USER：用户，TAG：标签）", obj.getTargetType());
            row.put("目标id", obj.getTargetId());
            row.put("演出者id", obj.getArtistId());
            row.put("创建时间", obj.getCreateTime());
            row.put("更新时间", obj.getUpdateTime());

            list.add(row);
        }

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("关注-关联信息", "UTF-8");
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
        List<Follow_relation> saveList = new ArrayList<>();
        for (List<Object> row : lists) {
            Follow_relation obj = new Follow_relation();
            obj.setUserId((Long) row.get(1));
            obj.setTargetType((String) row.get(2));
            obj.setTargetId((Long) row.get(3));
            obj.setArtistId((Long) row.get(4));
            obj.setCreateTime(DateUtil.parseDateTime((String) row.get(5)));
            obj.setUpdateTime(DateUtil.parseDateTime((String) row.get(6)));

            saveList.add(obj);
        }
        follow_relationService.saveBatch(saveList);
        return Result.success();
    }

}
