

public class NWAlignments {

    public int globalAlignment(char[] w, char[] v){
        int l1 = w.length, l2 = v.length;
        if(l1 == 0 || l2 == 0)
            return l1 + l2;
        int [][] distances = new int[l1 + 1][l2 + 1];
        int [][] bp = new int[l1 + 1][l2 + 1];
        for(int i = 0; i <= l1; i++){
            distances[i][0] = i;
            bp[i][0] = 2;
        }
        for(int j = 0; j <= l2; j++){
            distances[0][j] = j;
            bp[0][j] = 1;
        }

        for(int i = 1; i <= l1; i++){
            for (int j =1; j<= l2; j++){
                //i-1 at l1, i-2 at l2
                char c1 = w[i - 1];
                char c2 = v[j - 1];
                // if(distances[])
                int dis1  = Math.min(distances[i-1][j] + 1, distances[i][j-1] + 1);
                if(distances[i-1][j] < distances[i][j-1])
                    bp[i][j] = 2;
                else
                    bp[i][j] = 1;
                int dis2;
                if(c1 == c2)
                    dis2 = distances[i-1][j-1];
                else
                    dis2 = distances[i-1][j-1] + 1;

                distances[i][j] = Math.min(dis1,dis2);
                if(dis2 < dis1)
                    bp[i][j] = 0;

            }
        }
        int a = l1, b = l2;
//        StringBuilder wAligned = new StringBuilder();
//        StringBuilder vAligned = new StringBuilder();
//        while(a >0 || b > 0) {
//            // System.out.println("index " + a + " " + b+ " " +bp[a][b]);
//            if(bp[a][b] == 1){
//                b--;
//                vAligned.append(v.charAt(b));
//                wAligned.append('-');
//            }else if(bp[a][b] == 2){
//                a--;
//                wAligned.append(w.charAt(a));
//                vAligned.append('-');
//            }else if(bp[a][b] == 0){
//                a--;
//                b--;
//                wAligned.append(w.charAt(a));
//                vAligned.append(v.charAt(b));
//            }
//        }
//        System.out.println(wAligned.reverse().toString());
//        System.out.println(vAligned.reverse().toString());
        return distances[l1][l2];
    }
    public int globalAlignment(String w, String v){
        int l1 = w.length(), l2 = v.length();
        if(l1 == 0 || l2 == 0)
            return l1 + l2;
        int [][] distances = new int[l1 + 1][l2 + 1];
        int [][] bp = new int[l1 + 1][l2 + 1];
        for(int i = 0; i <= l1; i++){
            distances[i][0] = i;
            bp[i][0] = 2;
        }
        for(int j = 0; j <= l2; j++){
            distances[0][j] = j;
            bp[0][j] = 1;
        }

        for(int i = 1; i <= l1; i++){
            for (int j =1; j<= l2; j++){
                //i-1 at l1, i-2 at l2
                char c1 = w.charAt(i - 1);
                char c2 = v.charAt(j - 1);
               // if(distances[])
                int dis1  = Math.min(distances[i-1][j] + 1, distances[i][j-1] + 1);
                if(distances[i-1][j] < distances[i][j-1])
                    bp[i][j] = 2;
                else
                    bp[i][j] = 1;
                int dis2;
                if(c1 == c2)
                    dis2 = distances[i-1][j-1];
                else
                    dis2 = distances[i-1][j-1] + 1;

                distances[i][j] = Math.min(dis1,dis2);
                if(dis2 < dis1)
                    bp[i][j] = 0;

            }
        }
        System.out.print("   ");
        for(int i = 0;i<l2;i++){
            System.out.print(String.format("%3s",v.charAt(i)));
        }
        System.out.println("");
        for(int i = 1; i <= l1; i++) {
            System.out.print(String.format("%3s",w.charAt(i-1)));
            for (int j = 1; j <= l2; j++) {
                System.out.print(String.format("%3d",distances[i][j]));
            }
            System.out.println("");
        }
        int a = l1, b = l2;
        StringBuilder wAligned = new StringBuilder();
        StringBuilder vAligned = new StringBuilder();
        while(a >0 || b > 0) {
           // System.out.println("index " + a + " " + b+ " " +bp[a][b]);
            if(bp[a][b] == 1){
                b--;
                vAligned.append(v.charAt(b));
                wAligned.append('-');
            }else if(bp[a][b] == 2){
                a--;
                wAligned.append(w.charAt(a));
                vAligned.append('-');
            }else if(bp[a][b] == 0){
                a--;
                b--;
                wAligned.append(w.charAt(a));
                vAligned.append(v.charAt(b));
            }
        }
        System.out.println();
        System.out.println(wAligned.reverse().toString());
        System.out.println(vAligned.reverse().toString());
        return distances[l1][l2];
    }

    public int fittingAlignmentNaive(String w, String v){

        int min = Integer.MAX_VALUE;
        for(int i = 0; i<v.length();i++){
            int curr = globalAlignment(w,v.substring(i));
            if( curr< min){
                min = curr;
            }
        }
        return min;
    }

    public int fittingAlignmentDp(String w, String v){
        int min = Integer.MAX_VALUE;
        int l1 = w.length(), l2 = v.length();
        if(l1 == 0 || l2 == 0)
            return l1 + l2;
        int [][] distances = new int[l1 + 1][l2 + 1];

        for(int i = 0; i <= l1; i++){
            distances[i][0] = i;
        }
        for(int j = 0; j <= l2; j++){
            distances[0][j] = 0;
        }

        for(int i = 1; i <= l1; i++){
            for (int j =1; j<= l2; j++){
                //i-1 at l1, i-2 at l2
                char c1 = w.charAt(i - 1);
                char c2 = v.charAt(j - 1);
                int dis1  = Math.min(distances[i-1][j] + 1, distances[i][j-1] + 1);
                int dis2;
                if(c1 == c2)
                    dis2 = distances[i-1][j-1];
                else
                    dis2 = distances[i-1][j-1] + 1;

                distances[i][j] = Math.min(dis1,dis2);

            }
        }
        System.out.print("      ");
        for(int i = 0;i<l2;i++){
            System.out.print(String.format("%3s",v.charAt(i)));
        }
        System.out.println("");
        for(int i = 0; i <= l1; i++) {
            if(i == 0) System.out.print("   ");
            else
            System.out.print(String.format("%3s",w.charAt(i-1)));
            for (int j = 0; j <= l2; j++) {

                System.out.print(String.format("%3d",distances[i][j]));
            }
            System.out.println("");
        }

        for(int i = 0; i < l2;i++){
            if(distances[l1][i] < min)
                min = distances[l1][i];
        }
        return min;
    }

    public int localAlignment(String w, String v){
        System.out.println("aligning " +w +" "+v);
        int max = Integer.MIN_VALUE;
        int l1 = w.length(), l2 = v.length();
        if(l1 == 0 || l2 == 0)
            return l1 + l2;
        int [][] distances = new int[l1 + 1][l2 + 1];

        for(int i = 1; i <= l1; i++){
            for (int j =1; j<= l2; j++){
                //i-1 at l1, i-2 at l2
                char c1 = w.charAt(i - 1);
                char c2 = v.charAt(j - 1);
                int dis2;
                if(c1 == c2){
                    dis2 = distances[i-1][j-1]+1;
                }else{
                    dis2 = distances[i-1][j-1]-1;
                }

                int dis1  = Math.max(distances[i-1][j] - 1, distances[i][j-1] - 1);

                distances[i][j] = Math.max(dis1,dis2);

            }
        }
        System.out.print("      ");
        for(int i = 0;i<l2;i++){
            System.out.print(String.format("%3s",v.charAt(i)));
        }
        System.out.println("");
        for(int i = 0; i <= l1; i++) {
            if(i == 0) System.out.print("   ");
            else
                System.out.print(String.format("%3s",w.charAt(i-1)));
            for (int j = 0; j <= l2; j++) {

                System.out.print(String.format("%3d",distances[i][j]));
            }
            System.out.println("");
        }
        for(int i = 0;i<=l1;i++){
            for(int j = 0; j <= l2;j++){
                if(distances[i][j] > max)
                    max = distances[i][j];
            }
        }
        //System.out.println();
        return max;

    }

    public String LongestCommonSubsequence(String v, String w){
        int n1 = v.length(), n2 = w.length();
        String[][] lcs = new String[n1+1][n2+1];

        for(int i = 0; i<=n1;i++)
            lcs[i][0] ="";
        for(int i = 0; i<=n2;i++)
            lcs[0][i] ="";

        for(int i = 1; i<=n1;i++){
            for(int j = 1; j<=n2;j++){
             //   System.out.println(i + " " + j);
                String cur ="";
                if(v.charAt(i-1) == w.charAt(j-1)){
                   // System.out.println(v.charAt(i-1));
                    cur = lcs[i-1][j-1]+v.charAt(i-1);
                    //System.out.println(cur);
                }
                else{
                    cur = lcs[i-1][j];
                    if(lcs[i-1][j].length() < lcs[i][j-1].length())
                        cur = lcs[i][j-1];
                }

                lcs[i][j] = cur;
            }
        }

        return lcs[n1][n2];

    }

    public static void main(String[] arg){
        String str1 =  "CT", str2 = "GCAT";
        NWAlignments bw = new NWAlignments();
        long sta = System.currentTimeMillis();
        bw.globalAlignment(str1, str2);
        long end = System.currentTimeMillis();

        System.out.println("Running time(in milllis): "+ (end - sta));
    }
}
