/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fuzzy;

/**
 *
 * @author dee
 */
public class MamdaniOutput extends Input implements CrispOutput {

    public MamdaniOutput(int numLinguistic) {
        super(numLinguistic);
    }

    @Override
    public double defuzzy(FuzzyOutput[] output) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
