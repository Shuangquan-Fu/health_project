import com.aliyuncs.exceptions.ClientException;
import com.quan.utils.SMSUtils;

public class test {

    public static void main(String[] args) throws ClientException {
        SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,"17702709386","199812");
    }
}
