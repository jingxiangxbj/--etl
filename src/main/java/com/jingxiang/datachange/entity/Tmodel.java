package com.jingxiang.datachange.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class Tmodel implements Serializable {
    private static final long serialVersionUID = -7573904024872252113L;
    private int count;

    public Tmodel(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Tmodel{" +
                "count=" + count +
                '}';
    }
}
