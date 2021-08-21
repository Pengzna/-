package com.example.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.entity.Tag;
import com.example.service.TagService;
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
@RequestMapping("/api/tag")
public class TagController {
    @Resource
    private TagService tagService;
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
    public Result<?> save(@RequestBody Tag tag) {
        return Result.success(tagService.save(tag));
    }

    @PutMapping
    public Result<?> update(@RequestBody Tag tag) {
        return Result.success(tagService.updateById(tag));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        tagService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return Result.success(tagService.getById(id));
    }

    @GetMapping
    public Result<?> findAll() {
        return Result.success(tagService.list());
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String name,
                                                @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Tag> query = Wrappers.<Tag>lambdaQuery().orderByDesc(Tag::getId);
        if (StrUtil.isNotBlank(name)) {
            query.like(Tag::getName, name);
        }
        return Result.success(tagService.page(new Page<>(pageNum, pageSize), query));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {

        List<Map<String, Object>> list = CollUtil.newArrayList();

        List<Tag> all = tagService.list();
        for (Tag obj : all) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("主键", obj.getId());
            row.put("标签名称", obj.getName());
            row.put("创建人id", obj.getCreateUserId());
            row.put("粉丝人数", obj.getFansCount());
            row.put("演出者id", obj.getArtistId());
            row.put("创建时间", obj.getCreateTime());
            row.put("更新时间", obj.getUpdateTime());

            list.add(row);
        }

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("标签信息", "UTF-8");
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
        List<Tag> saveList = new ArrayList<>();
        for (List<Object> row : lists) {
            Tag obj = new Tag();
            obj.setName((String) row.get(1));
            obj.setCreateUserId((Long) row.get(2));
            obj.setFansCount(Integer.valueOf((String) row.get(3)));
            obj.setArtistId((Long) row.get(4));
            obj.setCreateTime(DateUtil.parseDateTime((String) row.get(5)));
            obj.setUpdateTime(DateUtil.parseDateTime((String) row.get(6)));

            saveList.add(obj);
        }
        tagService.saveBatch(saveList);
        return Result.success();
    }

}
