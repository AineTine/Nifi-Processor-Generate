package cn.tineaine.main;

import cn.tineaine.tools.FileSystemTools;

import java.net.URL;

/**
 * @author Tine Aine
 * @version 1.0.0
 * @date 2021年2月8日10:34:46
 */
public class Generate {

    //-----------------------------------------------
    // 请在生成前修改此处的内容
    //-----------------------------------------------

    // 项目名称（填写你的项目名）
    static String projectName = "Hello";
    // 最终包名（nifi processor最终包名称，用于描述功能类，当然，也可以随意）
    static String ProcessorName = "network";
    // 项目所在位置（在哪里创建就写哪里）
    static String projectPath = "D:/qiq";
    // 组ID，一般写公司的域名即可（一般就是一个域名，可以随意，但不能有奇怪的字符）
    static String groupId = "cn.tineaine";
    // Processor源文件名（同时也是类名，实际上就是一个简单的Processor文件）
    static String processorFileName = "MyProcessor";


    //-----------------------------------------------
    // 这部分的内容一般情况下请不要修改
    //-----------------------------------------------

    // 处理器所在包
    static String processorPath = groupId + ".processor." + ProcessorName;

    // 最终生成包全路径
    static String processorFullPath = processorPath + "." + processorFileName;
    // nifi processor module
    static String processorModule = "nifi-" + ProcessorName + "-processors";
    // nifi nar module
    static String narModule = "nifi-" + ProcessorName + "-nar";
    // 工件ID，一般是项目名（和projectName一致，不建议修改）
    static String artifactId = projectName;
    // 版本号（！目前暂不提供修改）
    static String version = "1.0-SNAPSHOT";

    //-----------------------------------------------
    // 主函数
    // 配置完成后在此执行即可
    //-----------------------------------------------
    public static void main(String[] args) {
        // 打印一些消息
        System.out.println("The Tine Aine Nifi Processor Project Automatic generate v1.0.0");
        System.out.println(" _____ _                _   _            \n" +
                "/__   (_)_ __   ___    /_\\ (_)_ __   ___ \n" +
                "  / /\\/ | '_ \\ / _ \\  //_\\\\| | '_ \\ / _ \\\n" +
                " / /  | | | | |  __/ /  _  \\ | | | |  __/\n" +
                " \\/   |_|_| |_|\\___| \\_/ \\_/_|_| |_|\\___|");
        System.out.println("Date:2021-02-08 13:49:58");
        // 实例化文件工具
        FileSystemTools file = new FileSystemTools();
        System.out.println("-------------------------------------------------------");
        System.out.println("Step 1：Create Folder");
        System.out.println("-------------------------------------------------------");
        // 创建项目根目录
        System.out.println("System Initialization（Project root dir）——> " + file.mkdir(projectPath + "//" + projectName));
        // 创建Nar根目录
        System.out.println("System Initialization（Nar root dir）——> " + file.mkdir(projectPath + "//" + projectName + "//" + narModule));
        // 创建处理器根目录
        System.out.println("System Initialization（Processor root dir）——> " + file.mkdir(projectPath + "//" + projectName + "//" + processorModule));
        // 创建src目录
        System.out.println("System Initialization（Processor src dir）——> " + file.mkdir(projectPath + "//" + projectName + "//" + processorModule + "//src//main"));
        // 创建main目录
        System.out.println("System Initialization（Processor main java dir）——> " + file.mkdir(projectPath + "//" + projectName + "//" + processorModule + "//src//main//java"));
        // 切割包路径
        String[] packages = processorPath.split("\\.");
        // 循环创建包目录
        StringBuilder lastPath = new StringBuilder(projectPath + "//" + projectName + "//" + processorModule + "//src//main//java//");
        for (String path : packages) {
            System.out.println("System Initialization（Processor main package dir）——> " + file.mkdir(lastPath + "//" + path));
            lastPath.append("//").append(path);
        }
        // 创建resource目录
        System.out.println("System Initialization（Processor main resource dir）——> " + file.mkdir(projectPath + "//" + projectName + "//" + processorModule + "//src//main//resources//META-INF//services"));
        System.out.println("-------------------------------------------------------");
        System.out.println("Step 2：Copy File");
        System.out.println("-------------------------------------------------------");
        // 获取本地路径并断言不为空
        // 系统配置
        URL configure = Generate.class.getClassLoader().getResource("default-configure.txt");
        assert configure != null;
        // nar pom
        URL narPom = Generate.class.getClassLoader().getResource("nar-pom.txt");
        assert narPom != null;
        // Processor pom
        URL processorPom = Generate.class.getClassLoader().getResource("processors-pom.txt");
        assert processorPom != null;
        // project pom
        URL projectPom = Generate.class.getClassLoader().getResource("project-pom.txt");
        assert projectPom != null;
        // source file
        URL processorCode = Generate.class.getClassLoader().getResource("default-processor.txt");
        assert processorCode != null;
        // 复制java源文件
        System.out.println("System Initialization（copy java source）——> " + file.cp(processorCode.getPath(), lastPath + "//" + processorFileName + ".java"));
        // 复制项目配置文件
        System.out.println("System Initialization（copy project configuration）——> " + file.cp(configure.getPath(), projectPath + "//" + projectName + "//" + processorModule + "//src//main//resources//META-INF//services" + "//" + "org.apache.nifi.processor.Processor"));
        // 复制项目pom
        System.out.println("System Initialization（copy project pom）——> " + file.cp(projectPom.getPath(), projectPath + "//" + projectName + "//pom.xml"));
        // 复制nar pom
        System.out.println("System Initialization（copy nar pom）——> " + file.cp(narPom.getPath(), projectPath + "//" + projectName + "//" + narModule + "//pom.xml"));
        // 复制Processor pom
        System.out.println("System Initialization（copy processor pom）——> " + file.cp(processorPom.getPath(), projectPath + "//" + projectName + "//" + processorModule + "//pom.xml"));
        System.out.println("-------------------------------------------------------");
        System.out.println("Step 3：Edit File");
        System.out.println("-------------------------------------------------------");
        // 修改源文件
        System.out.println("System Initialization（edit java source）——> " + file.replace(lastPath + "//" + processorFileName + ".java", "${processorPath}", processorPath));
        System.out.println("System Initialization（edit java source）——> " + file.replace(lastPath + "//" + processorFileName + ".java", "${processorFileName}", processorFileName));
        // 修改项目配置文件
        System.out.println("System Initialization（edit project configuration）——> " + file.replace(projectPath + "//" + projectName + "//" + processorModule + "//src//main//resources//META-INF//services" + "//" + "org.apache.nifi.processor.Processor", "${processorFullPath}", processorFullPath));
        // 修改项目pom
        System.out.println("System Initialization（edit project pom）——> " + file.replace(projectPath + "//" + projectName + "//pom.xml", "${groupId}", groupId));
        System.out.println("System Initialization（edit project pom）——> " + file.replace(projectPath + "//" + projectName + "//pom.xml", "${projectName}", projectName));
        System.out.println("System Initialization（edit project pom）——> " + file.replace(projectPath + "//" + projectName + "//pom.xml", "${processorModule}", processorModule));
        System.out.println("System Initialization（edit project pom）——> " + file.replace(projectPath + "//" + projectName + "//pom.xml", "${narModule}", narModule));
        // 修改nar pom
        System.out.println("System Initialization（edit nar pom）——> " + file.replace(projectPath + "//" + projectName + "//" + narModule + "//pom.xml", "${groupId}", groupId));
        System.out.println("System Initialization（edit nar pom）——> " + file.replace(projectPath + "//" + projectName + "//" + narModule + "//pom.xml", "${projectName}", projectName));
        System.out.println("System Initialization（edit nar pom）——> " + file.replace(projectPath + "//" + projectName + "//" + narModule + "//pom.xml", "${narModule}", narModule));
        System.out.println("System Initialization（edit nar pom）——> " + file.replace(projectPath + "//" + projectName + "//" + narModule + "//pom.xml", "${processorModule}", processorModule));
        // 修改processor pom
        System.out.println("System Initialization（edit processor pom）——> " + file.replace(projectPath + "//" + projectName + "//" + processorModule + "//pom.xml", "${groupId}", groupId));
        System.out.println("System Initialization（edit processor pom）——> " + file.replace(projectPath + "//" + projectName + "//" + processorModule + "//pom.xml", "${projectName}", projectName));
        System.out.println("System Initialization（edit processor pom）——> " + file.replace(projectPath + "//" + projectName + "//" + processorModule + "//pom.xml", "${processorModule}", processorModule));
        System.out.println("-------------------------------------------------------");
        System.out.println(" _______ _                _______ _             \n" +
                "(_______|_)              (_______|_)            \n" +
                "    _    _ ____  _____    _______ _ ____  _____ \n" +
                "   | |  | |  _ \\| ___ |  |  ___  | |  _ \\| ___ |\n" +
                "   | |  | | | | | ____|  | |   | | | | | | ____|\n" +
                "   |_|  |_|_| |_|_____)  |_|   |_|_|_| |_|_____)");
        System.out.println("Generate Successful");
        System.out.println("你可以直接通过Maven方式加载这个项目，或者对项目进行其他的修改");
        System.out.println("请预先准备需要的依赖库，否则无法对项目进行编译！");
        System.out.println("Maven命令行：clean install -T12 -f pom.xml");
        System.out.println("Maven配置：跳过测试，并使用JDK1.8（最好使用1.8）");
        System.out.println("Project Name: " + projectName);
        System.out.println("Project Path: " + projectPath + "//" + projectName);
        System.out.println("-------------------------------------------------------");
    }
}
