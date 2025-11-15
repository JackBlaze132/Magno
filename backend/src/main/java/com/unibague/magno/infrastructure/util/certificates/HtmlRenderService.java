package com.unibague.magno.infrastructure.util.certificates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;
import org.thymeleaf.context.Context;


@Service
public class HtmlRenderService {

    @Autowired
    private SpringTemplateEngine templateEngine;

    public String renderCertificado(Map<String, Object> data) {
        Context context = new Context();
        context.setVariables(data);
        return templateEngine.process("certificado", context);
    }
}

