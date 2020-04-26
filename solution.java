import java.util.Map;
import java.util.HashMap;
public class solution {
    public static int[][] middle(int[][] points) {
        // 用一个hashmap，key装中点的min值，value装这个中点的
        // 两个parent
        Map<Double, int[]> map = new HashMap<>();
        double max = Double.NEGATIVE_INFINITY;
        for(int i = 0; i < points.length - 1; i++) {
            for(int j = i + 1; j < points.length; j++) {
                double[] point = calcMiddlePoint(points[i], points[j]);
                double min = getMin(point);
                if(min >= max)
                    max = min;
                map.put(min, new int[]{i, j});
            }
        }
        int[][] result = new int[2][2];
        result[0] = points[map.get(max)[0]];
        result[1] = points[map.get(max)[1]];
        return result;
    }


    private static double[] calcMiddlePoint(int[] p1, int[] p2) {
        double[] result = new double[2];
        result[0] = (double)(p1[0] + p2[0]) / 2;
        result[1] = (double)(p1[1] + p2[1]) / 2;
        return result;
    }

    private static double getMin(double[] p) {
        if(p[0] > p[1]) {
            return p[1];
        } else {
            return p[0];
        }
    }

    public static void main(String args[]) {
        int[][] points = {
                {1,2},
                {2,4},
                {3,4},
                {5,5},
                {8,7}
        };
        int[][] result = middle(points);
        for(int i = 0; i < result.length; i++) {
            System.out.print(result[i][0] + " ");
            System.out.println(result[i][1]);
        }
    }
}
