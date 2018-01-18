import com.smart.parser.SmartFileUtils;
import com.smart.parser.interfaces.SmartTxtLineParser;
import org.junit.Test;

import java.io.IOException;

/**
 * @author: Smart
 * @date: 2018/01/18 17:35
 * @explain:
 */
public class T {

    @Test
    public void test() throws IOException {
        SmartFileUtils.loadTxtFile("D:\\smart_workspace\\smart-common-tools\\src\\main\\resources\\text.txt", new SmartTxtLineParser() {
            @Override
            public void parser(String line) {
                System.out.println(line);
            }
        });
    }
}
