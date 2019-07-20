package com.jingxiang.datachange.repository.impl;

import org.springframework.stereotype.Repository;

import org.springframework.beans.factory.annotation.Value;

import com.jingxiang.datachange.repository.StorageProperties;

@Repository 
public class StoragePropertiesImpl implements StorageProperties{

  //private String prefix="/home/test0/maven/project/datachange/file/";
  private String prefix="/Users/huadanyu/Desktop/datachange/file/";

  @Override 
  public String getLocation(){
      return prefix;
  }

}
