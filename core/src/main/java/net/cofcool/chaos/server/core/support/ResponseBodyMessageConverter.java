package net.cofcool.chaos.server.core.support;

import java.io.IOException;
import java.lang.reflect.Type;
import net.cofcool.chaos.server.common.core.ExceptionCodeDescriptor;
import net.cofcool.chaos.server.common.core.ExceptionCodeManager;
import net.cofcool.chaos.server.common.core.Message;
import net.cofcool.chaos.server.common.core.Result;
import net.cofcool.chaos.server.common.core.Result.ResultState;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * 处理响应数据(JSON), {@link Result} 等
 *
 * @author CofCool
 */
public class ResponseBodyMessageConverter extends MappingJackson2HttpMessageConverter {

    private ExceptionCodeManager exceptionCodeManager;

    public ExceptionCodeManager getExceptionCodeManager() {
        return exceptionCodeManager;
    }

    public void setExceptionCodeManager(ExceptionCodeManager exceptionCodeManager) {
        this.exceptionCodeManager = exceptionCodeManager;
    }

    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage)
        throws IOException, HttpMessageNotWritableException {
        if (object instanceof Result) {
            object = handleResult((Result) object);
        } else if (object instanceof Number || object instanceof String){
            object = Message.of(
                exceptionCodeManager.getCode(ExceptionCodeDescriptor.SERVER_OK),
                exceptionCodeManager.getDescription(ExceptionCodeDescriptor.SERVER_OK_DESC),
                object
            );
        } else if (object instanceof Result.ResultState) {
            ResultState state = (ResultState) object;
            if (state == ResultState.SUCCESSFUL) {
                object = Message.of(
                    exceptionCodeManager.getCode(ExceptionCodeDescriptor.SERVER_OK),
                    exceptionCodeManager.getDescription(ExceptionCodeDescriptor.SERVER_OK_DESC),
                    null
                );
            } else {
                object = Message.of(
                    exceptionCodeManager.getCode(ExceptionCodeDescriptor.OPERATION_ERR),
                    exceptionCodeManager.getDescription(ExceptionCodeDescriptor.OPERATION_ERR_DESC),
                    null
                );
            }
        }

        super.writeInternal(object, type, outputMessage);
    }

    private Message handleResult(Result result) {
        return result.result();
    }

}
