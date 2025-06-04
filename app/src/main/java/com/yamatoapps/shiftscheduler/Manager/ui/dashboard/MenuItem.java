package com.yamatoapps.shiftscheduler.Manager.ui.dashboard;

import android.view.View;

public class MenuItem {
    public String menu_name = "";
    public String menu_image = "";
    public  int menu_image_int = 0;
    public  int background_image_int = 0;
    public View.OnClickListener onclickListener;



    public MenuItem(String menu_name, String menu_image, int menu_image_int) {
        this.menu_name = menu_name;
        this.menu_image = menu_image;
        this.menu_image_int = menu_image_int;
    }
    public MenuItem(String menu_name,  int menu_image_int) {
        this.menu_name = menu_name;
        this.menu_image_int = menu_image_int;
    }
    public MenuItem(String menu_name,  int menu_image_int, View.OnClickListener onclickListener) {
        this.menu_name = menu_name;
        this.menu_image_int = menu_image_int;
        this.onclickListener = onclickListener;
    }
    public MenuItem(String menu_name,  int menu_image_int,int background_image_int) {
        this.menu_name = menu_name;
        this.menu_image_int = menu_image_int;
        this.background_image_int = background_image_int;
    }
}
