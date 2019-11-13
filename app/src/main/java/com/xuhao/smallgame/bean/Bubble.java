package com.xuhao.smallgame.bean;

import com.xuhao.smallgame.util.ColorUtil;

public class Bubble  {
    /** 气泡半径 */
    private float radius;
    /** 上升速度 */
    private float speedY;
    /** 平移速度 */
    private float speedX;
    /** 气泡x坐标 */
    private float x;
    /** 气泡y坐标 */
    private float y;
    /** 距离顶部消失距离 */
    private float removeY;
    /** 渐变色 */
    private int[] shaderColor;
    /* 原使色 */
    private int originalColor;

    private boolean isToach;

    public boolean isToach() {
        return isToach;
    }

    public void setToach(boolean toach) {
        isToach = toach;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setRemoveY(float y) {
        this.removeY = y;
    }
    public float getRemoveY() {
        return removeY;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public void setShaderColor(int color) {
        originalColor = color;
        this.shaderColor = new int[] {
                ColorUtil.parseColor(color, "#CC"),
                ColorUtil.parseColor(color, "#99"),
                ColorUtil.parseColor(color, "#73"),
                ColorUtil.parseColor(color, "#66"),
                ColorUtil.parseColor(color, "#59"),
                ColorUtil.parseColor(color, "#4D"),
                ColorUtil.parseColor(color, "#33"),
                ColorUtil.parseColor(color, "#1A"),
        };
    }

    public int[] getShaderColor() {
        return this.shaderColor;
    }

    public int getOriginalColor() {
        return originalColor;
    }
}
