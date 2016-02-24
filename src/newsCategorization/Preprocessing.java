/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newsCategorization;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author dee
 */
public class Preprocessing {

    private HashMap<String, Double> preprocessedData;

    public Preprocessing() {
        preprocessedData = new HashMap<>();
    }

    public String removeUrl(String article) {
        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern p = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(article);
        int i = 0;
        while (m.find()) {
            article = article.replaceAll(m.group(i), "").trim();
            i++;
        }
        return article;
    }

    public String[] stopwordRemoval(String[] tokens) {
        return tokens;
    }

    public String[] preprocess(String article) {
        String[] result;
        article.toLowerCase();
        article = removeUrl(article);
        article.replaceAll("[^a-zA-Z|^ ]", "");
        String[] tokens = article.split(" ");

        String[] rootword = new String[1];

        Set<String> rootwordSet = new HashSet<String>(Arrays.asList(rootword));
        Set<String> tokenSet = new HashSet<String>(Arrays.asList(tokens));
        rootwordSet.retainAll(tokenSet);

        String[] nonKataDasar = rootwordSet.toArray(new String[rootwordSet.size()]);

        result = stopwordRemoval(tokens);

        return result;
    }

    public void preprocessInput(String[] input, int[] cls) {
        if (input.length != cls.length) {
            throw new IllegalStateException("Wrong Input size");
        }

        for (int i = 0; i < input.length; i++) {

        }

    }

    public HashMap<String, Double> getPreprocessedData() {
        return preprocessedData;
    }

}
