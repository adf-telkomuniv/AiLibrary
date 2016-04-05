/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gsp;

import misc.FileIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author ANDITYAARIFIANTO
 */
public class GSP {

    HashSet<String> current;
    ArrayList<String> goal;
    Stack<String> stack = new Stack();
    ArrayList<String> queue = new ArrayList();

    public void start(String currentFile, String goalFile) throws IOException {
        current = new HashSet(FileIO.readFileString(currentFile));
        goal = FileIO.readFileString(goalFile);

        // tambahkan semua goal ke dalam stack
        goal.forEach((g) -> stack.add(g));

        System.out.println("initial : " + current);
        System.out.println("goal : " + goal);

        // forward chaining
        // proses hingga stack kosong
        Scanner next = new Scanner(System.in);
        int it = 0;
        while (!stack.isEmpty()) {
            System.out.println("it = " + (it++));
            System.out.println("current : " + current);
            System.out.println("stack : " + stack);
            System.out.println("queue : " + queue);
            String st = stack.peek();
            if (current.contains(st)) {
                // jika top stack sudah ada di current, 
                // hapus top stack 
                // jika top stack tidak ada di goal, hapus current yg sama
                System.out.println(" -- remove " + st + " from stack");
                stack.pop();
            } // 
            // jika top stack adalah operator, 
            // pindahkan operator ke queue
            // tambahkan state efek operator ke current
            else if (st.startsWith("stack")) {
                char param1 = st.charAt(6);
                char param2 = st.charAt(8);
                if ((current.contains("holding(" + param1 + ")")) & (current.contains("clear(" + param2 + ")"))) {
                    System.out.println(" -- do stack " + param1 + "," + param2);
                    current.add("on(" + param1 + "," + param2 + ")");
                    current.add("armempty");
                    current.remove("holding(" + param1 + ")");
                    current.remove("clear(" + param2 + ")");
                    stack.pop();
                    queue.add(st);
                } else {
                    System.out.println(" -- start over stack -----------------------------");
                    stack.push("clear(" + param2 + ")");
                    stack.push("holding(" + param1 + ")");
                }
            } else if (st.startsWith("unstack")) {
                char param1 = st.charAt(8);
                char param2 = st.charAt(10);
                if ((current.contains("on(" + param1 + "," + param2 + ")")) & (current.contains("armempty"))) {
                    System.out.println(" -- do unstack " + param1 + "," + param2);
                    current.add("holding(" + param1 + ")");
                    current.add("clear(" + param2 + ")");
                    current.remove("on(" + param1 + "," + param2 + ")");
                    current.remove("armempty");
                    stack.pop();
                    queue.add(st);
                } else {
                    System.out.println(" -- start over unstack -----------------------------");
                    stack.push("armempty");
                    stack.push("clear(" + param1 + ")");
                    stack.push("on(" + param1 + "," + param2 + ")");
                }
            } else if (st.startsWith("pickup")) {
                char param1 = st.charAt(7);
                if ((current.contains("ontable(" + param1 + ")")) & (current.contains("armempty"))) {
                    System.out.println(" -- do pickup " + param1);
                    current.add("holding(" + param1 + ")");
                    current.remove("ontable(" + param1 + ")");
                    current.remove("armempty");
                    stack.pop();
                    queue.add(st);
                } else {
                    System.out.println(" -- start over pickup -----------------------------");
                    stack.push("armempty");
                    stack.push("clear(" + param1 + ")");
                    stack.push("ontable(" + param1 + ")");
                }
            } else if (st.startsWith("putdown")) {
                char param1 = st.charAt(8);
                if (current.contains("holding(" + param1 + ")")) {
                    System.out.println(" -- do putdown " + param1);
                    current.add("ontable(" + param1 + ")");
                    current.add("armempty");
                    current.remove("holding(" + param1 + ")");
                    stack.pop();
                    queue.add(st);
                } else {
                    System.out.println(" -- start over putdown -----------------------------");
                    stack.push("putdown(" + param1 + ")");
                    stack.push("holding(" + param1 + ")");
                }
            }//
            // jika top stack adalah state
            // cari operator yang paling sesuai
            // tambahkan operator ke stack
            // tambahkan precondition operator ke stack
            else if (st.startsWith("ontable")) {
                // ontable (x)
                // panggil putdown (x)
                char param1 = st.charAt(8);
                System.out.println(" -- putdown " + param1);
                stack.push("putdown(" + param1 + ")");
                stack.push("holding(" + param1 + ")");
            } else if (st.startsWith("on")) {
                // on (x,y)
                // panggil stack (x,y)
                char param1 = st.charAt(3);
                char param2 = st.charAt(5);
                System.out.println(" -- stack " + param1 + "," + param2);
                stack.push("stack(" + param1 + "," + param2 + ")");
                stack.push("clear(" + param2 + ")");
                stack.push("holding(" + param1 + ")");
            } else if (st.startsWith("clear")) {
                // clear (x)
                // cek apakah ada on (x,y) di current, 
                // kalau ada panggil unstack (x,y)
                char param2 = st.charAt(6);
                for (String c : current) {
                    if (c.startsWith("on(") & c.endsWith(param2 + ")")) {
                        char param1 = c.charAt(3);
                        System.out.println(" -- unstack " + param1 + "," + param2);
                        stack.push("unstack(" + param1 + "," + param2 + ")");
                        stack.push("armempty");
                        stack.push("clear(" + param1 + ")");
                        stack.push("on(" + param1 + "," + param2 + ")");
                        break;
                    }
                }
            } else if (st.startsWith("holding")) {
                // holding (x)
                // cek apakah ada on (x,y) di current, 
                // kalau ada panggil unstack (x,y)
                // kalau tidak ada, panggil pickup (x)
                char param1 = st.charAt(8);
                boolean onFound = false;
                for (String c : current) {
                    if (c.startsWith("on(" + param1)) {
                        char param2 = c.charAt(5);
                        System.out.println(" -- unstack " + param1 + "," + param2);
                        stack.push("unstack(" + param1 + "," + param2 + ")");
                        stack.push("armempty");
                        stack.push("clear(" + param1 + ")");
                        stack.push("on(" + param1 + "," + param2 + ")");
                        onFound = true;
                        break;
                    }
                }
                if (!onFound) {
                    System.out.println(" -- pickup " + param1);
                    stack.push("pickup(" + param1 + ")");
                    stack.push("armempty");
                    stack.push("clear(" + param1 + ")");
                    stack.push("ontable(" + param1 + ")");
                }
            } else if (st.startsWith("armempty")) {
                // get param holding (x) dari current
                // cek apakah ada on (x,y) di stack
                // jika ada 
                // -- cek apakah ada stack (x,y) di stack
                // -- jika ada panggil putdown (x)
                // -- jika tidak ada panggil stack (x,y)
                // jika tidak ada panggil putdown (x)
                for (String c1 : current) {
                    if (c1.startsWith("holding")) {
                        char param1 = c1.charAt(8);
                        boolean onFound = false;
                        for (String s : stack) {
                            if (s.startsWith("on(" + param1)) {
                                char param2 = s.charAt(5);
                                if (!stack.contains("stack(" + param1 + "," + param2 + ")")) {
                                    System.out.println(" -- stack " + param1 + "," + param2);
                                    stack.push("stack(" + param1 + "," + param2 + ")");
                                    stack.push("clear(" + param2 + ")");
                                    stack.push("holding(" + param1 + ")");
                                } else {
                                    System.out.println(" -- putdown " + param1);
                                    stack.push("putdown(" + param1 + ")");
                                    stack.push("holding(" + param1 + ")");
                                }
                                onFound = true;
                                break;
                            }
                        }
                        if (!onFound) {
                            System.out.println(" -- putdown " + param1);
                            stack.push("putdown(" + param1 + ")");
                            stack.push("holding(" + param1 + ")");
                        }
                        break;
                    }
                }
            }
            System.out.println("<Press Enter>");
            next.nextLine();
            if (stack.isEmpty() && !cekResult()) {
                System.out.println(" -- check again -- ");
                System.out.println("<Press Enter>");
                next.nextLine();
                goal.forEach(g -> stack.add(g));
            }
        }
        boolean result = cekResult();
        System.out.println("result found : " + result);
        System.out.println(queue);

        // backward chain to find optimum solution
        backwardChain();
    }

    public void backwardChain() {
        ArrayList<String> fix = new ArrayList();
        String temp = queue.get(0);
        for (int i = 1; i < queue.size(); i++) {
            if (temp.startsWith("stack") && queue.get(i).startsWith("unstack")) {
                if ((temp.charAt(6) == queue.get(i).charAt(8)) && (temp.charAt(8) == queue.get(i).charAt(10))) {
                    System.out.println("skip");
                    i++;
                } else {
                    fix.add(temp);
                }
            } else if (temp.startsWith("unstack") && queue.get(i).startsWith("stack")) {
                if ((temp.charAt(8) == queue.get(i).charAt(6)) && (temp.charAt(10) == queue.get(i).charAt(8))) {
                    System.out.println("skip");
                    i++;
                } else {
                    fix.add(temp);
                }
            } else if (temp.startsWith("pickup") && queue.get(i).startsWith("putdown")) {
                if (temp.charAt(7) == queue.get(i).charAt(8)) {
                    System.out.println("skip");
                    i++;
                } else {
                    fix.add(temp);
                }
            } else if (temp.startsWith("putdown") && queue.get(i).startsWith("pickup")) {
                if (temp.charAt(8) == queue.get(i).charAt(7)) {
                    System.out.println("skip");
                    i++;
                } else {
                    fix.add(temp);
                }
            } else {
                fix.add(temp);
            }
            temp = queue.get(i);
        }

        fix.add(temp);

        System.out.println(
                "fix : " + fix);
    }

    public boolean cekResult() {
        for (String g : goal) {
            if (!current.contains(g)) {
                return false;
            }
        }
        return true;
    }

}
