import org.junit.Assert;
import org.junit.Test;

public class AlignmentsTest {
    @Test
    public void fittingAlignmentDpTest() throws Exception {
        NWAlignments al = new NWAlignments();
        String v = "TAGATA", w = "GTAGGCTTAAGGTTA";
        Assert.assertEquals(al.fittingAlignmentDp(v,w),3);
    }

    @Test
    public void fittingAlingmentNaiveTest() throws Exception {
        NWAlignments al = new NWAlignments();
        String v = "TAGATA", w = "GTAGGCTTAAGGTTA";
        Assert.assertEquals(al.fittingAlignmentNaive(v,w),3);
    }

    @Test
    public void globalAlignmentTest() throws Exception {
        NWAlignments al = new NWAlignments();
        String v = "TAGATA", w = "GTAGGCTTAAGGTTA";
        Assert.assertEquals(al.globalAlignment(v,w),9);
    }

    @Test
    public void localAlignmentTest() throws Exception {
        NWAlignments al = new NWAlignments();
        String v = "TAGATA", w = "GTAGGCTTAAGGTTA";
        Assert.assertEquals(al.localAlignment(v,w),3);
    }

    @Test
    public void lcsTest() throws Exception {
        NWAlignments al = new NWAlignments();
        String v = "ABCDGH", w = "AEDFHR";
        Assert.assertEquals(al.LongestCommonSubsequence(v,w),"ADH");
        Assert.assertEquals(al.LongestCommonSubsequence("AGGTAB","GXTXAYB"),"GTAB");
        Assert.assertEquals(al.LongestCommonSubsequence("ABGXINDGOEPQ","ZZXBFICNAGGO"),"BINGO");
    }
}
