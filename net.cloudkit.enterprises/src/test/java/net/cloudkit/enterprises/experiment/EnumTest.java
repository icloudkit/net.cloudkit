package net.cloudkit.enterprises.experiment;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class EnumTest {

    public enum Color {
        RED(1, "红色"), GREEN(2, "绿色"), BLANK(3, "白色"), YELLO(4, "黄色");
        // 成员变量
        private String name;
        private int index;

        // 构造方法
        private Color(int index, String name) {
            this.index = index;
            this.name = name;
        }

        public static Color get(String value) {
            for (Color color : values()) {
                if (color.toString().equals(value)) {
                    return color;
                }
            }
            return null;
        }

        // 覆盖方法
        @Override
        public String toString() {
            return this.index + "_" + this.name;
        }
    }

    public static void main(String[] args) {
        System.out.println(Color.RED);
        System.out.println(Color.get("1_红色"));

        Home home = new Home();
        home.setColor(Color.BLANK);
        System.out.println(home.getColor());

//        try {
//            File file = new File("/Users/mango/Downloads/13d7ad63-341c-4317-ad98-4e2112e20341.xls");
//            InputStream input = new FileInputStream(file);
//            Workbook wb = CommonUtility.createWorkbook(input);
//            Sheet sheet = wb.getSheetAt(0);
//            inputImport(sheet);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        System.out.println("da86ca16-76a2-44f8-b4ca-d07adf571af5,fsdfsafasfasfasfa".split(",")[1]);

        int i = 0;
        System.err.println(++i);
    }

}

class Home {

    @Enumerated(EnumType.STRING)
    private EnumTest.Color color;

    public EnumTest.Color getColor() {
        return color;
    }

    public void setColor(EnumTest.Color color) {
        this.color = color;
    }
}
