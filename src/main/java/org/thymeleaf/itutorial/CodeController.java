/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2014, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package org.thymeleaf.itutorial;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CodeController {
    private static final Logger LOG = LoggerFactory.getLogger(CodeController.class);
    private final ServletContext servletContext;

    @Autowired
    public CodeController(final ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @RequestMapping(value = "/resources/**", method = RequestMethod.GET)
    public void code(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        String servletPath = request.getServletPath();
        LOG.debug("Getting resource {}", servletPath);
        InputStream resourceStream = servletContext.getResourceAsStream(resourcePath(servletPath));
        response.setContentType(mimeType(servletPath));
        response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
        FileCopyUtils.copy(resourceStream, response.getOutputStream());
    }

    private String resourcePath(final String servletPath) {
        int firstSlash = servletPath.indexOf('/');
        int secondSlash = servletPath.indexOf('/', firstSlash + 1);
        String path = servletPath.substring(secondSlash + 1);
        return "/WEB-INF/" + path;
    }

    private String mimeType(final String servletPath) {
        if (servletPath.endsWith(".html")) {
            return MediaType.TEXT_HTML_VALUE;
        } else if (servletPath.endsWith(".java")) {
            return "text/x-java";
        } else {
            return MediaType.TEXT_PLAIN_VALUE;
        }
    }
}
