/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cnn;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author dee
 */
public class Options {

    private Map<String, Object> opt;

    public Options() {
        opt = new HashMap();
    }

    public void put(String fielad_name, Object value) {
        opt.put(fielad_name, value);
    }

    public Object getOpt(String[] field_name, Object default_value) {
        Object ret = default_value;
        for (String field : field_name) {
            Object f = opt.get(field);
            if (f != null) {
                ret = f;
            }
        }
        return ret;
    }

    public Object getOpt(String field, Object default_value) {
        Object f = opt.get(field);
        return (f == null ? default_value : f);
    }

    public Object get(String field) {
        return opt.get(field);
    }

//    public static Object getOpt(Map<String, Object> opt, String field) {
//        return opt.get(field);
//    }
}
