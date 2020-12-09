package com.abc.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CallPython {
    public static void test() {
        String[] arguments = new String[]{"python", "D://runtime.py"};
        try {
            Process process = Runtime.getRuntime().exec(arguments);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(),
                    "GBK"));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            //java代码中的process.waitFor()返回值为0表示我们调用python脚本成功，
            //返回值为1表示调用python脚本失败，这和我们通常意义上见到的0与1定义正好相反
            int re = process.waitFor();
            System.out.println(re);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String callPython(String dir,String ...parameters){
        //"D://runtime.py"
        String[] arguments = new String[parameters.length+2];
        arguments[0]="python";
        arguments[1]=dir;
        int i=2;
        for(String parameter:parameters){
            arguments[i] = parameter;
        }
        try {
            Process process = Runtime.getRuntime().exec(arguments);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(),
                    "GBK"));
            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            //java代码中的process.waitFor()返回值为0表示我们调用python脚本成功，
            //返回值为1表示调用python脚本失败，这和我们通常意义上见到的0与1定义正好相反
            int re = process.waitFor();
            System.out.println(re);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




        public static void main(String[] args) {
            callPython("D://runtime.py");

    }
}
