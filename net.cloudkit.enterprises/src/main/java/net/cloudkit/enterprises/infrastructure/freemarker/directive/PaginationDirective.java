/*
* Copyright (C) 2015 The CloudKit Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package net.cloudkit.enterprises.infrastructure.freemarker.directive;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import net.cloudkit.enterprises.infrastructure.utilities.FreemarkerHelper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PaginationDirective.java
 *
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2014年1月8日 下午2:43:10
 */
@Component("paginationDirective")
public class PaginationDirective implements TemplateDirectiveModel {

    private static final String PATTERN = "pattern";
    private static final String PAGE_NUMBER = "pageNumber";
    private static final String TOTAL_PAGES = "totalPages";
    private static final String SEGMENT_COUNT = "segmentCount";

    private static final String HAS_PREVIOUS = "hasPrevious";
    private static final String HAS_NEXT = "hasNext";
    private static final String IS_FIRST = "isFirst";
    private static final String IS_LAST = "isLast";
    private static final String PREVIOUS_PAGE_NUMBER = "previousPageNumber";
    private static final String NEXT_PAGE_NUMBER = "nextPageNumber";
    private static final String FIRST_PAGE_NUMBER = "firstPageNumber";
    private static final String LAST_PAGE_NUMBER = "lastPageNumber";
    private static final String SEGMENT = "segment";

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {

        String pattern = FreemarkerHelper.getParameter(PATTERN, String.class, params);
        Integer pageNumber = FreemarkerHelper.getParameter(PAGE_NUMBER, Integer.class, params);
        Integer totalPages = FreemarkerHelper.getParameter(TOTAL_PAGES, Integer.class, params);
        Integer segmentCount = FreemarkerHelper.getParameter(SEGMENT_COUNT, Integer.class, params);

        if ((pageNumber == null) || (pageNumber.intValue() < 1)) {
            pageNumber = Integer.valueOf(1);
        }
        if ((totalPages == null) || (totalPages.intValue() < 1)) {
            totalPages = Integer.valueOf(1);
        }
        if ((segmentCount == null) || (segmentCount.intValue() < 1)) {
            segmentCount = Integer.valueOf(5);
        }
        boolean hasPrevious = pageNumber.intValue() > 1;
        boolean hasNext = pageNumber.intValue() < totalPages.intValue();
        boolean isFirst = pageNumber.intValue() == 1;
        boolean isLast = pageNumber.equals(totalPages);
        int previousPageNumber = pageNumber.intValue() - 1;
        int nextPageNumber = pageNumber.intValue() + 1;
        int firstPageNumber = 1;
        int lastPageNumber = totalPages.intValue();

        int n = pageNumber.intValue() - (int) Math.floor((segmentCount.intValue() - 1) / 2.0D);
        int i1 = pageNumber.intValue() + (int) Math.ceil((segmentCount.intValue() - 1) / 2.0D);
        if (n < 1) {
            n = 1;
        }
        if (i1 > totalPages.intValue()) {
            i1 = totalPages.intValue();
        }
        List<Integer> segment = new ArrayList<Integer>();
        for (int i2 = n; i2 <= i1; i2++) {
            segment.add(i2);
        }

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put(PATTERN, pattern);
        paramMap.put(PAGE_NUMBER, pageNumber);
        paramMap.put(TOTAL_PAGES, totalPages);
        paramMap.put(SEGMENT_COUNT, segmentCount);
        paramMap.put(HAS_PREVIOUS, Boolean.valueOf(hasPrevious));
        paramMap.put(HAS_NEXT, Boolean.valueOf(hasNext));
        paramMap.put(IS_FIRST, Boolean.valueOf(isFirst));
        paramMap.put(IS_LAST, Boolean.valueOf(isLast));
        paramMap.put(PREVIOUS_PAGE_NUMBER, Integer.valueOf(previousPageNumber));
        paramMap.put(NEXT_PAGE_NUMBER, Integer.valueOf(nextPageNumber));
        paramMap.put(FIRST_PAGE_NUMBER, Integer.valueOf(firstPageNumber));
        paramMap.put(LAST_PAGE_NUMBER, Integer.valueOf(lastPageNumber));
        paramMap.put(SEGMENT, segment);

        Map<String, Object> hashMap = new HashMap<String, Object>();
        for (String dataKey : paramMap.keySet()) {
            TemplateModel templateModel = FreemarkerHelper.getVariable(env, dataKey);
            hashMap.put(pattern, templateModel);
        }

        FreemarkerHelper.setVariables(env, paramMap);
        body.render(env.getOut());
        FreemarkerHelper.setVariables(env, hashMap);
    }
}
