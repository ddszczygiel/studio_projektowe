package com.agh.studio_projektowe.parser;


import com.agh.studio_projektowe.error.ErrorType;
import com.agh.studio_projektowe.error.FunctionalException;
import com.agh.studio_projektowe.model.LTLPatternType;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class ConfigurationParser {

    private static final Pattern CONF_LINE_PATTERN = Pattern.compile("(\\w+):(\\d)");

    // in conf file must be present EXACTLY those patterns which are defined in LTLPattern class
    public void loadConfig(String filePath) throws FunctionalException {

        File file = new File(filePath);
        if (!file.exists()) {
            throw new FunctionalException(ErrorType.FILE_NOT_EXIST);
        }

        List<String> lines = getLines(file);
        Map<String, Integer> parsedValues = parseLines(lines);

        for (Map.Entry<String, Integer> entry : parsedValues.entrySet()) {
            // can not be null here
            LTLPatternType.getPatternByName(entry.getKey()).setPriority(entry.getValue());
        }
    }

    private List<String> getLines(File file) throws FunctionalException {

        List<String> lines = new ArrayList<>();
        try {

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new FunctionalException(ErrorType.IO_ERROR);
        }

        return lines;
    }

    private Map<String, Integer> parseLines(List<String> lines) throws FunctionalException {

        Map<String, Integer> values = new HashMap<>();
        for (String line : lines) {

            Matcher matcher = CONF_LINE_PATTERN.matcher(line);
            if (matcher.find()) {

                if (LTLPatternType.getPatternByName(matcher.group(1)) == null) {
                    throw new FunctionalException(ErrorType.UNKNOWN_CONF_PATTERN);
                } else {
                    values.put(matcher.group(1), Integer.parseInt(matcher.group(2)));
                }
            }
        }

        if (values.size() != LTLPatternType.values().length) {
            throw new FunctionalException(ErrorType.MISSING_PATTERN_CONF);
        }

        return values;
    }

}
