package com.jingxiang.datachange.controller;

import com.jingxiang.datachange.entity.Menu;
import com.jingxiang.datachange.entity.Tree;
import com.jingxiang.datachange.service.MenuService;
import com.jingxiang.datachange.util.ResponseBo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class MenuController  {


  @Autowired
    private MenuService menuService;

    @RequestMapping("menu")
    public String index() {
        return "system/menu/menu";
    }

    @RequestMapping("menu/menu")
    @ResponseBody
    public ResponseBo getMenu(String userName) {
        try {
            List<Menu> menus = this.menuService.findUserMenus(userName);
            System.out.println(menus.toString());
            return ResponseBo.ok(menus);
        } catch (Exception e) {
            return ResponseBo.error("获取菜单失败！");
        }
    }

    @RequestMapping("menu/getMenu")
    @ResponseBody
    public ResponseBo getMenu(Long menuId) {
        try {
            Menu menu = this.menuService.findById(menuId);
            return ResponseBo.ok(menu);
        } catch (Exception e) {
            return ResponseBo.error("获取信息失败，请联系网站管理员！");
        }
    }

    @RequestMapping("menu/menuButtonTree")
    @ResponseBody
    public ResponseBo getMenuButtonTree() {
        try {
            Tree<Menu> tree = this.menuService.getMenuButtonTree();
            return ResponseBo.ok(tree);
        } catch (Exception e) {
            return ResponseBo.error("获取菜单列表失败！");
        }
    }

    @RequestMapping("menu/tree")
    @ResponseBody
    public ResponseBo getMenuTree() {
        try {
            Tree<Menu> tree = this.menuService.getMenuTree();
            return ResponseBo.ok(tree);
        } catch (Exception e) {
            return ResponseBo.error("获取菜单树失败！");
        }
    }

    @RequestMapping("menu/getUserMenu")
   @ResponseBody
   public ResponseBo getUserMenu(String userName) {
       try {
           Tree<Menu> tree = this.menuService.getUserMenu(userName);
           return ResponseBo.ok(tree);
        } catch (Exception e) {
            return ResponseBo.error("获取用户菜单失败！");
        }
   }

    @RequestMapping("menu/list")
    @RequiresPermissions("menu:list")
    @ResponseBody
    public List<Menu> menuList(Menu menu) {
        try {
            return this.menuService.findAllMenus(menu);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }




    @RequestMapping("menu/checkMenuName")
    @ResponseBody
    public boolean checkMenuName(String menuName, String type, String oldMenuName) {
        if (StringUtils.isNotBlank(oldMenuName) && menuName.equalsIgnoreCase(oldMenuName)) {
            return true;
        }
        Menu result = this.menuService.findByNameAndType(menuName, type);
        return result == null;
    }

    @RequiresPermissions("menu:add")
    @RequestMapping("menu/add")
    @ResponseBody
    public ResponseBo addMenu(Menu menu) {
        String name;
        if (Menu.TYPE_MENU.equals(menu.getType())) {
            name = "菜单";
        } else {
            name = "按钮";
        }
        try {
            this.menuService.addMenu(menu);
            return ResponseBo.ok("新增" + name + "成功！");
        } catch (Exception e) {
            return ResponseBo.error("新增" + name + "失败，请联系网站管理员！");
        }
    }

    @RequiresPermissions("menu:delete")
    @RequestMapping("menu/delete")
    @ResponseBody
    public ResponseBo deleteMenus(String ids) {
        try {
            this.menuService.deleteMeuns(ids);
            return ResponseBo.ok("删除成功！");
        } catch (Exception e) {
            return ResponseBo.error("删除失败，请联系网站管理员！");
        }
    }

    @RequiresPermissions("menu:update")
    @RequestMapping("menu/update")
    @ResponseBody
    public ResponseBo updateMenu(Menu menu) {
        String name;
        if (Menu.TYPE_MENU.equals(menu.getType()))
            name = "菜单";
        else
            name = "按钮";
        try {
            this.menuService.updateMenu(menu);
            return ResponseBo.ok("修改" + name + "成功！");
        } catch (Exception e) {
            return ResponseBo.error("修改" + name + "失败，请联系网站管理员！");
        }
    }


    @GetMapping("menu/urlList")
    @ResponseBody
    public List<Map<String, String>> getAllUrl() {
        return this.menuService.getAllUrl("1");
    }

}
