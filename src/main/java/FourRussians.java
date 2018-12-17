public class FourRussians {
    int blocksize_x,  blocksize_y;
    int [] lookup;

    public FourRussians(int blocksize_x, int blocksize_y) {
        this.blocksize_x = blocksize_x;
        this.blocksize_y = blocksize_y;

        lookup = getLookup(blocksize_x, blocksize_y);
    }
//
    public int align(char[] seq1, char[] seq2){
        int x =this.blocksize_x,y =this.blocksize_y;
        int m = seq1.length, n = seq2.length;
        int m_num = (int)Math.ceil((double)m/((double)x));
        int n_num = (int)Math.ceil((double)n/((double)y));
        int [][] dptable = new int[m][n];
        int[] prev_Bs = new int[x];
        int[] prev_Cs = new int[y];
        int[] Bs = new int[x];
        int[] Cs = new int[y];
        int[] Ys = new int[x];
        int[] Xs = new int[x];
        for(int i = 0; i < m_num; i++){
            for(int j = 0; j < n_num; j++) {
                Ys = new int[x];
                Xs = new int[x];
                for (int z = 0; z < x; z++) {
                    if (i * (x - 1) + z >= seq1.length)
                        break;
                    Xs[z] = seq1[i * (x - 1) + z];
                }
                for (int z = 0; z < x; z++) {
                    if (i * (x - 1) + z >= seq1.length)
                        break;
                    Ys[z] = seq2[i * (y - 1) + z];
                }
                Bs = prev_Bs;
                Cs = prev_Cs;

                int encode = getEncoding(Bs, Cs, Xs, Ys);
                int z = lookup[encode];
                int[][] BC = decode(z, x, y);
                prev_Bs = BC[0];
                prev_Cs = BC[0];
            }
        }
        return dptable[m-1][n-1];
    }


    public int[][] decode(int code, int x, int y){
        int mask = 1;
        int i =0;
        mask<<=(x+y-1)*2;
        int[] Bs = new int[x];
        int[] Cs = new int[y];

         while(i < x ){
            mask >>= 2;
            Bs[i] = mask&code;
            i++;
        }
        i =0;
        while(i < y){
            mask >>= 2;//
            Cs[i] = mask&code;
            i++;
        }
        int[][] ret = new int[2][];
        ret[0] = Bs;
        ret[1] =Cs;

        return ret;
    }
    public int getEncoding(int[] Ys, int[]Bs, int[] Xs, int[]Cs){
        int index = 0, i = 0;
        for (int c: Ys){
            index &= c << i*2;
            i++;
        }
        for (int c: Bs){
            index &= c << i*2;
            i++;
        }
        for (int c: Xs){
            index &= c << i*2;
            i++;
        }
        for (int c: Cs){
            index &= c << i*2;
            i++;
        }
        return index;
    }



    // Encode the structure using  Y B X C
    public int[] getLookup(int x, int y) {
        int[] lut = new int[(int) Math.pow(2, 4 * (x + y))];
        for (int i = 0; i < Math.pow(2, 4 * (x + y)); ++i) {
            //System.out.println(i);
            int[] Ys = new int[x];
            int[] Bs = new int[x];
            int[] Xs = new int[y];
            int[] Cs = new int[y];

            for (int j = 0; j < x; j++) {
                int unit = 11 << (((4 * y) + x * 2 + 2 * (x - j - 1))) & i;
                Ys[j] = unit;
            }
            for (int j = 0; j < x; j++) {
                int unit = 11 << ((4 * y) + 2 * (x - j - 1)) & i;
                Bs[j] = unit;
            }
            for (int j = 0; j < y; j++) {
                int unit = 11 << ((2 * y) + 2 * (y - j - 1)) & i;
                Xs[j] = unit;
            }
            for (int j = 0; j < y; j++) {
                int unit = 11 << (2 * (y - j - 1)) & i;
                Cs[j] = unit;
            }


            lut[i] = calculate(Ys, Bs, Xs, Cs);
        }

        return lut;

    }

    public int calculate(int[]Ys,int []Bs, int[]Xs, int[] Cs) {
        int n = Bs.length, m = Cs.length;

        int[][] dpTable = new int[Cs.length+1][Bs.length +1];
        dpTable[0][0] = 0;
        for(int i = 0; i < Bs.length; i++){
            dpTable[0][i+1] =dpTable[0][i] +Bs[i];
        }

        for(int i = 0; i < Cs.length; i++){
            dpTable[i+1][0] = dpTable[i][0] + Cs[i];
        }

        for(int i = 1; i < m; i++){
            for(int j = 1; j< n; j++){
                int score1 = Math.max(dpTable[i-1][j], dpTable[i][j-1]) -1;
                int score2;
                if(Ys[i-1] == Xs[j-1])
                    score2 = dpTable[i-1][j-1] +1;
                else
                    score2 = dpTable[i-1][j-1];
                dpTable[i][j] = Math.max(score1, score2);
            }
        }

        int retval = 0;
        for(int i = 1; i < Bs.length; i++){
            retval +=  (dpTable[m][i] - dpTable[m][i-1])<<(4*m + 4*(n - i));
        }
        for(int i = 1; i < Cs.length; i++){
            retval +=  (dpTable[i][n] - dpTable[i-1][n])<<( 4*(m - i));
        }

        return retval;
    }


    public static void main(String[] args){
        FourRussians a = new FourRussians(3,3);
        char[] input1 =  utils.str_rand(10000).toCharArray(), input2 = utils.str_rand(10000).toCharArray();
        for(int i = 1; i<= 4;i++) {
            long tic = System.currentTimeMillis();
            a.align(input1, input2);
            long tac = System.currentTimeMillis();
            System.out.println(String.format("blocksize %d x %d run in time %d",i,i,tac - tic));
        }
        
    }


}
