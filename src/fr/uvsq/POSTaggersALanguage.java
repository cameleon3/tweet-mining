package fr.uvsq;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.shef.ac.uk.util.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.hfst.NoTokenizationException;
import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.cmdline.postag.POSModelLoader;
//import opennlp.tools.lang.spanish.SentenceDetector;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;
import postaggersalanguage.Heuristics;

/**
 *
 * @author ahmetaker
 */
public class POSTaggersALanguage {

    private POSModel itsPOSModel = null;
    private SentenceModel itsSentenceModel = null;
    private TokenizerModel itsTokenizerModel = null;

    public String[] tokenize(String aSentence, String aLang, String aResourceFolder) throws InvalidFormatException, IOException {
        if (itsTokenizerModel == null) {
            InputStream is = new FileInputStream(aResourceFolder + "/tokenizerModels/" + aLang + "-token.bin");
            itsTokenizerModel = new TokenizerModel(is);
            is.close();
        }
        Tokenizer tokenizer = new TokenizerME(itsTokenizerModel);
        String tokens[] = tokenizer.tokenize(aSentence);


        //now apply also some rules!
        ArrayList<String> array = new ArrayList<String>();
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i].trim();
            if ("".equals(token)) {
                continue;
            }
//            if (token.equals("...")) {
//                array.add(".");
//                array.add(".");
//                array.add(".");
//                continue;
//            }

            //    String first = token.charAt(0) + "";
            //System.out.println("here we got a token " + token);
//            if (Heuristics.isPunctuation(first)) {
//                token = token.substring(1);
//                String secondFirst = null;
//                if (token.length() > 0) {
//                    secondFirst = token.charAt(0) + "";
//                    if (Heuristics.isPunctuation(secondFirst)) {
//                        token = token.substring(1);
//                    } else {
//                        secondFirst = null;
//                    }
//                }
//                array.add(first);
//                if (secondFirst != null) {
//                    array.add(secondFirst);
//                }
//            } else {
            char chraters[] = token.toCharArray();
            Vector<String> take = new Vector<String>();
            StringBuffer buffer = new StringBuffer();
            for (int j = 0; j < chraters.length; j++) {
                String c = chraters[j] + "";
                if (Heuristics.isPunctuation(c)) {
                    String str = buffer.toString().trim();
                    if (!str.equals("")) {
                        take.add(buffer.toString());
                    }
                    buffer = new StringBuffer();
                    take.add(c);
                } else {
                    buffer.append(c);
                }
            }
            if (!buffer.toString().equals("")) {
                take.add(buffer.toString());
            }
            for (int j = 0; j < take.size(); j++) {
                String string = take.get(j);
                array.add(string);
            }
//            }



//            if (token.length() > 0) {
//                String last = token.charAt(token.length() - 1) + "";
//
//                if (Heuristics.isPunctuation(last)) {
//                    token = token.substring(0, token.length() - 1);
//                    if (token.length() > 0) {
//                        String secondLast = token.charAt(token.length() - 1) + "";
//                        if (Heuristics.isPunctuation(secondLast)) {
//                            token = token.substring(0, token.length() - 1);
//                            if (token.length() > 0) {
//                                array.add(token);
//                            }
//                            array.add(secondLast);
//                            array.add(last);
//                        } else {
//                            array.add(token);
//                            array.add(last);
//                        }
//                    }
//                } else {
//                    array.add(token);
//                }
//            }

        }

        String a[] = new String[array.size()];
        return array.toArray(a);

    }

    public String[] sentenceDetect(String aText, String aLang, String aResourceFolder) throws InvalidFormatException, IOException {
        if (itsSentenceModel == null) {
            InputStream is = new FileInputStream(aResourceFolder + "/setenceDetectionModels/" + aLang + "-sent.bin");
            itsSentenceModel = new SentenceModel(is);
            is.close();
        }
        SentenceDetectorME sdetector = new SentenceDetectorME(itsSentenceModel);

        String sentences[] = sdetector.sentDetect(aText);
        return sentences;
    }

    public String[] posTag(String aSentence[], String aLang, String aResourceFolder) {
        String posTaggedVersion[] = null;
        if (itsPOSModel == null) {
            itsPOSModel = new POSModelLoader()
                    .load(new File(aResourceFolder + "/posModels/" + aLang + "-pos-maxent.bin"));
        }
        //PerformanceMonitor perfMon = new PerformanceMonitor(System.err, "sent");
        POSTaggerME tagger = new POSTaggerME(itsPOSModel);

        posTaggedVersion = tagger.tag(aSentence);
        return posTaggedVersion;
    }

    public static void main2(String args[]) throws InvalidFormatException, IOException {
        POSTaggersALanguage posTagger = new POSTaggersALanguage();

        String text = "This time, it’s your turn: advise Parliament in the first LinkedIn discussion on an ongoing report. The rapporteur wants to hear your views @...(read more). --- Keywords ---";
        String lang = "en";
        String sentences[] = posTagger.sentenceDetect(text, lang, "D:\\work\\workspace\\POSTaggersALanguage");
        for (int i = 0; i < sentences.length; i++) {
            String string = sentences[i];
            System.out.println(string);
            String tokens[] = posTagger.tokenize(string, lang, "D:\\work\\workspace\\POSTaggersALanguage");
            for (int j = 0; j < tokens.length; j++) {
                String token = tokens[j];
                System.out.println(token);
            }
            String posTags[] = posTagger.posTag(tokens, lang, "D:\\\\work\\\\workspace\\\\POSTaggersALanguage");
            for (int j = 0; j < posTags.length; j++) {
                String string1 = posTags[j];
                System.out.println(string1);
            }
        }
    }

    public static void mainTest(String args[]) throws IOException, NoTokenizationException {
//        if (args.length < 4) {
//            System.err.print("Error in argument list: java -jar POSTaggerALanguage.jar inputFile outputFolder language resourcesFolder isList");
//            System.exit(0);
//        }
        File folders[] = (new File("D:\\work\\workspace\\POSTaggersALanguage\\LemmatiserTestDataFromTreeTagger")).listFiles();
        for (int t = 0; t < folders.length; t++) {
            File folder = folders[t];
            String folderName = folder.getName();
            StringBuffer bufferUp = new StringBuffer();
            if (folderName.contains("plain") && !folderName.contains("USFD")) {
                String args2[] = new String[5];
                args2[3] = "D:\\work\\workspace\\POSTaggersALanguage";
                args2[4] = "1";
                Map<String, String> errors = new HashMap<String, String>();
                File files[] = folder.listFiles();
                File newFolder = new File("D:\\work\\workspace\\POSTaggersALanguage\\LemmatiserTestDataFromTreeTagger\\" + folderName + "_USFD_Tagged");
                newFolder.mkdir();;
                args2[0] = folder.getAbsolutePath() + "//fileList.txt";
                args2[1] = newFolder.getAbsolutePath();
                for (int i = 0; i < files.length; i++) {
                    File file = files[i];
                    bufferUp.append(file.getAbsolutePath()).append("\n");
                }
                Util.doSave(folder.getAbsolutePath() + "//fileList.txt", bufferUp.toString());

                if (folderName.contains("de")) {
                    args2[2] = "de";
                } else if (folderName.contains("fr")) {
                    args2[2] = "fr";
                } else if (folderName.contains("it")) {
                    args2[2] = "it";
                } else if (folderName.contains("en")) {
                    args2[2] = "en";
                } else if (folderName.contains("nl")) {
                    args2[2] = "nl";
                }
                args = args2;
                String fileToReadFrom = args[0];
                String fileToSaveFolder = args[1];
                String language = args[2];
                String resourcesFolder = args[3];
                String isList = args[4];
                System.out.println(fileToReadFrom);
                System.out.println(language);

                if ("de".equalsIgnoreCase(language) || "en".equalsIgnoreCase(language)
                        || "fr".equalsIgnoreCase(language) || "nl".equalsIgnoreCase(language) || "it".equalsIgnoreCase(language)) {

                    POSTaggersALanguage posTagger = new POSTaggersALanguage();
                    Map<String, String> nounDic = Util.loadDictionary(resourcesFolder + "//dictionaries//" + language + "//nounDic.txt");
                    Map<String, String> adjDic = Util.loadDictionary(resourcesFolder + "//dictionaries//" + language + "//adjDic.txt");
                    Map<String, String> advDic = Util.loadDictionary(resourcesFolder + "//dictionaries//" + language + "//advDic.txt");
                    Map<String, String> verbDic = Util.loadDictionary(resourcesFolder + "//dictionaries//" + language + "//verbDic.txt");
                    Map<String, String> detDic = Util.loadDictionary(resourcesFolder + "//dictionaries//" + language + "//detDic.txt");
                    Map<String, String> pronDic = Util.loadDictionary(resourcesFolder + "//dictionaries//" + language + "//pronounDic.txt");


                    Map<String, String> posMap = Util.getFileContentAsMap(resourcesFolder + "\\universal-pos-tags\\" + language + "POSMapping.txt", "######", true);
                    //System.out.println(posMap);
                    Vector<String> fileList = new Vector<String>();
                    if (isList.equals("1")) {
                        fileList = Util.getFileContentAsVectorUTF(fileToReadFrom);
                    } else {
                        fileList.add(fileToReadFrom);
                    }
                    for (int k = 0; k < fileList.size(); k++) {
                        String file = fileList.get(k);
                        StringBuffer buffer = new StringBuffer();
                        String text = null;
                        StringBuffer buf = new StringBuffer();
                        Vector<String> lines = Util.getFileContentAsVectorUTF(file);
                        for (int i = 0; i < lines.size(); i++) {
                            String line = lines.get(i);
                            if (!line.trim().equals("")) {
                                buf.append(line).append("\n");
                            }
                        }
                        text = buf.toString();
                        String sentences[] = null;
                        sentences = posTagger.sentenceDetect(text, language, resourcesFolder);

                        for (int i = 0; i < sentences.length; i++) {
                            String sentence = sentences[i].trim();
                            String tokens[] = posTagger.tokenize(sentence, language, resourcesFolder);
                            String posTaggedVersion[] = posTagger.posTag(tokens, language, resourcesFolder);
                            for (int s = 0; s < tokens.length; ++s) {
                                String token = tokens[s];
                                if (Heuristics.isNumber(token)) {
                                    buffer.append(token).append("\t").append("NUM").append("\t").append(token.toLowerCase()).append("\n");
                                } else if (Heuristics.isPunctuation(tokens[s])) {
                                    buffer.append(token).append("\t").append("PUNC").append("\t").append(token.toLowerCase()).append("\n");
                                } else if (detDic.get(token.toLowerCase()) != null) {
                                    buffer.append(token).append("\t").append("DET").append("\t").append(token.toLowerCase()).append("\n");
                                } else {
                                    String lemma = null;
                                    String posType = posTaggedVersion[s];
                                    if ("it".equalsIgnoreCase(language)) {
                                        posType = posType.substring(0, 1);
                                    }
                                    //System.out.println(posType);
                                    String generalType = posMap.get(posType.toLowerCase());

                                    if (generalType != null) {
                                        if ("NOUN".equalsIgnoreCase(generalType)) {
                                            lemma = nounDic.get(token.toLowerCase());
                                        } else if ("VERB".equalsIgnoreCase(generalType)) {
                                            lemma = verbDic.get(token.toLowerCase());
                                        } else if ("ADJ".equalsIgnoreCase(generalType)) {
                                            lemma = adjDic.get(token.toLowerCase());
                                        } else if ("ADV".equalsIgnoreCase(generalType)) {
                                            lemma = advDic.get(token.toLowerCase());
                                        } else if ("PRON".equalsIgnoreCase(generalType)) {
                                            lemma = pronDic.get(token.toLowerCase());

                                        }
                                        if (!"nl".equalsIgnoreCase(language) && lemma == null) {
                                            try {
                                                lemma = Lemmatizer.getLemma(resourcesFolder, token, language, generalType);
                                            } catch (Exception e) {
                                                try {
                                                    lemma = Lemmatizer.getLemma(resourcesFolder, token.toLowerCase(), language, generalType);
                                                } catch (Exception e2) {
                                                }
                                            }
                                        }
                                    } else {
                                        errors.put(token, posTaggedVersion[s]);
                                    }
                                    if (lemma == null || "".equals(lemma)) {
                                        lemma = tokens[s];
                                    }
                                    buffer.append(token).append("\t").append(posTaggedVersion[s]).append("\t").append(lemma.toLowerCase()).append("\n");
                                }
                            }
                        }
                        Util.doSaveUTF(fileToSaveFolder + "//" + ((new File(file)).getName()), buffer.toString());
                    }
                    System.out.println(errors);
                } else {
                    System.err.print("Error : language you have given is not support. Please use: de, en, it, fr or nl.");
                }



            }

        }

    }

    public static void main(String args[]) throws IOException, NoTokenizationException {
        POSTaggersALanguage posTagger = new POSTaggersALanguage();

        String args2[] = {"/home/souley/Bureau/text.txt", "/home/souley/Bureau/", "fr", "/home/souley/Documents/AllFiveLanguages","1"};
        args = args2;
        String fileToReadFrom = args[0];
        String fileToSaveFolder = args[1];
        String language = args[2];
        String resourcesFolder = args[3];
        String isList = args[4];
        if ("de".equalsIgnoreCase(language) || "en".equalsIgnoreCase(language)
                || "fr".equalsIgnoreCase(language) || "nl".equalsIgnoreCase(language) || "it".equalsIgnoreCase(language)) {

            Map<String, String> nounDic = Util.loadDictionary(resourcesFolder + "/dictionaries/" + language + "/nounDic.txt");
            Map<String, String> adjDic = Util.loadDictionary(resourcesFolder + "/dictionaries/" + language + "/adjDic.txt");
            Map<String, String> advDic = Util.loadDictionary(resourcesFolder + "/dictionaries/" + language + "/advDic.txt");
            Map<String, String> verbDic = Util.loadDictionary(resourcesFolder + "/dictionaries/" + language + "/verbDic.txt");
            Map<String, String> detDic = Util.loadDictionary(resourcesFolder + "/dictionaries/" + language + "/detDic.txt");
            Map<String, String> pronDic = Util.loadDictionary(resourcesFolder + "/dictionaries/" + language + "/pronounDic.txt");


            Map<String, String> posMap = Util.getFileContentAsMap(resourcesFolder + "/universal-pos-tags/" + language + "POSMapping.txt", "######", true);

            Vector<String> fileList = new Vector<String>();
            if (isList.equals("1")) {
                fileList = Util.getFileContentAsVectorUTF(fileToReadFrom);
            } else {
                fileList.add(fileToReadFrom);
            }
            for (int k = 0; k < fileList.size(); k++) {
                String file = fileList.get(k);

                StringBuffer buffer = new StringBuffer();
                String text = null;
                StringBuffer buf = new StringBuffer();
                Vector<String> lines = Util.getFileContentAsVectorUTF(file);
                for (int i = 0; i < lines.size(); i++) {
                    String line = lines.get(i);
                    if (!line.trim().equals("")) {
                        line = line.replaceAll("\\{", " { ");
                        line = line.replaceAll("\\}", " } ");
                        line = line.replaceAll("\\[", " [ ");
                        line = line.replaceAll("\\]", " ] ");
                        buf.append(line).append("\n");
                    }
                }
                text = buf.toString();
                String sentences[] = null;
                sentences = posTagger.sentenceDetect(text, language, resourcesFolder);

                for (int i = 0; i < sentences.length; i++) {
                    String sentence = sentences[i];
                    String tokens[] = posTagger.tokenize(sentence, language, resourcesFolder);
                    String posTaggedVersion[] = posTagger.posTag(tokens, language, resourcesFolder);
                    for (int s = 0; s < tokens.length; ++s) {
                        String token = tokens[s];
                        if (Heuristics.isNumber(token)) {
                            buffer.append(token).append("\t").append("NUM").append("\t").append(token).append("\n");
                        } else if (Heuristics.isPunctuation(tokens[s])) {
                            buffer.append(token).append("\t").append("PUNC").append("\t").append(token.toLowerCase()).append("\n");
                        } else if (detDic.get(token.toLowerCase()) != null) {
                            buffer.append(token).append("\t").append("DET").append("\t").append(token.toLowerCase()).append("\n");
                        } else {
                            String lemma = null;
                            String posType = posTaggedVersion[s];
                            if ("it".equalsIgnoreCase(language)) {
                                posType = posType.substring(0, 1);
                            }
                            String generalType = posMap.get(posType.toLowerCase());
                            if (generalType != null) {
                                if ("NOUN".equalsIgnoreCase(generalType)) {
                                    lemma = nounDic.get(token.toLowerCase());
                                } else if ("VERB".equalsIgnoreCase(generalType)) {
                                    lemma = verbDic.get(token.toLowerCase());
                                } else if ("ADJ".equalsIgnoreCase(generalType)) {
                                    lemma = adjDic.get(token.toLowerCase());
                                } else if ("ADV".equalsIgnoreCase(generalType)) {
                                    lemma = advDic.get(token.toLowerCase());
                                } else if ("PRON".equalsIgnoreCase(generalType)) {
                                    lemma = pronDic.get(token.toLowerCase());

                                }
                                if (!"nl".equalsIgnoreCase(language) && lemma == null) {
                                    try {
                                        lemma = Lemmatizer.getLemma(resourcesFolder, token, language, generalType);
                                    } catch (Exception e) {
                                        try {
                                            lemma = Lemmatizer.getLemma(resourcesFolder, token.toLowerCase(), language, generalType);
                                        } catch (Exception e2) {
                                        }
                                    }
                                }
                            }
                            if (lemma == null || "".equals(lemma)) {
                                lemma = tokens[s];
                            } else {
                                if ("NOUN".equalsIgnoreCase(generalType)) {
                                    String firstCharToken = token.substring(0, 1);
                                    String firstCharLemma = lemma.toLowerCase().substring(0, 1);
                                    if (!firstCharToken.equals(firstCharLemma) && firstCharToken.equalsIgnoreCase(firstCharLemma)) {
                                        lemma = firstCharToken + lemma.substring(1).toLowerCase();
                                    }
                                }
                            }
                            buffer.append(token).append("\t").append(posTaggedVersion[s]).append("\t").append(lemma).append("\n");
                        }
                    }
                }
                if (isList.equals("1")) {
                    Util.doSaveUTF(fileToSaveFolder + "/" + ((new File(file)).getName()), buffer.toString());
                } else {
                    Util.doSaveUTF(fileToSaveFolder, buffer.toString());

                }
            }
        } else {
            System.err.print("Error : language you have given is not support. Please use: de, en, it, fr or nl.");
            System.exit(0);
        }



    }
}
