/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ga;

import ga.chromosome.Chromosome;

/**
 *
 * @author dee
 */
public final class OperatorOptions {

    private int parentSelection;
    private int survivorSelection;
    private int chromosomeType;
    private int crossoverType;
    private int mutationType;
    private Chromosome pattern;

    public OperatorOptions() throws Exception {
        parentSelection = 0;
        survivorSelection = 0;
        chromosomeType = 0;
        crossoverType = 0;
        mutationType = 0;
        pattern = null;
    }

    public OperatorOptions(int chromosomeType) throws Exception {
        this();
        setChromosomeType(chromosomeType);
    }

    public OperatorOptions(int chromosomeType, int crossoverType) throws Exception {
        this(chromosomeType);
        setCrossoverType(crossoverType);
    }

    public OperatorOptions(int chromosomeType, int crossoverType, int mutationType) throws Exception {
        this(chromosomeType, crossoverType);
        setMutationType(mutationType);
    }

    public OperatorOptions(int chromosomeType, int crossoverType, int mutationType, int parentSelection) throws Exception {
        this(chromosomeType, crossoverType, mutationType);
        setParentSelection(parentSelection);
    }

    public OperatorOptions(int chromosomeType, int crossoverType, int mutationType, int parentSelection, int survivorSelection) throws Exception {
        this(chromosomeType, crossoverType, mutationType, parentSelection);
        setSurvivorSelection(survivorSelection);
    }

    public OperatorOptions(int chromosomeType, int crossoverType, int mutationType, int parentSelection, int survivorSelection, Chromosome pattern) throws Exception {
        this(chromosomeType, crossoverType, mutationType, parentSelection, survivorSelection);
        setPattern(pattern);
    }

    public int getParentSelection() {
        return parentSelection;
    }

    public final void setParentSelection(int parentSelection) throws Exception {
        this.parentSelection = parentSelection;
        if (parentSelection > 5 || parentSelection < 0) {
            throw new Exception("wrong parent selection option");
        }
    }

    public int getSurvivorSelection() {
        return survivorSelection;
    }

    public void setSurvivorSelection(int survivorSelection) throws Exception {
        if (survivorSelection > 2 || survivorSelection < 0) {
            throw new Exception("wrong survivor selection option");
        }
        this.survivorSelection = survivorSelection;
    }

    public int getChromosomeType() {
        return chromosomeType;
    }

    public void setChromosomeType(int chromosomeType) throws Exception {
        if (chromosomeType > 3 || chromosomeType < 0) {
            throw new Exception("wrong chromosome type option");
        }
        this.chromosomeType = chromosomeType;
    }

    public int getCrossOverType() {
        return crossoverType;
    }

    public void setCrossoverType(int crossoverType) throws Exception {
        if (crossoverType > 11 || crossoverType < 0) {
            throw new Exception("wrong crossover type option");
        } else if (chromosomeType < 2 && crossoverType > 2) {
            throw new Exception("wrong crossover type option");
        } else if (chromosomeType < 3 && crossoverType > 5) {
            throw new Exception("wrong crossover type option");
        } else if (chromosomeType == 3 && crossoverType < 6) {
            throw new Exception("wrong crossover type option");
        }
        this.crossoverType = crossoverType;
    }

    public int getMutationType() {
        return mutationType;
    }

    public void setMutationType(int mutationType) throws Exception {
        if (mutationType > 9 || crossoverType < 0) {
            throw new Exception("wrong mutation type option");
        } else if (chromosomeType == 0 && mutationType != 0) {
            throw new Exception("wrong binary mutation type option");
        } else if (chromosomeType == 1 && (mutationType < 1 || mutationType > 3)) {
            throw new Exception("wrong integer mutation type option");
        } else if (chromosomeType == 2 && (mutationType < 2 || mutationType > 5)) {
            throw new Exception("wrong real mutation type option");
        } else if (chromosomeType == 3 && mutationType < 6) {
            throw new Exception("wrong mutation type option");
        }
        this.mutationType = mutationType;
    }

    public Chromosome getPattern() {
        return pattern;
    }

    public void setPattern(Chromosome pattern) {
        this.pattern = pattern;
    }

}
