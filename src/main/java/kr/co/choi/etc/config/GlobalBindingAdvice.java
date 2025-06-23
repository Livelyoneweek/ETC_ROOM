package kr.co.choi.etc.config;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * 모든 컨트롤러에 적용되는 전역 바인더 (body 데이터는 미적용)
 * - nullAsEmpty = true  ➜  "" → null
 * - nullAsEmpty = false ➜  "  " → ""
 */
@ControllerAdvice
public class GlobalBindingAdvice {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
