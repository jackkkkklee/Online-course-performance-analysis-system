package com.abc.util;

import org.python.core.*;
import org.python.util.PythonInterpreter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

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
    public static String callPython(String dir){
        //"D://runtime.py"
        String[] arguments = new String[2];
        arguments[0]="E://Anaconda//envs//ten//python";
        arguments[1]=dir;
        StringBuilder sb=new StringBuilder();

        try {
            Process process = Runtime.getRuntime().exec(arguments);
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(),
                    "GBK"));
            String line = null;
            while ((line = in.readLine()) != null) {
                sb.append(line);
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
        return sb.toString();
    }


    public static void callPythonByFunction(String dir,String imageDir) {
            PythonInterpreter interpreter = new PythonInterpreter();
            interpreter.execfile(dir);

            // 第一个参数为期望获得的函数（变量）的名字，第二个参数为期望返回的对象类型
            PyFunction pyFunction = interpreter.get("attention_analysis", PyFunction.class);

            //调用函数，如果函数需要参数，在Java中必须先将参数转化为对应的“Python类型”
            PyObject pyobj = pyFunction.__call__(new PyString(imageDir));
            System.out.println("the anwser is: " + pyobj);
    }


    public static String callPython() {

        return callPython("E://aut//RD//project//attentiveness-detection-master//test1.py");
    }








        public static void main(String[] args) {
            //无参调用，调用main
            //callPython("D://runtime//runtime.py");
            //callPython("E://aut//RD//project//attentiveness-detection-master//AnalysisOfConcentration.py","E:/aut/RD/project/attentiveness-detection-master/2.jpg");
            //要安装额外的库
            //callPythonByFunction("E://aut//RD//project//attentiveness-detection-master//AnalysisOfConcentration.py","E:/aut/RD/project/attentiveness-detection-master/2.jpg");

            //暂时考虑直接调用python方法，或最好的就是使用微服务
            String res=callPython("E://aut//RD//project//attentiveness-detection-master//test1.py");
            System.out.println(res);
    }
}
