/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cnn;

/**
 *
 * @author dee
 */
public class NewClass1 {
    public static void main(String[] args) {
        Options opt = new Options();
        opt.put("aaa", 1);
        opt.put("aaa", 2);
        opt.put("aaa", 3);
        System.out.println(opt.size());
        int x = (int)opt.get("aaa");
        System.out.println(x);
        System.out.println(opt.find("aaa"));
        System.out.println(opt.find("bbb"));
        System.out.println(opt.getOpt("aaa", 40));
    }
   
}
