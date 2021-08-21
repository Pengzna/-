package com.example.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.entity.T_notice;
import com.example.service.T_noticeService;
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
@RequestMapping("/api/t_notice")
public class T_noticeController {
    @Resource
    private T_noticeService t_noticeService;
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
    public Result<?> save(@RequestBody T_notice t_notice) {
        return Result.success(t_noticeService.save(t_notice));
    }

    @PutMapping
    public Result<?> update(@RequestBody T_notice t_notice) {
        return Result.success(t_noticeService.updateById(t_notice));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        t_noticeService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return Result.success(t_noticeService.getById(id));
    }

    @GetMapping
    public Result<?> findAll() {
        return Result.success(t_noticeService.list());
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String name,
                                                @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<T_notice> query = Wrappers.<T_notice>lambdaQuery().orderByDesc(T_notice::getId);
        if (StrUtil.isNotBlank(name)) {
            query.like(T_notice::getId, name);
        }
        return Result.success(t_noticeService.page(new Page<>(pageNum, pageSize), query));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {

        List<Map<String, Object>> list = CollUtil.newArrayList();

        List<T_notice> all = t_noticeService.list();
        for (T_notice obj : all) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("ID", obj.getId());
            row.put("标题", obj.getTitle());
            row.put("内容", obj.getContent());
            row.put("发布时间", obj.getTime());

            list.add(row);
        }

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("系统公告信息", "UTF-8");
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
        List<T_notice> saveList = new ArrayList<>();
        for (List<Object> row : lists) {
            T_notice obj = new T_notice();
            obj.setTitle((String) row.get(1));
            obj.setContent((String) row.get(2));
            obj.setTime((String) row.get(3));

            saveList.add(obj);
        }
        t_noticeService.saveBatch(saveList);
        return Result.success();
    }

}
