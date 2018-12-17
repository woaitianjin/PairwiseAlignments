import java.util.ArrayList;

import java.util.List;
import java.util.Random;
import  java.io.*;
import java.util.TreeMap;

public class utils {
    static public String str_repeat(String str, int num){
        return new String(new char[num]).replace("\0", str);
    }
    static public String str_rand(int num){
        char[] units = {'A', 'T','C','G'};
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i <num; i++){
            sb.append(units[rand.nextInt(4)]);
        }
        return sb.toString();
    }

    static public List<String> seqFromFile(String path) throws IOException{
        List<String> ret = new ArrayList<String>();
        File file = new File(path);

        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null) {
            if(st.startsWith("|"));
            ret.add(st);
        }
        return ret;
    }

    static public boolean seqToFile(String path, List<String> seqs) throws IOException {
        List<String> ret = new ArrayList<String>();
        File file = new File(path);

        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;

        return true;
    }

}
