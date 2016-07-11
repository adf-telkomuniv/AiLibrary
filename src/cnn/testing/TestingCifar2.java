/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cnn.testing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import org.python.core.PyDictionary;
import org.python.core.PyFile;
import org.python.core.PyObject;
import org.python.modules.cPickle;

/**
 *
 * @author ANDITYAARIFIANTO
 */
public class TestingCifar2 {

    public static void main(String[] args) {
        cPickle.Unpickler u;

        File f = new File("C:\\Users\\ANDITYAARIFIANTO\\PycharmProjects\\untitled\\cifar-10-batches-py\\data_batch_1");

        InputStream fs = null;
        try {
            fs = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        PyFile pf = new PyFile(fs);
        PyDictionary d = (PyDictionary) cPickle.load(pf);
//        u = cPickle.Unpickler(pf);
//        System.out.println(u.load());

    }
}
