package edu.austral.ingsis;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toList;

public class StringSimplifier {

    public static List<Line> removeEnters(String s){
        List<Line> result = new ArrayList<>();
        AtomicInteger row = new AtomicInteger(1);
        String nonChecked = s;
        while (nonChecked.contains("\"") || nonChecked.contains("'")) {
            String between = getFirstStringInText(nonChecked);
            String[] aux = split(nonChecked, between).toArray(String[]::new);
            nonChecked = aux.length > 1 ? aux[1] : "";
            if(!result.isEmpty()){
                row.decrementAndGet();
                List<Line> newLines = Arrays.stream(aux[0].split("\n")).map(text -> new Line(text, row.getAndIncrement())).collect(toList());
                mergeLists(result, newLines);
            }
            else {
                result = Arrays.stream(aux[0].split("\n")).map(text -> new Line(text, row.getAndIncrement())).collect(toList());
            }
            if(aux[0].charAt(aux[0].length()-1) == '\n') {
                result.add(new Line(between, row.getAndIncrement()));
            } else {
                result.set(result.size()-1, result.get(result.size()-1).concatText(between));
            }
        }
        if (nonChecked.equals(s)) result.addAll(Arrays.stream(s.split("\n")).map(text -> new Line(text, row.getAndIncrement())).collect(toList()));
        else{
            row.decrementAndGet();
            List<Line> last = Arrays.stream(nonChecked.split("\n")).map(text -> new Line(text, row.getAndIncrement())).collect(toList());
            mergeLists(result, last);
        }
        return result;
    }

    public static List<String> removeSpaces(String s){
        List<String> split = new ArrayList<>();
        String nonChecked = s;
        while (nonChecked.contains("\"") || nonChecked.contains("'")) {
            String between = getFirstStringInText(nonChecked);
            String[] aux = split(nonChecked, between).toArray(String[]::new);
            nonChecked = aux.length > 1 ? aux[1] : "";
            split.addAll(Arrays.asList(aux[0].strip().split(" ")));
            split.add(between);
        }
        if (nonChecked.equals(s)) split.addAll(Arrays.asList(s.split(" ")));
        else split.addAll(Arrays.asList(nonChecked.strip().split(" ")));
        return split;
    }


    private static void mergeLists(List<Line> oldLines,List<Line> newLines){
        oldLines.set(oldLines.size()-1, oldLines.get(oldLines.size()-1).concatText(newLines.get(0).getText()));
        oldLines.addAll(newLines.subList(1, newLines.size()));
    }

    private static List<String> split(String txt, String value) {
        List<String> array = new ArrayList<>();
        if (txt.contains(value)) {
            array.add(txt.substring(0, txt.indexOf(value.charAt(0))));
            String s = txt.substring(txt.indexOf(value.charAt(0))+1);
            if(!s.substring(s.indexOf(value.charAt(0))+1).isEmpty()) array.add(s.substring(s.indexOf(value.charAt(0))+1));
        } else {
            array.add(value);
        }
        return array;
    }

    private static String getFirstStringInText(String text){
        String colons = whichCharacterAppearsFirst(text);
        String subBetween =  StringUtils.substringBetween(text, colons);
        if (subBetween == null) {
            throw new RuntimeException("Missing one " + colons + " in " + text);
        }
        return colons + subBetween + colons;
    }

    private static String whichCharacterAppearsFirst(String text){
        int one = text.indexOf('\"');
        int two = text.indexOf('\'');
        if(one == -1) return "'";
        if(two == -1) return "\"";
        return one < two ? "\"" : "'";
    }
}