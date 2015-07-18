package com.agh.studio_projektowe.parser;


import com.agh.studio_projektowe.error.ErrorType;
import com.agh.studio_projektowe.error.FunctionalException;
import com.agh.studio_projektowe.model.LTLPattern;
import com.agh.studio_projektowe.model.LTLPatternType;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class LTLModelsParser {

    private static Pattern BEGIN_PATTERN = Pattern.compile("(\\w+)\\((.*?)\\)");

    private String getLines(InputStream inputStream) throws FunctionalException {

        StringBuilder builder = new StringBuilder();
        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append('\n');
            }
        } catch (IOException e) {
            throw new FunctionalException(ErrorType.IO_ERROR);
        }

        return builder.toString().trim();
    }

    private LTLPattern buildPattern(String patternLines) throws FunctionalException {

        String[] lines = patternLines.split("\\n");
        if (lines.length < 3) {
            throw new FunctionalException(ErrorType.BAD_LTL_STRUCTURE);
        }

        Matcher match = BEGIN_PATTERN.matcher(lines[0]);
        if (!match.find()) {
            throw new FunctionalException(ErrorType.BAD_LTL_STRUCTURE);
        }

        LTLPatternType patternType = LTLPatternType.getPatternByName(match.group(1));   // equalsIgnoreCase
        if (patternType == null) {
            throw new FunctionalException(ErrorType.UNKNOWN_LTL_PATTERN);
        }

        String[] inParams = match.group(2).split(",");
        if (inParams.length != patternType.getInParamsCount()) {
            throw new FunctionalException(ErrorType.BAD_LTL_STRUCTURE);
        }

        trimElements(inParams);

        String[] iniFin = getIniFinElements(lines[1]);
        String[] logic = Arrays.copyOfRange(lines, 2, lines.length);

        return new LTLPattern(patternType, inParams, iniFin[0], iniFin[1], logic);
    }

    private String[] getIniFinElements(String line) throws FunctionalException {

        String[] inOutParams = line.split("/");
        if (inOutParams.length != 2) {
            throw new FunctionalException(ErrorType.BAD_LTL_STRUCTURE);
        }

        String[] ini = inOutParams[0].split("=");
        String[] fin = inOutParams[1].split("=");
        trimElements(ini);
        trimElements(fin);

        if ((ini.length != 2) || (fin.length != 2)) {
            throw new FunctionalException(ErrorType.BAD_LTL_STRUCTURE);
        }

        if (!"INI".equalsIgnoreCase(ini[0]) || !"FIN".equalsIgnoreCase(fin[0])) {
            throw new FunctionalException(ErrorType.BAD_LTL_STRUCTURE);
        }

        return new String[] {ini[1], fin[1]};
    }

    private void trimElements(String[] tab) {

        for (int i=0; i<tab.length; i++) {
            tab[i] = tab[i].trim();
        }
    }

    public List<LTLPattern> getPatterns(InputStream inputStream) throws FunctionalException {

//        File file = new File(filePath);
//        if (!file.exists()) {
        if ( inputStream == null ) {
            throw new FunctionalException(ErrorType.FILE_NOT_EXIST);
        }

        String lines = getLines(inputStream);
        String[] patterns = lines.split("@");
        List<LTLPattern> patternList = new ArrayList<>();

        for (String patternLines : patterns) {
            patternList.add(buildPattern(patternLines.trim())); // trim() because of exception
        }

        return patternList;
    }

}
