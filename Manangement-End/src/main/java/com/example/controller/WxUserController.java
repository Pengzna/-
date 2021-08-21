package com.example.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.entity.WxUser;
import com.example.service.WxUserService;
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
@RequestMapping("/api/wxUser")
public class WxUserController {
    @Resource
    private WxUserService wxUserService;
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
    public Result<?> save(@RequestBody WxUser wxUser) {
        return Result.success(wxUserService.save(wxUser));
    }

    @PutMapping
    public Result<?> update(@RequestBody WxUser wxUser) {
        return Result.success(wxUserService.updateById(wxUser));
    }

    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Long id) {
        wxUserService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<?> findById(@PathVariable Long id) {
        return Result.success(wxUserService.getById(id));
    }

    @GetMapping
    public Result<?> findAll() {
        return Result.success(wxUserService.list());
    }

    @GetMapping("/page")
    public Result<?> findPage(@RequestParam(required = false, defaultValue = "") String name,
                                                @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        LambdaQueryWrapper<WxUser> query = Wrappers.<WxUser>lambdaQuery().orderByDesc(WxUser::getId);
        if (StrUtil.isNotBlank(name)) {
            query.like(WxUser::getName, name);
        }
        return Result.success(wxUserService.page(new Page<>(pageNum, pageSize), query));
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws IOException {

        List<Map<String, Object>> list = CollUtil.newArrayList();

        List<WxUser> all = wxUserService.list();
        for (WxUser obj : all) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("主键Id", obj.getId());
            row.put("用户名", obj.getName());
            row.put("性别（0：女，1：男）", obj.getGender());
            row.put("头像url", obj.getAvatarUrl());
            row.put("个人简介", obj.getDescription());
            row.put("微信小程序unionId", obj.getMiniWeixinUnionId());
            row.put("微信小程序openId", obj.getMiniWeixinOpenId());
            row.put("QQ小程序unionId", obj.getMiniQqUnionId());
            row.put("QQ小程序openId", obj.getMiniQqOpenId());
            row.put("国家", obj.getCountry());
            row.put("省", obj.getProvince());
            row.put("市", obj.getCity());
            row.put("大学", obj.getUniversity());
            row.put("关注人数", obj.getFollowCount());
            row.put("粉丝人数", obj.getFansCount());
            row.put("私信未读", obj.getLetterUnread());
            row.put("评论未读", obj.getCommentUnread());
            row.put("@未读", obj.getAtUnread());
            row.put("通知未读", obj.getNotificationUnread());
            row.put("演出者id", obj.getArtistId());
            row.put("创建时间", obj.getCreateTime());
            row.put("更新时间", obj.getUpdateTime());
            row.put("大学id", obj.getUniId());

            list.add(row);
        }

        // 2. 写excel
        ExcelWriter writer = ExcelUtil.getWriter(true);
        writer.write(list, true);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("微信端用户管理信息", "UTF-8");
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
        List<WxUser> saveList = new ArrayList<>();
        for (List<Object> row : lists) {
            WxUser obj = new WxUser();
            obj.setName((String) row.get(1));
            obj.setGender(Integer.valueOf((String) row.get(2)));
            obj.setAvatarUrl((String) row.get(3));
            obj.setDescription((String) row.get(4));
            obj.setMiniWeixinUnionId((String) row.get(5));
            obj.setMiniWeixinOpenId((String) row.get(6));
            obj.setMiniQqUnionId((String) row.get(7));
            obj.setMiniQqOpenId((String) row.get(8));
            obj.setCountry((String) row.get(9));
            obj.setProvince((String) row.get(10));
            obj.setCity((String) row.get(11));
            obj.setUniversity((String) row.get(12));
            obj.setFollowCount(Integer.valueOf((String) row.get(13)));
            obj.setFansCount(Integer.valueOf((String) row.get(14)));
            obj.setLetterUnread(Integer.valueOf((String) row.get(15)));
            obj.setCommentUnread(Integer.valueOf((String) row.get(16)));
            obj.setAtUnread(Integer.valueOf((String) row.get(17)));
            obj.setNotificationUnread(Integer.valueOf((String) row.get(18)));
            obj.setArtistId((Long) row.get(19));
            obj.setCreateTime(DateUtil.parseDateTime((String) row.get(20)));
            obj.setUpdateTime(DateUtil.parseDateTime((String) row.get(21)));
            obj.setUniId((String) row.get(22));

            saveList.add(obj);
        }
        wxUserService.saveBatch(saveList);
        return Result.success();
    }

}
