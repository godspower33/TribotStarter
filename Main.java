import obf.Yi;
import obf.xl;
import obf.zg;
 
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
 
/**
 * Created by Spencer on 9/8/2016.
 */
public class Main {
 
    public static void main(String... sargs) {
        HashMap<String, String> parsedArguments = new HashMap<>();
        for (String arg : sargs) {
            String[] split = arg.split("~");
            if (split.length == 2)
                parsedArguments.put(split[0], split[1]);
        }
 
        String accountSession = "";
        String heapSize = "";
 
        //ugly af but yolo
        try {
            List<String> lines = Files.readAllLines(Paths.get(Main.getAppDataDirectory().toString() + "/settings/startup.ini"));
            for (String line : lines) {
                if (line.contains("<mem>")) {
                    String[] splits = line.split("<mem>");
                    String[] splits2 = splits[1].split("</mem");
                    heapSize = splits2[0];
                }
                if (line.contains("<sid>")) {
                    String[] splits = line.split("<sid>");
                    String[] splits2 = splits[1].split("</sid");
                    accountSession = "SID" + splits2[0];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
 
 
        //Messy but idc
        String accountName = getArgument(parsedArguments, "accountName");
        String scriptName = "dChopper"; //getArgument(parsedArguments, "scriptName").replace("_", " ");
        String scriptCommand = getArgument(parsedArguments, "scriptCommand");
        int world = 394; //Integer.parseInt(getArgument(parsedArguments, "world").equals("") ? "0" : getArgument(parsedArguments, "world"));
        String breakProfile = getArgument(parsedArguments, "breakProfile");
        String proxyIP = getArgument(parsedArguments, "proxyIP");
        String proxyPort = getArgument(parsedArguments, "proxyPort");
        String proxyUsername = getArgument(parsedArguments, "proxyUsername");
        String proxyPassword = getArgument(parsedArguments, "proxyPassword");
 
 
        ArrayList<String> args = new ArrayList<>();
        String os = System.getProperty("os.name").toLowerCase();
        String sep = os.contains("win") ? ";" : ":";
        String dir = os.contains("win") ? "\\" : "/";
        String q = os.contains("win") ? "\"" : "";
        String TRIBOT_PATH = getAppDataDirectory().toString().replace("\\", dir);
 
        args.add("java");
        args.add("-Xmx384m -Xss2m -Dsun.java2d.noddraw=true -XX:CompileThreshold=1500 -Xincgc");
        args.add("-XX:+UseConcMarkSweepGC -XX:+UseParNewGC -Xbootclasspath/p:" + q + TRIBOT_PATH + dir + "dependancies" + dir + "xboot860.jar" + q);
        args.add("-classpath "
                + q + Main.getJavaBinDirectory().getParent().replace("\\", dir) + dir + "lib" + dir + "tools.jar" + q + sep
                + q + TRIBOT_PATH + dir + "bin" + q + sep
                + q + TRIBOT_PATH + dir + "dependancies" + dir + "substance.jar" + q + sep
                + q + TRIBOT_PATH + dir + "dependancies" + dir + "trident.jar" + q + sep
                + q + TRIBOT_PATH + dir + "dependancies" + dir + "TRiBot.jar" + q + sep);
        args.add("org.tribot.TRiBot");
        args.add(accountSession);
        args.add("MEM" + heapSize);
        Yi clientArgs = new Yi(accountName, scriptName, scriptCommand, world, breakProfile, 1472396136); //Magic number best number
        xl var6 = new xl(new ArrayList<>(), null);
        var6.e.add(clientArgs);
 
        int crand = (int) (Math.random() * 10000000); //Random file for the client to load the arguments from
        final String bootname = "/client" + crand + ".boot";
 
        if (proxyIP != null && !proxyIP.equals(""))
            args.add("PROXY_HOST" + zg.yL(proxyIP.getBytes()));
        if (proxyPort != null && !proxyPort.equals(""))
            args.add("PROXY_PORT" + zg.yL(proxyPort.getBytes()));
        if (proxyUsername != null && !proxyUsername.equals(""))
            args.add("PROXY_USERNAME" + zg.yL(proxyUsername.getBytes()));
        if (proxyPassword != null && !proxyPassword.equals(""))
            args.add("PROXY_PASSWORD" + zg.yL(proxyPassword.getBytes()));
 
        args.add("BOOT_INFO" + zg.yL(bootname.getBytes()));
        args.add("OLDSCHOOL");
 
 
        String exe = "";
        for (String sarg : args) {
            exe += sarg + " ";
        }
 
        Path path = Paths.get(TRIBOT_PATH + bootname);
        try {
            Path tfile = Files.createFile(path);
            if (!tfile.toFile().exists())
                throw new Error("Clientboot file does not exist.");
            else
                System.out.println("Created boot: " + path);
 
            FileOutputStream fos = new FileOutputStream(tfile.toFile());
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos);
 
            oos.writeObject(var6);
            oos.close();
            bos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
 
        try {
            Runtime.getRuntime().exec(exe);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    public static String getArgument(HashMap<String, String> set, String key) {
        return set.containsKey(key) ? set.get(key) : "";
    }
 
    public static java.io.File getJavaBinDirectory() {
        java.lang.String[] a;
        java.lang.String a2;
        java.lang.String a3 = java.lang.System.getProperty("os.name").toLowerCase();
        java.lang.String a4 = java.lang.System.getenv("JAVA_HOME");
        if (a4 != null && a4.length() > 0) {
            return new java.io.File(a4, "bin");
        }
        if (!a3.contains("win")) {
            a3.contains("mac");
            return null;
        }
        if (bc("HKEY_LOCAL_MACHINE\\SOFTWARE\\JavaSoft\\Java Development Kit") != null && (a2=iB("HKEY_LOCAL_MACHINE\\SOFTWARE\\JavaSoft\\Java Development Kit", "CurrentVersion")) != null && (a2 = iB(("HKEY_LOCAL_MACHINE\\SOFTWARE\\JavaSoft\\Java Development Kit\\" + a2), "JavaHome")) != null) {
            return new java.io.File(a2, "bin");
        }
        a = bc("HKEY_LOCAL_MACHINE\\SOFTWARE\\Wow6432Node\\JavaSoft\\Java Runtime Environment");
        if (a == null) return null;
        a2 = iB("HKEY_LOCAL_MACHINE\\SOFTWARE\\Wow6432Node\\JavaSoft\\Java Runtime Environment", "CurrentVersion");
        if (a2 == null) return null;
        java.lang.String a5 = iB(("HKEY_LOCAL_MACHINE\\SOFTWARE\\Wow6432Node\\JavaSoft\\Java Runtime Environment\\" + a2), "JavaHome");
        if (a5 == null) return null;
        return new java.io.File(a5, "bin");
    }
 
    private static String[] bc(String a) {
        try {
            Process var1 = Runtime.getRuntime().exec("reg query \"" + a + "\"");
            StreamReader var2 = new StreamReader(var1.getInputStream());
            var2.start();
            var1.waitFor();
            var2.join();
            String var8 = var2.getResult();
            if(var8.length() < 1) {
                return null;
            } else {
                String[] var9 = var8.split(a.replace("\\", "\\\\") + "\\\\");
                ArrayList var3 = new ArrayList();
                String[] var6 = var9;
                int var5 = var9.length;
 
                int var4;
                for(int var10000 = var4 = 0; var10000 < var5; var10000 = var4) {
                    if(!(var8 = var6[var4]).contains(a)) {
                        var3.add(var8);
                    }
 
                    ++var4;
                }
 
                String[] var10001 = new String[var3.size()];
                return var10001;
            }
        } catch (Exception var7) {
            var7.printStackTrace();
            return null;
        }
    }
 
 
    private static String iB(String a, String a1) {
        try {
            Process a2 = Runtime.getRuntime().exec("reg query \"" + a + "\" /v " + a1);
            StreamReader var2 = new StreamReader(a2.getInputStream());
            var2.start();
            a2.waitFor();
            var2.join();
            String var5 = var2.getResult();
            if(var5.length() < 1) {
                return null;
            } else {
                var5 = var5.substring(var5.indexOf("REG_SZ") + "REG_SZ".length() + 1, var5.length()).trim();
                return var5;
            }
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }
 
 
    public static java.io.File getAppDataDirectory() {
        java.io.File file;
        java.io.File file2 = null;
        java.io.File a = null;
        java.lang.String a2 = java.lang.System.getProperty("user.home");
        java.lang.String a3 = java.lang.System.getProperty("os.name").toLowerCase();
        if (a3.contains("win")) {
            java.lang.String a4 = java.lang.System.getenv("APPDATA");
            file2 = a4 == null || a4.length() < 1 ? (a = new java.io.File(a2, ".tribot" + java.io.File.separatorChar)) : (a = new java.io.File(a4, ".tribot" + java.io.File.separatorChar));
        } else if (a3.contains("solaris") || a3.contains("linux") || a3.contains("sunos") || a3.contains("unix")) {
            file2 = a = new java.io.File(a2, ".tribot" + java.io.File.separatorChar);
        } else if (a3.contains("mac")) {
            file = new java.io.File(a2, "Library" + java.io.File.separatorChar + "Application Support" + java.io.File.separatorChar + "tribot");
            java.io.File file3 = a = file;
        } else {
            file = new java.io.File(a2, "tribot" + java.io.File.separatorChar);
            java.io.File file4 = a = file;
        }
        if (file2 != null) {
            if (a.exists()) return a;
            if (a.mkdirs()) return a;
        }
        a = new java.io.File("data");
        java.lang.System.out.println("Couldn't create seperate application data directory. Using application data directory as: " + a.getAbsolutePath());
        return a;
    }
}
