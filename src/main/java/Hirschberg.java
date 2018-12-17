public class Hirschberg {

    public  void Hirschberg(String input1, String input2){
        int len1 = input1.length(), len2 = input2.length();
        int[] istar = new int [len2+1];
        hirschbergHelper(input1.toCharArray(), input2.toCharArray(),0,0,len1,len2,istar);
        //hirschbergHelper(input1.toCharArray(), input2.toCharArray(),1,3,1,6,istar);
        linearAlignment(input1.toCharArray(),input2.toCharArray(),0,1,0,2);
     //   System.out.println(Arrays.toString(istar));
         printIStar(input1.toCharArray(),input2.toCharArray(),istar);

    }

    // align input1 input2(i1, i2, j1, j2)

    private void hirschbergHelper(char[] input1, char[] input2, int s1, int s2, int e1, int e2, int[] istar){
       // System.out.println("hirschberg Align:" + s1 + "  " + s2 + "  " + e1 +"  "+ e2);
        if(e2 - s2 > 1) {
            if (e1 - s1 == 0){
                for (int i = s2; i < e2; i++)
                    istar[i] = e1;
                return;
            }

            int mid = s2  + (e2 - s2+1)/2;
            int[][] forward =  linearAlignment(input1, input2, s1, s2,e1,mid);
            int[][] backward = linearAlignmentBackword(input1, input2, s1, mid, e1, e2);

            int[] fv = forward[0];
            int [] bv = backward[0];


//            System.out.println("fv:"+Arrays.toString(fv));
//            System.out.println("bv:"+Arrays.toString(bv));

            //System.out.println("fvlen:" + fv.length);
            //System.out.println("bvlen:"+bv.length);
            int [] wt = new int[Math.min(fv.length, bv.length)];

            int max = Integer.MIN_VALUE;
            int k = 0;
            for(int i = wt.length - 1;  i>=0; i--){
                wt[i] = fv[i] +bv[i];
                if(wt[i]>=max){
                    max = wt[i];
                    k = i;
                }
            }
            k += s1;
 //           System.out.println("report: " + k + " mid: " + mid);
            istar[mid] = k;

            hirschbergHelper(input1, input2, s1, s2, k, mid,istar);
            hirschbergHelper(input1, input2, k,mid ,e1,e2,istar);

        } else if(e2 - s2 == 1) {
            if (e1 - s1 == 0){
                for (int i = s2; i < e2; i++)
                    istar[i] = e1;
                return;
            }

            int mid = s2  + (e2 - s2+1)/2;
            int[][] forward =  linearAlignment(input1, input2, s1, s2,e1,mid);
            int[][] backward = linearAlignmentBackword(input1, input2, s1, mid, e1, e2);

            int[] fv = forward[0];
            int [] bv = backward[0];


//            System.out.println("fv:"+Arrays.toString(fv));
//            System.out.println("bv:"+Arrays.toString(bv));

            //System.out.println("fvlen:" + fv.length);
            //System.out.println("bvlen:"+bv.length);
            int [] wt = new int[Math.min(fv.length, bv.length)];

            int max = Integer.MIN_VALUE;
            int k = 0;
            for(int i = wt.length - 1;  i>=0; i--){
                wt[i] = fv[i] +bv[i];
                if(wt[i]>=max){
                    max = wt[i];
                    k = i;
                }
            }
            k += s1;
 //           System.out.println("report: " + k + " mid: " + mid);
            istar[mid] = k;

        } else if(e1 - s1 == 0){
//                System.out.println("final");
            }

           // System.out.println("report: " + k + "mid:" + mid);


    }
    //input1 : ATCG s1=0 - s1=A s2=T
    private int[][] linearAlignment(char[] input1, char[] input2, int s1, int s2, int e1, int e2){
 //       System.out.println("Foward align:" + s1 + "  " + s2 + "  " + e1 +"  "+ e2);
        // m * n matrix
        //int len1 = input1.length, len2 = input2.length, n = len1 > len2?len1:len2;
        int m;
        if(s1 != 0)
            m = e1 - s1 + 2;
        else
            m = e1 - s1 + 1;

        int[] prevScore = new int[m];
        int[] currScore = new int[m];
        int[] backtrack = new int[m]; // 0 for (i-1, j-1) -> (i, j); 1 for (i-1,j) -> (i,j) ; 2 for (i, j-1) -> (i,j)
        for (int j = 0; j < m; j++) {
            prevScore[j] = -j;
        }
 //        System.out.println(Arrays.toString(prevScore));

        for (int j = s2; j < e2; j++) {
            for (int i = 0; i < m; i++) {
                // i - (s1-1) get index in array
                if (i == 0) {
                    currScore[i] = prevScore[i] - 1;
                    backtrack[i] = 1;
                } else {
                    int score1;
                    int bp;
                    if (currScore[i - 1] > prevScore[i]) {
                        score1 = currScore[i - 1] - 1;
                        bp = 2;
                    } else {
                        score1 = prevScore[i] - 1;
                        bp = 1;
                    }

                    int score2;
                    //System.out.println(i+" "+j);
                    if(s1!=0) {
                        if (input1[i + s1 - 2] == input2[j]) {
                            score2 = prevScore[i] + 1;
                        } else {
                            score2 = prevScore[i-1] - 1;
                        }
                        if (score2 > score1)
                            bp = 0;
                    }else{

                        if (input1[i + s1 - 1] == input2[j]) {
                            score2 = prevScore[i - 1] + 1;
                        } else {
                            score2 = prevScore[i - 1] - 1;
                        }
                        if (score2 > score1)
                            bp = 0;
                    }
                    currScore[i] = Math.max(score1, score2);
                    backtrack[i] = bp;
                }
            }
            prevScore = currScore.clone();

        }

        int[][] ret = new int[2][];
  //      System.out.println("fwreult iter: "+Arrays.toString(prevScore));

       // remove 0 for s1 != 0
        ret[0] = prevScore;
        ret[1] = backtrack;

        return ret;
    }


    private  int[][] linearAlignmentBackword(char[] input1, char[] input2, int s1, int s2, int e1, int e2){
//        System.out.println("Backward align:" + s1 + "  " + s1 + "  " + e1 +"  "+ e2);
        int m =  e1 - s1 + 1;

        int[] prevScore = new int[m];
        int[] currScore = new int[m];
        int[] backtrack = new int[m]; // 0 for (i-1, j-1) -> (i, j); 1 for (i-1,j) -> (i,j) ; 2 for (i, j-1) -> (i,j)
        for(int j = m-2; j >= 0; j--){
            prevScore[j] = prevScore[j+1] -1;
        }
        //System.out.println(Arrays.toString(prevScore));
        for(int j = e2-1; j >= s2; j--) {
            for (int i = m-1  ; i >= 0; i--) {
                // i - (s1-1) get index in array
                if (i == m-1) {
                    currScore[m-1] = prevScore[m-1] - 1;
                    backtrack[m-1] = 1;
                } else {
                    int score1;
                    int bp;
                    //  Sysem.out.println(currScore[i - s1 + 2]+ " " + prevScore[i - s1 + 1]);
                    if (currScore[i+1]> prevScore[i]) {
                        score1 = currScore[i+1] - 1;
                        bp = 2;
                    } else {
                        score1 = prevScore[i] - 1;
                        bp = 1;
                    }

                   // System.out.println(s1);
                    //System.out.println(j+;

                    if(s1 == 0){

                        //System.out.println(input1[s1 + i-1] + " "+input1[j-1] )
                        //System.out.println(input1[s1 + i]);
                            int score2;
                            if (input1[s1 + i] == input2[j ]) {
                                score2 = prevScore[i + 1] + 1;
                               /// System.out.println("find Same");
                            } else {
                                score2 = prevScore[i + 1] - 1;
                            }
                            if (score2 > score1)
                                bp = 0;
                            currScore[i] = Math.max(score1, score2);

                    }
                    else {
                        //System.out.println(input1[s1 + i-1] + " "+input1[j-1] );
                        if(s1 + i>= 0) {
                            int score2;
                            if (input1[s1 ] == input2[j ]) {
                                score2 = prevScore[i + 1] + 1;
  //                              System.out.println("find Same");
                            } else {
                                score2 = prevScore[i + 1] - 1;
                            }
                            if (score2 > score1)
                                bp = 0;
                            currScore[i] = Math.max(score1, score2);

                        } else{
                            currScore[i] = score1;
                        }

                    }
                   // System.out.println(Arrays.toString(currScore));
                    backtrack[i] = bp;
                }
            }
           // System.out.println();
            prevScore = currScore.clone();
           // System.out.println(Arrays.toString(prevScore));
           // System.out.println(Arrays.toString(backtrack));
        }

        int[][] ret = new int[2][];
        ret[0] = prevScore;
 //       System.out.println("bwreult iter: "+Arrays.toString(prevScore));
        ret[1] = backtrack;
        return ret;

    }

    public void printIStar(char[] input1, char[] input2, int[] istat){
        System.out.print("   -  ");
        for(int i = 0; i<input2.length; i++){
            System.out.print(input2[i] + "  ");

        }
        System.out.println();
        System.out.print("-");
        int j =0;
        while( j < istat.length && istat[j] == 0){
            System.out.print("  X");
            j++;
        }
        System.out.println();
        for(int i = 0; i<input1.length; i++){
            System.out.print(input1[i] + "  ");
            if(j< istat.length && i +1 == istat[j]) {
                System.out.print(new String(new char[Math.max((j), 0)]).replace("\0", "   ") + "X");
                j++;
                while (j< istat.length && i+1 == istat[j] ){
                    System.out.print("  X");
                    j++;
                }
            }
            System.out.println();
        }
        System.out.println();


    }


    public static void main(String[] a){
        Hirschberg ali = new Hirschberg();
        String str1 =  "CT", str2 = "GCAT";
        long sta = System.currentTimeMillis();
        ali.Hirschberg(str1, str2);
        long end  =System.currentTimeMillis();
        System.out.println("Running time(in milllis): "+ (end - sta));

    }
}
