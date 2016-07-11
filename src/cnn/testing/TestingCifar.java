/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cnn.testing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.io.FileInputStream;
import java.io.InputStream;
import org.python.core.PyDictionary;
import org.python.core.PyFile;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.modules.cPickle;

/**
 *
 * @author ANDITYAARIFIANTO
 */
public class TestingCifar {

    public static void main(String[] args) {
        TestingCifar loader = new TestingCifar();
        HashMap<String, ArrayList<String>> loadCnt1 =  loader.getIdToCountriesStr();

        System.out.println(loadCnt1.toString());

//        HashMap<String, ArrayList<String>> loadCnt2 =loader.getIdToCountriesFileStream();
//
//        System.out.println(loadCnt2.toString());
    }

    public HashMap<String, ArrayList<String>> getIdToCountriesStr() {

        HashMap<String, ArrayList<String>> idToCountries = new HashMap<String, ArrayList<String>>();

        File f = new File("C:\\Users\\ANDITYAARIFIANTO\\PycharmProjects\\untitled\\cifar-10-batches-py\\data_batch_1");

        System.out.println(f.length());

        BufferedReader bufR;

        StringBuilder strBuilder = new StringBuilder();

        try {

            bufR = new BufferedReader(new FileReader(f));

            String aLine;

            while (null != (aLine = bufR.readLine())) {

                strBuilder.append(aLine).append("n");

            }

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

        PyString pyStr = new PyString(strBuilder.toString());

        PyDictionary idToCountriesObj = (PyDictionary) cPickle.loads(pyStr);

        ConcurrentMap<PyObject, PyObject> aMap = idToCountriesObj.getMap();

        for (Map.Entry<PyObject, PyObject> entry : aMap.entrySet()) {

            String appId = entry.getKey().toString();

            PyList countryIdList = (PyList) entry.getValue();

            List<String> countryList = (List<String>) countryIdList.subList(0, countryIdList.size());

            ArrayList<String> countryArrList = new ArrayList<String>(countryList);

            idToCountries.put(appId, countryArrList);

        }

//        System.out.println(idToCountries.toString());
        return idToCountries;

    }

    public HashMap<String, ArrayList<String>> getIdToCountriesFileStream() {

        HashMap<String, ArrayList<String>> idToCountries = new HashMap<String, ArrayList<String>>();

        File f = new File("C:\\Users\\ANDITYAARIFIANTO\\PycharmProjects\\untitled\\cifar-10-batches-py\\data_batch_1");

        InputStream fs = null;

        try {

            fs = new FileInputStream(f);

        } catch (FileNotFoundException e) {

            e.printStackTrace();

            return null;

        }

        PyFile pyStr = new PyFile(fs);

        PyDictionary idToCountriesObj = (PyDictionary) cPickle.load(pyStr);

        ConcurrentMap<PyObject, PyObject> aMap = idToCountriesObj.getMap();

        for (Map.Entry<PyObject, PyObject> entry : aMap.entrySet()) {

            String appId = entry.getKey().toString();

            PyList countryIdList = (PyList) entry.getValue();

            List<String> countryList = (List<String>) countryIdList.subList(0, countryIdList.size());

            ArrayList<String> countryArrList = new ArrayList<String>(countryList);

            idToCountries.put(appId, countryArrList);

        }

//        System.out.println(idToCountries.toString());
        return idToCountries;

    }

}
