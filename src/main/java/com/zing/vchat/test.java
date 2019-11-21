package com.zing.vchat;


import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class test {

    public static void main(String[] args) {
        String cidr = "11.1.1.0/24";
        String[] subs = "10.10.10.0".split("\\.");
        System.out.println(Integer.parseInt("-1"));


        System.out.println();
        System.out.println(cidr.substring(cidr.indexOf("/")+1));
        System.out.println(Integer.parseInt("24"));
        System.out.println(Integer.parseInt(cidr.substring(cidr.indexOf("/")+1)));
    }
}
