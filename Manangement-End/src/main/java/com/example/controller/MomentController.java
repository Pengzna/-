package com.example.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.entity.Moment;
import com.example.service.MomentService;
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
@RequestMapping("/api/moment")
public class MomentController {
    @Resource
    private MomentService momentService;
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
    public Result<?> save(@RequestBody Moment moment) {
        return Result.success(momentService.save(moment));
    }

    @PutMapping
    public Result<?> update(@RequestBody Moment moment) {
        return Result.success(momentService.updateById(moment));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        momentService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return Result.success(momentService.getById(id));
    }

    @GetMapping
    public Result<?> findAll() {
        return Result.success(momentService.list());
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String name,
                                                @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<Moment> query = Wrappers.<Moment>lambdaQuery().orderByDesc(Moment::getId);
        if (StrUtil.isNotBlank(name)) {
            query.like(Moment::getId, name);
        }
        return Result.success(momentService.page(new Page<>(pageNum, pageSize), query));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {

        List<Map<String, Object>> list = CollUtil.newArrayList();

        List<Moment> all = momentService.list();
        for (Moment obj : all) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("主键id", obj.getId());
            row.put("发送者id", obj.getUserId());
            row.put("类型（TEXT：纯文本，IMAGE：图片，VIDEO：视频）", obj.getType());
            row.put("文本内容", obj.getTextContent());
            row.put("活动id", obj.getActivityId());
            row.put("点赞数量", obj.getLikeCount());
            row.put("评论数量", obj.getCommentCount());
            row.put("转发数量", obj.getRepostCount());
            row.put("分享数量", obj.getShareCount());
            row.put("查看次数", obj.getViewCount());
            row.put("转发的动态id", obj.getRepostMomentId());
            row.put("状态（0：已屏蔽，1：正常，2：已删除）", obj.getStatus());
            row.put("演出者id", obj.getArtistId());
            row.put("创建时间", obj.getCreateTime());
            row.put("更新时间", obj.getUpdateTime());

            list.add(row);
        }

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("动态信息", "UTF-8");
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
        List<Moment> saveList = new ArrayList<>();
        for (List<Object> row : lists) {
            Moment obj = new Moment();
            obj.setUserId((Long) row.get(1));
            obj.setType((String) row.get(2));
            obj.setTextContent((String) row.get(3));
            obj.setActivityId((Long) row.get(4));
            obj.setLikeCount(Integer.valueOf((String) row.get(5)));
            obj.setCommentCount(Integer.valueOf((String) row.get(6)));
            obj.setRepostCount(Integer.valueOf((String) row.get(7)));
            obj.setShareCount(Integer.valueOf((String) row.get(8)));
            obj.setViewCount(Integer.valueOf((String) row.get(9)));
            obj.setRepostMomentId((Long) row.get(10));
            obj.setStatus(Integer.valueOf((String) row.get(11)));
            obj.setArtistId((Long) row.get(12));
            obj.setCreateTime(DateUtil.parseDateTime((String) row.get(13)));
            obj.setUpdateTime(DateUtil.parseDateTime((String) row.get(14)));

            saveList.add(obj);
        }
        momentService.saveBatch(saveList);
        return Result.success();
    }

}
