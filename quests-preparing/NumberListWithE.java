public class NumberArrayWithE {
    public static void main(String[] args) {
        StringBuilder result = new StringBuilder();

        for (int i = 1; i <= 200; i++) {
            result.append(i).append("-E");
            if (i < 200) {
                result.append(", ");
            }
        }

        System.out.println(result.toString());
    }
}
