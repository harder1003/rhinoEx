import org.mozilla.javascript.*;
import org.mozilla.javascript.serialize.ScriptableInputStream;
import org.mozilla.javascript.serialize.ScriptableOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.FileReader;
public class Main {
    public static void main(String[] args) {
        File file = new File("serialize.js");
        String str = readFileToString("serialized.shot");
        compile(file,str);
        exe(file);
        System.out.println("Hello world!");
    }

    public static void exe(File file) {
        try {

            Context ctx = Context.enter();

            Scriptable scope = ctx.initStandardObjects();

            ScriptableInputStream scriptableInputStream = new ScriptableInputStream(new FileInputStream(file), scope);
            InterpretedFunction script = (InterpretedFunction) scriptableInputStream.readObject();
            scriptableInputStream.close();
            InterpreterData idata = script.idata;
            System.out.println(Arrays.toString(data.itsICode));
            Object res = script.exec(ctx, scope);
            System.out.println(Context.toString(res));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void compile(File f,String str) {
        try {

            Context ctx = Context.enter();
            ctx.setOptimizationLevel(-1);
            ctx.setGeneratingSource(false);
            ctx.setLanguageVersion(Context.VERSION_ES6);
            Scriptable scope = ctx.initStandardObjects();
            String a = "var msg = 100;msg";
            Script compiled = ctx.compileString(str, "js", 1, null);
            ScriptableOutputStream scriptableOutputStream = new ScriptableOutputStream(new FileOutputStream(f), scope);
            scriptableOutputStream.writeObject(compiled);
            scriptableOutputStream.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readFileToString(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}